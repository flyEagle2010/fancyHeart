//
//  FData.cpp
//  fancyHeart
//
//  Created by 秦亮亮 on 14-6-23.
//
//

#include "FData.h"

FData* FData::create(BData bd)
{
    FData* pRet=new FData();
    if(pRet && pRet->init(bd))
    {
        pRet->autorelease();
        return pRet;
    }else{
        CC_SAFE_DELETE(pRet);
        return nullptr;
    }
}

/*
 当前品质基础＋等级＊（品质成长＋星级成长）
 C	怪物的实际数据=由角色表Id获得的基础属性+等级X品质成长率+等级X星级成长率
 */
bool FData::init(BData bd)
{
    if(bd.xid<=0 || bd.lv<=0 || bd.rate<0 || bd.star<=0){
        log("角色基本信息有误:%d,%d,%d,%d",bd.xid,bd.lv,bd.rate,bd.star);
        return false;
    }
    this->bd=bd;
    log("bd.xid:%d",bd.xid);
    XRole* xr=XRole::record(Value(bd.xid));
    XRoleData* xrd=XRoleData::record(Value(bd.xid*10+bd.rate));
    XRoleStar* xrs=XRoleStar::record(Value(bd.xid*10+bd.star));
    
    this->row=xr->getPos();
    this->crh=xrd->getCrh();
    this->heal=xrd->getHeal();
    this->miss=xrd->getMiss();
    
    this->hp=xrd->getHp()+bd.lv*(xrs->getHpRate()+xrd->getHpRate());
    this->def=xrd->getDf()+bd.lv*(xrs->getDfRate()+xrd->getDfRate());
    this->mDef=xrd->getMDf()+bd.lv*(xrs->getMDfRate()+xrd->getMDfRate());
    this->atc=xrd->getAtk()+bd.lv*(xrs->getAttackRate()+xrd->getAtkRate());
    this->fullHp=hp;
    return true;
}

void FData::addBuff(Buff* buff)
{
    XBuff* xb=XBuff::record(Value(buff->getBuffID()));
    this->atc  += xb->getAtc();
    this->crh  += xb->getHit();
    this->def  += xb->getDef();
    this->mDef += xb->getMdef();
    this->miss += xb->getMiss();
}

void FData::delBuff(Buff* buff)
{
    XBuff* xb=XBuff::record(Value(buff->getBuffID()));
    this->atc  -= xb->getAtc();
    this->crh  -= xb->getHit();
    this->def  -= xb->getDef();
    this->mDef -= xb->getMdef();
    this->miss -= xb->getMiss();
}

bool FData::isRole()
{
    XRole* xr=XRole::record(Value(bd.xid));
    return xr->getIsRole();
}

bool FData::isHitOnAir()
{
    XRole* xr=XRole::record(Value(bd.xid));
    return xr->getIsOnAir();
}

int FData::getRow()
{
    XRole* xr=XRole::record(Value(bd.xid));
    return xr->getPos();
}

int FData::getAttackForce()
{
    return this->hp/10000.0f+(this->atc+this->mDef+this->mDef+this->miss+this->crh)/1000.0f;
}

