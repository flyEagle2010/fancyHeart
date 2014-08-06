//
//  BezierTools.h
//  test
//
//  Created by zhai on 14-6-12.
//
//

#ifndef __test__BezierTools__
#define __test__BezierTools__

#include <iostream>
#include "cocos2d.h"
USING_NS_CC;
#endif /* defined(__test__BezierTools__) */
class BezierTools: public cocos2d::Layer
{
public:
    void static BezierMoveSprite(Node* sprite,Vec2 startPoint,Vec2 endPoint,float duration);
    void static BezierMoveSpriteRotate(Node* sprite,Vec2 startPoint,Vec2 endPoint,float starAngle,float endAngle,float duration);
};
