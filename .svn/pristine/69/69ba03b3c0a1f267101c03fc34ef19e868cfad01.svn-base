//XDrama自动生成类
#ifndef __fancyHeart__XDrama__
#define __fancyHeart__XDrama__
#include <iostream>
#include "external/json/document.h"
#include "cocos2d.h"
#include "extensions/cocos-ext.h"
USING_NS_CC;
class XDrama{

private:
	Value v;
public:
	static XDrama* record(Value v);
	rapidjson::Document doc;
	/*陈天华:
id长度6位
前三位是事件集id
后三位是事件id*/
	int getId();
	/*陈天华:
1 【角色出现
】播放时间、角色id、角色朝向、位置、背景音乐id、背景音乐的播放/停止


2 【角色消失
】播放时间、角色id、背景音乐id、背景音乐的播放/停止


3 【角色移动
】播放时间、角色id、角色朝向、角色移动终点、移动时长、背景音乐id、背景音乐的播放/停止


4 【角色动作播放】播放时间、角色id、角色朝向、动作选择、背景音乐id、背景音乐的播放/停止


5 【角色对白播放】播放时间、半身像位置、半身像id、对白文本id、背景音乐的播放/停止


6 【特效播放】播放时间、角色id、位置、背景音乐id、背景音乐的播放/停止、特效资源id、特效播放次数、是否为弹道、弹道终止坐标、角色绑点位置


7 【音效播放】音效id


0 结束标识*/
	int getType();
	/*陈天华:
就是啥时候播事件，单位毫秒*/
	int getStartTime();
	/*陈天华:
填入角色的数据id*/
	int getRoleId();
	/*陈天华:
0表示左边
1表示右边*/
	int getDirection();
	/*陈天华:
0表示向右看
1表示向左看*/
	int getFaceTo();
	/*陈天华:
填入横向格子的坐标
从左到右1-24格
范例：{横坐标格数,纵坐标位置}
纵坐标值为：上=1 中=2 下=3*/
	std::string getPos();
	int getIsStop();
	int getMusicId();
	/*陈天华:
填入横向格子的坐标
从左到右1-24格
范例：{横坐标格数,纵坐标位置}
纵坐标值为：上=1 中=2 下=3*/
	std::string getTargetPos();
	/*陈天华:
以毫秒为单位*/
	int getMoveTime();
	std::string getAction();
	std::string getTalk();
	std::string getEffectName();
	/*Administrator:
即特效能播几次
若填了特效ID但该处为空则表示该特效为循环播放*/
	int getTimes();
	/*陈天华:
0为否
1为是*/
	int getIsSkill();
	/*Administrator:
填入横向格子的坐标
从左到右1-24格
范例：{横坐标格数,纵坐标位置}
纵坐标值为：上=1 中=2 下=3*/
	std::string getSkillPos();
	/*陈天华:
0头顶
1身上
2脚底*/
	int getPart();
	std::string getSoundName();
	int getIsFadeIn();
	std::string getTargetName();
};
#endif // defined(__dx__Data__)
