//
//  Confirm.cpp
//  fancyHeart
//
//  Created by zhai on 14-6-18.
//
//

#include "Confirm.h"

Confirm* Confirm::create(BaseUI* delegate,std::string data)
{
    Confirm* confirm=new Confirm();
    if (confirm && confirm->init("confirm.json")) {
//        confirm->data=data;
        confirm->autorelease();
        return confirm;
    }
    CC_SAFE_DELETE(confirm);
    return nullptr;
}

bool Confirm::init(std::string fileName)
{
	if(!BaseUI::init(fileName))
	{
		return false;
	}
	//init ui
    

	return true;
}

void Confirm::show(BaseUI* preUI,int effectType)
{
    BaseUI::show(preUI, effectType);
}

void Confirm::clear(bool isDel)
{
    if(isDel){
        this->removeFromParent();
    }
}

void Confirm::touchEvent(Ref *pSender, TouchEventType type)
{
    switch (type)
    {
        case TouchEventType::BEGAN:
            break;
        case TouchEventType::MOVED:
            break;
        case TouchEventType::ENDED:
        {
            this->delegate->onDlgClose(data);
        }
            break;
        case TouchEventType::CANCELED:
            break;
        default:
            break;
    }
}

