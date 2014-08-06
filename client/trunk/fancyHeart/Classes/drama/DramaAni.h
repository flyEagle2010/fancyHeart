//
//  DramaAni.h
//  fancyHeart
//
//  Created by 秦亮亮 on 14-7-30.
//
//

#ifndef __fancyHeart__DramaAni__
#define __fancyHeart__DramaAni__

#include <iostream>
#include "cocos2d.h"
#include "Manager.h"
#include "BaseUI.h"
#include "Hero.h"
#include "XDrama.h"
#include "Clip.h"
using namespace ui;
using namespace cocos2d;
using namespace cocostudio;

class DramaAni:public BaseUI
{
public:
    static DramaAni* getInstance();
	virtual bool init();
	virtual void onEnter();
	virtual void onExit();
	virtual void show(BaseUI* preUI,int effectType=0);
    virtual void clear(bool isDel);
    virtual void resetUI(){};
    
    void startPre(int groupID);
    void startEnd(int groupID);

private:
	void touchButtonEvent(Ref *pSender, TouchEventType type);
    void touchEvent(Ref *pSender, TouchEventType type);
    void displayTalk(Node * pSender,bool isDisplay=true);
    void displayAction(Node * pSender,string action,int loop=1);
    void startBattle();
    void playEffect(int effectId);

private: //私有属性
 	BaseUI* preUI;
    Node* heroLayer;
    Node* effectLayer;
    Text* txtTalk;
    Widget* talkPanel;
    bool isTalk=false;

};
#endif /* defined(__fancyHeart__DramaAni__) */
