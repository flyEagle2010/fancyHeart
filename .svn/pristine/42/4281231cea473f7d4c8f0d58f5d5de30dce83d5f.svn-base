#ifndef __fancyHeart__LoginScene__
#define __fancyHeart__LoginScene__

#include "cocos2d.h"
#include "ui/UIButton.h"
#include "ui/UIText.h"
#include "Manager.h"
#include "WebHttp.h"
#include "Confirm.h"
#include "HomeScene.h"
#include "Loading.h"
USING_NS_CC;
using namespace ui;
using namespace cocostudio;
class LoginScene : public BaseUI
{
public:

    static cocos2d::Scene* createScene();
    static LoginScene* create();
    virtual bool init(std::string fileName);
    virtual void onEnter();
    virtual void onExit();
    virtual void resetUI();
    virtual void onDlgClose(rapidjson::Value &data);
    void webSend(std::string str,bool isQuick);
private:
    rapidjson::Document sData;
    void initGame();
    void initGameCallback(std::vector<char> *data);
    void initNetEvent();
    void touchEvent(Ref *pSender, Widget::TouchEventType type);
    void touchServerEvent(Ref *pSender, Widget::TouchEventType type);
    void touchServerListEvent(Ref *pSender, Widget::TouchEventType type);
    Vector<Button*> serverItemLists;//各服务器单项下的列表按钮
    Vector<Button*> serverList;//各服务器按钮
    void setSeverItemsMouseEnable(bool isCan);//设置是否可以点击选择服务器按钮
    TextField* accountInput;//账号输入框
    TextField* passwordInput;//密码输入框
    bool isremember;//记录账号和密码是否被记录过
    TabBar* tabBar;
    Widget* firstPanel;
    Widget* registerBottom;
    Widget* createRole;
    Widget* choosePanel;
    TextField* txtInput;
    int selectRoleId;//创建角色时所选择的角色静态id
    std::vector<string> nameList;//名字数组
    int nameIndex;//读到的名字序数
    long equipmentNumber;//设备号码
    void quickLoginBack(std::vector<char> *data);
    void bindBack(std::vector<char> *data);
    int butTag;//被点击的服务器按钮的tag
    void takeNewServerMsg();
    bool isEnter;
};

#endif // __HELLOWORLD_SCENE_H__
