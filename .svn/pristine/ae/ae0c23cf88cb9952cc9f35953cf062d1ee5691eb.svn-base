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
	std::string getName();
	std::string getDes();
	std::string getSlogan();
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
	int getSkill0();
	int getSkill1();
	int getSkill2();
	int getSkill3();
	int getSkill4();
	int getSkill5();
	int getSkill6();
	int getSkill7();
	/*Administrator:
不可召唤为空可召唤为1*/
	int getCalled();
	int getCalledNum();
	/*Administrator:
就是角色被召唤后出来后的星级是多少*/
	int getStar();
	double getLockGrid();
	/*Administrator:
填入每个角色的专属召唤石的道具id*/
	int getPropId();
	/*Administrator:
若为1就是主角*/
	bool getIsRole();
	/*Administrator:
若有值则为每次变异需要多少召唤石否则为空*/
	int getMutationNum();
	/*Administrator:
1是剑
2是法杖*/
	int getProfessionSign();
	/*Administrator:
1表示可浮空*/
	bool getIsOnAir();
};
#endif // defined(__dx__Data__)
