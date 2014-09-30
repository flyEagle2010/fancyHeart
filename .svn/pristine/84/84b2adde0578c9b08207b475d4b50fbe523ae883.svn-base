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
    auto chatBg=static_cast<Widget*>(layout->getChildByName("chatBg"));
    // RichText
    richText = RichText::create();
    richText->ignoreContentAdaptWithSize(false);
    richText->setAnchorPoint(Vec2(0, 1));
    richText->setSize(Size(chatBg->getContentSize().width, chatBg->getContentSize().height));
    richText->setPosition(Vec2(0, chatBg->getContentSize().height));
    richText->setLocalZOrder(10);
    chatBg->addChild(richText);
    auto button=static_cast<Button*>(layout->getChildByName("btn_send"));
    button->addTouchEventListener(CC_CALLBACK_2(Chat::touchButtonEvent, this));
    button=static_cast<Button*>(layout->getChildByName("btn_close"));
    button->addTouchEventListener(CC_CALLBACK_2(Chat::touchButtonEvent, this));
    txtInput=static_cast<TextField*>(layout->getChildByName("txt_input"));
    std::vector<std::string> chanel={"all","world","team","private"};
    std::vector<Button*> buttons;
    for (int i=0; i<chanel.size(); i++) {
        Button* button=static_cast<Button*>(layout->getChildByName("btn_"+chanel.at(i)));
        button->addTouchEventListener(CC_CALLBACK_2(Chat::touchButtonEvent,this));
        buttons.push_back(static_cast<Button*>(button));
    }
    tabBar=TabBar::create(buttons);
    tabBar->retain();
	return true;
}
void Chat::addChatMsg(int channel, string roleName, const char* chatMsg)
{
    RichElementImage* reImg=RichElementImage::create(channel, Color3B::BLACK, 255, "CloseNormal.png");
    RichElementText* re1 = RichElementText::create(channel, Color3B::YELLOW, 255, chatMsg, "Helvetica", 24);
    
    richText->insertElement(reImg,0);
    richText->insertElement(re1,0);
//    richText->insertNewLine();
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
    this->runAction(MoveTo::create(1, Vec2(0, 0)));
}

void Chat::hide()
{
    
    Sequence* sequence=Sequence::create(MoveTo::create(1, Vec2(-600, 0)),CallFunc::create(CC_CALLBACK_0(Chat::disVisible, this)), NULL);
    this->runAction(sequence);
}

void Chat::disVisible()
{
    this->setVisible(false);
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
                    chat.set_msg("exp[1844674407371343][100]");
                    //chat.set_msg("goods[1844674407371342][1008x1,1009x1]");
                    //chat.set_msg("pet[1844674407371342][105x2]");
                    
                    Manager::getInstance()->socket->send(C_CHAT, &chat);
                    
                    //this->addChatMsg(1, "", txtInput->getStringValue().c_str());
                    //txtInput->setText("");
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

