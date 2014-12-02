//
//  GateSelect.h
//  fancyHeart
//
//  Created by zhai on 14-6-18.
//
//

#ifndef __fancyHeart__GateSelect__
#define __fancyHeart__GateSelect__

#include <iostream>
#include "cocos2d.h"
#include "Manager.h"
#include "ui/CocosGUI.h"
#include "cocostudio/CocoStudio.h"
#include "BaseUI.h"
#include "JValue.h"
#include "XNode.h"
#include "XGate.h"
#include "XBattle.h"
#include "TabBar.h"
#include "GateResult.h"
#include "NewPageView.h"
#include "XBattle.h"
#include "XItem.h"
USING_NS_CC;
using namespace ui;
using namespace cocostudio;

class GateSelect:public BaseUI
{
public:
	static GateSelect* create(BaseUI* delegate,int gateId,int nodeId);
	virtual bool init(std::string fileName,int gateId,int nodeId);
    virtual void onExit();
private:
    int gateId;
    int nodeId;
    void initGroup();//通过组索引，显示相应组数据
    void touchButtonEvent(Ref *pSender, TouchEventType type);
    void touchItemEvent(Ref *pSender, TouchEventType type);
    void pageViewEvent(Ref *pSender, PageView::EventType type);
    void initNetEvent();
    TabBar* tabBar;
    Text* txtGroup;
};
#endif /* defined(__fancyHeart__GateSelect__) */
