//
//  SkillIcon.cpp
//  fancyHeart
//
//  Created by 秦亮亮 on 14-9-24.
//
//

#include "SkillIcon.h"

SkillIcon* SkillIcon::create(ImageView* rim,Vec2 position,int skillID)
{
    SkillIcon* pRet=new SkillIcon();
    if(pRet && pRet->init(rim,position,skillID)){
        pRet->autorelease();
        return pRet;
    }
    pRet->autorelease();
    return nullptr;
}

bool SkillIcon::init(ImageView* rim,Vec2 position,int skillID)
{
    this->rim=rim;
    this->skillID=skillID;
    this->skill=Skill::create(skillID);
    ImageView* img=ImageView::create("battle_000.png");
    Vec2 pos=rim->getPosition();
    img->setPosition(pos);
    this->addChild(img);
    
    this->addChild(rim);
    rim->setVisible(false);
    
    this->progressBar=ProgressTimer::create(Sprite::create("combat_below_a2.png"));
    this->addChild(progressBar);
    this->progressBar->setPosition(position-Vec2(0,2));
    this->progressBar->setType(ProgressTimer::Type::BAR);
    this->progressBar->setMidpoint(Vec2(0,0));
    this->progressBar->setBarChangeRate(Vec2(1, 0));
    this->progressBar->setPercentage(0.0);
  
    this->start();
    return true;
}

void SkillIcon::start()
{
    this->progressBar->setPercentage(0.0);
    this->rim->stopAllActions();
    this->rim->setVisible(false);
    this->skill->isReady=false;
    this->isOK=false;

    
    XSkill* xskill=XSkill::record(Value(skillID));
    ProgressTo* pt=ProgressTo::create(xskill->getTriggerParam()/10000.0, 100.0);
    this->progressBar->runAction(Sequence::create(pt,CallFunc::create(CC_CALLBACK_0(SkillIcon::skillReady, this)), NULL));
}

void SkillIcon::skillReady()
{
    this->skill->isReady=true;
    this->isOK=true;
    this->rim->setVisible(true);
    this->rim->runAction(RepeatForever::create(Sequence::create(FadeTo::create(0.8,60),FadeTo::create(0.8,255), NULL)));
}
/*
void SkillIcon::setIsOK(bool isOK)
{
    this->isOK=isOK;
    if(isOK){
        this->rim->setVisible(true);
        this->rim->runAction(RepeatForever::create(Sequence::create(FadeTo::create(0.8,60),FadeTo::create(0.8,255), NULL)));
    }else{
        this->rim->stopAllActions();
        this->rim->setVisible(false);
    }
}

void SkillIcon::resetBeans(int num)
{
    int allNum=this->beanBgs.size();
    int hasNum=this->beans.size();
    if(num>=allNum){
        this->setIsOK(true);
    }
    for(int i=0;i<allNum;i++)
    {
        //消耗后减少
        if(num<hasNum){
            this->beans.at(i)->setVisible(false);
            continue;
        }
        //增加，已经有了
        if(this->beans.size()>i){
            this->beans.at(i)->setVisible(true);
            continue;
        }else{
            ImageView* bean=ImageView::create("combat_small.png");
            bean->setPosition(beanBgs.at(i)->getPosition());
            this->addChild(bean,1);
            this->beans.pushBack(bean);
        }
    }
}
 */
