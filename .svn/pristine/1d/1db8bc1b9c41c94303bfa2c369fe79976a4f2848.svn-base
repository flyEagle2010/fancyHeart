//
//  TestScene2.h
//  fancyHeart
//
//  Created by 秦亮亮 on 14-6-25.
//
//

#ifndef __fancyHeart__TestScene2__
#define __fancyHeart__TestScene2__

#include <iostream>
#include "cocos2d.h"
#include "Manager.h"
#include "ui/CocosGUI.h"
#include "cocostudio/CocoStudio.h"
#include "RotateList.h"
USING_NS_CC;
class TestScene2:public BaseUI{
public:
	static Scene* createScene();
	virtual bool init();
	CREATE_FUNC(TestScene2);
//    virtual void onEnter();
    
    void listEvent(Ref* pSender, ScrollView::EventType type);
    void selectedItemEvent(Ref *pSender, ListView::EventType type);
private:
    RotateList* rotateList;
};
#endif /* defined(__fancyHeart__TestScene2__) */
