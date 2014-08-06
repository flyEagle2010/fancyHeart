//
//  Package.cpp
//  fancyHeart
//
//  Created by doteyplay on 14-8-5.
//
//

#include "Package.h"

Package* Package::create()
{
    Package* package=new Package();
    if (package && package->init("publish/package/package.ExportJson")) {
        package->autorelease();
        return package;
    }
    CC_SAFE_DELETE(package);
    return nullptr;
}

bool Package::init(std::string fileName)
{
	if(!BaseUI::init(fileName))
    {
        return false;
    }
	//如果需要对cocostudio 设计的ui进行调整
    
    getTypeInfo(-1);//-1表示全部
    
    Widget* bgImg=static_cast<Widget*>(layout->getChildByName("img_bg"));
    std::vector<Button*> buttons;
    std::vector<std::string> btnName={"tab1","tab2","tab3","tab4","tab5","returnBtn"};
    for (std::string name : btnName)
    {
        auto btn=bgImg->getChildByName(name);
        btn->setTouchEnabled(true);
        btn->addTouchEventListener(CC_CALLBACK_2(Package::touchEvent,this));
        buttons.push_back(static_cast<Button*>(btn));
    }
    
    tabBar=TabBar::create(buttons);
    tabBar->retain();
	return true;
}

void Package::onEnter()
{
    BaseUI::onEnter();
}

void Package::getTypeInfo(int type)
{
    auto itemlist = Manager::getInstance()->getRoleData()->itemlist();
    Widget* bgImg=static_cast<Widget*>(layout->getChildByName("img_bg"));
    ScrollView*scrollView=static_cast<ui::ScrollView*>(bgImg->getChildByName("scrollView"));
    this->propItem=scrollView->getChildByName("propItem");
    this->currentPropNum = static_cast<Text*>(bgImg->getChildByName("currentPropNum"));//当前道具得数量
    this->propertyTxt1 = static_cast<Text*>(bgImg->getChildByName("propertyTxt1"));//属性描述
    this->propertyTxt2 = static_cast<Text*>(bgImg->getChildByName("propertyTxt2"));
    this->propertyTxt3 = static_cast<Text*>(bgImg->getChildByName("propertyTxt3"));
    this->propertyTxt4 = static_cast<Text*>(bgImg->getChildByName("propertyTxt4"));
    this->propertyTxt5 = static_cast<Text*>(bgImg->getChildByName("propertyTxt5"));
    this->itemPriceTxt = static_cast<Text*>(bgImg->getChildByName("itemPriceTxt"));//出售单价
    this->propertyTxt1->setString("");
    this->propertyTxt2->setString("");
    this->propertyTxt3->setString("");
    this->propertyTxt4->setString("");
    this->propertyTxt5->setString("");
    
    int num=itemlist.size();
    Size size=Size(scrollView->getInnerContainerSize().width,(propItem->getContentSize().height+50)*(num/4+1) - 50);
    scrollView->setInnerContainerSize(size);

    //清除道具容器
    this->propItems.clear();
    //清除道具数据容器
//    this->propItemData.clear();
    this->propItem->removeFromParent();//移除舞台上的单个item

    //根据服务器给的数据添加道具列表
    for (int i = 0;i<itemlist.size();i++)
//    for (int i = 0;i<50;i++)
    {
        auto item = Manager::getInstance()->getRoleData()->itemlist(i);
        XItem*xItem = XItem::record(Value(item.itemid()));
        if (type != -1) {
            //类型一样，则显示出来此道具
            if (xItem->getType() == type) {
                this->setItem(item);
            }
        }else if(type == -1){
            this->setItem(item);
        }
        
    }
    
}
//设置单个item
void Package::setItem(PItem item)
{
    //当前模版得数量
    int len = int(this->propItems.size());
    Widget* bgImg=static_cast<Widget*>(layout->getChildByName("img_bg"));
    ScrollView*scrollView=static_cast<ui::ScrollView*>(bgImg->getChildByName("scrollView"));
    
    Widget* newItem = dynamic_cast<Widget*>(this->propItem->clone());
    Text* propNum = static_cast<Text*>(newItem->getChildByName("propNum"));//数量
    newItem->setTouchEnabled(true);
    newItem->addTouchEventListener(CC_CALLBACK_2(Package::itemTouchEvent,this));
    //将道具单项存储起来
    this->propItems.pushBack(newItem);
//    this->propItemData.pushBack(item);
    newItem->setTag(len);
    
    scrollView->addChild(newItem);
    
    Size sSize=scrollView->getInnerContainerSize();
    Size itemSize=newItem->getContentSize();
    
    float x=(sSize.width/4-itemSize.width)/4.0+sSize.width/4*(len%4)+50;
    float y=sSize.height-(itemSize.height + 10)*(len/4) - 60;
    newItem->setPosition(Vec2(x,y));
    newItem->addTouchEventListener(CC_CALLBACK_2(Package::touchEvent,this));
    
    //数值转化为字符串
    propNum->setString(Value(item.itemnum()).asString());
    
    //默认左边信息栏中显示第一个道具
    if (len == 0) {
        this->currentPropNum->setString("拥有"+Value(item.itemnum()).asString()+"件");
        XItem*xItem = XItem::record(Value(item.itemid()));
        this->itemPriceTxt->setString(Value(xItem->getPrice()).asString());
        //属性的显示
//        this->propertyTxt1->setString(xItem->get);
//        this->propertyTxt2->setString();
//        this->propertyTxt3->setString();
//        this->propertyTxt4->setString();
    }
}

void Package::touchEvent(cocos2d::Ref *pSender, TouchEventType type)
{
    Button* btn=static_cast<Button*>(pSender);
    if(type!=TouchEventType::ENDED){
        return;
    }
    if (btn->getTag() == 11221) {//tab1全部
        //设置按钮选中状态
        tabBar->setIndex(0);
    }else if (btn->getTag() == 11225){//tab2装备
        tabBar->setIndex(1);
        getTypeInfo(1);
    }else if (btn->getTag() == 11224){//tab3卷轴
        tabBar->setIndex(2);
        getTypeInfo(2);
    }else if (btn->getTag() == 11223){//tab4召唤石
        tabBar->setIndex(3);
    }else if (btn->getTag() == 11222){//tab5消耗品
        tabBar->setIndex(4);
        getTypeInfo(0);
    }else if (btn->getTag() == 11226){//返回按钮
        this->clear(true);
        return;
    }
    
}

void Package::itemTouchEvent(cocos2d::Ref *pSender, TouchEventType type)
{
    Widget* item=static_cast<Widget*>(pSender);
//    PItem itemData = this->propItemData.at(item->getTag());
    
//    this->currentPropNum->setString("拥有"+Value(itemData.itemnum()).asString()+"件");
//    XItem*xItem = XItem::record(Value(itemData.itemid()));
//    this->itemPriceTxt->setString(Value(xItem->getPrice()).asString());
    //属性的显示
    //        this->propertyTxt1->setString(xItem->get);
    //        this->propertyTxt2->setString();
    //        this->propertyTxt3->setString();
    //        this->propertyTxt4->setString();
}

void Package::initNetEvent(){
    auto listener = EventListenerCustom::create(NET_MESSAGE, [=](EventCustom* event){
        NetMsg* msg = static_cast<NetMsg*>(event->getUserData());
        switch (msg->msgId)
        {
            case C_LOGIN:
            {
                 //需要存储（例子）
                /*
                PackageLoginResp *pb=new PackageLoginResp();
                pb->ParseFromArray(msg->bytes, msg->len);
                Manager::getInstance()->setMsg(C_LOGIN, pb);
                Manager::getInstance()->switchScence(HomeScene::createScene());
                pb=nullptr;
                */
                /*
                 //不需要存储（例子）
                 PackageLoginResp pb;
                 pb.ParseFromArray(msg->bytes, msg->len);
                 Manager::getInstance()->switchScence(HomeScene::createScene());
                 */
            }
                break;
            default:
                break;
        }
    });
    this->_eventDispatcher->addEventListenerWithFixedPriority(listener,1);
}
