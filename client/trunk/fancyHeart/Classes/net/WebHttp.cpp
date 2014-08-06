//
//  WebHttp.cpp
//  fancyHeart
//
//  Created by zhai on 14-6-16.
//
//

#include "WebHttp.h"
static WebHttp* instance=nullptr;

WebHttp* WebHttp::getInstance()
{
    if(instance==nullptr)
    {
        instance=new WebHttp();
    }
    return instance;
}
void WebHttp::send(string url,const webHttpCallback &callback ,const char* params)
{
    HttpSingle _HttpSingle;
    HttpRequest* request = new HttpRequest();
    request->setUrl(url.c_str());
    request->setRequestType(HttpRequest::Type::POST);
    if(params!=nullptr){
        request->setRequestData(params, strlen(params));
    }
    request->setResponseCallback(CC_CALLBACK_2(WebHttp::onHttpRequestCompleted, this));
    _HttpSingle.currentRequest=request;
    
    _HttpSingle.callback=callback;
    cmdPools.push_back(_HttpSingle);
    if (this->isSending)
    {
        return;
    }
    this->sendSingle();
}
void WebHttp::sendSingle()
{
    if (this->cmdPools.size()==0)
    {
        this->isSending=false;
        return;
    }
    this->isSending=true;
    this->httpSingle=this->cmdPools.at(0);
    Loading::getInstance()->show();
    HttpClient::getInstance()->send(this->httpSingle.currentRequest);
    this->httpSingle.currentRequest->release();
    this->cmdPools.erase(this->cmdPools.begin());
}
void WebHttp::onHttpRequestCompleted(HttpClient *sender, HttpResponse *response)
{
    Loading::getInstance()->hide();
    if (!response)
    {
        return;
    }
    // You can get original request type from: response->request->reqType
    if (0 != strlen(response->getHttpRequest()->getTag()))
    {
        log("%s completed", response->getHttpRequest()->getTag());
    }
    int statusCode = response->getResponseCode();
    char statusString[64] = {};
    sprintf(statusString, "HTTP Status Code: %d, tag = %s", statusCode, response->getHttpRequest()->getTag());
    log("response code: %d", statusCode);
    if (!response->isSucceed())
    {
        log("response failed");
        log("error buffer: %s", response->getErrorBuffer());
        this->sendSingle();
        return;
    }
    // dump data
    std::vector<char> *buffer = response->getResponseData();
    this->httpSingle.callback(buffer);
    this->sendSingle();
}