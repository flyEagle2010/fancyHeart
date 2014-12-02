//
//  GateMap.cpp
//  fancyHeart
//
//  Created by zhai on 14-6-18.
//
//

#include "GateMap.h"

GateMap* GateMap::create(BaseUI* delegate,int  gateId)
{
    GateMap* gateMap=new GateMap();
    if (gateMap && gateMap->init("publish/gateMap/gateMap.ExportJson",gateId)) {
        gateMap->autorelease();
        return gateMap;
    }
    CC_SAFE_DELETE(gateMap);
    return nullptr;
}

bool GateMap::init(std::string fileName,int gateId)
{
	if(!BaseUI::init(fileName))
	{
		return false;
	}
    Manager::getInstance()->gateId=0;
    this->resetUI(gateId);
	return true;
}

void GateMap::onEnter()
{
    BaseUI::onEnter();
}

void GateMap::resetUI(int gateId)//init map
{
    auto size=Director::getInstance()->getOpenGLView()->getDesignResolutionSize();
    gateItem=Manager::getInstance()->getGateItem(gateId);
    Widget* bgLayer=(Widget*)layout->getChildByName("bgLayer");
    Button* button=static_cast<Button*>(layout->getChildByName("btn_return"));
    button->setLocalZOrder(2);
    button->addTouchEventListener(CC_CALLBACK_2(GateMap::touchButtonEvent, this));
    
    Button* btn_send=static_cast<Button*>(bgLayer->getChildByName("btn_send"));
    btn_send->setLocalZOrder(2);
    btn_send->addTouchEventListener(CC_CALLBACK_2(GateMap::touchButtonEvent, this));
    
    
    
    XGate* xGate=XGate::record(Value(gateId));
    string path="publish/map_100/map_100.ExportJson";
    //地图
    Layout* map=static_cast<Layout*>(cocostudio::GUIReader::getInstance()->widgetFromJsonFile(path.c_str()));
    Widget* lineBody=static_cast<Widget*>(map->getChildByName("lineBody"));
    for (auto child:map->getChildren()) {
        string name=child->getName();
        if (name.find("img_")!=-1) {
            child->setVisible(false);
            int tag=gateId*100+child->getTag()%100;
            Node* dot=map->getChildByName("dot_"+Value(child->getTag()).asString());
            if (dot) {
                dot->setVisible(false);
            }
            Widget* star=static_cast<Widget*>(child->getChildByName("star_bg"));
            if (star) {
                star->setVisible(false);
            }
            child->setTag(tag);
            static_cast<Widget*>(child)->setTouchEnabled(true);
            static_cast<Widget*>(child)->addTouchEventListener(CC_CALLBACK_2(GateMap::touchEvent, this));
            this->sprites.insert(child->getTag(), child);
        }
    }
    bgLayer->addChild(map);
//    //通过服务器传递的数据判断显示哪个
    for (int i=0; i<gateItem->items_size(); i++) {
        PNodeItem* nodeItem= gateItem->mutable_items(i);
        Node*  sprite=this->sprites.at(nodeItem->xid());
        if (sprite) {
            XNode* xn=XNode::record(Value(nodeItem->xid()));
            if (xn->getDisplay()==1) {//通关后不显示的点
                sprite->setVisible(nodeItem->star()==0);
            }else{//通关后还一直显示的点
                sprite->setVisible(true);
                Node* dot=map->getChildByName("dot_"+Value(sprite->getTag()).asString());
                if (dot) {
                    dot->setVisible(true);
                }
                Widget* star=static_cast<Widget*>(sprite->getChildByName("star_bg"));
                if (star) {
                    //星级不为0就显示
                    star->setVisible(nodeItem->star()!=0);
                    if (nodeItem->star()!=0) {
                        int firstPx=(star->getContentSize().width-nodeItem->star()*32)/2+16;
                        for (int i=1; i<=3; i++) {
                            Node* node=star->getChildByName("star_"+Value(i).asString());;
                            node->setPositionX(firstPx+(i-1)*32);
                            node->setVisible(i<=nodeItem->star());
                        }
                    }
                }
            }
            //显示最后一个的指引位置，小点跟大建筑显示位置不一样
            if(i==gateItem->items_size()-1){
                Vec2 p=sprite->getPosition();
                if (xn->getDisplay()==1) {
                    p=p+Vec2(0, sprite->getContentSize().height);
                }else{
                    Node* dot=map->getChildByName("dot_"+Value(sprite->getTag()).asString());
                    p=dot->getPosition()+Vec2(0, dot->getContentSize().height+15);
                }
                lineBody->setPosition(p);
            }
        }
    }

    bgLayer->setTouchEnabled(true);
    bgLayer->setEnabled(true);
    bgLayer->addTouchEventListener(CC_CALLBACK_2(GateMap::touchEvent, this));
}

void GateMap::touchButtonEvent(Ref *pSender, TouchEventType type)
{
    auto btn=static_cast<Button*>(pSender);
    if (!btn) {
        return;
    }
    if (type==TouchEventType::ENDED) {
        switch (btn->getTag()) {
            case 10705:
                this->clear(true);
                break;
                
            default:
                break;
        }
    }
}

//遍历所有显示对象，判断鼠标点击的位置是否在显示对象的区域内，来判断点击的哪个对象
Node* GateMap::getTouchSprite(cocos2d::Vec2 pos)
{
    for(Map<int, Node*>::iterator it = this->sprites.begin(); it != this->sprites.end(); it++) {
        auto sprite=it->second;
        //全局坐标转换局部坐标
        Vec2 nsp = sprite->convertToNodeSpace(pos);
        Rect bb;
        bb.size = sprite->getContentSize();
        if (bb.containsPoint(nsp) && sprite->isVisible())
        {
            return sprite;
        }
    }
    return nullptr;
}

void GateMap::touchEvent(Ref *pSender, TouchEventType type)
{
    Widget* widget=static_cast<Widget*>(pSender);
    if(type==TouchEventType::BEGAN)
    {
        widget->stopAllActions();
        widget->runAction(Sequence::create(ScaleTo::create(0.15,1.1),ScaleTo::create(0.15, 1),NULL) );
    }
    if(type==TouchEventType::ENDED)
    {
        if (widget) {
            XNode* xn=XNode::record(Value(widget->getTag()));
            if (xn->getType()==1) {//进战役
                GateSelect* gateSelect=GateSelect::create(this, gateItem->gateid(),widget->getTag());
                gateSelect->show(this);
            }else if(xn->getType()==0){//进关卡
                this->resetUI(xn->getGateID());
            }
        }
        
    }
}

void GateMap::onExit()
{
    this->sprites.clear();
    BaseUI::onExit();
}
