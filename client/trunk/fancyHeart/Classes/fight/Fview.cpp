//
//  Fview.cpp
//  fancyHeart
//
//  Created by 秦亮亮 on 14-5-6.
//
//

#include "Fview.h"
#include "FightScene.h"


Fview* Fview::create(std::string fPath,std::string rPath,int pos)
{
    Fview* pRet = new Fview();
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

bool Fview::init(std::string fPath,std::string rPath,int pos)
{
    //this->setAnchorPoint(Vec2(0.5,0.5));
    this->pos=pos;
	//init ui
    Size winSize=Director::getInstance()->getWinSize();

    this->cEventDispatcher=new EventDispatcher();
    this->cEventDispatcher->setEnabled(true);
    ArmatureDataManager::getInstance()->addArmatureFileInfo(rPath+".png", rPath+".plist", fPath+".ExportJson");
    
    this->armature=Armature::create("man animation"); //HeroAnimation
    this->armature->getAnimation()->setMovementEventCallFunc(CC_CALLBACK_0(Fview::animationEvent, this, std::placeholders::_1, std::placeholders::_2, std::placeholders::_3));
    this->armature->getAnimation()->setFrameEventCallFunc(CC_CALLBACK_0(Fview::onFrameEvent,this,std::placeholders::_1,std::placeholders::_2,std::placeholders::_3,std::placeholders::_4));
    
    if(this->pos>4){
        this->armature->setScale(1, 1);
        Size size=this->armature->getContentSize();
        this->armature->setPosition(Vec2(size.width*0.6/2,0));
    }else{
        this->armature->setScale(-1,1);
    }
    this->addChild(armature);
    
    Label* label=Label::createWithTTF(Value(pos).asString(), "Marker Felt.ttf", 32);
    this->addChild(label);
    label->setPosition(armature->getPosition());
    label->setColor(Color3B(255,100,100));
    
    Sprite* hpBg=Sprite::create("battle_HP_1.png");
    this->addChild(hpBg);
    this->hpBar=LoadingBar::create("battle_HP_2.png");
    this->addChild(hpBar);
    hpBg->setPosition(armature->getPosition()+Vec2(0,200));
    this->hpBar->setPosition(armature->getPosition()+Vec2(0,200));
    this->hpBar->setPercent(100);
    this->hpBar->setDirection(LoadingBar::Direction::LEFT);
	return true;
}

void Fview::onEnter()
{
    Node::onEnter();
    this->stand();
}

void Fview::stand()
{
    this->armature->getAnimation()->play(ani_idle,1,1);
}

void Fview::run()
{
    Vec2 vec(pos>4?-960:960,0);
    MoveBy* move=MoveBy::create(5, vec);
    move->setTag(ACTION_RUN_TAG);
    this->runAction(move);
    std::string mName=this->armature->getAnimation()->getCurrentMovementID();
    if(ani_walk!=mName){
        this->armature->getAnimation()->play(ani_walk,1,1);
    }
}

void Fview::attack(int type)
{
    std::string mName=this->armature->getAnimation()->getCurrentMovementID();
    if(type==1){
        this->armature->getAnimation()->play(ani_attack,1,0);
    }
    if(type==2){
        this->armature->getAnimation()->play(ani_skillAttack1,1,0);
    }
}

void Fview::attacked(PHit& pHit)
{
    this->fallHp(Value(pHit.hp()));
    this->hpBar->setPercent(delegate->data->hp/delegate->data->fullHp);
    
    std::string mName=this->armature->getAnimation()->getCurrentMovementID();
    if(mName==ani_attack||mName==ani_skillAttack1){
        return;
    }
    if(mName!=ani_attacked){
        this->armature->getAnimation()->play(ani_attacked,1,0);
    }
}

void Fview::spell()
{
    std::string mName=this->armature->getAnimation()->getCurrentMovementID();
    this->armature->getAnimation()->stop();
    this->armature->getAnimation()->play("",1,0);
}

void Fview::fallHp(Value num)
{
    std::string fileName=num.asInt()>0?"shuzi4.png":"shuzi3.png";
    Label* label=Label::createWithCharMap(fileName, 30, 50, '1');
    label->setString(num.asString());
    label->setScale(0.05);
    this->addChild(label);
    Vec2 pos=this->armature->getPosition();
    label->setPosition(Vec2(pos.x,pos.y+80));
    
    Spawn* spawn1=Spawn::create(MoveBy::create(0.2,Vec2(0,80)),ScaleTo::create(0.3, 0.7), NULL);
    EaseOut* ease=EaseOut::create(spawn1,2);
    Spawn* spawn2=Spawn::create(MoveBy::create(0.3,Vec2(0,80)),Sequence::create(DelayTime::create(0.2),FadeOut::create(0.3),NULL), NULL);
    
    label->runAction(CCSequence::create(ease,spawn2,DelayTime::create(0.5),
                                        CallFunc::create(CC_CALLBACK_0(Label::removeFromParent, label)), NULL));
    
//    this->hitWord();
}

void Fview::fallMp()
{
    SpriteFrameCache::getInstance()->addSpriteFramesWithFile("effect/bean.plist");
    Sprite* bean=Sprite::create();
    FightMgr::getInstance()->view->effectNode->addChild(bean);
    Vec2 pos=this->getPosition();
    bean->setPosition(pos+Vec2(0,100));
        
    Vector<SpriteFrame*> frames;
    for(int i=0;i<10;i++){
        std::string index=Value(i+1).asString();
        std::string imgName="bean_"+index+".png";
        if(i==0) bean->setSpriteFrame(imgName);
        SpriteFrame* pFrame=SpriteFrameCache::getInstance()->getSpriteFrameByName(imgName);
        frames.pushBack(pFrame);
    }
        
    Animation* bonce=Animation::createWithSpriteFrames(frames,0.05);
        
    Vector<SpriteFrame*> frames2;
    for(int i=10;i<16;i++){
        std::string index=Value(i+1).asString();
        std::string imgName="bean_"+index+".png";
        SpriteFrame* pFrame=SpriteFrameCache::getInstance()->getSpriteFrameByName(imgName);
        frames2.pushBack(pFrame);
    }
    Vector<SpriteFrame*> frames3=frames2;
    frames3.reverse();
    frames.pushBack(frames3);
    Animation* slash=Animation::createWithSpriteFrames(frames2,0.05);
        
    ccBezierConfig configer={Vec2(pos.x-random()%250,pos.y-random()%150),Vec2(pos.x-60,pos.y+300),Vec2(pos.x-110,pos.y+200)};
    BezierTo* bezier=BezierTo::create(0.6, configer);
    Vec2 endPos=FightMgr::getInstance()->view->beans.at(4)->getPosition();
    auto cf=CallFuncN::create( CC_CALLBACK_1(FightScene::pickBean, FightMgr::getInstance()->view));
    auto sp=Spawn::create(Repeat::create(Animate::create(slash), 5),Sequence::create(DelayTime::create(2),MoveTo::create(0.4, endPos),NULL), NULL);

    Sequence* seq=Sequence::create(bezier,Animate::create(bonce),sp,cf,nullptr);
    
    bean->runAction(seq);
}

void Fview::hitWord()
{
    Sprite* word=Sprite::create("hit.jpg");
    this->addChild(word);
    word->setScale(0.2);
    word->setPosition(Vec2(30,this->armature->boundingBox().size.height));
    MoveBy* move=MoveBy::create(0.3, Vec2(0, 100));
    Sequence* sq=Sequence::create(Spawn::create(EaseOut::create(move,2),EaseOut::create(ScaleTo::create(0.3, 0.8),2), NULL),DelayTime::create(0.2),FadeTo::create(0.2, 0),
                                  CallFunc::create(CC_CALLBACK_0(Sprite::removeFromParent, word)),NULL);
    word->runAction(sq);
}

void Fview::die()
{
    std::string mName=this->armature->getAnimation()->getCurrentMovementID();
    this->armature->getAnimation()->play(ani_die,1,0);
    this->stopActionByTag(ACTION_RUN_TAG);
    this->stopActionByTag(ACTION_SHOOT_TAG);
}

void Fview::dieClear()
{
    this->delegate->dieClear();
}

void Fview::revive()
{
    this->stopActionByTag(HERO_DIE_TAG);
}

void Fview::win()
{
    this->armature->getAnimation()->play(ani_win,1,0);
}

// 直线弹道
float Fview::lineThrow(MFighter* mf)
{
    Vec2 p = this->armature->getBone("man_ren_qiubang1")->getDisplayRenderNode()->convertToWorldSpaceAR( Vec2(0, 0));
    Armature* bullet=Armature::create("man animation");
    bullet->getAnimation()->play("skillAttack1_effect2",0,1);
    if(this->pos<5) bullet->setScale(-1);
    Vec2 end=Vec2(mf->view->getPositionX(),mf->view->getPositionY());
    
    FightMgr::getInstance()->view->addChild(bullet,2);
    bullet->setPosition(p);
    float duration=abs(end.x-p.x)/1200.0;
    bullet->runAction(Sequence::create(MoveTo::create(duration, Vec2(end.x,end.y+20)),CallFunc::create(CC_CALLBACK_0(Armature::removeFromParent,bullet)),NULL));
    return duration;
}

//弧线弹道,被塞尔曲线计算
float Fview::arcThrow(MFighter* mf)
{
    Vec2 p = this->armature->getBone("man_ren_qiubang1")->getDisplayRenderNode()->convertToWorldSpaceAR( Vec2(0, 0));
    
    Armature* bullet=Armature::create("man animation");
    bullet->getAnimation()->play("skillAttack1_effect2",0,1);
//    if(this->pos<5) bullet->setScale(-1);
    bullet->setScale(-1);
    
    FightMgr::getInstance()->view->addChild(bullet,2);
    bullet->setPosition(p);
    Vec2 end=mf->view->getPosition()+Vec2(0,60);

    float duration=abs(end.x-p.x)/1200.;
    Vec2 mid=pos<5?Vec2(p.x+abs(end.x-p.x)/2,end.y+100):Vec2(p.x-abs(end.x-p.x)/2,end.y+150);
    BezierMove* move=BezierMove::create(duration, end,mid);
    
//    ccBezierConfig c={end,mid,mid+Vec2(100,100)};
//    BezierTo* move=BezierTo::create(duration, c);
    
    bullet->runAction(Sequence::create(move,CallFunc::create(CC_CALLBACK_0(Armature::removeFromParent,bullet)), NULL));
    
    return duration;
}

//穿刺弹道，快速穿透
float Fview::impaleThrow(MFighter* target)
{
    return 0.0;
}

void Fview::animationEvent(Armature *armature, MovementEventType movementType, const std::string& movementID)
{
    if(movementType==COMPLETE){
        if(movementID==ani_attack || movementID==ani_skillAttack1 || movementID==ani_skillAttack2){
            this->delegate->attackOver();
        }
        
        if(movementID==ani_die){
            Sequence* sq=Sequence::create(DelayTime::create(0.2),FadeOut::create(2.0),CallFunc::create(CC_CALLBACK_0(Fview::dieClear,this)), NULL);
            this->armature->runAction(sq);
        }
        
        if(movementID==ani_win){
            FightMgr::getInstance()->winPos=this->pos;
            FightMgr::getInstance()->startEndDram();
        }
        
        if(movementID!=ani_die && movementID!=ani_win){
            this->stand();
        }
    }
}

void Fview::onFrameEvent(Bone *bone, const std::string& frameEventName, int originFrameIndex, int currentFrameIndex)
{
    if(frameEventName=="kick"){
        for(int mPos : this->delegate->targets){
            MFighter* mf=FightMgr::getInstance()->getHero(mPos);
            
            this->delegate->hit(mf);
            
        }
    }
    if(frameEventName=="shoot"){
        for(int mPos : this->delegate->targets){
            MFighter* mf=FightMgr::getInstance()->getHero(mPos);
            float duration=this->lineThrow(mf);
//            float duration=this->arcThrow(mf);
            Sequence* seq=Sequence::create(DelayTime::create(duration),CallFunc::create(CC_CALLBACK_0(MFighter::hit, delegate,mf)),nullptr);
            seq->setTag(ACTION_SHOOT_TAG);
            this->runAction(seq);
        }
    }
}

void Fview::onExit()
{
    Node::onExit();
}