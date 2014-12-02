//
//  Role.h
//  fancyHeart
//
//  Created by doteyplay on 14-8-12.
//
//

#ifndef __fancyHeart__Role__
#define __fancyHeart__Role__

#include <iostream>
#include "cocos2d.h"
#include "Manager.h"
#include "BaseUI.h"
#include "TabBar.h"
#include "XRoleData.h"
#include "XItem.h"
#include "XCraft.h"
#include "EquipInfo.h"
#include "XRole.h"
#include "InfoPanel.h"
#include "Manager.h"
#include "Utils.h"
#include "XRoleStar.h"
#include "XExp.h"
//#include "CCColorUtil.h"
using namespace ui;
using namespace cocos2d;
using namespace cocostudio;
struct PropertyMess{
    string propertyNum;//属性值
    string growUpNum;//成长值
};

class Role:public BaseUI
{
public:
	static Role* create(int spriteId);
	virtual bool init(std::string fileName,int spriteId);
	virtual void onEnter();
	virtual void onExit();
    //发送请求成功后调的方法
    void sucessReturn();

private:
	void initNetEvent();
	void touchEvent(Ref *pSender, TouchEventType type);
    void touchBtnEvent(Ref *pSender, TouchEventType type);
    void touchSkillItemEvent(Ref *pSender, TouchEventType type);

private: //私有属性
    Widget* propertyPanel;
    Widget* skillPanel;
    ImageView* rolePic;
    Widget*equipPanel;
    TabBar* tabBar;
    Widget* imgBg;
    Button*changeBtn;
    Widget* progress;
    Text* desc;
    void setBtnVisible();
    PNpc itemData;
    XRoleData*xRoleData;//角色属性表中对应的数据
    void setEquips();
    std::vector<Sprite*> equips;//装备位列表
    std::vector<int> equipStatus;//装备位列表状态
    bool propIsEnough(int itemId,int num);
    std::vector<int> equipsData;//存储需要装备的id
    void openInfoPanel(int index);//点击装备位弹出弹窗方法
    int status;//0:为进化  1:为变异
    void createIconBox(int itemId,string parentName,int desIndex);
    void setData();
    void setStar();
    int messageId;//0:为进化  1:为升阶  2:穿装备  3:变异
    PGoldQualityUpgrade pGoldQualityUpgrade;
    
    std::vector<PropertyMess> propertyList;//id列表
    std::vector<int> EquipPosList;//装备位列表
    void updateSkills(int skillId);
    void setShowProgress();
    
    void showTips(string messInfo,Widget*widget);
    void hideTips();
    
};
#endif /* defined(__fancyHeart__Role__) */
