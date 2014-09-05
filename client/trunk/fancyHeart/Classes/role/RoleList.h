//
//  RoleList.h
//  fancyHeart
//
//  Created by doteyplay on 14-8-12.
//
//

#ifndef __fancyHeart__RoleList__
#define __fancyHeart__RoleList__

#include <iostream>
#include "cocos2d.h"
#include "Manager.h"
#include "BaseUI.h"
#include "RotateList.h"
#include "XRole.h"
#include "XItem.h"
#include "Role.h"

using namespace ui;
using namespace cocos2d;
using namespace cocostudio;

class RoleList:public BaseUI
{
public:
	static RoleList* create();
	virtual bool init(std::string fileName);
	virtual void onEnter();
	virtual void onExit();
	//virtual void onDlgClose(std::string data);

private:
	void initNetEvent();
	void touchEvent(Ref *pSender, TouchEventType type);

private: //私有属性
 	BaseUI* preUI;
    RotateList* rotateList;
    void rotateListCallback(RotateList::EventType type,Widget*item,int index);
    Slider* slider;
    void sliderEvent(Ref *pSender, Slider::EventType type);
    void setItemData(Widget*item,PNpc itemData);
    std::vector<Widget*> stars;//星级
    PNpc middleItemData;//当前处于中间的角色信息
    void setBottomData();
    Widget* progress;
    Button* btnCall;

};
#endif /* defined(__fancyHeart__RoleList__) */
