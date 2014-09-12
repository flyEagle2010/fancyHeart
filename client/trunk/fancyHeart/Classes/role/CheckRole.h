//
//  CheckRole.h
//  fancyHeart
//
//  Created by doteyplay on 14-8-12.
//
//

#ifndef __fancyHeart__CheckRole__
#define __fancyHeart__CheckRole__

#include <iostream>
#include "cocos2d.h"
#include "Manager.h"
#include "BaseUI.h"
#include "RotateList.h"
#include "XRole.h"
//#include "XItem.h"
#include "Role.h"
#include "XRoleData.h"
struct PropertysInfo{
    string propertyNum;//属性值
    string growUpNum;//成长值
//    std::string baseNum;//基础值
//    std::string rate;//成长率
//    std::string starRate;//星级成长率
};

using namespace ui;
using namespace cocos2d;
using namespace cocostudio;

class CheckRole:public BaseUI
{
public:
	static CheckRole* create(int roleId);
	virtual bool init(std::string fileName,int roleId);
	virtual void onEnter();
	virtual void onExit();

private:
    void touchButtonEvent(Ref *pSender, TouchEventType type);
    int roleId;

private: //私有属性

};
#endif /* defined(__fancyHeart__CheckRole__) */
