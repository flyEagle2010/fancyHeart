//
//  TestScene.cpp
//  fancyHeart
//
//  Created by zhai on 14-6-12.
//
//

#include "TestScene.h"
#include "BezierMove.h"
#include "WebHttp.h"
//TestScene::TestScene(){
//
//}
//
//TestScene::~TestScene(){
//
//}

Scene* TestScene::createScene(){
	auto scene = Scene::create();
    auto layer=TestScene::create();
    scene->addChild(layer);
	return scene;
}
//void TestScene::draw(Renderer *renderer, const Mat4& transform, bool transformUpdated)
//{
//    Layer::draw(renderer, transform, transformUpdated);
////    Vec2 startP=Vec2(100,200);
////    Vec2 endP=Vec2(600, 200);
//    //Vec2 control1 = startP;
//    Vec2 control1 = Vec2(endP.x/2, endP.y*2);
//    Vec2 control2 = endP;
//    Vec2 end = endP;
//    
//    // 画控制点
//    DrawPrimitives::drawLine(startP, control1);
//    //DrawPrimitives::drawLine(control2, end);
//    
//    // 画贝塞尔曲线
//    DrawPrimitives::drawCubicBezier(startP, control1, end, end, 100);
//}
void TestScene::loadingCallback(std::vector<char> * ref)
{
    ++m_loadedSp;//每进到这个函数一次，让m_loadedSp + 1
    
    char buf_str[16];
    sprintf(buf_str,"%d%%",(int)(((float)m_loadedSp / m_numSp) * 100),m_numSp);
    percentLabel->setString(buf_str);//更新percentLabel的值
    
    float newPercent = 100 - ((float)m_numSp - (float)m_loadedSp)/((float)m_numSp/100);//计算进度条当前的百分比
    //因为加载图片速度很快，所以就没有使用ProgressTo，
    //或者ProgressFromTo这种动作来更新进度条
    loadProgress->setPercentage(newPercent);//更新进度条
    
    
    // dump data
    //std::vector<char> *buffer = response->getResponseData();
    
    std::string path = FileUtils::getInstance()->getWritablePath();
    std::string bufffff(ref->begin(),ref->end());
    //保存到本地文件
    path+="test.png";
    
    CCLOG("path: %s",path.c_str());
    FILE *fp = fopen(path.c_str(), "wb+");
    fwrite(bufffff.c_str(), 1,ref->size(),  fp);
    fclose(fp);
    
    Image* img = new Image();
    img->initWithImageData((unsigned char*)ref->data(), ref->size());
    //this->addChild(img);
    //create texture
//    CCTexture2D* texture = new CCTexture2D();
//    bool isImg = texture->initWithImage(img);
    
//    Texture2D* texture = new Texture2D();
//    bool isImg = texture->initWithImage(img);
//    Sprite* sprite = Sprite::createWithTexture(texture);
//    this->addChild(sprite);
    img->saveToFile("ttt.png");
//    img->release();
    //图片加载完成后
    if(m_loadedSp == m_numSp)
    {
        //this->removeChild(loadProgress);//将添加的几个对象删除掉
        //this->removeChild(percentLabel);
        //this->removeChild(loadLabel);
        
        //加载完既要跳转到gotoNewLayer,在这里可以
        //创建新的Scene，新的Layer，或者其他什么乱七八糟的
        //this->gotoNewLayer();
    }
}
void TestScene::checkUpdate()
{
    
}
AssetsManager* getAssetsManager()
{
    static AssetsManager* pAssetsManager=NULL;
//    if (!pa) {
//        statements
//    }
}
bool TestScene::init(){
	if(!Layer::init()){
		return false;
	}
    Size visibleSize = Director::getInstance()->getVisibleSize();
    Point origin = Director::getInstance()->getVisibleOrigin();
    
    loadLabel = CCLabelTTF::create("Loading:","Arial",20);//创建显示Loading: 的label
    loadLabel->setPosition(Point(visibleSize.width/2-30,visibleSize.height/2+30));
    this->addChild(loadLabel,1);
    
    percentLabel = CCLabelTTF::create("0%","Arial",20);//创建显示百分比的label
    percentLabel->setPosition(Point(visibleSize.width/2+35,visibleSize.height/2+30));
    this->addChild(percentLabel,2);
    
    auto loadBg = Sprite::create("progressBg.png");//进程条的底图
    loadBg->setPosition(Point(visibleSize.width/2,visibleSize.height/2));
    this->addChild(loadBg,1);
    
    loadProgress = ProgressTimer::create(Sprite::create("progress.png"));//创建一个进程条
    loadProgress->setBarChangeRate(Point(1,0));//设置进程条的变化速率
    loadProgress->setType(ProgressTimer::Type::BAR);//设置进程条的类型
    loadProgress->setMidpoint(Point(0,1));//设置进度的运动方向
    loadProgress->setPosition(Point(visibleSize.width/2,visibleSize.height/2));
    loadProgress->setPercentage(0.0f);//设置初始值为0
    this->addChild(loadProgress,2);
    
    this->checkUpdate();
   // WebHttp::getInstance()->send("http://static.adzerk.net/Advertisers/d18eea9d28f3490b8dcbfa9e38f8336e.jpg", CC_CALLBACK_1(TestScene::loadingCallback, this));
//    startP=Vec2(100,200);
//    endP=Vec2(800, 200);
//	//init ui
//    Size size=Director::getInstance()->getVisibleSize();
//    auto sprite2=Sprite::create("arrow.png");
//    sprite2->setPosition(Vec2(size.width/2, size.height/2));
//    addChild(sprite2,20);
//    sprite2->setVisible(false);
//    auto listener1=EventListenerTouchOneByOne::create();
//    listener1->onTouchBegan=[](Touch* touch,Event* event){
//        log("scrollTouchBegan");
//        return true;
//    };
//    listener1->onTouchEnded=[this](Touch* touch,Event* event) {
//        log("scrollTouchEnd");
//        sprite=Sprite::create("arrow.png");
//        addChild(sprite);
//        
//        sprite->setPosition(Vec2(100,200));
//        sprite->cocos2d::Node::setRotation(90);
//        //Utils::BezierRotate(sprite, startP  , endP, 0.6);
//        auto bezierTo1 = BezierMove::create(0.6, endP);
//        Sequence* sp=Sequence::create(bezierTo1,CallFunc::create(CC_CALLBACK_0(Sprite::removeFromParent, sprite)), NULL);
//        sprite->runAction(sp);


//        for(int i=0;i<10;i++){
//            Sprite* sprite=Sprite::create("arrow.png");
//            this->addChild(sprite);
//            sprite->setAnchorPoint(Vec2(0.5,0.5));
//            sprite->setScale(0.4);
//            Utils::BezierRotate(sprite, Vec2(100,200), Vec2(600+50*(i), 200+10*(i%3)) ,.4);
//        }
    //};
    //Director::getInstance()->getEventDispatcher()->addEventListenerWithSceneGraphPriority(listener1, sprite2);
    
    
    
    std::vector<string> strs;
    strs.push_back("aaaa");
    strs.push_back("bbbbbb");
    
    
    string s=strs.at(0);
    strs.erase(strs.begin());
    string s2=strs.at(0);
    strs.erase(strs.begin());
    
    
    
//    WebHttp::getInstance()->send("http://h.hiphotos.baidu.com/news/pic/item/9358d109b3de9c8221dd79636e81800a18d843a6.jpg",CC_CALLBACK_1(TestScene::loadingCallback, this));
    for (int i=0; i<20; i++) {
        
//        Director::getInstance()->getTextureCache()->addImageAsync("http://h.hiphotos.baidu.com/news/pic/item/9358d109b3de9c8221dd79636e81800a18d843a6.jpg", CC_CALLBACK_1(TestScene::loadingCallback, this));
    }
    
	return true;
}