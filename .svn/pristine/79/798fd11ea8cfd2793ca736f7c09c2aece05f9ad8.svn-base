//
//  Effect.h
//  fancyHeart
//
//  Created by 秦亮亮 on 14-8-22.
//
//

#ifndef __fancyHeart__Effect__
#define __fancyHeart__Effect__

#include <iostream>
#include "cocos2d.h"

using namespace cocos2d;

class EffectSprite;

class Effect : public Ref
{
public:
    GLProgramState* getGLProgramState() const { return _glprogramstate; }
    virtual void setTarget(EffectSprite *sprite){}
    
protected:
    bool initGLProgramState(const std::string &fragmentFilename);
    Effect() : _glprogramstate(nullptr){}
    virtual ~Effect() {}
    GLProgramState *_glprogramstate;
};
#endif /* defined(__fancyHeart__Effect__) */
