//
//  BagSellProp.cpp
//  fancyHeart
//
//  Created by doteyplay on 14-8-11.
//
//

#include "BagSellProp.h"

BagSellProp* BagSellProp::create(BaseUI* delebag,PItem itemInfo)
{
    BagSellProp* bagSellProp=new BagSellProp();
    if (bagSellProp && bagSellProp->init("publish/bagSellProp/bagSellProp.ExportJson",itemInfo,delebag)) {
        bagSellProp->autorelease();
        return bagSellProp;
    }
    CC_SAFE_DELETE(bagSellProp);
    return nullptr;
}

bool BagSellProp::init(std::string fileName,PItem itemInfo,BaseUI* delebag)
{
    if(!BaseUI::init(fileName))
    {
        return false;
    }
	//如果需要对cocostudio 设计的ui进行调整
    selectNumber= 1;
    this->itemInfo = itemInfo;
    this->bagPanel = delebag;
    Widget* infoPanel=static_cast<Widget*>(layout->getChildByName("infoPanel"));
    Button* btnReduce =static_cast<ui::Button*>(infoPanel->getChildByName("btnReduce"));
    Button* btnAdd =static_cast<ui::Button*>(infoPanel->getChildByName("btnAdd"));
    Button* btnAll =static_cast<ui::Button*>(infoPanel->getChildByName("btnAll"));
    Button* sureSellBtn =static_cast<ui::Button*>(infoPanel->getChildByName("sureSellBtn"));
    
    btnReduce->addTouchEventListener(CC_CALLBACK_2(BagSellProp::touchEvent,this));
    btnAdd->addTouchEventListener(CC_CALLBACK_2(BagSellProp::touchEvent,this));
    btnAll->addTouchEventListener(CC_CALLBACK_2(BagSellProp::touchEvent,this));
    sureSellBtn->addTouchEventListener(CC_CALLBACK_2(BagSellProp::touchEvent,this));
    
    Text* nameLabel = static_cast<Text*>(infoPanel->getChildByName("nameLabel"));
    Text* propNum =static_cast<Text*>(infoPanel->getChildByName("propNum"));
    Text* itemPriceTxt =static_cast<Text*>(infoPanel->getChildByName("itemPriceTxt"));
    this->selectNum =static_cast<Text*>(infoPanel->getChildByName("selectNum"));
    this->gainCoinTxt =static_cast<Text*>(infoPanel->getChildByName("gainCoinTxt"));
    
    XItem*xItem = XItem::record(Value(itemInfo.itemid()));
    itemPriceTxt ->setString(Value(xItem->getPrice()).asString());
    int gainNum = this->selectNumber * (xItem->getPrice());
    this->gainCoinTxt->setString(Value(gainNum).asString());
    this->selectNum ->setString(Value(this->selectNumber).asString() +"/"+Value(itemInfo.itemnum()).asString());
    nameLabel->setString(Value(xItem->getNameID()).asString());

	return true;
}

void BagSellProp::onEnter()
{
    BaseUI::onEnter();
}

void BagSellProp::touchEvent(cocos2d::Ref *pSender, TouchEventType type)
{
    Button* btn=static_cast<Button*>(pSender);
    if (!btn) {
        return;
    }
    if(type==TouchEventType::ENDED)
    {
        XItem*xItem = XItem::record(Value(itemInfo.itemid()));
        switch (btn->getTag()) {
            case 12042://减少
            {
                if(this->selectNumber == 1){
                    return;
                }
                -- this->selectNumber;
                break;
            }
            case 12043://增加
            {
                if(this->selectNumber == this->itemInfo.itemnum()){
                    return;
                }
                ++ this->selectNumber;
                break;
            }
            case 12044://全部
            {
                this->selectNumber = this->itemInfo.itemnum();
                break;
            }
            case 12063://确认出售
            {
                this->clear(true);
                static_cast<Bag*>(this->bagPanel)->sendInfo(this->selectNumber);
                return;
                break;
            }
            default:
                break;
        }
        
        selectNum ->setString(Value(this->selectNumber).asString() +"/"+Value(itemInfo.itemnum()).asString());
        int gainNum = this->selectNumber * (xItem->getPrice());
        this->gainCoinTxt->setString(Value(gainNum).asString());
    }
}

void BagSellProp::onExit()
{
    BaseUI::onExit();
}