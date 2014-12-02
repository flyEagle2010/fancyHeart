//XSkillEffect自动生成类
#ifndef __fancyHeart__XSkillEffect__
#define __fancyHeart__XSkillEffect__
#include <iostream>
#include "external/json/document.h"
#include "cocos2d.h"
#include "extensions/cocos-ext.h"
USING_NS_CC;
class XSkillEffect{

private:
	Value v;
public:
	static XSkillEffect* record(Value v);
	rapidjson::Document doc;
	int getId();
	std::string getSpell();
	std::string getCast();
	int getFrameNum();
	std::string getBullet();
	/*Administrator:
需在此声明弹道飞行时间
0抛物线
1直射
2穿刺*/
	int getBulletType();
	int getBulletSpeed();
	/*qll:
1.粒子
2.序列帧*/
	int getBulletEffectType();
	int getParam3();
	int getParam4();
	int getParam5();
	std::string getHit();
	std::string getOnAirHit();
};
#endif // defined(__dx__Data__)
