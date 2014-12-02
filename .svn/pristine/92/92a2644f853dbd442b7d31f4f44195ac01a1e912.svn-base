//XNode自动生成类
#ifndef __fancyHeart__XNode__
#define __fancyHeart__XNode__
#include <iostream>
#include "external/json/document.h"
#include "cocos2d.h"
#include "extensions/cocos-ext.h"
USING_NS_CC;
class XNode{

private:
	Value v;
public:
	static XNode* record(Value v);
	rapidjson::Document doc;
	std::string getId();
	int getGateID();
	/*陈天华:
0进关卡
1进战役
*/
	int getType();
	int getBattleID();
	/*Administrator:
1表示通关后不显示
若为空则表示通关后显示*/
	int getDisplay();
};
#endif // defined(__dx__Data__)
