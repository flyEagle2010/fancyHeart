//
//  BezierMoveBy.cpp
//  fancyHeart
//
//  Created by zhai on 14-6-13.
//
//

#include "BezierMove.h"
BezierMove* BezierMove::create(float t, Vec2 endPosition)
{
    ccBezierConfig bezier; // 创建贝塞尔曲线
    Vec2 control1 = Vec2(endPosition.x/2, endPosition.y*2);
    bezier.controlPoint_1 = control1; // 起始点
    bezier.controlPoint_2 = endPosition; //控制点
    bezier.endPosition = endPosition; // 结束位置
    
    BezierMove *bezierTo = new BezierMove();
    bezierTo->initWithDuration(t, bezier);
    bezierTo->autorelease();
    return bezierTo;
}
BezierMove* BezierMove::create(float t, Vec2 endPosition,Vec2 control1)
{
    ccBezierConfig bezier; // 创建贝塞尔曲线
    bezier.controlPoint_1 = control1; // 起始点
    bezier.controlPoint_2 = endPosition; //控制点
    bezier.endPosition = endPosition; // 结束位置
    
    BezierMove *bezierTo = new BezierMove();
    bezierTo->initWithDuration(t, bezier);
    bezierTo->autorelease();
    return bezierTo;
}
BezierMove* BezierMove::create(float t, Vec2 endPosition,Vec2 control1,Vec2 control2)
{
    ccBezierConfig bezier; // 创建贝塞尔曲线
    bezier.controlPoint_1 = control1; // 起始点
    bezier.controlPoint_2 = control2; //控制点
    bezier.endPosition = endPosition; // 结束位置
    
    BezierMove *bezierTo = new BezierMove();
    bezierTo->initWithDuration(t, bezier);
    bezierTo->autorelease();
    return bezierTo;
}
bool BezierMove::initWithDuration(float t, const ccBezierConfig &c)
{
    if (ActionInterval::initWithDuration(t))
    {
        _toConfig = c;
        return true;
    }
    
    return false;
}

BezierMove* BezierMove::clone(void) const
{
	// no copy constructor
	auto a = new BezierMove();
	a->initWithDuration(_duration, _toConfig);
	a->autorelease();
	return a;
}

void BezierMove::startWithTarget(Node *target)
{
    BezierBy::startWithTarget(target);
    _config.controlPoint_1 = _toConfig.controlPoint_1 - _startPosition;
    _config.controlPoint_2 = _toConfig.controlPoint_2 - _startPosition;
    _config.endPosition = _toConfig.endPosition - _startPosition;
}
// Bezier cubic formula:
//    ((1 - t) + t)3 = 1
// Expands to ...
//   (1 - t)3 + 3t(1-t)2 + 3t2(1 - t) + t3 = 1
static inline float bezierat( float a, float b, float c, float d, float t )
{
    return (powf(1-t,3) * a +
            3*t*(powf(1-t,2))*b +
            3*powf(t,2)*(1-t)*c +
            powf(t,3)*d );
}
void BezierMove::update(float time)
{
    if (_target)
    {
        float xa = 0;
        float xb = _config.controlPoint_1.x;
        float xc = _config.controlPoint_2.x;
        float xd = _config.endPosition.x;
        
        float ya = 0;
        float yb = _config.controlPoint_1.y;
        float yc = _config.controlPoint_2.y;
        float yd = _config.endPosition.y;
        
        float x = bezierat(xa, xb, xc, xd, time);
        float y = bezierat(ya, yb, yc, yd, time);
        
#if CC_ENABLE_STACKABLE_ACTIONS
        Vec2 currentPos = _target->getPosition();
        Vec2 diff = currentPos - _previousPosition;
        _startPosition = _startPosition + diff;
        
        Vec2 newPos = _startPosition + Vec2(x,y);
        _target->setPosition(newPos);
        float mDegree = BezierMove::angle(currentPos, newPos);
        static_cast<Sprite *>(_target)->setRotation(mDegree);
        _previousPosition = newPos;
#else
        _target->setPosition( _startPosition + Vec2(x,y));
        
        
#endif // !CC_ENABLE_STACKABLE_ACTIONS
    }
}
float BezierMove::angle(const Vec2 sp,const Vec2 ep)
{
    float mDegree;
    Vec2 sub=Vec2(ep.x-sp.x,ep.y-sp.y);
    if (IS_EQUAL(sub.x, 0.f) && IS_EQUAL(sub.y, 0.f))
        return -1;
    if (IS_EQUAL(sub.y, 0.f) && sub.x > 0)
    {
        mDegree = 90.f;
    }
    else if (IS_EQUAL(sub.y, 0.f) && sub.x < 0)
    {
        mDegree = 180.f;
    }
    else{
        // 弧度转角度
        float radians = atanf(sub.x/sub.y);
        mDegree = CC_RADIANS_TO_DEGREES(radians)-90;
        
        if (sub.x >= 0 && sub.y >= 0 )          // 第一象限
        {
            
        }
        else if (sub.x >= 0 && sub.y <= 0)      // 第二象限
        {
            mDegree += 180.f;
        }
        else if (sub.x <= 0 && sub.y <= 0)      // 第三象限
        {
            mDegree += 180.f;
        }
        else                                   // 第四象限
        {
            mDegree += 360.f;
        }
    }
    if (mDegree < 0.f)
        mDegree = 0.f;
    if (mDegree > 360.f)
        mDegree = 0.f;
    return mDegree;
}
BezierMove* BezierMove::reverse() const
{
	CCASSERT(false, "BezierMove doesn't support the 'reverse' method");
	return nullptr;
}