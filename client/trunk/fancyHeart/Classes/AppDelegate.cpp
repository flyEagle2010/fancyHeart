#include "AppDelegate.h"
#include "LoginScene.h"
#include "TestScene.h"
#include "Update.h"
USING_NS_CC;

AppDelegate::AppDelegate() {

}

AppDelegate::~AppDelegate() 
{
}

bool AppDelegate::applicationDidFinishLaunching() {
    // initialize director
    auto director = Director::getInstance();
    auto glview = director->getOpenGLView();
    if(!glview) {
        glview = GLView::create("My Game");
        director->setOpenGLView(glview);
    }

    // turn on display FPS
    director->setDisplayStats(true);

    // set FPS. the default value is 1.0/60 if you don't call this
    director->setAnimationInterval(1.0 / 60);
    
    Size size=glview->getFrameSize();
//    if(((int)size.width%1024==0) && ((int)size.height%768==0)){
//        glview->setDesignResolutionSize(1136.0, 640.0, ResolutionPolicy::FIXED_WIDTH);
//    }
//    else{
    glview->setDesignResolutionSize(1136, 640, ResolutionPolicy::FIXED_HEIGHT);
//    }
    
//    glview->setDesignResolutionSize(1136.0, 640.0, ResolutionPolicy::FIXED_WIDTH);
    std::vector<std::string>searchPath=FileUtils::getInstance()->getSearchPaths();
    searchPath.push_back("/");
    searchPath.push_back("bone");
    searchPath.push_back("data");
    searchPath.push_back("effect");
    searchPath.push_back("fight");
    searchPath.push_back("fonts");
    searchPath.push_back("loading");
    searchPath.push_back("map");
    searchPath.push_back("projectile");
    searchPath.push_back("publish");
    searchPath.push_back("publish/chat");
    searchPath.push_back("publish/fight");
    searchPath.push_back("publish/gate");
    searchPath.push_back("publish/gateMap");
    searchPath.push_back("publish/gateResult");
    searchPath.push_back("publish/gateSelect");
    searchPath.push_back("publish/home");
    searchPath.push_back("publish/srcImg");
    searchPath.push_back("bone/man");
    searchPath.push_back("shader");
    searchPath.push_back("Shaders");
    searchPath.push_back("test");
    
    
    
    FileUtils::getInstance()->setSearchPaths(searchPath);
//    director->setContentScaleFactor(640.0/320.0);

    std::string pathToSave = FileUtils::getInstance()->getWritablePath();
    pathToSave += "tmpdir";
    std::vector<std::string> searchPaths = FileUtils::getInstance()->getSearchPaths();
    std::vector<std::string>::iterator iter = searchPaths.begin();
    searchPaths.insert(iter, pathToSave);
    FileUtils::getInstance()->setSearchPaths(searchPaths);


    // create a scene. it's an autorelease object
    auto scene = LoginScene::createScene();
    //auto scene = TestScene::createScene();
//   auto scene = Update::createScene();

    // run
//    director->runWithScene(scene);
//
//    Manager::getInstance()->scene=scene;
    Manager::getInstance()->switchScence(scene);
   

    
    auto listener = EventListenerKeyboard::create();
    listener->onKeyReleased =[&](EventKeyboard::KeyCode keyCode, Event* event)
    {
        if(keyCode == EventKeyboard::KeyCode::KEY_BACKSPACE)
        {
            Director::getInstance()->end();
            #if (CC_TARGET_PLATFORM == CC_PLATFORM_IOS)
                exit(0);
            #endif
        }
    };
    Director::getInstance()->getEventDispatcher()->addEventListenerWithFixedPriority(listener, -1);
    return true;
}

// This function will be called when the app is inactive. When comes a phone call,it's be invoked too
void AppDelegate::applicationDidEnterBackground() {
    Director::getInstance()->stopAnimation();

    // if you use SimpleAudioEngine, it must be pause
    // SimpleAudioEngine::getInstance()->pauseBackgroundMusic();
}

// this function will be called when the app is active again
void AppDelegate::applicationWillEnterForeground() {
    Director::getInstance()->startAnimation();

    // if you use SimpleAudioEngine, it must resume here
    // SimpleAudioEngine::getInstance()->resumeBackgroundMusic();
}
