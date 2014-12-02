//
//  SkillIcon.h
//  fancyHeart
//
//  Created by 秦亮亮 on 14-9-24.
//
//

#ifndef __fancyHeart__SkillIcon__
#define __fancyHeart__SkillIcon__

#include <stdio.h>
#include "cocos2d.h"
#include "ui/CocosGUI.h"
#include "XSkill.h"
#include "Skill.h"
#include "cocosGUI.h"
USING_NS_CC;
using namespace ui;

class Skill;
class SkillIcon:public Widget
{
public:
    static SkillIcon* create(ImageView* rim,Vec2 pos,int skillID);
    bool init(ImageView* rim,Vec2(pos),int skillID);
//    void setIsOK(bool isOK);
//    void resetBeans(int num);
    void start();
    void skillReady();
public:
    Vector<ImageView*> beans;
    Vector<ImageView*> beanBgs;
    ImageView* rim;
    bool isOK;
    int skillID;
    Skill* skill;
private:
    ProgressTimer* progressBar;
};

#endif /* defined(__fancyHeart__SkillIcon__) */
