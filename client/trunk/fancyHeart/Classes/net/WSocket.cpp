//
//  WSocket.cpp
//  FancyHeart
//
//  Created by 秦亮亮 on 14-4-23.
//
//

#include "WSocket.h"
#include <sys/types.h>
#include <sys/socket.h>
#include <arpa/inet.h>
WSocket::WSocket()
{
    this->webSocket = new network::WebSocket();
    //192.168.1.69:2888
//    if (!ws->init(*this, "ws://echo.websocket.org"))
    std::string host="ws://192.168.1.69:2888";
    if(!this->webSocket->init(*this,host))
    {
        CC_SAFE_DELETE(this->webSocket);
    }
}

void WSocket::send(short msgId,google::protobuf::Message* msg)
{
    return;
    /*
    SearchRequest sr;
    sr.set_page_number(31);
    sr.set_query("wahaha");
    sr.set_result_per_page(100);
    */
    char buffer[msg->ByteSize()+6+1];
    
    //headflag 3637(short) msglen(short) msgnum(short) msg
    //htonl(3637);
    int flag=htons(3637);
    memset(buffer,flag,2);  //
    memset(buffer+2, msg->ByteSize()+2+2, 2);
    memset(buffer+2, msgId, 2);
    
    
    //    google::protobuf::io::ArrayOutputStream* out;
    google::protobuf::io::ZeroCopyOutputStream* rawOut=new google::protobuf::io::ArrayOutputStream(buffer+6,msg->ByteSize());
    google::protobuf::io::CodedOutputStream* codeOut=new google::protobuf::io::CodedOutputStream(rawOut);
    msg->SerializeToCodedStream(codeOut);
    
//    this->webSocket->send(buffer, sizeof(buffer));
    this->webSocket->send((const unsigned char*)buffer, sizeof(buffer));
    
}

// Delegate methods
void WSocket::onOpen(network::WebSocket* ws)
{
    log("Websocket (%p) opened", ws);
}

void WSocket::onMessage(network::WebSocket* ws, const network::WebSocket::Data& data)
{
/*
    {
        _sendBinaryTimes++;
        char times[100] = {0};
        sprintf(times, "%d", _sendBinaryTimes);
        
        std::string binaryStr = "response bin msg: ";
        
        for (int i = 0; i < data.len; ++i) {
            if (data.bytes[i] != '\0')
            {
                binaryStr += data.bytes[i];
            }
            else
            {
                binaryStr += "\'\\0\'";
            }
        }
        
//        binaryStr += std::string(", ")+times;ß
//        log("%s", binaryStr.c_str());
//        _sendBinaryStatus->setString(binaryStr.c_str());
    }
 
 */
    log("get data from server!");
}

void WSocket::onClose(network::WebSocket* ws)
{
    log("websocket instance (%p) closed.", ws);
    ws=NULL;
    // Delete websocket instance.
    CC_SAFE_DELETE(ws);
}

void WSocket::onError(network::WebSocket* ws, const network::WebSocket::ErrorCode& error)
{
    log("Error was fired, error code: %d", error);
}

WSocket::~WSocket()
{
    if(this->webSocket)
    {
        this->webSocket->close();
    }
}