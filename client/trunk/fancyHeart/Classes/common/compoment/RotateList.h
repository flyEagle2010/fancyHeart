//
//  RotatList.h
//  fancyHeart
//
//  Created by 秦亮亮 on 14-6-30.
//
//

#ifndef __fancyHeart__RotatList__
#define __fancyHeart__RotatList__
#define COMMON_DISTANCE  6//默认rotateList显示几个模版

#include <iostream>
#include "cocos2d.h"
#include "ui/UILayout.h"
#include "Manager.h"
using namespace cocos2d;
using namespace cocos2d::ui;

class RotateList:public Widget
{
public:
	static RotateList* create();
    virtual void onEnter() override;
    virtual ~RotateList();
    
    enum class EventType
    {
        SCROLL_MIDDLE,
        TOUCH_ITEM,
        SET_ITEM_DATA
    };
    typedef std::function<void(EventType,Widget*,int)> rotateListCallback;
    void addEventListener(const rotateListCallback& callback);
    
    virtual void update(float dt) override;//移动还有角度变化
    virtual bool init() override;
    //设置样式:此函数的参数分别是模版，正弦弧度的一个弧度的直线长度，弹窗大小，模版直接间距
    void setItemModel(Widget* model,float radius=0,Size panelSize = Size(0,0),float itemDistance=0);
    
    Vector<Widget*>& getItems();//得到items
    void setNum(int num);
    void setSlider(Slider* slider);//设置滚动条
    int getMiddleIndex();//获取中间位置模版的index
    
private:
    float goDistance;//触摸移动的横向位移
    int itemNum;//需要制作多少模版

    int tagNumAtMiddle;//在中间的模版的tag值,移动过程中此值为－1

    void startBounce();
    
    void setItemTransform(Widget* item,float dx);
    
    void resetPos(int index);//重新设置坐标

    void changeMiddleEvent(Widget*widget,int middleNum);//移动到中间位置的回调函数
    void tellIndexEvent(Widget* item,int num);
    ssize_t curSelectedIndex;//当前点击的item的index
    int total;
    Size panelSize;//窗口大小
    float itemDistance;//模版间间距
    float radius;//正弦弧度的一个弧度的直线长度
    void resetZOrder();
    void sliderEvent(Ref *pSender, Slider::EventType type);
    void setSilderPercent();
    
    
    
    
protected:
    virtual bool scrollChildren(float touchOffsetX, float touchOffsetY);//移动坐标
    void handleReleaseLogic(const Vec2 &touchPoint);
    bool bePressed;
    float slidTime;
    Vec2 touchBeganPoint;
    Vec2 touchEndedPoint;
    Vec2 touchMovingPoint;

    void interceptTouchEvent(Widget::TouchEventType event, Widget *sender, Touch *touch);

    float childFocusCancelOffset;
    
    Widget* model;//模版
    Vector<Widget*> items;
    rotateListCallback eventCallback;
    bool _bePressed;
    void stopAutoScrollChildren();
    Vec2 autoScrollDir;
    bool isAutoScrollSpeedAttenuated;
    float autoScrollOriginalSpeed;//初始速度
    float autoScrollAcceleration;//加速度
    bool autoScroll;
    float autoScrollAddUpTime;
    int backStep;//自动走得步长
    float backDis;//卡牌需要走x坐标长度
    float endOffsetX;//最后停止前走得x坐标长度
    void setMiddleNum();
    float preDistance;
    Slider* slider;
    
};
#endif /* defined(__fancyHeart__RotateList__) */