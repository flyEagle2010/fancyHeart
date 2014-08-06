//
//  DramaAni.cpp
//  fancyHeart
//
//  Created by 秦亮亮 on 14-7-30.
//
//

#include "DramaAni.h"
#include "FightMgr.h"
DramaAni* dram=nullptr;

DramaAni* DramaAni::getInstance()
{
    if(dram==nullptr){
        dram=new DramaAni();
        dram->init();
    }
    return dram;
}

bool DramaAni::init()
{
	if(!BaseUI::init("publish/drama/drama.ExportJson"))
	{
		return false;
	}
	//如果需要对cocostudio 设计的ui进行调整
    this->talkPanel=layout->getChildByName("talkPanel");
    this->txtTalk=static_cast<Text*>(talkPanel->getChildByName("txt_talk"));
    this->talkPanel->setVisible(false);
    this->heroLayer=Node::create();
    this->effectLayer=Node::create();
    this->addChild(this->heroLayer,-3);
    this->addChild(this->effectLayer,-2);
    layout->setTouchEnabled(true);
    layout->setEnabled(true);
    layout->addTouchEventListener(CC_CALLBACK_2(DramaAni::touchEvent, this));
	return true;
}

void DramaAni::onEnter()
{
    BaseUI::onEnter();
    
}

void DramaAni::startPre(int groupID)
{
    FightMgr::getInstance()->startBattle();
    return;
    
    if(this->getParent()==nullptr)FightMgr::getInstance()->view->addChild(this);
    this->setVisible(true);
    int id=100;
    int drameId=id*1000+1;
    do{
        XDrama* dram=XDrama::record(Value(drameId));
        CC_BREAK_IF(dram->doc[Value(drameId).asString().c_str()].IsNull());
        drameId=drameId+1;
        int type=dram->getType();
        if(type==0){//剧情动画结束
            this->runAction(Sequence::create(
                                             DelayTime::create(dram->getStartTime()/1000),
                                             CallFunc::create(CC_CALLBACK_0(DramaAni::startBattle, this)),
                                             NULL));
        }
        else if (type==1) {//角色出现
            Vec2 p=PointFromString(dram->getPos());
            auto hero=Hero::create("man animation", "man animation0", p);
            this->heroLayer->addChild(hero);
            hero->setOpacity(0);
            hero->setScaleX(dram->getFaceTo()==0?-1:1);
            hero->setTag(dram->getRoleId());
            hero->runAction(Sequence::create(
                                             DelayTime::create(dram->getStartTime()/1000),
                                             FadeIn::create(.5), NULL));
        }else if (type==2){//角色消失
            auto hero=this->heroLayer->getChildByTag(dram->getRoleId());
            hero->runAction(Sequence::create(
                                             DelayTime::create(dram->getStartTime()/1000),
                                             FadeOut::create(.5),
                                             CallFunc::create(CC_CALLBACK_0(Sprite::removeFromParent, hero)), NULL));
        }else if (type==3){//角色移动
            Size winSize=Director::getInstance()->getWinSize();
            auto hero=this->heroLayer->getChildByTag(dram->getRoleId());
            Vec2 p=PointFromString(dram->getTargetPos());
            Vec2 target=Vec2((p.x)*40,winSize.height/2-(p.y-2)*50);
            Vec2 startP=hero->getPosition();
            Vec2 subP=target-startP;
            float distance=sqrt((subP.x*subP.x+subP.y*subP.y));
            float t=5*(distance/960);
            Spawn* sp=Spawn::create(MoveTo::create(t, target),CallFuncN::create(CC_CALLBACK_1(DramaAni::displayAction, this,"walk",1)),NULL);
            hero->runAction(Sequence::create(
                                             DelayTime::create(dram->getStartTime()/1000),
                                             sp,
                                             CallFuncN::create(CC_CALLBACK_1(DramaAni::displayAction, this,"idle",1)),NULL));
        }else if (type==4){//角色动作播放 包括转脸
            auto hero=this->heroLayer->getChildByTag(dram->getRoleId());
            hero->runAction(Sequence::create(
                                             DelayTime::create(dram->getStartTime()/1000),
                                             CallFuncN::create(CC_CALLBACK_1(DramaAni::displayAction,this,dram->getAction(),0)), NULL));
        }else if (type==5){//角色对白播放
            this->talkPanel->setScaleX(dram->getDirection()==0?-1:1);
            txtTalk->setString(dram->getTalk());
            this->talkPanel->runAction(Sequence::create(DelayTime::create(dram->getStartTime()/1000),
                                                  CallFuncN::create(CC_CALLBACK_1(DramaAni::displayTalk,this,true)),NULL));
            
        }else if (type==6){//特效播放
            this->runAction(Sequence::create(
                                             DelayTime::create(dram->getStartTime()/1000),
                                             CallFuncN::create(CC_CALLBACK_0(DramaAni::playEffect, this,1001)),
                                             NULL));
            
        }else if (type==7){//音效播放
            
        }
    }
    while (1);
}

void DramaAni::playEffect(int effectId)
{
    Clip* clip=Clip::create("effect/e1001.plist");
    this->effectLayer->addChild(clip);
    clip->setPosition(Vec2(400, 300));
    clip->play(false);
}

void DramaAni::displayTalk(Node * pSender,bool isDisplay)
{
    pSender->setVisible(isDisplay);
    this->isTalk=isDisplay;
    if (isDisplay) {
        Director::getInstance()->stopAnimation();
    }else if(!isDisplay){
        Director::getInstance()->startAnimation();
    }
}

void DramaAni::startBattle()
{
    FightMgr::getInstance()->startBattle();
    this->setVisible(false);
}

void DramaAni::displayAction(Node * pSender,string action,int loop)
{
    static_cast<Hero*>(pSender)->playAction(action,loop);
}

void DramaAni::startEnd(int groupID)
{
    //动画结束后调用
    FightMgr::getInstance()->handleResult();
}

void DramaAni::show(BaseUI* preUI,int effectType)
{
    BaseUI::show(preUI,effectType);
}

void DramaAni::clear(bool isDel)
{
    BaseUI::clear(isDel);
}

void DramaAni::touchEvent(Ref *pSender, TouchEventType type)
{
    if(type!=TouchEventType::ENDED){
        return;
    }
    if (!this->isTalk) {
        return;
    }
    this->displayTalk(this->talkPanel,false);
}

void DramaAni::touchButtonEvent(Ref *pSender, TouchEventType type)
{
    if(type!=TouchEventType::ENDED){
        return;
    }
    Button* btn=(Button*)pSender;
    switch (btn->getTag())
    {
        case 1001:
            break;
        default:
            break;
    }
}

void DramaAni::onExit()
{
    BaseUI::onExit();
}