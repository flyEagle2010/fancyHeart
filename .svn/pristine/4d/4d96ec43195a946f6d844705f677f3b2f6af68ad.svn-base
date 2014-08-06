//
//  LRActionInterval.h
//  fancyHeart
//
//  Created by zhai on 14-6-13.
//
//

#ifndef __fancyHeart__LRActionInterval__
#define __fancyHeart__LRActionInterval__

#include <iostream>
#include "cocos2d.h"
#include "Manager.h"
USING_NS_CC;
#endif /* defined(__fancyHeart__LRActionInterval__) */
#include "2d/CCActionInterval.h"//包含系统延时类动作头文件

using namespace cocos2d;
// 定义一个结构来包含确定椭圆的参数
typedef struct _lrTuoyuanConfig {
    //中心点坐标
    Vec2 centerPosition;
    //椭圆a长，三角斜边
    float aLength;
    //椭圆c长，三角底边
    float cLength;
} lrTuoyuanConfig;
class  CircleMove : public ActionInterval
{
public:
    //用“动作持续时间”和“椭圆控制参数”初始化动作
    bool initWithDuration(float t, const lrTuoyuanConfig& c);
    virtual void update(float time);//利用update函数来不断的设定坐标
    //
    // Overrides
    //
    virtual CircleMove* clone() const override;
	virtual CircleMove* reverse(void) const  override;
    virtual void startWithTarget(Node *target) override;
public:
    //用“动作持续时间”和“椭圆控制参数”创建动作
    static CircleMove *actionWithDuration(float t, const lrTuoyuanConfig& c);
    
protected:
    lrTuoyuanConfig m_sConfig;
    Vec2 m_startPosition;
    Vec2 s_startPosition;
};