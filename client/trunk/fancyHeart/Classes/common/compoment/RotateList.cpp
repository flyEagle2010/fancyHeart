//
//  RotatList.cpp
//  fancyHeart
//
//  Created by 秦亮亮 on 14-6-30.
//
//

#include "RotateList.h"

RotateList::~RotateList()
{
    this->items.clear();
}

RotateList* RotateList::create()
{
    RotateList* pRet = new RotateList();
    if (pRet && pRet->init()){
        pRet->autorelease();
        return pRet;
    }
    CC_SAFE_DELETE(pRet);
    return nullptr;
}

bool RotateList::init()
{
    if (Widget::init()){
        this->picNum = 0;
        this->addToNum = 0;
        this->tagNumAtMiddle=0;
        this->childFocusCancelOffset=5.0;
        return true;
    }
    return false;
}

void RotateList::onEnter()
{
    Widget::onEnter();
    this->scheduleUpdate();
}

//设置样式:此函数的参数分别是模版，正弦弧度的一个弧度的直线长度，弹窗大小，模版直接间距
void RotateList::setItemModel(Widget* model,float radius,Size panelSize,float itemDistance)
{
    CCASSERT(model,"模板不能为空");
    Size winSize=Director::getInstance()->getWinSize();

    this->panelSize=(panelSize.width<=0 || panelSize.height<=0)?winSize:panelSize;
    this->radius=radius<=0?panelSize.width:radius;
    this->itemDistance=itemDistance<=0?panelSize.width/COMMON_DISTANCE:itemDistance;;
    this->model=model;
    this->model->retain();
    this->model->setTouchEnabled(true);
    this->model->addTouchEventListener(CC_CALLBACK_2(RotateList::touchEvent,this));
    
    this->setSize(panelSize);
    
    //整个显示界面当前放几个模版＋1(整个界面被分成几个平均等份)
    this->haveItemNum=ceil(this->panelSize.width/this->itemDistance);
//    this->showPicNum=haveItemNum+4;
//    this->showPicNum=haveItemNum;

}

//设置数量
void RotateList::setNum(int num)
{
    this->total=num;
    this->picNum = 0;
    this->tagNumAtMiddle = 0;

    int childNum=this->items.size();
    if(childNum > num){
        for(int i=itemNum-num;i<itemNum;i++){
            this->removeChild(this->items.at(i));
        }
    }
    
    for(int i=0;i<num;i++){
        Widget* item=this->model->clone();
        this->addChild(item);
        this->tellIndexEvent(item,i);
        int x =(this->panelSize.width/haveItemNum)*i + this->panelSize.width/2;
        //初始设置位置
        this->setItemTransform(item, x);
        //item->addTouchEventListener(CC_CALLBACK_2(RotateList::touchEvent,this));

        this->items.pushBack(item);
    }
    
    this->resetZOrder();
    
    if (num!=0) {
        this->changeMiddleEvent(this->items.at(tagNumAtMiddle),tagNumAtMiddle);
    }
}

//移动坐标
bool RotateList::scrollChildren(float touchOffsetX, float touchOffsetY)
{
    bool scrollEnd = false;
    auto widgetEnd = this->items.at(items.size()-1);//this->getChildByTag(num-1);
    auto widgetFirst = this->items.at(0);//this->getChildByTag(0);
    this->isBack = false;
 
    //向左移动最多确保最后一个模版在中间位置
    if ((widgetEnd->getPositionX()+touchOffsetX <= this->panelSize.width/2)){
        scrollEnd=true;
        this->isBack = false;
        touchOffsetX = this->panelSize.width/2 - widgetEnd->getPositionX();
//        this->tagNumAtMiddle=this->items.size()-1;
        return true;
    }

    if(widgetFirst->getPositionX()+touchOffsetX >= this->panelSize.width/2){
        scrollEnd=true;
        this->isBack = false;
//        this->tagNumAtMiddle=0;
        touchOffsetX = -this->panelSize.width/2 - widgetEnd->getPositionX();
        return true;
     }
    //改变坐标以及scale
    for (int i = 0; i<this->items.size(); ++i) {
        Widget* widget = this->items.at(i);
        float x =widget->getPositionX()+touchOffsetX;
        
        if(x<0){
            widget->setVisible(false);
        }else{
            widget->setVisible(true);
        }
        this->setItemTransform(widget, x);
//        if(!this->isBack && abs(x-this->panelSize.width/2)<10){
//            this->tagNumAtMiddle=i;
//        }
    }
    this->setMiddleNum();
    this->resetZOrder();
    
    if (this->isBack == false) {
//        this->setMiddleNum();
        this->changeMiddleEvent(this->items.at(tagNumAtMiddle),tagNumAtMiddle);
    }
    this->setSilderPercent();
    return scrollEnd;
}


//释放,并且设置层级关系
void RotateList::handleReleaseLogic(const Vec2 &touchPoint)
{
    //把世界坐标转换到当前节点的本地坐标系
    this->touchEndedPoint = convertToNodeSpace(touchPoint);
    float totalDis = 0.0f;
    totalDis = this->touchEndedPoint.x - this->touchBeganPoint.x;
    
    if (this->slidTime <= 0.016f){
        return;
    }
    //Vec2 dir=totalDis<0?Vec2(-1.0,0):Vec2(1.0,0);
    
    //fabs：求浮点数绝对值
    float startSpeed = MIN(fabs(totalDis)/(slidTime), 1000);//初速度
//    this->startAutoScrollChildrenWithOriginalSpeed(dir, orSpeed, true, -1000);//加速度是固定值－1000
    
    this->stopAutoScrollChildren();
    this->autoScrollDir = totalDis<0?Vec2(-1.0,0):Vec2(1.0,0);;
    this->isAutoScrollSpeedAttenuated = true;
    this->autoScrollOriginalSpeed = startSpeed;
    this->autoScroll = true;
    this->autoScrollAcceleration = -1000;//加速度
    
    this->slidTime = 0.0f;
    this->bePressed = false;
}


void RotateList::stopAutoScrollChildren()
{
    this->autoScroll = false;//先让它停下来
    this->autoScrollOriginalSpeed = 0.0f;
    this->autoScrollAddUpTime = 0.0f;
    this->isAutoScrollSpeedAttenuated=false;
}

//回调函数
void RotateList::changeMiddleEvent(Widget*widget,int middleNum)
{
    if (this->eventCallback) {
        this->eventCallback(EventType::SCROLL_MIDDLE,widget,middleNum);
    }
}

//回调函数，通知index
void RotateList::tellIndexEvent(Widget* item,int num)
{
    if (this->eventCallback) {
        this->eventCallback(EventType::SET_ITEM_DATA,item,num);
    }
}

void RotateList::update(float dt)
{
    if (isAutoScrollSpeedAttenuated){
        //总时间
        float lastTime = autoScrollAddUpTime;
        this->autoScrollAddUpTime += dt;
        //现在速度：初始速度＋加速度＊时间
        float nowSpeed = autoScrollOriginalSpeed + autoScrollAcceleration * autoScrollAddUpTime;
        if (nowSpeed <= 0.0f){
            this->stopAutoScrollChildren();
            //回弹
            if (this->isBack) {
                this->startBounce();
            }
        }else{
            float timeParam = lastTime * 2 + dt;
            //autoScrollOriginalSpeed:初始速度
            //autoScrollAcceleration:加速度
            
            //V0*t+0.5a*t*t
            
            // （初始速度+加速度*总时间）
            
            //总时间=
//            float offset = (autoScrollOriginalSpeed + autoScrollAcceleration * timeParam * 0.5f) * dt;
            float offset=(nowSpeed + autoScrollAcceleration * dt * 0.5) * dt;
            float offsetX = offset * autoScrollDir.x;
            float offsetY = offset * autoScrollDir.y;
            this->endOffsetX = abs(offsetX);
            if (this->scrollChildren(offsetX, offsetY)){
                this->stopAutoScrollChildren();
            }
        }
    }
    
    //手触发屏幕后时间会进行加操作
    if (this->bePressed){
        this->slidTime += dt;
    }
    //加速度完毕后缓动回到正规位置
    if (this->isBackScoll) {
        float flag=this->goDis>0?1:-1;
        this->goDis += 5*flag;

        if(goDis<=0){
            this->isBackScoll=false;
        }else{
            this->scrollChildren(5*flag, 0);
        }
        
    }
}


//回弹
void RotateList::startBounce()
{
    this->isBackScoll = true;
    //查看现在哪个处于中间位置
    Size widgetSize = this->panelSize;
        this->setMiddleNum();
    //再重新设定坐标（确定没有误差）
    //    auto itemPos = widgetSize.width/haveItemNum;//每个模版间距离÷
//    for (int i=0; i<itemNum; ++i) {
//        auto widget = this->getChildByTag(i);
//        this->goDis=widget->getPositionX()-this->panelSize.width/2;
//        if(abs(this->goDis)<30){
//            this->tagNumAtMiddle=i;
//            break;
//        }
//    }
    this->changeMiddleEvent(this->items.at(tagNumAtMiddle),tagNumAtMiddle);
}


void RotateList::checkChildInfo(int handleState,Widget* sender,const Vec2 &touchPoint)
{
    switch (handleState)
    {
        case 0: //begin
        {
            this->touchBeganPoint = convertToNodeSpace(touchPoint);
            //起始移动点
            this->touchMovingPoint = this->touchBeganPoint;
            this->slidTime = 0.0f;
            this->bePressed = true;
            break;
        }
        case 1: //move
        {
            //float offset = (sender->getTouchStartPos() - touchPoint).getLength();
            float offset=sender->getTouchStartPos().x-touchPoint.x;
            if (abs(offset) > this->childFocusCancelOffset){
                sender->setHighlighted(false);
                this->touchMovedPoint = convertToNodeSpace(touchPoint);
                Vec2 delta = this->touchMovedPoint - this->touchMovingPoint;
                //随着移动，起始移动点坐标变化
                this->touchMovingPoint = this->touchMovedPoint;
                this->scrollChildren(delta.x, 0.0f);
            }
        }
            break;
        case 2: //release
            this->handleReleaseLogic(touchPoint);
            break;
    }
}


//设置位置、缩放、旋转
void RotateList::setItemTransform(cocos2d::ui::Widget* widget, float dx)
{
    if(!widget->isVisible()){
        return;
    }
    
    float y=model->getContentSize().height/2*sin(dx/this->radius*3.14);
    widget->setPosition(Vec2(dx,y));
    
    //缩放：越往中心点缩放系数越大
    float dis=abs(this->radius/2-widget->getPositionX());
    float scaleNum=pow(dis/this->radius,0.8);
    widget->setScale(fmax(1-scaleNum, 0.4));
    
    //旋转角度
    int rotation = (180/this->radius/2)*(dx - this->panelSize.width/2);
    widget->setRotation(rotation);
}

void RotateList::setMiddleNum()
{
    //查看现在哪个处于中间位置
    auto dis = this->panelSize.width/haveItemNum;
    for (int i = 0; i<this->items.size(); ++i) {
        auto widget = this->items.at(i);//this->getChildByTag(i);
        int x= widget->getPositionX();
        //改变层级setLocalZOrder 当x坐标在中间图片范围内
        if (x>= (panelSize.width/2-dis/2) && x<=(panelSize.width/2+dis/2)) {
            tagNumAtMiddle = i;
        }
    }
}

void RotateList::touchEvent(cocos2d::Ref *pSender, TouchEventType type)
{
    if (type == TouchEventType::ENDED && this->eventCallback){
        Widget*sprite=static_cast<Widget*>(pSender);
        if (sprite){
            this->curSelectedIndex = this->items.getIndex(sprite);
        }
        if (int(this->curSelectedIndex + addToNum) != tagNumAtMiddle) {
            return;
        }
        Widget* widget=static_cast<Widget*>(pSender);
        this->eventCallback(EventType::TOUCH_ITEM,widget,int(this->curSelectedIndex + addToNum));
    }
}

//更改层级
void RotateList::resetZOrder()
{
    int num=this->items.size();
    for (int i = 0; i<num; ++i) {
        auto widget = this->items.at(i);
        widget->setZOrder(widget->getPositionY());
    }
}

void RotateList::setSlider(Slider* slider)
{
    this->slider = slider;
    //slider->setPercent(0);
    //slider->addEventListener(CC_CALLBACK_2(RotateList::sliderEvent,this));
}

//对slider位置进行移动
void RotateList::setSilderPercent()
{
    //这里也需要对slider位置进行移动
//    int percent = 100*tagNumAtMiddle/this->items.size();
//    this->slider->setPercent(percent);
}

//滚动条到回调函数
void RotateList::sliderEvent(Ref *pSender, Slider::EventType type)
{
    Slider* slider = static_cast<Slider*>(pSender);
    if(type==Slider::EventType::ON_PERCENTAGE_CHANGED){
        //计算滚动到哪一个
        int index = floor(this->items.size()*(slider->getPercent())/100);
        tagNumAtMiddle = index;
        
        float stepLength = (this->items.size()-1)*this->itemDistance*(slider->getPercent())/100 - preDistance;
        this->scrollChildren(-stepLength,0);
        preDistance += stepLength;
        
    }else if(type==Slider::EventType::ON_PERCENTAGE_CHANGE_ENDED){
        this->endOffsetX = 10;
        this->stopAutoScrollChildren();
        this->startBounce();
        this->setSilderPercent();
    }
    this->changeMiddleEvent(this->items.at(tagNumAtMiddle),tagNumAtMiddle);
}

void RotateList::addEventListener(const rotateListCallback& callback)
{
    this->eventCallback = callback;
}

int RotateList::getMiddleIndex()
{
    return tagNumAtMiddle;
}

Vector<Widget*>& RotateList::getItems()
{
    return this->items;
}
