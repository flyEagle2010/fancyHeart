//
//  fconfig.h
//  fancyHeart
//
//  Created by 秦亮亮 on 14-8-14.
//
//

#ifndef fancyHeart_fconfig_h
#define fancyHeart_fconfig_h

#define RANDOM_0_1() ((float)rand()/RAND_MAX)
#define HERO_DIE_TAG 2000
#define GRID_SIZE (40.0*BattleMgr::getInstance()->heroScale)
#define ACTION_RUN_TAG 100
#define ACTION_SHOOT_TAG 101
#define ACTION_ATTACKED_TAG 102
#define ANIM_SKILL_RIM_TAG 103

#define BEAN_NUM 2
#define ARROW_GAP 0.3

enum TrackIndex{
    ANI_COMMON,
    ANI_DIE,
    ANI_WIN
};


enum Efstate{
    idle,
    run,
    spell,
    shoot,
    onAir,
    die,
    win
};

enum SkillState{
    SKILL_PAUSE,
    SKILL_RESUME,
    SKILL_STOP
};
#endif
