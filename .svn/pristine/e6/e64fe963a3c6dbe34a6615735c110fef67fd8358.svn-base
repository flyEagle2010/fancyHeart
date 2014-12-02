//
//  RoleList.h
//  fancyHeart
//
//  Created by doteyplay on 14-8-12.
//
//

#ifndef __fancyHeart__RoleList__
#define __fancyHeart__RoleList__

#include <iostream>
#include "cocos2d.h"
#include "Manager.h"
#include "BaseUI.h"
#include "RotateList.h"
#include "XRole.h"
//#include "XItem.h"
#include "Role.h"
#include "XRoleData.h"
#include "CheckRole.h"

using namespace ui;
using namespace cocos2d;
using namespace cocostudio;

struct RoleItemData{
    int spriteId;
    int type;//已经召唤3 召唤石已满2 未被召唤1
    int level;
    int quality;
    int star;
};

class RoleList:public BaseUI
{
public:
	static RoleList* create();
	virtual bool init(std::string fileName);
	virtual void onEnter();
	virtual void onExit();

private:
	void initNetEvent();
	void touchEvent(Ref *pSender, TouchEventType type);

private: //私有属性
    RotateList* rotateList;
    void rotateListCallback(RotateList::EventType type,Widget*item,int index);
    void setItemData(Widget* item,RoleItemData itemData,int index);
    RoleItemData middleItemData;
    Widget* progress;
    Button* btnCall;
    Widget* middleItem;//中间位置的item
    std::vector<RoleItemData> listItems;//id列表
    Widget* currentCalledItem;
    //对还未召唤的角色进行排序
    static bool sortHandler(RoleItemData data1,RoleItemData data2);
    Button* searchBtn;
    

};
#endif /* defined(__fancyHeart__RoleList__) */
