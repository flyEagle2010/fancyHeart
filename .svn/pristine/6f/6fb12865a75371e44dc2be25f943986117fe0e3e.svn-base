//
//  Role.cpp
//  fancyHeart
//
//  Created by doteyplay on 14-8-12.
//
//

#include "Role.h"

Role* Role::create(int spriteId)
{
    Role* role=new Role();
    if (role && role->init("publish/roleInfo/roleInfo.ExportJson",spriteId)) {
        role->autorelease();
        return role;
    }
    CC_SAFE_DELETE(role);
    return nullptr;
}

bool Role::init(std::string fileName,int spriteId)
{
    if(!BaseUI::init(fileName))
    {
        return false;
    }
    LoginResp* loginResp = Manager::getInstance()->getRoleData();
    for (int i = 0; i<loginResp->npclist_size(); i++) {
        if (loginResp->npclist(i).spriteid() == spriteId) {
            this->itemData = loginResp->npclist(i);
            break;
        }
    }
    
    //如果需要对cocostudio 设计的ui进行调整
    this->imgBg = static_cast<Widget*>(layout->getChildByName("img_bg"));
    this->propertyPanel = static_cast<Widget*>(imgBg->getChildByName("propertyPanel"));
    this->skillPanel = static_cast<Widget*>(imgBg->getChildByName("skillPanel"));
    this->rolePic = static_cast<ImageView*>(imgBg->getChildByName("rolePic"));
    this->equipPanel = static_cast<Widget*>(imgBg->getChildByName("equipPanel"));
//    Button* btnReturn = static_cast<Button*>(imgBg->getChildByName("btnReturn"));
    Button* btnReturn = static_cast<Button*>(layout->getChildByName("btnReturn"));
    this->progress = static_cast<Widget*>(imgBg->getChildByName("progress"));
    Button* propInfoBtn = static_cast<Button*>(this->progress->getChildByName("propInfoBtn"));
    this->changeBtn = static_cast<Button*>(imgBg->getChildByName("changeBtn"));
    this->changeBtn->addTouchEventListener(CC_CALLBACK_2(Role::touchBtnEvent, this));
    this->desc = static_cast<Text*>(imgBg->getChildByName("desc"));
    btnReturn->setTouchEnabled(true);
    propInfoBtn->setTouchEnabled(false);//先将此按钮置灰并且不可点击
    propInfoBtn->setColor(Color3B::GRAY);
    btnReturn->addTouchEventListener(CC_CALLBACK_2(Role::touchBtnEvent, this));
    propInfoBtn->addTouchEventListener(CC_CALLBACK_2(Role::touchBtnEvent, this));
    static_cast<Button*>(this->equipPanel->getChildByName("btnAscend"))->addTouchEventListener(CC_CALLBACK_2(Role::touchBtnEvent, this));
    //设置左边栏属性的显示
    this->setBtnVisible();
    this->rolePic->setVisible(true);
    this->desc->setVisible(false);
    
    static_cast<ImageView*>(this->rolePic)->loadTexture("card_"+Value(itemData.spriteid()).asString()+".png");
    
    //按钮分别为图鉴，属性，技能，派遣
    std::vector<Button*> buttons;
    std::vector<std::string> btnName={"handbookBtn","qualityBtn","skillBtn","sendBtn"};
    for (std::string name : btnName)
    {
        Button* btn=static_cast<Button*>(imgBg->getChildByName(name));
        btn->setTouchEnabled(name == "sendBtn"?false:true);
        btn->setColor(name == "sendBtn"?Color3B::GRAY:Color3B::WHITE);
        btn->addTouchEventListener(CC_CALLBACK_2(Role::touchEvent,this));
        buttons.push_back(static_cast<Button*>(btn));
    }
    tabBar=TabBar::create(buttons);
    tabBar->retain();

    this->setData();
    //给技能添加监听
    for (int i=7; i>=0; i--) {
        ImageView* skill=static_cast<ImageView*>(this->skillPanel->getChildByName("skill"+Value(i+1).asString()));
        skill->addTouchEventListener(CC_CALLBACK_2(Role::touchSkillItemEvent, this));
        skill->setEnabled(true);
        
    }
    
    return true;
}

void Role::onEnter()
{
    BaseUI::onEnter();
}

void Role::setData()
{
    PNpc* pNpc=Manager::getInstance()->getNpc(this->itemData.npcid());
    XRole* xRole=XRole::record(Value(this->itemData.spriteid()));
    Widget* info = static_cast<Text*>(this->imgBg->getChildByName("info"));
    //战力的显示(根据策划需求先写null，以后会有公式来算这个值)
    FData* fd=FData::create({xRole->getId(),pNpc->level(),pNpc->quality(),pNpc->star(),0,std::vector<int>()});
    static_cast<Text*>(this->imgBg->getChildByName("powerRight"))->setString(StringUtils::format("战力%d",fd->getAttackForce()));
    static_cast<Text*>(this->rolePic->getChildByName("powerLabel"))->setString(StringUtils::format("战力%d",fd->getAttackForce()));
    
    Text* nameLabel = static_cast<Text*>(rolePic->getChildByName("nameLabel"));
    Text* nameRight = static_cast<Text*>(info->getChildByName("nameRight"));
    static_cast<Text*>(rolePic->getChildByName("levelLabel"))->setString("LV."+Value(this->itemData.level()).asString());
    static_cast<Text*>(info->getChildByName("levelRight"))->setString("LV."+Value(this->itemData.level()).asString());
    //设置星级
    this->setStar();
    
    nameLabel->setString(Value(this->itemData.npcname()).asString());
    nameLabel->setColor(Manager::getInstance()->Qualitys[this->itemData.quality()].colorLabel);
    nameRight->setString(Value(this->itemData.npcname()).asString());
    nameRight->setColor(Manager::getInstance()->Qualitys[this->itemData.quality()].colorLabel);
    
    ImageView*posPic = static_cast<ImageView*>(this->rolePic->getChildByName("posPic"));
    ImageView*posPicRight = static_cast<ImageView*>(info->getChildByName("posPicRight"));
    //职业标识
//    XRole*xRole = XRole::record(Value(this->itemData.spriteid()));
    posPic->loadTexture("hero_cardframe_"+Value(xRole->getProfessionSign()+5).asString()+".png");
    posPicRight->loadTexture("profession_icon_"+Value(xRole->getProfessionSign()).asString()+".png");
    
    //人物颜色框
    ImageView* roleFrame=static_cast<ImageView*>(this->rolePic->getChildByName("roleFrame"));
    roleFrame->loadTexture("hero_cardframe_"+Value(Manager::getInstance()->Qualitys[this->itemData.quality()].color).asString()+".png");///
    
    //经验
    LoadingBar*expProgressBar = static_cast<LoadingBar*>(info->getChildByName("expProgressBar"));
    int exp = this->itemData.exp();
    int level = this->itemData.level();
    for(int i = 1;i<this->itemData.level();i++){
        exp -= XExp::record(Value(level-i))->getExp();
    }
    XExp*xExp = XExp::record(Value(this->itemData.level()));
    static_cast<Text*>(info->getChildByName("rateRight"))->setString(Value(exp).asString() +"/"+ Value(xExp->getExp()).asString());
    //进度条进度
    expProgressBar->setPercent(float(exp*100/xExp->getExp()));
    
    this->changeBtn->setVisible(false);
    
    //装备面板是否显示
    this->equipPanel->setVisible(this->itemData.quality() >= 8?false:true);//
    //如果角色是主角，则界面右下方的召唤石进度，进化，变异按钮均不显示
    if(xRole->getIsRole() == 1){
        this->progress->setVisible(false);
        this->desc->setVisible(false);
        return;
    }
    
    //装备
    for (int m=0; m<6; m++) {
        Sprite*sp = Sprite::create();
        this->equips.push_back(sp);
    }
    //设置装备位装备的排列
    this->setEquips();
    
    //设置进度条进度以及按钮的显示
    this->setShowProgress();
    
}
//设置进度条进度以及按钮的显示
void Role::setShowProgress()
{
    this->desc->setVisible(false);
    XRole*xRole = XRole::record(Value(this->itemData.spriteid()));
    //召唤石数量
    Text* rateLabel = static_cast<Text*>(progress->getChildByName("rateLabel"));
    LoadingBar* progressBar = static_cast<LoadingBar*>(this->progress->getChildByName("progressBar"));
    XRoleStar* xRoleStar = XRoleStar::record(Value(Value(this->itemData.spriteid()).asString()+Value(this->itemData.star()).asString()));
    if (xRoleStar->doc[Value(Value(this->itemData.spriteid()).asString()+Value(this->itemData.star()).asString()).asString().c_str()].IsNull() != false){
        return;
    }
    
    //已有召唤石的数量，到背包中查看,如果为null，说明背包中没有此道具
    PItem* haveProp = Manager::getInstance()->getPropItem(xRole->getPropId());
    int haveNum = haveProp == NULL?0:haveProp->itemnum();
    //需要召唤石的数量（升阶不需要召唤石）
    int needNum;
    if (this->itemData.star() < 6){//进化需要召唤石数量
        needNum =xRoleStar->getItemNum();
    }else if(this->itemData.quality() >= 8 && this->itemData.star() == 6){//变异所需召唤石
        needNum =xRole->getMutationNum();
    }
    //进化需要花费
    int cost =xRoleStar->getCost();
    rateLabel->setString(Value(haveNum).asString()+"/"+Value(needNum).asString());
    
    //如果已有召唤石数量等于需要召唤石数量，那么进度条消失，召唤石按钮出现
    this->progress->setVisible(haveNum < needNum?true:false);
    this->changeBtn->setVisible(haveNum < needNum?false:true);
    
    if (haveNum >= needNum) {
        //当召唤石足够的时候，显示的是进化按钮，品阶为【紫+2】及星级为6星的角色才能进行变异并且召唤石是否足够，不足够则显示召唤石进度
        if (this->itemData.quality() >= 8 && this->itemData.star() == 6) {
            this->changeBtn->setTitleText("变异");
            this->status = 1;
        }else if (this->itemData.star() < 6){
            this->changeBtn->setTitleText("进化");
            this->status = 0;
        }else if(this->itemData.quality() < 8 &&this->itemData.star()== 6){
            this->desc->setVisible(true);
            //让按钮置灰并且不可点击
            this->changeBtn->setVisible(false);
//            this->changeBtn->setTouchEnabled(false);
            this->progress->setVisible(false);
//            this->changeBtn->setColor(Color3B::GRAY);
//            this->changeBtn->setTitleText("变异");
            
        }
    }
    
    if (needNum != 0) {
        progressBar->setPercent(float(haveNum*100/needNum));
    }
}

void Role::setStar()
{
    for (int i=0; i<6; i++) {
        ImageView* star=static_cast<ImageView*>(this->rolePic->getChildByName("star"+Value(i+1).asString()));
        //右边显示的星级
        ImageView* star1=static_cast<ImageView*>(this->imgBg->getChildByName("starRight"+Value(i+1).asString()));
        star->loadTexture(i <this->itemData.star()?"star_1.png":"star_2.png");
        star1->setVisible(i <this->itemData.star()?true:false);
    }
}

void Role::setEquips()
{
    this->xRoleData = XRoleData::record(Value(Value(this->itemData.spriteid()).asString() +Value(this->itemData.quality()).asString()));
    if(this->xRoleData->doc[Value(Value(this->itemData.spriteid()).asString() +Value(this->itemData.quality()).asString()).asString().c_str()].IsNull()){
        return;
    }
    //将属性存储在列表中
    this->propertyList.push_back(PropertyMess{Value(this->xRoleData->getHp()).asString(),Value(this->xRoleData->getHpRate()).asString()});//生命和成长
    this->propertyList.push_back(PropertyMess{Value(this->xRoleData->getAtk()).asString(),Value(this->xRoleData->getAtkRate()).asString()});//攻击和成长
    this->propertyList.push_back(PropertyMess{Value(this->xRoleData->getDf()).asString(),Value(this->xRoleData->getDfRate()).asString()});//物防和成长
    this->propertyList.push_back(PropertyMess{Value(this->xRoleData->getMDf()).asString(),Value(this->xRoleData->getMDfRate()).asString()});//法防和成长
    this->propertyList.push_back(PropertyMess{Value(this->xRoleData->getMiss()).asString(),"0"});//闪避
    this->propertyList.push_back(PropertyMess{Value(this->xRoleData->getCrh()).asString(),"0"});//爆击
    this->propertyList.push_back(PropertyMess{Value(this->xRoleData->getHeal()).asString(),"0"});//生命恢复速度
    //存储需要装备位上的准备id
    this->equipsData = {this->xRoleData->getEquipPos1(),this->xRoleData->getEquipPos2(),this->xRoleData->getEquipPos3(),
        this->xRoleData->getEquipPos4(),this->xRoleData->getEquipPos5(),this->xRoleData->getEquipPos6()};
    for (int i = 0; i<6; i++) {
        Widget* equipPos = static_cast<Widget*>(this->equipPanel->getChildByName("equipPos"+Value(i+1).asString()));
        equipPos->addTouchEventListener(CC_CALLBACK_2(Role::touchEvent, this));
    }
    
    if (this->xRoleData == nullptr ||this->equipsData.size() == 0) {
        return;
    }
    this->equipStatus.clear();
    
    Sprite*newEquip;
    //判断装备位的状态以及显示已经穿上的装备－－共五种状态：无装备，未装备，可合成，可装备，已装备
    for (int i = 0; i<6; i++) {
        //如果装备位存在道具图标，先移除道具图标
        Widget* equipPosItem = static_cast<Widget*>(this->equipPanel->getChildByName("equipPos"+Value(i+1).asString()));
        Widget* iconBox=static_cast<Widget*>(equipPosItem->getChildByName("iconBox"));
        if(iconBox)equipPosItem->removeChild(iconBox);
        
        XItem* xItem = XItem::record(Value(this->equipsData.at(i)));
        //合成表
        XCraft*xCraft = XCraft::record(Value(this->equipsData.at(i)));
        bool isHaveEquip = false;
        for (int j = 0; j<this->itemData.equiplist_size(); j++) {
            PItem item = this->itemData.equiplist(j);
            //根据服务器给的装备位置，显示道具
            if (this->itemData.equiplist(j).posid() == i) {
                isHaveEquip = true;
                this->equipStatus.push_back(2);
                //显示装备
                this->createIconBox(this->equipsData.at(i),"equipPos"+Value(i+1).asString(),2);//
                break;
            }
        }
        //当装备位上没有穿装备
        if (!isHaveEquip) {
            PItem* haveProp = Manager::getInstance()->getPropItem(this->equipsData.at(i));
            //当背包中没有此装备的时候分为两种情况，一种是有足够的材料可合成一种是材料不够不足以合成
            if (haveProp == nullptr) {
                std::vector<int> materialsId;
                //可合成－－当角色未穿上装备及玩家的背包内没有所需的装备道具，但背包中有足以合成装备道具的材料的时候
                if (xCraft->doc[Value(this->equipsData.at(i)).asString().c_str()].IsNull() == false) {
                    //isMayCompose是否有足够的材料进行合成
                    bool isMayCompose = true;
                    for (int t =1; t<6; t++) {
                        if (xCraft->doc[Value(this->equipsData.at(i)).asString().c_str()][("item"+Value(t).asString()).c_str()].GetInt() != 0 ) {
                            
                            isMayCompose = propIsEnough(xCraft->getItem1(),xCraft->doc[Value(this->equipsData.at(i)).asString().c_str()][("num"+Value(t).asString()).c_str()].GetInt());
                            if (isMayCompose == false) {
                                this->createIconBox(this->equipsData.at(i),"equipPos"+Value(i+1).asString(),1);//
                                this->equipStatus.push_back(1);
                                break;
                            }else{
                                this->createIconBox(this->equipsData.at(i),"equipPos"+Value(i+1).asString(),3);//
                                this->equipStatus.push_back(3);
                                break;
                            }
                        }
                    }
                }//无装备--当角色未穿上装备，且玩家的背包内没有所需的装备道具时
                else{
                    this->createIconBox(this->equipsData.at(i),"equipPos"+Value(i+1).asString(),1);//
                    this->equipStatus.push_back(1);
                }
            }//未装备－－当玩家的背包内有所需的装备道具，但角色因等级小于装备等级限制，而尚未装备该道具时
            else if (haveProp!= nullptr && xItem->getMaxLv()>=itemData.level()){
                this->createIconBox(this->equipsData.at(i),"equipPos"+Value(i+1).asString(),5);//
                this->equipStatus.push_back(5);
            }//可装备－－当玩家的背包内有所需的装备道具，且角色等级与装备等级限制限制相当，且尚未装备该道具时
            else if (haveProp!= nullptr && xItem->getMaxLv()<itemData.level()){
                this->createIconBox(this->equipsData.at(i),"equipPos"+Value(i+1).asString(),4);//
                this->equipStatus.push_back(4);
            }
        }
    }
    //判断各装备位状态，如果都有装备，则升阶按钮可点击
    bool isMayPress = true;
    Button* btnAscend = static_cast<Button*>(this->equipPanel->getChildByName("btnAscend"));
    for (int m = 0; m<this->equipStatus.size(); m++) {
        if(this->equipStatus.at(m)!= 2){
            isMayPress = false;
            break;
        }
    }
    btnAscend->setEnabled(isMayPress?true:false);
    btnAscend->setColor(isMayPress?Color3B::WHITE:Color3B::GRAY);
    
}

bool Role::propIsEnough(int itemId,int num)
{
    PItem*item = Manager::getInstance()->getPropItem(itemId);
    if (item != nullptr) {
        if (item->itemnum() >= num) {
            return true;
        }
    }
    return false;
    
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
    switch (btn->getTag()) {
        case 10000://图鉴按钮
        {
//            Manager::getInstance()->showGoUpMsg("我们都是好孩子！");
//            //设置按钮选中状态
            tabBar->setIndex(0);
            setBtnVisible();
            this->rolePic->setVisible(true);
            break;
        }
        case 10001://属性按钮
        {
            tabBar->setIndex(1);
            setBtnVisible();
            this->propertyPanel->setVisible(true);
            //数据表里如果没信息，则返回
            if (this->xRoleData == nullptr) {
                return;
            }
            for (int i = 0; i<6; i++) {
                Widget* widget = static_cast<Widget*>(this->propertyPanel->getChildByName("qualityItem_"+Value(i).asString()));
                Text* rateLabel = static_cast<Text*>(widget->getChildByName("rateLabel"));
                Text* qualityLabel = static_cast<Text*>(widget->getChildByName("qualityLabel"));
                if(this->propertyList.size() !=0) qualityLabel->setString(this->propertyList.at(i).propertyNum);
                if (i<4) {
                    if(this->propertyList.size() !=0) rateLabel->setString(this->propertyList.at(i).growUpNum);
                }
            }
            break;
        }
        case 10002://技能按钮
        {
            this->tabBar->setIndex(2);
            setBtnVisible();
            for (int i=7; i>=0; i--) {
                ImageView* skill=static_cast<ImageView*>(this->skillPanel->getChildByName("skill"+Value(i+1).asString()));
                Text* levelLabel =static_cast<Text*>(skill->getChildByName("levelLabel"));
                Widget* skillImg = static_cast< Widget*>(skill->getChildByName("skillImg"));
                ImageView* icon = static_cast<ImageView* >(skill->getChildByName("icon"));
                bool boo = this->itemData.skillidlist_size()>i;
                skill->setTag((i+1)*100);
                if(boo){
                    //服务器给的技能id是倒着给的
                    int skillId = this->itemData.skillidlist(this->itemData.skillidlist_size()-i-1);
                    boo = skillId==0?false:true;
                    if (boo) {
                        //将skill的tag记录下来，和在this->itemData.skillidlist_size()中的排序数相同
                        skill->setTag(this->itemData.skillidlist_size()-i-1);
                        icon->loadTexture("skill_"+Value(skillId/100).asString()+".png");
                        levelLabel->setString(boo?"LV."+Value(skillId%100).asString():"");
                    }
                }
                levelLabel->setVisible(boo);
                skillImg->setVisible(boo);
                icon->setVisible(boo);
                std::vector<int> picNums = {1,1,2,3,4,5,6,6};
                skill->loadTexture(boo?"frame_"+Value(picNums.at(i)).asString()+".png":"frame_0.png");
                
            }
            this->skillPanel->setVisible(true);
            break;
        }
        case 10003://派遣按钮
        {
            this->tabBar->setIndex(3);
            break;
        }
        case 20001://6个装备位
        case 20002:
        case 20003:
        case 20004:
        case 20005:
        case 20006:
        {
            this->openInfoPanel(btn->getTag()%20000 - 1);
            break;
        }
        default:
            break;
    }
}
//更新添加技能及替换技能（替换技能是因为技能升级了）
void Role::updateSkills(int skillId)
{
    XRole*xRole = XRole::record(Value(this->itemData.spriteid()));
    int skill = skillId;
    //技能未开5个等级以上
    if(skillId == 0){
        std::vector<int> skillOpen = {0,1,3,6,9};
        for (int j = 0; j<skillOpen.size(); j++) {
            if (this->itemData.quality() == skillOpen[j] && this->itemData.skillidlist_size() < j+1) {
                skill = xRole->doc[Value(this->itemData.spriteid()).asString().c_str()][("skill"+Value(j+1).asString()).c_str()].GetInt();
            }
        }
    }
    if(skill!=0){
        Manager::getInstance()->updateNpcSkills(this->itemData.npcid(),skill);
        this->itemData = *Manager::getInstance()->getNpc(this->itemData.npcid());
    }
}

void Role::openInfoPanel(int index)
{
    if (this->equipsData.at(index) == 0) {
        return;
    }
    XItem* xItem;
    int equipIndex;
    equipIndex = this->equipStatus.at(index);
    xItem = XItem::record(Value(this->equipsData.at(index)));
    this->messageId = 2;
    EquipInfo* equipInfo = EquipInfo::create(equipIndex,xItem,this->itemData.npcid(),index,this);
    equipInfo->show(this);
}

void Role::touchBtnEvent(Ref *pSender, TouchEventType type)
{
    Button* btn=static_cast<Button*>(pSender);
    if(type!=TouchEventType::ENDED){
        return;
    }
    switch (btn->getTag()) {
        case 12088://返回按钮
        {
            this->clear(true);
            break;
        }
        case 12305://点击后弹出召唤石获得途径窗口按钮
        {
            
            break;
        }
        case 12548://角色的升阶按钮
        {
            
            this->messageId = 1;
            //向服务器发送请求——升阶
            PAscend pAscend;
            pAscend.set_heroid(this->itemData.npcid());
            Manager::getInstance()->socket->send(C_ASCEND, &pAscend);
            break;
        }
        case 14423://判断是进化还是变异按钮
        {
            //向服务器发送请求——进化
            if (this->status == 0) {//进化
                this->messageId = 0;
                PEvolve pEvolve;
                pEvolve.set_heroid(this->itemData.npcid());
                Manager::getInstance()->socket->send(C_EVOLVE, &pEvolve);
            }else if(this->status == 1){//变异
                if (this->itemData.star()>=6) {
                    Manager::getInstance()->showMsg("已经达到最大星级!");
                    return;
                }
                this->messageId = 3;
                PMutation pMutation;
                pMutation.set_heroid(this->itemData.npcid());
                Manager::getInstance()->socket->send(C_MUTATION, &pMutation);
            }
            break;
        }
            
        default:
            break;
    }
}

void Role::touchSkillItemEvent(Ref *pSender, TouchEventType type)
{
    Widget* widget=static_cast<Widget*>(pSender);
    if (this->itemData.skillidlist().size()<widget->getTag()+1) {
        return;
    }
    if(type==TouchEventType::BEGAN)
    {
        XSkill* xSkill = XSkill::record(Value(this->itemData.skillidlist(widget->getTag())));
        string info = xSkill->getDesc() == 0?"":Value(xSkill->getDesc()).asString();
        this->showTips(info,widget);
    }
    else if(type==TouchEventType::ENDED){
        this->hideTips();
    }else if(type==TouchEventType::CANCELED){
        this->hideTips();
    }
}

void Role::initNetEvent(){
    auto listener = EventListenerCustom::create(NET_MESSAGE, [=](EventCustom* event){
        NetMsg* msg = static_cast<NetMsg*>(event->getUserData());
        switch (msg->msgId)
        {
            case C_COMMONMSG:
            {
                PCommonResp pCommonResp;
                pCommonResp.ParseFromArray(msg->bytes, msg->len);
                if(pCommonResp.resulttype()==C_ASCEND){//升阶
                    //status:0成功
                    if (pCommonResp.status()!=0) {
//                        Manager::getInstance()->showMsg("");
                    }
                }else if (pCommonResp.resulttype()==C_EVOLVE){//进化
                    if (pCommonResp.status()==0) {//成功
                        
                    }else if (pCommonResp.status()==1){//宠物不存在
                        Manager::getInstance()->showMsg("宠物不存在!");
                    }else if (pCommonResp.status()==2){//星级达到最大等级
                        Manager::getInstance()->showMsg("星级达到最大等级!");
                    }else if (pCommonResp.status()==3){//缺少星级数据
                        Manager::getInstance()->showMsg("缺少星级数据!");
                    }else if (pCommonResp.status()==4){//金币不足
                        Manager::getInstance()->showMsg("金币不足!");
                    }else if (pCommonResp.status()==5){//物品不足
                        Manager::getInstance()->showMsg("物品不足!");
                    }
                }else if (pCommonResp.resulttype()==C_MUTATION){//变异
                    if (pCommonResp.status()!=0) {
                    }
                }else if(pCommonResp.resulttype()==C_WEAREQUIP){//穿装备
                    //status:0成功
                    if (pCommonResp.status()==0) {//穿装备成功，弹出所加属性
                    }else if (pCommonResp.status()==1){//已经穿上了
                        Manager::getInstance()->showMsg("已经穿上了!");
                    }else if (pCommonResp.status()==2){//物品不存在
                        Manager::getInstance()->showMsg("物品不存在!");
                    }else if (pCommonResp.status()==3){//等级不足
                        Manager::getInstance()->showMsg("等级不足!");
                    }else if (pCommonResp.status()==4){//找不到穿装备的精灵
                        Manager::getInstance()->showMsg("找不到穿装备的精灵!");
                    }else if (pCommonResp.status()==5){//装备不匹配
                        Manager::getInstance()->showMsg("装备不匹配!");
                    }else if (pCommonResp.status()==6){//物品数量不足
                        Manager::getInstance()->showMsg("物品数量不足!");
                    }
                }
            }
                break;
            case C_UPROLE://更新角色
            {
                this->sucessReturn();
            }
                break;
            case C_UPITEM://更新物品
            {
                this->sucessReturn();
            }
                break;
            case C_MUTATION://变异
            {
                this->pGoldQualityUpgrade.ParseFromArray(msg->bytes, msg->len);
                if (this->pGoldQualityUpgrade.result()==0) {
                    //变异结果标示 0升到金色品质 1获得随机技能 2随机技能升级
                    if (this->pGoldQualityUpgrade.changeflag() == 0) {
                        this->setData();
                        
                    }else if (this->pGoldQualityUpgrade.changeflag() == 1||this->pGoldQualityUpgrade.changeflag() == 2) {
                        int index = this->pGoldQualityUpgrade.changeflag() == 1?3:4;
                        this->updateSkills(this->pGoldQualityUpgrade.skillid());
                        InfoPanel*infoPanel = InfoPanel::create(index,this->itemData);
                        infoPanel->show(this);
                    }
                }
                else if (this->pGoldQualityUpgrade.result()==1){//物品不足
                    Manager::getInstance()->showMsg("物品不足!");
                }else if (this->pGoldQualityUpgrade.result()==2){//找不到穿装备的精灵
                    Manager::getInstance()->showMsg("找不到穿装备的精灵!");
                }else if (this->pGoldQualityUpgrade.result()==3){//品质最大值
                    Manager::getInstance()->showMsg("品质最大值!");
                }else if (this->pGoldQualityUpgrade.result()==4){//金币不足
                    Manager::getInstance()->showMsg("金币不足!");
                }else if (this->pGoldQualityUpgrade.result()==5){//不符合变异品质条件
                    Manager::getInstance()->showMsg("不符合变异品质条件!");
                }else if (this->pGoldQualityUpgrade.result()==6){//被动技能等级已满
                    Manager::getInstance()->showMsg("被动技能等级已满!");
                }
            }
                break;
            default:
                break;
        }
    });
    Director::getInstance()->getEventDispatcher()->addEventListenerWithSceneGraphPriority(listener, this);
}

void Role::createIconBox(int itemId,string parentName,int desIndex)
{
    Widget* img=(Widget*)this->equipPanel->getChildByName(parentName);
    if(EquipPosList.size() == 6){
       static_cast<Sprite*>(img->getChildByName("itemIcon"))->removeFromParent();
        static_cast<Sprite*>(img->getChildByName("frame"))->removeFromParent();
        static_cast<Sprite*>(img->getChildByName("statusPic"))->removeFromParent();
        static_cast<Sprite*>(img->getChildByName("iconFrame"))->removeFromParent();
    }
    Widget* iconBox=Widget::create();
    ImageView* iconFrame=ImageView::create();
    img->addChild(iconFrame);
    iconFrame->setPosition(Vec2(iconBox->getPositionX(), iconBox->getPositionY()));
    XItem* xItem=XItem::record(Value(itemId));
    iconFrame->loadTexture("hero_circle_"+Value(xItem->getRate()+1).asString()+".png");
    iconFrame->setName("iconFrame");
    iconFrame->setAnchorPoint(Vec2(0, 0));
    iconFrame->setTag(img->getTag());//点击此物品和点击此装备位一样弹出信息弹窗
    iconFrame->addTouchEventListener(CC_CALLBACK_2(Role::touchEvent, this));
    Sprite* sprite=Sprite::create("item_"+Value(xItem->getIcon()).asString()+".png");
    sprite->setAnchorPoint(Vec2(0, 0));
    //sprite->setColor(desIndex == 2?Color3B::WHITE:Color3B::GRAY);//装备未穿上则装备为灰色
    //iconFrame->setColor(desIndex == 2?Color3B::WHITE:Color3B::GRAY);//装备未穿上则装备为灰色
    img->addChild(sprite);
    if (desIndex!=2) {
        Utils::addGray(static_cast<Sprite*>(iconFrame->getVirtualRenderer()));
        Utils::addGray(static_cast<Sprite*>(sprite));
    }
    
    sprite->setName("itemIcon");
    sprite->setPosition(Vec2(iconFrame->getPositionX()+(iconFrame->getContentSize().width-sprite->getContentSize().width)/2, iconFrame->getPositionY()+(iconFrame->getContentSize().height-sprite->getContentSize().height)/2));
    //黄色加号
    Sprite* sprite1=Sprite::create("hero_circle_7.png");
    img->addChild(sprite1);
    sprite1->setName("frame");
    sprite1->setPosition(Vec2(iconFrame->getPositionX()+iconFrame->getContentSize().width-sprite1->getContentSize().width,iconFrame->getPositionY()+iconFrame->getContentSize().height-sprite1->getContentSize().height));
    sprite1->setVisible(desIndex!= 2?true:false);
    //装备状态图片的显示
    ImageView* sprite2=ImageView::create("equipStatus_"+Value(desIndex).asString()+".png");
    img->addChild(sprite2);
    sprite2->setName("statusPic");
    sprite2->setPosition(Vec2(iconFrame->getPositionX()+iconFrame->getContentSize().width/2-5,iconFrame->getPositionY()));

    img->addChild(iconBox);
    img->setVisible(true);
    if(EquipPosList.size()<6){
        this->EquipPosList.push_back(1);
    }
}

void Role::sucessReturn()
{
    PNpc data = *Manager::getInstance()->getNpc(this->itemData.npcid());
//    this->itemData = *Manager::getInstance()->getNpc(this->itemData.npcid());
    if(this->messageId == 0){//进化——提升星级
        if(this->itemData.star()== data.star()){
            return;
        }
        this->itemData = data;
        this->itemData = *Manager::getInstance()->getNpc(this->itemData.npcid());
        this->setStar();
        //弹出进化成功弹窗
        InfoPanel*infoPanel = InfoPanel::create(2,this->itemData);
        infoPanel->show();
    }else if(this->messageId == 1){//升阶——提升角色的品阶等级
        if(this->itemData.quality() == data.quality()){
            return;
        }
        this->itemData = data;
        updateSkills(0);
        this->setData();
        InfoPanel*infoPanel = InfoPanel::create(0,this->itemData);
        infoPanel->show(this);
    }else if(this->messageId == 2){//穿装备
        this->itemData = data;
        this->setEquips();
    }else if(this->messageId == 3){//变异
        this->itemData = data;
        this->setData();
    }
    this->setShowProgress();
}

void Role::showTips(string messInfo,Widget*widget)
{
    auto tipsLayer=Node::create();
    Size size=CCDirector::getInstance()->getWinSize();
    tipsLayer->setName("messInfoLayer");
    this->skillPanel->addChild(tipsLayer, TIPS_LAY);
    cocos2d::extension::Scale9Sprite* messagBg = cocos2d::extension::Scale9Sprite::create("outfit_frame_7.png");
    tipsLayer->addChild(messagBg,0,1);
    
    Label* richNameText=Label::createWithTTF(messInfo, "Marker Felt.ttf", 20,Size(200,30),TextHAlignment::LEFT);
    richNameText->setAnchorPoint(Vec2(0, 0));
    richNameText->setPosition(Vec2(-richNameText->getContentSize().width/2+10, 0));
    tipsLayer->addChild(richNameText,0,1);
    tipsLayer->setPosition(Vec2(widget->getPositionX()+messagBg->getContentSize().width/2+widget->getContentSize().width/2,widget->getPositionY()));
}

void Role::hideTips(){
    Node* tipsLayer=this->skillPanel->getChildByName("messInfoLayer");
    if (tipsLayer) {
        this->skillPanel->removeChild(tipsLayer);
    }
}


void Role::onExit()
{
    this->tabBar->release();
    BaseUI::onExit();
}