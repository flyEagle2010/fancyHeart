//
//  BezierMoveBy.h
//  fancyHeart
//
//  Created by zhai on 14-6-13.
//
//

#ifndef __fancyHeart__BezierMoveBy__
#define __fancyHeart__BezierMoveBy__

#include <iostream>
#include "2d/CCActionInterval.h"//包含系统延时类动作头文件
#include "cocos2d.h"
#define PI 3.14159265
#define EPSION 0.0001f
#define IS_EQUAL(val1, val2)  (fabs((val1) - (val2)) <= EPSION)
using namespace cocos2d;
class BezierMove:public cocos2d::BezierBy
{
public:
    /** creates the action with a duration and a bezier configuration
     * @code
     * when this function bound to js or lua,the input params are changed
     * in js: var create(var t,var table)
     * in lua: lcaol create(local t, local table)
     * @endcode
     */
    static BezierMove* create(float t, Vec2 endPosition);
    static BezierMove* create(float t, Vec2 endPosition,Vec2 control1);
    static BezierMove* create(float t, Vec2 endPosition,Vec2 control1,Vec2 control2);
    //
    // Overrides
    //
    virtual void startWithTarget(Node *target) override;
    virtual BezierMove* clone() const override;
	virtual BezierMove* reverse(void) const override;
    virtual void update(float time);
CC_CONSTRUCTOR_ACCESS:
    BezierMove() {}
    virtual ~BezierMove() {}
    float angle(const Vec2 sp,const Vec2 ep);
    bool initWithDuration(float t, const ccBezierConfig &c);
    
protected:
    ccBezierConfig _toConfig;
};

#endif /* defined(__fancyHeart__BezierMoveBy__) */
