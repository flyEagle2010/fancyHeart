//
//  CheckRole.cpp
//  fancyHeart
//
//  Created by doteyplay on 14-8-28.
//
//

#include "CheckRole.h"

CheckRole* CheckRole::create(int roleId)
{
    CheckRole* checkRole=new CheckRole();
    if (checkRole && checkRole->init("publish/checkRole/checkRole.ExportJson",roleId)) {
        checkRole->autorelease();
        return checkRole;
    }
    CC_SAFE_DELETE(checkRole);
    return nullptr;
}

bool CheckRole::init(std::string fileName,int roleId)
{
	if(!BaseUI::init(fileName))
    {
        return false;
    }
    
	//如果需要对cocostudio 设计的ui进行调整
    this->roleId = roleId;
    Widget* container = static_cast<Widget*>(layout->getChildByName("container"));
    Widget* item=static_cast<Widget*>(container->getChildByName("item"));
    ImageView* roleFrame=static_cast<ImageView*>(item->getChildByName("roleFrame"));
    
    ImageView*icon = static_cast<ImageView*>(item->getChildByName("icon"));
    Text* nameLabel = static_cast<Text*>(item->getChildByName("nameLabel"));
    Text* levelLabel = static_cast<Text*>(item->getChildByName("levelLabel"));
    
    Button* returnBtn = static_cast<Button*>(layout->getChildByName("returnBtn"));
    Widget* propertyPanel = static_cast<Widget*>(container->getChildByName("propertyPanel"));
    Widget* progress = static_cast<Widget*>(layout->getChildByName("progress"));
    Button* propInfoBtn = static_cast<Button*>(progress->getChildByName("propInfoBtn"));
    Button* btnCall = static_cast<Button*>(layout->getChildByName("btnCall"));
    returnBtn->addTouchEventListener(CC_CALLBACK_2(CheckRole::touchButtonEvent, this));
    btnCall->addTouchEventListener(CC_CALLBACK_2(CheckRole::touchButtonEvent, this));
    
    XRole*xRole = XRole::record(Value(roleId));
    nameLabel->setString(Value(xRole->getName()).asString());
    //名字的颜色随品质变化
//    nameLabel->setColor(Manager::getInstance()->Qualitys[itemData.quality()].colorLabel);
//    levelLabel->setString(Value(1).asString());//等级显示
    levelLabel->setVisible(false);
    //颜色框
    roleFrame->loadTexture("card_"+Value(Manager::getInstance()->Qualitys[0].color).asString()+".png",TextureResType::LOCAL);
    //站位
    icon->loadTexture("grade_icon_"+Value(xRole->getPos()).asString()+".png");
    
    Text* progressLabel = static_cast<Text*>(progress->getChildByName("progressLabel"));
    //进度条
    LoadingBar* progressBar = static_cast<LoadingBar*>(progress->getChildByName("progressBar"));
    //已有召唤石的数量，到背包中查看,如果为null，说明背包中没有此道具
    PItem* haveProp = Manager::getInstance()->getPropItem(xRole->getPropId());
    int haveNum = haveProp == NULL?0:haveProp->itemnum();
    //需要召唤石的数量
    int needNum =xRole->getCalledNum();
    progressLabel->setString(Value(haveNum).asString()+"/"+Value(needNum).asString());
    //如果已有召唤石数量等于需要召唤石数量，那么进度条消失，召唤石按钮出现
    btnCall->setVisible((haveNum >= needNum)?true:false);
    progress->setVisible((haveNum >= needNum)?false:true);
    if (needNum != 0) {
        progressBar->setPercent(float(haveNum*100/needNum));
    }
    
    //战力
//    FData* fd=FData::create({xRole->getId(),itemData.level,itemData.quality,itemData.star,0,std::vector<int>()});
//    static_cast<Text*>(item->getChildByName("powerLabel"))->setString(StringUtils::format("战力%d",fd->getAttackForce()));
    
    //星级的显示
    for (int i = 1; i<7; i++) {
        ImageView*star = static_cast<ImageView*>(item->getChildByName("star"+Value(i).asString()));
        star->loadTexture(i<=xRole->getStar()?"star_2.png":"star_1.png");
    }
    
    //属性
    XRoleData* xRoleData = XRoleData::record(Value(Value(roleId).asString() +Value(0).asString()));
    if(xRoleData->doc[Value(Value(roleId).asString() +Value(0).asString()).asString().c_str()].IsNull()){
        return true;
    }
    //将属性存储在列表中
    std::vector<PropertysInfo> propertyList;//id列表
    propertyList.push_back(PropertysInfo{"生命 "+Value(xRoleData->getHp()).asString(),"成长 "+Value(xRoleData->getHpRate()).asString()});
    propertyList.push_back(PropertysInfo{"攻击 "+Value(xRoleData->getAtk()).asString(),"成长 "+Value(xRoleData->getAtkRate()).asString()});
    propertyList.push_back(PropertysInfo{"物防 "+Value(xRoleData->getDf()).asString(),"成长 "+Value(xRoleData->getDfRate()).asString()});
    propertyList.push_back(PropertysInfo{"法防 "+Value(xRoleData->getMDf()).asString(),"成长 "+Value(xRoleData->getMDfRate()).asString()});
    propertyList.push_back(PropertysInfo{"闪避 "+Value(xRoleData->getMiss()).asString(),"0"});
    propertyList.push_back(PropertysInfo{"爆击 "+Value(xRoleData->getCrh()).asString(),"0"});
    propertyList.push_back(PropertysInfo{"生命恢复速度 "+Value(xRoleData->getHeal()).asString(),"0"});
    //数据表里如果没信息，则返回
    if (xRoleData == nullptr) {
        return true;
    }
    for (int i = 0; i<7; i++) {
        Widget* widget = static_cast<Widget*>(propertyPanel->getChildByName("qualityItem_"+Value(i).asString()));
        Text* rateLabel = static_cast<Text*>(widget->getChildByName("rateLabel"));
        Widget* smallBottom = static_cast<Widget*>(widget->getChildByName("smallBottom"));
        Text* qualityLabel = static_cast<Text*>(widget->getChildByName("qualityLabel"));
        qualityLabel->setString(propertyList.at(i).propertyNum);
        if (i!=6) {
            rateLabel->setString(propertyList.at(i).growUpNum);
            smallBottom->setVisible(i<4?true:false);
            rateLabel->setVisible(i<4?true:false);
        }
    }
    
	return true;
}

void CheckRole::onEnter()
{
    BaseUI::onEnter();
}

void CheckRole::touchButtonEvent(Ref *pSender, TouchEventType type)
{
    auto btn=static_cast<Button*>(pSender);
    if (!btn||type!=TouchEventType::ENDED) {
        return;
    }
    switch (btn->getTag()) {
        case 20602://返回按钮
            this->clear(true);
            break;
        case 20612://召唤按钮
        {
            //向服务器发送请求
            PCallHero pCallHero;
            pCallHero.set_heroid(this->roleId);
            Manager::getInstance()->socket->send(C_CALLHERO, &pCallHero);
        }
            break;
        default:
            break;
    }
}

void CheckRole::onExit()
{
    BaseUI::onExit();
}