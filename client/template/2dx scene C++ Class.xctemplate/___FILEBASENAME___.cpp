//
//  ___FILENAME___
//  ___PROJECTNAME___
//
//  Created by ___FULLUSERNAME___ on ___DATE___.
//___COPYRIGHT___
//

#include "___FILEBASENAME___.h"

Scene* ___FILEBASENAMEASIDENTIFIER___::createScene()
{
	auto scene = Scene::create();
	auto layer=___FILEBASENAMEASIDENTIFIER___::create();
	scene->addChild(layer);
	return scene;
}

bool ___FILEBASENAMEASIDENTIFIER___::init()
{
	if(!BaseUI::init("publish/___FILEBASENAMEASIDENTIFIER___.json",true))
	{
		return false;
	}
	//如果需要对cocostudio 设计的ui进行调整

	return true;
}

void ___FILEBASENAMEASIDENTIFIER___::onEnter()
{

}



void ___FILEBASENAMEASIDENTIFIER___::onExit()
{

}