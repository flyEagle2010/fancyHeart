//
//  RoleList.h
//  fancyHeart
//
//  Created by doteyplay on 14-8-12.
//
//

#ifndef __fancyHeart__RoleAllList__
#define __fancyHeart__RoleAllList__

#include <iostream>
#include "cocos2d.h"
#include "Manager.h"
#include "BaseUI.h"
#include "RotateList.h"
#include "XRole.h"
//#include "XItem.h"
#include "Role.h"
#include "XRoleData.h"

using namespace ui;
using namespace cocos2d;
using namespace cocostudio;

class RoleAllList:public BaseUI
{
public:
	static RoleAllList* create();
	virtual bool init(std::string fileName);
	virtual void onEnter();
	virtual void onExit();

private:
    void touchButtonEvent(Ref *pSender, TouchEventType type);

private: //私有属性
    RotateList* rotateList;
    void rotateListCallback(RotateList::EventType type,Widget*item,int index);
    void setItemData(Widget* item,PNpc itemData,int index);
    //对还未召唤的角色进行排序
    static bool sortHandler(PNpc data1,PNpc data2);
    
    std::vector<PNpc> pNpcs;
    void initList();//站位 0前排1中排2后排－1全部
    int currentPos=-1;

};
#endif /* defined(__fancyHeart__RoleAllList__) */
