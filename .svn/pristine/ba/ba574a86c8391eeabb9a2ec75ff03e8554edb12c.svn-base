//
//  JsonValue.cpp
//  fancyHeart
//
//  Created by 秦亮亮 on 14-6-20.
//
//

#include "JValue.h"
JValue::JValue(){
    
}

JValue* JValue::create()
{
    JValue* pRet=new JValue();
    if(pRet && pRet->init()){
        pRet->autorelease();
    }
    return pRet;
}

bool JValue::init()
{
    this->item.SetObject();
    return true;
}

void JValue::setItem(const char *key, cocos2d::Value val)
{
    this->item.AddMember(key, val.asString().c_str(), doc.GetAllocator());
}

Value JValue::getItem(const char* key)
{
    return Value(item[key].GetString());
}