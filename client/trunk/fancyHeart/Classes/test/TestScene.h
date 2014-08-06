//
//  TestScene.h
//  fancyHeart
//
//  Created by zhai on 14-6-12.
//
//

#ifndef __fancyHeart__TestScene__
#define __fancyHeart__TestScene__

#include <iostream>
#include "cocos2d.h"
#include "Manager.h"
#include "extensions/ExtensionMacros.h"

USING_NS_CC;
USING_NS_CC_EXT;
class TestScene:public Layer{
public:
//	TestScene();
//	~TestScene();
//	static Scene* createScene();
//	virtual bool init();
//    virtual void draw(Renderer *renderer, const Mat4& transform, bool transformUpdated);
    
    
    TestScene():m_numSp(20),m_loadedSp(0),loadProgress(NULL){};
    void loadingCallback(std::vector<char> *);//加载一张图片完成后跳转的毁掉函数
    static cocos2d::Scene* createScene();
    virtual bool init();
    //virtual void draw(Renderer *renderer, const Mat4 &transform, bool transformUpdated);
	CREATE_FUNC(TestScene);

private:
    Sprite* sprite;
    Vec2 startP;
    Vec2 endP;
    cocos2d::ProgressTimer* loadProgress;//进度条
    void checkUpdate();
    AssetsManager* getAssetsManager();
    std::string pathToSave;
    CCLabelTTF* percentLabel;
    CCLabelTTF* loadLabel;//显示 loading: 的label
    
    int m_numSp;//要加载的精灵数目,初始化为 20 张
    int m_loadedSp;//已加载的精灵数目
};
#endif /* defined(__fancyHeart__TestScene__) */
