//
//  ShaderNode.h
//  fancyHeart
//
//  Created by 秦亮亮 on 14-6-10.
//
//

#ifndef __fancyHeart__ShaderNode__
#define __fancyHeart__ShaderNode__

#include <iostream>
#include "cocos2d.h"
using namespace cocos2d;

class ShaderNode : public Node
{
public:
    static ShaderNode* shaderNodeWithVertex(const std::string &vert, const std::string &frag);
    
    virtual void update(float dt);
    virtual void setPosition(const Vec2 &newPosition);
    virtual void draw(Renderer *renderer, const Mat4 &transform, bool transformUpdated);
    
protected:
    ShaderNode();
    ~ShaderNode();
    
    bool initWithVertex(const std::string &vert, const std::string &frag);
    void loadShaderVertex(const std::string &vert, const std::string &frag);
    
    void onDraw(const Mat4 &transform, bool transformUpdated);
    
    Vec2 _center;
    Vec2 _resolution;
    float      _time;
    std::string _vertFileName;
    std::string _fragFileName;
    CustomCommand _customCommand;
};
#endif /* defined(__fancyHeart__ShaderNode__) */
