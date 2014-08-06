//
//  BezierTools.cpp
//  test
//
//  Created by zhai on 14-6-12.
//
//

#include "BezierTools.h"
void BezierTools::BezierMoveSprite(Node* sprite,Vec2 startPoint,Vec2 endPoint,float duration)
{
    ccBezierConfig bezier; // 创建贝塞尔曲线
    bezier.controlPoint_1 = startPoint; // 起始点
    bezier.controlPoint_2 = Vec2((startPoint.x+endPoint.x)/2, (startPoint.y+endPoint.y)/2); //控制点
    bezier.endPosition = endPoint; // 结束位置
    auto bezierTo1 = BezierTo::create(duration, bezier);
    Sequence* sq=CCSequence::create(bezierTo1,CallFunc::create(CC_CALLBACK_0(Sprite::removeFromParent, sprite)), NULL);
    sprite->runAction(sq);
}
void  BezierTools::BezierMoveSpriteRotate(Node* sprite,Vec2 startPoint,Vec2 endPoint,float starAngle,float endAngle,float duration)
{
    sprite->setRotation(starAngle);
    ccBezierConfig bezier; // 创建贝塞尔曲线
    bezier.controlPoint_1 = startPoint; // 起始点
    bezier.controlPoint_2 = Vec2((startPoint.x+endPoint.x)/2, (startPoint.y+endPoint.y)/2); //控制点
    bezier.endPosition = endPoint; // 结束位置
    auto bezierTo1 = BezierTo::create(duration, bezier);
    auto rotateTo=RotateTo::create(duration, endAngle);
    Sequence* sq=CCSequence::create(CCSpawn::create(bezierTo1,rotateTo, NULL),CallFunc::create(CC_CALLBACK_0(Sprite::removeFromParent, sprite)), NULL);
    sprite->runAction(sq);
}