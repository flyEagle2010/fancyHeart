//
//  Role.cpp
//  fancyHeart
//
//  Created by doteyplay on 14-8-12.
//
//

#include "Role.h"

Role* Role::create(PNpc itemData)
{
    Role* role=new Role();
    if (role && role->init("publish/roleItem/roleItem.ExportJson",itemData)) {
        role->autorelease();
        return role;
    }
    CC_SAFE_DELETE(role);
    return nullptr;
}

bool Role::init(std::string fileName,PNpc itemData)
{
	if(!BaseUI::init(fileName))
    {
        return false;
    }
	//如果需要对cocostudio 设计的ui进行调整
    this->propertyPanel = static_cast<Widget*>(layout->getChildByName("propertyPanel"));
    this->skillPanel = static_cast<Widget*>(layout->getChildByName("skillPanel"));
    this->rolePic = static_cast<Widget*>(layout->getChildByName("rolePic"));
    Widget* panel = static_cast<Widget*>(layout->getChildByName("panel"));
    Button* btnReturn = static_cast<Button*>(panel->getChildByName("btnReturn"));
    Button* propInfoBtn = static_cast<Button*>(panel->getChildByName("propInfoBtn"));
    Text* nameLabel = static_cast<Text*>(rolePic->getChildByName("nameLabel"));
    Text* levelLabel = static_cast<Text*>(rolePic->getChildByName("levelLabel"));
    static_cast<Text*>(panel->getChildByName("powerRight"))->setString("Null");
    static_cast<Text*>(this->rolePic->getChildByName("powerLabel"))->setString("Null");
    this->setBtnVisible();
    this->rolePic->setVisible(true);
    
    nameLabel->setString(Value(itemData.npcname()).asString());
    static_cast<Text*>(panel->getChildByName("nameRight"))->setString(Value(itemData.npcname()).asString());
    levelLabel->setString(Value(itemData.level()).asString());
    static_cast<Text*>(panel->getChildByName("levelRight"))->setString(Value(itemData.level()).asString());
    
    btnReturn->setTouchEnabled(true);
    propInfoBtn->setTouchEnabled(true);
    btnReturn->addTouchEventListener(CC_CALLBACK_2(Role::touchBtnEvent, this));
    propInfoBtn->addTouchEventListener(CC_CALLBACK_2(Role::touchBtnEvent, this));
    
    std::vector<Widget*> stars;//星级
    std::vector<Widget*> starsRight;//右边星级
    for (int i=0; i<6; i++) {
        Widget* star=static_cast<Widget*>(rolePic->getChildByName("star"+Value(i+1).asString()));
        stars.push_back(static_cast<Widget*>(star));
        //右边显示的星级
        Widget* star1=static_cast<Widget*>(panel->getChildByName("starRight"+Value(i+1).asString()));
        starsRight.push_back(static_cast<Widget*>(star1));
        if (i <itemData.star()) {
            star->setVisible(true);
            star1->setVisible(true);
        }else{
            star->setVisible(false);
            star1->setVisible(false);
        }
    }
    
    //按钮分别为图鉴，属性，技能，派遣
    std::vector<Button*> buttons;
    std::vector<std::string> btnName={"handbookBtn","qualityBtn","skillBtn","sendBtn"};
    for (std::string name : btnName)
    {
        auto btn=panel->getChildByName(name);
        btn->setTouchEnabled(true);
        btn->addTouchEventListener(CC_CALLBACK_2(Role::touchEvent,this));
        buttons.push_back(static_cast<Button*>(btn));
    }
    tabBar=TabBar::create(buttons);
    tabBar->retain();
    
	return true;
}

void Role::onEnter()
{
    BaseUI::onEnter();
}

void Role::setBtnVisible()
{
    this->propertyPanel->setVisible(false);
    this->skillPanel->setVisible(false);
    this->rolePic->setVisible(false);
}

void Role::touchEvent(Ref *pSender, TouchEventType type)
{
    Button* btn=static_cast<Button*>(pSender);
    if(type!=TouchEventType::ENDED){
        return;
    }
    if (btn->getTag() == 12066) {//图鉴按钮
        //设置按钮选中状态
        tabBar->setIndex(0);
        setBtnVisible();
        this->rolePic->setVisible(true);
    }else if (btn->getTag() == 12067){//属性按钮
        tabBar->setIndex(1);
        setBtnVisible();
        this->skillPanel->setVisible(true);
    }else if (btn->getTag() == 12068){//技能按钮
        tabBar->setIndex(2);
        setBtnVisible();
        this->propertyPanel->setVisible(true);
    }else if (btn->getTag() == 12069){//派遣按钮
        tabBar->setIndex(3);
    }
}

void Role::touchBtnEvent(Ref *pSender, TouchEventType type)
{
    auto btn=static_cast<Button*>(pSender);
    if (!btn) {
        return;
    }
    
    if (btn->getTag() == 12088) {//返回按钮
        this->clear(true);
    }else if (btn->getTag() == 12305){//点击后弹出召唤石获得途径窗口按钮
        
    }
}

void Role::initNetEvent(){
    auto listener = EventListenerCustom::create(NET_MESSAGE, [=](EventCustom* event){
        NetMsg* msg = static_cast<NetMsg*>(event->getUserData());
        switch (msg->msgId)
        {
            case C_LOGIN:
            {
                 //需要存储（例子）
                /*
                PackageLoginResp *pb=new PackageLoginResp();
                pb->ParseFromArray(msg->bytes, msg->len);
                Manager::getInstance()->setMsg(C_LOGIN, pb);
                Manager::getInstance()->switchScence(HomeScene::createScene());
                pb=nullptr;
                */
                /*
                 //不需要存储（例子）
                 PackageLoginResp pb;
                 pb.ParseFromArray(msg->bytes, msg->len);
                 Manager::getInstance()->switchScence(HomeScene::createScene());
                 */
            }
                break;
            default:
                break;
        }
    });
    this->_eventDispatcher->addEventListenerWithFixedPriority(listener,1);
}

void Role::onExit()
{
    BaseUI::onExit();
}