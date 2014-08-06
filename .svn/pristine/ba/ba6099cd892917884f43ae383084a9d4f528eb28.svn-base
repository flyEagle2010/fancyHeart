//
//  TestUi2.cpp
//  fancyHeart
//
//  Created by doteyplay on 14-7-9.
//
//

#include "TestUi2.h"

TestUi2* TestUi2::create()
{
	TestUi2* widget = new TestUi2();
    if (widget && widget->init())
    {
        widget->autorelease();
        return widget;
    }
    CC_SAFE_DELETE(widget);
    return nullptr;
}

void TestUi2::onEnter()
{
    Layout::onEnter();
    scheduleUpdate();
}


void TestUi2::initRenderer()
{
    Layout::initRenderer();
    _innerContainer = Layout::create();
    Layout::addChild(_innerContainer,1,1);
}

bool TestUi2::init()
{
    if (Layout::init())
    {
        zOrder = 11;//记录总共显示多少个模版数量，以便用来显示层级
        _innerContainer->setTouchEnabled(false);
        return true;
    }
    return false;
}

void TestUi2::addChild(Node *child)
{
    Layout::addChild(child);
}

void TestUi2::addChild(Node *child, int zOrder, int tag)
{
    _innerContainer->addChild(child, zOrder, tag);
}

//设置内部容器宽高
void TestUi2::setInnerContainerSize(const Size &size)
{
    _innerContainer->setSize(Size(size.width, size.height));
}

//移动坐标
bool TestUi2::scrollChildren(float touchOffsetX, float touchOffsetY)//
{
    bool scrollenabled = true;
    
    auto rightBoundary = _size.width;//宽的边界
    
    float realOffset = touchOffsetX;
    float icRightPos = _innerContainer->getRightInParent();
    
    if (icRightPos + touchOffsetX <= rightBoundary)
    {
        realOffset = rightBoundary - icRightPos;
        scrollenabled = false;
    }
    float icLeftPos = _innerContainer->getLeftInParent();
    if (icLeftPos + touchOffsetX >= _leftBoundary)
    {
        realOffset = _leftBoundary - icLeftPos;
        scrollenabled = false;
    }
    
    auto moveChildPoint = _innerContainer->getPosition() + Vec2(realOffset,0);//Vec2(offsetX, offsetY);
    _innerContainer->setPosition(moveChildPoint);
    //改变坐标以及scale
    change();
    
    return scrollenabled;
}
//释放
void TestUi2::handleReleaseLogic(const Vec2 &touchPoint)
{
    //把世界坐标转换到当前节点的本地坐标系
    _touchEndedPoint = convertToNodeSpace(touchPoint);
    
    if (_slidTime <= 0.016f)
        
    {
        return;
    }
    float totalDis = 0.0f;
    Vec2 dir;
    
    totalDis = _touchEndedPoint.x - _touchBeganPoint.x;
    if (totalDis < 0.0f)
    {
        dir = Vec2(-1.0f, 0.0f);;
    }
    else
    {
        dir = Vec2(1.0f, 0.0f);;
    }
    //fabs：求浮点数绝对值
    float orSpeed = MIN(fabs(totalDis)/(_slidTime), 1000);//初速度
    startAutoScrollChildrenWithOriginalSpeed(dir, orSpeed, true, -1000);//加速度是固定值－1000
    
    _slidTime = 0.0f;
    
    _bePressed = false;
}

bool TestUi2::onTouchBegan(Touch *touch, Event *unusedEvent)
{
    bool pass = Layout::onTouchBegan(touch, unusedEvent);
    _touchBeganPoint = convertToNodeSpace(_touchStartPos);
    //起始移动点
    _touchMovingPoint = _touchBeganPoint;
    _slidTime = 0.0f;
    _bePressed = true;
    return pass;
}

void TestUi2::onTouchMoved(Touch *touch, Event *unusedEvent)
{
    Layout::onTouchMoved(touch, unusedEvent);
    _touchMovedPoint = convertToNodeSpace(_touchMovePos);

    Vec2 delta = _touchMovedPoint - _touchMovingPoint;
    //随着移动，起始移动点坐标变化
    _touchMovingPoint = _touchMovedPoint;
    
    scrollChildren(delta.x, 0.0f);
}

void TestUi2::onTouchEnded(Touch *touch, Event *unusedEvent)
{
    Layout::onTouchEnded(touch, unusedEvent);
    handleReleaseLogic(_touchEndPos);
}

void TestUi2::onTouchCancelled(Touch *touch, Event *unusedEvent)
{
    Layout::onTouchCancelled(touch, unusedEvent);
    handleReleaseLogic(touch->getLocation());
}

void TestUi2::update(float dt)
{
    if (_autoScroll)
    {
        if (_isAutoScrollSpeedAttenuated)
        {
            //总时间
            float lastTime = _autoScrollAddUpTime;
            _autoScrollAddUpTime += dt;
            //现在速度：初始速度＋加速度＊时间
            float nowSpeed = _autoScrollOriginalSpeed + _autoScrollAcceleration * _autoScrollAddUpTime;
            if (nowSpeed <= 0.0f)
            {
                stopAutoScrollChildren();
            }
            else
            {
                float timeParam = lastTime * 2 + dt;
                //_autoScrollOriginalSpeed:初始速度
                //_autoScrollAcceleration:加速度
                float offset = (_autoScrollOriginalSpeed + _autoScrollAcceleration * timeParam * 0.5f) * dt;
                float offsetX = offset * _autoScrollDir.x;
                float offsetY = offset * _autoScrollDir.y;
                if (!scrollChildren(offsetX, offsetY))
                {
                    stopAutoScrollChildren();
                }
            }
        }
        
    }
    //手触发屏幕后时间会进行加操作
    if (_bePressed)
    {
        _slidTime += dt;
    }
    
}

void TestUi2::startAutoScrollChildrenWithOriginalSpeed(const Vec2& dir, float v, bool attenuated, float acceleration)
{
    stopAutoScrollChildren();
    _autoScrollDir = dir;
    _isAutoScrollSpeedAttenuated = attenuated;
    _autoScrollOriginalSpeed = v;
    _autoScroll = true;
    _autoScrollAcceleration = acceleration;//加速度
}

void TestUi2::stopAutoScrollChildren()
{
    _autoScroll = false;//先让它停下来
    _autoScrollOriginalSpeed = 0.0f;
    _autoScrollAddUpTime = 0.0f;
}

void TestUi2::checkChildInfo(int handleState,Widget* sender,const Vec2 &touchPoint)
{
    interceptTouchEvent(handleState, sender, touchPoint);
}

void TestUi2::interceptTouchEvent(int handleState, Widget *sender, const Vec2 &touchPoint)
{
    switch (handleState)
    {
        case 0:
            _touchBeganPoint = convertToNodeSpace(touchPoint);
            //起始移动点
            _touchMovingPoint = _touchBeganPoint;
            _slidTime = 0.0f;
            _bePressed = true;
            break;
            
        case 1:
        {
            float offset = (sender->getTouchStartPos() - touchPoint).getLength();
            if (offset > _childFocusCancelOffset)
            {
                sender->setHighlighted(false);
                _touchMovedPoint = convertToNodeSpace(touchPoint);
                
                Vec2 delta = _touchMovedPoint - _touchMovingPoint;
                //随着移动，起始移动点坐标变化
                _touchMovingPoint = _touchMovedPoint;
                
                scrollChildren(delta.x, 0.0f);
            }
        }
            break;
            
        case 2:
            handleReleaseLogic(touchPoint);
            break;

    }
}

Layout* TestUi2::getInnerContainer()
{
    return _innerContainer;
}

void TestUi2::change()
{
    Size widgetSize=Director::getInstance()->getWinSize();
    
    for (int i = 0; i<10; ++i) {
        auto widget = _innerContainer->getChildByTag(i);
        //设置位置
        int x =widget->getPositionX() + _innerContainer->getPositionX();
        widget->setPosition(Vec2(widget->getPositionX(),widgetSize.height*sin(x*(3.1415926/widgetSize.width))/2));
        
        //缩放
        float wholePosX = _innerContainer->getPositionX() + widget->getPositionX();
        if ( wholePosX< widgetSize.width/2) {//如果图片在屏幕中央左边
            float scaleNum = wholePosX/(widgetSize.width/2)+1/4;
            widget->setScale(scaleNum);
        }else if (wholePosX > widgetSize.width/2){//如果图片在屏幕中央右边
            float scaleNum = (widgetSize.width - wholePosX)/(widgetSize.width/2)+1/4;
            widget->setScale(scaleNum);
        }
        //旋转角度
        int rotation = (180/widgetSize.width/2)*(x - widgetSize.width/2);
        widget->setRotation(rotation);
        //层级setLocalZOrder 当x坐标在中间图片范围内
        if (x>= (widgetSize.width/2-widget->getContentSize().width/4) && x<=(widgetSize.width/2+widget->getContentSize().width/4)) {
            ++zOrder;
            widget->setLocalZOrder(zOrder);
        }
        
    }
}

