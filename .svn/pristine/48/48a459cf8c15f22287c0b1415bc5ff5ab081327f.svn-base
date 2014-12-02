//
//  Skill.h
//  fancyHeart
//
//  Created by 秦亮亮 on 14-6-4.
//
//

#ifndef __fancyHeart__Skill__
#define __fancyHeart__Skill__

#include <iostream>
#include "cocos2d.h"
#include "XSkill.h"
#include "XBuff.h"
#include "XRole.h"
#include "XRoleData.h"
#include "XRoleStar.h"
#include "Buff.h"
#include "fight.pb.h"
#include "XSkillEffect.h"
using namespace cocos2d;

class FighterMgr;

class Skill:public Ref
{
    CC_SYNTHESIZE(int, type, Type);
public:
    ~Skill();
    static Skill* create(int skillID);
    bool init(int skillID);
    std::vector<int> selectStrategy(std::vector<int> arr,int num=5);

    int getType();
    bool isReady;
    int skillID;
  
    void start();
    void pause();
    void stop();
    void resume();
    
    
    FighterMgr* fm;
private:
    void coldDown(float dt);
    std::vector<int> targets;
    int bounceIdx; //弹射索引
};

#endif /* defined(__fancyHeart__Skill__) */
