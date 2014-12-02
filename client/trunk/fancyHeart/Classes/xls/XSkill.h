//XSkill自动生成类
#ifndef __fancyHeart__XSkill__
#define __fancyHeart__XSkill__
#include <iostream>
#include "external/json/document.h"
#include "cocos2d.h"
#include "extensions/cocos-ext.h"
USING_NS_CC;
class XSkill{

private:
	Value v;
public:
	static XSkill* record(Value v);
	rapidjson::Document doc;
	int getId();
	int getName();
	int getDesc();
	/*陈天华:
0主动，被动类型为1，被动参数填冷却时间
1被动
3普通攻击*/
	int getType();
	/*陈天华:
0：不触发

1：自动触发 冷却时间（被动技能参数）

2：入场时

3：死亡时

4：杀死时

5：某主动技能释放时  
触发几率/主动技能id（被动技能参数）

6：攻击时
触发几率/伤害类型（被动技能参数）

7：被攻击时
触发几率/伤害类型（被动技能参数）

8：产生伤害时
触发几率/伤害类型（被动技能参数）

9：受到伤害时
触发几率/伤害类型（被动技能参数）
*/
	int getTriggerTyp();
	int getTriggerRate();
	int getTriggerParam();
	/*陈天华:
0无
1物理
2法术
*/
	int getHurtType();
	/*陈天华:
若为空表示不引导
数值则表示施法次数*/
	int getLeadNum();
	/*陈天华:
表示施法间隔*/
	int getLeadGap();
	/*陈天华:
0 自身 
1 我方 影响人数
2 近战 影响人数
3 爆发 前排/后排
4 射击 最大距离/子弹数量
5 弹射 弹射次数
6 穿透 最大距离*/
	int getRangeType();
	int getRangeParam1();
	int getRangeParam2();
	int getRangeParam3();
	int getRangeParam4();
	int getRangeParam5();
	/*陈天华:
0 普通
1 随机
2 血最少
3 血最多
4 离得近
5 离得远
*/
	int getSelectType();
	/*陈天华:
空表示不蓄力
数值表示蓄力时间（毫秒）
此处内容于引导互斥*/
	int getBuildTime();
	int getBuildBaseRate();
	int getBuildBaseNum();
	int getBuildStepRate();
	int getBuildStepNum();
	/*陈天华:
0 攻击 攻击加成千分比/攻击加成实数
# 溅射攻击 溅射攻击加成千分比/溅射攻击加成实数
# 治疗 治疗加成千分比/治疗加成实数
# 吸取生命 吸取生命千分比/吸取生命加成实数

有#标识的表示还没做的技能效果*/
	int getEffectType();
	int getEffectParam1();
	int getEffectParam2();
	int getEffectParam3();
	int getEffectParam4();
	int getBuffID();
	int getBuffRate();
	/*这里填写的是伤害的逻辑延迟时间，比如若技能有引导，那么从引导动作/特效开始播到第一次伤害生效中间需要间隔多少时间*/
	int getSpellTime();
	/*Administrator:
填入技能表现配置表中的id*/
	int getEffectID();
	/*Administrator:
1表示优先
空表示不优先*/
	bool getIsAirFirst();
	/*Administrator:
1为可将目标打飞
空 为不可将目标打飞*/
	bool getIsHitOnAir();
};
#endif // defined(__dx__Data__)
