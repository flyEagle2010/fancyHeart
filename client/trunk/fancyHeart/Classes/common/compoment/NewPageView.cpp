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
    
    if (!_leftChild || !_rightChild)
    {
        return false;
    }
    movePages(touchOffset);
    return true;
}


void NewPageView::handleReleaseLogic(const Vec2 &touchPoint)
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
                scrollPages(-curPageLocation);
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
                scrollPages(-curPageLocation);
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
