//
//  Compose.h
//  fancyHeart
//
//  Created by doteyplay on 14-8-20.
//
//

#ifndef __fancyHeart__Compose__
#define __fancyHeart__Compose__

#include <iostream>
#include "cocos2d.h"
#include "Manager.h"
#include "BaseUI.h"

using namespace ui;
using namespace cocos2d;
using namespace cocostudio;

class Compose:public BaseUI
{
public:
	static Compose* create();
	virtual bool init(std::string fileName);
	virtual void onEnter();
	virtual void onExit();
	//virtual void onDlgClose(std::string data);

private:
	void initNetEvent();
	void touchEvent(Ref *pSender, TouchEventType type);

private: //私有属性
 	BaseUI* preUI;

};
#endif /* defined(__fancyHeart__Compose__) */
