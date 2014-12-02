//
//  Skill.cpp
//  fancyHeart
//
//  Created by 秦亮亮 on 14-6-4.
//
//

#include "Skill.h"
Skill* Skill::create(int skillID)
{
    Skill* pRet=new Skill();
    if(pRet && pRet->init(skillID))
    {
        pRet->autorelease();
        return pRet;
    }
    CC_SAFE_DELETE(pRet);
    return nullptr;
}

bool Skill::init(int skillID)
{
    this->skillID=skillID;
    return true;
}

void Skill::start()
{    
    //被动技能走CD
    XSkill* xskill=XSkill::record(Value(skillID));
    if(!Director::getInstance()->getScheduler()->isTargetPaused(this)){
        Director::getInstance()->getScheduler()->schedule(SEL_SCHEDULE(&Skill::coldDown),this,xskill->getTriggerParam()/10000.0,false);
    }
}

void Skill::coldDown(float dt)
{
    //0主动 1被动 3普通
    //普通攻击 和 怪物的主动技能 cd到了直接使用
    this->isReady=true;
    XSkill* xs=XSkill::record(Value(skillID));
    int skillType=xs->getType();
    if(skillType==3 || (skillType==0 && fm->pos>4)){
        this->fm->startAttack(this);
    }
}

//选择策略
std::vector<int> Skill::selectStrategy(std::vector<int> arr,int num)
{
    std::vector<int> targets;
    XSkill* xskill=XSkill::record(Value(skillID));
    int type=xskill->getSelectType();
    switch(type){
        case 0: //普通-  只能选中一次（可能多目标）
        {
            num=MIN(num, (int)arr.size());
            if(arr.size()==0 || num==0){
                break;
            }
            std::vector<int> vec=Utils::randSeveral((int)arr.size(),num,false);
            for(int i=0;i<num;i++)
            {
                targets.push_back(arr.at(vec[i]));
            }
            break;
        }
        case 1: //随机- 可以选中多次  （可能多目标）
        {
            if(arr.size()==0 || num==0){
                break;
            }
            std::vector<int> vec=Utils::randSeveral((int)arr.size(),num,true);
            for(int i=0;i<num;i++)
            {
                targets.push_back(arr.at(vec[i]));
            }
            break;
        }
        case 2://血最少- （只有一个）
        {
            //num=MIN(num, arr.size());
            sort(arr.begin(), arr.end(), [](int pos1, int pos2) {
                FighterMgr* f1=BattleMgr::getInstance()->getHero(pos1);
                FighterMgr* f2=BattleMgr::getInstance()->getHero(pos2);
                return f1->mf->data->hp<f2->mf->data->hp;
            });

            if(arr.size()>0){
                targets.push_back(arr.at(0));
            }
            break;
        }
        case 3: //血最多 （只有一个）
        {
            //num=MIN(num, arr.size());
            sort(arr.begin(),arr.end(),[](int pos1,int pos2){
                FighterMgr* f1=BattleMgr::getInstance()->getHero(pos1);
                FighterMgr* f2=BattleMgr::getInstance()->getHero(pos2);
                return f1->mf->data->hp>f2->mf->data->hp;
            });
            if(arr.size()>0){
                targets.push_back(arr.at(0));
            }
            break;
        }
        case 4: //离得近- （只有一个）
        {
            //num=MIN(num, arr.size());
            sort(arr.begin(),arr.end(),[](int pos1,int pos2){
                FighterMgr* f1=BattleMgr::getInstance()->getHero(pos1);
                FighterMgr* f2=BattleMgr::getInstance()->getHero(pos2);
                return f1->getGrid()-f2->getGrid() < 0;
            });
            if(arr.size()>0){
                targets.push_back(arr.at(0));
            }
            break;
        }
        case 5: //离得远 （只有一个）
        {
            //num=MIN(num, arr.size());
            sort(arr.begin(),arr.end(),[](int pos1,int pos2){
                FighterMgr* f1=BattleMgr::getInstance()->getHero(pos1);
                FighterMgr* f2=BattleMgr::getInstance()->getHero(pos2);
                return f1->getGrid()-f2->getGrid() > 0;
            });
            if(arr.size()>0){
                targets.push_back(arr.at(0));
            }
            break;
        }
        default:
            log("错误的选择策略skillID:%d,type:%d",this->skillID,type);
            break;
    }
    return targets;
}

int Skill::getType()
{
    return XSkill::record(Value(skillID))->getType();
}

void Skill::pause()
{
    Director::getInstance()->getScheduler()->pauseTarget(this);
}

void Skill::resume()
{
    Director::getInstance()->getScheduler()->resumeTarget(this);
}

void Skill::stop()
{
    Director::getInstance()->getScheduler()->unscheduleAllForTarget(this);
}

Skill::~Skill()
{
    Director::getInstance()->getScheduler()->unschedule(SEL_SCHEDULE(&Skill::coldDown),this);
}