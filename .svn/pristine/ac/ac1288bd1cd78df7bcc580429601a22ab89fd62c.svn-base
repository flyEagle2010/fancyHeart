//
//  Utils.cpp
//  fancyHeart
//
//  Created by 秦亮亮 on 14-6-3.
//
//

#include "Utils.h"
#define PI 3.14159265
#define EPSION 0.0001f
#define IS_EQUAL(val1, val2)  (fabs((val1) - (val2)) <= EPSION)
#include "BezierMove.h"
float Utils::random01()
{
    //srand(time(0));
    return (float)rand()/RAND_MAX;
}

long Utils::getTime()
{
    struct timeval tv;
    gettimeofday(&tv,NULL);
    return tv.tv_sec * 1000 + tv.tv_usec / 1000;
}

double Utils::distance(Vec2 sp,Vec2 ep)
{
    return sqrt((ep.x-sp.x)*(ep.x-sp.x)+(ep.y-sp.y)*(ep.y-sp.y));
}



std::vector<int> Utils::randSeveral(int num,int count,bool isRepeat)
{
    std::vector<int> arr;
    for(int i=0;i<count;i++)
    {
        int item=random()%num;
        if(isRepeat)
        {
            arr.push_back(item);
            continue;
        }
        if( find(arr.begin(),arr.end(),item) != arr.end())
        {
            i--;
        }else{
            arr.push_back(item);
        }
    }
    return arr;
}

std::string Utils::getLang(string arg,std::vector<string> params)
{
    for (int i=0; i<params.size(); i++) {
        string format="{"+Value(i+1).asString()+"}";
        arg.replace(arg.find(format), format.length(), params.at(i));
    }
    return  arg;
}

std::string Utils::getLang(string str){
    return str;
}

std::vector<std::string> Utils::split(std::string str, std::string match)
{
    std::vector<std::string> strvec;
    std::string::size_type pos1, pos2;
    pos2 = str.find(match);
    pos1 = 0;
    while (std::string::npos != pos2)
    {
        strvec.push_back(str.substr(pos1, pos2 - pos1));
        
        pos1 = pos2 + 1;
        pos2 = str.find(match, pos1);
    }
    strvec.push_back(str.substr(pos1));
    return strvec;
}

Sprite* Utils::maskedSpriteWithSprite(Sprite* textureSprite, Sprite* maskSprite)
{
    if (textureSprite->getContentSize().width<maskSprite->getContentSize().width && textureSprite->getContentSize().height<maskSprite->getContentSize().height) {
        return textureSprite;
    }
    // 1
    RenderTexture * rt = RenderTexture::create( maskSprite->getContentSize().width,
                                               maskSprite->getContentSize().height );
    
    // 2
    maskSprite->setPosition(maskSprite->getContentSize().width/2,
                            maskSprite->getContentSize().height/2);
    textureSprite->setPosition(textureSprite->getContentSize().width/2,
                               textureSprite->getContentSize().height/2);
    
    // 3
    maskSprite->setBlendFunc( BlendFunc{GL_ONE, GL_ZERO} );
    textureSprite->setBlendFunc( BlendFunc{GL_DST_ALPHA, GL_ZERO} );
    
    // 4
    rt->begin();
    maskSprite->visit();
    textureSprite->visit();
    rt->end();
    
    // 5
    Sprite *retval = Sprite::createWithTexture(rt->getSprite()->getTexture());
    retval->setFlippedY(true);
    return retval;
}

void Utils::addGray(Sprite* sp)
{
    GLProgram * p = new GLProgram();
    p->initWithFilenames("gray.vsh", "gray.fsh");
    p->bindAttribLocation(GLProgram::ATTRIBUTE_NAME_POSITION, GLProgram::VERTEX_ATTRIB_POSITION);
    p->bindAttribLocation(GLProgram::ATTRIBUTE_NAME_COLOR, GLProgram::VERTEX_ATTRIB_COLOR);
    p->bindAttribLocation(GLProgram::ATTRIBUTE_NAME_TEX_COORD, GLProgram::VERTEX_ATTRIB_TEX_COORDS);
    p->link();
    p->updateUniforms();
    sp->setGLProgram(p);
}
