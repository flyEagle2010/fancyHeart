//
//  BattleMgr.cpp
//  fancyHeart
//
//  Created by 秦亮亮 on 14-5-15.
//
//

#include "BattleMgr.h"

static BattleMgr* instance=nullptr;

BattleMgr* BattleMgr::getInstance(){
    if(instance==nullptr){
        instance=new BattleMgr();
    }
    return instance;
}

void BattleMgr::init(std::vector<long long>heros,int nodeID,int gateID)
{
    this->gateID=gateID;
    this->nodeID=nodeID;
    
    XNode* xn=XNode::record(Value(nodeID));
    
    this->battleID=xn->getBattleID();
    XBattle* xb=XBattle::record(Value(battleID));
    if(xb->getMGroup1()>0){
        this->groups.push_back(xb->getMGroup1());
    }
    if(xb->getMGroup2()>0){
        this->groups.push_back(xb->getMGroup2());
    }
    if(xb->getMGroup3()>0){
        this->groups.push_back(xb->getMGroup3());
    }
    if(xb->getMGroup4()>0){
        this->groups.push_back(xb->getMGroup4());
    }
    if(xb->getMGroup5()>0){
        this->groups.push_back(xb->getMGroup5());
    }
    this->npcNum=this->groups.size();
    
    auto scene=BattleScene::createScene();
    Manager::getInstance()->switchScence(scene);
    
    this->view=(BattleScene*)scene->getChildByTag(0);
    this->view->initNpcIcon(this->npcNum);
    
    this->initHero(heros);
    this->heroNum=heros.size();
    this->groupID=this->groups.at(0);

    DramaAni::getInstance()->startPre(this->groupID,this->gateID,this->nodeID);
}

void BattleMgr::init(rapidjson::Value& data)
{
    
}

void BattleMgr::startBattle()
{
    this->isOver=false;
    this->view->layout->runAction(Sequence::create(FadeOut::create(0.8),FadeIn::create(0.8),CallFunc::create(CC_CALLBACK_0(BattleMgr::startBattleAnimEnd, this)) ,NULL));
}

void BattleMgr::startBattleAnimEnd()
{
    this->view->resume();
    BattleMgr::getInstance()->npcs.clear();
    this->groupID=this->groups.at(0);
    this->groups.erase(groups.begin());
    this->initNpc();
    
    //hero
    Size winSize=Director::getInstance()->getWinSize();
    for(int i=0;i<this->heros.size();i++){
        FighterMgr* fm=heros.at(i);
        int pos=fm->pos;
        fm->view->setPosition(Vec2(-GRID_SIZE*3*i-50,winSize.height/2+(pos%2?-GRID_SIZE*0.8:GRID_SIZE*0.8)-150));
        this->view->heroNode->reorderChild(fm->view, 640-abs(fm->view->getPositionY()));
        fm->state=Efstate::idle;
        fm->view->runAction(Sequence::create(DelayTime::create(0.5*i),CallFunc::create(CC_CALLBACK_0(FighterMgr::start, fm)), NULL));
        if(this->npcNum - this->groups.size() == 1){
            fm->view->runAction(Sequence::create(DelayTime::create(0.5*i),CallFunc::create(CC_CALLBACK_0(SkillIcon::start, skillIcons.at(i))), NULL));
        }else{
            fm->view->runAction(Sequence::create(DelayTime::create(0.5*i),CallFunc::create(CC_CALLBACK_0(SkillIcon::resume, skillIcons.at(i))), NULL));
        }
    }
}

void BattleMgr::initHero(std::vector<long long> npcs)
{
    //初始化角色100
    //std::vector<int> ids={100,101,101,102,999};
    std::vector<BData> heroDatas;
    //int len=MIN(npcs.size(),ids.size());
    auto len = npcs.size();
    for(int i=0;i<len;i++)
    {
        PNpc* npc=Manager::getInstance()->getNpc(npcs[i]);
        std::vector<int> skills;
        for(int i=0;i<npc->skillidlist_size();i++){
            skills.push_back(npc->skillidlist(i));
        }
        XRole* xrole=XRole::record(Value(npc->spriteid()));
        skills.push_back(xrole->getCommonSkill());
        heroDatas.push_back(BData{npc->spriteid(),npc->level(),npc->quality(),npc->star(),i,skills});
        //测试数据
        //heroDatas.push_back(BData{ids[i],npc->level(),ids[i]==999?9:npc->quality(),npc->star(),i,skills});
    }
    sort(heroDatas.begin(),heroDatas.end(), BattleMgr::sortGrid);

    Vector<FData*> arr;
    for(int i=0;i<heroDatas.size();i++)
    {
        BData bd=heroDatas.at(i);
        
        int activeSkill;//=bd.skills.at(0);
        for(int j=0;j<bd.skills.size();j++ ){
            XSkill* xskill=XSkill::record(Value(bd.skills.at(j)));
            if(xskill->getType() == 0){
                activeSkill=bd.skills.at(j);
                bd.skills.erase(bd.skills.begin()+j);
                break;
            }
        }
        
        FData* fd=FData::create(bd);
        
        FighterMgr* hero=FighterMgr::create(fd);
        this->heros.pushBack(hero);
        
        this->skillIcons.pushBack(this->view->createSkillIcon(activeSkill, bd.pos,hero));
    }
}

void BattleMgr::initNpc()
{
//    int groupID=this->groups.at(0);
//    this->groups.erase(groups.begin());
    this->view->resetProgress();

    std::vector<BData> npc;
    XMonster* xm=XMonster::record(Value(this->groupID));
    
    int mid=xm->getMID1();
    npc.push_back({mid/10,xm->getMLv1(),mid%10,xm->getMStar1(),5,this->getMonsterSkill(mid/10)});
    
    mid=xm->getMID2();
    if(mid > 0){
        npc.push_back({mid/10,xm->getMLv2(),mid%10,xm->getMStar2(),6,this->getMonsterSkill(mid/10)});
    }
    
    mid=xm->getMID3();
    if(mid > 0){
        npc.push_back({mid/10,xm->getMLv3(),mid%10,xm->getMStar3(),7,this->getMonsterSkill(mid/10)});
    }
    
    mid=xm->getMID4();
    if(mid > 0){
        npc.push_back({mid/10,xm->getMLv4(),mid%10,xm->getMStar4(),8,this->getMonsterSkill(mid/10)});
    }
    
    mid=xm->getMID5();
    if(mid > 0){
        npc.push_back({mid/10,xm->getMLv5(),mid%10,xm->getMStar5(),9,this->getMonsterSkill(mid/10)});
    }
    
    //init modle & view
    Size winSize=Director::getInstance()->getWinSize();
    sort(npc.begin(), npc.end(), this->sortGrid);
    
    for(int i=0;i<npc.size();i++)
    {
        FData* fd=FData::create(npc.at(i));
        FighterMgr* npc=FighterMgr::create(fd);
        this->npcs.pushBack(npc);
        //npc->start();
        npc->view->runAction(Sequence::create(DelayTime::create(0.3*i),CallFunc::create(CC_CALLBACK_0(FighterMgr::start, npc)), NULL));
    }
}

std::vector<int> BattleMgr::getMonsterSkill(int xid)
{
    std::vector<int> skills;
    XRole* xrole=XRole::record(Value(xid));
    
   
    int skill=xrole->getSkill0();
    if(skill>0){
        skills.push_back(skill);
    }
    skill=xrole->getSkill1();
    if(skill>0){
        skills.push_back(skill);
    }
    skill=xrole->getSkill2();
    if(skill>0){
        skills.push_back(skill);
    }
    skill=xrole->getSkill3();
    if(skill>0){
        skills.push_back(skill);
    }
    skill=xrole->getSkill4();
    if(skill>0){
        skills.push_back(skill);
    }
    skill=xrole->getSkill5();
    if(skill>0){
        skills.push_back(skill);
    }
    skill=xrole->getSkill6();
    if(skill>0){
        skills.push_back(skill);
    }
    skill=xrole->getSkill7();
    if(skill>0){
        skills.push_back(skill);
    }
    skills.push_back(xrole->getCommonSkill());
    return skills;
}

bool BattleMgr::sortGrid(BData f1,BData f2)
{
    int dis1=XRole::record(Value(f1.xid))->getLockGrid();
    int dis2=XRole::record(Value(f2.xid))->getLockGrid();
    return dis1-dis2<0;
}

std::vector<int> BattleMgr::getFoes(int pos,bool isMe)
{
    std::vector<int> arr;
    if((pos<5 && isMe) || (pos>4 && !isMe)){
        for(FighterMgr* mf : heros){
            if(mf->state==Efstate::die) continue;
            arr.push_back(mf->pos);
        }
    }
    if((pos<5 && !isMe) || (pos>4 && isMe)){
        for(FighterMgr* mf : npcs){
            if(mf->state==Efstate::die) continue;
            arr.push_back(mf->pos);
        }
    }
    return arr;
}

FighterMgr* BattleMgr::getFirst(int pos)
{
    FighterMgr* pRet=nullptr;
    if(pos > 4){
        pRet=heros.at(0);
        float px=pRet->view->getPositionX();
        if(pRet->state==Efstate::die){
            px=-10000;
        }
        for(FighterMgr* mf : heros){
            if(mf->state==Efstate::die){
                continue;
            }
            float mfPx=mf->view->getPositionX();
            if(mfPx > px){
                pRet=mf;
                px=mfPx;
            }
        }
    }else{
        pRet=npcs.at(0);
        float px=pRet->view->getPositionX();
        if(pRet->state==Efstate::die){
            px=10000;
        }
        for(FighterMgr* mf :npcs){
            if(mf->state==Efstate::die){
                continue;
            }
            float mfPx=mf->view->getPositionX();
            if(mfPx < px){
                pRet=mf;
                px=mfPx;
            }
        }
    }
    return pRet;
}

FighterMgr* BattleMgr::getHero(int pos){
    if(pos<5){
        for(FighterMgr* mf : heros){
            if(mf->pos==pos){
                return mf;
            }
        }
    }else{
        for(FighterMgr* mf : npcs){
            if(mf->pos==pos){
                return mf;
            }
        }
    }
    return nullptr;
}

void BattleMgr::adjustPosition(FighterMgr *fm)
{
    Vector<FighterMgr*> arr=fm->pos>4?this->npcs:this->heros;
    for(FighterMgr* fighter : arr){
        if(fighter->pos == fm->pos){
            continue;
        }
        if(abs(fighter->view->getPositionX() - fm->view->getPositionX())<5  &&  abs(fighter->view->getPositionY() - fm->view->getPositionY())<5 ){
            if(fm->view->getZOrder() > fighter->view->getZOrder()){
                fighter->view->move(Vec2(0,50));
            }else{
                fm->view->move(Vec2(0,50));
            }
            break;
        }
    }
}

void BattleMgr::stopAllFighter()
{
    for(FighterMgr* npc :heros){
        npc->stop();
    }
    for(FighterMgr* npc : npcs){
        npc->stop();
    }
    for(SkillIcon* icon : skillIcons){
        icon->pause();
    }
}

void BattleMgr::startEndDram()
{
    this->isOver=true;
    for(FighterMgr* mf : heros) {
        mf->view->setVisible(false);
        mf->stop();
    }
    DramaAni::getInstance()->startEnd(this->groupID,this->gateID,this->nodeID);
}

void BattleMgr::handleResult()
{
    if(this->winPos<5 && this->groups.size()){
        for(FighterMgr* fm : BattleMgr::getInstance()->heros){
            if(fm->state==Efstate::die){
                continue;
            }
            fm->view->setVisible(true);
            fm->view->run();
            int healHp=fm->mf->data->fullHp*0.5;
            fm->mf->data->hp=MIN(fm->mf->data->hp+healHp, fm->mf->data->fullHp);
            
            PHit phit;
            phit.set_hp(-healHp);
            phit.set_perhp(fm->mf->data->hp*100.0/fm->mf->data->fullHp);
            
            fm->view->fallHp(phit);
        }
        Director::getInstance()->getScheduler()->schedule(SEL_SCHEDULE(&BattleMgr::startBattle), this, 0, 0, 4, false);
        this->view->setNpcIcon(npcNum-this->groups.size(),true);
    }else{
        PResultReq pResultReq;
        pResultReq.set_gateid(this->gateID);
        pResultReq.set_xid(this->nodeID);
        if(this->winPos>4){
            pResultReq.set_star(0);
        }
        if(this->groups.size()==0){
            pResultReq.set_star(MAX(1,3-(this->heroNum-this->heros.size())));
        }
        Manager::getInstance()->socket->send(C_FIGHTRESULT, &pResultReq);
    }
}
//超时失败
void BattleMgr::overTime()
{
    this->stopAllFighter();
    PResultReq pResultReq;
    pResultReq.set_gateid(this->gateID);
    pResultReq.set_xid(this->nodeID);
    pResultReq.set_star(0);
    Manager::getInstance()->socket->send(C_FIGHTRESULT, &pResultReq);
}

void BattleMgr::clearDieNpc(int pos)
{
    FighterMgr* fm=this->getHero(pos);
    if(pos<5 && pos>-1){
        this->heros.eraseObject(fm);
    }else if(pos>4 && pos<10){
        this->npcs.eraseObject(fm);
    }else{
        log("die error pos:%d",pos);
    }
}

void BattleMgr::pause()
{
    for(FighterMgr* hero : this->heros){
        hero->pause();
    }
    for(FighterMgr* npc : this->npcs){
        npc->pause();
    }
    this->view->pause();
    
    for(Node* child : this->view->getChildren()){
        child->pause();
    }
    for(Node* child : this->view->skillNode->getChildren()){
        child->pause();
        for(Node* soon : child->getChildren()){
            soon->pause();
        }
    }
}

void BattleMgr::resume()
{
    for(FighterMgr* hero : this->heros){
        hero->resume();
    }
    for(FighterMgr* npc : this->npcs){
        npc->resume();
    }
    this->view->resume();
    for(Node* child : this->view->getChildren()){
        child->resume();
    }
    for(Node* child : this->view->skillNode->getChildren()){
        child->resume();
        for(Node* soon : child->getChildren()){
            soon->resume();
        }
    }
}

void BattleMgr::clear()
{
    this->heros.clear();
    this->npcs.clear();
    this->groups.clear();
    this->skillIcons.clear();
    SpriteFrameCache::getInstance()->removeSpriteFrames();
}