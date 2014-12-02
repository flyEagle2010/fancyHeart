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
    this->pos=pos;
	//init ui
    float scale=fPath=="huLi.json"?0.25:0.5;
    this->skeletonNode = SkeletonAnimation::createWithFile(fPath, rPath, BattleMgr::getInstance()->heroScale*scale);

    if(this->pos>4){
        this->skeletonNode->setScale(1,1);
    }else{
        this->skeletonNode->setScale(-1,1);
    }
    this->addChild(skeletonNode);
    this->skeletonNode->setEndListener(CC_CALLBACK_1(VFighter::onAnimationEnd, this));
    //this->skeletonNode->setTimeScale(0.8);
    
    //-------------------------------------------------------------------------------
    Label* label=Label::createWithTTF(Value(pos).asString(), "Marker Felt.ttf", 32);
    this->addChild(label);
    label->setPosition(skeletonNode->getPosition());
    label->setColor(Color3B(255,0,0));
    //-------------------------------------------------------------------------------
    
    spBone* body=this->skeletonNode->findBone("body");
    this->hpBg=Sprite::create("battle_HP_1.png");
    this->addChild(hpBg);
    this->hpBg->setPosition(Vec2(body->worldX,body->worldY)+Vec2(0,200));

    this->hpBar=ProgressTimer::create(Sprite::create("battle_HP_2.png"));
    this->addChild(hpBar);
    this->hpBar->setPosition(Vec2(body->worldX,body->worldY)+Vec2(0,200));
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
    this->setAnimation(TrackIndex::ANI_COMMON, ani_idle, true);
}

void VFighter::run()
{
    if(this->getActionByTag(ACTION_RUN_TAG)){
        this->stopActionByTag(ACTION_RUN_TAG);
    }
    Vec2 vec(pos>4?-1100:1100,0);
    MoveBy* move=MoveBy::create(5, vec);
    move->setTag(ACTION_RUN_TAG);
    this->runAction(move);
    this->setAnimation(TrackIndex::ANI_COMMON, ani_walk, true);
}

void VFighter::move(Vec2 vec)
{
    MoveBy* move=MoveBy::create(0.2, vec);
    this->runAction(move);
}

void VFighter::attack(std::string actionName)
{
    this->setAnimation(TrackIndex::ANI_COMMON, actionName, false);
    this->skeletonNode->addAnimation(TrackIndex::ANI_COMMON, ani_idle, true);
}

void VFighter::attacked(PHit& pHit)
{
    this->fallHp(pHit);
    
    std::string name="";
    if(this->skeletonNode->getCurrent()){
        name=this->skeletonNode->getCurrent()->animation->name;
    }
    
    if(name==ani_onAir || name==ani_onAirAttacked){
        this->skeletonNode->clearTrack();
        this->setAnimation(TrackIndex::ANI_COMMON, ani_onAirAttacked, false);
        this->skeletonNode->addAnimation(TrackIndex::ANI_COMMON, ani_onAir, true);
        XSkillEffect* xse=XSkillEffect::record(Value(pHit.xse()));

        this->onAirEffect(xse->getOnAirHit());
    }
    else if(name!=ani_defence && name!= ani_attack && name!=ani_skillAttack1 && name!=ani_skillAttack2 && name!=ani_win){
        this->setAnimation(TrackIndex::ANI_COMMON ,ani_attacked, false);
        this->skeletonNode->addAnimation(TrackIndex::ANI_COMMON, ani_idle, true);
        XSkillEffect* xse=XSkillEffect::record(Value(pHit.xse()));
        this->attackedEffect(xse->getHit());
    }
}

void VFighter::attackedEffect(std::string effectName)
{
    Clip* clip=Clip::create(effectName+".plist", effectName,12);
    this->addChild(clip,2);

    spBone* bone=this->skeletonNode->findBone("body");
    clip->setPosition(Vec2(bone->worldX,bone->worldY+bone->data->length*0.8));
    
    clip->play();
}

void VFighter::onAirEffect(std::string effectName)
{
    this->attackNum++;
    
    Clip* clip=Clip::create(effectName+".plist", effectName,12);
    this->addChild(clip,2);
    //clip->setScale(0.4);
    spBone* bone=this->skeletonNode->findBone("body");
    Vec2 vec;
    float dis=20;
    switch (attackNum%5) {
        case 0: //中心
            vec=Vec2(bone->worldX,bone->worldY+bone->data->length*0.8);
            break;
        case 1: //左下
            vec=Vec2(bone->worldX-dis,bone->worldY+bone->data->length*0.8-dis);
            break;
        case 2: //右上
            vec=Vec2(bone->worldX+dis,bone->worldY+bone->data->length*0.8+dis);
            break;
        case 3: //左上
            vec=Vec2(bone->worldX-dis,bone->worldY+bone->data->length*0.8+dis);
            break;
        case 4: //右下
            vec=Vec2(bone->worldX+dis,bone->worldY+bone->data->length*0.8-dis);
            break;
    }
  
    clip->setPosition(vec);
    clip->play();
}

void VFighter::attackedOnAir(PHit &pHit)
{
    this->fallHp(pHit);
    
    this->skeletonNode->clearTrack();
    this->setAnimation(TrackIndex::ANI_COMMON, ani_onAir, false);
  
    CallFunc* cf=CallFunc::create(CC_CALLBACK_0(VFighter::onAirFall, this));
    
    MoveBy* move=MoveBy::create(0.3, Vec2(0,350));
    Sequence* sq=Sequence::create(move,DelayTime::create(0.5),MoveBy::create(1.3, Vec2(0,-350)),cf, NULL);
    this->runAction(sq);

}

void VFighter::onAirFall()
{
    this->setAnimation(TrackIndex::ANI_COMMON, ani_onAirFall, false);
    this->skeletonNode->addAnimation(TrackIndex::ANI_COMMON, ani_idle, true);
    
    if(this->isDie){
        PHit phit;
        this->die(phit);
        if(this->pos<5){
            for(SkillIcon* skillIcon : BattleMgr::getInstance()->skillIcons){
                if(!skillIcon->hero){
                    continue;
                }
                if (skillIcon->hero->pos == this->pos) {
                    skillIcon->heroDie();
                    break;
                }
            }
        }
    }else{
        BattleMgr::getInstance()->getHero(pos)->state=Efstate::idle;
        BattleMgr::getInstance()->getHero(pos)->resetSkill(SkillState::SKILL_RESUME);
    }
}

void VFighter::spell(std::string actionName)
{
    this->setAnimation(TrackIndex::ANI_COMMON, ani_spell, false);
}

void VFighter::defence(PHit& pHit)
{
    this->fallHp(pHit);
    
    std::string name="";
    if(this->skeletonNode->getCurrent()){
        name=this->skeletonNode->getCurrent()->animation->name;
    }
    
    if(name!= ani_attack && name!=ani_skillAttack1 && name!=ani_skillAttack2 && name!=ani_win){
        this->setAnimation(TrackIndex::ANI_COMMON, ani_defence, 0);
        this->skeletonNode->addAnimation(0, ani_idle, true);
    }
    Clip* clip=Clip::create("dun.plist", "dun",10);
    this->addChild(clip,2);
    if(this->pos>4){
        clip->setScale(0.5,0.5);
        clip->setPosition(Vec2(-30,100));
    }else{
        clip->setScale(-0.5,0.5);
        clip->setPosition(Vec2(30,100));
    }
    clip->play();

}

void VFighter::die(PHit& pHit)
{
    this->skeletonNode->clearTracks();
    
    this->setAnimation(TrackIndex::ANI_DIE, ani_die, false);

    this->fallHp(pHit);
    
    this->stopActionByTag(ACTION_RUN_TAG);
    this->stopActionByTag(ACTION_SHOOT_TAG);
    
    BattleMgr::getInstance()->view->heroNode->reorderChild(this, 0);
}

void VFighter::dieClear()
{
    BattleMgr::getInstance()->clearDieNpc(pos);
}

void VFighter::fallHp(PHit& phit)
{
    spBone* body=this->skeletonNode->findBone("body");
    if(phit.isimmune()){
        return;
    }
    
    if(phit.ismiss()){
        Sprite* miss=Sprite::create("miss.png");
        BattleMgr::getInstance()->view->addChild(miss);
        miss->setScale(1.2);
        miss->setPosition(this->convertToWorldSpace(Vec2(body->worldX,body->worldY+120)));
        Sequence* seq=Sequence::create(ScaleTo::create(0.2, 0.6),MoveBy::create(0.3, Vec2(0,40)),FadeOut::create(0.3),CallFunc::create(CC_CALLBACK_0(Label::removeFromParent, miss)), NULL);
        miss->runAction(seq);
        return;
    }
    std::string hpFileName=phit.hp()>0?"num_hp.png":"num_heal.png";
    Label* label;
    if(phit.iscrh()){
        label=Label::createWithCharMap("num_hit.png", 84, 95, '0');
    }else{
        label=Label::createWithCharMap(hpFileName, 56, 63, '0');
    }
    
    std::string str=(phit.hp()>0?";":":")+Value(abs(phit.hp())).asString();
    label->setString(str);
    label->setScale(1);
    BattleMgr::getInstance()->view->addChild(label,2);
//    this->addChild(label);
    //Vec2 pos=this->skeletonNode->getPosition();
    label->setPosition(this->convertToWorldSpace(Vec2(body->worldX,body->worldY+100)));
    ScaleTo* scale1=ScaleTo::create(0.15, 0.5);
    
    MoveBy* move=MoveBy::create(0.5, Vec2(0, 80));
    Sequence* sq=Sequence::create(DelayTime::create(0.2),FadeOut::create(0.3), NULL);
    
    Spawn* sp3=Spawn::create(move,sq, NULL);
    CallFunc* cf4=CallFunc::create(CC_CALLBACK_0(Label::removeFromParent, label));
    
    label->runAction(Sequence::create(scale1,DelayTime::create(0.3),sp3,cf4, NULL));

    //this->hpBar->runAction(ProgressTo::create(0.3, phit.perhp()));
    this->hpBar->setPercentage(phit.perhp());

    //this->hitWord();
}

void VFighter::hitWord()
{
    Sprite* word=Sprite::create("hit.jpg");
    this->addChild(word);
    word->setScale(0.2);
    word->setPosition(Vec2(30,this->skeletonNode->boundingBox().size.height));
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
    this->setAnimation(TrackIndex::ANI_WIN, ani_win, false);
}

// 直线弹道
float VFighter::lineThrow(int xse,VFighter* target,int count)
{
    spBone* bone=this->skeletonNode->findBone("launch");
    spBone* body=this->skeletonNode->findBone("body");
    
    Vec2 p= this->skeletonNode->convertToWorldSpace(Vec2(bone->worldX,bone->worldY+bone->data->length*0.8));
    Vec2 end=target->convertToWorldSpace(Vec2(body->worldX,body->worldY+body->data->length*0.8));
    
    XSkillEffect* xe=XSkillEffect::record(Value(xse));
    float duration;
    for(int i=0;i<count;i++){
        
        if(xe->getBulletEffectType()==1){
            ParticleSystem* bullet=ParticleSystemQuad::create(xe->getBullet());
            BattleMgr::getInstance()->view->addChild(bullet,2);
            bullet->setVisible(false);
            bullet->setPosition(p);
            
            duration=abs(end.x-p.x)/xe->getBulletSpeed();
            
            MoveTo* move=MoveTo::create(duration, end);
            CallFunc* clear=CallFunc::create(CC_CALLBACK_0(Clip::removeFromParent,bullet));
            
            bullet->runAction(Sequence::create(DelayTime::create(ARROW_GAP*i),Show::create(),move,clear,NULL));
        }
        if(xe->getBulletEffectType()==2){
            Clip* bullet=Clip::create(xe->getBullet()+".plist", xe->getBullet(),12);
            BattleMgr::getInstance()->view->addChild(bullet,2);
            bullet->setPosition(p);
            bullet->setVisible(false);
            float dy=end.y-p.y;
            float dx=end.x-p.x;
            
            float scale=1;
            if(this->pos>4){
                bullet->setScale(scale,scale);
                bullet->setRotation(180-atan2(dy, dx)*180./3.14);
            }
            else{
                bullet->setScale(-scale,scale);
                bullet->setRotation(-atan2(dy, dx)*180./3.14);
            }
            
            duration=abs(end.x-p.x)/xe->getBulletSpeed();
            
            bullet->play(true);

            MoveTo* move=MoveTo::create(duration, end);
            CallFunc* clear=CallFunc::create(CC_CALLBACK_0(Clip::removeFromParent,bullet));
            
            bullet->runAction(Sequence::create(DelayTime::create(ARROW_GAP*i),Show::create(),move,clear,NULL));
        }
        
    }
    
    return duration;
}

float VFighter::trackThrow(int xse,VFighter* vf,int count)
{
    spBone* bone=this->skeletonNode->findBone("launch");
    Vec2 p= this->convertToWorldSpace(Vec2(bone->worldX,bone->worldY));
    for(int i=0;i<count;i++){
        Bullet* bullet=Bullet::create(xse, vf,i*ARROW_GAP);
        BattleMgr::getInstance()->view->addChild(bullet,2);
        bullet->setPosition(p);
    }
    XSkillEffect* xe=XSkillEffect::record(Value(xse));
    
    return p.distance(vf->getPosition())/xe->getBulletSpeed();
}

//弧线弹道,被塞尔曲线计算
float VFighter::arcThrow(std::string bulletName,MFighter* mf,int count)
{
    /*
    Vec2 p = this->armature->getBone("man_ren_qiubang1")->getDisplayRenderNode()->convertToWorldSpaceAR( Vec2(0, 0));
    
    Armature* bullet=Armature::create("man animation");
    bullet->getAnimation()->play("skillAttack1_effect2",0,1);
//    if(this->pos<5) bullet->setScale(-1);
    bullet->setScale(-1);
    
    BattleMgr::getInstance()->view->addChild(bullet,2);
    bullet->setPosition(p);
    Vec2 end=mf->view->getPosition()+Vec2(0,60);

    float duration=abs(end.x-p.x)/1200.;
    Vec2 mid=pos<5?Vec2(p.x+abs(end.x-p.x)/2,end.y+100):Vec2(p.x-abs(end.x-p.x)/2,end.y+150);
    BezierMove* move=BezierMove::create(duration, end,mid);
    
//    ccBezierConfig c={end,mid,mid+Vec2(100,100)};
//    BezierTo* move=BezierTo::create(duration, c);
    
    bullet->runAction(Sequence::create(move,CallFunc::create(CC_CALLBACK_0(Armature::removeFromParent,bullet)), NULL));
    */
    
    float duration=0.6;
    return duration;
}

//穿刺弹道，快速穿透
float VFighter::impaleThrow(std::string bulletName)
{
    return 0.3;
}

void VFighter::setAnimation(int trackIndex, std::string animName ,bool loop)
{
    this->skeletonNode->setToSetupPose();
    this->skeletonNode->setAnimation(trackIndex, animName, loop);
 
}

void VFighter::onAnimationEnd(int trackIndex)
{
    switch (trackIndex) {
        case TrackIndex::ANI_DIE:
        {
            BattleMgr::getInstance()->getHero(this->pos)->stop();
            Sequence* sq=Sequence::create(DelayTime::create(0.2),FadeOut::create(2.0),CallFunc::create(CC_CALLBACK_0(VFighter::dieClear, this)), NULL);
            this->runAction(sq);
            this->hpBg->setVisible(false);
            this->hpBar->setVisible(false);
            //this->stopAllActions();
            break;
        }
        case TrackIndex::ANI_WIN:
        {
            BattleMgr::getInstance()->winPos=this->pos;
            this->runAction(Sequence::create(DelayTime::create(1.2),CallFunc::create(CC_CALLBACK_0(BattleMgr::startEndDram, BattleMgr::getInstance())), NULL));
            break;
        }
        default:
            break;
    }
}

void VFighter::onExit()
{
    Node::onExit();
}