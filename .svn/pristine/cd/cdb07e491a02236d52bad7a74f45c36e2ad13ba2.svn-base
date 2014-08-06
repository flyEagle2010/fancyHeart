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

    static cocos2d::Scene* createScene();
    static Gate* create();
    virtual bool init(std::string fileName);
    virtual void onEnter();
    virtual void update(float dt) override;
//    virtual void onExit();
    
private:
    
    void touchEvent(cocos2d::Ref *pSender, TouchEventType type);
    RotateList* rotateList;
    void sliderEvent(Ref *pSender, Slider::EventType type);
    int middleIndex;//上次在中间位置的序列数
    bool boo;//是否第一次刷新
    Slider* slider;
    PGateResp gateResp;
    void getList(int type);
    void setItemData(Widget*item,PGateItem gateItem);
    void setData();
    void setVis(Widget*item,bool boo);
    TabBar* tabBar;
    void rotateListCallback(Ref* sender,RotateList::EventType type);
    int middleId;//中间卡牌的id
    //模版对应的id数组
};

#endif // __HELLOWORLD_SCENE_H__
