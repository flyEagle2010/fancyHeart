//
//  ___FILENAME___
//  ___PROJECTNAME___
//
//  Created by ___FULLUSERNAME___ on ___DATE___.
//___COPYRIGHT___
//

#include "___FILEBASENAME___.h"

bool ___FILEBASENAMEASIDENTIFIER___::init()
{
	if(!BaseUI::init("publish/___FILEBASENAMEASIDENTIFIER___.json"))
	{
		return false;
	}
	//如果需要对cocostudio 设计的ui进行调整

	return true;
}

void ___FILEBASENAMEASIDENTIFIER___::onEnter()
{

}

void ___FILEBASENAMEASIDENTIFIER___::show(BaseUI* preUI,int effectType)
{
    BaseUI::show(preUI,effectType);
}

void ___FILEBASENAMEASIDENTIFIER___::clear(bool isDel)
{
    BaseUI::clear(isDel);
}

void ___FILEBASENAMEASIDENTIFIER___::touchEvent(Ref *pSender, TouchEventType type)
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

void ___FILEBASENAMEASIDENTIFIER___::initNetEvent(){
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

void ___FILEBASENAMEASIDENTIFIER___::onExit()
{
    
}