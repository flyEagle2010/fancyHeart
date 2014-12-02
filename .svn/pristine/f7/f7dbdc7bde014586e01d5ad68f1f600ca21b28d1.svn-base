//
//  Socket.cpp
//  fancyHeart
//
//  Created by 秦亮亮 on 14-5-5.
//
//

#include "Socket.h"
#include "Manager.h"
class SocketThreadHelper : public Ref
{
public:
    SocketThreadHelper();
    ~SocketThreadHelper();
    
    bool createThread(const Socket& socket);
    // Quits sub-thread (socket thread).
    void quitSubThread();
    
    // Schedule callback function
    virtual void update(float dt);
    
    // Sends message to UI thread. It's needed to be invoked in sub-thread.
    void sendMessageToUIThread(NetMsg* msg);
    
    // Sends message to sub-thread(socket thread). It's needs to be invoked in UI thread.
    void sendMessageToSubThread(NetMsg* msg);
    
    // Waits the sub-thread (websocket thread) to exit,
    void joinSubThread();
    
    
protected:
    void SocketThreadEntryFunc();
    
public:
    std::list<NetMsg*>* UIMessageQueue;
    std::list<NetMsg*>* subThreadMessageQueue;
    std::mutex   UIMessageQueueMutex;
    std::mutex   subThreadMessageQueueMutex;
    std::thread* subThreadInstance;
    bool needQuit;
    friend class Socket;
    Socket* socket;
    
};

SocketThreadHelper::SocketThreadHelper()
: subThreadInstance(nullptr)
, socket(nullptr)
, needQuit(false){
    
    UIMessageQueue = new std::list<NetMsg*>();
    subThreadMessageQueue = new std::list<NetMsg*>();
    Director::getInstance()->getScheduler()->scheduleUpdate(this, 0, false);
}

SocketThreadHelper::~SocketThreadHelper(){
    Director::getInstance()->getScheduler()->unscheduleAllForTarget(this);
    joinSubThread();
    CC_SAFE_DELETE(this->subThreadInstance);
    delete this->UIMessageQueue;
    delete this->subThreadMessageQueue;
}

bool SocketThreadHelper::createThread(const Socket& socket){
    // _socket = const_cast<WebSocket*>(&socket);
    this->socket=const_cast<Socket*>(&socket);
    
    // Creates websocket thread
    this->subThreadInstance = new std::thread(&SocketThreadHelper::SocketThreadEntryFunc, this);
    return true;
}

void SocketThreadHelper::quitSubThread(){
    needQuit = true;
}

void SocketThreadHelper::SocketThreadEntryFunc(){
    this->socket->onSubThreadStarted();
    while (!needQuit){
        if (this->socket->onSubThreadLoop()){
            break;
        }
    }
}

void SocketThreadHelper::sendMessageToUIThread(NetMsg* msg){
    std::lock_guard<std::mutex> lk(UIMessageQueueMutex);
    UIMessageQueue->push_back(msg);
}

void SocketThreadHelper::sendMessageToSubThread(NetMsg* msg){
    std::lock_guard<std::mutex> lk(subThreadMessageQueueMutex);
    subThreadMessageQueue->push_back(msg);
}

void SocketThreadHelper::joinSubThread(){
    if (subThreadInstance->joinable()){
        subThreadInstance->join();
    }
}

void SocketThreadHelper::update(float dt){
    // Returns quickly if no message
    
    if (0 == UIMessageQueue->size()){
        return;
    }
    
    // Gets message
//    NetMsg* msg =*(UIMessageQueue->begin());
    NetMsg* msg=UIMessageQueue->front();
    
    if (this->socket){
        this->socket->onUIThreadReceiveMessage(msg);
    }
    
    UIMessageQueueMutex.lock();
    UIMessageQueue->pop_front();
    UIMessageQueueMutex.unlock();
}

//------------------------------------------------------------------------
enum S_MSG {
    S_MSG_TO_SUBTRHEAD_SENDING_STRING = 0,
    S_MSG_TO_SUBTRHEAD_SENDING_BINARY,
    S_MSG_TO_UITHREAD_MESSAGE,
    S_MSG_TO_UITHREAD_ERROR,
    S_MSG_TO_UITHREAD_CLOSE
};


Socket::Socket(){
    
}

Socket::~Socket(){
    this->close();
    CC_SAFE_DELETE_ARRAY(this->socketHelper);
}

void Socket::init(std::string ip,int port){
    Loading::getInstance()->show();
    this->ip=ip;
    this->port=port;
    
    this->socketHelper=new SocketThreadHelper();
    this->socketHelper->createThread(*this);
}

void Socket::send(short mid,google::protobuf::Message* pm,bool isShowLoading){
    if(this->state == State::OPEN){
        if(isShowLoading){
            Loading::getInstance()->show();
        }
        short size=pm->ByteSize();
        char* buffer =new char[size+6+1];
        short flag=htons(3637);
        short len=htons(size+2+2);
        short msgid=htons(mid);

        memcpy(buffer, &flag, 2);
        memcpy(buffer+2, &len, 2);
        memcpy(buffer+4, &msgid, 2);
        
        google::protobuf::io::ZeroCopyOutputStream* rawOut=new google::protobuf::io::ArrayOutputStream(buffer+6,size);
        google::protobuf::io::CodedOutputStream* codeOut=new google::protobuf::io::CodedOutputStream(rawOut);
        pm->SerializeToCodedStream(codeOut);
        buffer[size+6]='\0';
        
        delete rawOut;
        delete codeOut;
        
        NetMsg* msg=new NetMsg();
        
        msg->msgId=mid;
        msg->bytes=buffer;
        msg->len=size+6;
        
        this->socketHelper->sendMessageToSubThread(msg);
        msg=nullptr;
        buffer=nullptr;
        
    }
}

void Socket::close(){
    Director::getInstance()->getScheduler()->unscheduleAllForTarget(this->socketHelper);
    if(state==State::CLOSING || state==State::CLOSED){
        return;
    }
    CCLOG("socket (%p) connection closed by client",this);
    state=State::CLOSED;
    this->socketHelper->joinSubThread();
}

void Socket::onSubThreadStarted(){
    //connect to server
    
    struct sockaddr_in sa;
    struct hostent* hp;
    hp = gethostbyname(this->ip.c_str());
    
    memset(&sa, 0, sizeof(sa));
    memcpy((char*)&sa.sin_addr, hp->h_addr, hp->h_length);
    sa.sin_family = hp->h_addrtype;
    sa.sin_port = htons(this->port);
    
    this->socketHandle = socket(sa.sin_family, SOCK_STREAM, 0);
    if(socketHandle < 0){
        CCLOG( "failed to create socket\n" );
    }
    this->state=State::CONNECTING;
    
    int n=::connect(socketHandle, (sockaddr*)&sa, sizeof(sa));
    if (n<0) {
        CCLOG("connect failed:%s",strerror(errno));
        NetMsg* msg=new NetMsg();
        msg->msgId=CONNECT_ERROR;
        msg->type=S_MSG_TO_UITHREAD_MESSAGE;
        //memcpy(msg->bytes, &errno, sizeof(errno));
        this->socketHelper->sendMessageToUIThread(msg);
        
        this->state=State::CLOSED;
//        this->close();
        return;
    }
    this->state=State::OPEN;
    this->buffer=new char[this->bufferSize];
    
    
    NetMsg* msg=new NetMsg();
    msg->msgId=CONNECTED;
    msg->type=S_MSG_TO_UITHREAD_MESSAGE;
    this->socketHelper->sendMessageToUIThread(msg);
}

int Socket::onSubThreadLoop(){
    if(this->state==State::CLOSED || state==State::CLOSING){
        // return 1 to exit the loop
        //清空数据
        return 1;
    }
    if(this->state==State::OPEN){
    
        //------------------------send-----------------------------------------------------------
        
        this->socketHelper->subThreadMessageQueueMutex.lock();
        std::list<NetMsg*>::iterator iter = this->socketHelper->subThreadMessageQueue->begin();
        
        for (;iter != this->socketHelper->subThreadMessageQueue->end();){
            NetMsg* msg=*iter;
            ssize_t dataSended=::send(socketHandle, msg->bytes, msg->len, 0);
            ssize_t totalSend=dataSended;
            while(totalSend < msg->len){
                dataSended=::send(socketHandle, msg->bytes+totalSend, msg->len-totalSend, 0);
                totalSend+=dataSended;
            }
            log("send msg:%d sucess",msg->msgId);
            this->socketHelper->subThreadMessageQueue->erase(iter++);
            CC_SAFE_DELETE(msg);
        }
        
        this->socketHelper->subThreadMessageQueue->clear();
        this->socketHelper->subThreadMessageQueueMutex.unlock();

        //------------------------receive---------------------------------------------------------
        fd_set fds;
        FD_ZERO(&fds);
        FD_SET(socketHandle,&fds);
        struct timeval timeout={0,100};
        int selectRet=select(socketHandle+1, &fds, 0, 0, &timeout);
        if(selectRet==0){
            return 0;
        }
        if(selectRet==-1){
            log("error......%d",errno);
        }
        /*
        if(!FD_ISSET(socketHandle, &fds)){
            return 0;
        }
        */
        
        int recvlen = recv(socketHandle,this->buffer,bufferSize,0);
        //CCLOG("thread recvLen:%d",recvlen);
        //flag id name
        if( 0 > recvlen ){ //出错
			CCLOG("ERROR: %d Socket recv msg error!\n", recvlen);
            perror("recv error:");
            NetMsg* msg=new NetMsg();
            msg->msgId=errno;
            if(errno==60){
                msg->type=S_MSG_TO_UITHREAD_CLOSE;
            }else{
                msg->type=S_MSG_TO_UITHREAD_ERROR;
            }
            this->socketHelper->sendMessageToUIThread(msg);

            //s_socket->isConnect=false;
		}else if ( 0 == recvlen ) { //服务器关闭
            CCLOG("server close");
            //s_socket->isConnect=false;
            this->state=State::CLOSED;
		}else{
            int index=0;
            int handleNum=0;
            while (index<recvlen) {
                //flag + len + op + data
                short headLen=2;
                
                short bflag=0;
                short bmsgid=0;
                short blen=0;  //小头

                memcpy(&bflag, this->buffer+index, 2);
                memcpy(&blen, buffer+index+2, 2); //len+msgid+msg
                memcpy(&bmsgid,buffer+index+4,2);
                
                //打小头转换
                bflag=htons(bflag);
                blen=htons(blen);
                bmsgid=htons(bmsgid);
                //log("bflag:%d,len:%d,msgid:%d",bflag,blen,bmsgid);
     
                //超容
                if(blen>bufferSize){
                    char* bigBuffer=new char[blen+headLen];
                    memcpy(bigBuffer, buffer, recvlen);
                    delete[] buffer;
                    buffer=bigBuffer;
                    bufferSize=blen+headLen;
                }
                
                //分包
                while(recvlen-handleNum<blen+headLen) {
                    short rlen=recv(socketHandle,buffer+recvlen,blen+headLen,0);
                    recvlen+=rlen;
                }
                
                //内容
                NetMsg* sm=new NetMsg();
                sm->bytes=new char[blen+1];
                sm->bytes[blen]='\0';
                sm->msgId=bmsgid;
                sm->len=blen;
                sm->type=S_MSG_TO_UITHREAD_MESSAGE;
                memcpy(sm->bytes, buffer+index+headLen+2+2, blen);
                
                this->socketHelper->sendMessageToUIThread(sm);
                
                index+=blen+headLen;
                handleNum+=blen+headLen;
                
                //log("index,recivelen:%d,%d",index,recvlen);

            }
            
            //传完，清空数据
            memset(buffer, 0, bufferSize);
        }
    }
    std::this_thread::sleep_for(std::chrono::milliseconds(100));
    // return 0 to continue the loop
    return 0;
}

void Socket::onSubThreadEnded(){
    
}

void Socket::onUIThreadReceiveMessage(NetMsg* msg){
    
    switch (msg->type) {
        case S_MSG_TO_UITHREAD_MESSAGE:
        {
            if(msg->msgId==S_HEATBEAT){
                CC_SAFE_DELETE(msg);
                break;
            }
            cocos2d::EventCustom evt(NET_MESSAGE);
            evt.setUserData(msg);
            switch (msg->msgId) {
                case C_LOGIN:
                    Manager::getInstance()->setRoleData(msg);
                    break;
                case C_UPROLE:
                    Manager::getInstance()->updateRole(msg);
                    break;
                case C_UPITEM:
                    Manager::getInstance()->updateItems(msg);
                    break;
                case C_UPDATEGATE:
                    Manager::getInstance()->updateGates(msg);
                    break;
                case C_UPDATENODE:
                    Manager::getInstance()->updateNodes(msg);
                    break;
                case C_ADDORREMOVENPC:
                    Manager::getInstance()->addOrRemoveNpc(msg);
                    break;
                default:
                    break;
            }
            if(msg->msgId!=C_UPROLE&&msg->msgId!=C_UPITEM&&msg->msgId!=C_UPDATEGATE&&msg->msgId!=C_UPDATENODE){
                Loading::getInstance()->hide();//服务器推送消息不隐藏loading
            }
            Director::getInstance()->getEventDispatcher()->dispatchEvent(&evt);
            CC_SAFE_DELETE(msg);
        }
            break;
        case S_MSG_TO_UITHREAD_CLOSE:
        {
            //Waiting for the subThread safety exit
            this->socketHelper->joinSubThread();
        }
            break;
        case S_MSG_TO_UITHREAD_ERROR:
        {
            // FIXME: The exact error needs to be checked.
            Socket::ErrorCode err = ErrorCode::CONNECTION_FAILURE;
            this->onError(err);
        }
            break;
        default:
            break;
    }
}
