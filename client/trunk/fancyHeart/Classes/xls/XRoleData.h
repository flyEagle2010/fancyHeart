//XRoleData自动生成类
#ifndef __fancyHeart__XRoleData__
#define __fancyHeart__XRoleData__
#include <iostream>
#include "external/json/document.h"
#include "cocos2d.h"
#include "extensions/cocos-ext.h"
USING_NS_CC;
class XRoleData{

private:
	Value v;
public:
	static XRoleData* record(Value v);
	rapidjson::Document doc;
	/*陈天华:
前3位是角色id
第四位是品质
0 白
1 绿
2 绿+1
3 蓝
4 蓝+1
5 蓝+2
6 紫
7 紫+1
8 紫+2
9 金*/
	std::string getId();
	int getHp();
	int getHpRate();
	int getAtk();
	int getAtkRate();
	int getDf();
	int getDfRate();
	int getMDf();
	int getMDfRate();
	int getMiss();
	int getCrh();
	int getHeal();
	/*Administrator:
填入装备的道具id*/
	int getEquipPos1();
	/*Administrator:
填入装备的道具id*/
	int getEquipPos2();
	/*Administrator:
填入装备的道具id*/
	int getEquipPos3();
	/*Administrator:
填入装备的道具id*/
	int getEquipPos4();
	/*Administrator:
填入装备的道具id*/
	int getEquipPos5();
	/*Administrator:
填入装备的道具id*/
	int getEquipPos6();
};
#endif // defined(__dx__Data__)
