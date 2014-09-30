//
//  Manager.h
//  fancyHeart
//
//  Created by 秦亮亮 on 14-5-4.
//
//

#ifndef __fancyHeart__Manager__
#define __fancyHeart__Manager__

#include <iostream>
#include <stdio.h>
#include "google/protobuf/message.h"
#include "net/Socket.h"
#include "common/DeviceInfo.h"
#include "MsgID.h"
#include "BaseUI.h"
#include "GConfig.h"
#include "XExp.h"
//#include "Formation.h"
USING_NS_CC;
using namespace google::protobuf;

#define EVENT_RUN "event_run"
#define EVENT_HERO_EVENTER "event_hero_enter"

#define LOADING_LAY 10
#define MSG_LAY 6
#define GUIDE_LAY 5
#define CHAT_LAY 1

class Manager{
    struct  ColorData{
        int color;
        std::string text;
    };
    
public:
    Node* scene;
    Socket* socket;
public:
    std::vector<ColorData> Qualitys={
        ColorData{1,""},
        ColorData{2,""},ColorData{2,"+1"},
        ColorData{3,""},ColorData{3,"+1"},ColorData{3,"+2"},
        ColorData{4,""},ColorData{4,"+1"},ColorData{4,"+2"},
        ColorData{5,""}};//颜色0白 1绿 2绿+1 3蓝 4蓝+1 5蓝+2 6紫 7紫+1 8紫+2 9金
    static Manager* getInstance();
    void switchScence(Scene* scene);
    void setRoleData(NetMsg* msg);//设置玩家数据
    LoginResp* getRoleData();
    
    void updateRole(NetMsg* msg);//更新role
    void updateItems(NetMsg* msg);//更新item
    void addOrRemoveNpc(NetMsg* msg);//增加或者删除npc 更新走updateRole
    
    void updateGates(NetMsg* msg);//更新关卡
    void updateNodes(NetMsg* msg);//更新节点
    
    PNpc* getNpc(int64 npcId);
    PGateItem* getGateItem(int gateId);
    PNodeItem* getNodeItem(int gateId,int nodeId);
    PItem*getPropItem(int itemId);
    void showMsg(const string msg);//浮出提示
    int gateId=0;
    void updateNpcSkills(int npcid,int skillId);
    int getCurrExp(int exp,int lvl);
    
private:
    LoginResp* roleData=nullptr;
    void updateItem(RepeatedPtrField< ::PItemChangeLog >::iterator it,RepeatedPtrField< ::PItem > *items);//更新单个物品
};

#endif /* defined(__fancyHeart__Manager__) */
