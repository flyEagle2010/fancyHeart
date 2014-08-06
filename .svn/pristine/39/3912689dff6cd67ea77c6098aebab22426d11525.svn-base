//
//  TabBar.cpp
//  fancyHeart
//
//  Created by zhai on 14-7-14.
//
//

#include "TabBar.h"
TabBar* TabBar::create(std::vector<Button*> buttons)
{
    
    TabBar* tabBar=new TabBar();
    if (tabBar && tabBar->init(buttons)) {
        tabBar->setIndex(0);
        tabBar->autorelease();
        return  tabBar;
    }
    CC_SAFE_DELETE(tabBar);
    return nullptr;
}

bool TabBar::init(std::vector<Button*> buttons)
{
    this->buttons=buttons;
    this->setIndex(0);
    return  true;
}

void TabBar::setIndex(int index)
{
    this->index=index;
    if (this->buttons.size()==0) {
        return;
    }
    for (int i=0; i<this->buttons.size(); i++) {
        auto button=this->buttons.at(i);
        if (i==index) {
            button->setBrightStyle(BrightStyle::HIGHLIGHT);
        }else if(i!=index){
            button->setBrightStyle(BrightStyle::NORMAL);
        }
    }
}

int TabBar::getIndex()
{
    return this->index;
}