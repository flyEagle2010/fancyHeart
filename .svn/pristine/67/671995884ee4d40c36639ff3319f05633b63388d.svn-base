//
//  SkillIcon.cpp
//  fancyHeart
//
//  Created by 秦亮亮 on 14-9-24.
//
//

#include "SkillIcon.h"

SkillIcon* SkillIcon::create(ImageView* rim,int skillID,FighterMgr* hero)
{
    SkillIcon* pRet=new SkillIcon();
    if(pRet && pRet->init(rim,skillID,hero)){
        pRet->autorelease();
        return pRet;
    }
    pRet->autorelease();
    return nullptr;
}

bool SkillIcon::init(ImageView* rim,int skillID,FighterMgr* hero)
{
    this->skillID=skillID;
    this->skill=Skill::create(skillID);
    this->skill->fm=hero;
    this->skill->retain();
    
    this->hero=hero;
    this->setContentSize(rim->getContentSize());
    
    std::string fName="face_"+Value(hero->mf->data->bd.xid).asString()+".png";
    this->icon=ImageView::create(fName);
    this->icon->setPosition(rim->getPosition());
    this->addChild(this->icon);
    this->icon->setTouchEnabled(true);
    
    this->progressBar=ProgressTimer::create(Sprite::createWithSpriteFrameName("fight/combat_skill.png"));
    this->progressBar->setPosition(rim->getPosition());
    this->addChild(progressBar);
    
    this->progressBar->setPercentage(0.0);
    
    this->setName("icon");

    return true;
}

void SkillIcon::start()
{
    this->resume();
    XSkill* xskill=XSkill::record(Value(skillID));
    ProgressTo* pt=ProgressTo::create(xskill->getTriggerParam()/10000.0, 100.0);
    this->progressBar->setOpacity(255);
    this->progressBar->runAction(Sequence::create(pt,CallFunc::create(CC_CALLBACK_0(SkillIcon::skillReady, this)), NULL));
}

void SkillIcon::resume()
{
    this->skill->resume();
    this->skill->start();
    this->progressBar->resume();
}

void SkillIcon::pause()
{
    this->skill->pause();
    this->progressBar->pause();
}

void SkillIcon::skillReady()
{
    this->skill->stop();
    this->skill->isReady=true;
    this->progressBar->runAction(RepeatForever::create(Sequence::create(FadeTo::create(0.8,60),FadeTo::create(0.8,255), NULL)));
}

void SkillIcon::skillAttack()
{
    if(!this->hero || this->hero->state==Efstate::onAir){
        return;
    }
    this->progressBar->stopAllActions();
    this->progressBar->setPercentage(0);
    this->hero->stopCast();;
    this->hero->view->stopActionByTag(ACTION_RUN_TAG);
    //this->hero->skill=this->skill;
    
    this->hero->state=Efstate::idle;
    this->hero->skill=nullptr;
    this->hero->startAttack(this->skill);
    
    this->start();
    this->skill->isReady=false;

}

void SkillIcon::heroDie()
{
    this->progressBar->stopAllActions();
    this->progressBar->setPercentage(0);

    this->skill->isReady=false;
    this->skill->stop();
    this->hero=nullptr;
    this->icon->setColor(Color3B(80, 80, 80));
}

void SkillIcon::onExit()
{
    Widget::onExit();
    if(this->skill){
        this->skill->release();
    }
}
