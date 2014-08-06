//XGate自动生成类
#ifndef __fancyHeart__XGate__
#define __fancyHeart__XGate__
#include <iostream>
#include "external/json/document.h"
#include "cocos2d.h"
#include "extensions/cocos-ext.h"
USING_NS_CC;
class XGate{

private:
	Value v;
public:
	static XGate* record(Value v);
	rapidjson::Document doc;
	int getId();
	std::string getName();
	std::string getDesc();
	std::string getDesc2();
	/*Administrator:
1剧情
2精英
3活动
0秘境*/
	int getType();
	int getAvatorId();
	int getMapId();
	/*Administrator:
若关卡b是从关卡a进入的则关卡b的父id为关卡a*/
	int getParentId();
};
#endif // defined(__dx__Data__)
