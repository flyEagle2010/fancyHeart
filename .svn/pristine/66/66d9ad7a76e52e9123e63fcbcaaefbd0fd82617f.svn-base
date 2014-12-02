//
//  Compose.cpp
//  fancyHeart
//
//  Created by doteyplay on 14-8-20.
//
//

#include "Compose.h"

Compose* Compose::create(BaseUI* delegate,int composeId)
{
    Compose* compose=new Compose();
    if (compose && compose->init("publish/compose/compose.ExportJson",composeId,delegate)) {
        compose->autorelease();
        return compose;
    }
    CC_SAFE_DELETE(compose);
    return nullptr;
}

bool Compose::init(std::string fileName,int composeId,BaseUI* delegate)
{
	if(!BaseUI::init(fileName))
    {
        return false;
    }
	//如果需要对cocostudio 设计的ui进行调整
    this->delegates = delegate;
    layout->addTouchEventListener(CC_CALLBACK_2(Compose::touchEvent, this));
    this->img_bg=static_cast<Widget*>(layout->getChildByName("img_bg"));
    this->currentIndex=1;
    //this->indexs.push_back(composeId);
    for (int i=1; i<6; i++) {
         Widget* item=(Widget*)this->img_bg->getChildByName("item_"+Value(i).asString());
        item->setVisible(false);
    }
    this->createIconBox(composeId,"item_1",1);
    Widget* panel=static_cast<Widget*>(this->img_bg->getChildByName("panel"));
    Widget* btn=static_cast<Widget*>(panel->getChildByName("btnCompose"));
    
    btn->addTouchEventListener(CC_CALLBACK_2(Compose::touchButtonEvent, this));
    this->initUi(composeId);
	return true;
}

void Compose::initUi(int composeId)
{
    XCraft* xCraft=XCraft::record(Value(composeId));
    if(xCraft->doc[Value(composeId).asString().c_str()].IsNull()){
        return;
    }
    if (this->composeId==composeId) {
        return;
    }
    Widget* panel=static_cast<Widget*>(this->img_bg->getChildByName("panel"));
    panel->setOpacity(0);
    panel->runAction(FadeTo::create(0.3, 255));
    this->composeId=composeId;
    this->createIconBox(composeId,"img_6",6);
    static_cast<Text*>(panel->getChildByName("txt_coin"))->setString(Value(xCraft->getCost()).asString());
    XItem* xItem=XItem::record(Value(composeId));
    Text* nameLabel = static_cast<Text*>(panel->getChildByName("nameLabel"));
    nameLabel->setString(Value(xItem->getName()).asString());
    //装备名字颜色
    std::vector<const cocos2d::Color3B> colorsLabel={Color3B::WHITE,Color3B::GREEN,Color3B::BLUE,Color3B::MAGENTA,Color3B::YELLOW};//颜色：白绿蓝紫金
    nameLabel->setColor(colorsLabel[xItem->getRate()]);
    
    //合成需要得材料数量
    int needCount=0;
    Widget* centerImg=(Widget*)panel->getChildByName("img_6");
    Vec2 centerPoint=centerImg->getPosition();
    int radias=156;
    
    for (int i=1; i<6; i++) {
        int id =xCraft->doc[Value(composeId).asString().c_str()][("item"+Value(i).asString()).c_str()].GetInt();
        if (id==0) {
            continue;
        }
        needCount++;
    }
    //needCount=5;
    int degree=180/(needCount+1);//起始角度
    int splitDegree=180/(needCount+1);//分割角度
    if(needCount==5){
        degree=0;
        splitDegree=180/(needCount-1);
    }else if(needCount==4){
        degree=22;
        splitDegree=45;
    }
    ImageView* item_bg=static_cast<ImageView*>(panel->getChildByName("item_bg"));
    item_bg->loadTexture("pop_blank_"+Value(needCount).asString()+".png");
    
    for (int i=1; i<6; i++) {
        int id =xCraft->doc[Value(composeId).asString().c_str()][("item"+Value(i).asString()).c_str()].GetInt();
        Widget* img=(Widget*)panel->getChildByName("img_"+Value(i).asString());
        Text* txt= static_cast<Text*>(img->getChildByName("txt_num"));
        txt->setVisible(false);
        img->setVisible(id!=0);
        
        if (id==0) {
            continue;
        }
        
        txt->setVisible(true);
        Widget* imgSprite=static_cast<Widget*>(img->getChildByName("imgSprite"));
        if (imgSprite) {
            imgSprite->removeFromParent();
        }
        this->createIconBox(id,"img_"+Value(i).asString(),i);
        img->setVisible(i<=needCount);
        //设置数量显示
        int num =xCraft->doc[Value(composeId).asString().c_str()][("num"+Value(i).asString()).c_str()].GetInt();
        PItem* item=Manager::getInstance()->getPropItem(id);
        int hasNum=item?item->itemnum():0;
        this->isEnough=hasNum>=num;
        txt->setString(StringUtils::format("%d/%d",hasNum,num));
        if(hasNum<num){
            txt->setColor(Color3B::RED);
            txt->enableOutline(Color4B(45,0,0,255));
        }else{
            txt->setColor(Color3B::GREEN);
            txt->enableOutline(Color4B(1,25,0,255));
        }
        log("degree:%d",degree);
        img->setPosition(Vec2(centerPoint.x-radias*cos(PI/180*degree), centerPoint.y-radias*sin(PI/180*degree)));
        degree=degree+splitDegree;
        
       
    }
    
}

void Compose::onEnter()
{
    BaseUI::onEnter();
}

void Compose::createIconBox(int itemId,string parentName,int index)
{
    XItem* xItem=XItem::record(Value(itemId));
    Widget* panel=static_cast<Widget*>(this->img_bg->getChildByName("panel"));
    ImageView* img=(ImageView*)this->img_bg->getChildByName(parentName);
    if (!img) {
        img=(ImageView*)panel->getChildByName(parentName);
    }
    Sprite* sprite=static_cast<Sprite*>(img->getChildByName("imgSprite"));
    if (sprite) {
        sprite->removeFromParent();
    }
    sprite=Sprite::create("item_"+Value(xItem->getIcon()).asString()+".png");
    sprite->setName("imgSprite");
    //Sprite* spriteFrame=Sprite::create("Damascene.png");
    //Sprite* maskSprite=Utils::maskedSpriteWithSprite(sprite, spriteFrame);
    sprite->setAnchorPoint(Vec2(0, 0));
    img->addChild(sprite);
    img->loadTexture("hero_circle_"+Value(xItem->getRate()+1).asString()+".png");
    //img->loadTexture("hero_circle_2.png");
    img->setTag(itemId*1000+index);//为了获取当前点中的物品id和index 所以做个组合
    img->setTouchEnabled(true);
    Size imgSize=img->getContentSize();
    Size spriteSize=sprite->getContentSize();
    if(parentName.find("item")==-1){
        sprite->setPosition(Vec2((imgSize.width-spriteSize.width)/2, (imgSize.height-spriteSize.height)/2));
        img->addTouchEventListener(CC_CALLBACK_2(Compose::touchIconEvent, this));
    }else{
        sprite->setPosition(Vec2(Vec2((imgSize.width-spriteSize.width)/2, (imgSize.height-spriteSize.height)/2)));
        img->addTouchEventListener(CC_CALLBACK_2(Compose::touchItemEvent, this));
    }
    img->setTag(itemId*1000+index);
    img->setVisible(true);
}

void Compose::removeItemToEnd(int index)
{
    for (int i=index+1; i<6; i++) {
        Widget* item=(Widget*)this->img_bg->getChildByName("item_"+Value(i).asString());
        item->setVisible(false);
        Widget* iconBox=static_cast<Widget*>(item->getChildByName("iconBox"));
        if(iconBox)item->removeChild(iconBox);
    }
}

Widget* Compose::getImgBg()
{
    return this->img_bg;
}

void Compose::touchEvent(Ref *pSender, TouchEventType type)
{
    if (type!=TouchEventType::ENDED) {
        return;
    }
    
    if (this->img_bg->hitTest(static_cast<Layout*>(pSender)->getTouchStartPos())||static_cast<EquipInfo*>(this->delegates)->imgBg->hitTest(static_cast<Layout*>(pSender)->getTouchStartPos())) {
        return;
    }
    this->clear(true);
    this->delegates->clear(true);
    
}

void Compose::touchButtonEvent(Ref *pSender, TouchEventType type)
{
    Button* btn=static_cast<Button*>(pSender);
    if (!btn) {
        return;
    }
    if(type!=TouchEventType::ENDED){
        return;
    }
    if (btn->getTag()==12647) {
        if (!this->isEnough) {
            Manager::getInstance()->showMsg("物品数量不足!");
            return;
        }
        XCraft* xCraft=XCraft::record(Value(this->composeId));
        if (Manager::getInstance()->getRoleData()->role().coin()<xCraft->getCost()) {
            Manager::getInstance()->showMsg("钱不够");
            return;
        }
        PComposeItem pComposeItem;
        pComposeItem.set_itemid(this->composeId);
        pComposeItem.set_itemnum(1);
        Manager::getInstance()->socket->send(C_COMPOSEITEM, &pComposeItem);
        //合成
    }
}

void Compose::touchIconEvent(Ref *pSender, TouchEventType type)
{
    if (type!=TouchEventType::ENDED) {
        return;
    }
    Widget* widget=static_cast<Widget*>(pSender);
    int itemId=widget->getTag()/1000;
    int index=widget->getTag()%1000;
    if (index>5) {
        return;
    }
    XCraft* xCraft=XCraft::record(Value(itemId));
    if(xCraft->doc[Value(itemId).asString().c_str()].IsNull()){
        return;
    }
    this->currentIndex=this->currentIndex+1;
    this->createIconBox(itemId,"item_"+Value(this->currentIndex).asString(),this->currentIndex);
    this->initUi(static_cast<Widget*>(pSender)->getTag()/1000);
    
}

void Compose::touchItemEvent(Ref *pSender, TouchEventType type)
{
    if (type!=TouchEventType::ENDED) {
        return;
    }
    Widget* widget=static_cast<Widget*>(pSender);
    int index=widget->getTag()%1000;
    this->currentIndex=index;
    this->removeItemToEnd(index);
    this->initUi(static_cast<Widget*>(pSender)->getTag()/1000);
    
}

void Compose::initNetEvent(){
    auto listener = EventListenerCustom::create(NET_MESSAGE, [=](EventCustom* event){
        NetMsg* msg = static_cast<NetMsg*>(event->getUserData());
        switch (msg->msgId)
        {
            case C_UPITEM:
            {
                this->currentIndex=max(1, this->currentIndex-1);
                this->composeId=0;
                this->removeItemToEnd(this->currentIndex);
                Widget* item=(Widget*)this->img_bg->getChildByName("item_"+Value(this->currentIndex).asString());
                this->initUi(item->getTag()/1000);
                break;
                
            }
            case C_COMMONMSG:
            {
                PCommonResp pCommonResp;
                pCommonResp.ParseFromArray(msg->bytes, msg->len);
                if(pCommonResp.resulttype()==C_COMPOSEITEM){
                    if (pCommonResp.status()!=0) {
                        Manager::getInstance()->showMsg("合成失败,物品数量不足或者钱不够");
                    }else{
                         Manager::getInstance()->showMsg("合成成功");
                    }
                }
                break;
            }
            default:
                break;
        }
    });
    Director::getInstance()->getEventDispatcher()->addEventListenerWithSceneGraphPriority(listener, this);
}

void Compose::onExit()
{
    BaseUI::onExit();
}