//
//  InfoPanel.cpp
//  fancyHeart
//
//  Created by doteyplay on 14-8-20.
//
//

#include "InfoPanel.h"

InfoPanel* InfoPanel::create(int status,PNpc pNpc,PItem*xItem)
{
    InfoPanel* infoPanel=new InfoPanel();
    if (infoPanel && infoPanel->init("publish/infoPanel/infoPanel.ExportJson",status,pNpc,xItem)) {
        infoPanel->autorelease();
        return infoPanel;
    }
    CC_SAFE_DELETE(infoPanel);
    return nullptr;
}

bool InfoPanel::init(std::string fileName,int status,PNpc pNpc,PItem*xItem)
{
	if(!BaseUI::init(fileName))
    {
        return false;
    }
	//如果需要对cocostudio 设计的ui进行调整
    Widget* imgBg = static_cast<Widget*>(layout->getChildByName("imgBg"));
    this->panel1 = static_cast<Widget*>(imgBg->getChildByName("panel1"));
    this->panel2 = static_cast<Widget*>(imgBg->getChildByName("panel2"));
    this->skillFrame = static_cast<Widget*>(imgBg->getChildByName("skillFrame"));
    Widget* iconFrame2 = static_cast<Widget*>(panel1->getChildByName("iconFrame2"));
    Widget* skillFrame = static_cast<Widget*>(panel1->getChildByName("skillFrame"));
    
    this->nameLabel = static_cast<Text*>(imgBg->getChildByName("nameLabel"));
    Text* explainLabel = static_cast<Text*>(skillFrame->getChildByName("explainLabel"));
    Button*sureBtn = static_cast<Button*>(imgBg->getChildByName("sureBtn"));
    sureBtn->addTouchEventListener(CC_CALLBACK_2(InfoPanel::touchBtnEvent, this));
    
    this->setPanelVisible();
    if (status == 0||status == 1) {//升阶
        this->panel1->setVisible(true);
        this->setAscend(status);
    }else if(status == 2){//进化
        this->panel2->setVisible(true);
        this->setEvolve(status);
    }else if(status == 3||status == 4){//技能解锁和升级
        this->skillFrame->setVisible(true);
        setSkill(status);
    }
    
	return true;
}

void InfoPanel::onEnter()
{
    BaseUI::onEnter();
}

void InfoPanel::setPanelVisible(){
    this->panel1->setVisible(false);
    this->panel2->setVisible(false);
    this->skillFrame->setVisible(false);
}
//设置升阶数据
void InfoPanel::setAscend(int status)
{
    Text* defAfterLabel = static_cast<Text*>(this->panel1->getChildByName("defAfterLabel"));
    Text* label1 = static_cast<Text*>(defAfterLabel->getChildByName("label1"));
    Text* label2 = static_cast<Text*>(defAfterLabel->getChildByName("label2"));
    Text* label3 = static_cast<Text*>(defAfterLabel->getChildByName("label3"));
    Text* label4 = static_cast<Text*>(defAfterLabel->getChildByName("label4"));
    Text* atkLabel = static_cast<Text*>(defAfterLabel->getChildByName("atkLabel"));
    Text* atkAfterLabel = static_cast<Text*>(defAfterLabel->getChildByName("atkAfterLabel"));
    Text* hpLabel = static_cast<Text*>(defAfterLabel->getChildByName("hpLabel"));
    Text* hpAfterLabel = static_cast<Text*>(defAfterLabel->getChildByName("hpAfterLabel"));
    Text* defLabel = static_cast<Text*>(defAfterLabel->getChildByName("defLabel"));
    Text* mDefLabel = static_cast<Text*>(defAfterLabel->getChildByName("mDefLabel"));
    Text* mDefAfterLabel = static_cast<Text*>(defAfterLabel->getChildByName("mDefAfterLabel"));
    Widget* skillFrame = static_cast<Widget*>(defAfterLabel->getChildByName("skillFrame"));
    if(status == 0){//0:品阶等级等于1、3、6、9
        skillFrame->setVisible(true);
    }else if(status == 1){//1:品阶等级等于2、4、5、7、8
        skillFrame->setVisible(false);
    }
    this->nameLabel->setString("升阶成功");
}
//设置进化数据
void InfoPanel::setEvolve(int status)
{
    this->nameLabel->setString("进化成功");
    Widget* frame1 = static_cast<Widget*>(this->panel2->getChildByName("frame1"));
    Widget* frame2 = static_cast<Widget*>(this->panel2->getChildByName("frame2"));
    Widget* heroIcon1 = static_cast<Widget*>(this->panel2->getChildByName("heroIcon1"));
    Widget* heroIcon2 = static_cast<Widget*>(this->panel2->getChildByName("heroIcon2"));
}
//设置技能解锁和升阶界面
void InfoPanel::setSkill(int status)
{
    if(status == 3){
        this->nameLabel->setString("觉醒技解锁");
    }else if (status == 4){
       this->nameLabel->setString("技能升级");
    }
    Widget* skillIcon = static_cast<Widget*>(this->skillFrame->getChildByName("skillIcon"));
}

void InfoPanel::touchEvent(Ref *pSender, TouchEventType type)
{
    switch (type)
    {
        case TouchEventType::BEGAN:
            break;
        case TouchEventType::MOVED:
            break;
        case TouchEventType::ENDED:
            break;
        case TouchEventType::CANCELED:
            break;
        default:
            break;
    }
}

void InfoPanel::touchBtnEvent(Ref *pSender, TouchEventType type)
{
    Button* btn=static_cast<Button*>(pSender);
    if (!btn) {
        return;
    }
    
    if (btn->getTag() == 12725) {//确定按钮
        this->clear(true);
    }
}

void InfoPanel::initNetEvent(){
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

void InfoPanel::onExit()
{
    BaseUI::onExit();
}