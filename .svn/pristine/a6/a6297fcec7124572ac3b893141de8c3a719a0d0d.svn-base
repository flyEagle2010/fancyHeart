//
//  GateSelect.cpp
//  fancyHeart
//
//  Created by zhai on 14-6-18.
//
//

#include "GateSelect.h"
#include "BattleMgr.h"
GateSelect* GateSelect::create(BaseUI* delegate,int gateId,int nodeId)
{
    GateSelect* gateSelect=new GateSelect();
    if (gateSelect && gateSelect->init("publish/gateSelect/gateSelect.ExportJson",gateId,nodeId)) {
        gateSelect->autorelease();
        return gateSelect;
    }
    CC_SAFE_DELETE(gateSelect);
    return nullptr;
}

bool GateSelect::init(std::string fileName,int gateId,int nodeId)
{
	if(!BaseUI::init(fileName))
	{
		return false;
	}
	//init ui
    this->gateId=gateId;
    this->nodeId=nodeId;
    Widget* widget=static_cast<Widget*>(layout->getChildByName("img_bg"));
    XGate* xg=XGate::record(Value(this->gateId));
    XNode* xNode=XNode::record(Value(this->nodeId));
    XBattle* xbattle=XBattle::record(Value(xNode->getBattleID()));
    Text* txtTitle=static_cast<Text*>(widget->getChildByName("txt_title"));
    txtTitle->setString(xbattle->getName());
    Label* lb=static_cast<Label*>(txtTitle->getVirtualRenderer());
    lb->enableOutline(Color4B(19, 8, 1, 255),1);
    Text* txtDesc=static_cast<Text*>(widget->getChildByName("txt_desc"));
    txtDesc->setString(xbattle->getDes());
    static_cast<Label*>(static_cast<Text*>(widget->getChildByName("txt_desc"))->getVirtualRenderer())->enableOutline(Color4B(44, 25, 3, 255),1);
    Button* btnEdit=static_cast<Button*>(widget->getChildByName("btn_edit"));
    btnEdit->addTouchEventListener(CC_CALLBACK_2(GateSelect::touchButtonEvent, this));
//    btnEdit->setTouchEnabled(false);
//    btnEdit->getVirtualRenderer()->setColor(Color3B::GRAY);
    
    Widget* paneGoup=static_cast<Widget*>(widget->getChildByName("panel_group"));
    std::vector<Button*> buttons;
    for (int i=1; i<6; i++) {
        Button* button=static_cast<Button*>(paneGoup->getChildByName("group_"+Value(i).asString()));
        button->addTouchEventListener(CC_CALLBACK_2(GateSelect::touchButtonEvent,this));
        buttons.push_back(static_cast<Button*>(button));
    }
    
    Widget* paneDrop=static_cast<Widget*>(widget->getChildByName("panel_drop"));
    string strDrop=xbattle->getDropItems();
    std::vector<std::string> items=Utils::split(xbattle->getDropItems(), ",");
    for (int i=1; i<items.size()+1; i++) {
        ImageView* item=static_cast<ImageView*>(paneDrop->getChildByName("img_"+Value(i).asString()));
        XItem* xItem=XItem::record(Value(items.at(i-1)));
        item->loadTexture("frame_"+Value(Manager::getInstance()->Qualitys[xItem->getRate()].color).asString()+".png");
        Sprite* sprite=Sprite::create("item_"+Value(xItem->getIcon()).asString()+".png");
        sprite->setAnchorPoint(Vec2(0, 0));
        sprite->setPosition(Vec2(6, 8));
        item->addChild(sprite);
        item->addTouchEventListener(CC_CALLBACK_2(GateSelect::touchItemEvent, this));
        item->setTouchEnabled(true);
        item->setTag(Value(items.at(i-1)).asInt());
    }
    tabBar=TabBar::create(buttons);
    tabBar->retain();
    this->txtGroup=static_cast<Text*>(widget->getChildByName("txt_groupName"));
    auto button=static_cast<Button*>(widget->getChildByName("btn_start"));
    button->addTouchEventListener(CC_CALLBACK_2(GateSelect::touchButtonEvent, this));
    button=static_cast<Button*>(layout->getChildByName("btn_return"));
    button->addTouchEventListener(CC_CALLBACK_2(GateSelect::touchButtonEvent, this));
    
    Button* btn_sweep=static_cast<Button*>(widget->getChildByName("btn_sweep"));
    btn_sweep->setTouchEnabled(false);
    btn_sweep->setColor(Color3B::GRAY);
    this->initGroup();
	return true;
}

void GateSelect::initGroup()
{
    Widget* widget=static_cast<Widget*>(layout->getChildByName("img_bg"));
    Widget* paneGoup=static_cast<Widget*>(widget->getChildByName("panel_group"));
    NewPageView* pageView=NewPageView::create();
    PageView* pageView2=static_cast<PageView*>(paneGoup->getChildByName("pageView"));
    paneGoup->addChild(pageView);
    Vec2 p=pageView2->getPosition();
    pageView->setPosition(p);
    pageView->setSize(pageView2->getSize());
    pageView->addEventListener(CC_CALLBACK_2(GateSelect::pageViewEvent, this));
    Widget* panelHero=static_cast<Widget*>(paneGoup->getChildByName("panelHero"));
    panelHero->setVisible(false);
    for (int i=0; i<1; i++) {
        Layout* subPage=static_cast<Layout*>(panelHero->clone());
        subPage->setVisible(true);
        pageView->addPage(subPage);
        ::google::protobuf::RepeatedField< ::google::protobuf::int64 > npcIds=Manager::getInstance()->getRoleData()->npcids();
        for(int j=0;j<5;j++)
        {
            
            ImageView* img=static_cast<ImageView*>(subPage->getChildByName("img_"+Value((j+1)).asString()));
            ImageView* icon=(ImageView*)img->getChildByName("icon");
            if (j<npcIds.size() && npcIds.Get(j)!=0) {
                PNpc* pNpc=Manager::getInstance()->getNpc(npcIds.Get(j));
                XRole* xRole=XRole::record(Value(pNpc->spriteid()));
                icon->loadTexture("face_"+Value(xRole->getId()).asString()+".png");
                img->loadTexture("frame_"+Value(Manager::getInstance()->Qualitys[pNpc->quality()].color).asString()+".png");
            }
            icon->setVisible(j<npcIds.size() && npcIds.Get(j)!=0);

        }
    }
}

void GateSelect::pageViewEvent(Ref *pSender, PageView::EventType type)
{
    switch (type)
    {
        case PageView::EventType::TURNING:
        {
            PageView* pageView = dynamic_cast<PageView*>(pSender);
            this->txtGroup->setString(StringUtils::format("卡组%d",int(pageView->getCurPageIndex()+1)));
            tabBar->setIndex(int(pageView->getCurPageIndex()));
//            Layout* subPage=pageView->getPage(int(pageView->getCurPageIndex()));
//            for(int i=0;i<5;i++)
//            {
//                ImageView* img=static_cast<ImageView*>(subPage->getChildByName("img_"+Value((i+1)).asString()));
//                ImageView* icon=(ImageView*)img->getChildByName("icon");
//                if (i<pGroup.npcid_size() && pGroup.npcid(i)!=0) {
//                    PNpc* pNpc=Manager::getInstance()->getNpc(pGroup.npcid(j));
//                    XRole* xRole=XRole::record(Value(pNpc->spriteid()));
//                    icon->loadTexture("face_"+Value(xRole->getId()).asString()+".png");
//                    img->loadTexture("frame_"+Value(Manager::getInstance()->Qualitys[pNpc->quality()].color).asString()+".png");
//                }
//                icon->setVisible(j<pGroup.npcid_size() && pGroup.npcid(j)!=0);
//                
//            }
        }
            break;
            
        default:
            break;
    }
}

void GateSelect::touchItemEvent(Ref *pSender, TouchEventType type)
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

void GateSelect::touchButtonEvent(Ref *pSender, TouchEventType type)
{
    Button* btn=static_cast<Button*>(pSender);
    if (!btn) {
        return;
    }
    if(type==TouchEventType::ENDED)
    {
        switch (btn->getTag()) {
            case 10505://开始
            {
                PNodeReq pNodeReq;
                pNodeReq.set_gateid(this->gateId);
                pNodeReq.set_xid(this->nodeId);
                pNodeReq.set_groupid(this->tabBar->getIndex());
                Manager::getInstance()->socket->send(C_STARTFIGHT, &pNodeReq);
                break;
            }
            case 10522://返回
            {
                this->clear(true);
                break;
            }
            case 10515://返回
            case 10517:
            case 10518://返回
            case 10519:
            case 10520://返回
            {
                tabBar->setIndex(tabBar->getIndex());
                break;
            }
            case 10524://编组
                Formation::create(this)->show(this);
                break;
            default:
                break;
        }
    }
}

void GateSelect::initNetEvent(){
    auto listener = EventListenerCustom::create(NET_MESSAGE, [=](EventCustom* event){
        NetMsg* msg = static_cast<NetMsg*>(event->getUserData());
        log("Custom event 1 received:%d,%d",msg->msgId,msg->len);
        switch (msg->msgId)
        {
            case C_STARTFIGHT:
            {
                PNodeResp pNodeResp;
                pNodeResp.ParseFromArray(msg->bytes, msg->len);
                if (pNodeResp.state()==1) {//请求节点成功
//                    PResultReq pResultReq;
//                    pResultReq.set_gateid(this->gateId);
//                    pResultReq.set_xid(this->nodeId);
//                    pResultReq.set_star(3);
//                    Manager::getInstance()->socket->send(C_FIGHTRESULT, &pResultReq);
//                    int itemCount=pNodeResp.itemcount();
//                    return ;
                    
                    
                    std::vector<long long> heros;
                    for(int i=0;i<Manager::getInstance()->getRoleData()->npcids().size();i++){
                        if (Manager::getInstance()->getRoleData()->npcids(i)!=0) {
                            heros.push_back(Manager::getInstance()->getRoleData()->npcids(i));
                        }
                    }
                    Manager::getInstance()->gateId=this->gateId;
                    BattleMgr::getInstance()->init(heros,this->nodeId,this->gateId);
                }
            }
            break;
            case C_FIGHTRESULT:
            {
//                PResultResp pResultResp;
//                pResultResp.ParseFromArray(msg->bytes, msg->len);
//                GateResult* gateResult=GateResult::create(this, pResultResp);
//                gateResult->show(this);
              break;
            }
               
            default:
                break;
        }
    });
    Director::getInstance()->getEventDispatcher()->addEventListenerWithSceneGraphPriority(listener, this);
}

void GateSelect::onExit()
{
    tabBar->release();
    BaseUI::onExit();
}

