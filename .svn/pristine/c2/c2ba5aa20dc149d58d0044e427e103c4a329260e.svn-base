//
//  Bullet.cpp
//  fancyHeart
//
//  Created by 秦亮亮 on 14/11/7.
//
//

#include "Bullet.h"
Bullet* Bullet::create(int xse,VFighter* vf,float delay)
{
    Bullet* pRet=new Bullet();
    if(pRet && pRet->init(xse,vf,delay)){
        pRet->autorelease();
        return pRet;
    }
    CC_SAFE_DELETE(pRet);
    return nullptr;
}

bool Bullet::init(int xs,VFighter* vf,float delay)
{
    this->xs=xs;
    this->vf=vf;
    this->delay=delay;
    
    XSkillEffect* xe=XSkillEffect::record(Value(xs));
    //粒子
    if(xe->getBulletEffectType()==1){
        ParticleSystem* bullet=ParticleSystemQuad::create(xe->getBullet());
        this->addChild(bullet);
        bullet->setPosition(Vec2(30,0));
    }
    //序列帧
    if(xe->getBulletEffectType()==2){
        Clip* bullet=Clip::create(xe->getBullet()+".plist",xe->getBullet(), 20);
        this->addChild(bullet);
        float scale=1;
        if(this->vf->pos<5){
            bullet->setScale(-scale, scale);
        }else{
            bullet->setScale(scale, scale);
        }
        bullet->play(true);
    }
    return true;
}

void Bullet::onEnter()
{
    Node::onEnter();
    
    XSkillEffect* xe=XSkillEffect::record(Value(this->xs));
    Vec2 start=this->getPosition();
    spBone* body=this->vf->skeletonNode->findBone("body");
    Vec2 end=this->vf->convertToWorldSpace(Vec2(body->worldX,body->worldY+body->data->length*0.8));

    this->duration=abs(end.distance(start))/xe->getBulletSpeed();
    
    this->setVisible(false);
    this->scheduleOnce(SEL_SCHEDULE(&Bullet::start), this->delay);
}

void Bullet::start()
{
    this->setVisible(true);
    this->scheduleUpdate();
}

void Bullet::update(float dt)
{
    if(!this->vf || BattleMgr::getInstance()->getHero(vf->pos)->state==die){
        this->unscheduleUpdate();
        this->removeFromParent();
        return;
    }
    this->duration-=dt;
    Vec2 start=this->getPosition();
    spBone* body=this->vf->skeletonNode->findBone("body");
    Vec2 end=this->vf->convertToWorldSpace(Vec2(body->worldX,body->worldY+body->data->length*0.8));

    this->setPosition(start+(end-start)/duration*dt);
    this->setRotation(-atan2(end.y-start.y, end.x-start.x)* 180/3.14159);
    if(duration<=0){
        this->unscheduleUpdate();
        this->removeFromParent();
    }
}