//
//  Bag.cpp
//  fancyHeart
//
//  Created by doteyplay on 14-8-5.
//
//

#include "Bag.h"

Bag* Bag::create()
{
    Bag* bag=new Bag();
    if (bag && bag->init("publish/bag/bag.ExportJson")) {
        bag->autorelease();
        return bag;
    }
    CC_SAFE_DELETE(bag);
    return nullptr;
}

bool Bag::init(std::string fileName)
{
	if(!BaseUI::init(fileName))
    {
        return false;
    }
	//如果需要对cocostudio 设计的ui进行调整
    this->selectNumber= 1;
    Widget*imgBg = static_cast<Widget*>(layout->getChildByName("imgBg"));
    this->rightBg=static_cast<Widget*>(imgBg->getChildByName("rightBg"));
    this->leftBg=static_cast<Widget*>(imgBg->getChildByName("leftBg"));
    this->top=static_cast<Widget*>(layout->getChildByName("top"));
    this->qualityPanel = static_cast<Widget*>(this->leftBg->getChildByName("qualityPanel"));
    this->sellPanel = static_cast<Widget*>(this->leftBg->getChildByName("sellPanel"));
    this->sellBtn=static_cast<Button*>(leftBg->getChildByName("sellBtn"));
    this->lookBtn=static_cast<Button*>(leftBg->getChildByName("lookBtn"));
    Button* btnReduce=static_cast<Button*>(sellPanel->getChildByName("btnReduce"));
    Button* btnAdd=static_cast<Button*>(sellPanel->getChildByName("btnAdd"));
    Button* btnAll=static_cast<Button*>(sellPanel->getChildByName("btnAll"));
    this->isShow=false;
    this->isNotAllSell = false;
    
    scrollView=static_cast<ui::ScrollView*>(this->rightBg->getChildByName("scrollView"));
    
    scrollView->addEventListener(CC_CALLBACK_2(Bag::scrollEvent, this));
    this->propItem=(Widget*)scrollView->getChildByName("propItem");
    this->propItem->retain();
    this->itemSize=this->propItem->getContentSize();
    //this->itemInfo->Clear();
    //道具的显示（-1表示显示全部）
    this->currentType = -1;
    this->getTypeInfo(this->currentType);
    
    sellBtn->addTouchEventListener(CC_CALLBACK_2(Bag::touchButtonEvent, this));
    lookBtn->addTouchEventListener(CC_CALLBACK_2(Bag::touchButtonEvent, this));
    btnReduce->addTouchEventListener(CC_CALLBACK_2(Bag::touchButtonEvent,this));
    btnAdd->addTouchEventListener(CC_CALLBACK_2(Bag::touchButtonEvent,this));
    btnAll->addTouchEventListener(CC_CALLBACK_2(Bag::touchButtonEvent,this));
    
    std::vector<Button*> buttons;
    std::vector<std::string> btnName={"tab1","tab2","tab3","tab4","tab5"};
    for (std::string name : btnName)
    {
        Button* btn=static_cast<Button*>(this->rightBg->getChildByName(name));
        btn->setTouchEnabled(true);
        btn->addTouchEventListener(CC_CALLBACK_2(Bag::touchEvent,this));
        buttons.push_back(btn);
    }
    Button* btn1=static_cast<Button*>(layout->getChildByName("returnBtn"));
    btn1->setTouchEnabled(true);
    btn1->addTouchEventListener(CC_CALLBACK_2(Bag::touchEvent,this));
    //移除舞台上的单个item
    this->propItem->removeFromParent();
    
    tabBar=TabBar::create(buttons);
    tabBar->retain();

    this->bgChange(true);
	return true;
}

void Bag::onEnter()
{
    BaseUI::onEnter();
    this->setTouchEnabled(true);//
    this->scheduleUpdate();//
}

std::vector<PItem*>  Bag::getItemByType(int type)
{
    ::google::protobuf::RepeatedPtrField< ::PItem > itemlist = Manager::getInstance()->getRoleData()->itemlist();
    std::vector<PItem*> list;
    for (int i = 0;i<itemlist.size();i++)
    {
        PItem* item = Manager::getInstance()->getRoleData()->mutable_itemlist(i);
        XItem*xItem = XItem::record(Value(item->itemid()));
        if (type==-1) {
            list.push_back(item);
        }
        else if (xItem->getType() == type) {
            list.push_back(item);
        }
    }
    return list;
}

void Bag::getTypeInfo(int type)
{
    this->leftBg->setVisible(this->isShow);
    this->itemList = this->getItemByType(type);
    ScrollView*scrollView=static_cast<ui::ScrollView*>(this->rightBg->getChildByName("scrollView"));
    this->isScolling=false;
    int num=this->itemList.size();
    this->sSize=Size(scrollView->getInnerContainerSize().width,MAX(scrollView->getContentSize().height,(this->propItem->getContentSize().height+this->space)*ceil(num/4.0f)));
    scrollView->setInnerContainerSize(sSize);
   
    //移除所有对象
    scrollView->removeAllChildren();
    //清除道具容器
    this->propItems.clear();
    //根据服务器给的数据添加道具列表
 
    for (int i = 0;i<this->itemList.size();i++)
    {
        if (i>19 && this->currentCol==0) {
            break;
        }
        this->setItem(this->itemList.at(i),i);
    }
    if (this->isShow == false) {
        MoveTo* move = MoveTo::create(0.2, Vec2(-(this->leftBg->getContentSize().width/2)-Director::getInstance()->getWinSize().width/2,this->leftBg->getPositionY()));
        this->leftBg->runAction(move);
    }
}
//设置单个item显示信息
void Bag::setItem(PItem* item,int index)
{

    ImageView* newItem = dynamic_cast<ImageView*>(this->propItem->clone());
    newItem->setEnabled(true);
    scrollView->addChild(newItem);
    newItem->setTouchEnabled(true);
    newItem->addTouchEventListener(CC_CALLBACK_2(Bag::itemTouchEvent,this));
    Text* propNum = static_cast<Text*>(newItem->getChildByName("propNum"));//数量
    ImageView* icon  = static_cast<ImageView*>(newItem->getChildByName("icon"));
    
    //将道具单项存储起来
    this->propItems.pushBack(newItem);
    this->setItemPosition(newItem,index);
    
    //数值转化为字符串
    propNum->setString(Value(item->itemnum()).asString());
    XItem* xItem = XItem::record(Value(item->itemid()));
    icon->loadTexture("item_"+Value(xItem->getIcon()).asString()+".png");
//    newItem->loadTexture("frame_"+Value(Manager::getInstance()->Qualitys[xItem->getRate()].color).asString()+".png");
    newItem->loadTexture("frame_"+Value(xItem->getRate()+1).asString()+".png");
}
//设置单个位置
void Bag::setItemPosition(ImageView* newItem,int index)
{
    //此处设置tag，用来对应总数据里的序列数
    newItem->setTag(index);
    float x=(sSize.width/4-itemSize.width)/4.0+sSize.width/4*(index%4)+50;
    float y=sSize.height-(itemSize.height+this->space)*(index/4)-itemSize.height/2;
    newItem->setPosition(Vec2(x,y));
}

//被选中的道具的具体显示信息
void Bag::setProperty(PItem* itemData,bool isHaveData)
{
    //当前道具得数量
    Text* currentPropNum = static_cast<Text*>(this->leftBg->getChildByName("currentPropNum"));
    //属性描述
    Text* propertyTxt1 = static_cast<Text*>(this->qualityPanel->getChildByName("propertyTxt1"));
    Text* propertyTxt2 = static_cast<Text*>(this->qualityPanel->getChildByName("propertyTxt2"));
    Text* propertyTxt3 = static_cast<Text*>(this->qualityPanel->getChildByName("propertyTxt3"));
    Text* propertyTxt4 = static_cast<Text*>(this->qualityPanel->getChildByName("propertyTxt4"));
    Text* propertyTxt5 = static_cast<Text*>(this->qualityPanel->getChildByName("propertyTxt5"));
    //出售单价
    Text* itemPriceTxt = static_cast<Text*>(this->leftBg->getChildByName("itemPriceTxt"));
    Text* desTxt =static_cast<Text*>(this->qualityPanel->getChildByName("desTxt"));
    Text* nameTxt = static_cast<Text*>(this->leftBg->getChildByName("nameTxt"));
    ImageView* icon = static_cast<ImageView*>(this->leftBg->getChildByName("icon"));
    ImageView* frame = static_cast<ImageView*>(this->leftBg->getChildByName("frame"));
    Label* nameTxt1 = static_cast<Label*>(nameTxt->getVirtualRenderer());
    nameTxt1->enableOutline(Color4B::BLACK,1);
    
    currentPropNum->setString("");
    itemPriceTxt->setString("");
    propertyTxt1->setString("");
    propertyTxt2->setString("");
    propertyTxt3->setString("");
    propertyTxt4->setString("");
    propertyTxt5->setString("");
    desTxt->setString("");
    nameTxt->setString("");
    icon->setVisible(false);
    
    currentPropNum->setString(Value(itemData->itemnum()).asString());
    Label* currentPropNum1 = static_cast<Label*>(currentPropNum->getVirtualRenderer());
    currentPropNum1->enableOutline(Color4B::BLACK,1);
    
    XItem*xItem = XItem::record(Value(itemData->itemid()));
    itemPriceTxt->setString(Value(xItem->getPrice()).asString());
    Label* itemPriceTxt1 = static_cast<Label*>(itemPriceTxt->getVirtualRenderer());
    itemPriceTxt1->enableOutline(Color4B::BLACK,1);
    
    desTxt->setString(Value(xItem->getDes()).asString());
    Label* desTxt1 = static_cast<Label*>(desTxt->getVirtualRenderer());
    desTxt1->enableOutline(Color4B::BLACK,1);
    
    nameTxt->setString(Value(xItem->getName()).asString());
    nameTxt->setColor(this->colorsLabel[xItem->getRate()]);
    icon->setVisible(true);
    icon->loadTexture("item_"+Value(xItem->getIcon()).asString()+".png");
//    frame->loadTexture("frame_"+Value(Manager::getInstance()->Qualitys[xItem->getRate()].color).asString()+".png");
    frame->loadTexture("frame_"+Value(xItem->getRate()+1).asString()+".png");
    //属性的显示
    vector<string> propertyData;
    if (xItem->getCrh()!= 0) {
        propertyData.push_back("暴击:"+Value(xItem->getCrh()).asString());
    }
    if (xItem->getMiss()!= 0) {
        propertyData.push_back("闪避:"+Value(xItem->getMiss()).asString());
    }
    if (xItem->getDef() != 0) {
        propertyData.push_back("物防:"+Value(xItem->getDef()).asString());
    }
    if (xItem->getMDef() != 0) {
        propertyData.push_back("法防:"+Value(xItem->getMDef()).asString());
    }
    if (xItem->getAtk() != 0) {
        propertyData.push_back("攻击:"+Value(xItem->getAtk()).asString());
    }
    if (xItem->getHp() != 0) {
        propertyData.push_back("生命:"+Value(xItem->getHp()).asString());
    }
    if (xItem->getHeal() != 0) {
        propertyData.push_back("生命恢复速度:"+Value(xItem->getHeal()).asString());
    }
    //为每个属性赋值显示
    int propertyLen = fmin(5, propertyData.size());
    for (int i = 0; i<propertyLen; i++) {
        Text* propertyTxt = static_cast<Text*>(this->qualityPanel->getChildByName("propertyTxt"+Value(i+1).asString()));
        propertyTxt->setString(propertyData.at(i));
        Label* propertyTxt1 = static_cast<Label*>(propertyTxt->getVirtualRenderer());
        propertyTxt1->enableOutline(Color4B::BLACK,1);
    }
    //此处应该设置道具图片以及道具品质颜色框的显示
}

void Bag::setRightPosition()
{
    if (this->isShow) {
        float x = this->rightBg->getPositionX()-(this->leftBg->getContentSize().width/2+ rightBg->getContentSize().width/2)+20;
        float y =this->leftBg->getPositionY();
        this->leftBg->runAction(MoveTo::create(0.2, Vec2(x,y)));
    }else{
        MoveTo* move = MoveTo::create(0.2, Vec2(-(this->leftBg->getContentSize().width/2)-Director::getInstance()->getWinSize().width/2,this->leftBg->getPositionY()));
        this->leftBg->runAction(move);
    }
}

void Bag::touchEvent(cocos2d::Ref *pSender, TouchEventType type)
{
    Button* btn=static_cast<Button*>(pSender);
    if(type!=TouchEventType::ENDED){
        return;
    }
    switch (btn->getTag()) {
        case 11221://tab1全部
        {
            //设置按钮选中状态
            tabBar->setIndex(0);
            this->currentType=-1;
            break;
        }
        case 11225://tab2装备
        {
            tabBar->setIndex(1);
            this->currentType = 1;
            break;
        }
        case 11224://tab3卷轴
        {
            tabBar->setIndex(2);
            this->currentType = 2;
            break;
        }
        case 11223://tab4召唤石
        {
            tabBar->setIndex(3);
            this->currentType = 3;
            break;
        }
        case 11222://tab5消耗品
        {
            tabBar->setIndex(4);
            this->currentType = 0;
            break;
        }
        case 11226://返回按钮
        {
            this->clear(true);
            return;
            break;
        }
        default:
            break;
    }
    scrollView->getInnerContainer()->setPositionY(-this->sSize.height+scrollView->getContentSize().height);
    this->currentCol=0;
    getTypeInfo(this->currentType);
}

void Bag::itemTouchEvent(Ref *pSender, TouchEventType type)
{
    Button* btn=static_cast<Button*>(pSender);
    if(type!=TouchEventType::ENDED){
        return;
    }
    
    Widget* item=static_cast<Widget*>(pSender);
    PItem* itemData = this->itemList.at(item->getTag());
    //记录当前被点击的道具组的tag
    this->currentTag = item->getTag();
    //记录当前被点击的道具的id
    this->currentId = itemData->itemid();
    
    this->bgChange(true);
    this->setProperty(itemData,true);
    this->sellBtn->setTitleText("出售");
    this->lookBtn->setTitleText("查看");
    this->lookBtn->setTouchEnabled(false);
    this->lookBtn->setColor(Color3B::GRAY);
    if (this->isShow == false) {
        this->isShow = true;
        this->leftBg->setVisible(this->isShow);
        this->setRightPosition();
    }
}

void Bag::bgChange(bool isQualityShow)
{
    this->isQuality = isQualityShow;
    this->qualityPanel->setVisible(isQualityShow);
    this->sellPanel->setVisible(!isQualityShow);
}

void Bag::touchButtonEvent(Ref *pSender, TouchEventType type)
{
    Button* btn=static_cast<Button*>(pSender);
    if (!btn) {
        return;
    }
    if (type==TouchEventType::ENDED) {
        PItem* itemInfo = this->itemList.at(this->currentTag);
        XItem*xItem = XItem::record(Value(itemInfo->itemid()));
        switch (btn->getTag()) {
            case 11214://出售按钮／确认出售
            {
                if (this->isQuality) {//出售按钮
                    this->bgChange(false);
                    this->selectNumber= 1;
                    this->setData(xItem);
                    this->sellBtn->setTitleText("确认");
                    this->lookBtn->setTitleText("返回");
                    this->lookBtn->setTouchEnabled(true);
                    this->lookBtn->setColor(Color3B::WHITE);
                }else{//确认出售
                    this->sendInfo(this->selectNumber);
                }
            }
                break;
            case 11215://查看按钮／返回按钮
                if (!this->isQuality) {//返回
                    this->bgChange(true);
                    this->selectNumber= 1;
                    this->sellBtn->setTitleText("出售");
                    this->lookBtn->setTitleText("查看");
                    this->lookBtn->setTouchEnabled(false);
                    this->lookBtn->setColor(Color3B::GRAY);
                }else{//查看
                    
                }
                break;
            case 25322://减少
                if(this->selectNumber == 1){
                    return;
                }
                -- this->selectNumber;
                this->setData(xItem);
                break;
            case 25323://增加
                if(this->selectNumber == itemInfo->itemnum()){
                    return;
                }
                ++ this->selectNumber;
                this->setData(xItem);
                break;
            case 25321://全部
                this->selectNumber = itemInfo->itemnum();
                this->setData(xItem);
                break;
            default:
                break;
        }
    }
}

void Bag::setData(XItem*xItem)
{
    PItem* itemInfo = this->itemList.at(this->currentTag);
    int gainNum = this->selectNumber * (xItem->getPrice());
    Text*selectNum=static_cast<Text*>(this->sellPanel->getChildByName("selectNum"));
    selectNum->setString(Value(this->selectNumber).asString() +"/"+Value(itemInfo->itemnum()).asString());
    Label* selectNum1 = static_cast<Label*>(selectNum->getVirtualRenderer());
    selectNum1->enableOutline(Color4B::BLACK,1);
    
    Text*gainCoinTxt=static_cast<Text*>(this->sellPanel->getChildByName("gainCoinTxt"));
    gainCoinTxt->setString(Value(gainNum).asString());
    Label* gainCoinTxt1 = static_cast<Label*>(gainCoinTxt->getVirtualRenderer());
    gainCoinTxt1->enableOutline(Color4B::BLACK,1);
}

void Bag::sendInfo(int selectNumber)
{
    PItem* itemInfo = this->itemList.at(this->currentTag);
    this->isNotAllSell = selectNumber == itemInfo->itemnum()?false:true;
    
    PSellGroup pSellGroup;
    pSellGroup.add_itemid(itemInfo->itemid());
    pSellGroup.add_itemsellnum(selectNumber);
    Manager::getInstance()->socket->send(C_SELLPROP, &pSellGroup);
}

void Bag::initNetEvent(){
    auto listener = EventListenerCustom::create(NET_MESSAGE, [=](EventCustom* event){
        NetMsg* msg = static_cast<NetMsg*>(event->getUserData());
        switch (msg->msgId)
        {
            case C_COMMONMSG://通用返回消息
            {
                //0：成功 1:物品不足
                PCommonResp commonResp;
                commonResp.ParseFromArray(msg->bytes,msg->len);
                //卖出商品成功(如果出售的数量是总数量，那么isShow＝false)
                if (commonResp.status() == 0) {
                    this->sellBtn->setTitleText("出售");
                    this->lookBtn->setTitleText("查看");
                    this->lookBtn->setTouchEnabled(false);
                    this->lookBtn->setColor(Color3B::GRAY);
                    this->selectNumber = 1;
                    this->bgChange(true);
                    if (this->isShow && this->isNotAllSell ==false) {
                        this->isShow = this->isNotAllSell;
                        this->setRightPosition();
                    }
                }//物品不足
                else if(commonResp.status() == 1){
                   Manager::getInstance()->showMsg("物品不足!"); 
                }
            }
                break;
            case C_UPITEM://更新道具
            {
                PUpItem item;
                item.ParseFromArray(msg->bytes, msg->len);
                google::protobuf::RepeatedPtrField< ::PItemChangeLog >::iterator it=item.mutable_itemloglist()->begin();
                for (; it!=item.mutable_itemloglist()->end(); ++it)
                {
                    if(it->npcid()==0)
                    {
                        bool isFound=false;
                        for (int i=0;i<this->itemList.size();i++)
                        {
                            PItem* item1 = this->itemList.at(this->currentTag);
                            if (this->currentId==it->itemid() && it->posid() == item1->posid()) {
                                if (it->itemfinalnum()==0)//为0要从背包删除此物品
                                {
                                    this->propItems.at(this->currentTag)->removeFromParent();
                                    this->itemList.erase(this->itemList.begin()+this->currentTag);
                                    this->propItems.erase(this->propItems.begin()+this->currentTag);//删除第i+1个元素
                                    for (int j=this->currentTag; j<this->propItems.size(); j++) {
                                        this->setItemPosition(static_cast<ImageView*>(this->propItems.at(j)),j);
                                    }
                                }
                                else if(it->itemfinalnum()!=0)//不为0则更新数量
                                {
                                    static_cast<PItem*>(this->itemList.at(this->currentTag))->set_itemnum(it->itemfinalnum());
                                    static_cast<Text*>(this->propItems.at(this->currentTag)->getChildByName("propNum"))->setString(Value(item1->itemnum()).asString());
                                    static_cast<Text*>(this->leftBg->getChildByName("currentPropNum"))->setString(Value(this->itemList.at(this->currentTag)->itemnum()).asString());
                                }
                                isFound=true;
                                break;
                            }
                        }
                        if (!isFound) {//不存在添加
                            PItem* item=new PItem();
                            item->set_itemnum(it->itemfinalnum());
                            item->set_itemid(it->itemid());
                            item->set_npcid(it->npcid());
                            item->set_posid(it->posid());
                            this->setItem(item,this->itemList.size());
                        }
                    }
                }
                
                
            }
//                this->getTypeInfo(this->currentType);
                break;
//            case C_UPROLE:
//                this->setPlayerInfo();
//                break;
            default:
                break;
        }
    });
    Director::getInstance()->getEventDispatcher()->addEventListenerWithSceneGraphPriority(listener, this);
}

void Bag::scrollEvent(Ref* pSender, ui::ScrollView::EventType type)
{
    ScrollView* scrollView=static_cast<ui::ScrollView*>(pSender);
    
    
    if (type==ui::ScrollView::EventType::SCROLLING) {
        int subOffY=scrollView->getInnerContainer()->getPositionY()-this->currentOffY;
        this->currentOffY=scrollView->getInnerContainer()->getPositionY();
        if (!this->isScolling) {
            this->isScolling=true;
            return;
        }
        float offY=sSize.height+scrollView->getInnerContainer()->getPositionY();
        int col=ceil(offY/(this->itemSize.height+this->space))-1;
        if (this->currentCol<col) {
            this->currentCol=col+4;
            for (int j=col; j<=this->currentCol; j++) {
                for (int i=0; i<4; i++) {
                    int nowIndex=(j*4+i);
                    Node* item=scrollView->getChildByTag(nowIndex);
                    if(!item && nowIndex<this->itemList.size())
                    {
                        this->setItem(this->itemList.at(nowIndex), nowIndex);
                    }
                }
            }
            
        }
    }
    else if(type==ui::ScrollView::EventType::SCROLL_TO_TOP)
    {
    }
}

void Bag::onExit()
{
    this->propItem->release();
    this->tabBar->release();
    //停止执行自己定义的函数定时器
    BaseUI::onExit();
}
