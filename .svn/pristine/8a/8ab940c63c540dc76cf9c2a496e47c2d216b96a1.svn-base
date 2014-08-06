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
    this->resetUI();
	return true;
}

void GateResult::resetUI()//初始化界面
{
    Widget* img_bg=layout->getChildByName("img_bg");
    auto btn_return=img_bg->getChildByName("btn_return");
    btn_return->addTouchEventListener(CC_CALLBACK_2(GateResult::touchButtonEvent, this));
    for (int i=0; i<5; i++) {
        Widget* npc=static_cast<Widget*>(img_bg->getChildByName("role_"+Value(i+1).asString()));
        Widget* item=static_cast<Widget*>(img_bg->getChildByName("item_"+Value(i+1).asString()));
        npc->setVisible(i<this->pResultResp.npcs_size());
        item->setVisible(i<this->pResultResp.items_size());
        if (i<this->pResultResp.npcs_size()) {
            PNpcRes pNpcRes=this->pResultResp.npcs(i);
            string addLevel=Value(pNpcRes.addlvl()).asString();
            const string addExp=Value(pNpcRes.addexp()).asString();
            static_cast<Text*>(npc->getChildByName("txt_lvl"))->setString(addLevel);
            std::vector<string> params;
            params.push_back(addExp);
            static_cast<Text*>(npc->getChildByName("txt_exp"))->setString(Utils::getLang("EXP+{1}",params));
            //pNpcRes
        }
        if (i<this->pResultResp.items_size()) {
            PItemRes pItemRes=this->pResultResp.items(i);
            static_cast<Text*>(npc->getChildByName("txt_num"))->setString(Value(pItemRes.itemnum()).asString());
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
                Gate* gate = Gate::create();
                gate->show();
                break;

        }
    }

}

void GateResult::touchEvent(Ref *pSender, TouchEventType type)
{
    switch (type)
    {
        case TouchEventType::BEGAN:
            break;
        case TouchEventType::MOVED:
            break;
        case TouchEventType::ENDED:
        {
        }
            break;
        case TouchEventType::CANCELED:
            break;
        default:
            break;
    }
}

void GateResult::initNetEvent(){
    auto listener = EventListenerCustom::create(NET_MESSAGE, [=](EventCustom* event){
        NetMsg* msg = static_cast<NetMsg*>(event->getUserData());
//        log("Custom event 1 received:%d,%d",msg->msgId,msg->len);
        switch (msg->msgId)
        {
            case C_STARTFIGHT:
            {
//                GateResult* result=GateResult::create(this, "");
//                result->show(this);
                break;
            }
            default:
                break;
        }
    });
    Director::getInstance()->getEventDispatcher()->addEventListenerWithSceneGraphPriority(listener, this);
}
