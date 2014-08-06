//
//  TestUii.cpp
//  fancyHeart
//
//  Created by doteyplay on 14-7-14.
//
//

#include "TestUii.h"

Scene* TestUii::createScene()
{
	auto scene = Scene::create();
	auto layer=TestUii::create();
	scene->addChild(layer);
	return scene;
}

bool TestUii::init()
{
	if(!BaseUI::init("publish/testUii.json",false))
	{
		return false;
	}
    
	//如果需要对cocostudio 设计的ui进行调整
    
	return true;
}

void TestUii::onEnter()
{
    Layout::onEnter();
    scheduleUpdate();
}

int TestUii::getWidth()
{
    auto image=static_cast<Widget*>(layout->getChildByName("image"));
//    log("22342%f",image->getContentSize().width);

    return image->getContentSize().width;
}


void TestUii::onExit()
{
    BaseUI::onExit();
    this->removeAllChildrenWithCleanup(true);
}