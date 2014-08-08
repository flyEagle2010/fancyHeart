//
//  MFight.h
//  fancyHeart
//
//  Created by 秦亮亮 on 14-5-15.
//
//

#ifndef __fancyHeart__MFight__
#define __fancyHeart__MFight__

#include <iostream>
#include "cocos2d.h"
#include "MFighter.h"
#include "external/json/rapidjson.h"
#include "external/json/document.h"
#include "FightScene.h"
#include "XMonster.h"
#include "XRole.h"
#include "XRoleData.h"
#include "XRoleStar.h"
#include "XGate.h"
#include "FData.h"
#include "XBattle.h"
#include "DramaAni.h"
using namespace cocos2d;

#define RANDOM_0_1() ((float)rand()/RAND_MAX)
#define HERO_DIE_TAG 2000
#define GRID_SIZE 40.0
#define ACTION_RUN_TAG 100
#define ACTION_SHOOT_TAG 101


#define BEAN_NUM 2

class MFighter;
class FightScene;
class FData;
struct BData;

class FightMgr:public Ref{
private:
    void initHero(std::vector<long> heros);
    void initSkill();
    static bool sortGrid(BData f1,BData f2);
    std::vector<int> groups;
    
    int gateID;
    int nodeID;

public:
    static FightMgr* getInstance();
    void init(std::vector<long>heros,int nodeID,int gateID=0);
    void init(rapidjson::Value& data);
    void startBattle();
    void startEndDram();
    void handleResult();
    void stopAllFighter();
    void initNpc();

    MFighter* getRole();
    MFighter* getHero(int pos);
    MFighter* getFirst(int pos);
    std::vector<int> getFoes(int pos,bool isMe=false);
    
    void skillAttack(int skill);
    void clear();

    Vector<MFighter*> heros;
    Vector<MFighter*> npcs;
    
    rapidjson::Value data;
    FightScene* view;
    int roleMp;
    bool isOver;
    bool isAuto;
    int npcNum;
    int groupID;
    int winPos;
};

#endif /* defined(__fancyHeart__MFight__) */
