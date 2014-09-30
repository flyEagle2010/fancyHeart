//
//  OpuntiaAnim.h
//  fancyHeart
//
//  Created by 秦亮亮 on 14-9-26.
//
//

#ifndef __fancyHeart__OpuntiaAnim__
#define __fancyHeart__OpuntiaAnim__

#include <stdio.h>
#include "cocos2d.h"
#include "cocostudio/CocoStudio.h"
#include "ui/CocosGUI.h"
using namespace cocos2d;
using namespace cocostudio;
using namespace ui;

class OpuntiaAnim: public Widget
{
public:
    static OpuntiaAnim* create();
    bool init();
    void drink(int index);
    void grow();
private:
    int fruitNum;
    Armature* armature;
    Vector<Armature*> leafs;

    void leafGrow();

    void onFrameEvent(Bone *bone, const std::string& frameEventName, int originFrameIndex, int currentFrameIndex);
    void animationEvent(Armature *armature, MovementEventType movementType, const std::string& movementID);
};

#endif /* defined(__fancyHeart__OpuntiaAnim__) */
