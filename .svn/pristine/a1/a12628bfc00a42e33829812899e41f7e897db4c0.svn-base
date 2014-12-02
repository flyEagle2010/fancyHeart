//
//  FighterCtrl.h
//  fancyHeart
//
//  Created by 秦亮亮 on 14-5-5.
//
//

#ifndef __fancyHeart__MFighter__
#define __fancyHeart__MFighter__

#include <iostream>
#include "cocos2d.h"
#include "external/json/rapidjson.h"
#include "Manager.h"
#include "BattleMgr.h"
#include "Utils.h"
#include "Skill.h"
#include "Buff.h"
#include "FData.h"
#include "fight.pb.h"
#include "Confirm.h"
#include "fconfig.h"
using namespace cocos2d;
class Skill;
class Buff;
class FData;

class MFighter:public Ref{
    CC_SYNTHESIZE(bool, isRole, IsRole);
public:
    static MFighter* create(FData* fd);
    ~MFighter();
    bool init(FData* fd);

    void dieClear();
    
    int getGrid();

    void clearBuff(Buff* buff);
    Skill* getSkillByID(int skillID);
    PHit hit(FighterMgr* mf,Skill* skill);
    
    double getLockGrid();
    void resetData(PHit& phit);
public:
    FData* data;
    Vector<Buff*> buffs;

};
#endif /* defined(__fancyHeart__MFighter__) */
