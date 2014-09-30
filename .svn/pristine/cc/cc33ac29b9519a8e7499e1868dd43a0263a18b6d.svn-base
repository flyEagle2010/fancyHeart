#include "LoginScene.h"
#include "external/json/document.h"
#include "extensions/cocos-ext.h"
#include "ui/CocosGUI.h"
#include "cocostudio/CocoStudio.h"
#include "ShaderNode.h"


Scene* LoginScene::createScene()
{
    auto scene = Scene::create();
    auto layer = LoginScene::create();
    scene->addChild(layer);
    return scene;
}

LoginScene* LoginScene::create()
{
    LoginScene* loginScene=new LoginScene();
    if (loginScene && loginScene->init("publish/login/login.ExportJson")) {
        loginScene->autorelease();
        return loginScene;
    }
    CC_SAFE_DELETE(loginScene);
    return nullptr;
}

bool LoginScene::init(std::string fileName)
{
    if(!BaseUI::init(fileName))
    {
        return false;
    }
    ScrollView* scrollView=static_cast<ui::ScrollView*>(layout->getChildByName("scrollView"));
    Widget* lgintBtn=(Widget*)scrollView->getChildByName("loginBtn");
    lgintBtn->setVisible(false);
    Size sSize=Director::getInstance()->getOpenGLView()->getFrameSize();
    Size winsize=Director::getInstance()->getWinSize();
    return true;
}

void LoginScene::onEnter()
{
    BaseUI::onEnter();
    
    this->initGame();
}

void LoginScene::resetUI()
{
    ScrollView* scrollView=static_cast<ui::ScrollView*>(layout->getChildByName("scrollView"));
//    Button* lgintBtn=static_cast<Button*>(scrollView->getChildByName("loginBtn"));
    Widget* lgintBtn=(Widget*)scrollView->getChildByName("loginBtn");
    int num=sData["areaList"].Size();
    Size size=Size(scrollView->getInnerContainerSize().width,(lgintBtn->getContentSize().height+50)*num/2);
    scrollView->setInnerContainerSize(size);
    
    //清除容器
    this->serverItems.clear();
    
    //根据服务器给的数据添加多少个服选项
    for (int i = 0;i<this->sData["areaList"].Size();i++)
    {
        Button* newItem = dynamic_cast<Button*>(lgintBtn->clone());
        //将选服按钮存储起来
        this->serverItems.pushBack(newItem);
        newItem->setTag(i);
        newItem->setTitleText(sData["areaList"][i]["name"].GetString());
        newItem->setTitleColor(Color3B(0, 0, 0));
        newItem->setVisible(true);
        
        scrollView->addChild(newItem);
        
        Size sSize=scrollView->getInnerContainerSize();
        Size itemSize=newItem->getContentSize();
        
        float x=(sSize.width/2-itemSize.width)/2.0+sSize.width/2*(i%2)+80;
        float y=sSize.height-itemSize.height*(i/2+1);
        newItem->setPosition(Vec2(x,y));
        newItem->addTouchEventListener(CC_CALLBACK_2(LoginScene::touchServerEvent,this));
    }
//    this->setSeverItemsMouseEnable(true);

    lgintBtn->removeFromParent();//移除舞台上的单个按钮
    
    
    //如果没记录过账号和密码，将刚刚注册的登录账号和密码记录下来
    //将注册界面隐藏
    auto registerBottom=static_cast<Widget*>(layout->getChildByName("registerBottom"));
    registerBottom->setVisible(false);
    
    //添加一个字符串数据到指定key
    UserDefault::getInstance()->setStringForKey("account",accountInput->getStringValue());
    UserDefault::getInstance()->setStringForKey("password",passwordInput->getStringValue());
    
    //提交,生成xml文件
    UserDefault::getInstance()->flush();

}

//init 游戏服务器 服务器认证
void LoginScene::initGame()
{
    //DeviceInfo d;
    //log("uuid:%s",d.getUUID());
    //std::string str="account="+Value(d.getUUID()).asString()+"&password="+d.getUUID();
    
//    std::string str="account=shuzl&password=123";
//    WebHttp::getInstance()->send(HTTP_URL, CC_CALLBACK_1(LoginScene::initGameCallback, this),str.c_str());
    
    //用以判断是否记录账号和密码,没有则弹出弹窗，填写用户名及密码，有则直接登录进入选择服务器界面
    auto registerBottom=static_cast<Widget*>(layout->getChildByName("registerBottom"));
    auto accountStr =UserDefault::getInstance()->getStringForKey("account");
    auto passwordStr =UserDefault::getInstance()->getStringForKey("password");
    
    registerBottom->setVisible(true);
    Button* sureBtn=(Button*)registerBottom->getChildByName("sureBtn");
    accountInput=static_cast<TextField*>(registerBottom->getChildByName("accountInput"));
    passwordInput=static_cast<TextField*>(registerBottom->getChildByName("passwordInput"));
    sureBtn->setTouchEnabled(true);
    sureBtn->addTouchEventListener(CC_CALLBACK_2(LoginScene::touchEvent,this));
    
    if (accountStr != "" && passwordStr != "") {
        accountInput->setText(UserDefault::getInstance()->getStringForKey("account"));
        passwordInput->setText(UserDefault::getInstance()->getStringForKey("password"));
    }else{
        accountInput->setText("");
        passwordInput->setText("");
    }
}

void LoginScene::webSend(std::string str)
{
    WebHttp::getInstance()->send(HTTP_URL, CC_CALLBACK_1(LoginScene::initGameCallback, this),str.c_str());
}

void LoginScene::initGameCallback(std::vector<char> *data)
{
    std::string str(data->begin(),data->end());
    sData.Parse<0>(str.c_str());
    log("loginData:%s",str.c_str());
    if (sData["loginState"].GetInt()!=0) {//先用以后修改
        log("登录认证失败！");
    }else if(sData["areaList"].Size()<1){
        log("服务列表数据长度不够");
    }else{
        this->resetUI();
    }
}
//设置是否可以点击选择服务器按钮
void LoginScene::setSeverItemsMouseEnable(bool isCan)
{
    for (int i = 0; i<this->serverItems.size(); ++i) {
        this->serverItems.at(i)->setTouchEnabled(isCan);
    }
}

void LoginScene::onDlgClose(rapidjson::Value &data)
{
    BaseUI::onDlgClose(data);
    std::string btnName=data.GetString();
    log("you click confirm %s",btnName.c_str());
}

void LoginScene::touchEvent(Ref *pSender, Widget::TouchEventType type)
{
    auto button=static_cast<Button*>(pSender);
    if(type!=TouchEventType::ENDED){
        return;
    }
    switch (button->getTag()) {
        case 11203://注册确定按钮
            if (accountInput->getStringValue() != ""  && passwordInput->getStringValue() != "") {
                std::string str="account="+this->accountInput->getStringValue()+"&password="+this->passwordInput->getStringValue();//"account=shuzl&password=123";
                this->webSend(str);
            }
            break;
        default:
            break;
    }
}

void LoginScene::touchServerEvent(Ref *pSender, Widget::TouchEventType type)
{
    auto button=static_cast<Button*>(pSender);
    if(type!=TouchEventType::ENDED){
        return;
    }
    Manager::getInstance()->socket=new Socket();
    string ip=this->sData["areaList"][button->getTag()]["ip"].GetString();
    int port=this->sData["areaList"][button->getTag()]["port"].GetInt();
    Manager::getInstance()->socket->init(ip, port);
    this->setSeverItemsMouseEnable(false);
}

void LoginScene::initNetEvent(){
    auto listener = EventListenerCustom::create(NET_MESSAGE, [=](EventCustom* event){
        NetMsg* msg = static_cast<NetMsg*>(event->getUserData());
        switch (msg->msgId)
        {
            case CONNECT_ERROR:
            {
                
#if COCOS2D_DEBUG
                auto scene=HomeScene::createScene();
                Manager::getInstance()->switchScence(scene);
                Manager::getInstance()->showMsg("进入单机模式，本地数据启动");
                
                this->setSeverItemsMouseEnable(true);
#endif
                break;
            }
            case CONNECTED:
            {
                Loading::getInstance()->hide();

                LoginReq pbLogin;
                pbLogin.set_account(sData["userBean"]["name"].GetString());
                pbLogin.set_key(sData["key"].GetString());
                pbLogin.set_areaid(sData["areaList"][2]["id"].GetInt());
                Manager::getInstance()->socket->send(C_LOGIN, &pbLogin);
                break;
            }
            case C_LOGIN:
            {
                 Manager::getInstance()->switchScence(HomeScene::createScene());
                break;
            }
            default:
                break;
        }
    });
    Director::getInstance()->getEventDispatcher()->addEventListenerWithSceneGraphPriority(listener, this);
    
}
void LoginScene::onExit()
{
    BaseUI::onExit();
}

