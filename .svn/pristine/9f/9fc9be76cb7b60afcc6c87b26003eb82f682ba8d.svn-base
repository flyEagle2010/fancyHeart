//
//  BezierRotateBy.h
//  fancyHeart
//
//  Created by zhai on 14-6-13.
//
//

#ifndef __fancyHeart__BezierRotateBy__
#define __fancyHeart__BezierRotateBy__

#include <iostream>
#include "CCActionInterval.h"//包含系统延时类动作头文件
#include "cocos2d.h"
#define PI 3.14159265
#define EPSION 0.0001f
#define IS_EQUAL(val1, val2)  (fabs((val1) - (val2)) <= EPSION)
#endif /* defined(__fancyHeart__BezierRotateBy__) */
using namespace cocos2d;
class BezierRotate:public cocos2d::BezierBy
{
public:
    static BezierRotate* create(float t, const ccBezierConfig& c);
    virtual void startWithTarget(Node *target) override;
    virtual BezierRotate* clone() const override;
	virtual BezierRotate* reverse(void) const override;
    virtual void update(float time);
CC_CONSTRUCTOR_ACCESS:
    BezierRotate() {}
    virtual ~BezierRotate() {}
    float angle(const Vec2 sp,const Vec2 ep);
    bool initWithDuration(float t, const ccBezierConfig &c);
    
protected:
    ccBezierConfig _toConfig;
};