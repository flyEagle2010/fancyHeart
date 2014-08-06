//XCraft自动生成类
#ifndef __fancyHeart__XCraft__
#define __fancyHeart__XCraft__
#include <iostream>
#include "external/json/document.h"
#include "cocos2d.h"
#include "extensions/cocos-ext.h"
USING_NS_CC;
class XCraft{

private:
	Value v;
public:
	static XCraft* record(Value v);
	rapidjson::Document doc;
	/*陈天华:
【合成类型】【合成数量】【合成产物道具ID】【材料id】【材料数量】

0 是消耗N个不同的道具合成1个道具
1 是消耗N个同名道具合成1个道具*/
	int getType();
	/*陈天华:
就是要花多少游戏币*/
	int getCost();
	/*陈天华:
道具id*/
	int getOutItemID();
	int getItem1();
	int getNum1();
	int getItem2();
	int getNum2();
	int getItem3();
	int getNum3();
	int getItem4();
	int getNum4();
	int getItem5();
	int getNum5();
};
#endif // defined(__dx__Data__)
