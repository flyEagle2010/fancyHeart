//
//  NewPageView.cpp
//  fancyHeart
//
//  Created by zhai on 14-8-22.
//
//

#include "NewPageView.h"
NewPageView* NewPageView::create()
{
    NewPageView* widget = new NewPageView();
    if (widget && widget->init())
    {
        widget->autorelease();
        return widget;
    }
    CC_SAFE_DELETE(widget);
    return nullptr;
}

bool NewPageView::scrollPages(float touchOffset)
{
    if (_pages.size() <= 0)
    {
        return false;
    }
    
//    if (!_leftChild || !_rightChild)
    {
//        return false;
    }
    movePages(touchOffset);
    return true;
}


void NewPageView::handleReleaseLogic(Touch *touch)
{
    if (_pages.size() <= 0)
    {
        return;
    }
    Widget* curPage = _pages.at(_curPageIdx);
    if (curPage)
    {
        Vec2 curPagePos = curPage->getPosition();
        ssize_t pageCount = _pages.size();
        float curPageLocation = curPagePos.x;
        float boundary = 10;
        if (curPageLocation <= -boundary)
        {
            if (_curPageIdx >= pageCount-1)
            {
                _curPageIdx=0;
                scrollToPage(_curPageIdx);
            }
            else
            {
                scrollToPage(_curPageIdx+1);
            }
        }
        else if (curPageLocation >= boundary)
        {
            if (_curPageIdx <= 0)
            {
                 _curPageIdx=pageCount-1;
                scrollToPage(_curPageIdx);
            }
            else
            {
                scrollToPage(_curPageIdx-1);
            }
        }
        else
        {
            scrollToPage(_curPageIdx);
        }
    }
}
