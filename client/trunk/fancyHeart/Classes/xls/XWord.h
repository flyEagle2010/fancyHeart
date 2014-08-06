//XWord自动生成类
#ifndef __fancyHeart__XWord__
#define __fancyHeart__XWord__
#include <iostream>
#include "external/json/document.h"
#include "cocos2d.h"
#include "extensions/cocos-ext.h"
USING_NS_CC;
class XWord{

private:
	Value v;
public:
	static XWord* record(Value v);
	rapidjson::Document doc;
	int getId();
};
#endif // defined(__dx__Data__)
