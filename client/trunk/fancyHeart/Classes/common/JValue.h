//
//  JsonValue.h
//  fancyHeart
//
//  Created by 秦亮亮 on 14-6-20.
//
//

#ifndef __fancyHeart__JValue__
#define __fancyHeart__JValue__
#include "external/json/document.h"
#include "external/json/writer.h"
#include "external/json/stringbuffer.h"
#include <iostream>
#include "cocos2d.h"
using namespace cocos2d;
class JValue:public Ref
{
private:
//    rapidjson::Document::AllocatorType& allocator;
    rapidjson::Document doc;
public:
    static JValue* create();
    JValue();
    bool init();
    void setItem(const char* key,Value val);
    Value getItem(const char* key);
    
    rapidjson::Value item;
};
#endif /* defined(__fancyHeart__JValue__) */
