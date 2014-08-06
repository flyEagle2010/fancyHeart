//
//  Confirm.h
//  fancyHeart
//
//  Created by zhai on 14-6-18.
//
//

#ifndef __fancyHeart__Confirm__
#define __fancyHeart__Confirm__

#include <iostream>
#include "cocos2d.h"
#include "Manager.h"
#include "ui/CocosGUI.h"
#include "cocostudio/CocoStudio.h"
#include "BaseUI.h"
#include "JValue.h"
USING_NS_CC;
using namespace ui;
using namespace cocostudio;

class Confirm:public BaseUI
{
public:
	static Confirm* create(BaseUI* delegate,std::string data);
	virtual bool init(std::string fileName);
    virtual void show(BaseUI* preUI,int effectType=0);
    void clear(bool isDel);

private:
	void touchEvent(Ref *pSender, TouchEventType type);
    BaseUI* delegate;
    rapidjson::Document data;
};
#endif /* defined(__fancyHeart__Confirm__) */
