//
//  TestShadeScene.cpp
//  fancyHeart
//
//  Created by 秦亮亮 on 14-8-20.
//
//

#include "TestShadeScene.h"

Scene* TestShadeScene::createScene()
{
	auto scene = Scene::create();
	auto layer=TestShadeScene::create();
	scene->addChild(layer);
	return scene;
}

bool TestShadeScene::init()
{
	if(!BaseUI::init("publish/TestShadeScene.json",true))
	{
		return false;
	}
	//如果需要对cocostudio 设计的ui进行调整

	return true;
}

void TestShadeScene::onEnter()
{

}



void TestShadeScene::onExit()
{

}