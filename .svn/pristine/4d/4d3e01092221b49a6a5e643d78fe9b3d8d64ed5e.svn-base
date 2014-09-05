//
//  Role.h
//  fancyHeart
//
//  Created by doteyplay on 14-8-12.
//
//

#ifndef __fancyHeart__Role__
#define __fancyHeart__Role__

#include <iostream>
#include "cocos2d.h"
#include "Manager.h"
#include "BaseUI.h"
#include "TabBar.h"

using namespace ui;
using namespace cocos2d;
using namespace cocostudio;

class Role:public BaseUI
{
public:
	static Role* create(PNpc itemData);
	virtual bool init(std::string fileName,PNpc itemData);
	virtual void onEnter();
	virtual void onExit();

private:
	void initNetEvent();
	void touchEvent(Ref *pSender, TouchEventType type);
    void touchBtnEvent(Ref *pSender, TouchEventType type);

private: //私有属性
 	BaseUI* preUI;
    Widget* propertyPanel;
    Widget* skillPanel;
    Widget* rolePic;
    TabBar* tabBar;
    void setBtnVisible();

};
#endif /* defined(__fancyHeart__Role__) */
