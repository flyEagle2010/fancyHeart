//
//  BaseUI.cpp
//  fancyHeart
//
//  Created by 秦亮亮 on 14-6-12.
//
//

#include "BaseUI.h"
bool BaseUI::init(std::string fileName,bool isScence)
{
    if (isScence) {
      //  layout = static_cast<Layout*>(SceneReader::getInstance()->createNodeWithSceneFile(fileName));
    }
    else{
        layout=static_cast<Layout*>(cocostudio::GUIReader::getInstance()->widgetFromJsonFile(fileName.c_str()));
    }
    this->addChild(layout,0);
    return true;
};

void BaseUI::onDlgClose(rapidjson::Value &data)
{
    
}

void BaseUI::onEnter()
{
    Layout::onEnter();
    this->initNetEvent();
    
}

void BaseUI::show(BaseUI* preUI,int effectType)
{
    if(preUI)
    {
        this->preUI=preUI;
        this->preUI->addChild(this);
    }
}

void BaseUI::show(int effectType)
{
    Manager::getInstance()->scene->addChild(this,0);
    Mask::getInstance()->show();
}

void BaseUI::clear(bool isDel)
{
    if(isDel)
    {
        this->removeFromParent();
    }
    else
    {
        this->setVisible(false);
    }
}

void BaseUI::onExit()
{
    if(this->preUI==nullptr){
        Mask::getInstance()->hide();
    }else{
        this->preUI=nullptr;
    }
    Layout::onExit();

}
