//
//  LRActionInterval.cpp
//  fancyHeart
//
//  Created by zhai on 14-6-13.
//
//

#include "CircleMove.h"
static inline float tuoyuanXat( float a, float bx, float c, float t )//返回X坐标
{
    //参数方程
    return -a*cos(2*3.1415926*t)+a;
}
static inline float tuoyuanYat( float a, float by, float c, float t )//返回Y坐标
{
    float b = sqrt(powf(a, 2) - powf(c, 2));//因为之前定义的参数是焦距c而不是短半轴b，所以需要计算出b
    //float b=1;
    //参数方程
    return b*sin(2*3.1415926*t);
}
CircleMove* CircleMove::clone(void) const
{
	// no copy constructor
	auto a = new CircleMove();
    a->initWithDuration(_duration, m_sConfig);
	a->autorelease();
	return a;
}

void CircleMove::startWithTarget(Node *target)
{
    ActionInterval::startWithTarget(target);
    //_previousPosition = _startPosition = target->getPosition();
}

CircleMove* CircleMove::reverse() const
{
    //return MoveBy::create(_duration, Vec2( -_positionDelta.x, -_positionDelta.y));
    return NULL;
}
//
//TuoyuanBy
//
CircleMove* CircleMove::actionWithDuration(float t, const lrTuoyuanConfig& c)//利用之前定义的椭圆的三个参数初始化椭圆
{
    CircleMove *pTuoyuanBy = new CircleMove();
    pTuoyuanBy->initWithDuration(t, c);
    pTuoyuanBy->autorelease();
    
    return pTuoyuanBy;
}
bool CircleMove::initWithDuration(float t, const lrTuoyuanConfig& c)
{
    if (CCActionInterval::initWithDuration(t))
    {
        m_sConfig = c;
        return true;
    }
    
    return false;
}
void CircleMove::update(float time)
{
    if (_target)
    {
        Vec2 s_startPosition =m_sConfig.centerPosition;//中心点坐标
        float a = m_sConfig.aLength;
        float bx = m_sConfig.centerPosition.x;
        float by = m_sConfig.centerPosition.y;
        float c = m_sConfig.cLength;
        float x = tuoyuanXat(a, bx, c, time);//调用之前的坐标计算函数来计算出坐标值
        float y = tuoyuanYat(a, by, c, time);
        _target->setPosition(s_startPosition+Vec2(x-a, y));//由于我们画计算出的椭圆你做值是以原点为中心的，所以需要加上我们设定的中心点坐标
        float radians = -atan2(y, x);
        float mDegree = CC_RADIANS_TO_DEGREES(radians);
        //log("mdegree=%f",mDegree);
        _target->setVisible(true);
        static_cast<Sprite*>(_target)->setRotation(mDegree);
    }
}