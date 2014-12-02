//
//  FighterCtrl.cpp
//  fancyHeart
//
//  Created by 秦亮亮 on 14-5-5.
//
//

#include "MFighter.h"
#include "BattleScene.h"
MFighter* MFighter::create(FData* fd)
{
    MFighter* pRet=new MFighter();
    if(pRet && pRet->init(fd))
    {
        pRet->autorelease();
        return pRet;
    }else{
        delete pRet;
        pRet = NULL;
        return NULL;
    }
}

bool MFighter::init(FData* fd)
{
    this->data=fd;
    this->data->retain();
    //初始化技能
    this->initSkill();
    return true;
}

void MFighter::initSkill()
{
    //std::vector<int>  allSkills={15201,15301,15401,15501,15601,15701,15801,15901,16001,16101};
    //std::vector<int>  allSkills={15701,15801,15901,16001,16101};
//    XRole* xr=XRole::record(Value(data->bd.xid));
//    this->skills.pushBack(Skill::create(xr->getCommonSkill()));
//    this->skills.pushBack(Skill::create(xr->getSkill1()));
  
    for(int skillID : this->data->bd.skills){
        this->skills.pushBack(Skill::create(skillID));
    }
    
}

void MFighter::start()
{
    Director::getInstance()->getScheduler()->resumeTarget(this);
    for(Skill* skill : skills){
        skill->resume();
    }
}

void MFighter::pause()
{
    Director::getInstance()->getScheduler()->pauseTarget(this);
    for(Skill* skill : skills){
        skill->stop();
    }
}

int MFighter::getLockGrid()
{
    XRole* xrole=XRole::record(Value(data->bd.xid));
    return xrole->getLockGrid();
}



int MFighter::autoHeal()
{
    XRoleData* xrd=XRoleData::record(Value(data->bd.xid*10+data->bd.rate));
    int healHp=xrd->getHeal();
    this->data->hp+=healHp;
    return healHp;
}

/*
 //2个自动攻击的技能（优先使用，随机取一个，会有自己的CD和公共冷却时间）
 */
Skill* MFighter::selectSkill()
{
    Vector<Skill*> readys;
    for(Skill* skill : skills){
        if(skill->isReady){
            readys.pushBack(skill);
        }
    }
    if(readys.size()==0){
        return nullptr;
    }else{
        return readys.at(rand()%readys.size());
    }
}



//击打
PHit MFighter::hit(MFighter *mf,Skill* skill)
{
    PHit pHit;

//    if(mf->state==fstate::die || this->currentSkill==nullptr){
//        return pHit;
//    }
    bool isSub=false;
    //预估 命中 爆击 免疫
    //A	若目标携带物理免疫buff则物理伤害类技能无法生效
    //B	若目标携带法术免疫buff则法术伤害类技能无法生效
    //C	若目标携带治疗免疫buff则目标无法被治疗
    //命中率=（90+攻方等级-防方等级-防方闪避）*1%

    pHit.set_ismiss(false);
    XSkill* xskill=XSkill::record(Value(skill->skillID));
    
    for(Buff* buff : mf->buffs){
        //XRole* xrole=XRole::record(Value(this->data->bd.xid));
        //0物理 1法术

        if((xskill->getHurtType()==0 && buff->isAtkMiss()) ||
           (xskill->getHurtType()==1 && buff->isMatkMiss()) ||
           (xskill->getHurtType()==2 && buff->isHeal())
          )
        {
            pHit.set_ismiss(true);
        }
    }
    float hitRate=(90+this->data->bd.lv-mf->data->bd.lv-mf->data->miss)/0.01;
    hitRate=1;
    if(Utils::random01() > hitRate){ //miss
        pHit.set_ismiss(true);
    }
    
    
    if(pHit.ismiss()){
//        mf->attacked(pHit);
        return pHit;
    }
    
    if(Utils::random01() > hitRate) //miss
    {
        pHit.set_ismiss(true);
//        mf->attacked(pHit);
        return pHit;
    }
    
    /*
     A	暴击率=（攻方等级-防方等级+10+暴击率调整指数）*1%
     B	最大暴击率为80%
     C	最小暴击率为1%
     D	只有攻击类技能才有暴击
     */
    float chRate=(this->data->bd.lv-mf->data->bd.lv+10+2)*0.01;
    chRate=MAX(MIN(0.8,chRate), 0.01);
    bool isCrh=Utils::random01()<=chRate;
    
    //计算伤害
    int hurt;
    if(xskill->getHurtType()==1){
        hurt=MAX(1,this->data->atc-mf->data->def);
    }
    if(xskill->getHurtType()==2){
        hurt=MAX(1,this->data->atc-mf->data->mDef);
    }
    log("hurt:%d,%d,%d",hurt,data->atc,data->def);
    int hpHeal=0;
    int mpHeal=0;
    //处理技能效果(效果1，数值类的）
    switch (xskill->getEffectType()) {
        case 0: //A	攻击 攻击加成千分比/攻击加成实数
        {
            hurt=hurt+hurt*xskill->getEffectParam1()/10000.0+xskill->getEffectParam2();
            if(isCrh) hurt*=2;
            //A	普通伤害=基础伤害+基础伤害*技能给与的伤害加成比例+技能给与的伤害加成实数
            //B	暴击伤害=普通伤害*2
            //蓄力攻击
            //A	普通伤害=基础伤害*（起始伤害百分比+蓄力时间*每秒伤害加成百分比+起始加成实数+蓄力时间*每秒加成实数）
            //B	暴击伤害=普通伤害*2
            break;
        }
        case 1: //溅射 溅射攻击加成千分比/溅射攻击加成实数
        {
            /*
             A	普通伤害
             a	主目标伤害=基础伤害*主目标伤害加成百分比+主目标加成实数
             b	副目标伤害=主目标伤害*副目标衰减指数
             B	暴击伤害=普通伤害*2
             */
            hurt=hurt*xskill->getEffectParam1()/10000.0+xskill->getEffectParam2();
            if(isSub)
            {
                //hurt=hurt
            }
            if(isCrh) hurt*=2;
            break;
        }
        case 2: //治疗
        {
            XRoleData* xrd=XRoleData::record(Value(this->data->bd.xid*10+this->data->bd.rate));
            hurt=this->data->bd.lv*xrd->getHeal();
            hurt=hurt+hurt*xskill->getEffectParam1()/10000.0+xskill->getEffectParam2();
            hurt=-hurt;
        }
            break;
        case 3: //能量回复 恢复法力加成实数
        {
           /*
             A	为主角恢复的能量值=技能给与的能量恢复实数
             */
            mpHeal=xskill->getEffectParam1();
//            pAtc.set_mp(mpHeal);
            pHit.set_ahp(mpHeal);
            break;
        }
        case 4: //吸取生命 吸取生命千分比/吸取生命加成实数
        {
            /*
             A	对敌方角色造成的伤害=目标现有生命值*技能给与的生命吸取比例
             B	对自身的治疗量=对敌方角色造成的伤害+技能给与的生命加成实数
             */
            hurt=mf->data->hp*xskill->getEffectParam1()/10000.0;
            hpHeal=hurt+xskill->getEffectParam2();
//            pAtc.set_mp(mpHeal);
            pHit.set_ahp(mpHeal);
            break;
        }
        case 5: //夺取能量 （特例，可抢夺敌方在战斗过程中掉落但未拾取的能量豆）
        {
            /*
             A	夺取能量的概念
             a	使用夺取能量的技能可抢夺敌方在战斗过程中掉落但未拾取的能量豆
             B	每释放一次技能抢一颗能量豆
             */
            mpHeal=1;
//            pAtc.set_mp(mpHeal);
            break;
        }
        default:
            log("未知的效果类型,skill:%d,effectType:%d",skill->skillID,xskill->getEffectType());
            break;
    }
    pHit.set_hp(hurt);
    float buffRate=xskill->getBuffRate()/10000.0;
    Buff* buff=nullptr;
    if(Utils::random01() <= buffRate)
    {
        buff=Buff::create(xskill->getBuffID());
        buff->mf=mf;
    }
    
    //触发buff被动技能检查
    //buffer替换
    bool isRealTime=true;
    for(Buff* bf : mf->buffs){
        if(!buff) break;
        if(bf->getGroupID() == buff->getGroupID()){
            if (bf->getLv()<=buff->getLv()){
                mf->buffs.eraseObject(bf);
                isRealTime=true;
            }else{
                isRealTime=false;
            }
        }
    }
    if(buff){
        mf->buffs.pushBack(buff);
        if(isRealTime){
            //buff生效，受击者属性改变，
            buff->start();
        }
    }
    
    //修正结果
    //检查死亡
    pHit.set_isdie(mf->data->hp <= pHit.hp());
    pHit.set_perhp((float)data->hp/data->fullHp);

    //mf->attacked(pHit);
    mf->data->hp-=pHit.hp();
    //吸血
//    if(pAtc.amp() >0 )this->fallHp(-pAtc.amp());
    
    return pHit;
    
}

//掉血（加血）
//void MFighter::fallHp(int num)
//{
//    this->data->hp-=num;
//}

void MFighter::clearBuff(Buff* buff)
{
    return;
    //清除特效，重新计算属性,buff数组中清除
    for(Buff* bf : this->buffs)
    {
        if((bf->getGroupID() == buff->getGroupID()) && (bf->getLv()<buff->getLv()))
        {
            bf->start();
            this->data->addBuff(bf);
            break;
        }
    }
    this->data->delBuff(buff);
    this->buffs.eraseObject(buff);
}

Skill* MFighter::getSkillByID(int skillID)
{
    for(auto skill : this->skills){
        if(skill->skillID==skillID){
            return skill;
        }
    }
    return nullptr;
}

MFighter::~MFighter()
{
    this->data->release();
    this->skills.clear();
    this->buffs.clear();
}