//
//  WebHttp.h
//  fancyHeart
//
//  Created by zhai on 14-6-16.
//
//

#ifndef __fancyHeart__WebHttp__
#define __fancyHeart__WebHttp__

#include <iostream>

#endif /* defined(__fancyHeart__WebHttp__) */
#include "cocos2d.h"
#include "network/HttpClient.h"
#include "Loading.h"
using namespace cocos2d;
using namespace cocos2d::network;
using namespace std;
typedef std::function<void(std::vector<char> *)> webHttpCallback;
typedef struct _HttpSingle {
	HttpRequest* currentRequest;
    webHttpCallback callback;;
} HttpSingle;
class WebHttp
{
public:
    static WebHttp* getInstance();
    void send(string url,const webHttpCallback &callback,const char* params=nullptr);
private:
    vector<HttpSingle> cmdPools;//发送请求对象池
    HttpSingle httpSingle;//当前请求
    bool isSending=false;//是否正在发送请求
    void sendSingle();//发送一条请求
    void onHttpRequestCompleted(HttpClient *sender, HttpResponse *response);//Http Response Callback
};