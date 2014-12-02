//
//  GateResult.h
//  fancyHeart
//
//  Created by zhai on 14-6-18.
//
//

#ifndef __fancyHeart__GateResult__
#define __fancyHeart__GateResult__

#include <iostream>
#include "cocos2d.h"
#include "Manager.h"
#include "ui/CocosGUI.h"
#include "cocostudio/CocoStudio.h"
#include "BaseUI.h"
#include "JValue.h"
#include "XGate.h"
#include "GateSelect.h"
#include "Utils.h"
#include "HomeScene.h"
USING_NS_CC;
using namespace ui;
using namespace cocostudio;

class GateResult:public BaseUI
{
public:
	static GateResult* create(BaseUI* delegate,PResultResp pResultResp);
	virtual bool init(std::string fileName,PResultResp pResultResp);
    virtual void resetUI();
    virtual void onExit();
    virtual void onEnter();
private:
    void touchButtonEvent(Ref *pSender, TouchEventType type);
    void touchItemEvent(Ref *pSender, TouchEventType type);
    PResultResp pResultResp;
};
#endif /* defined(__fancyHeart__GateResult__) */
