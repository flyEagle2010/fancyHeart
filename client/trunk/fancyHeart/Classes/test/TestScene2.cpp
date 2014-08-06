//
//  TestScene2.cpp
//  fancyHeart
//
//  Created by 秦亮亮 on 14-6-25.
//
//

#include "TestScene2.h"

Scene* TestScene2::createScene(){
	auto scene = Scene::create();
	return scene;
}

bool TestScene2::init(){
	if(!Layer::init()){
		return false;
	}
	//init ui
    /*
     ui::ScrollView* scrollView = ui::ScrollView::create();
     scrollView->setSize(Size(280.0f, 150.0f));
     Size backgroundSize = background->getContentSize();
     scrollView->setPosition(Vec2((widgetSize.width - backgroundSize.width) / 2.0f +
     (backgroundSize.width - scrollView->getSize().width) / 2.0f,
     (widgetSize.height - backgroundSize.height) / 2.0f +
     (backgroundSize.height - scrollView->getSize().height) / 2.0f));
     _uiLayer->addChild(scrollView);
     
     ImageView* imageView = ImageView::create("cocosui/ccicon.png");
     
     float innerWidth = scrollView->getSize().width;
     float innerHeight = scrollView->getSize().height + imageView->getSize().height;
     
     scrollView->setInnerContainerSize(Size(innerWidth, innerHeight));
    */
    
    ScrollView* view=ScrollView::create();
    view->setDirection(ScrollView::Direction::HORIZONTAL);
    view->setTouchEnabled(true);
    view->setBounceEnabled(false);
    view->setSize(Size(960,640));
    view->setPosition(Vec2(480,320));
    view->setInnerContainerSize(Size(300,200));
    view->addEventListener(CC_CALLBACK_2(TestScene2::listEvent, this));
    
	return true;
}

void TestScene2::listEvent(Ref* pSender, ScrollView::EventType type)
{
    
}

void TestScene2::selectedItemEvent(Ref *pSender, ListView::EventType type)
{
    
}
