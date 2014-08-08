//
//  Hero.cpp
//  fancyHeart
//
//  Created by zhai on 14-8-4.
//
//

#ifndef __fancyHeart__Hero__
#define __fancyHeart__Hero__

#include <iostream>
#include "cocos2d.h"
#include "Manager.h"
#include "ui/CocosGUI.h"
#include "cocostudio/CocoStudio.h"
#include "fight.pb.h"
#include "Clip.h"
USING_NS_CC;
using namespace ui;
using namespace cocostudio;
class Hero:public Node
{
public:

    static Hero* create(std::string fPath,std::string rPath,Vec2 pos);
    bool init(std::string fPath,std::string rPath,Vec2 pos);
    virtual void onEnter();
    virtual void onExit();
    virtual void setOpacity(GLubyte opacity);
    void stand();
    void run();
    void playAction(string action,int loop=0);
    void playEffect(int part,string effectName,int times=0);
private:
    Armature* armature;
};
#endif /* defined(__fancyHeart__Hero__) */
