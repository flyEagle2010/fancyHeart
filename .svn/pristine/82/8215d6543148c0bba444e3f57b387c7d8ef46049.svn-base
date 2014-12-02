//XItem自动生成类
#ifndef __fancyHeart__XItem__
#define __fancyHeart__XItem__
#include <iostream>
#include "external/json/document.h"
#include "cocos2d.h"
#include "extensions/cocos-ext.h"
USING_NS_CC;
class XItem{

private:
	Value v;
public:
	static XItem* record(Value v);
	rapidjson::Document doc;
	int getId();
	std::string getName();
	std::string getDes();
	/*Administrator:
0 白色
1 绿色
2 蓝色
3 紫色
4 金色*/
	int getRate();
	/*陈天华:
0消耗品
1装备
2卷轴
3召唤石*/
	int getType();
	/*Administrator:
若为空即无等级限制*/
	int getMaxLv();
	/*陈天华:
若为空则不可使用*/
	int getEventID();
	/*陈天华:
空表示不能卖
数值表示售价*/
	int getPrice();
	int getCrh();
	int getMiss();
	int getDef();
	int getMDef();
	int getAtk();
	int getHp();
	int getHeal();
	/*陈天华:
若为7654321
·则表示这是召唤石图标
·读取当前道具的id并去除首位数字
·根据该数值找到对应的角色头像
·并在该头像上显示通用召唤石图标

显示样式由美术提供*/
	int getIcon();
};
#endif // defined(__dx__Data__)
