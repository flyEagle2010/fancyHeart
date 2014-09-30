//
//  TabBar.h
//  fancyHeart
//
//  Created by zhai on 14-7-14.
//
//

#ifndef __fancyHeart__TabBar__
#define __fancyHeart__TabBar__

#include <iostream>
#include "cocos2d.h"
#include "ui/CocosGUI.h"
using namespace cocos2d;
using namespace ui;

class TabBar:public Ref
{
public:
	static TabBar* create(std::vector<Button*> buttons);//buttons把作为tabbar的一组按钮传进去
    virtual bool init(std::vector<Button*> buttons);//
    void setIndex(int index);//设置点中index默认从0开始
    int getIndex();//获取当前index
private:

private: //私有属性
    std::vector<Button*> buttons;
    int index;
    
};
#endif /* defined(__fancyHeart__TabBar__) */
