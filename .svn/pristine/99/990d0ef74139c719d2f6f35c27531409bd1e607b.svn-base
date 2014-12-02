//
//  loading.cpp
//  fancyHeart
//
//  Created by zhai on 14-6-25.
//
//

#include "Loading.h"
static Loading* instance;
Loading* Loading::getInstance()
{
    if (instance==nullptr) {
        instance=new Loading();
        instance->init();
    }
    return instance;
}

bool Loading::init()
{
    if(!Layout::init())
    {
        return false;
    }
    Size size=Director::getInstance()->getWinSize();
    this->img=Sprite::create("round.png");
    this->img->setPosition(Vec2(size.width/2,size.height/2));
    this->addChild(img);
    this->setSize(size);
    this->setBackGroundColorType(BackGroundColorType::SOLID);
    this->setBackGroundColor(Color3B::BLACK);
    this->setBackGroundColorOpacity(160);
    this->setVisible(false);
    return true;
}

void Loading::show()
{
    
    //this->setVisible(true);
    //this->img->runAction(RepeatForever::create(RotateBy::create(2, 360)));
}

void Loading::hide()
{
    this->setVisible(false);
    this->img->stopAllActions();
}