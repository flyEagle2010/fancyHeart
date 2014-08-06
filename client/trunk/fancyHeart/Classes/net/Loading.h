//
//  loading.h
//  fancyHeart
//
//  Created by zhai on 14-6-25.
//
//

#ifndef __fancyHeart__Loading__
#define __fancyHeart__Loading__
#include "cocos2d.h"
#include "ui/UILayout.h"
USING_NS_CC;
class Loading:public cocos2d::ui::Layout
{
public:
    static Loading* getInstance();
    virtual bool init();
    void show();
    void hide();
private:
    Sprite* img;
};
#endif /* defined(__fancyHeart__Loading__) */
