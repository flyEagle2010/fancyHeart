//
//  RotatList.cpp
//  fancyHeart
//
//  Created by 秦亮亮 on 14-6-30.
//
//

#include "RotateList.h"

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
        this->tagNumAtMiddle=0;
        this->childFocusCancelOffset=5.0;
        return true;
    }
    return false;
}

void RotateList::onEnter()
{
    Widget::onEnter();
    this->setAnchorPoint(Vec2(0,0));
    this->setTouchEnabled(true);
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
    this->setSize(panelSize);
    
    //整个显示界面当前放几个模版＋1(整个界面被分成几个平均等份)
    this->itemNum=floor(this->panelSize.width/this->itemDistance);
}

//设置数量
void RotateList::setNum(int num)
{
    this->total=num;
    this->tagNumAtMiddle = 0;

    int childNum=this->items.size();
    if(childNum > num){
        for(int i=0;i<childNum-num;i++){
            this->removeChild(this->items.at(childNum-i-1));
            this->items.popBack();
        }
    }
    
    for(int i=childNum;i<num;i++){
        Widget* item=this->model->clone();
        this->addChild(item);
        this->items.pushBack(item);
    }
    //设置位置
    for (int j = 0; j<this->items.size(); j++) {
        int x =(this->panelSize.width/itemNum)*j + this->panelSize.width/2;
        this->setItemTransform(this->items.at(j), x);
        this->tellIndexEvent(this->items.at(j),j);
        this->items.at(j)->setTouchEnabled(true);
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
    auto widgetEnd = this->items.at(items.size()-1);
    auto widgetFirst = this->items.at(0);
 
    //向左移动最多确保最后一个模版在中间位置
    if ((widgetEnd->getPositionX()+touchOffsetX <= this->panelSize.width/2)){
        scrollEnd=true;
        touchOffsetX = this->panelSize.width/2 - widgetEnd->getPositionX();
        return true;
    }

    if(widgetFirst->getPositionX()+touchOffsetX >= this->panelSize.width/2){
        scrollEnd=true;
        touchOffsetX = -this->panelSize.width/2 - widgetEnd->getPositionX();
        return true;
     }
    //改变坐标以及scale
    for (int i = 0; i<this->items.size(); ++i) {
        Widget* widget = this->items.at(i);
        float x =widget->getPositionX()+touchOffsetX;
        this->setItemTransform(widget, x);
    }
    this->resetZOrder();

    if(this->slider){
        this->setSilderPercent();
    }
    return scrollEnd;
}

void RotateList::stopAutoScrollChildren()
{
    this->autoScroll = false;//先让它停下来
    this->autoScrollOriginalSpeed = 0.0f;
    this->autoScrollAddUpTime = 0.0f;
    this->isAutoScrollSpeedAttenuated=false;
}

void RotateList::update(float dt)
{
    //手触发屏幕后时间会进行加操作
    if (this->bePressed){
        this->slidTime += dt;
    }
    
    if (isAutoScrollSpeedAttenuated){
        //总时间
        //float lastTime = autoScrollAddUpTime;
        this->autoScrollAddUpTime += dt;
        //现在速度：初始速度＋加速度＊时间
        float nowSpeed = autoScrollOriginalSpeed + autoScrollAcceleration * autoScrollAddUpTime;
        if (nowSpeed <= 0.0f){
            this->stopAutoScrollChildren();
            //回弹
            this->startBounce();
        }else{
            //float offset = (autoScrollOriginalSpeed + autoScrollAcceleration * timeParam * 0.5f) * dt;
            float offset=(nowSpeed + autoScrollAcceleration * dt * 0.5) * dt;
            float offsetX = offset * autoScrollDir.x;
            float offsetY = offset * autoScrollDir.y;
            this->endOffsetX = abs(offsetX);
            if (this->scrollChildren(offsetX, offsetY)){
                this->stopAutoScrollChildren();
            }
        }
    }
  
    //加速度完毕后缓动回到正规位置
    if (abs(this->backDis) > 0) {
        float flag=this->backDis>0?1:-1;
        this->backDis -= 5*flag;
        if((flag>=0 && backDis<=0 ) || (flag<=0 && backDis>=0)){
            this->backDis=0;
            this->scrollChildren(backDis, 0);
        }else{
            this->scrollChildren(5*flag, 0);
        }
    }
}

//回弹
void RotateList::startBounce()
{
    //查看现在哪个处于中间位置
    this->setMiddleNum();
    this->backDis=this->panelSize.width/2-this->items.at(tagNumAtMiddle)->getPositionX();
    this->changeMiddleEvent(this->items.at(tagNumAtMiddle),tagNumAtMiddle);
}

//void RotateList::checkChildInfo(int handleState,Widget* sender,const Vec2 &touchPoint)
void RotateList::interceptTouchEvent(Widget::TouchEventType event, Widget *sender, Touch *touch)
{
    switch (event)
    {
        case TouchEventType::BEGAN: //begin
        {
            this->touchBeganPoint = touch->getLocationInView();//convertToNodeSpace(touchPoint);
            //起始移动点
            this->touchMovingPoint = this->touchBeganPoint;
            this->slidTime = 0.0f;
            this->bePressed = true;
            break;
        }
        case TouchEventType::MOVED: //move
        {
            float offset=sender->getTouchStartPos().x-touch->getLocation().x;
            if (abs(offset) > this->childFocusCancelOffset){
                sender->setHighlighted(false);
                Vec2 currentPoint = touch->getLocationInView();//this->convertToNodeSpace(touchPoint);
                Vec2 delta = currentPoint - this->touchMovingPoint;
                this->touchMovingPoint=currentPoint;
                this->scrollChildren(delta.x, 0.0f);
            }
            break;
        }
        case TouchEventType::ENDED: //release
        {
            if(touchBeganPoint.distance(touch->getLocationInView())<10 && this->items.getIndex(sender)==this->tagNumAtMiddle && this->eventCallback){
                this->eventCallback(EventType::TOUCH_ITEM,sender,this->tagNumAtMiddle);
            }
            this->handleReleaseLogic(touch->getLocation());
            break;
        }
    }
}

//释放,并且设置层级关系
void RotateList::handleReleaseLogic(const Vec2 &touchPoint)
{
    //把世界坐标转换到当前节点的本地坐标系
    this->touchEndedPoint = convertToNodeSpace(touchPoint);
    float totalDis = this->touchEndedPoint.x - this->touchBeganPoint.x;
    
    if (this->slidTime <= 0.016f){
        return;
    }
    
    //fabs：求浮点数绝对值
    float startSpeed = MIN(fabs(totalDis)/(this->slidTime), 1000);//初速度
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

//设置位置、缩放、旋转
void RotateList::setItemTransform(cocos2d::ui::Widget* widget, float dx)
{
    float y=model->getContentSize().height/2*sin(dx/this->radius*3.14);
    widget->setPosition(Vec2(dx,y));
    
    //缩放：越往中心点缩放系数越大
    float dis=abs(this->radius/2-widget->getPositionX());
    
    float scaleNum=pow(dis/this->radius,0.8);
    widget->setScale(fmax(1-scaleNum, 0.4));
    //widget->setScale(dis<5?1:0.5);
    
    //float color=fmax(1-scaleNum, 0.4);
    //widget->setColor(Color3B(255*color,255*color,255*color));
    //旋转角度
    int rotation = (180/this->radius/2)*(dx - this->panelSize.width/2);
    widget->setRotation(rotation);
}

void RotateList::setMiddleNum()
{
    //查看现在哪个处于中间位置
    auto dis = this->panelSize.width/itemNum;
    for (int i = 0; i<this->items.size(); ++i) {
        auto widget = this->items.at(i);//this->getChildByTag(i);
        int x= widget->getPositionX();
        //改变层级setLocalZOrder 当x坐标在中间图片范围内
        if (x>= (panelSize.width/2-dis/2) && x<=(panelSize.width/2+dis/2)) {
            tagNumAtMiddle = i;
            break;
        }
    }
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
    
    this->slider->setPercent(0);
    this->slider->addEventListener(CC_CALLBACK_2(RotateList::sliderEvent,this));
}

//滚动条到回调函数
void RotateList::sliderEvent(Ref *pSender, Slider::EventType type)
{
    Slider* slider = static_cast<Slider*>(pSender);
    if(type==Slider::EventType::ON_PERCENTAGE_CHANGED){
        //计算滚动到哪一个
        int index = (this->items.size())*(slider->getPercent())/100.0;
        this->tagNumAtMiddle = index;
        
        float stepLength = this->items.size()*this->itemDistance*(slider->getPercent())/100 - preDistance;
        this->scrollChildren(-stepLength,0);
        preDistance += stepLength;
    }
//    if(type==Slider::EventType::ON_PERCENTAGE_CHANGE_ENDED){
//        this->endOffsetX = 10;
//        this->stopAutoScrollChildren();
//        this->startBounce();
//        this->setSilderPercent();
//        this->changeMiddleEvent(this->items.at(tagNumAtMiddle),tagNumAtMiddle);
//    }
}

void RotateList::addEventListener(const rotateListCallback& callback)
{
    this->eventCallback = callback;
}

//对slider位置进行移动
void RotateList::setSilderPercent()
{
    //这里也需要对slider位置进行移动
    int percent = 100*tagNumAtMiddle/this->items.size();
    this->slider->setPercent(percent);
}

int RotateList::getMiddleIndex()
{
    return tagNumAtMiddle;
}

Vector<Widget*>& RotateList::getItems()
{
    return this->items;
}

RotateList::~RotateList()
{
    this->items.clear();
    this->model->release();
}