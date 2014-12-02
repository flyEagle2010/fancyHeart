//
//  TestUi.cpp
//  fancyHeart
//
//  Created by doteyplay on 14-7-2.
//
//

#include "TestUi.h"
#include "RotateList.h"

Scene* TestUi::createScene()
{
	auto scene = Scene::create();
	auto layer=TestUi::create();
	scene->addChild(layer);
	return scene;
}

bool TestUi::init()
{
    Size widgetSize = Director::getInstance()->getWinSize();
    
//    TestUi2* scrollView = TestUi2::create();
    RotateList* scrollView = RotateList::create();
    
    scrollView->setSize(Size(widgetSize.width, widgetSize.height));
    scrollView->setPosition(Vec2(0,0));
    
    TestUi*testUii = TestUi::create();
    scrollView->setItemModel(testUii);
    this->addChild(scrollView);

	return true;
}

void TestUi::onEnter()
{
    Widget::onEnter();
}

bool TestUi::onTouchBegan(Touch* touch, Event* event)
{
    // 获取点击的坐标
    this->isStart=false;
    moveBegainX =touch->getStartLocation().x;
    return true;
}

void TestUi::onTouchMoved(Touch* touch, Event* event)
{
    this->isStart=true;
}

void TestUi::onTouchEnded(Touch* touch, Event* event)
{
    this->isStart=false;
}

void TestUi::onTouchCancelled(Touch* touch, Event* event)
{
    this->isStart=false;
}

void TestUi::update(float dt)
{
    if(this->isStart){
    }
}

void TestUi::onExit()
{
    Widget::onExit();
    this->removeAllChildrenWithCleanup(true);
}