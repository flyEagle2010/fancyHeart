//
//  InfoPanel.h
//  fancyHeart
//
//  Created by doteyplay on 14-8-20.
//
//

#ifndef __fancyHeart__InfoPanel__
#define __fancyHeart__InfoPanel__

#include <iostream>
#include "cocos2d.h"
#include "Manager.h"
#include "BaseUI.h"
#include "XRoleData.h"
#include "XSkill.h"
#include "XItem.h"
#include "XRoleStar.h"
#include "XRole.h"

using namespace ui;
using namespace cocos2d;
using namespace cocostudio;
struct PropertyInfo{
    std::string equipProperty;//道具属性
    std::string baseNum;//基础值
    std::string rate;//成长率
    std::string starRate;//星级成长率
};

class InfoPanel:public BaseUI
{
public:
    //status:0是升阶,2是进化，3是技能解锁，4:技能升级
    //0:品阶等级等于1、3、6、9
    //1:品阶等级等于2、4、5、7、8
	static InfoPanel* create(int status,PNpc pNpc,int skillId = 0);
	virtual bool init(std::string fileName,int status,PNpc pNpc,int skillId=0);
	virtual void onEnter();
	virtual void onExit();

private:
    void touchEvent(Ref *pSender, TouchEventType type);
    Widget*panel1;
    Widget*panel2;
    Widget*skillFrame;

private: //私有属性
    void setAscend(int status,int skillId);
    void setEvolve(int status);
    void setSkill(int status,int skillId);
    Widget* imgBg;
    PNpc pNpc;
    XSkill*xSkill;
    //参数分别为——pNpc:角色信息，index:取哪种数据（攻击，生命，物防，法防）,status:表示的是0:升阶还是1:进化,qualityOrStarAdd:增加数值
    float getQualityData(PNpc pNpc,int index,int status,int qualityOrStarAdd);
    std::vector<PropertyInfo> infoVector;
    ImageView* ascendTitle;

};
#endif /* defined(__fancyHeart__InfoPanel__) */
