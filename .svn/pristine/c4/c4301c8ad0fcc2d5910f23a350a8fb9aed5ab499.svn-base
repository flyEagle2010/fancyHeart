//
//  Hero.cpp
//  fancyHeart
//
//  Created by zhai on 14-8-4.
//
//

#include "Hero.h"

Hero* Hero::create(std::string fPath,std::string rPath,Vec2 pos)
{
    Hero* pRet = new Hero();
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

bool Hero::init(std::string fPath,std::string rPath,Vec2 pos)
{
    Size winSize=Director::getInstance()->getWinSize();
    ArmatureDataManager::getInstance()->addArmatureFileInfo(rPath+".png", rPath+".plist", fPath+".ExportJson");
    this->armature=Armature::create("man animation"); //HeroAnimation
    this->setPosition(Vec2((pos.x)*40,winSize.height/2-(pos.y-2)*50));
    this->addChild(armature);
	return true;
}

void Hero::onEnter()
{
    Node::onEnter();
    this->stand();
}

void Hero::setOpacity(GLubyte opacity)
{
    Node::setOpacity(opacity);
    this->armature->setOpacity(opacity);
}

void Hero::stand()
{
    this->armature->getAnimation()->play("idle",1,1);
}

void Hero::playAction(string action,int loop)
{
    this->armature->getAnimation()->play(action,1,loop);
}

void Hero::run()
{
}

void Hero::playEffect(int part,string effectName,int times)
{
    int heroZOrder=this->armature->getLocalZOrder();
    int clipZOrder=part==2?heroZOrder-1:heroZOrder+1;
    Clip* clip=Clip::create("effect/"+effectName+".plist","head");
    this->addChild(clip,clipZOrder);
    Vec2 p;
    if (part==0) {
        p=Vec2(0,this->armature->getContentSize().height+clip->getContentSize().height/2);
    }else if (part==1){
        p=Vec2(0,this->armature->getContentSize().height/2);
    }else if (part==2){
        p=Vec2(0,0);
    }
    clip->setPosition(p);
    clip->play(times);
    
}

void Hero::onExit()
{
    Node::onExit();
}