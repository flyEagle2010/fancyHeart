//
//  chat.cpp
//  fancyHeart
//
//  Created by zhai on 14-7-9.
//
//

#include "chat.h"
Chat* Chat::create(){
    Chat* chat=new Chat();
    if(chat && chat->init()){
        chat->setPosition(Vec2(-600, 0));
        chat->setVisible(false);
        chat->autorelease();
        return  chat;
    }
    CC_SAFE_DELETE(chat);
    return nullptr;
}

bool Chat::init()
{
	if(!BaseUI::init("publish/chat/chat.ExportJson"))
	{
		return false;
	}
    auto img=static_cast<Widget*>(layout->getChildByName("img"));
    auto button=static_cast<Button*>(img->getChildByName("btn_send"));
    button->addTouchEventListener(CC_CALLBACK_2(Chat::touchButtonEvent, this));
    button=static_cast<Button*>(img->getChildByName("btn_close"));
    button->addTouchEventListener(CC_CALLBACK_2(Chat::touchButtonEvent, this));
    inputHolder=static_cast<Widget*>(img->getChildByName("inputHolder"));
    //TextField* oldInput=static_cast<TextField*>(inputHolder->getChildByName("txt_input"));
    txtInput=static_cast<TextField*>(inputHolder->getChildByName("txt_input"));
    //txtInput=NewTextField::create();
    //txtInput->setContentSize(oldInput->getContentSize());
    //txtInput->setPosition(oldInput->getPosition());
    //txtInput->setAnchorPoint(oldInput->getAnchorPoint());
    //txtInput->setPlaceHolder(oldInput->getPlaceHolder());
    //txtInput->setFontSize(oldInput->getFontSize());
    //inputHolder->addChild(txtInput);
    //oldInput->removeFromParent();
    
    txtInput->addEventListener(CC_CALLBACK_2(Chat::textFieldEvent, this));
    txtInput->setTouchSize(Size(inputHolder->getContentSize().width, inputHolder->getContentSize().height));
    txtInput->setTouchAreaEnabled(true);
    
    list=static_cast<ListView*>(img->getChildByName("list_chat"));
    model=static_cast<Widget*>(img->getChildByName("chat_item"));
    
    Text* title=static_cast<Text*>(model->getChildByName("txt_title"));
    Text* content=static_cast<Text*>(model->getChildByName("txt_content"));
    this->titleY=title->getPositionY();
    this->contentY=content->getPositionY();
    model->retain();
    model->removeFromParent();
    list->setItemModel(model);
    list->removeAllChildren();
    //txtInput->setText("pet[][100x1]");
//    for (int i=0; i<1; i++) {
//        this->list->pushBackDefaultItem();
//        Widget* item=this->list->getItem(i);
//        Text* text=static_cast<Text*>(item->getChildByName("txt_content"));
//        text->setString(Value(i).asString());
//    }
	return true;
}

void Chat::addChatMsg(const char* chatMsg)
{
    this->list->pushBackDefaultItem();
    this->chats.push_back(chatMsg);
    this->initContent();
    this->list->scrollToTop(0.3, true);
}

void Chat::initContent()
{
    int size=int(this->list->getItems().size());
    for(int i=0;i<size;i++){
        Widget* item=this->list->getItem(i);
        Text* title=static_cast<Text*>(item->getChildByName("txt_title"));
        Text* content=static_cast<Text*>(item->getChildByName("txt_content"));
        //设置换行宽度
        static_cast<Label*>(content->getVirtualRenderer())->setMaxLineWidth(item->getContentSize().width-40);
        title->setString(Manager::getInstance()->getRoleData()->role().rolename()+":");
        content->setString(" ");
        string pre=" ";
        while (content->getContentSize().width<=title->getContentSize().width) {
            pre+=" ";
            content->setString(pre);
        }
        content->setString(pre+this->chats.at(size-1-i));
        
        //背景自适应
        item->setContentSize(Size(item->getContentSize().width, content->getContentSize().height+20));
        //自适应后得动态调整标题和内容的y坐标
        title->setPosition(Vec2(title->getPositionX(), this->titleY+item->getContentSize().height));
        content->setPosition(Vec2(content->getPositionX(), this->titleY+item->getContentSize().height));
    }
}

void Chat::onEnter()
{
    BaseUI::onEnter();
}

void Chat::onExit()
{
    tabBar->release();
    BaseUI::onExit();
}
void Chat::show()
{
    this->setVisible(true);
    this->runAction(MoveTo::create(.5, Vec2(0, 0)));
}

void Chat::hide()
{
    
    Sequence* sequence=Sequence::create(MoveTo::create(.5, Vec2(-600, 0)),CallFunc::create(CC_CALLBACK_0(Chat::disVisible, this)), NULL);
    this->runAction(sequence);
}

void Chat::disVisible()
{
    this->setVisible(false);
}

void Chat::textFieldEvent(Ref *pSender, TextField::EventType type)
{
    switch (type)
    {
        case TextField::EventType::DELETE_BACKWARD:
        case TextField::EventType::INSERT_TEXT:
        {
            TextField* tf=static_cast<TextField*>(pSender);
            Size s=tf->getContentSize();
            if (tf->getContentSize().width<=inputHolder->getContentSize().width-40) {
                return;
            }
            tf->setPosition(Vec2(inputHolder->getContentSize().width-tf->getContentSize().width-40, tf->getPosition().y));
            break;
        }
        case TextField::EventType::ATTACH_WITH_IME:
        {
            log("aaa");
            break;
        }
        case TextField::EventType::DETACH_WITH_IME:
        {
            log("bbb");
            break;
        }
            
           
            
        default:
            break;
    }
}

void Chat::touchButtonEvent(Ref *pSender, TouchEventType type)
{
    auto button=static_cast<Button*>(pSender);
    if (!button) {
        return;
    }
    switch(type)
    {
        case TouchEventType::BEGAN:
            
            break;
        case TouchEventType::MOVED:
            break;
        case TouchEventType::ENDED:
            switch (button->getTag()) {
                case 10071://聊天按钮
                {
                    PChatReq chat;
                    //chat.set_msg("money[1844674407371342][100000]");
//                    chat.set_msg("exp[1844674407371343][100]");
                    //chat.set_msg("goods[11414016167939][3102x100]");
                    //chat.set_msg("pet[1844674407371342][105x2]");
                    
//                    chat.set_msg("goods[-1][3102X100]");
                    //chat.set_msg("pet[][101x1]");
                    //chat.set_msg(txtInput->getStringValue());
                    //Manager::getInstance()->socket->send(C_CHAT, &chat);
                    this->addChatMsg(txtInput->getStringValue().c_str());
                    txtInput->setText("");
                    txtInput->setPosition(Vec2(5, txtInput->getPosition().y));
                    break;
                }
                case 10190://关闭按钮
                {
                    this->hide();
                    break;
                }
                case 10267://综合
                {
                    tabBar->setIndex(0);
                    break;
                }
                case 10269://世界
                {
                    tabBar->setIndex(1);
                 break;
                }
                case 10270://帮会
                {
                    tabBar->setIndex(2);
                    break;
                }
                case 10271://私聊
                {
                    tabBar->setIndex(3);
                    break;
                }
                default:
                    break;
            }
            break;
        default:
            break;
    }

}

