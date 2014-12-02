//
//  VFighter.cpp
//  fancyHeart
//
//  Created by 秦亮亮 on 14-5-6.
//
//

#include "VFighter.h"
#include "BattleScene.h"


VFighter* VFighter::create(std::string fPath,std::string rPath,int pos)
{
    VFighter* pRet = new VFighter();
    if (pRet && pRet->init(fPath,rPath,pos))
    {
        pRet->autorelease();
        return pRet;
    }else{
        delete pRet;
        pRet = NULL;
        return NULL;
    }
}

bool VFighter::init(std::string fPath,std::string rPath,int pos)
{
    //this->setAnchorPoint(Vec2(0.5,0.5));
    this->pos=pos;
	//init ui
    Size winSize=Director::getInstance()->getWinSize();

    this->cEventDispatcher=new EventDispatcher();
    this->cEventDispatcher->setEnabled(true);
    ArmatureDataManager::getInstance()->addArmatureFileInfo(rPath+".png", rPath+".plist", fPath+".ExportJson");
    
    ArmatureDataManager::getInstance()->addArmatureFileInfo("NewAnimation0.png", "NewAnimation0.plist", "NewAnimation.ExportJson");
    ArmatureDataManager::getInstance()->addArmatureFileInfo("longnv0.png","longnv0.plist","longnv.ExportJson");
    
    
//    this->armature=Armature::create("man2"); //HeroAnimation
    this->armature=Armature::create("longnv");
    this->armature->getAnimation()->setMovementEventCallFunc(CC_CALLBACK_0(VFighter::animationEvent, this, std::placeholders::_1, std::placeholders::_2, std::placeholders::_3));
    this->armature->getAnimation()->setFrameEventCallFunc(CC_CALLBACK_0(VFighter::onFrameEvent,this,std::placeholders::_1,std::placeholders::_2,std::placeholders::_3,std::placeholders::_4));
    
    if(this->pos>4){
        this->armature->setScale(BattleMgr::getInstance()->heroScale, BattleMgr::getInstance()->heroScale);
    }else{
        this->armature->setScale(-BattleMgr::getInstance()->heroScale,BattleMgr::getInstance()->heroScale);
        Size size=this->armature->getContentSize();
    }
    
    //-------------------------------------------------------------------------------
    this->addChild(armature);
    Label* label=Label::createWithTTF(Value(pos).asString(), "Marker Felt.ttf", 32);
    this->addChild(label);
    label->setPosition(armature->getPosition());
    label->setColor(Color3B(255,0,0));
    //-------------------------------------------------------------------------------

    
    this->hpBg=Sprite::create("battle_HP_1.png");
    this->addChild(hpBg);
    this->hpBg->setPosition(armature->getPosition()+Vec2(0,200));

    this->hpBar=ProgressTimer::create(Sprite::create("battle_HP_2.png"));
    this->addChild(hpBar);
    this->hpBar->setPosition(armature->getPosition()+Vec2(0,200));
    this->hpBar->setType(ProgressTimer::Type::BAR);
    this->hpBar->setMidpoint(Vec2(0,0));
    this->hpBar->setBarChangeRate(Vec2(1, 0));
    this->hpBar->setPercentage(100.);
	return true;
}

void VFighter::onEnter()
{
    Node::onEnter();
    this->stand();
}

void VFighter::stand()
{
    this->state=fstate::idle;
    this->armature->getAnimation()->play(ani_idle,1,1);
}

void VFighter::run()
{
    this->state=fstate::run;

    if(this->getActionByTag(ACTION_RUN_TAG)){
        return;
    }
    Vec2 vec(pos>4?-960:960,0);
    MoveBy* move=MoveBy::create(5, vec);
    move->setTag(ACTION_RUN_TAG);
    this->runAction(move);
    std::string mName=this->armature->getAnimation()->getCurrentMovementID();
    if(ani_walk!=mName){
        this->armature->getAnimation()->play(ani_walk,1,1);
    }
}

void VFighter::move(Vec2 vec)
{
//    this->armature->getAnimation()->play(ani_walk,1,1);
//    MoveBy* move=MoveBy::create(1, vec);
//    this->runAction(move);
}

void VFighter::attack(std::string actionName)
{
    std::string mName=this->armature->getAnimation()->getCurrentMovementID();
    this->armature->getAnimation()->play(actionName,1,0);
}

void VFighter::attacked(PHit& pHit)
{
    this->fallHp(Value(pHit.hp()));
    this->hpBar->runAction(ProgressTo::create(0.3, pHit.perhp()));
    std::string mName=this->armature->getAnimation()->getCurrentMovementID();
    if(mName==ani_attack||mName==ani_skillAttack1|| mName==ani_skillAttack2 || mName==ani_win){
        return;
    }
    this->armature->getAnimation()->play(ani_attacked,1,0);
}

void VFighter::attackedOnAir(PHit &pHit)
{
    this->fallHp(Value(pHit.hp()));
    this->hpBar->runAction(ProgressTo::create(0.3, pHit.perhp()));
    
    this->armature->getAnimation()->play(ani_attackedOnAir,1,0);

    this->state=fstate::onAir;
}

void VFighter::spell(std::string actionName)
{
    std::string mName=this->armature->getAnimation()->getCurrentMovementID();
    this->armature->getAnimation()->play(actionName,1,0);
}

void VFighter::defence(PHit& pHit)
{
    this->fallHp(Value(pHit.hp()));
    this->hpBar->runAction(ProgressTo::create(0.3, pHit.perhp()));

    this->armature->getAnimation()->play(ani_defence,1,0);
}

void VFighter::die(PHit& pHit)
{
    this->state=fstate::die;
    
    this->fallHp(Value(pHit.hp()));
    this->hpBar->setPercentage(pHit.perhp());
    this->hpBar->runAction(ProgressTo::create(0.3, pHit.perhp()));
    
    std::string mName=this->armature->getAnimation()->getCurrentMovementID();
    this->armature->getAnimation()->play(ani_die,1,0);
    this->stopActionByTag(ACTION_RUN_TAG);
    this->stopActionByTag(ACTION_SHOOT_TAG);
}

void VFighter::dieClear()
{
    BattleMgr::getInstance()->clearDieNpc(pos);
}

void VFighter::fallHp(Value num)
{
    std::string fileName=num.asInt()>0?"shuzi4.png":"shuzi3.png";
    Label* label=Label::createWithCharMap(fileName, 30, 50, '1');
    label->setString(num.asString());
    label->setScale(0.01);
    this->addChild(label);
    Vec2 pos=this->armature->getPosition();
    label->setPosition(Vec2(pos.x,pos.y+60));
    
    Spawn* spawn1=Spawn::create(MoveBy::create(0.2,Vec2(0,80)),ScaleTo::create(0.3, 0.55), NULL);
    EaseOut* ease=EaseOut::create(spawn1,2);
    Spawn* spawn2=Spawn::create(MoveBy::create(0.3,Vec2(0,80)),Sequence::create(DelayTime::create(0.2),FadeOut::create(0.3),NULL), NULL);
    
    label->runAction(CCSequence::create(ease,spawn2,DelayTime::create(0.5),
                                        CallFunc::create(CC_CALLBACK_0(Label::removeFromParent, label)), NULL));
    
//    this->hitWord();
}

void VFighter::hitWord()
{
    Sprite* word=Sprite::create("hit.jpg");
    this->addChild(word);
    word->setScale(0.2);
    word->setPosition(Vec2(30,this->armature->boundingBox().size.height));
    MoveBy* move=MoveBy::create(0.3, Vec2(0, 100));
    Spawn* scaleMove=Spawn::create(EaseOut::create(move,2),EaseOut::create(ScaleTo::create(0.3, 0.8),2), NULL);
    CallFunc* cf=CallFunc::create(CC_CALLBACK_0(Sprite::removeFromParent, word));
    Sequence* sq=Sequence::create(scaleMove,DelayTime::create(0.2),FadeTo::create(0.2, 0),cf,NULL);
    word->runAction(sq);
}

void VFighter::revive()
{
    this->stopActionByTag(HERO_DIE_TAG);
}

void VFighter::win()
{
    this->armature->getAnimation()->play(ani_win,1,0);
}

// 直线弹道
float VFighter::lineThrow(std::string bulletName,Vec2 end,int count)
{
    Vec2 p = this->armature->getBone("man_ren_wu qi")->getDisplayRenderNode()->convertToWorldSpaceAR( Vec2(0, 0));
    
   
//    Vec2 end=Vec2(mf->view->getPositionX(),mf->view->getPositionY());
    
    float duration;
    for(int i=0;i<count;i++){
        Armature* bullet=Armature::create("NewAnimation");
        bullet->getAnimation()->play("ball",0,1);
        if(this->pos<5) bullet->setScale(-1);
        
        BattleMgr::getInstance()->view->addChild(bullet,2);
        bullet->setVisible(false);
        bullet->setPosition(p);
        duration=abs(end.x-p.x)/800.0;
        
        MoveTo* move=MoveTo::create(duration, Vec2(end.x,end.y+40));
        CallFunc* clear=CallFunc::create(CC_CALLBACK_0(Armature::removeFromParent,bullet));
        
        bullet->runAction(Sequence::create(DelayTime::create(ARROW_GAP*i),Show::create(),move,clear,NULL));
    }
    
    return duration;
}

//弧线弹道,被塞尔曲线计算
float VFighter::arcThrow(std::string bulletName,MFighter* mf,int count)
{
//    Vec2 p = this->armature->getBone("man_ren_qiubang1")->getDisplayRenderNode()->convertToWorldSpaceAR( Vec2(0, 0));
//    
//    Armature* bullet=Armature::create("man animation");
//    bullet->getAnimation()->play("skillAttack1_effect2",0,1);
////    if(this->pos<5) bullet->setScale(-1);
//    bullet->setScale(-1);
//    
//    BattleMgr::getInstance()->view->addChild(bullet,2);
//    bullet->setPosition(p);
//    Vec2 end=mf->view->getPosition()+Vec2(0,60);

//    float duration=abs(end.x-p.x)/1200.;
//    Vec2 mid=pos<5?Vec2(p.x+abs(end.x-p.x)/2,end.y+100):Vec2(p.x-abs(end.x-p.x)/2,end.y+150);
//    BezierMove* move=BezierMove::create(duration, end,mid);
//    
////    ccBezierConfig c={end,mid,mid+Vec2(100,100)};
////    BezierTo* move=BezierTo::create(duration, c);
//    
//    bullet->runAction(Sequence::create(move,CallFunc::create(CC_CALLBACK_0(Armature::removeFromParent,bullet)), NULL));
    
    float duration=0;
    return duration;
}

//穿刺弹道，快速穿透
float VFighter::impaleThrow(std::string bulletName)
{
    return 0.3;
}

void VFighter::animationEvent(Armature *armature, MovementEventType movementType, const std::string& movementID)
{
    if(movementType==COMPLETE){
        if(movementID==ani_attack || movementID==ani_skillAttack1 || movementID==ani_skillAttack2){
            this->state=fstate::idle;
            this->stand();
        }
        
        if(movementID==ani_die){
            Sequence* sq=Sequence::create(DelayTime::create(0.2),FadeOut::create(2.0),CallFunc::create(CC_CALLBACK_0(VFighter::dieClear,this)), NULL);
            this->armature->runAction(sq);
            this->hpBg->setVisible(false);
            this->hpBar->setVisible(false);
        }
        
        if(movementID==ani_win){
            BattleMgr::getInstance()->winPos=this->pos;
            BattleMgr::getInstance()->startEndDram();
        }
        
        if(movementID==ani_attacked || movementID==ani_attackedOnAir){
            this->stand();
        }
    }
}

void VFighter::onFrameEvent(Bone *bone, const std::string& frameEventName, int originFrameIndex, int currentFrameIndex)
{
    if(frameEventName=="kick"){

    }
    if(frameEventName=="shoot"){

    }
}

void VFighter::onExit()
{
    Node::onExit();
}