//
//  TestScene2.cpp
//  fancyHeart
//
//  Created by 秦亮亮 on 14-6-25.
//
//

#include "TestScene2.h"
#include "Clip.h"
#include "VFighter.h"
Scene* TestScene2::createScene(){
	auto scene = Scene::create();
    scene->addChild(TestScene2::create());
	return scene;
}

bool TestScene2::init(){
//	if(!BaseUI::init("publish/gate/gate.ExportJson")){
//		return false;
//	}
//	//init ui
//    auto winSize = Director::getInstance()->getWinSize();
    
    /*
    Clip* clip=Clip::create("effect/battle_Flashing box.plist", "battle_Flashing box");
    this->addChild(clip);
    clip->setPosition(Vec2(520,320));
    clip->setScale(2, 2);
    
    return true;
    */
    
//    this->rotateList = RotateList::create();
////    this->rotateList->setSize(Size(widgetSize.width, widgetSize.height));
////    this->rotateList->addEventListener(CC_CALLBACK_2(Gate::rotateListCallback,this));
//    
//    //模版
//    auto item=(Widget*)layout->getChildByName("item");
//    item->removeFromParent();
//    this->rotateList->setItemModel(item,winSize.width,Size(winSize.width,item->getContentSize().height),winSize.width/7);
//    this->addChild(this->rotateList);
//    this->rotateList->setPosition(Vec2(0,200));
//    
//    //滚动条
//    auto bottom=static_cast<Widget*>(layout->getChildByName("bottom"));
//    Slider* slider=static_cast<Slider*>(bottom->getChildByName("slider"));
//    this->rotateList->setSlider(slider);
//
//    this->rotateList->setNum(140);


    auto listener1 = EventListenerTouchOneByOne::create();
    listener1->setSwallowTouches(true);
    
    listener1->onTouchBegan = [](Touch* touch, Event* event){
        auto target = static_cast<Sprite*>(event->getCurrentTarget());
        
        return true;
    };
    
    listener1->onTouchMoved = [](Touch* touch, Event* event){
        auto target = static_cast<Sprite*>(event->getCurrentTarget());
        target->setPosition(target->getPosition() + touch->getDelta());
    };
    
    listener1->onTouchEnded = [=](Touch* touch, Event* event){
        auto target = static_cast<Sprite*>(event->getCurrentTarget());
        //log("et name:%s",this->et->animation->name);
//        this->skeletonNode->clearTracks();
        this->skeletonNode->clearTrack(1);
        this->skeletonNode->setAnimation(2, ani_attack, false);

    };
    
    _eventDispatcher->addEventListenerWithSceneGraphPriority(listener1, this);
    
    
    this->spineTest();
    
	return true;
}

void TestScene2::spineTest()
{
    skeletonNode = SkeletonAnimation::createWithFile("huLi.json","huLi.atlas");
    skeletonNode->setScale(0.5);
    this->addChild(skeletonNode);
    skeletonNode->setPosition(Vec2(580,320));
    this->skeletonNode->setEndListener(CC_CALLBACK_1(TestScene2::onAnimationEnd, this));

    
    et=skeletonNode->setAnimation(0, ani_idle, false);
    et=skeletonNode->setAnimation(1, ani_skillAttack1, false);
    skeletonNode->addAnimation(1, ani_idle, true);
    log("et name:%s",et->animation->name);
    
    this->setTouchEnabled(true);
    this->addTouchEventListener(CC_CALLBACK_2(TestScene2::onTouch, this));
}

void TestScene2::onAnimationEnd(int trackIndex)
{
    switch (trackIndex) {
        case 1:
//            this->skeletonNode->setAnimation(0, ani_idle, false);
            break;
            
        default:
            break;
    }
}


void TestScene2::onTouch(cocos2d::Ref *, Widget::TouchEventType)
{
    log("et name:%s",this->et->animation->name);
}

void TestScene2::listEvent(Ref* pSender, ScrollView::EventType type)
{
    
}

void TestScene2::selectedItemEvent(Ref *pSender, ListView::EventType type)
{
    
}
