//
//  CardItem.cpp
//  fancyHeart
//
//  Created by doteyplay on 14-8-20.
//
//

#include "CardItem.h"
CardItem* CardItem::create()
{
    CardItem* cardItem=new CardItem();
    if (cardItem && cardItem->init("publish/cardItem/cardItem.ExportJson")) {
        cardItem->autorelease();
        return cardItem;
    }
    CC_SAFE_DELETE(cardItem);
    return nullptr;
}

bool CardItem::init(std::string fileName)
{
	if(!BaseUI::init(fileName))
    {
        return false;
    }
    item=static_cast<Widget*>(layout->getChildByName("item"));
	return true;
}

void CardItem::initUI(int npcId)
{
    PNpc* pNpc=Manager::getInstance()->getNpc(npcId);
    if (!pNpc) {
        return;
    }
    
    Widget* img_on=(Widget*)layout->getChildByName("img_on");
    for(int i=1;i<7;i++){
        ImageView* imageView=static_cast<ImageView*>(layout->getChildByName("star_"+Value(i).asString()));
        imageView->loadTexture(i<=pNpc->star()?"hero_tar level.png":"hero_tar level_1.png",TextureResType::PLIST);
    }
    static_cast<Text*>(layout->getChildByName("txt_lvl"))->setString(Value(pNpc->level()).asString());
    ImageView* imageView=static_cast<ImageView*>(layout->getChildByName("img_frame"));
    imageView->loadTexture("card_"+Value(Manager::getInstance()->Qualitys[pNpc->quality()].color).asString()+".png");
}

void CardItem::setTouchEnabled(bool enable)
{
    BaseUI::setTouchEnabled(enable);
}
/// contentSize getter
const Size& CardItem::getInnerSize() const
{
    return static_cast<ImageView*>(item->getChildByName("img_frame"))->getContentSize();
}

//Widget* Widget::clone()
//{
//    Widget* clonedWidget = createCloneInstance();
//    clonedWidget->copyProperties(this);
//    clonedWidget->copyClonedWidgetChildren(this);
//    return clonedWidget;
//}

void CardItem::onEnter()
{
    BaseUI::onEnter();
}

void CardItem::onExit()
{
    BaseUI::onExit();
}