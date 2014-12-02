//
//  Mask.cpp
//  fancyHeart
//
//  Created by zhai on 14-6-25.
//
//

#include "Mask.h"
static Mask* instance;
Mask* Mask::getInstance()
{
    if (instance==nullptr) {
        instance=new Mask();
        Size size=Director::getInstance()->getWinSize();
        instance->init(size);
    }
    return instance;
}

Mask* Mask::create(Size size)
{
    Mask* mask=new Mask();
    if (mask && mask->init(size)) {
        mask->autorelease();
        return mask;
    }
    CC_SAFE_DELETE(mask);
    return nullptr;
}

bool Mask::init(Size size)
{
    if(!Layout::init())
    {
        return false;
    }
    
    this->setSize(size);
    this->setBackGroundColorType(BackGroundColorType::SOLID);
    this->setBackGroundColor(Color3B::BLACK);
    this->setBackGroundColorOpacity(240);
    this->setVisible(false);
    return true;
}

void Mask::show()
{
    this->setVisible(true);
}

void Mask::hide()
{
    this->setVisible(false);
}