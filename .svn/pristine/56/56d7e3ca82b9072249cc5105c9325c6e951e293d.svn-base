//
//  BattleScene.cpp
//  fancyHeart
//
//  Created by 秦亮亮 on 14-5-5.
//
//

#include "BattleScene.h"
#include "HomeScene.h"

Scene* BattleScene::createScene(){
	auto scene = Scene::create();
    auto layer = BattleScene::create();
    scene->addChild(layer,0,0);
	return scene;
}

BattleScene* BattleScene::create()
{
    BattleScene* pRet=new BattleScene();
    if (pRet && pRet->init("publish/fight/fight.ExportJson")) {
        pRet->autorelease();
        return pRet;
    }
    CC_SAFE_DELETE(pRet);
    return nullptr;
}

bool BattleScene::init(const char* fileName){

    if(!BaseUI::init(fileName)){
        return false;
    }
    this->layout->setOpacity(255);

    this->setSize(Director::getInstance()->getWinSize());
    this->heroNode=Widget::create();
    this->layout->addChild(heroNode,-1);

    Size dSize(1136,640);
    Size size=Director::getInstance()->getWinSize();
    
    BattleMgr::getInstance()->heroScale=MIN(1,size.width/960.0);
    
    
    int battleID=BattleMgr::getInstance()->battleID;
    
    XBattle* xb=XBattle::record(Value(battleID));
    this->bg=Sprite::create(xb->getMapID()+".png");
    
    this->layout->addChild(bg,-2);
    this->bg->setPosition(Vec2(size.width/2,size.height/2));
    
//    float scale=fmin(size.width/dSize.width,size.height/dSize.height);
  
    this->top=(Widget*)this->layout->getChildByName("top");
//    top->setScale(scale);
//    top->setPosition(Vec2(0,size.height-(dSize.height-top->getPositionY())*(scale)));
    top->setPositionX((size.width-top->getContentSize().width)/2);

    this->skillNode=(Widget*)this->layout->getChildByName("skill");
    this->skillNode->setPositionX((size.width-skillNode->getContentSize().width)/2);
//    this->skillNode->setScale(scale);
   

    ui::Button* btn_pause=static_cast<ui::Button*>(top->getChildByName("btn_pause"));
    ui::Button* btn_auto=static_cast<ui::Button*>(skillNode->getChildByName("btn_auto"));
    btn_pause->addTouchEventListener(CC_CALLBACK_2(BattleScene::touchButtonEvent,this));
    btn_auto->addTouchEventListener(CC_CALLBACK_2(BattleScene::touchButtonEvent, this));
    return true;
}

void BattleScene::onEnter()
{
    BaseUI::onEnter();
    this->schedule(SEL_SCHEDULE(&BattleScene::tick), 1);
}

void BattleScene::initNpcIcon(int num)
{
    this->npcInfo=(Widget*)this->top->getChildByName("npc");
    this->bossInfo=(Widget*)this->top->getChildByName("boss");
    if(num>1){
        this->bossInfo->setVisible(false);
        Vec2 sp=this->npcInfo->getChildByName("npcBg")->getPosition();
        Size bSize=this->npcInfo->getChildByName("npcBg")->getContentSize();

        float gap=(bSize.width-20)/(num-1);
        for(int i=0;i<num;i++){
            ImageView* icon=ImageView::create("combat_boss_a2.png");
            this->npcInfo->addChild(icon,0,"icon"+Value(i).asString());
            icon->setPosition(Vec2(12+sp.x-bSize.width/2+gap*i,sp.y));
            if(i==num-1){
                break;
            }
            ImageView* dot=ImageView::create("combat_progress.png");
            this->npcInfo->addChild(dot,1,"dot"+Value(i).asString());
            float dotX=icon->getPositionX()+gap/(num-1);
            dot->setPosition(Vec2(dotX,icon->getPositionY()));
        }
    }else{
        this->npcInfo->setVisible(false);
    }
}

SkillIcon* BattleScene::createSkillIcon(int skillID,int pos,FighterMgr* hero)
{
    ImageView* rim=(ImageView*)skillNode->getChildByName("skill"+Value(pos).asString());
    this->skillNode->removeChild(rim);
    
    SkillIcon* icon=SkillIcon::create(rim,skillID,hero);
    this->skillNode->addChild(icon);
    
    return icon;
}

void BattleScene::setNpcIcon(int num,bool isKill)
{
    for(int i=0;i<num;i++){
        ImageView* icon=(ImageView*)this->npcInfo->getChildByName("icon"+Value(i).asString());
        ImageView* dot=(ImageView*)this->npcInfo->getChildByName("dot"+Value(i).asString());
        ImageView* xx=(ImageView*)this->npcInfo->getChildByName("xx"+Value(i).asString());
        
        dot->loadTexture("combat_progress_a2.png");

        if(!xx){
            xx=ImageView::create("combat_boss_a3.png");
            xx->setPosition(icon->getPosition());
            this->npcInfo->addChild(xx);
        }
    }
}

void BattleScene::resetProgress()
{
    
}

void BattleScene::bounceTo(FighterMgr *npc)
{
    
}

void BattleScene::tick(float dt)
{
    this->duration--;
    std::string str=Value(duration/60).asString()+":"+Value(duration%60).asString();
    Text* cdLabel=static_cast<Text*>(this->top->getChildByName("cdLabel"));
    cdLabel->setString(str);
    if(this->duration<=0){
        this->stopAllActions();
        this->unschedule(SEL_SCHEDULE(&BattleScene::tick));
        BattleMgr::getInstance()->overTime();
    }
}

void BattleScene::interceptTouchEvent(Widget::TouchEventType event, Widget *sender, Touch *touch)
{
    if(event != TouchEventType::ENDED){
        return;
    }
    if(BattleMgr::getInstance()->isOver){
        return;
    }
    if(sender->getParent()->getName() != "icon"){
        return;
    }

    Vec2 end=touch->getLocation();
    end=this->skillNode->convertToNodeSpace(end);
    SkillIcon* icon=static_cast<SkillIcon*>(sender->getParent());
  
    if(icon && icon->skill->isReady && icon->hero && icon->hero->state!=Efstate::die){
        // 使用技能
        icon->skillAttack();
    }
}

void BattleScene::touchButtonEvent(cocos2d::Ref *pSender, Widget::TouchEventType type)
{
    if(type!=TouchEventType::ENDED) return;
    
    Button* sender=dynamic_cast<Button*>(pSender);
    log("tag:%d",sender->getTag());
    if(sender->getTag()==10125){ //pause
        if(this->isPause){
            sender->setBrightStyle(BrightStyle::NORMAL);
            BattleMgr::getInstance()->resume();
        }else{
            sender->setBrightStyle(BrightStyle::HIGHLIGHT);
            BattleMgr::getInstance()->pause();
        }
        this->isPause=!this->isPause;
    }
    if(sender->getTag()==10145){ //auto
                
    }
}

void BattleScene::initNetEvent()
{
    auto listener = EventListenerCustom::create(NET_MESSAGE, [=](EventCustom* event){
        NetMsg* msg = static_cast<NetMsg*>(event->getUserData());
        switch (msg->msgId)
        {
            case C_FIGHTRESULT:
            {
                PResultResp pResultResp;
                pResultResp.ParseFromArray(msg->bytes, msg->len);
                GateResult* gateResult=GateResult::create(this, pResultResp);
                gateResult->show();
                break;
            }
            default:
                break;
        }
    });
    Director::getInstance()->getEventDispatcher()->addEventListenerWithSceneGraphPriority(listener, this);
}

void BattleScene::onExit()
{
    BattleMgr::getInstance()->clear();
    Node::onExit();
    SpriteFrameCache::getInstance()->removeSpriteFrames();
}