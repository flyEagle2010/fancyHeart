//
//  WSocket.h
//  FancyHeart
//
//  Created by 秦亮亮 on 14-4-23.
//
//

#ifndef __FancyHeart__WSocket__
#define __FancyHeart__WSocket__

#include <iostream>
#include "cocos2d.h"
#include "extensions/cocos-ext.h"
#include "network/WebSocket.h"
#include <google/protobuf/io/zero_copy_stream_impl.h>
#include <google/protobuf/io/zero_copy_stream.h>
#include <google/protobuf/io/coded_stream.h>
#include <google/protobuf/message.h>
USING_NS_CC;
USING_NS_CC_EXT;
class WSocket:public cocos2d::network::WebSocket::Delegate{
public:
    
   
    
    virtual void onOpen(cocos2d::network::WebSocket* ws);
    virtual void onMessage(cocos2d::network::WebSocket* ws, const cocos2d::network::WebSocket::Data& data);
    virtual void onClose(cocos2d::network::WebSocket* ws);
    virtual void onError(cocos2d::network::WebSocket* ws, const cocos2d::network::WebSocket::ErrorCode& error);
    
//    void send(const cocos2d::network::WebSocket::Data& data);
    
    //void toExtensionsMainLayer(cocos2d::Ref *sender);
    
    // Menu Callbacks
    //void onMenuSendTextClicked(cocos2d::Ref *sender);
    //void onMenuSendBinaryClicked(cocos2d::Ref *sender);

    WSocket();
    virtual ~WSocket();
    void send(short msgId,google::protobuf::Message* msg);
private:
    cocos2d::network::WebSocket* webSocket;
        std::string host;
    int _sendTextTimes;
    int _sendBinaryTimes;

};

#endif /* defined(__FancyHeart__WSocket__) */
