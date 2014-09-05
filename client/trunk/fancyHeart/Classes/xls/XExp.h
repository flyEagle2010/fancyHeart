//XExp自动生成类
#ifndef __fancyHeart__XExp__
#define __fancyHeart__XExp__
#include <iostream>
#include "external/json/document.h"
#include "cocos2d.h"
#include "extensions/cocos-ext.h"
USING_NS_CC;
class XExp{

private:
	Value v;
public:
	static XExp* record(Value v);
	rapidjson::Document doc;
	int getId();
	int getExp();
};
#endif // defined(__dx__Data__)
