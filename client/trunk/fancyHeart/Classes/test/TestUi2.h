//
//  TestUi2.h
//  fancyHeart
//
//  Created by doteyplay on 14-7-9.
//
//

#ifndef __fancyHeart__TestUi2__
#define __fancyHeart__TestUi2__

#include <iostream>
#include "cocos2d.h"
#include "ui/UILayout.h"
#include "ui/UIScrollInterface.h"
using namespace cocos2d;
using namespace cocos2d::ui;
class TestUi2:public Layout{
//    DECLARE_CLASS_GUI_INFO
public:
	static TestUi2* create();
   
    void setInnerContainerSize(const Size &size);
    virtual void onEnter() override;
    
	virtual bool onTouchBegan(Touch *touch, Event *unusedEvent) override;
    virtual void onTouchMoved(Touch *touch, Event *unusedEvent) override;
    virtual void onTouchEnded(Touch *touch, Event *unusedEvent) override;
    virtual void onTouchCancelled(Touch *touch, Event *unusedEvent) override;
    
    virtual void update(float dt) override;//移动还有角度变化
    
    virtual bool init() override;
    
    virtual void addChild(Node * child) override;
    virtual void addChild(Node* child, int zOrder, int tag) override;
    Layout* getInnerContainer();
    int zOrder;

private:
    
protected:
    Layout* _innerContainer;
    virtual void initRenderer() override;
    virtual bool scrollChildren(float touchOffsetX, float touchOffsetY);//移动坐标
    void handleReleaseLogic(const Vec2 &touchPoint);
    bool _bePressed;
    float _slidTime;
    Vec2 _touchBeganPoint;
    Vec2 _touchMovedPoint;
    Vec2 _touchEndedPoint;
    Vec2 _touchMovingPoint;
    bool _autoScroll;
    Vec2 _autoScrollDir;
    float _autoScrollOriginalSpeed;
    float _autoScrollAcceleration;
    
    float _leftBoundary;
    bool _isAutoScrollSpeedAttenuated;
    float _rightBoundary;
    float _autoScrollAddUpTime;
    void stopAutoScrollChildren();
    void startAutoScrollChildrenWithOriginalSpeed(const Vec2& dir, float v, bool attenuated, float acceleration);
    virtual void checkChildInfo(int handleState,Widget* sender,const Vec2 &touchPoint) override;
    virtual void interceptTouchEvent(int handleState,Widget* sender,const Vec2 &touchPoint);
    float _childFocusCancelOffset;
    void change();

};
#endif /* defined(__fancyHeart__TestUi2__) */
