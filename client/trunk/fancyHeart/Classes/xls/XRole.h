//XRole自动生成类
#ifndef __fancyHeart__XRole__
#define __fancyHeart__XRole__
#include <iostream>
#include "external/json/document.h"
#include "cocos2d.h"
#include "extensions/cocos-ext.h"
USING_NS_CC;
class XRole{

private:
	Value v;
public:
	static XRole* record(Value v);
	rapidjson::Document doc;
	int getId();
	int getNameID();
	int getDes();
	int getSlogan();
	/*陈天华:
0物理
1法术*/
	int getAttackType();
	/*陈天华:
0前排
1中排
2后排*/
	int getPos();
	std::string getAvatar();
	std::string getCard();
	std::string getIcon();
	std::string getPotrait();
	int getCommonSkill();
	int getSkill1();
	int getSkill2();
	int getSkill3();
	int getSkill4();
	int getSkill5();
	int getSkill6();
	int getSkill7();
	int getLockGrid();
	/*Administrator:
若为1就是主角*/
	bool getIsRole();
};
#endif // defined(__dx__Data__)
