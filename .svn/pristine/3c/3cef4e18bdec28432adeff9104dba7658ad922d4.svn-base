//
//  Clip.h
//  dotaLink
//
//  Created by 秦亮亮 on 14-7-14.
//
//

#ifndef __dotaLink__Clip__
#define __dotaLink__Clip__

#include <iostream>
#include "cocos2d.h"

using namespace cocos2d;

class Clip:public Sprite {
private:
    
public:
    static Clip* create(std::string plistFile,int fps);
    static Clip* create(std::string plistFile);//如果不传递fps默认是图片数量
    virtual bool init(std::string plistFile,int fps=0);
    virtual void onExit();
    float play(bool isLoop=false);
    Animation* animation;
};
#endif /* defined(__dotaLink__Clip__) */
