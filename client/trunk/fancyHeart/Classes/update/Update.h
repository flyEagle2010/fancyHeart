//
//  Update.h
//  fancyHeart
//
//  Created by zhai on 14-6-17.
//
//

#ifndef __fancyHeart__Update__
#define __fancyHeart__Update__

#include <iostream>
#include "cocos2d.h"
#include "Manager.h"
#include "extensions/assets-manager/AssetsManager.h"
#include <dirent.h>
#include <sys/stat.h>

#include "extensions/cocos-ext.h"
USING_NS_CC;
USING_NS_CC_EXT;
class Update:public Layer,public cocos2d::extension::AssetsManagerDelegateProtocol{
public:
	Update();
	~Update();
	static Scene* createScene();
	virtual bool init();
	CREATE_FUNC(Update);
    void onEnter();
    void reset(Ref *pSender);
    void update(Ref *pSender);
    
    virtual void onError(int errorCode);
    virtual void onProgress(int percent);
    virtual void onSuccess();


private:
    AssetsManager* getAssetsManager();
    void createDownloadedDir();
    AssetsManager *pAssetsManager=nullptr;
    void checkUpdate();
    std::string pathToSave;
    
    cocos2d::ProgressTimer* loadProgress;//进度条
    Label* percentLabel;
    Label* loadLabel;//显示 loading: 的label
};
#endif /* defined(__fancyHeart__Update__) */
