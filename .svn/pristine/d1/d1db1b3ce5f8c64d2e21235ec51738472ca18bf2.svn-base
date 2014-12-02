//
//  Formation.cpp
//  fancyHeart
//
//  Created by zhai on 14-6-18.
//
//

#include "Formation.h"

Formation* Formation::create(BaseUI* delegate)
{
    Formation* formation=new Formation();
    if (formation && formation->init("publish/formation/formation.ExportJson")) {
        formation->autorelease();
        return formation;
    }
    CC_SAFE_DELETE(formation);
    return nullptr;
}

bool Formation::init(std::string fileName)
{
	if(!BaseUI::init(fileName))
	{
		return false;
	}
    this->resetUI();
	return true;
}

void Formation::onEnter()
{
    BaseUI::onEnter();
}

void Formation::resetUI()//init map
{
    CardItem* cardItem=CardItem::create();
    Widget* item=static_cast<Widget*>(cardItem->item);
    auto widgetSize = Director::getInstance()->getWinSize();
    this->rotateList = RotateList::create();
    this->rotateList->setSize(Size(widgetSize.width, widgetSize.height));
    this->rotateList->addEventListener(CC_CALLBACK_3(Formation::rotateListCallback,this));
    
    for (int i=0; i<Manager::getInstance()->getRoleData()->npcids_size(); i++) {
        int64 npcId=Manager::getInstance()->getRoleData()->npcids(i);
        if(npcId!=0)
            this->formactionIds.push_back(FormationData{npcId,this->isInFormation(npcId)?1:0});
    }
    sort(this->formactionIds.begin(), this->formactionIds.end(), this->sortIds);
    //模版
    item->removeFromParent();
    this->rotateList->setItemModel(item,Director::getInstance()->getWinSize().width,Director::getInstance()->getWinSize(),Director::getInstance()->getWinSize().width/10);//传进去单个模版
    this->addChild(this->rotateList,0);
    this->rotateList->setPosition(Vec2(0,Director::getInstance()->getWinSize().height-item->getContentSize().height-20));
    auto slider=static_cast<Slider*>(layout->getChildByName("slider_0"));
    this->rotateList->setSlider(slider);
    slider->getVirtualRenderer()->setContentSize(Size(474,slider->getVirtualRenderer()->getContentSize().height));
    Button* btnReturn=static_cast<Button*>(layout->getChildByName("btn_return"));
    btnReturn->addTouchEventListener(CC_CALLBACK_2(Formation::touchButtonEvent, this));
    Widget* panel_btns=static_cast<Widget*>(layout->getChildByName("panel_btns"));
    panel_btns->removeFromParent();
    this->addChild(panel_btns,1);
    static_cast<Button*>(panel_btns->getChildByName("btn_all"))->addTouchEventListener(CC_CALLBACK_2(Formation::touchButtonEvent, this));
    static_cast<Button*>(panel_btns->getChildByName("btn_front"))->addTouchEventListener(CC_CALLBACK_2(Formation::touchButtonEvent, this));
    static_cast<Button*>(panel_btns->getChildByName("btn_after"))->addTouchEventListener(CC_CALLBACK_2(Formation::touchButtonEvent, this));
    Widget* imgGroup=static_cast<Widget*>(layout->getChildByName("img_group"));
    
    Widget* paneHero=static_cast<Widget*>(imgGroup->getChildByName("panelHero"));
    paneHero->setVisible(false);
    
    
    this->pageView=NewPageView::create();
    imgGroup->addChild(this->pageView);
    this->pageView->setSize(paneHero->getContentSize());
    this->pageView->setPosition(paneHero->getPosition());
    //this->pageView=static_cast<PageView*>(imgGroup->getChildByName("pageView"));
    this->pageView->addEventListener(CC_CALLBACK_2(Formation::pageViewEvent, this));
    this->pageView->setLayoutType(Type::VERTICAL);
    
    Button* btnChange=static_cast<Button*>(imgGroup->getChildByName("btn_change"));
    btnChange->setLocalZOrder(1);
    btnChange->addTouchEventListener(CC_CALLBACK_2(Formation::touchButtonEvent, this));
    
    
    
    for (int i=0; i<1; i++) {
        Layout* subPage=static_cast<Layout*>(paneHero->clone());
        subPage->setVisible(true);
        pageView->addPage(subPage);
        this->initGroup(i);
        
    }
    this->currentPos=-1;
    this->initList();
}

void Formation::initList()
{
    this->npcIds.clear();
    for(int i=0;i<Manager::getInstance()->getRoleData()->npclist_size();i++){
        PNpc pNpc=Manager::getInstance()->getRoleData()->npclist(i);
        int64 npcdId=pNpc.npcid();
        if (this->currentPos==-1) {
            this->npcIds.push_back(FormationData{npcdId,this->isInFormation(npcdId)?1:0});
        }else{
            XRole* xRole=XRole::record(Value(pNpc.spriteid()));
            if (xRole->getPos()==this->currentPos) {
                this->npcIds.push_back(FormationData{npcdId,this->isInFormation(npcdId)?1:0});
            }
        }
    }
    sort(this->npcIds.begin(), this->npcIds.end(), this->sortIds);
    this->rotateList->setNum(this->npcIds.size());
    Widget* title=static_cast<Widget*>(layout->getChildByName("img_title"));
    Text* txtForce= static_cast<Text*>(title->getChildByName("txt_forceDesc"));
    txtForce->setString(StringUtils::format("%d", int(this->pageView->getCurPageIndex())+1));
    txtForce->enableOutline(Color4B(37,19,0,255));
}

void Formation::setListMask(int index)
{
    //std::vector<FormationData> ids=this->groups.at(index);
    //先把所有遮罩隐藏
    for(int i=0;i<this->rotateList->getItems().size();i++){
        Widget* widget=this->rotateList->getItems().at(i);
        Widget* img_on=(Widget*)widget->getChildByName("img_on");
        int64 npcId=this->npcIds.at(widget->getTag()%10000).npcId;
        img_on->setVisible(this->isInFormation(npcId));
    }

}

bool Formation::isInFormation(int64 npcId)
{
    for(int i=0;i<this->formactionIds.size();i++){
        if (this->formactionIds.at(i).npcId==npcId) {
            return true;
            break;
        }
    }
    return  false;
}

void Formation::changeGroup(int64 npcId,int groupIndex,bool isFormation)
{
    
    std::vector<FormationData> ids=this->formactionIds;
    std::vector<FormationData>::iterator it = ids.begin();
    
    bool isFound=false;
    int index=0;
    for (; it !=ids.end(); ++it)
    {
        index=index+1;
        if (FormationData(*it).npcId==npcId) {
            if (isFormation) {
                ids.erase(it);
            }
            isFound=true;
            break;
        }
    }
    if (!isFormation) {
        ids.push_back(FormationData{npcId,1});
        
    }
    this->isSave=true;
    sort(ids.begin(), ids.end(), this->sortIds);
    this->formactionIds=ids;
    this->setListMask(groupIndex);
    this->initGroup(groupIndex,false);
}

bool Formation::sortIds(FormationData fd1,FormationData fd2)
{
    PNpc* pNpc1=Manager::getInstance()->getNpc(fd1.npcId);
    PNpc* pNpc2=Manager::getInstance()->getNpc(fd2.npcId);
    if (fd1.isFormation!=fd2.isFormation) {
        return fd1.isFormation>fd2.isFormation;
    }else if (pNpc1->level()!=pNpc2->level()) {
        return pNpc1->level()>pNpc2->level();
    }else if (pNpc1->quality()!=pNpc2->quality()){
        return pNpc1->quality()>pNpc2->quality();
    }else if (pNpc1->star()!=pNpc2->star()){
        return pNpc1->star()>pNpc2->star();
    }else{
        return pNpc1->npcid()<pNpc2->npcid();
    }
    
    return  true;
}

void Formation::initGroup(int groupIndex,bool isInit)
{
    //std::vector<FormationData> ids=this->groups.at(groupIndex);
    Layout* subPage=this->pageView->getPage(groupIndex);
    for(int i=0;i<5;i++)
    {
        Widget* img=static_cast<Widget*>(subPage->getChildByName("img_"+Value((i+1)).asString()));
        ImageView* icon=(ImageView*)img->getChildByName("icon");
        Text* txt=static_cast<Text*>(icon->getChildByName("txt_lvl"));
        icon->setVisible(i<this->formactionIds.size());
        if (i<this->formactionIds.size()) {
            PNpc* pNpc=Manager::getInstance()->getNpc(this->formactionIds.at(i).npcId);
            XRole* xRole=XRole::record(Value(pNpc->spriteid()));
            icon->loadTexture("face_"+Value(xRole->getId()).asString()+".png");
            txt->setString(Value(pNpc->level()).asString());
        }
        
        if (isInit) {
            img->setTouchEnabled(true);
            img->setEnabled(true);
            img->addTouchEventListener(CC_CALLBACK_2(Formation::touchEvent, this));
        }
        img->setTag((i+1)*10000+i);
        
        PNpc* pnpc;
        if (i<this->formactionIds.size()) {
            pnpc=Manager::getInstance()->getNpc(this->formactionIds.at(i).npcId);
        }else{
            pnpc=nullptr;
        }
        ImageView* imageView=static_cast<ImageView*>(img->getChildByName("frame"));
        //imageView->loadTexture("frame_0.png");
        imageView->loadTexture(pnpc?"frame_"+Value(Manager::getInstance()->Qualitys[pnpc->quality()].color).asString()+".png":"frame_0.png");
        for(int j=1;j<7;j++){
            ImageView* imageView=static_cast<ImageView*>(img->getChildByName("star_"+Value(j).asString()));
            imageView->setVisible(i<this->formactionIds.size());
            imageView->setLocalZOrder(7-j);
            if (pnpc) {
                imageView->loadTexture(j<=pnpc->star()?"star_1.png":"star_2.png");
            }
        }
    }
}

void Formation::rotateListCallback(RotateList::EventType type,Widget*item,int index)
{
    switch (type)
    {
        //移到中间处理
        case RotateList::EventType::SCROLL_MIDDLE:
        {
            
            break;
        }
        //点击中间list
        case RotateList::EventType::TOUCH_ITEM:
        {
            PNpc* pNpc=Manager::getInstance()->getNpc(this->npcIds.at(index).npcId);
            XRole* xRole=XRole::record(Value(pNpc->spriteid()));
            if (xRole->getIsRole()) {
                Manager::getInstance()->showMsg("主将不能下阵");
                return;
            }
            //Mask* mask=static_cast<Mask*>(item->getChildByName("mask"));
            Widget* img_on=(Widget*)item->getChildByName("img_on");
            //通过mask的显示不显示判断是上阵还是下阵
            bool isDown=img_on->isVisible()?true:false;
            Widget* subPage=this->pageView->getPage(this->pageView->getCurPageIndex());
            Widget* img=static_cast<Widget*>(subPage->getChildByName("img_1"));
            ImageView* icon=(ImageView*)img->getChildByName("icon");
            icon->loadTexture("face_"+Value(xRole->getId()).asString()+".png");
            Widget* iconClone=icon->clone();
            this->displayAction(iconClone, isDown);
            this->changeGroup(pNpc->npcid(), int(this->pageView->getCurPageIndex()), img_on->isVisible());
            break;
        }
        //
        case RotateList::EventType::SET_ITEM_DATA:
        {
            item->setTag(100000+index);
            Widget* img_on=(Widget*)item->getChildByName("img_on");
            img_on->setLocalZOrder(1);
            PNpc* pNpc=Manager::getInstance()->getNpc(this->npcIds.at(index).npcId);
            item->setTag(100000+index);
            img_on->setVisible(this->isInFormation(pNpc->npcid()));
            XRole* xRole=XRole::record(Value(pNpc->spriteid()));
            Text* txtName = static_cast<Text*>(item->getChildByName("txt_name"));
            txtName->setString(xRole->getIsRole()?Manager::getInstance()->getRoleData()->role().rolename():xRole->getName());
            //名字的颜色
            txtName->setColor(Manager::getInstance()->Qualitys[pNpc->quality()].colorLabel);
            for(int i=1;i<7;i++){
                ImageView* imageView=static_cast<ImageView*>(item->getChildByName("star_"+Value(i).asString()));
                imageView->loadTexture(i<=pNpc->star()?"star_1.png":"star_2.png");
            }
            
            static_cast<Text*>(item->getChildByName("txt_lvl"))->setString(Value(pNpc->level()).asString());
            ImageView* imageView=static_cast<ImageView*>(item->getChildByName("img_frame"));
            imageView->loadTexture("hero_cardframe_"+Value(Manager::getInstance()->Qualitys[pNpc->quality()].color).asString()+".png");
            static_cast<ImageView*>(item)->loadTexture("card_"+Value(xRole->getId()).asString()+".png");
            Text* tf_Force=static_cast<Text*>(item->getChildByName("txt_force"));
            FData* fd=FData::create({xRole->getId(),pNpc->level(),pNpc->quality(),pNpc->star(),0,std::vector<int>()});
            tf_Force->setString(StringUtils::format("战力%d",fd->getAttackForce()));
            break;
        }
        default:
            break;
            
    }
}

void Formation::displayAction(Widget* widget,bool isDown)
{
    auto widgetSize = Director::getInstance()->getWinSize();
    widget->setVisible(true);
    Vec2 startP=Vec2(widgetSize.width/2, widgetSize.height/2-100);
    Vec2 endP=Vec2(widgetSize.width/2,widgetSize.height/2);
    widget->setPosition(isDown?startP:endP);
    this->addChild(widget);
    widget->runAction(Sequence::create(Spawn::create(
                                                     MoveTo::create(0.5, isDown?endP:startP),
                                                    FadeOut::create(0.5),NULL),
                                      CallFunc::create(CC_CALLBACK_0(Sprite::removeFromParent, widget)),NULL));
}

void Formation::pageViewEvent(Ref *pSender, PageView::EventType type)
{
    switch (type)
    {
        case PageView::EventType::TURNING:
        {
            if (this->currentPageIndex==int(pageView->getCurPageIndex())) {
                return;
            }
            this->currentPageIndex=int(pageView->getCurPageIndex());
            this->initList();
            break;
        }
        default:
            break;
    }
}

void Formation::touchButtonEvent(Ref *pSender, TouchEventType type)
{
    auto btn=static_cast<Button*>(pSender);
    if (!btn||type!=TouchEventType::ENDED) {
        return;
    }
    //返回按钮
    switch (btn->getTag()) {
        case 12215:
        {
            if (!this->isSave) {
                this->clear(true);
//                rapidjson::Value data={};
//                this->onDlgClose(data);
                return;
            }
            PSaveFormation formation;
            for (int i=0; i<this->formactionIds.size(); i++) {
                formation.add_npcids(this->formactionIds.at(i).npcId);
            }
            Manager::getInstance()->socket->send(C_SAVEFORMATION, &formation);
            break;
        }
        case 12873:
        {
             //this->pageView->scrollToPage(this->pageView->getCurPageIndex()>=4?0:this->pageView->getCurPageIndex()+1);
            break;
        }
        case 13086://全部
        {
            this->currentPos=-1;
            this->initList();
            break;
        }
        case 13087://前排
        {
            this->currentPos=0;
            this->initList();
            break;
        }
        case 13089://中排
        {
            this->currentPos=1;
            this->initList();
            break;
        }
        case 13091://后排
        {
            this->currentPos=2;
            this->initList();
          break;
        }
        default:
            break;
    }
}

void Formation::updateGroups(const std::vector<FormationData> npcIds)
{
    Manager::getInstance()->getRoleData()->mutable_npcids()->Clear();
    for (int i=0; i<npcIds.size(); i++) {
        Manager::getInstance()->getRoleData()->mutable_npcids()->Add(npcIds.at(i).npcId);
    }
}

void Formation::touchEvent(Ref *pSender, TouchEventType type)
{
    if (type!=TouchEventType::ENDED) {
        return;
    }
    

    Widget* widget=static_cast<Widget*>(pSender);
    int index=widget->getTag()%10000;
    if (index>=this->formactionIds.size()) {
        return;
    }
    PNpc* pNpc=Manager::getInstance()->getNpc(this->formactionIds.at(index).npcId);
    XRole* xRole=XRole::record(Value(pNpc->spriteid()));
    if (xRole->getIsRole()) {
        Manager::getInstance()->showMsg("主将不能下阵");
        return;
    }
    Widget* icon=(Widget*)widget->getChildByName("icon");
    this->displayAction(icon->clone(),true);
    this->changeGroup(pNpc->npcid(), int(this->pageView->getCurPageIndex()), true);
    
}

void Formation::initNetEvent(){
    auto listener = EventListenerCustom::create(NET_MESSAGE, [=](EventCustom* event){
        NetMsg* msg = static_cast<NetMsg*>(event->getUserData());
        switch (msg->msgId)
        {
            case C_COMMONMSG:
            {
                PCommonResp pCommonResp;
                pCommonResp.ParseFromArray(msg->bytes, msg->len);
                if(pCommonResp.resulttype()==C_SAVEFORMATION){
                    this->updateGroups(this->formactionIds);
                    this->clear(true);
//                    rapidjson::Value data={};
//                    this->onDlgClose(data);
                }
                break;
            }
            default:
                break;
        }
    });
    Director::getInstance()->getEventDispatcher()->addEventListenerWithSceneGraphPriority(listener, this);
    
}

void Formation::onExit()
{
    BaseUI::onExit();
}
