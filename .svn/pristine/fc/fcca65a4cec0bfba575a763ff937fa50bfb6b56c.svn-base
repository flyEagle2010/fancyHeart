//
//  TestUi.h
//  fancyHeart
//
//  Created by doteyplay on 14-7-2.
//
//

#ifndef __fancyHeart__TestUi__
#define __fancyHeart__TestUi__

#include <iostream>
#include "cocos2d.h"
#include "Manager.h"
//#include "RotateList.h"
using namespace cocos2d;
using namespace ui;
using namespace cocostudio;
class TestUi:public Widget{
public:
	static Scene* createScene();
	CREATE_FUNC(TestUi);
	virtual void onEnter();
	virtual void onExit();
	virtual bool init();
    int getY(float x);
    virtual bool onTouchBegan(Touch *touch, Event *unusedEvent);
    virtual void onTouchMoved(Touch *touch, Event *unusedEvent);
    virtual void onTouchEnded(Touch *touch, Event *unusedEvent);
    virtual void onTouchCancelled(Touch *touch, Event *unusedEvent);
//    void touchEvent(Ref *pSender, TouchEventType type);
    virtual void update(float dt);
    void autoScrollChildren(float dt);

    
    bool isStart;
    int moveBegainX;//移动的初始点
    int movePointX; //当前移动触发点x坐标点

    
    Layer*containlayer;
 
};
#endif /* defined(__fancyHeart__TestUi__) */
