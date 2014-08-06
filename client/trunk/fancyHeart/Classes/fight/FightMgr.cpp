//
//  FightMgr.cpp
//  fancyHeart
//
//  Created by 秦亮亮 on 14-5-15.
//
//

#include "FightMgr.h"

static FightMgr* instance=nullptr;

FightMgr* FightMgr::getInstance(){
    if(instance==nullptr){
        instance=new FightMgr();
    }
    return instance;
}

void FightMgr::init(std::vector<long>heros,int nodeID,int gateID)
{
    auto scene=FightScene::createScene();
    Manager::getInstance()->switchScence(scene);

    this->gateID=gateID;
    this->nodeID=nodeID;
    this->view=(FightScene*)scene->getChildByTag(0);
   
    XNode* xn=XNode::record(Value(nodeID));
    
    int battleID=xn->getBattleID();
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
    this->view->setNpcIcon(this->npcNum,false);
    
    this->initHero(heros);
    this->initSkill();

    this->groupID=this->groups.at(0);
    this->groups.erase(groups.begin());

    DramaAni::getInstance()->startPre(this->groupID);
}

void FightMgr::init(rapidjson::Value& data)
{
    
}

void FightMgr::startBattle()
{
    this->isOver=false;
    this->view->bg->setDisplayFrame(Sprite::create("fightBg2.png")->displayFrame());
    FightMgr::getInstance()->npcs.clear();
    this->groupID=this->groups.at(0);
    this->groups.erase(groups.begin());
    this->initNpc();
    
    //hero
    Size winSize=Director::getInstance()->getWinSize();
    for(int i=0;i<this->heros.size();i++){
        MFighter* mf=FightMgr::getInstance()->heros.at(i);
        int pos=mf->data->bd.pos;
        mf->view->setPosition(Vec2(-GRID_SIZE*2*i,winSize.height/2+(pos%2?-GRID_SIZE:GRID_SIZE)));
        this->view->heroNode->reorderChild(mf->view, 640-abs(mf->view->getPositionY()));
        mf->state=fstate::idle;
        mf->start();
        for(Skill* skill : mf->skills){
            skill->resume();
        }
    }
}

void FightMgr::initHero(std::vector<long> hero)
{
    //初始化角色100
    std::vector<int> heros={100,101,101,102,999};
    Size winSize=Director::getInstance()->getWinSize();
    sort(heros.begin(),heros.end(), FightMgr::sortGrid);

    Vector<FData*> arr;
    for(int i=0;i<heros.size();i++)
    {
        int xid=heros.at(i);
        //xid lv rate star pos
        BData bd={xid,1,1,1,i};
        if(xid==999 || xid==998){
            bd={xid,1,9,1,i};
        }
        FData* fd=FData::create(bd);
        int pos = fd->bd.pos;
        MFighter* mf=MFighter::create(fd);
        mf->view=Fview::create("man animation", "man animation0", pos);
        //mf->view->setPosition(Vec2(-GRID_SIZE*2*i,winSize.height/2+(pos%2?-GRID_SIZE:GRID_SIZE)));
        mf->view->setPosition(Vec2(-100,0));
        this->view->addChild(mf->view);
        mf->view->delegate=mf;
        this->heros.pushBack(mf);
    }
}

void FightMgr::initNpc()
{
//    int groupID=this->groups.at(0);
//    this->groups.erase(groups.begin());
    this->view->resetProgress();

    std::vector<int> npc;
    XMonster* xm=XMonster::record(Value(this->groupID));
    
    int mid=xm->getMID1();
    npc.push_back(mid/10);
    
    mid=xm->getMID2();
    if(mid > 0){
        npc.push_back(mid/10);
    }
    
    mid=xm->getMID3();
    if(mid > 0){
       npc.push_back(mid/10);
    }
    
    mid=xm->getMID4();
    if(mid > 0){
        npc.push_back(mid/10);
    }
    
    mid=xm->getMID5();
    if(mid > 0){
        npc.push_back(mid/10);
    }
    
    //init modle & view
    Size winSize=Director::getInstance()->getWinSize();
    sort(npc.begin(), npc.end(), this->sortGrid);
    
    for(int i=0;i<npc.size();i++)
    {
        BData bd={npc.at(i),xm->getMLv2(),mid%10,xm->getMStar2(),i+5};
        FData* fd=FData::create(bd);
        int pos = fd->bd.pos;
        MFighter* mf=MFighter::create(fd);
        mf->view=Fview::create("man animation", "man animation0", pos);
        mf->view->setPosition(Vec2(GRID_SIZE*2*i+winSize.width,winSize.height/2+(pos%2?GRID_SIZE:-GRID_SIZE)));
        this->view->heroNode->addChild(mf->view,640-mf->view->getPositionY());
        mf->view->delegate=mf;
        this->npcs.pushBack(mf);
        
        mf->start();
    }
}

void FightMgr::initSkill()
{
    std::vector<int> skills={15201,15301,15401,15501,15601};
    //普通攻击特殊处理
    for(int i=0;i<skills.size();i++){
        this->view->skillIcons.at(i)->setTag(skills.at(i));
        this->view->skillIcons.at(i)->setVisible(true);
    }
    
}

void FightMgr::skillAttack(int skill)
{
    SpriteFrameCache::getInstance()->addSpriteFramesWithFile("effect/ziguang.plist");

    for(MFighter* mf : this->npcs){
        mf->fallHp(20);
        mf->view->fallHp(Value(20));
    }
    Clip* clip=Clip::create("effect/ziguang.plist", 12);
    this->view->addChild(clip,1);
    clip->setPosition(npcs.at(0)->view->getPosition());
    clip->play();
    
    roleMp-=XSkill::record(Value(-skill))->getMp()*BEAN_NUM;
}

bool FightMgr::sortGrid(int f1,int f2)
{
    int dis1=XRole::record(Value(f1))->getLockGrid();
    int dis2=XRole::record(Value(f2))->getLockGrid();
    return dis1-dis2<0;
}

std::vector<int> FightMgr::getFoes(int pos,bool isMe)
{
    std::vector<int> arr;
    if((pos<5 && isMe) || (pos>4 && !isMe)){
        for(MFighter* mf : heros){
            if(mf->state==fstate::die || mf->state==fstate::over) continue;
            arr.push_back(mf->pos);
        }
    }
    if((pos<5 && !isMe) || (pos>4 && isMe)){
        for(MFighter* mf : npcs){
            if(mf->state==fstate::die || mf->state==fstate::over) continue;
            arr.push_back(mf->pos);
        }
    }
    return arr;
}

MFighter* FightMgr::getFirst(int pos)
{
    MFighter* pRet=nullptr;
    if(pos > 5){
        pRet=heros.at(0);
        for(MFighter* mf : heros){
            if(mf->view->getPositionX()>pRet->view->getPositionX()){
                pRet=mf;
            }
        }
    }else{
        pRet=npcs.at(0);
        for(MFighter* mf :npcs){
            if(mf->view->getPositionX()<pRet->view->getPositionX()){
                pRet=mf;
            }
        }
    }
    return pRet;
}

MFighter* FightMgr::getRole()
{
    if(this->heros.size()<2){
        return nullptr;
    }
    for(auto mf : this->heros){
        if(mf && mf->getIsRole()){
            return mf;
        }
    }
    return nullptr;
}

MFighter* FightMgr::getHero(int pos){
    if(pos<5){
        for(MFighter* mf : heros){
            if(mf->pos==pos){
                return mf;
            }
        }
    }else{
        for(MFighter* mf : npcs){
            if(mf->pos==pos){
                return mf;
            }
        }
    }
    return nullptr;
}

void FightMgr::stopAllFighter()
{
    for(MFighter* mf :heros){
        mf->pause();
        if(mf->state!=fstate::die){
            mf->state=fstate::over;
        }
    }
    for(MFighter* npc : npcs){
        npc->pause();
        if(npc->state!=fstate::die){
            npc->state=fstate::over;
        }
    }
}

void FightMgr::startEndDram()
{
    this->isOver=true;

    for(MFighter* mf : heros) {
        mf->view->setVisible(false);
        mf->pause();
    }
    DramaAni::getInstance()->startEnd(this->groupID);
}

void FightMgr::handleResult()
{
    if(this->winPos<5 && this->groups.size()){
        for(MFighter* mf : FightMgr::getInstance()->heros){
            if(mf->state==fstate::die){
                continue;
            }
            mf->view->setVisible(true);
            mf->view->run();
        }
        Director::getInstance()->getScheduler()->schedule(SEL_SCHEDULE(&FightMgr::startBattle), this, 5, 0, 5, false);
        this->view->setNpcIcon(npcNum-this->groups.size(),true);
    }
    
    if(this->winPos>4 || this->groups.size()==0){
        PResultReq pResultReq;
        pResultReq.set_gateid(this->gateID);
        pResultReq.set_xid(this->nodeID);
        pResultReq.set_star(3);
        Manager::getInstance()->socket->send(C_FIGHTRESULT, &pResultReq);
    }
}

void FightMgr::clear()
{
    this->heros.clear();
    this->npcs.clear();
    this->groups.clear();
}