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
#include "XCraft.h"
#include "Utils.h"
using namespace ui;
using namespace cocos2d;
using namespace cocostudio;
class Compose:public BaseUI
{
public:
	static Compose* create(BaseUI* delegate=NULL,int composeId=1100104);
	virtual bool init(std::string fileName,int composeId,BaseUI* delegate);
	virtual void onEnter();
	virtual void onExit();
    Widget* getImgBg();

private:
	void initNetEvent();
	void touchEvent(Ref *pSender, TouchEventType type);
    void touchButtonEvent(Ref *pSender, TouchEventType type);
    //点击左边物品事件
    void touchIconEvent(Ref *pSender, TouchEventType type);
    //点击右边分类事件
    void touchItemEvent(Ref *pSender, TouchEventType type);
    //初始化界面
    void initUi(int composeId);
    //创建box 里面有遮罩图片颜色框
    void createIconBox(int itemId,string parentName,int index);
    //移除当前点击index以后的图片
    void removeItemToEnd(int index);
private: //私有属性
    Widget* img_bg;
    //当前要合成的物品id
    int composeId;
    //合成物品道具数量是否不足
    bool isEnough=true;
    //当前索引
    int currentIndex=1;
    BaseUI* delegates;
};
#endif /* defined(__fancyHeart__Compose__) */
