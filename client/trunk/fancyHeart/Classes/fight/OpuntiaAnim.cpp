//
//  OpuntiaAnim.cpp
//  fancyHeart
//
//  Created by 秦亮亮 on 14-9-26.
//
//

#include "OpuntiaAnim.h"

OpuntiaAnim* OpuntiaAnim::create()
{
    OpuntiaAnim* pRet=new OpuntiaAnim();
    if(pRet && pRet->init()){
        pRet->autorelease();
        return pRet;
    }
    CC_SAFE_DELETE(pRet);
    return nullptr;
}

bool OpuntiaAnim::init()
{
    ArmatureDataManager::getInstance()->addArmatureFileInfo("opuntiaAnimation0.png", "opuntiaAnimation0.plist", "opuntiaAnimation.ExportJson");
    
    this->armature=Armature::create("opuntiaAnimation"); //loop
    this->armature->getAnimation()->setMovementEventCallFunc(CC_CALLBACK_0(OpuntiaAnim::animationEvent, this, std::placeholders::_1, std::placeholders::_2, std::placeholders::_3));
    this->addChild(armature);
    
    this->armature->getAnimation()->play("await_1", -1, 1);
    
//    Vector<Vec2> points={Vec2(32,96)};
    std::vector<Vec2> points={Vec2(32, 96),Vec2(50, 119),Vec2(74,91),Vec2(92,118),Vec2(115,94)};
    for(int i=0;i<5;i++)
    {
        Armature* leaf=Armature::create("opuntiaAnimation");
        this->addChild(leaf);
        leaf->setPosition(points.at(i));
        leaf->getAnimation()->play("germinate_1");
        this->leafs.pushBack(leaf);
    }
    return true;
}

void OpuntiaAnim::drink(int index)
{
    this->fruitNum=index;
    this->armature->getAnimation()->play("drinking");
}

void OpuntiaAnim::leafGrow()
{
    this->leafs.at(fruitNum)->getAnimation()->play("germinate");
}

void OpuntiaAnim::animationEvent(Armature *armature, MovementEventType movementType, const std::string& movementID)
{
    if(movementType==COMPLETE){
        if(movementID=="drinking"){
            this->armature->getAnimation()->play("suck");
        }
        if(movementID=="suck"){
            leafGrow();
        }
    }
}