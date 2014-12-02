 //
//  FighterMgr.cpp
//  fancyHeart
//
//  Created by 秦亮亮 on 14-8-14.
//
//

#include "FighterMgr.h"
FighterMgr* FighterMgr::create(FData* data)
{
    FighterMgr* pRet=new FighterMgr();
    if(pRet && pRet->init(data)){
        pRet->autorelease();
        return pRet;
    }
    CC_SAFE_DELETE(pRet);
    return nullptr;
}

bool FighterMgr::init(FData *data)
{
    this->pos = data->bd.pos;
    Size winSize=Director::getInstance()->getWinSize();

    this->mf=MFighter::create(data);
    this->mf->retain();
    this->initSkill(data->bd.skills);
    
    XRole* xrole=XRole::record(Value(data->bd.xid));
    this->view=VFighter::create(xrole->getAvatar()+".json", xrole->getAvatar()+".atlas", pos);
    this->view->setPosition(Vec2(GRID_SIZE*3*(pos%5)+winSize.width +50, winSize.height/2+(pos%2?GRID_SIZE*0.8:-GRID_SIZE*0.8)-150));
    BattleMgr::getInstance()->view->heroNode->addChild(view,960-view->getPositionY());
    
    return true;
}

void FighterMgr::start()
{
    Director::getInstance()->getScheduler()->schedule(SEL_SCHEDULE(&FighterMgr::checkRun), this, 0, false );
    Director::getInstance()->getScheduler()->schedule(SEL_SCHEDULE(&FighterMgr::passiveAttack), this, 5, false);
    
    for(Skill* skill : skills){
        skill->start();
    }
    
    this->state=Efstate::idle;
    this->startAttack(skills.back());
}

void FighterMgr::initSkill(std::vector<int> skills)
{
    for(int skillID : skills){
        Skill* skill=Skill::create(skillID);
        skill->fm=this;
        this->skills.pushBack(skill);
    }
}

void FighterMgr::checkRun(float dt)
{
    if(this->state==Efstate::run){
        FighterMgr* rf=BattleMgr::getInstance()->getFirst(pos);
        if(abs(this->getGrid() - rf->getGrid()) <= mf->getLockGrid()*2){
            this->view->stopActionByTag(ACTION_RUN_TAG);
            this->state=Efstate::idle;
            this->startAttack(this->skill);

            BattleMgr::getInstance()->adjustPosition(this);
        }
    }
}

void FighterMgr::passiveAttack()
{
    Skill* skill=this->selectSkill();
    
    this->startAttack(skill);
}

void FighterMgr::startAttack(Skill* skill)
{
    //0 主动 1被动 3 普通攻击
    if(this->skill && this->skill->getType() < skill->getType()){
        return;
    }
    
    if(this->state == Efstate::onAir){
        this->skill=nullptr;
        return;
    }
    
    if(this->state != Efstate::idle){
        return;
    }
    
    this->skill=skill;
    //取mf普通攻击距离 和默认目标的位置计算是否移动
    FighterMgr* rf=BattleMgr::getInstance()->getFirst(pos);
    //检查攻击距离
    if(abs(this->getGrid() - rf->getGrid()) > mf->getLockGrid()*2){
        this->view->run();
        this->state=Efstate::run;
        return;
    }
    this->view->stopActionByTag(ACTION_SHOOT_TAG);
    this->preHit();
}


void FighterMgr::preHit()
{
    if(this->state==Efstate::die || this->state==Efstate::win){
        return;
    }
    
    //施法时间 1舜发 2吟唱（引导） 3蓄力
    XSkill* xskill=XSkill::record(Value(skill->skillID));
    XSkillEffect* xse=XSkillEffect::record(Value(xskill->getEffectID()));
   
    this->targets=this->selectTarget();
    
    if(this->targets.size() ==0 ){
        return;
    }
    
    float delay=xskill->getSpellTime()/10000.0 + xse->getFrameNum()/60.0;
    
    //引导
    if(xskill->getLeadNum()>0){
        this->view->spell(xse->getSpell());
        this->state=Efstate::spell;
        //Director::getInstance()->getScheduler()->schedule(SEL_SCHEDULE(&FighterMgr::cast), this,0,0,vec.x/60+vec.y/10000.0, false);
    }
    //射击  攻击动作-》弹道-》伤害
    else if(xskill->getRangeType()==4 && xskill->getRangeParam2()>0){
        this->view->attack(xse->getCast());
        this->state=Efstate::shoot;
        Sequence* seq=Sequence::create(DelayTime::create(delay),CallFunc::create(std::bind(&FighterMgr::shoot,this)), NULL);
        seq->setTag(ACTION_SHOOT_TAG);
        this->view->runAction(seq);
    }
    else{  //瞬发攻击
        this->view->attack(xse->getCast());
        for(int i=0;i<this->targets.size();i++){
            FighterMgr* fm=BattleMgr::getInstance()->getHero(targets.at(i));
            PHit phit=this->mf->hit(fm, this->skill);
            phit.set_isignore(true);
            phit.set_xse(xskill->getEffectID());
            fm->mf->resetData(phit);
        
            Sequence* sq=Sequence::create(DelayTime::create(delay),CallFunc::create(std::bind(&FighterMgr::attacked,fm,this,phit)), NULL);
            sq->setTag(ACTION_ATTACKED_TAG);
            
            fm->view->runAction(sq);
        }

        this->skill=nullptr;
    }
}

void FighterMgr::shoot()
{
    if(!this->skill){
        return;
    }
    XSkill* xskill=XSkill::record(Value(skill->skillID));
    int maxArrow=0;
    int maxDuration=0;
    for(int i=0;i<this->targets.size();){
        int pos=this->targets.at(i);
        
        this->targets.erase(targets.begin());
        int arrowCount=1;
        
        vector<int>::iterator it =find(targets.begin(), targets.end(), pos);
        while (it!=targets.end()) {
            arrowCount++;
            this->targets.erase(it);
            it=find(targets.begin(), targets.end(), pos);
        }
        
        FighterMgr* fm=BattleMgr::getInstance()->getHero(pos);
        if(!fm || fm->state==Efstate::die){
            continue;
        }
        
        /*
        float duration=this->view->lineThrow(xskill->getEffectID(),fm->view,arrowCount);

        for(int j=0;j<arrowCount;j++){
            PHit phit=this->mf->hit(fm, this->skill);
            CallFunc* fun=CallFunc::create(std::bind(&FighterMgr::attacked,fm,this,phit));
            this->view->runAction(Sequence::create(DelayTime::create(duration+ARROW_GAP*j),fun, NULL));
        }
        */

        //--------------------------------------
        ///*
        float duration=this->view->trackThrow(xskill->getEffectID(), fm->view, arrowCount);
        for(int j=0;j<arrowCount;j++){
            PHit phit=this->mf->hit(fm, this->skill);
            phit.set_xse(xskill->getEffectID());
            //CallFunc* fun=CallFunc::create(std::bind(&FighterMgr::attacked,fm,this,phit));
            //this->view->runAction(Sequence::create(DelayTime::create(duration+ARROW_GAP*j),fun, NULL));
            CallFunc* fun=CallFunc::create(std::bind(&FighterMgr::attacked,fm,this,phit));
            Sequence* sq=Sequence::create(DelayTime::create(duration+ARROW_GAP*j),fun, NULL);
            sq->setTag(ACTION_ATTACKED_TAG);
            fm->view->runAction(sq);
        }
        //*/
        //--------------------------------------
        
        if(arrowCount > maxArrow){
            maxArrow=arrowCount;
        }
        if(duration > maxDuration){
            maxDuration=duration;
        }
    }
    this->state=Efstate::idle;
    this->skill=nullptr;
}

void FighterMgr::impale()
{
    XSkill* xskill=XSkill::record(Value(skill->skillID));
    XSkillEffect* xse=XSkillEffect::record(Value(xskill->getEffectID()));
    //穿透 一个个来
    if(xskill->getRangeType()==6){
        this->view->impaleThrow(xse->getBullet());
        for(int i=0;i<this->targets.size();i++){
            //FighterMgr* mf=BattleMgr::getInstance()->getHero(targets.at(i));
            //CallFuncN* fun=CallFuncN::create(CC_CALLBACK_0(FighterMgr::hitOne, this,mf));
            //this->view->runAction(Sequence::create(DelayTime::create(0.1*i),fun, NULL));
        }
        return;
    }
}

void FighterMgr::bounce()
{
    XSkill* xskill=XSkill::record(Value(skill->skillID));
    //弹射，一个个来
    if(xskill->getRangeType()==5){
        for(int i=0;i<this->targets.size();i++){
            FighterMgr* mf=BattleMgr::getInstance()->getHero(targets.at(i));
            float duration=this->view->lineThrow(xskill->getEffectID(),mf->view,1);
            CallFuncN* fun=CallFuncN::create( CC_CALLBACK_0(FighterMgr::bounce, this));
            this->view->runAction(Sequence::create(DelayTime::create(duration+0.2*i),fun, NULL));
        }
        return;
    }
    
    FighterMgr* mf=BattleMgr::getInstance()->getHero(targets.at(0));
    this->targets.erase(targets.begin());
    if(this->targets.size()>0){
        BattleMgr::getInstance()->view->bounceTo(mf);
    }
}

void FighterMgr::attacked(FighterMgr* attacker,PHit& phit)
{
    if(this->state==Efstate::die){
        return;
    }
    
    if(phit.isdie()){
        
        this->state=Efstate::die;
        this->stop();
        if(this->state==Efstate::onAir){
            this->view->isDie=true;
        }else{
            this->view->die(phit);
        }

        if(this->pos<5){
            for(SkillIcon* skillIcon : BattleMgr::getInstance()->skillIcons){
                if(!skillIcon->hero){
                    continue;
                }
                if (skillIcon->hero->pos == this->pos) {
                    skillIcon->heroDie();
                    break;
                }
            }
        }

        Vector<FighterMgr*> arr=this->pos<5?BattleMgr::getInstance()->heros:BattleMgr::getInstance()->npcs;
        bool isFightOver=true;

        for(int i=0;i<arr.size();i++){
            FighterMgr* npc=arr.at(i);
            if(npc->state == Efstate::die){
                continue;
            }
            isFightOver=false;
            break;
        }
        
        if(isFightOver){
            //战斗结果处理
            attacker->view->win();
            attacker->state=Efstate::win;
            BattleMgr::getInstance()->stopAllFighter();
            return;
        }
    }
    else if (phit.ishitonair() && this->state != Efstate::onAir) {
        this->state=Efstate::onAir;
        this->resetSkill(SkillState::SKILL_PAUSE);
        this->view->attackedOnAir(phit);
    }
    else if(phit.isdefence() && this->state!=Efstate::onAir){
        this->view->defence(phit);
    }
    else{
        this->view->attacked(phit);
    }
    
    if(!phit.isignore()){
        this->mf->resetData(phit);
    }
}

void FighterMgr::hit()
{
    
}

void FighterMgr::stopCast()
{
    //Director::getInstance()->getScheduler()->unschedule(SEL_SCHEDULE(&FighterMgr::cast), this);
}

//被动技能没有CD
Skill* FighterMgr::selectSkill()
{
    Vector<Skill*> readys;
    for(Skill* skill : skills){
        if(XSkill::record(Value(skill->skillID))->getType()==1){
            readys.pushBack(skill);
        }
    }
    if(readys.size()==0){
        return nullptr;
    }else{
        return readys.at(rand()%readys.size());
    }
}

void FighterMgr::stop()
{
    Director::getInstance()->getScheduler()->unscheduleAllForTarget(this);
    this->resetSkill(SkillState::SKILL_STOP);
}

void FighterMgr::pause()
{
    Director::getInstance()->getScheduler()->pauseTarget(this);
    this->view->stopActionByTag(ACTION_RUN_TAG);
    this->view->pause();
    for(Node* child : this->view->getChildren()){
        child->pause();
    }
    this->resetSkill(SkillState::SKILL_PAUSE);
}

void FighterMgr::resume()
{
    Director::getInstance()->getScheduler()->resumeTarget(this);
    this->view->resume();
    for(Node* child : this->view->getChildren()){
        child->resume();
    }
    this->resetSkill(SkillState::SKILL_RESUME);
}

float FighterMgr::getGrid()
{
    return this->view->getPositionX();
}

std::vector<int> FighterMgr::selectTarget()
{
    std::vector<int> arr;

    XSkill* xSkill=XSkill::record(Value(skill->skillID));
   
    switch (xSkill->getRangeType()) {
        case 0: //自身 ==没有浮空
            arr.push_back(this->pos);
            break;
        case 1: //我方 影响人数 all  ==没有浮空
            arr=BattleMgr::getInstance()->getFoes(this->pos,true);
            arr=skill->selectStrategy(arr,xSkill->getRangeParam1());
            break;
        case 2: //近战 影响人数 all
            arr=BattleMgr::getInstance()->getFoes(this->pos,false);
            for(int i=0;i<arr.size();i++){
                FighterMgr* fm=BattleMgr::getInstance()->getHero(arr[i]);
                if(abs(this->getGrid() - fm->getGrid()) > mf->getLockGrid()*2){
                    arr.erase(arr.begin()+i);
                    i--;
                }
            }
            arr=skill->selectStrategy(arr,xSkill->getRangeParam1());
            break;
        case 3:{ //爆发 前排/中排/后排
            std::vector<int> vec=BattleMgr::getInstance()->getFoes(this->pos);
            int row1=0,row2=0;
            
            for(int mPos : vec){
                FighterMgr* fm=BattleMgr::getInstance()->getHero(mPos);
                if(fm->mf->data->getRow() == 0){
                    if(row1 <= xSkill->getRangeParam1()){
                        arr.push_back(mPos);
                        row1++;
                    }
                    continue;
                }
                if(fm->mf->data->getRow() == 2){
                    if(row2 <= xSkill->getRangeParam2()){
                        arr.push_back(mPos);
                        row2++;
                    }
                }
            }
            break;
        }
        case 4: //射击  最大距离/子弹数量 all
            arr=BattleMgr::getInstance()->getFoes(this->pos);
            for(int i=0;i<arr.size();i++)
            {
                int pos=arr[i];
                FighterMgr* mf=BattleMgr::getInstance()->getHero(arr[i]);
                if(mf->state==Efstate::onAir && xSkill->getIsAirFirst()){
                    arr.clear();
                    arr.push_back(pos);
                    break;
                }
                if(abs(mf->getGrid()-mf->getGrid()) > xSkill->getRangeParam1())
                {
                    arr.erase(arr.begin()+i);
                }
            }
            arr=skill->selectStrategy(arr,xSkill->getRangeParam2());
            break;
        case 5: //弹射 弹射次数
        {
            std::vector<int> vec=BattleMgr::getInstance()->getFoes(this->pos);
            int bondNum=xSkill->getRangeParam1();
            for(int i=0;i<bondNum;i++)
            {
                arr.push_back(vec.at(i%vec.size()));
            }
        }
            break;
        case 6: //穿透 最大距离 ==没有浮空
        {
            std::vector<int> vec=BattleMgr::getInstance()->getFoes(this->pos);
            for(int mPos : vec)
            {
                FighterMgr* mf=BattleMgr::getInstance()->getHero(mPos);
                if(mf->getGrid()-mf->getGrid() <= xSkill->getRangeParam1()){
                    arr.push_back(mPos);
                }
            }
        }
            break;
        default:
            log("error range type:%d,skillID:%d",xSkill->getRangeType(),skill->skillID);
            break;
    }
    return arr;
}

void FighterMgr::resetSkill(SkillState state)
{
    for(Skill* skill : skills){
        switch (state) {
            case SkillState::SKILL_PAUSE:
                skill->pause();
                break;
            case SkillState::SKILL_RESUME:
                skill->resume();
                break;
            case SkillState::SKILL_STOP:
                skill->stop();
                break;
        }
    }
}

FighterMgr::~FighterMgr()
{
    Director::getInstance()->getScheduler()->unscheduleAllForTarget(this);
    this->skills.clear();
    this->mf->release();
    this->view->removeFromParent();
}