//
//  FData.h
//  fancyHeart
//
//  Created by 秦亮亮 on 14-6-23.
//
//

#ifndef __fancyHeart__FData__
#define __fancyHeart__FData__

#include <iostream>
#include "cocos2d.h"
#include "XRole.h"
#include "XRoleData.h"
#include "XRoleStar.h"
#include "Buff.h"
#include "XMonster.h"
using namespace cocos2d;
class Buff;

struct BData{
    int xid;
    int lv;
    int rate;
    int star;
    int pos;
    std::vector<int> skills;
};

class FData:public Ref
{
public:
    BData bd;
    int hp;
    int row;
    int atc;
    int def;
    int mDef;
    int miss;
    int crh;
    int heal;
    int fullHp;
public:
    static FData* create(BData bd);
    bool init(BData bd);
    void addBuff(Buff* buff);
    void delBuff(Buff* buff);
    bool isRole();
    bool isHitOnAir();
    int getRow();
    int getAttackForce();

private:
};
#endif /* defined(__fancyHeart__FData__) */
