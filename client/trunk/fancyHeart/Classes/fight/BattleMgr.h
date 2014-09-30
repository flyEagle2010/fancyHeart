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
#include "external/json/rapidjson.h"
#include "external/json/document.h"
#include "BattleScene.h"
#include "XMonster.h"
#include "XRole.h"
#include "XRoleData.h"
#include "XRoleStar.h"
#include "XGate.h"
#include "FData.h"
#include "XBattle.h"
#include "DramaAni.h"
#include "FighterMgr.h"
using namespace cocos2d;

class FighterMgr;
class BattleScene;
class FData;
struct BData;

class BattleMgr:public Ref{
private:
    void initHero(std::vector<long long> heros);
    static bool sortGrid(BData f1,BData f2);
    std::vector<int> getMonsterSkill(int xid);
    std::vector<int> groups;
    
    int gateID;
    int nodeID;

public:
    static BattleMgr* getInstance();
    void init(std::vector<long long>heros,int nodeID,int gateID=0);
    void init(rapidjson::Value& data);
    void startBattle();
    void startBattleAnimEnd();
    void startEndDram();
    void handleResult();
    void overTime();
    void stopAllFighter();
    void initNpc();
    
    FighterMgr* getHero(int pos);
    FighterMgr* getFirst(int pos);
    std::vector<int> getFoes(int pos,bool isMe=false);
    
    void skillAttack(int skill);
//    void bonceTo(FighterMgr* mf);
    void clear();
    void clearDieNpc(int pos);
    Vector<FighterMgr*> heros;
    Vector<FighterMgr*> npcs;
    
    rapidjson::Value data;
    BattleScene* view;
    bool isOver;
    bool isAuto;
    int npcNum;
    int groupID;
    int winPos;
};

#endif /* defined(__fancyHeart__MFight__) */