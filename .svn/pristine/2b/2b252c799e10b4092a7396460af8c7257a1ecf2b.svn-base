//
//  Buff.cpp
//  fancyHeart
//
//  Created by 秦亮亮 on 14-6-23.
//
//

#include "Buff.h"
Buff* Buff::create(int buffID)
{
    Buff* pRet=new Buff();
    if(pRet && pRet->init(buffID))
    {
        pRet->autorelease();
        return pRet;
    }else{
        CC_SAFE_DELETE(pRet);
        pRet=nullptr;
        return nullptr;
    }
}

bool Buff::init(int buffID)
{
    if (buffID <= 0 ) {
        return false;
    }
    this->setBuffID(buffID);
    
    XBuff* xb=XBuff::record(Value(buffID));
    float duration=xb->getDuration()/10000.0;
    Director::getInstance()->getScheduler()->schedule(SEL_SCHEDULE(&Buff::finish),this,duration,false);
    return true;
}

void Buff::start()
{
    //属性修正
    XBuff* xb=XBuff::record(Value(buffID));
    this->mf->data->addBuff(this);

    if(xb->getDps()>0)
    {
        Director::getInstance()->getScheduler()->schedule(SEL_SCHEDULE(&Buff::dps),this,1,false);
    }
}

void Buff::finish()
{
    this->mf->clearBuff(this);
}

void Buff::dps()
{
    XBuff* xb=XBuff::record(Value(buffID));
    //this->mf->view->fallHp(Value(xb->getDps()));
}

void Buff::atkBreak()
{
    XBuff* xb=XBuff::record(Value(buffID));
    if(xb->getIsBreak())
    {
        this->finish();
    }
}

bool Buff::canMove()
{
    XBuff* xb=XBuff::record(Value(buffID));
    return xb->getIsMove();
}

bool Buff::canSkill()
{
    XBuff* xb=XBuff::record(Value(buffID));
    return xb->getIsUseSkill();
}

bool Buff::canHeal()
{
    XBuff* xb=XBuff::record(Value(buffID));
    return xb->getIsHeal();
}

bool Buff::isMatkMiss()
{
    XBuff* xb=XBuff::record(Value(buffID));
    return xb->getIsMagicPass();
}

bool Buff::isAtkMiss()
{
    XBuff* xb=XBuff::record(Value(buffID));
    return xb->getIsPhisicPass();
}

bool Buff::isHeal()
{
    XBuff* xb=XBuff::record(Value(buffID));
    return xb->getIsHeal();
}

int Buff::getGroupID()
{
    XBuff* xb=XBuff::record(Value(buffID));
    return xb->getGroupID();
}

int Buff::getLv()
{
    XBuff* xb=XBuff::record(Value(buffID));
    return xb->getLevel();
}

Buff::~Buff()
{
    Director::getInstance()->getScheduler()->unscheduleAllForTarget(this);
}