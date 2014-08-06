//
//  Update.cpp
//  fancyHeart
//
//  Created by zhai on 14-6-17.
//
//

#include "Update.h"
#include "login/LoginScene.h"
Update::Update(){

}

Update::~Update(){

}

Scene* Update::createScene(){
	auto scene = Scene::create();
    auto layer=Update::create();
    scene->addChild(layer);
	return scene;
}
bool Update::init(){
	if(!Layer::init()){
		return false;
	}
    createDownloadedDir();
	//init ui
    Size visibleSize = Director::getInstance()->getVisibleSize();
    Point origin = Director::getInstance()->getVisibleOrigin();
    
    
//    auto sprite=Sprite::create("battle.png");
//    this->addChild(sprite);
//    sprite->setAnchorPoint(Vec2(0, 0));
    
    
    loadLabel = Label::createWithTTF("Loading:","Marker Felt.ttf",20);//创建显示Loading: 的label
    loadLabel->setPosition(Point(visibleSize.width/2-30,visibleSize.height/2+30));
    this->addChild(loadLabel,1);
    
    percentLabel = Label::createWithTTF("0%","Marker Felt.ttf",20);//创建显示百分比的label
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
	return true;
}
void Update::onEnter()
{
    Layer::onEnter();
    
}
void Update::checkUpdate()
{
//    pAssetsManager = new AssetsManager("https://raw.github.com/HimiGame/himigame/master/hello.zip", "http://192.168.1.88:8080/versionInfo.txt", pathToSave.c_str());
    const char* packUrl="http://192.168.1.88:8080/update.zip";
    const char* vUrl="http://192.168.1.88:8080/versionInfo.txt";
    this->pAssetsManager=AssetsManager::create(packUrl, vUrl, pathToSave.c_str(), CC_CALLBACK_1(Update::onError, this), CC_CALLBACK_1(Update::onProgress, this), CC_CALLBACK_0(Update::onSuccess, this));
    this->pAssetsManager->setConnectionTimeout(3);
    this->pAssetsManager->retain();
    
    bool flag=pAssetsManager->checkUpdate();
    if (flag)
    {
        pAssetsManager->update();
    }else
    {
        //Director::getInstance()->runWithScene(LoginScene::createScene());
    }
}

void Update::reset(Ref *pSender)
{
    loadLabel->setString(" ");
    
    // Remove downloaded files
#if (CC_TARGET_PLATFORM != CC_PLATFORM_WIN32)
    std::string command = "rm -r ";
    // Path may include space.
    command += "\"" + pathToSave + "\"";
    system(command.c_str());
#else
    string command = "rd /s /q ";
    // Path may include space.
    command += "\"" + pathToSave + "\"";
    system(command.c_str());
#endif
    // Delete recorded version codes.
    pAssetsManager->deleteVersion();
    
    createDownloadedDir();
}
void Update::createDownloadedDir()
{
    pathToSave = FileUtils::getInstance()->getWritablePath();
    pathToSave += "tmpdir";
    
    // Create the folder if it doesn't exist
#if (CC_TARGET_PLATFORM != CC_PLATFORM_WIN32)
    DIR *pDir = NULL;
    
    pDir = opendir (pathToSave.c_str());
    if (! pDir)
    {
        mkdir(pathToSave.c_str(), S_IRWXU | S_IRWXG | S_IRWXO);
    }
#else
	if ((GetFileAttributesA(pathToSave.c_str())) == INVALID_FILE_ATTRIBUTES)
	{
		CreateDirectoryA(pathToSave.c_str(), 0);
	}
#endif
}
void Update::onError(int errorCode)
{
    if (errorCode == (int)AssetsManager::ErrorCode::NO_NEW_VERSION)
    {
        loadLabel->setString("no new version");
    }
    
    if (errorCode == (int)AssetsManager::ErrorCode::NETWORK)
    {
        loadLabel->setString("network error");
    }
    Manager::getInstance()->switchScence(LoginScene::createScene());
}

void Update::onProgress(int percent)
{
    char progress[20];
    snprintf(progress, 20, "%d%%", percent);
    percentLabel->setString(progress);
    loadProgress->setPercentage(percent);
}
void Update::onSuccess()
{
    loadLabel->setString("download ok");
    percentLabel->setString("");
    Manager::getInstance()->switchScence(LoginScene::createScene());
    
}