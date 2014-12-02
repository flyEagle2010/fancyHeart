//
//  GateResult.cpp
//  fancyHeart
//
//  Created by zhai on 14-6-18.
//
//

#include "GateResult.h"

GateResult* GateResult::create(BaseUI* delegate,PResultResp pResultResp)
{
    GateResult* gateResult=new GateResult();
    if (gateResult && gateResult->init("publish/gateResult/gateResult.ExportJson",pResultResp)) {
        gateResult->autorelease();
        return gateResult;
    }
    CC_SAFE_DELETE(gateResult);
    return nullptr;
}

bool GateResult::init(std::string fileName,PResultResp pResultResp)
{
	if(!BaseUI::init(fileName))
	{
		return false;
	}
    this->pResultResp=pResultResp;
    
    //this->resetUI();
	return true;
}

void GateResult::onEnter()
{
    BaseUI::onEnter();
    bool isWin=this->pResultResp.star()!=0;
    if (isWin) {
        Widget* img_bg=(Widget*)layout->getChildByName("img_bg");
        Button* btn_return=(Button*)layout->getChildByName("btn_return");
        btn_return->addTouchEventListener(CC_CALLBACK_2(GateResult::touchButtonEvent, this));
        img_bg->setVisible(false);
        btn_return->setVisible(false);
        Widget* panel_win=(Widget*)layout->getChildByName("panel_win");
        panel_win->setAnchorPoint(Vec2(0.5, 0.5));
        panel_win->setScale(0.2);
        panel_win->setVisible(true);
        Widget* img_round=(Widget*)panel_win->getChildByName("img_round");
        img_round->runAction(RepeatForever::create(RotateBy::create(1.0f, 360)));
        Clip* clip=Clip::create("winEffect.plist", "winEffect");
        clip->setPosition(panel_win->getContentSize().width/2,panel_win->getContentSize().height/2);
        clip->setScale(2, 2);
        panel_win->addChild(clip);
        clip->play(true);
        panel_win->runAction(Sequence::create(ScaleTo::create(0.3, 1),DelayTime::create(1),CCCallFunc::create(CC_CALLBACK_0(GateResult::resetUI, this)),CallFunc::create(CC_CALLBACK_0(Sprite::removeFromParent, panel_win)), NULL));
    }else{
        this->resetUI();
    }
    
    
}

void GateResult::resetUI()//初始化界面
{
    Widget* img_bg=(Widget*)layout->getChildByName("img_bg");
    img_bg->setVisible(true);
    bool isWin=this->pResultResp.star()!=0;
    Widget* img_win=(Widget*)img_bg->getChildByName("img_win");
    Widget* img_lose=(Widget*)img_bg->getChildByName("img_lose");
    img_win->setVisible(isWin);
    img_lose->setVisible(!isWin);
    Button* btn_return=(Button*)layout->getChildByName("btn_return");
    btn_return->setVisible(true);
    btn_return->addTouchEventListener(CC_CALLBACK_2(GateResult::touchButtonEvent, this));
    Text* tf_groupLvl=static_cast<Text*>(img_bg->getChildByName("tf_groupLvl"));
    Text* tf_groupExp=static_cast<Text*>(img_bg->getChildByName("tf_groupExp"));
    Text* tf_coin=static_cast<Text*>(img_bg->getChildByName("tf_coin"));
    tf_groupLvl->setString(Value(this->pResultResp.curgrouplvl()).asString());
    tf_groupExp->setString(StringUtils::format("+%d",this->pResultResp.groupexp()));
    tf_coin->setString(StringUtils::format("+%d",this->pResultResp.coin()));
    for(int i=1;i<=3;i++){
        Node* star=img_bg->getChildByName("star_"+Value(i).asString());
        star->setVisible(i<=this->pResultResp.star());
    }
    for (int i=0; i<5; i++) {
        Widget* npc=static_cast<Widget*>(img_bg->getChildByName("role_"+Value(i+1).asString()));
        ImageView* item=static_cast<ImageView*>(img_bg->getChildByName("item_"+Value(i+1).asString()));
        npc->setVisible(i<this->pResultResp.npcs_size());
        item->setVisible(i<this->pResultResp.items_size());
        if (i<this->pResultResp.npcs_size()) {
            PNpcRes pNpcRes=this->pResultResp.npcs(i);
            string addLevel=Value(pNpcRes.addlvl()).asString();
            string addExp=Value(pNpcRes.addexp()).asString();
            int totalLvl=pNpcRes.curlvl()+pNpcRes.addlvl();
            int oldExp=Manager::getInstance()->getCurrExp(pNpcRes.curexp(), pNpcRes.curlvl());
            int currExp=Manager::getInstance()->getCurrExp(pNpcRes.curexp()+pNpcRes.addexp(), totalLvl);
            int neddExp=XExp::record(Value(totalLvl))->getExp();
            float oldPrecent=float(oldExp*100/neddExp);
            float curPrecent=float(currExp*100/neddExp);
            static_cast<TextAtlas*>(npc->getChildByName("txt_lvl"))->setString(Value(totalLvl).asString());
            auto action = ProgressTo::create(abs(curPrecent-oldExp)/100, curPrecent);
            LoadingBar* loadingBar=static_cast<LoadingBar*>(npc->getChildByName("pro_exp"));
            loadingBar->setVisible(false);
            auto progressTimer = ProgressTimer::create(static_cast<Sprite*>(loadingBar->getVirtualRenderer()));
            progressTimer->setType(ProgressTimer::Type::BAR);
            progressTimer->setPosition(loadingBar->getPosition());
            npc->addChild(progressTimer);
            //Setup for a bar starting from the left since the midpoint is 0 for the x
            progressTimer->setMidpoint(Vec2(0,0));
            //Setup for a horizontal bar since the bar change rate is 0 for y meaning no vertical change
            progressTimer->setBarChangeRate(Vec2(1, 0));
            progressTimer->setPercentage(oldPrecent>curPrecent?0:oldExp);
            progressTimer->runAction(action);
            static_cast<Text*>(npc->getChildByName("txt_addExp"))->setString(StringUtils::format("EXP+%d",pNpcRes.addexp()));
            if(pNpcRes.addlvl()>0){
                Clip* clip=Clip::create("levelUp.plist", "levelUp",8);
                clip->setPosition(npc->getContentSize().width/2-5,npc->getContentSize().height/2-7);
                clip->play(true);
                npc->addChild(clip);
            }
            

            //loadingBar->setPercent(50);
            //static_cast<Text*>(npc->getChildByName("txt_exp"))->setString(Utils::getLang("EXP+{1}",params));
            //pNpcRes
        }
        if (i<this->pResultResp.items_size()) {
            PItemRes pItemRes=this->pResultResp.items(i);
            static_cast<Text*>(item->getChildByName("txt_num"))->setString(Value(pItemRes.itemnum()).asString());
            XItem* xItem=XItem::record(Value(pItemRes.itemid()));
            item->loadTexture("item_"+Value(xItem->getIcon()).asString()+".png");
            item->addTouchEventListener(CC_CALLBACK_2(GateResult::touchItemEvent, this));
            item->setTouchEnabled(true);
            item->setTag(pItemRes.itemid());
            Manager::getInstance()->showMsg(StringUtils::format("获得%sX%d",xItem->getName().c_str(),pItemRes.itemnum()));

        }
    }
}

void GateResult::touchButtonEvent(Ref *pSender, TouchEventType type)
{
    auto btn=static_cast<Button*>(pSender);
    if (!btn) {
        return;
    }
    if(type==TouchEventType::ENDED){
        switch (btn->getTag()) {
            case 10611:
                Manager::getInstance()->switchScence(HomeScene::createScene());
                auto gate = Gate::create();
                gate->show();
                
                break;

        }
    }

}

void GateResult::touchItemEvent(Ref *pSender, TouchEventType type)
{
    Widget* widget=static_cast<Widget*>(pSender);
    if(type==TouchEventType::BEGAN)
    {
        Manager::getInstance()->showItemTips(widget->getTag(),widget->getTouchBeganPosition());
    }
    else if(type==TouchEventType::ENDED){
        Manager::getInstance()->hideItemTips();
    }else if(type==TouchEventType::CANCELED){
        Manager::getInstance()->hideItemTips();
    }
}

void GateResult::onExit()
{
    BaseUI::onExit();

}

