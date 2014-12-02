
#include "Gate.h"
#include "HomeScene.h"

Gate* Gate::create()
{
    Gate* gate=new Gate();
    if (gate && gate->init("publish/gate/gate.ExportJson")) {
        gate->autorelease();
        return gate;
    }
    CC_SAFE_DELETE(gate);
    return nullptr;
}

bool Gate::init(std::string fileName)
{
    if(!BaseUI::init(fileName))
    {
        return false;
    }
    this->gateResp=Manager::getInstance()->getRoleData()->gate();//服务器给的数据
    
    Widget* bottom=static_cast<Widget*>(layout->getChildByName("bottom"));
    this->nameLabel = static_cast<Text*>(bottom->getChildByName("nameLabel"));
    this->desLabel = static_cast<Text*>(bottom->getChildByName("desLabel"));
    //模版
    Widget* item=static_cast<Widget*>(layout->getChildByName("item"));
    item->removeFromParent();
    Size widgetSize = Director::getInstance()->getWinSize();
    this->rotateList = RotateList::create();
    this->rotateList->setSize(Size(widgetSize.width, widgetSize.height));
    this->rotateList->addEventListener(CC_CALLBACK_3(Gate::rotateListCallback,this));
    //传进去单个模版
    this->rotateList->setItemModel(item,widgetSize.width,widgetSize,113.6);//widgetSize.width/9);
    this->addChild(this->rotateList);
    float y =widgetSize.height-item->getContentSize().height;
    this->rotateList->setPosition(Vec2(0,y));
    
    //滚动条
    this->slider=static_cast<Slider*>(bottom->getChildByName("slider"));
    this->slider->getVirtualRenderer()->setContentSize(Size(474,slider->getVirtualRenderer()->getContentSize().height));
    this->rotateList->setSlider(this->slider);
    
    //按钮分别为剧情，精英，活动，密镜，返回
    std::vector<Button*> buttons;
    std::vector<std::string> btnName={"storyBtn","outstandBtn","activeBtn","mirrorBtn"};
    for (std::string name : btnName)
    {
        Button* btn=static_cast<Button*>(bottom->getChildByName(name));
        btn->setTouchEnabled(true);
        btn->addTouchEventListener(CC_CALLBACK_2(Gate::touchEvent,this));
        buttons.push_back(btn);
    }
    tabBar=TabBar::create(buttons);
    tabBar->retain();
    for (int i=0; i<buttons.size(); ++i) {
        bool isEnableTouch = getList(-buttons.at(i)->getTag());
        buttons.at(i)->setTouchEnabled(isEnableTouch);
        buttons.at(i)->setColor(isEnableTouch?Color3B::WHITE:Color3B::GRAY);
    }
    Button* backBtn=static_cast<Button*>(layout->getChildByName("backBtn"));
    backBtn->addTouchEventListener(CC_CALLBACK_2(Gate::touchEvent,this));
    
    //默认获取剧情的数据，并且设置高亮选中状态
    this->currentType = 1;
    this->getList(this->currentType-1);
    
    Label* name = static_cast<Label*>(nameLabel->getVirtualRenderer());
    name->enableOutline(Color4B::BLACK,1);
    Label* des = static_cast<Label*>(desLabel->getVirtualRenderer());
    des->enableOutline(Color4B::BLACK,1);
    
    return true;
}

void Gate::onEnter()
{
    if (Manager::getInstance()->gateId!=0) {
        GateMap* gateMap=GateMap::create(this, Manager::getInstance()->gateId);
        gateMap->show(this);
    }
    Layout::onEnter();
}

bool Gate::getList(int type)
{
    int typeNum = 0;
    for (int i = 0; i<this->gateResp.gates_size(); ++i) {
        //判断type是多少，需要从总数据中进行筛选
        PGateItem gateItem= this->gateResp.gates(i);
        XGate* xg=XGate::record(Value(gateItem.gateid()));
        if (xg->getType() == type+1) {//不是显示全部
            typeNum ++;
        }
    }
    //如果当前tab里面的数据个数为0，则不让其点击进入此项
    if (typeNum == 0) {
        return false;
    }
    this->rotateList->setNum(typeNum);
    //设置按钮选中状态
    tabBar->setIndex(type);
    return true;
}

void Gate::rotateListCallback(RotateList::EventType type,Widget*item,int index)
{
    switch (type)
    {
        case RotateList::EventType::SCROLL_MIDDLE:
        {
            this->setMiddleData(this->gateResp.gates(index));
            break;
        }
        case RotateList::EventType::TOUCH_ITEM:
        {
            PGateItem gateItem= this->gateResp.gates(index);
            //获得当前被点击对象的id
            int gateid = gateItem.gateid();
            GateMap* gateMap=GateMap::create(this, gateid);
            gateMap->show(this);
            break;
        }
        case RotateList::EventType::SET_ITEM_DATA:
        {
            PGateItem gateItem= this->gateResp.gates(index);
            this->setItemData(item,gateItem);//传入数据
            break;
        }
        default:
            break;
    }
}

void Gate::setMiddleData(PGateItem gateItem)
{
    XGate* xg=XGate::record(Value(gateItem.gateid()));
    this->nameLabel->setString(xg->getName());
    //显示解锁条件或描述，解锁前是条件解锁后是描述
    this->desLabel->setString(gateItem.islock()?xg->getDesc():xg->getDesc2());
}

//设置单个模版的数据显示
void Gate::setItemData(Widget*item,PGateItem gateItem)
{
    Text*desTxt=static_cast<Text*>(item->getChildByName("desTxt"));
    Label* desLabel = static_cast<Label*>(desTxt->getVirtualRenderer());
    desLabel->enableOutline(Color4B::BLACK,1);
    //此处需要判断是否有倒计时，没有倒计时就隐藏此文本，否则具体显示倒计时（策划说以后会有个事件表，从里面读取时间）
    
    ImageView* items = static_cast<ImageView*>(item);
    items->loadTexture("Level Select_Book_A"+Value(this->currentType).asString()+".png");
}

void Gate::touchEvent(cocos2d::Ref *pSender, TouchEventType type)
{
    Button* btn=static_cast<Button*>(pSender);
    if(type!=TouchEventType::ENDED){
        return;
    }
    if (btn->getTag() == 10272) {//返回按钮
        this->clear(true);
        return;
    }
    Button* button=static_cast<Button*>(pSender);
    this->currentType = -button->getTag();
    this->getList(this->currentType-1);
}

void Gate::onExit()
{
    tabBar->release();
    BaseUI::onExit();
}
