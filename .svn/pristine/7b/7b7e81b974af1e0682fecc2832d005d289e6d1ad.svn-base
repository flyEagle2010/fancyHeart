//
//  RotatList.h
//  fancyHeart
//
//  Created by 秦亮亮 on 14-6-30.
//
//

#ifndef __fancyHeart__RotatList__
#define __fancyHeart__RotatList__

#include <iostream>
#include "cocos2d.h"
#include "ui/UILayout.h"
using namespace cocos2d;
using namespace cocos2d::ui;

class RotateList:public Layout
{
public:
    static RotateList* create();
    void setContentSize(Size size);
    void setItemModel(Widget* model);
    
    void pushBackDefaultItem();
    void removeItem(Widget* item);
    void removeItem(int index);
    
    virtual bool onTouchBegan(Touch *touch, Event *unusedEvent) override;
    virtual void onTouchMoved(Touch *touch, Event *unusedEvent) override;
    virtual void onTouchEnded(Touch *touch, Event *unusedEvent) override;
    virtual void onTouchCancelled(Touch *touch, Event *unusedEvent) override;
    
    virtual void update(float dt) override;
};
#endif /* defined(__fancyHeart__RotatList__) */
