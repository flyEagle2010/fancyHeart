//
//  Bag.h
//  fancyHeart
//
//  Created by doteyplay on 14-8-5.
//
//

#ifndef __fancyHeart__Bag__
#define __fancyHeart__Bag__

#include <iostream>
#include "cocos2d.h"
#include "Manager.h"
#include "BaseUI.h"
#include "XItem.h"
#include "TabBar.h"
//using namespace ui;
using namespace cocos2d;
using namespace cocostudio;

class Bag:public BaseUI
{
public:
    static Bag* create();
	virtual bool init(std::string fileName);
	virtual void onEnter();
    virtual void onExit();
    void sendInfo(int selectNumber);
    void scrollEvent(Ref* pSender, ui::ScrollView::EventType type);
    std::vector<PItem*> getItemByType(int type);
    

private:
	void initNetEvent();
    void touchEvent(cocos2d::Ref *pSender, TouchEventType type);
    void itemTouchEvent(Ref *pSender, TouchEventType type);
    void touchButtonEvent(Ref *pSender, TouchEventType type);
    void getTypeInfo(int type);

private: //私有属性
    Vector<Widget*> propItems;//道具数组
    std::vector<PItem*> itemList;//符合要求的道具数组
    Size itemSize;//size大小
    Size sSize;//容器尺寸
    const int space=12;//间隙
    int currentCol;
    bool isScolling=false;
    float currentOffY;
    TabBar* tabBar;
    ui::ScrollView* scrollView;
    void setItem(PItem* item,int index);//设置单项并且赋予其显示数据
    Widget* propItem;//单个模版
    void setProperty(PItem* item,bool isHaveData);//设置被选中的道具的具体显示信息
    int currentTag;//存储当前被显示道具的道具的tag
    int currentId;//存储当前被显示道具的id
    int currentType;//当前选中的tab列表中的type
    bool isShow;//左边界面是否需要显示
    bool isNotAllSell;//道具是否被全部卖出
    void setRightPosition();//设置右边素材得位置
    Widget* rightBg;//右边界面
    Widget* leftBg;//左边界面
    Widget* top;//上面界面
    Widget* qualityPanel;
    Widget* sellPanel;
    Button* lookBtn;
    Button* sellBtn;
    bool isQuality;//是否显示的是属性界面
    int selectNumber;//当前选择数量
    void setData(XItem*xItem);
    void bgChange(bool isQualityShow);
    void setItemPosition(ImageView* newItem,int index);//
    std::vector<const cocos2d::Color3B> colorsLabel={Color3B::WHITE,Color3B::GREEN,Color3B::BLUE,Color3B::MAGENTA,Color3B::YELLOW};//颜色：白绿蓝紫金

};
#endif /* defined(__fancyHeart__Bag__) */
