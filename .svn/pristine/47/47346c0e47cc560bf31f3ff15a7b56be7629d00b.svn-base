//
//  HomeScene.h
//  fancyHeart
//
//  Created by 秦亮亮 on 14-5-12.
//
//

#ifndef __fancyHeart__HomeScene__
#define __fancyHeart__HomeScene__

#include <iostream>
#include "cocos2d.h"
#include "Manager.h"
#include "ui/CocosGUI.h"
#include "cocostudio/CocoStudio.h"
//#include "FightScene.h"
#include "CircleMove.h"
#include "Chat.h"
#include "Confirm.h"
#include <map>
#include "Gate.h"
#include "Bag.h"
#include "Formation.h"
#include "RoleList.h"
#include "Compose.h"
#include "RoleAllList.h"
USING_NS_CC;
using namespace ui;
using namespace cocostudio;

class HomeScene:public BaseUI
{
public:
	static Scene* createScene();
    static HomeScene* create();
    virtual void onEnter();
    virtual void onExit();
	virtual bool init(std::string fileName,bool isScence=false);
    void initUi();
private:
    void initNetEvent();
    Layout *comLayout;
    Chat* chat;
    void intAnimation(string plist,string effectName,int fps,int moveSpeed,float scale,Vec2 starP,Vec2 endP);
    void initBuildEffect(string plist,string effectName,string buidName,int fps);
    void touchBuildEvent(Ref *pSender, TouchEventType type);
    void touchButtonEvent(Ref *pSender, TouchEventType type);
    void touchIconEvent(Ref *pSender, TouchEventType type);
    
};

#endif /* defined(__fancyHeart__HomeScene__) */
