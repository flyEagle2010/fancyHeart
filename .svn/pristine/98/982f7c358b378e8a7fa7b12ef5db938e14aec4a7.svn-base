//
//  FighterMgr.h
//  fancyHeart
//
//  Created by 秦亮亮 on 14-8-14.
//
//

#ifndef __fancyHeart__FighterMgr__
#define __fancyHeart__FighterMgr__

#include <iostream>
#include "cocos2d.h"
#include "VFighter.h"
#include "Skill.h"
#include "MFighter.h"
#include "FData.h"
#include "fconfig.h"

using namespace cocos2d;

class Skill;
class FData;
class VFighter;
class MFighter;

class FighterMgr:public Ref
{
public:
    static FighterMgr* create(FData* data);
    ~FighterMgr();
    bool init(FData* data);
    void initSkill(std::vector<int>);
    void start();
    void stop();
    void resume();
    void pause();
    
    void stopCast();
    float getGrid();
    std::vector<int> selectTarget();
    void startAttack(Skill* skill);
    void resetSkill(SkillState state);

private:
    void checkRun(float dt);
    Skill* selectSkill();
    void passiveAttack();
public:
    void preHit();
    void shoot();
    void impale(); //穿透
    void bounce();
    void hit();
    void attacked(FighterMgr* attacker,PHit& phit);
public:
    int pos;
    VFighter* view;
    MFighter* mf;
    Skill* skill;
    std::vector<int> targets;
    Vector<Skill*> skills;
    
    Efstate state;

};


#endif /* defined(__fancyHeart__FighterMgr__) */
