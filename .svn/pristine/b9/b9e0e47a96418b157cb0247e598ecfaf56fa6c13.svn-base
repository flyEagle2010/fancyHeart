//
//  Bullet.h
//  fancyHeart
//
//  Created by 秦亮亮 on 14/11/7.
//
//

#ifndef __fancyHeart__Bullet__
#define __fancyHeart__Bullet__

#include <stdio.h>

#include "cocos2d.h"
#include "XSkillEffect.h"
#include "VFighter.h"
#include "Clip.h"
#include "BattleMgr.h"

using namespace cocos2d;
class VFighter;

class Bullet : public Node
{
public:
    static Bullet* create(int xs,VFighter* vf,float delay);
    bool init(int xs,VFighter* vf,float delay);
    void update(float dt);
    void onEnter();
    void start();
private:
    int xs;
    float delay;
    VFighter* vf;
    float duration;
};


#endif /* defined(__fancyHeart__Bullet__) */
