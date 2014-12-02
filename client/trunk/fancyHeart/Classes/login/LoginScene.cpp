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
    this->selectRoleId = 999;
    this->choosePanel = static_cast<Widget*>(layout->getChildByName("choosePanel"));
    ScrollView* scrollView=static_cast<ui::ScrollView*>(this->choosePanel->getChildByName("scrollView"));
    Widget* lgintBtn=(Widget*)scrollView->getChildByName("loginBtn");
    this->choosePanel->setVisible(false);
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
    this->firstPanel->setVisible(false);
    ScrollView* scrollView=static_cast<ui::ScrollView*>(this->choosePanel->getChildByName("scrollView"));
    ScrollView* leftScrollView = static_cast<ScrollView*>(this->choosePanel->getChildByName("leftScrollView"));
    Button* lgintBtn=(Button*)scrollView->getChildByName("loginBtn");
    Button* serverBtn=(Button*)leftScrollView->getChildByName("serverBtn");
    Size size=Size(scrollView->getInnerContainerSize().width,(lgintBtn->getContentSize().height+50)*sData["areaList"].Size()/2);
    scrollView->setInnerContainerSize(size);
    Size sizeSever = Size(leftScrollView->getInnerContainerSize().width,(serverBtn->getContentSize().height+15)*6);//数量先写死，以后根据服务器给的数据数量添加
    leftScrollView->setInnerContainerSize(sizeSever);
    
    //清除容器
    this->serverItemLists.clear();
    this->serverList.clear();
    
    std::vector<Button*> buttons;
    //添加左边服务器列表
    int lg = ceil(this->sData["areaList"].Size()/8.0f);
    for(int k = 0;k<lg;k++){
        Button* itemBtn = dynamic_cast<Button*>(serverBtn->clone());
        //将选服按钮存储起来
        this->serverList.pushBack(itemBtn);
        itemBtn->setTag(k);
        int num = this->sData["areaList"].Size() - k*8>8?8:this->sData["areaList"].Size() - k*8;
        string str = num/10>0? Value(num).asString():"0"+Value(num).asString();
        itemBtn->setTitleText("001—0"+str+"区");
        buttons.push_back(itemBtn);
//        itemBtn->setTitleColor(Color3B(0, 0, 0));//颜色设置为黑色
        leftScrollView->addChild(itemBtn);
        
        Size sSize1=leftScrollView->getInnerContainerSize();
        Size itemSize1=itemBtn->getContentSize();
        float x=(sSize1.width-itemSize1.width)/2.0+itemSize1.width/2;
        float y=sSize1.height-((itemSize1.height)/2)*(k+1)-40*k;
        itemBtn->setPosition(Vec2(x,y));
        itemBtn->addTouchEventListener(CC_CALLBACK_2(LoginScene::touchServerListEvent,this));
    }
    tabBar=TabBar::create(buttons);
    tabBar->retain();
    tabBar->setIndex(0);//暂时先这么写，到时候记录看上一次选了哪个区，再打开的时候默认停留在那一区
    
    //根据服务器给的数据添加左边服务器下多个分区服务器
    for (int i = 0;i<this->sData["areaList"].Size();i++)
    {
        Button* newItem = dynamic_cast<Button*>(lgintBtn->clone());
        //将选服按钮存储起来
        this->serverItemLists.pushBack(newItem);
        newItem->setTag(i);
        newItem->setTitleText(sData["areaList"][i]["name"].GetString());
//        newItem->setTitleColor(Color3B(0, 0, 0));//颜色设置为黑色
        //newItem->setAnchorPoint(Vec2(0,0));
//        newItem->setVisible(true);//
        
        scrollView->addChild(newItem);
        Size sSize=scrollView->getInnerContainerSize();
        Size itemSize=newItem->getContentSize();
        float x=(sSize.width/2-itemSize.width)/2.0+sSize.width/2*(i%2)+113;
        float y=sSize.height-(itemSize.height+22)*(i/2+1);
        newItem->setPosition(Vec2(x,y));
        newItem->addTouchEventListener(CC_CALLBACK_2(LoginScene::touchServerEvent,this));
    }
    this->setSeverItemsMouseEnable(true);
    this->choosePanel->setVisible(true);
    lgintBtn->removeFromParent();//移除舞台上的单个按钮
    serverBtn->removeFromParent();
    
    //将注册界面隐藏
    this->registerBottom->setVisible(false);
    //看是否上次登录的服务器名称被记录，如果记录则显示上次被登录的服务器名称。没记录则显示最新服务器名称（没记录的话先显示内网）
    if (UserDefault::getInstance()->getStringForKey("serverName") == "") {
        this->takeNewServerMsg();
    }
    static_cast<Text*>(this->choosePanel->getChildByName("preLogin"))->setText(UserDefault::getInstance()->getStringForKey("serverName"));
    
    //认证成功后添加一个字符串数据到指定key
    UserDefault::getInstance()->setStringForKey("account",accountInput->getStringValue());
    UserDefault::getInstance()->setStringForKey("password",passwordInput->getStringValue());
    //提交,生成xml文件
    UserDefault::getInstance()->flush();
    
}

//记录最新的服务器信息
void LoginScene::takeNewServerMsg()
{
//    this->butTag = 3;//如果上次没有选择服务器，则现在先默认显示内网
    //登录成功后记录上次登录选择的服务器名称,ip和port
    string serverName = this->sData["areaList"][this->butTag]["name"].GetString();
    UserDefault::getInstance()->setStringForKey("serverName",serverName);
    string ips = this->sData["areaList"][this->butTag]["ip"].GetString();
    UserDefault::getInstance()->setStringForKey("ip",ips);
    string ports = Value(this->sData["areaList"][this->butTag]["port"].GetInt()).asString();
    UserDefault::getInstance()->setStringForKey("port",ports);
    //提交,生成xml文件
    UserDefault::getInstance()->flush();
}

//init 游戏服务器 服务器认证
void LoginScene::initGame()
{
    this->firstPanel = static_cast<Widget*>(layout->getChildByName("firstPanel"));
    this->createRole = static_cast<Widget*>(layout->getChildByName("createRole"));
    this->txtInput=static_cast<TextField*>(this->createRole->getChildByName("txt_input"));
    static_cast<Button*>(this->firstPanel->getChildByName("loginBtn"))->addTouchEventListener(CC_CALLBACK_2(LoginScene::touchEvent,this));
    Widget* panel = static_cast<Widget*>(this->firstPanel->getChildByName("panel"));
    static_cast<Button*>(panel->getChildByName("selectBtn"))->addTouchEventListener(CC_CALLBACK_2(LoginScene::touchEvent,this));
    static_cast<Button*>(this->firstPanel->getChildByName("enterBtn"))->addTouchEventListener(CC_CALLBACK_2(LoginScene::touchEvent,this));
    Text* serverName = static_cast<Text*>(panel->getChildByName("serverName"));
    static_cast<Button*>(this->createRole->getChildByName("enterBtn"))->addTouchEventListener(CC_CALLBACK_2(LoginScene::touchEvent,this));
    static_cast<Widget*>(this->createRole->getChildByName("manImg"))->addTouchEventListener(CC_CALLBACK_2(LoginScene::touchEvent,this));
    static_cast<Widget*>(this->createRole->getChildByName("womanImg"))->addTouchEventListener(CC_CALLBACK_2(LoginScene::touchEvent,this));
    static_cast<Button*>(this->createRole->getChildByName("nameSelectBtn"))->addTouchEventListener(CC_CALLBACK_2(LoginScene::touchEvent,this));
    this->firstPanel->setVisible(true);//true
    this->createRole->setVisible(false);
    
    //用以判断是否记录账号和密码,没有则弹出弹窗，填写用户名及密码，有则直接登录进入选择服务器界面
    this->registerBottom=static_cast<Widget*>(layout->getChildByName("registerBottom"));
    Button* closeBtn = static_cast<Button*>(this->registerBottom->getChildByName("closeBtn"));
    Text* desLabel = static_cast<Text*>(this->registerBottom->getChildByName("desLabel"));
    
    this->registerBottom->setVisible(false);//false
    Button* sureBtn=(Button*)this->registerBottom->getChildByName("sureBtn");
    accountInput=static_cast<TextField*>(this->registerBottom->getChildByName("accountInput"));
    passwordInput=static_cast<TextField*>(this->registerBottom->getChildByName("passwordInput"));
    sureBtn->setTouchEnabled(true);
    sureBtn->addTouchEventListener(CC_CALLBACK_2(LoginScene::touchEvent,this));
    closeBtn->addTouchEventListener(CC_CALLBACK_2(LoginScene::touchEvent,this));
    Label* desLabel1 = static_cast<Label*>(desLabel->getVirtualRenderer());
    desLabel->enableOutline(Color4B::BLACK,1);
    
    auto accountStr =UserDefault::getInstance()->getStringForKey("account");
    auto passwordStr =UserDefault::getInstance()->getStringForKey("password");
    if (accountStr != "" && passwordStr != "") {
        accountInput->setText(UserDefault::getInstance()->getStringForKey("account"));
        passwordInput->setText(UserDefault::getInstance()->getStringForKey("password"));
    }else{
        accountInput->setText("");
        passwordInput->setText("");
    }
    //看是否上次登录的服务器名称被记录，如果记录则显示上次被登录的服务器名称，没有则显示最新服务器
    if(UserDefault::getInstance()->getStringForKey("serverName") == ""){
        this->butTag = 3;//如果上次没有选择服务器，则现在先默认显示内网，此处先写死，以后得改
        this->takeNewServerMsg();
    }
    serverName->setText(UserDefault::getInstance()->getStringForKey("serverName"));
    
    //设备号登录：首先看是否有记录的设备id，没有则向服务器发设备id(信息成功返回后记录下来设备号),有则直接取设备号给服务器
    string equipNumStr =UserDefault::getInstance()->getStringForKey("equipmentNumber");
    this->equipmentNumber = equipNumStr != ""?UserDefault::getInstance()->getDoubleForKey("equipmentNumber"):Utils::getTime();
    std::string numStr="UDID="+Value(StringUtils::format("%ld",this->equipmentNumber)).asString()+"";
//    std::string numStr2=StringUtils::format("UDID='%ld'",this->equipmentNumber);
    this->webSend(numStr,true);
}

void LoginScene::webSend(std::string str,bool isQuick)
{
    if (isQuick) {
        WebHttp::getInstance()->send(HTTP_QUICK_LOGIN_URL, CC_CALLBACK_1(LoginScene::quickLoginBack, this),str.c_str());
    }else{
        WebHttp::getInstance()->send(HTTP_URL, CC_CALLBACK_1(LoginScene::initGameCallback, this),str.c_str());
    }
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

void LoginScene::quickLoginBack(std::vector<char> *data)
{
    std::string str(data->begin(),data->end());
    sData.Parse<0>(str.c_str());
    log("loginData:%s",str.c_str());
    
    string equipNumStr =UserDefault::getInstance()->getStringForKey("equipmentNumber");
    if (equipNumStr == "") {
        UserDefault::getInstance()->setFloatForKey("equipmentNumber",this->equipmentNumber);
        //提交,生成xml文件
        UserDefault::getInstance()->flush();
    }
}

void LoginScene::bindBack(std::vector<char> *data)
{
    std::string str(data->begin(),data->end());
    rapidjson::Document bindData;
    bindData.Parse<0>(str.c_str());
    int loginState = bindData["loginState"].GetInt();
    if (loginState == 0) {
        Manager::getInstance()->showMsg("绑定成功！");
    }else if (loginState == 1){
        Manager::getInstance()->showMsg("账号不能为空！");
    }else if (loginState == 2){
        Manager::getInstance()->showMsg("账号受限！");
    }else if (loginState == 3){
        Manager::getInstance()->showMsg("密码错误！");
    }else if (loginState == 4){
        Manager::getInstance()->showMsg("账号不存在！");
    }else if (loginState == 5){
        Manager::getInstance()->showMsg("请换个用户名！");
    }
}

//设置是否可以点击选择服务器按钮
void LoginScene::setSeverItemsMouseEnable(bool isCan)
{
    for (int i = 0; i<this->serverItemLists.size(); ++i) {
        this->serverItemLists.at(i)->setTouchEnabled(isCan);
    }
    for(int j=0;j<this->serverList.size();j++){
        this->serverList.at(j)->setTouchEnabled(isCan);
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
        case 32556://点击选区按钮
            if (button->getTag() == 11203) {
                this->isEnter = true;
            }else if(button->getTag() == 32556){
                this->isEnter = false;
            }
            if (accountInput->getStringValue() != ""  && passwordInput->getStringValue() != "") {
                std::string str="account="+this->accountInput->getStringValue()+"&password="+this->passwordInput->getStringValue();//"account=shuzl&password=123";
                this->webSend(str,false);
            }
//        {
//            //绑定帐号和密码接口(这个不写在这里,以后要单独一个界面用得接口)
//            long equipNum = UserDefault::getInstance()->getDoubleForKey("equipmentNumber");
//            std::string numStr="UDID="+Value(StringUtils::format("%ld",this->equipmentNumber)).asString();
//            std::string str = "&account="+this->accountInput->getStringValue()+"&password="+this->passwordInput->getStringValue();
//            WebHttp::getInstance()->send(HTTP_BIND_URL, CC_CALLBACK_1(LoginScene::bindBack, this),(numStr+str).c_str());
//        }
            break;
        case 32138://关闭按钮
            this->firstPanel->setVisible(true);
            this->registerBottom->setVisible(false);
            break;
        case 32552://登录首页中的账号登录按钮
            this->firstPanel->setVisible(false);
            this->registerBottom->setVisible(true);
            break;
        case 32554://进入游戏按钮
        {
            Manager::getInstance()->socket=new Socket();
            string ip1=UserDefault::getInstance()->getStringForKey("ip");
            string port1=UserDefault::getInstance()->getStringForKey("port");
            Manager::getInstance()->socket->init(ip1, Value(port1).asInt());
            this->setSeverItemsMouseEnable(false);
        }
            break;
        case 33162://随机选名字按钮
        {
            this->nameIndex ++;
            this->nameIndex = this->nameIndex<this->nameList.size()?this->nameIndex:0;
            this->txtInput->setText(this->nameList.at(this->nameIndex));
            break;
        }
        case 33163://进入游戏按钮
        {
            if (this->txtInput->getStringValue() == "") {
                Manager::getInstance()->showMsg("名字不得为空！");
                return;
            }
            CreateRoleReq pCreateRoleReq;
            pCreateRoleReq.set_rolename(this->txtInput->getStringValue());
            //女主角静态id：998，男主角静态id：999
            pCreateRoleReq.set_spriteid(this->selectRoleId);//主角的静态id
            Manager::getInstance()->socket->send(C_CREATE_ROLE, &pCreateRoleReq);
            break;
        }
        case 99900://选中男性角色
        case 99800://选中女性角色
        {
            this->selectRoleId = button->getTag()/100;
            break;
        }
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
    this->butTag = button->getTag();
    if(!this->isEnter){//从选区进去点击服务器后，返回游戏首页面
        this->takeNewServerMsg();
        Widget* panel = static_cast<Widget*>(this->firstPanel->getChildByName("panel"));
        static_cast<Text*>(panel->getChildByName("serverName"))->setText(UserDefault::getInstance()->getStringForKey("serverName"));
        this->firstPanel->setVisible(true);
        this->choosePanel->setVisible(false);
    }else{//账号登录进去点击服务器后得响应
        Manager::getInstance()->socket=new Socket();
        string ip=this->sData["areaList"][button->getTag()]["ip"].GetString();
        int port=this->sData["areaList"][button->getTag()]["port"].GetInt();
        Manager::getInstance()->socket->init(ip, port);
        this->setSeverItemsMouseEnable(false);
    }
}

void LoginScene::touchServerListEvent(Ref *pSender, Widget::TouchEventType type)
{
    auto button=static_cast<Button*>(pSender);
    if(type!=TouchEventType::ENDED){
        return;
    }
    ScrollView* scrollView=static_cast<ui::ScrollView*>(this->choosePanel->getChildByName("scrollView"));
    scrollView->setVisible(button->getTag() == 0?true:false);
    tabBar->setIndex(button->getTag());
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
                pbLogin.set_uuid(sData["userBean"]["uuId"].GetString());
                pbLogin.set_key(sData["key"].GetString());
                pbLogin.set_areaid(sData["areaList"][2]["id"].GetInt());
                Manager::getInstance()->socket->send(C_LOGIN, &pbLogin);
                break;
            }
            case C_LOGIN:
            {
                LoginResp* roleData=new LoginResp();
                roleData->ParseFromArray(msg->bytes, msg->len);
                if (roleData->result() == 2) {//没有创建角色
                    this->createRole->setVisible(true);
                    this->registerBottom->setVisible(false);//测试加
                    this->choosePanel->setVisible(false);//测试加
                    this->firstPanel->setVisible(false);
                }else if(roleData->result() == 3){//3用户名重复
                    Manager::getInstance()->showMsg("用户名重复!");
                }else if(roleData->result() == 1){//认证失败
                    Manager::getInstance()->showMsg("认证失败!");
                }else if(roleData->result() == 0){//成功
                    Manager::getInstance()->switchScence(HomeScene::createScene());
                }
                //将服务器给的可选择的随机名字列表记录下来
                if (roleData->result() == 2 || roleData->result() == 3) {
                    this->nameIndex = 0;
                    this->nameList.clear();
                    for (int i=0; i<roleData->randomname_size(); i++) {
                        this->nameList.push_back(roleData->randomname(i));
                    }
                    this->txtInput->setText(this->nameList.at(this->nameIndex));
                }
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

