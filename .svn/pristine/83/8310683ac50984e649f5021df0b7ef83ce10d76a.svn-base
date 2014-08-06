//
//  Socket.h
//  fancyHeart
//
//  Created by 秦亮亮 on 14-5-5.
//
//

#ifndef __fancyHeart__Socket__
#define __fancyHeart__Socket__

#include <iostream>
#include <stdio.h>
#include <stdlib.h>
#include <sys/socket.h>
#include <sys/errno.h>
#include <string.h>
#include <arpa/inet.h>
#include <unistd.h>
#include "signal.h"
#include <fcntl.h>

#include "string.h"
#include "pthread.h"
#include "netdb.h"
#include "extensions/cocos-ext.h"

#include <google/protobuf/io/zero_copy_stream_impl.h>
#include <google/protobuf/io/zero_copy_stream.h>
#include <google/protobuf/io/coded_stream.h>
#include <google/protobuf/message.h>
#include "Loading.h"
#include "message.pb.h"
#include "MsgID.h"

USING_NS_CC;
#define NET_MESSAGE "netMessage"
class SocketThreadHelper;
class Delegate;

class NetMsg{
public:
    ~NetMsg(){
        delete bytes;
    }
    short type;
    short msgId;
    short len;
    char* bytes;
};

class Socket{
    
public:
	Socket();
	~Socket();
	
    
    /**
     *  Websocket state
     */
    enum class State
    {
        CONNECTING,
        OPEN,
        CLOSING,
        CLOSED,
    };
    
    /**
     *  @brief Errors in websocket
     */
    enum class ErrorCode
    {
        TIME_OUT,
        CONNECTION_FAILURE,
        UNKNOWN,
    };
    
public:
    virtual void onSubThreadStarted();
    virtual  int onSubThreadLoop();
    virtual void onSubThreadEnded();
    virtual void onUIThreadReceiveMessage(NetMsg* msg);
    
    void init(std::string ip,int port);
	void send(short msgId,google::protobuf::Message* msg,bool isShowLoading=true);
	void close();
    void onError(const ErrorCode& error) {};
    
private:
    std::string  ip;
    unsigned int port;
    State state;
    ssize_t currentDataLen;
    char *currentData;
    int socketHandle=0;
    
    friend class socketThreadHelper;
    SocketThreadHelper* socketHelper;
    Delegate* delegate;
    int bufferSize=1024*4;
    
    char* buffer;
};
#endif /* defined(__fancyHeart__Socket__) */
