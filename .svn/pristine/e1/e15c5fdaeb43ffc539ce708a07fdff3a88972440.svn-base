//
//  chat.h
//  fancyHeart
//
//  Created by zhai on 14-7-9.
//
//

#ifndef __fancyHeart__chat__
#define __fancyHeart__chat__

#include <iostream>
#include "cocos2d.h"
#include "Manager.h"
#include "TabBar.h"
using namespace cocos2d;
using namespace ui;
using namespace cocostudio;
class Chat:public BaseUI{
public:
	static Chat* create();
	virtual void onEnter();
    virtual void onExit();
	virtual bool init();
    void show();//显示
    void hide();//隐藏
    void addChatMsg(const char* chatMsg);
    void touchButtonEvent(Ref *pSender, TouchEventType type);
    void textFieldEvent(Ref *pSender, TextField::EventType type);
private:
    void disVisible();
    RichText* richText;
    TextField* txtInput;
    TabBar* tabBar;
    ListView* list;
    Widget* model;
    std::vector<std::string> chats;
    Widget* inputHolder;
    float titleY;
    float contentY;
    void initContent();
};
#endif /* defined(__fancyHeart__chat__) */
