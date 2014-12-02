#ifndef __fancyHeart__Gate__
#define __fancyHeart__Gate__

#include <iostream>
#include "cocos2d.h"
#include "Manager.h"
#include "RotateList.h"
#include "XGate.h"
#include "GateMap.h"
#include "TabBar.h"
USING_NS_CC;
using namespace cocos2d;
using namespace ui;
using namespace cocostudio;
class Gate : public BaseUI
{
public:
    static Gate* create();
    virtual bool init(std::string fileName);
    virtual void onEnter();
    virtual void onExit();
    
private:
    
    void touchEvent(cocos2d::Ref *pSender, TouchEventType type);
    RotateList* rotateList;
    Slider* slider;
    PGateResp gateResp;
    bool getList(int type);
    void setItemData(Widget*item,PGateItem gateItem);
    TabBar* tabBar;
    void rotateListCallback(RotateList::EventType type,Widget*item,int index);
    Text* nameLabel;
    Text* desLabel;
    void setMiddleData(PGateItem gateItem);
    int currentType;
};

#endif // __HELLOWORLD_SCENE_H__
