//
//  DramaAni.cpp
//  fancyHeart
//
//  Created by 秦亮亮 on 14-7-30.
//
//

#include "DramaAni.h"
#include "BattleMgr.h"
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
    this->talkPanel=(Widget*)layout->getChildByName("talkPanel");
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

void DramaAni::startPre(int groupID,int gateId,int nodeId)
{
    BattleMgr::getInstance()->startBattle();
    return;
    XMonster* xm=XMonster::record(Value(groupID));
    int id=xm->getPreID();
    PNodeItem* nodeItem=Manager::getInstance()->getNodeItem(gateId, nodeId);
    //没有战前动画直接开始战斗
    if (id==0||nodeItem->star()>0) {
        BattleMgr::getInstance()->startBattle();
        return;
    }
    
    this->playDramaAni(id);
    //this->playDramaAni(100);
}

void DramaAni::playDramaAni(int drameId,bool isPre)
{
    if(this->getParent()==nullptr)BattleMgr::getInstance()->view->addChild(this);
    this->setVisible(true);
    //drameId=100;
    drameId=drameId*1000+1;
    do{
        XDrama* dram=XDrama::record(Value(drameId));
        CC_BREAK_IF(dram->doc[Value(drameId).asString().c_str()].IsNull());
        drameId=drameId+1;
        int type=dram->getType();
        if(type==0){//剧情动画结束
            this->runAction(Sequence::create(
                                             DelayTime::create(dram->getStartTime()/1000.0f),
                                             isPre?CallFunc::create(CC_CALLBACK_0(BattleMgr::startBattle, BattleMgr::getInstance())):CallFunc::create(CC_CALLBACK_0(BattleMgr::handleResult, BattleMgr::getInstance())),
                                             CallFuncN::create(CC_CALLBACK_0(Layout::setVisible, this,false)),
                                             NULL));
        }
        else if (type==1) {//角色出现
            Vec2 p=PointFromString(dram->getPos());
            string str=dram->getPos().c_str();
            auto hero=Hero::create("man animation", "man animation0", p);
            this->heroLayer->addChild(hero);
            hero->setOpacity(0);
            hero->setScaleX(dram->getFaceTo()==0?-1:1);
            hero->setTag(dram->getRoleId());
            hero->runAction(Sequence::create(
                                             DelayTime::create(dram->getStartTime()/1000.0f),
                                             CallFuncN::create(CC_CALLBACK_0(DramaAni::displayDramaId, this,dram->getId())),
                                             FadeIn::create(.5),
                                             NULL));
        }else if (type==2){//角色消失
            auto hero=this->heroLayer->getChildByTag(dram->getRoleId());
            hero->runAction(Sequence::create(
                                             DelayTime::create(dram->getStartTime()/1000.0f),
                                             CallFuncN::create(CC_CALLBACK_0(DramaAni::displayDramaId, this,dram->getId())),
                                             FadeOut::create(.5),
                                             CallFunc::create(CC_CALLBACK_0(Sprite::removeFromParent, hero)),
                                             NULL));
        }else if (type==3){//角色移动
            Size winSize=Director::getInstance()->getWinSize();
            auto hero=this->heroLayer->getChildByTag(dram->getRoleId());
            Vec2 target=this->getPos(dram->getTargetPos());
            Vec2 startP=hero->getPosition();
            Vec2 subP=target-startP;
            float distance=sqrt((subP.x*subP.x+subP.y*subP.y));
            float t=5*(distance/960);
            Spawn* sp=Spawn::create(MoveTo::create(t, target),CallFuncN::create(CC_CALLBACK_1(DramaAni::displayAction, this,"walk",1)),NULL);
            hero->runAction(Sequence::create(
                                             DelayTime::create(dram->getStartTime()/1000.0f),
                                             CallFuncN::create(CC_CALLBACK_0(DramaAni::displayDramaId, this,dram->getId())),
                                             sp,
                                             CallFuncN::create(CC_CALLBACK_1(DramaAni::displayAction, this,"idle",1)),NULL));
        }else if (type==4){//角色动作播放 包括转脸
            auto hero=this->heroLayer->getChildByTag(dram->getRoleId());
            hero->runAction(Sequence::create(
                                             DelayTime::create(dram->getStartTime()/1000.0f),
                                             CallFuncN::create(CC_CALLBACK_0(DramaAni::displayDramaId, this,dram->getId())),
                                             CallFuncN::create(CC_CALLBACK_0(Hero::setScaleX, hero,dram->getFaceTo()==0?1:-1)),
                                             CallFuncN::create(CC_CALLBACK_1(DramaAni::displayAction,this,dram->getAction(),0)), NULL));
        }else if (type==5){//角色对白播放
            this->talkPanel->runAction(Sequence::create(
                                                        DelayTime::create(dram->getStartTime()/1000.0f),
                                                        CallFuncN::create(CC_CALLBACK_0(DramaAni::displayDramaId, this,dram->getId())),
                                                        CallFuncN::create(CC_CALLBACK_1(DramaAni::displayTalk,this,true,dram->getTalk(),dram->getDirection())),NULL));
            
        }else if (type==6){//特效播放
            this->runAction(Sequence::create(
                                             DelayTime::create(dram->getStartTime()/1000.0f),
                                             CallFuncN::create(CC_CALLBACK_0(DramaAni::displayDramaId, this,dram->getId())),
                                             CallFuncN::create(CC_CALLBACK_0(DramaAni::playEffect, this,dram->getId())),
                                             NULL));
            
        }else if (type==7){//音效播放
            
        }
    }
    while (1);
}

void DramaAni::displayDramaId(int dramaId)
{
    static_cast<Text*>(layout->getChildByName("txt_dramId"))->setString(Value(dramaId).asString());
}
void DramaAni::playEffect(int dramaId)
{
//    XDrama* dram=XDrama::record(Value(dramaId));
//    if (dram->getRoleId()==0) {//普通特效
//        Clip* clip=Clip::create("effect/e1001.plist","e1001.png");
//        this->effectLayer->addChild(clip);
//        Vec2 p=this->getPos(dram->getPos());
//        clip->setPosition(p);
//        clip->play(dram->getTimes());
//    }else if (dram->getRoleId()!=0 && dram->getIsSkill()==0){//非弹道
//        auto hero=static_cast<Hero*>(this->heroLayer->getChildByTag(dram->getRoleId()));
//        hero->playEffect(0, "head",2);
//        //hero->playEffect(dram->getPart(), dram->getEffectName());
//    }else if (dram->getRoleId()!=0 && dram->getIsSkill()!=0){//弹道
//        auto hero=static_cast<Hero*>(this->heroLayer->getChildByTag(dram->getRoleId()));
//        Clip* clip=Clip::create("effect/skillEffect.plist","skillEffect.png");
//        this->effectLayer->addChild(clip);
//        clip->setPosition(hero->getPosition());
//        clip->play(0);
//        Vec2 p=clip->getPosition();
//        Vec2 end=this->getPos("{24,2}");
//        float duration=abs(end.x-p.x)/960;
//        Vec2 mid=Vec2(p.x+abs(end.x-p.x)/2,end.y+100);
//        BezierMove* move=BezierMove::create(duration, end,mid);
//        clip->runAction(Sequence::create(move,CallFunc::create(CC_CALLBACK_0(Sprite::removeFromParent, clip)), NULL));
//    }
}

Vec2 DramaAni::getPos(string p)
{
    Size winSize=Director::getInstance()->getWinSize();
    Vec2 pos=PointFromString(p);
    Vec2 target=Vec2((pos.x)*40,winSize.height/2-(pos.y-2)*50);
    return target;
}

void DramaAni::displayTalk(Node * pSender,bool isDisplay,string talk,int direction)
{
    pSender->setVisible(isDisplay);
    this->isTalk=isDisplay;
    if (isDisplay) {
        this->talkPanel->setScaleX(direction==1?-1:1);
        txtTalk->setScaleX(direction==1?-1:1);
        Size size=Director::getInstance()->getOpenGLView()->getDesignResolutionSize();
        Size contentSize=this->talkPanel->getContentSize();
        this->talkPanel->setPosition(direction==1?Vec2(size.width-contentSize.width/2, contentSize.height/2):Vec2(contentSize.width/2, contentSize.height/2));
        Director::getInstance()->stopAnimation();
        txtTalk->setString(talk);
    }else if(!isDisplay){
        Director::getInstance()->startAnimation();
    }
}

void DramaAni::displayAction(Node * pSender,string action,int loop)
{
    if (action=="") {
        return;
    }
    static_cast<Hero*>(pSender)->playAction(action,loop);
}

void DramaAni::startEnd(int groupID,int gateId,int nodeId)
{
    BattleMgr::getInstance()->handleResult();
    return;
    XMonster* xm=XMonster::record(Value(groupID));
    int id=xm->getAfterID();
    PNodeItem* nodeItem=Manager::getInstance()->getNodeItem(gateId, nodeId);
    //没有战后动画直接开始战斗 或者是有动画但是已经打过了
    if (id==0||nodeItem->star()>0) {
        //动画结束后调用
        BattleMgr::getInstance()->handleResult();
        return;
    }
    
    this->playDramaAni(id,false);
    
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
    this->heroLayer->removeAllChildren();
}