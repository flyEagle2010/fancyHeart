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
#include "BezierMove.h"
#include "XMonster.h"
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
    virtual void clear(bool isDel);
    virtual void resetUI(){};
    
    void startPre(int groupID,int gateId,int nodeId);
    void startEnd(int groupID,int gateId,int nodeId);
    
private:
    //播放剧情动画 isPre是否是战前动画默认是战前动画如果是战后动画为false
    void playDramaAni(int drameId,bool isPre=true);
	void touchButtonEvent(Ref *pSender, TouchEventType type);
    void touchEvent(Ref *pSender, TouchEventType type);
    //弹出对话框 和隐藏对话框 通过isDisplay判断
    void displayTalk(Node * pSender,bool isDisplay=true,string talk="",int direction=0);
    //人物播放动作
    void displayAction(Node * pSender,string action,int loop=1);
    //播放特效 分两种 人身上还是场景中
    void playEffect(int dramaId);
    //通过p进行坐标转换成实际坐标 x为1-24 y 为 0 1 2 为上中下
    Vec2 getPos(string p);

private: //私有属性
 	BaseUI* preUI;
    //玩家所在层
    Node* heroLayer;
    //特效所在层
    Node* effectLayer;
    //对话文本框
    Text* txtTalk;
    //对话框
    Widget* talkPanel;
    //是否在对话 在点击事件中使用 目的是不在对话中点击事件不生效
    bool isTalk=false;
    void displayDramaId(int dramaId);

};
#endif /* defined(__fancyHeart__DramaAni__) */
