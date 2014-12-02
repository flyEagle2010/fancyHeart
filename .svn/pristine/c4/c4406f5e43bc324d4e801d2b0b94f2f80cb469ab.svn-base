//
//  GateMap.h
//  fancyHeart
//
//  Created by zhai on 14-6-18.
//
//

#ifndef __fancyHeart__GateMap__
#define __fancyHeart__GateMap__

#include <iostream>
#include <map>
#include "cocos2d.h"
#include "Manager.h"
#include "ui/CocosGUI.h"
#include "cocostudio/CocoStudio.h"
#include "BaseUI.h"
#include "JValue.h"
#include "XGate.h"
#include "XNode.h"
#include "GateSelect.h"
USING_NS_CC;
using namespace ui;
using namespace cocostudio;

class GateMap:public BaseUI
{
public:
	static GateMap* create(BaseUI* delegate,int gateId);
	virtual bool init(std::string fileName,int gateId);
    virtual void resetUI(int gateId);
    virtual void onEnter();
    virtual void onExit();
private:
	void touchEvent(Ref *pSender, TouchEventType type);
    void touchButtonEvent(Ref *pSender, TouchEventType type);
    Node* getTouchSprite(Vec2 pos);//通过点击坐标获取点中的节点
    PGateItem* gateItem;
    Map<int, Node*> sprites;//地图上的所有可点击的节点
};
#endif /* defined(__fancyHeart__GateMap__) */
