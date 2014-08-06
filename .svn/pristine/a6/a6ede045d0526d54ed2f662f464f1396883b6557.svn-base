//
//  Buff.h
//  fancyHeart
//
//  Created by 秦亮亮 on 14-6-23.
//
//

#ifndef __fancyHeart__Buff__
#define __fancyHeart__Buff__

#include <iostream>
#include "cocos2d.h"
#include "XBuff.h"
#include "MFighter.h"
using namespace cocos2d;
class MFighter;
class Buff:public Ref
{
    CC_SYNTHESIZE(int, buffID, BuffID);
public:
    ~Buff();
    static Buff* create(int buffID);
    bool init(int buffID);
    void start();
   
    void atkBreak();

//    是否离线存储	是否离线计时	持伤	持续治疗	是否被打断	是否能移动	是否能用主动技能	是否能被治疗	是否物理免疫	是否法术免疫
    bool canMove();
    bool canSkill();
    bool canHeal();
    bool isMatkMiss();
    bool isAtkMiss();
    bool isHeal();
    int getGroupID();
    int getLv();
    MFighter* mf;
private:
    void finish();
    void dps();
};
#endif /* defined(__fancyHeart__Buff__) */
