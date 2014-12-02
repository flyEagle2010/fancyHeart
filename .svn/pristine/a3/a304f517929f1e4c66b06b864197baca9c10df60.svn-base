//
//  EquipInfo.cpp
//  fancyHeart
//
//  Created by doteyplay on 14-8-20.
//
//

#include "EquipInfo.h"

EquipInfo* EquipInfo::create(int index,XItem* xItem,int64 heroId,int posId,BaseUI* delebag)
{
    EquipInfo* equipInfo=new EquipInfo();
    if (equipInfo && equipInfo->init("publish/equipPosInfo/equipPosInfo.ExportJson",index,xItem,heroId,posId,delebag)) {
        equipInfo->autorelease();
        return equipInfo;
    }
    CC_SAFE_DELETE(equipInfo);
    return nullptr;
}

bool EquipInfo::init(std::string fileName,int index,XItem* xItem,int64 heroId,int posId,BaseUI* delebag)
{
	if(!BaseUI::init(fileName))
    {
        return false;
    }
	//如果需要对cocostudio 设计的ui进行调整
    layout->addTouchEventListener(CC_CALLBACK_2(EquipInfo::touchEvent, this));
    this->heroId = heroId;
    this->currentXItem = xItem;
    this->itemId = xItem->getId();
    this->posId = posId;
    this->role = delebag;
    this->imgBg = static_cast<Widget*>(layout->getChildByName("imgBg"));
    Text* nameLabel = static_cast<Text*>(this->imgBg->getChildByName("nameLabel"));
    nameLabel->setString(Value(xItem->getName()).asString());
    nameLabel->setColor(Manager::getInstance()->Qualitys[xItem->getRate()].colorLabel);
    //人物头像颜色框
    ImageView* icon=static_cast<ImageView*>(imgBg->getChildByName("icon"));
    icon->loadTexture("frame_"+Value(Manager::getInstance()->Qualitys[xItem->getRate()].color).asString()+".png");
    PItem*item = Manager::getInstance()->getPropItem(xItem->getId());
    int num = item == nullptr?0:item->itemnum();
    static_cast<Text*>(this->imgBg->getChildByName("haveNumLabel"))->setString(Value(num).asString());
    Text*qualityLabel = static_cast<Text*>(this->imgBg->getChildByName("qualityLabel"));
    Button*bottomBtn = static_cast<Button*>(this->imgBg->getChildByName("btn"));
    ImageView* itemIcon = static_cast<ImageView*>(this->imgBg->getChildByName("itemIcon"));
    itemIcon->loadTexture("item_"+Value(xItem->getIcon()).asString()+".png");//后加
    bottomBtn->addTouchEventListener(CC_CALLBACK_2(EquipInfo::touchBtnEvent, this));
    bottomBtn->removeFromParent();
    Vec2 p=this->imgBg->convertToWorldSpace(bottomBtn->getPosition());
    this->addChild(bottomBtn, 2);
    
    bottomBtn->setPosition(p);
    //属性的显示
    vector<string> propertyData;
    if (xItem->getCrh()!= 0) {
        propertyData.push_back("暴击:"+Value(xItem->getCrh()).asString());
    }
    if (xItem->getMiss()!= 0) {
        propertyData.push_back("闪避:"+Value(xItem->getMiss()).asString());
    }
    if (xItem->getDef() != 0) {
        propertyData.push_back("物防:"+Value(xItem->getDef()).asString());
    }
    if (xItem->getMDef() != 0) {
        propertyData.push_back("法防:"+Value(xItem->getMDef()).asString());
    }
    if (xItem->getAtk() != 0) {
        propertyData.push_back("攻击:"+Value(xItem->getAtk()).asString());
    }
    if (xItem->getHp() != 0) {
        propertyData.push_back("生命:"+Value(xItem->getHp()).asString());
    }
    if (xItem->getHeal() != 0) {
        propertyData.push_back("生命恢复速度:"+Value(xItem->getHeal()).asString());
    }
    int propertyLen = fmin(5, propertyData.size());
    string str;
    for (int i = 0; i<propertyLen; i++) {
        str += propertyData.at(i)+"\n";
    }
    qualityLabel->setString(str);
    
    bottomBtn->setEnabled(true);
    bottomBtn->setColor(Color3B::WHITE);
    static_cast<Text*>(this->imgBg->getChildByName("label1"))->setString(Value(xItem->getDes()).asString());
    Text*label2 = static_cast<Text*>(this->imgBg->getChildByName("label2"));
    if (index == 1) {//无装备
        label2->setString("装备后会与该英雄绑定");
        bottomBtn->setTitleText("合成公式");
    }else if (index == 2){//已装备
        label2->setString("需求英雄等级："+Value(xItem->getMaxLv()).asString());
        bottomBtn->setTitleText("确定");
    }else if (index == 3){//可合成
        label2->setString("装备后会与该英雄绑定");
        bottomBtn->setTitleText("合成公式");
    }else if (index == 4){//可装备
        label2->setString("装备后会与该英雄绑定");
        bottomBtn->setTitleText("装备");
    }else if (index == 5){//未装备
        label2->setString("需求英雄等级："+Value(xItem->getMaxLv()).asString());
        bottomBtn->setTitleText("装备");
        bottomBtn->setEnabled(false);
        bottomBtn->setColor(Color3B::GRAY);
    }
    this->statusIndex = index;
    
	return true;
}

void EquipInfo::onEnter()
{
    BaseUI::onEnter();
}

void EquipInfo::touchBtnEvent(Ref *pSender, TouchEventType type)
{
    auto btn=static_cast<Button*>(pSender);
    if(type!=TouchEventType::ENDED){
        return;
    }
    switch (btn->getTag()) {
        case 12650://下面的按钮
            if (this->statusIndex == 1|| this->statusIndex == 3) {//合成公式——弹出合成界面
                Compose*compose = Compose::create(this,this->currentXItem->getId());
                compose->show(this,3);
                int commonNum = (Director::getInstance()->getWinSize().width-this->imgBg->getContentSize().width-compose->getImgBg()->getContentSize().width-10)/2;
                MoveTo* move = MoveTo::create(0.2, Vec2(commonNum+this->imgBg->getContentSize().width/2,this->imgBg->getPositionY()));
                this->imgBg->runAction(move);
                MoveTo* move1 = MoveTo::create(0.2, Vec2(commonNum+this->imgBg->getContentSize().width+10+compose->getImgBg()->getContentSize().width/2,compose->getImgBg()->getPositionY()));
                compose->getImgBg()->runAction(move1);
                
                
                Button* bottomBtn = static_cast<Button*>(this->getChildByName("btn"));
                MoveTo* moveButton = MoveTo::create(0.2, Vec2(commonNum+this->imgBg->getContentSize().width/2,bottomBtn->getPositionY()));
                bottomBtn->runAction(moveButton);
                bottomBtn->setEnabled(false);
                
            }else if (this->statusIndex == 4){//可装备——点击则角色穿上装备
                //向服务器发送请求——给角色穿装备
                PWearEquip pWearEquip;
                pWearEquip.set_heroid(this->heroId);
                pWearEquip.set_posid(this->posId);
                Manager::getInstance()->socket->send(C_WEAREQUIP, &pWearEquip);
            }else if(this->statusIndex == 5){//未装备——则反馈消息“需求英雄等级为XX”
                Manager::getInstance()->showMsg("需求英雄等级为"+Value(this->currentXItem->getMaxLv()).asString());
            }else{//确定
                this->clear(true);
            }
            break;
            
        default:
            break;
    }
}

void EquipInfo::touchEvent(Ref *pSender, TouchEventType type)
{
    if (type!=TouchEventType::ENDED) {
        return;
    }
    if (this->imgBg->hitTest(static_cast<Layout*>(pSender)->getTouchStartPos())) {
        return;
    }
    this->clear(true);
    
}

void EquipInfo::initNetEvent(){
    auto listener = EventListenerCustom::create(NET_MESSAGE, [=](EventCustom* event){
        NetMsg* msg = static_cast<NetMsg*>(event->getUserData());
        switch (msg->msgId)
        {
            case C_COMMONMSG:
            {
                PCommonResp pCommonResp;
                pCommonResp.ParseFromArray(msg->bytes, msg->len);
                if(pCommonResp.resulttype()==C_WEAREQUIP){
                    //status:0成功
                    if (pCommonResp.status()==0) {
                        //itemId
//                        Manager::getInstance()->showMsg("");
                        this->clear(true);
                    }else{
//                        Manager::getInstance()->showMsg("");
                    }
                }
            }
                break;
            case C_UPITEM://更新
            {
                PItem*item = Manager::getInstance()->getPropItem(this->itemId);
                auto num = item == nullptr?0:item->itemnum();
                static_cast<Text*>(this->imgBg->getChildByName("haveNumLabel"))->setString(Value(num).asString());
                if (num>0) {
                    Button*bottomBtn = static_cast<Button*>(this->getChildByName("btn"));
                    bottomBtn->setTitleText("装备");
                    this->statusIndex = 4;
                    bottomBtn->setColor(this->statusIndex == 5?Color3B::GRAY:Color3B::WHITE);
                    bottomBtn->setEnabled(this->statusIndex == 5?false:true);
                }
            }
            default:
                break;
        }
    });
    Director::getInstance()->getEventDispatcher()->addEventListenerWithSceneGraphPriority(listener, this);
}

void EquipInfo::onExit()
{
    BaseUI::onExit();
}