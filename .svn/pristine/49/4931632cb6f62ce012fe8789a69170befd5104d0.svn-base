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
//#include "RichTextUI.h"
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
    void addChatMsg(int channel, string roleName, const char* chatMsg);
    void touchButtonEvent(Ref *pSender, TouchEventType type);
private:
    void disVisible();
    RichText* richText;
    TextField* txtInput;
    TabBar* tabBar;
};
#endif /* defined(__fancyHeart__chat__) */
