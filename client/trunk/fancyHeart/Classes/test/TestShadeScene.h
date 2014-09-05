//
//  TestShadeScene.h
//  fancyHeart
//
//  Created by 秦亮亮 on 14-8-20.
//
//

#ifndef __fancyHeart__TestShadeScene__
#define __fancyHeart__TestShadeScene__

#include <iostream>
#include "cocos2d.h"
#include "Manager.h"
using namespace cocos2d;
using namespace ui;
using namespace cocostudio;
class TestShadeScene:public BaseUI{
public:
	static Scene* createScene();
	CREATE_FUNC(TestShadeScene);
	virtual void onEnter();
	virtual void onExit();
	virtual bool init();

private:

};
#endif /* defined(__fancyHeart__TestShadeScene__) */
