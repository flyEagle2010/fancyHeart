//
//  Clip.cpp
//  dotaLink
//
//  Created by 秦亮亮 on 14-7-14.
//
//

#include "Clip.h"

Clip* Clip::create(std::string plistFile,std::string frameName,int fps)
{
    Clip* pRet=new Clip();
    if(pRet && pRet->init(plistFile,frameName,fps)){
        pRet->autorelease();
        return pRet;
    }
    CC_SAFE_DELETE(pRet);
    
    return nullptr;
}

bool Clip::init(std::string plistFile,std::string frameName,int fps)
{
    Sprite::init();
    SpriteFrameCache::getInstance()->addSpriteFramesWithFile(plistFile);

    std::string fullPath = FileUtils::getInstance()->fullPathForFilename(plistFile);
    ValueMap dict = FileUtils::getInstance()->getValueMapFromFile(fullPath);
    ValueMap map=dict["frames"].asValueMap();

    ValueMap::iterator it;
    Vector<SpriteFrame*> frames;
    
    this->setSpriteFrame(SpriteFrameCache::getInstance()->getSpriteFrameByName(map.begin()->first));
    /*
    for(it=map.begin();it!=map.end();++it){        
        std::string imgName=it->first;
        SpriteFrame* pFrame=SpriteFrameCache::getInstance()->getSpriteFrameByName(imgName);
        frames.pushBack(pFrame);
    }
    */
    for(int i=1;i<=map.size();i++){
        std::string imgName=frameName+"_"+Value(i).asString()+".png";
        SpriteFrame* pFrame=SpriteFrameCache::getInstance()->getSpriteFrameByName(imgName);
        frames.pushBack(pFrame);
    }
    
    this->animation=Animation::createWithSpriteFrames(frames,1.f/(fps==0?frames.size():fps));
    this->animation->retain();
    return true;
}

float Clip::play(bool isLoop)
{
    this->stopAllActions();
    if(isLoop){
        RepeatForever* action=RepeatForever::create(Animate::create(this->animation));
        this->runAction(action);
        return -1;
    }else{
        Animate* ani=Animate::create(this->animation);
        this->runAction(Sequence::create(ani,CCCallFunc::create(CC_CALLBACK_0(Clip::removeFromParent,this)),NULL));
        return animation->getDuration();
    }
}

void Clip::play(int times)
{
    if(times==0){
        this->play(true);
    }else{
        Repeat* action=Repeat::create(Animate::create(this->animation), times);
        this->runAction(Sequence::create(action,CCCallFunc::create(CC_CALLBACK_0(Clip::removeFromParent,this)),NULL));
    }
    
}

void Clip::playNext(int times,float duration)
{
    if(times==0){
        this->play(true);
    }else{
        Repeat* action=Repeat::create(Animate::create(this->animation), times);
        this->runAction(Sequence::create(
                                         action,
                                         CallFuncN::create(CC_CALLBACK_0(Sprite::setVisible,this,false)),
                                         DelayTime::create(duration),
                                         CallFuncN::create(CC_CALLBACK_0(Sprite::setVisible,this,true)),
                                         CallFuncN::create(CC_CALLBACK_0(Clip::playNext,this,times,duration)),NULL));
    }
}

void Clip::onExit()
{
    Node::onExit();
    this->animation->release();
}