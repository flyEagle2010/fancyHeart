//
//  BaseUI.h
//  fancyHeart
//
//  Created by 秦亮亮 on 14-6-12.
//
//

#ifndef __fancyHeart__BaseUI__
#define __fancyHeart__BaseUI__

#include <iostream>
#include "cocos2d.h"
#include "ui/CocosGUI.h"
#include "cocostudio/CocoStudio.h"
#include "Manager.h"
#include "Mask.h"
using namespace cocostudio;

using namespace cocos2d;
using namespace ui;
class BaseUI:public Layout {
public:
    //type1确定2取消
    typedef std::function<void(Ref*, rapidjson::Value &data,int type)> baseUiCallback;
    //baseUiCallback eventCallback;
    //void addEventListener(const baseUiCallback& callback);
    virtual void initNetEvent(){};
    virtual void onDlgClose(rapidjson::Value &data);
    virtual bool init(std::string fileName,bool isScence=false);
    //effectType 0是当前层有黑色背景，但是没有背景图片 1当前层有背景图片
    virtual void show(BaseUI* preUI,int effectType=0);
    virtual void show(int effectType=0);//当前层没有背景
    virtual void clear(bool isDel);
    virtual void touchEvent(Ref *pSender, Widget::TouchEventType type){};
    virtual void resetUI(){};
    Layout* layout;
    
public:
    virtual void onEnter();
    virtual void onExit();
    virtual void reRestUI(){};
public:
    BaseUI* preUI;
};
#endif /* defined(__fancyHeart__BaseUI__) */
