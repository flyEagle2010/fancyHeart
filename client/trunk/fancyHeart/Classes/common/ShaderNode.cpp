//
//  ShaderNode.cpp
//  fancyHeart
//
//  Created by 秦亮亮 on 14-6-10.
//
//

#include "ShaderNode.h"
///---------------------------------------
//
// ShaderNode
//
///---------------------------------------
enum
{
    SIZE_X = 256,
    SIZE_Y = 256,
};

ShaderNode::ShaderNode()
:_center(Vec2(0.0f, 0.0f))
,_resolution(Vec2(0.0f, 0.0f))
,_time(0.0f)
{
}

ShaderNode::~ShaderNode()
{
}

ShaderNode* ShaderNode::shaderNodeWithVertex(const std::string &vert, const std::string& frag)
{
    auto node = new ShaderNode();
    node->initWithVertex(vert, frag);
    node->autorelease();
    
    return node;
}

bool ShaderNode::initWithVertex(const std::string &vert, const std::string &frag)
{
#if CC_ENABLE_CACHE_TEXTURE_DATA
    auto listener = EventListenerCustom::create(EVENT_COME_TO_FOREGROUND, [this](EventCustom* event){
        this->setGLProgramState(nullptr);
        loadShaderVertex(_vertFileName, _fragFileName);
    });
    
    _eventDispatcher->addEventListenerWithSceneGraphPriority(listener, this);
#endif
    
    _vertFileName = vert;
    _fragFileName = frag;
    
    loadShaderVertex(vert, frag);
    
    _time = 0;
    _resolution = Vec2(SIZE_X, SIZE_Y);
    getGLProgramState()->setUniformVec2("resolution", _resolution);
    
    scheduleUpdate();
    
    setContentSize(Size(SIZE_X, SIZE_Y));
    setAnchorPoint(Vec2(0.5f, 0.5f));
    
    
    return true;
}

void ShaderNode::loadShaderVertex(const std::string &vert, const std::string &frag)
{
    auto fileUtiles = FileUtils::getInstance();
    
    // frag
    auto fragmentFilePath = fileUtiles->fullPathForFilename(frag);
    auto fragSource = fileUtiles->getStringFromFile(fragmentFilePath);
    
    // vert
    std::string vertSource;
    if (vert.empty()) {
        vertSource = ccPositionTextureColor_vert;
    } else {
        std::string vertexFilePath = fileUtiles->fullPathForFilename(vert);
        vertSource = fileUtiles->getStringFromFile(vertexFilePath);
    }
    
    auto glprogram = GLProgram::createWithByteArrays(vertSource.c_str(), fragSource.c_str());
    auto glprogramstate = GLProgramState::getOrCreateWithGLProgram(glprogram);
    setGLProgramState(glprogramstate);
}

void ShaderNode::update(float dt)
{
    _time += dt;
}

void ShaderNode::setPosition(const Vec2 &newPosition)
{
    Node::setPosition(newPosition);
    auto position = getPosition();
    _center = Vec2(position.x * CC_CONTENT_SCALE_FACTOR(), position.y * CC_CONTENT_SCALE_FACTOR());
    getGLProgramState()->setUniformVec2("center", _center);
}

void ShaderNode::draw(Renderer *renderer, const Mat4 &transform, bool transformUpdated)
{
    _customCommand.init(_globalZOrder);
    _customCommand.func = CC_CALLBACK_0(ShaderNode::onDraw, this, transform, transformUpdated);
    renderer->addCommand(&_customCommand);
}

void ShaderNode::onDraw(const Mat4 &transform, bool transformUpdated)
{
    float w = SIZE_X, h = SIZE_Y;
    GLfloat vertices[12] = {0,0, w,0, w,h, 0,0, 0,h, w,h};
    
    auto glProgramState = getGLProgramState();
    glProgramState->setVertexAttribPointer("a_position", 2, GL_FLOAT, GL_FALSE, 0, vertices);
    glProgramState->apply(transform);
    
    glDrawArrays(GL_TRIANGLES, 0, 6);
    
    CC_INCREMENT_GL_DRAWN_BATCHES_AND_VERTICES(1,6);
}
