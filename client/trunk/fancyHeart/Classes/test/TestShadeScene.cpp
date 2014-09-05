//
//  TestShadeScene.cpp
//  fancyHeart
//
//  Created by 秦亮亮 on 14-8-20.
//
//

#include "TestShadeScene.h"
#include "ShaderNode.h"

Scene* TestShadeScene::createScene()
{
	auto scene = Scene::create();
	auto layer=TestShadeScene::create();
	scene->addChild(layer);
	return scene;
}

bool TestShadeScene::init()
{
    Node::init();
	//如果需要对cocostudio 设计的ui进行调整
//    this->setGLProgram(GLProgramCache::getInstance()->getGLProgram(GLProgram::SHADER_NAME_POSITION_COLOR));
    auto sn = ShaderNode::shaderNodeWithVertex("", "Shaders/shadertoy_LensFlare.fsh");
    
    auto s = Director::getInstance()->getWinSize();
    sn->setPosition(Vec2(s.width/2, s.height/2));
    sn->setContentSize(Size(s.width/2,s.height/2));
    addChild(sn);
    
	return true;
}

void TestShadeScene::onEnter()
{

}

void TestShadeScene::visit(Renderer *renderer, const Mat4 &transform, bool transformUpdated)
{
    Node::visit(renderer,transform,transformUpdated);
//    this->command.init(_globalZOrder);
//    this->command.func=CC_CALLBACK_0(TestShadeScene::onDraw,this);
//    Director::getInstance()->getRenderer()->addCommand(&command);
}

void TestShadeScene::onDraw()
{
    /*
    //获得当前HelloWorld的shader
    auto glProgram = getGLProgram();
    //使用此shader
    glProgram->use();
    //设置该shader的一些内置uniform,主要是MVP，即model-view-project矩阵
    glProgram->setUniformsForBuiltins();
    
    auto size = Director::getInstance()->getWinSize();
    //指定将要绘制的三角形的三个顶点，分别位到屏幕左下角，右下角和正中间的顶端
    float vertercies[] = { 0,0,   //第一个点的坐标
        size.width, 0,   //第二个点的坐标
        size.width / 2, size.height};  //第三个点的坐标
    //指定每一个顶点的颜色，颜色值是RGBA格式的，取值范围是0-1
    float color[] = { 0, 1,0, 1,    //第一个点的颜色，绿色
        1,0,0, 1,  //第二个点的颜色, 红色
        0, 0, 1, 1};  //第三个点的颜色， 蓝色
    //激活名字为position和color的vertex attribute
    GL::enableVertexAttribs(GL::VERTEX_ATTRIB_FLAG_POSITION | GL::VERTEX_ATTRIB_FLAG_COLOR);
    //分别给position和color指定数据源
    glVertexAttribPointer(GLProgram::VERTEX_ATTRIB_POSITION, 2, GL_FLOAT, GL_FALSE, 0, vertercies);
    glVertexAttribPointer(GLProgram::VERTEX_ATTRIB_COLOR, 4, GL_FLOAT, GL_FALSE, 0, color);
    //绘制三角形，所谓的draw call就是指这个函数调用
    glDrawArrays(GL_TRIANGLES, 0, 3);
    //通知cocos2d-x 的renderer，让它在合适的时候调用这些OpenGL命令
    CC_INCREMENT_GL_DRAWN_BATCHES_AND_VERTICES(1, 3);
    //如果出错了，可以使用这个函数来获取出错信息
    CHECK_GL_ERROR_DEBUG();
     */
    
    //create my own program
    auto program = new GLProgram;
    program->initWithFilenames("myVertextShader.vert", "myFragmentShader.frag");
    program->link();
    //set uniform locations
    program->updateUniforms();
    
    program->setUniformsForBuiltins();
    program->use();
    program->setUniformsForBuiltins();
    auto size=Director::getInstance()->getWinSize();
    float vertercies[] = {0,0,size.width,0,size.width/2};
    float color[]={0,1,0,1,
        1,0,0,1,
        0,0,1,1};
    GL::enableVertexAttribs(GL::VERTEX_ATTRIB_FLAG_POSITION | GL::VERTEX_ATTRIB_FLAG_COLOR);
    glVertexAttribPointer(GLProgram::VERTEX_ATTRIB_POSITION,2,GL_FLOAT,GL_FALSE,0,vertercies);
    glVertexAttribPointer(GLProgram::VERTEX_ATTRIB_COLOR,4,GL_FLOAT,GL_FALSE,0,color);
    glDrawArrays(GL_TRIANGLES,0,3);
    CC_INCREMENT_GL_DRAWN_BATCHES_AND_VERTICES(1,3);
    CHECK_GL_ERROR_DEBUG();
    
 
    /*
    auto glProgram=this->getGLProgram();
    glProgram->use();
    glProgram->setUniformsForBuiltins();
    auto size=Director::getInstance()->getWinSize();
    float vertercies[] = {0,0,size.width,0,size.width/2};
    float color[]={0,1,0,1,
                   1,0,0,1,
                   0,0,1,1};
    GL::enableVertexAttribs(GL::VERTEX_ATTRIB_FLAG_POSITION | GL::VERTEX_ATTRIB_FLAG_COLOR);
    glVertexAttribPointer(GLProgram::VERTEX_ATTRIB_POSITION,2,GL_FLOAT,GL_FALSE,0,vertercies);
    glVertexAttribPointer(GLProgram::VERTEX_ATTRIB_COLOR,4,GL_FLOAT,GL_FALSE,0,color);
    glDrawArrays(GL_TRIANGLES,0,3);
    CC_INCREMENT_GL_DRAWN_BATCHES_AND_VERTICES(1,3);
    CHECK_GL_ERROR_DEBUG();
    */
}

void TestShadeScene::onExit()
{

}