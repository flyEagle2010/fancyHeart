//XBattle自动生成类
#ifndef __fancyHeart__XBattle__
#define __fancyHeart__XBattle__
#include <iostream>
#include "external/json/document.h"
#include "cocos2d.h"
#include "extensions/cocos-ext.h"
USING_NS_CC;
class XBattle{

private:
	Value v;
public:
	static XBattle* record(Value v);
	rapidjson::Document doc;
	int getId();
	int getNameID();
	int getDesID();
	int getMGroup1();
	int getMGroup2();
	int getMGroup3();
	int getMGroup4();
	int getMGroup5();
	int getCoin();
	int getExp();
	int getDropID();
	int getMapID();
};
#endif // defined(__dx__Data__)
