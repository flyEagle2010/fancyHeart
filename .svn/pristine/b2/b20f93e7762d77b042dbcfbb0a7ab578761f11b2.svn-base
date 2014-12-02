//
//  BattleScene.h
//  fancyHeart
//
//  Created by 秦亮亮 on 14-5-5.
//
//

#ifndef __fancyHeart__BattleScene__
#define __fancyHeart__BattleScene__

#include <iostream>
#include "cocos2d.h"
#include "Manager.h"
#include "ui/CocosGUI.h"
#include "cocostudio/CocoStudio.h"
#include "VFighter.h"
#include "external/json/rapidjson.h"
#include "BattleMgr.h"
#include "FData.h"
#include "Clip.h"
#include "GateResult.h"
#include "FighterMgr.h"
#include "SkillIcon.h"

USING_NS_CC;
using namespace ui;
using namespace cocostudio;
class FData;
class FighterMgr;
class SkillIcon;
class BattleScene:public BaseUI{
public:
	static Scene* createScene();
    static BattleScene* create();
	virtual bool init(const char* fileName);
    virtual void onEnter();
    virtual void onExit();
    virtual void initNetEvent();
    virtual void touchButtonEvent(cocos2d::Ref *pSender, Widget::TouchEventType type);
    void interceptTouchEvent(Widget::TouchEventType event, Widget *sender, Touch *touch);

    void resetProgress();
    void setNpcIcon(int num,bool isKill);
    void initNpcIcon(int num);
    SkillIcon* createSkillIcon(int skillID,int pos,FighterMgr* hero);
private:
    int duration=180;
    bool isPause;
public:
    Widget* heroNode;
    Widget* bossInfo;
    Widget* npcInfo;
    Widget* progress;
    Widget* skillNode;
    Widget* top;
    
    
    Sprite* bg;
    void bounceTo(FighterMgr* mf);
    void tick(float dt);
};
#endif /* defined(__fancyHeart__BattleScene__) */
