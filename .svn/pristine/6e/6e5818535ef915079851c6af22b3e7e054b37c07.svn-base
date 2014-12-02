//
//  Manager.cpp
//  fancyHeart
//
//  Created by 秦亮亮 on 14-5-4.
//
//

#include "Manager.h"
#include "Loading.h"
static Manager* instance=nullptr;

Manager* Manager::getInstance(){
    if(instance==nullptr){
        instance=new Manager();
    }
    return instance;
}

void Manager::switchScence(Scene* scene)
{
    if (this->scene) {
        //this->scene->removeAllChildren();
        Director::getInstance()->replaceScene(scene);
    }else{
//        this->scene->removeAllChildren();
        Director::getInstance()->runWithScene(scene);
    }
    if(this->scene && this->scene->getChildByTag(-100)){
        this->scene->removeChildByTag(-100);
    }
    if(this->scene && this->scene->getChildByTag(-200)){
        this->scene->removeChildByTag(-200);
    }
    this->scene=scene;
    this->scene->addChild(Mask::getInstance(),0,-200);
    this->scene->addChild(Loading::getInstance(),LOADING_LAY,-100);
}

void Manager::setRoleData(NetMsg* msg)
{
    
    if(this->roleData) delete this->roleData;
    this->roleData=new LoginResp();
    this->roleData->ParseFromArray(msg->bytes, msg->len);
//    // write /
//    string path="/Users/zhai/Documents/roleData.txt";
//    int fd = open(path.c_str(), O_CREAT|O_TRUNC|O_RDWR,0644);
//    this->roleData->SerializeToFileDescriptor(fd);
}

LoginResp* Manager::getRoleData()
{
    if (this->roleData==nullptr) {
        this->roleData=new LoginResp();
        string path="roleData.txt";
        string fullPath=FileUtils::getInstance()->fullPathForFilename(path);
        int fd = open(fullPath.c_str(),O_RDONLY);
        this->roleData->ParseFromFileDescriptor(fd);
    }
    return this->roleData;
}

void Manager::updateRole(NetMsg* msg)
{
    PUpRole role;
    role.ParseFromArray(msg->bytes,msg->len);
    
    if (role.updatepkid()==this->roleData->mutable_role()->roleid()) {
        switch (role.fieldtype()) {
            case RoleFields::MONEY:
            {
                this->roleData->mutable_role()->set_coin(role.finalvalue());
                break;
            }
            case RoleFields::LEVEL:
            {
                this->roleData->mutable_role()->set_level(role.finalvalue());
                break;
            }
            case RoleFields::RMB:
            {
                this->roleData->mutable_role()->set_rmb(role.finalvalue());
                break;
            }
            case RoleFields::EXP:
            {
                this->roleData->mutable_role()->set_exp(role.finalvalue());
                break;
            }
            case RoleFields::VIP_LEVEL:
            {
                this->roleData->mutable_role()->set_viplvl(role.finalvalue());
                break;
            }
            case RoleFields::ENERGY:
            {
                this->roleData->mutable_role()->set_stamina(role.finalvalue());
                break;
            }
            default:
                break;
        }
    }
    else if(role.updatepkid()!=this->roleData->mutable_role()->roleid())
    {
        PNpc* pNpc=this->getNpc(role.updatepkid());//通过id获取npc指针
        if (pNpc) {
            switch (role.fieldtype()) {
                case RoleFields::LEVEL:
                {
                    pNpc->set_level(role.finalvalue());
                    break;
                }
                case RoleFields::EXP:
                {
                    pNpc->set_exp(role.finalvalue());
                    break;
                }
                case RoleFields::QUALITY:
                {
                    pNpc->set_quality(role.finalvalue());
                    break;
                }
                case RoleFields::STAR:
                {
                    pNpc->set_star(role.finalvalue());
                    break;
                }
                default:
                    break;
            }
        }
    }
    
}

void Manager::updateItems(NetMsg* msg)
{
    
    PUpItem item;
    item.ParseFromArray(msg->bytes, msg->len);
    google::protobuf::RepeatedPtrField< ::PItemChangeLog >::iterator it=item.mutable_itemloglist()->begin();
    for (; it!=item.mutable_itemloglist()->end(); ++it)
    {
        if(it->npcid()!=0)//更新英雄身上的装备
        {
            PNpc* pNpc=this->getNpc(it->npcid());
            this->updateItem(it, pNpc->mutable_equiplist());
        }
        else if(it->npcid()==0)//背包中添加
        {
            this->updateItem(it, this->roleData->mutable_itemlist());
        }
    }
}

void Manager::addOrRemoveNpc(NetMsg* msg)

{
    
    PAddOrRemoveNpc pnc;
    
    pnc.ParseFromArray(msg->bytes, msg->len);
    
    //true添加false删除
    
    if (pnc.addorremove()==true) {
        
        this->roleData->mutable_npclist()->MergeFrom(pnc.npcs());
        
    }else if (pnc.addorremove()==false){
        
        for (int i=0; i<this->roleData->npclist_size(); i++) {
            
            int64 npcId=this->roleData->npclist(i).npcid();
            
            for (int j=0; j<pnc.npcs_size(); j++) {
                
                if (npcId==pnc.npcs(j).npcid()) {
                    
                    this->roleData->mutable_npclist()->DeleteSubrange(i, 1);
                    
                }
                
            }
            
        }
        
    }
    
}

void Manager::updateItem(RepeatedPtrField< ::PItemChangeLog >::iterator it,RepeatedPtrField< ::PItem > *items)
{
    bool isFound=false;
    for (int i=0;i<items->size();i++)
    {
        PItem* item=items->Mutable(i);
        
        if (item->itemid()==it->itemid() && it->posid() == item->posid()) {
            if (it->itemfinalnum()==0)//为0要从背包删除此物品
            {
                items->DeleteSubrange(i, 1);
            }
            else if(it->itemfinalnum()!=0)//不为0更新
            {
                item->set_itemnum(it->itemfinalnum());
            }
            isFound=true;
            break;
        }
        
    }
    if (!isFound) {//不存在添加
        PItem* item=new PItem();
        item->set_itemnum(it->itemfinalnum());
        item->set_itemid(it->itemid());
        item->set_npcid(it->npcid());
        item->set_posid(it->posid());
        items->AddAllocated(item);
    }

}

PNpc* Manager::getNpc(int64 npcId)
{
    for (int i=0;i<this->roleData->npclist_size();i++)
    {
        PNpc* pnpc=this->roleData->mutable_npclist(i);
        if (pnpc->npcid()==npcId) {
            return pnpc;
            break;
        }
    }
    return nullptr;
    
}

PItem*Manager::getPropItem(int itemId)
{
    PItem*item;
    for (int i=0; i<this->roleData->itemlist().size(); i++) {
        item = this->roleData->mutable_itemlist(i);
        if (item->itemid() == itemId) {
            return item;
        }
    }
    return nullptr;
}

PGateItem* Manager::getGateItem(int gateId)
{
    for (int i=0;i<this->roleData->gate().gates_size();i++)
    {
        PGateItem* pGate=this->roleData->mutable_gate()->mutable_gates(i);
        if (pGate->gateid()==gateId) {
            return pGate;
            break;
        }
    }
    return nullptr;
}

PNodeItem* Manager::getNodeItem(int gateId,int nodeId)
{
    PGateItem* gateItem=Manager::getInstance()->getGateItem(gateId);
    for (int i=0; i<gateItem->items_size(); i++) {
        PNodeItem* nodeItem= gateItem->mutable_items(i);
        if (nodeItem->xid()==nodeId) {
            return nodeItem;
        }
    }
    return nullptr;
}

void Manager::updateGates(NetMsg* msg)//更新关卡
{
    PUpdateGates pUpdateGates;
    pUpdateGates.ParseFromArray(msg->bytes, msg->len);
    for (int i=0; i<pUpdateGates.gates_size(); i++) {
        PGateItem* pGateItem=pUpdateGates.mutable_gates(i);
        bool isFound=false;
        for (int j=0; j<this->roleData->gate().gates_size(); j++) {
            if (this->roleData->gate().gates(j).gateid()==pGateItem->gateid()) {
                this->roleData->mutable_gate()->mutable_gates(j)->set_islock(pGateItem->islock());
                isFound=true;
                break;
            }
        }
        if (!isFound) {
            this->roleData->mutable_gate()->mutable_gates()->AddAllocated(pGateItem);
        }
    }
}
//更新关卡节点
void Manager::updateNodes(NetMsg* msg)//更新节点
{
    PUpdateNodes pUpdateNodes;
    pUpdateNodes.ParseFromArray(msg->bytes, msg->len);
    PGateItem* pGateItem=this->getGateItem(pUpdateNodes.gateid());
    if (pGateItem!=nullptr) {
        for (int i=0; i<pUpdateNodes.nodeitems_size(); i++) {
            PUpdateNode* pUpdateNode=pUpdateNodes.mutable_nodeitems(i);
            if (pUpdateNode->type()==1) {//增加节点
                PNodeItem* nodeItem=new PNodeItem();
                nodeItem->set_star(pUpdateNode->mutable_nodeitem()->star());
                nodeItem->set_times(pUpdateNode->mutable_nodeitem()->times());
                nodeItem->set_xid(pUpdateNode->mutable_nodeitem()->xid());
                pGateItem->mutable_items()->AddAllocated(nodeItem);
            }else if (pUpdateNode->type()!=1){
                for (int j=0; j<pGateItem->items_size(); j++) {
                    if (pGateItem->items(j).xid()==pUpdateNode->nodeitem().xid()) {
                        if (pUpdateNode->type()==0) {//删除
                            pGateItem->mutable_items()->DeleteSubrange(j, 1);
                        }else if(pUpdateNode->type()==2){//修改节点
                            pGateItem->mutable_items(j)->set_star(pUpdateNode->mutable_nodeitem()->star());
                            pGateItem->mutable_items(j)->set_times(pUpdateNode->mutable_nodeitem()->times());
                        }
                        break;
                    }
                }
            }
            
        }
    }
    
}
//
void Manager::updateNpcSkills(int64 npcid,int skillId)
{
    PNpc* pNpc=this->getNpc(npcid);
    bool isNewSkill = true;
    //技能升级，替换原有技能的id
    for (int i =0; i<pNpc->skillidlist_size(); i++) {
        if (int(skillId/100) == int(pNpc->skillidlist(i)/100)) {
            //替换这个技能的id
            pNpc->set_skillidlist(i, skillId);
            isNewSkill = false;
            break;
        }
    }
    //新添技能
    if (isNewSkill) {
        pNpc->add_skillidlist(skillId);
    }
}

int Manager::getCurrExp(int exp,int lvl)
{
    for(int i = 1;i<lvl;i++){
        exp -= XExp::record(Value(i))->getExp();
    }
    return exp;
}

//int Manager::getAttackForce(BData bd)
//{
//    //角色战斗力=角色生命/10000+（角色攻击+角色物防+角色法防+闪避+暴击）/1000
//    FData* fd=FData::create(bd);
//    float force=fd->fullHp/10000.0f+(fd->atc+fd->def+fd->miss+fd->crh)/1000.0f;
//    return Value(force).asInt();
//}

void Manager::showMsg(const string msg)
{
    if (!this->scene)
    {
        return;
    }
    auto msgLayer=Node::create();
    Size size=CCDirector::getInstance()->getWinSize();
    this->scene->addChild(msgLayer, MSG_LAY);
    
    cocos2d::extension::Scale9Sprite* messagBg = cocos2d::extension::Scale9Sprite::create("loading_bg.png");
    messagBg->setPosition(Vec2(size.width/2, size.height/2));
    messagBg->setCapInsets(Rect(50, 50, messagBg->getContentSize().width-100, messagBg->getContentSize().height-100));
    msgLayer->addChild(messagBg,0,1);
    
    Label* label=Label::createWithTTF(msg, "Marker Felt.ttf", 24,Size(355,80),TextHAlignment::CENTER,TextVAlignment::CENTER);
    label->setTag(100);
    msgLayer->addChild(label,0,1);
    label->setPosition(Vec2(size.width/2,size.height/2));
    messagBg->setContentSize(Size(label->getContentSize().width+50, label->getContentSize().height+20));
    messagBg->setOpacity(0);
    Sequence* seqBg=Sequence::create(FadeIn::create(0.2),DelayTime::create(1),CCFadeOut::create(0.2),CallFunc::create(CC_CALLBACK_0(Sprite::removeFromParent, msgLayer)),NULL);
    messagBg->runAction(seqBg);
    
}

void Manager::showItemTips(int itemId,Vec2 pos)
{
    if (!this->scene)
    {
        return;
    }
    auto tipsLayer=Node::create();
    Size size=CCDirector::getInstance()->getWinSize();
    tipsLayer->setName("tipsLayer");
    this->scene->addChild(tipsLayer, TIPS_LAY);
    cocos2d::extension::Scale9Sprite* messagBg = cocos2d::extension::Scale9Sprite::create("loading_bg.png");
    //messagBg->setPosition(Vec2(size.width/2, size.height/2));
    messagBg->setCapInsets(Rect(50, 50, messagBg->getContentSize().width-100, messagBg->getContentSize().height-100));
    tipsLayer->addChild(messagBg,0,1);
    XItem* xItem=XItem::record(Value(itemId));
    
    Label* richNameText=Label::createWithTTF(xItem->getName(), "Marker Felt.ttf", 24,Size(200,30),TextHAlignment::LEFT);
    string name=xItem->getName();
    richNameText->setAnchorPoint(Vec2(0, 0));
    richNameText->setPosition(Vec2(-richNameText->getContentSize().width/2, 0));
    Label* richDescText=Label::createWithTTF(xItem->getDes(), "Marker Felt.ttf", 20,Size(200,30),TextHAlignment::LEFT,TextVAlignment::TOP);
    richDescText->setAnchorPoint(Vec2(0, 0));
    richDescText->setPosition(Vec2( -richDescText->getContentSize().width/2, -richDescText->getContentSize().height));
    
    tipsLayer->addChild(richNameText,0,1);
    tipsLayer->addChild(richDescText,0,1);
    
    tipsLayer->setPosition(pos);
    
    messagBg->setContentSize(Size(richNameText->getContentSize().width+50, richNameText->getContentSize().height+richDescText->getContentSize().height+20));
}

void Manager::hideItemTips(){
    if (!this->scene)
    {
        return;
    }
    Node* tipsLayer=this->scene->getChildByName("tipsLayer");
    if (tipsLayer) {
        this->scene->removeChild(tipsLayer);
    }
}

