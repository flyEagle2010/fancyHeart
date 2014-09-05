//
//  Compose.cpp
//  fancyHeart
//
//  Created by doteyplay on 14-8-20.
//
//

#include "Compose.h"

Compose* Compose::create()
{
    Compose* compose=new Compose();
    if (compose && compose->init("publish/compose/compose.ExportJson")) {
        compose->autorelease();
        return compose;
    }
    CC_SAFE_DELETE(compose);
    return nullptr;
}

bool Compose::init(std::string fileName)
{
	if(!BaseUI::init(fileName))
    {
        return false;
    }
	//如果需要对cocostudio 设计的ui进行调整

	return true;
}

void Compose::onEnter()
{
    BaseUI::onEnter();
}

void Compose::touchEvent(Ref *pSender, TouchEventType type)
{
    switch (type)
    {
        case TouchEventType::BEGAN:
            break;
        case TouchEventType::MOVED:
            break;
        case TouchEventType::ENDED:
            break;
        case TouchEventType::CANCELED:
            break;
        default:
            break;
    }
}

void Compose::initNetEvent(){
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

void Compose::onExit()
{
    BaseUI::onExit();
}