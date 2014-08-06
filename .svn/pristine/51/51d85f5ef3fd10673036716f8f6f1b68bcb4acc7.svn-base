//
//  Package.h
//  fancyHeart
//
//  Created by doteyplay on 14-8-5.
//
//

#ifndef __fancyHeart__Package__
#define __fancyHeart__Package__

#include <iostream>
#include "cocos2d.h"
#include "Manager.h"
#include "BaseUI.h"
#include "XItem.h"
#include "TabBar.h"

using namespace ui;
using namespace cocos2d;
using namespace cocostudio;

class Package:public BaseUI
{
public:
    static Package* create();
	virtual bool init(std::string fileName);
	virtual void onEnter();
	//virtual void onDlgClose(std::string data);

private:
	void initNetEvent();
    void touchEvent(cocos2d::Ref *pSender, TouchEventType type);
    void itemTouchEvent(cocos2d::Ref *pSender, TouchEventType type);
    void getTypeInfo(int type);

private: //私有属性
 	BaseUI* preUI;
    Vector<Widget*> propItems;//道具数组
//    Vector<PItem> propItemData;//道具信息数组
    TabBar* tabBar;
    void setItem(PItem item);//设置单项并且赋予其显示数据
    Widget* propItem;//单个模版
    Text* currentPropNum;//当前道具得数量
    Text* propertyTxt1;//属性描述
    Text* propertyTxt2;
    Text* propertyTxt3;
    Text* propertyTxt4;
    Text* propertyTxt5;
    Text* itemPriceTxt;//出售单价


};
#endif /* defined(__fancyHeart__Package__) */
