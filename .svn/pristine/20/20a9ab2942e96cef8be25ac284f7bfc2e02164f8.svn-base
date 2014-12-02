//
//  CardItem.h
//  fancyHeart
//
//  Created by doteyplay on 14-8-20.
//
//

#ifndef __fancyHeart__CardItem__
#define __fancyHeart__CardItem__

#include <iostream>
#include "cocos2d.h"
#include "Manager.h"
#include "BaseUI.h"
#include "XCraft.h"
#include "Utils.h"
using namespace ui;
using namespace cocos2d;
using namespace cocostudio;
class CardItem:public BaseUI
{
public:
	static CardItem* create();
	virtual bool init(std::string fileName);
	virtual void onEnter();
	virtual void onExit();
    virtual const Size& getInnerSize() const;
    Widget* item;
    virtual void setTouchEnabled(bool enabled);
    
    //Widget* clone();
private:
    void initUI(int npcId);
    
    };
#endif /* defined(__fancyHeart__CardItem__) */
