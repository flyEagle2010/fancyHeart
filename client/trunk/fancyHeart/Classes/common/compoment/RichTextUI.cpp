#include "RichTextUI.h"

NS_CC_BEGIN

namespace ui 
{    
	static int _calcCharCount(const char * pszText)
	{
		int n = 0;
		char ch = 0;
		while ((ch = *pszText))
		{
			CC_BREAK_IF(!ch);
			if (0x80 != (0xC0 & ch))
			{
				++n;
			}
			++pszText;
		}
		return n;
	}
 
	/*----------------------------------------------------------------------------------------------------------------------------------
	-----------------------------------------------------------------------------------------------------------------------------------*/
	RichTextUI::RichTextUI(): _formatTextDirty(true), _leftSpaceWidth(0.0f), _maxline(200), _lineSpace(2), _showTag(0),
		_richTextEventListener(nullptr),
		_richTextEventSelector(nullptr)
	{
		_context = "";
	}
    
	RichTextUI::~RichTextUI()
	{
		_richElements.clear();
		_richTextEventListener =  nullptr;
		_richTextEventSelector = nullptr;
	}

	RichTextUI* RichTextUI::create()
	{
		RichTextUI* widget = new RichTextUI();
		if (widget && widget->init())
		{
			widget->setDirection(Direction::VERTICAL);
			widget->setInertiaScrollEnabled(true);  
			widget->autorelease();
			return widget;
		}
		CC_SAFE_DELETE(widget);
		return NULL;
	}
    
	bool RichTextUI::init()
	{
		if (ScrollView::init())
		{
			return true;
		}
		return false;
	}
    
	void RichTextUI::initRenderer()
	{
		ScrollView::initRenderer();
	}

	void RichTextUI::insertNewLine()
	{
		RichItemNewLine* item = RichItemNewLine::create(0);
		insertElement(item);
	}

	void RichTextUI::clearAll()
	{
		_richElements.clear();
		_formatTextDirty = true;
	}

	void RichTextUI::showItemByTag(short tag)
	{
		if (_showTag != tag)
		{
			_showTag = tag;
			_formatTextDirty = true;
		}
	}

	void RichTextUI::insertElement(RichItem *element)
	{
		_richElements.pushBack(element);
		_formatTextDirty = true;
	}
    
	void RichTextUI::formatText()
	{
		if (_formatTextDirty)
		{
			removeAllChildren();
			_elementRenders.clear();

			changeLine();
			for (ssize_t i = 0; i < _richElements.size(); i++)
			{
				RichItem* element = static_cast<RichItem*>(_richElements.at(i));
				if (_showTag == 0 || element->_tag == 0 || element->_tag == _showTag)
				{
					switch (element->_type)
					{
					case RICH_ITEM_NEWLINE:
						{
							changeLine();					
						}
						break;
					case RICH_ITEM_TEXT:
						{
							RichItemText* elmtText = static_cast<RichItemText*>(element);
							handleTextRenderer(elmtText, elmtText->_text.c_str());						
						}
						break;
					case RICH_ITEM_IMAGE:
						{
							RichItemImage* elmtImage = static_cast<RichItemImage*>(element);
							handleImageRenderer(elmtImage->_filePath.c_str(), elmtImage->_color, elmtImage->_opacity);					
						}
						break;
					case RICH_ITEM_CUSTOM:
						{
							RichItemCustom* elmtCustom = static_cast<RichItemCustom*>(element);
							handleCustomRenderer(elmtCustom->_customNode);
						}
						break;
					default:
						break;
					}
				}				
			}
			formarRenderers();
			_formatTextDirty = false;
			jumpToBottom();
		}
	}

	void RichTextUI::addEventListenerRichText(Ref* target, SEL_RichTextClickEvent selector)
	{
		_richTextEventListener = target;
		_richTextEventSelector = selector;
	}

	void RichTextUI::labelClicked(LinkLable* lab)
	{
		RichItemText* itemText = static_cast<RichItemText*>(lab->getUserObject());
		if (itemText)
		{
			_context = itemText->getContext();
		}
		if (_richTextEventListener && _richTextEventSelector)
		{
			(_richTextEventListener->*_richTextEventSelector)(this, RICHTEXT_ANCHOR_CLICKED);
		}
	}

	LinkLable* RichTextUI::createLable(RichItemText* item, const char* text)
	{
		LinkLable* textRenderer = LinkLable::create(text, item->_fontName, item->_fontSize);
		if (textRenderer)
		{
			if (item->_outlinesize > 0)
			{
				textRenderer->enableOutline(item->_outcolor, item->_outlinesize);			
			}	
			if (item->_linksize > 0)
			{
				textRenderer->enableLinkLine(item->_linkcolor, item->_linksize);			
			}	
		}
		textRenderer->setLableDelegate(this);
		return textRenderer;
	}
    
	void RichTextUI::handleTextRenderer(RichItemText* item, const char* text)
	{
		LinkLable* textRenderer = createLable(item, text);		
		float textRendererWidth = textRenderer->getContentSize().width;
		_leftSpaceWidth -= textRendererWidth;
		if (_leftSpaceWidth < 0.0f)
		{
			float overstepPercent = (-_leftSpaceWidth) / textRendererWidth;
			std::string curText = text;
			size_t stringLength = _calcCharCount(text);
			int leftLength = stringLength * (1.0f - overstepPercent);
			std::string leftWords = curText.substr(0, leftLength);
			std::string cutWords = curText.substr(leftLength, curText.length()-1);
			if (leftLength > 0)
			{
				LinkLable* leftRenderer = createLable(item, leftWords.substr(0, leftLength).c_str());				
				if (leftRenderer)
				{
					leftRenderer->setColor(item->_color);
					leftRenderer->setOpacity(item->_opacity);
					leftRenderer->setUserObject(item);
					pushToContainer(leftRenderer);
				}
			}
			changeLine();
			handleTextRenderer(item, cutWords.c_str());
		}
		else
		{
			textRenderer->setColor(item->_color);
			textRenderer->setOpacity(item->_opacity);
			textRenderer->setUserObject(item);
			pushToContainer(textRenderer);
		}
	}

	void RichTextUI::handleImageRenderer(const char *fileParh, const Color3B &color, GLubyte opacity)
	{
		Sprite* imageRenderer = Sprite::create(fileParh);
		handleCustomRenderer(imageRenderer);
	}

	void RichTextUI::handleCustomRenderer(cocos2d::Node *renderer)
	{
		Size imgSize = renderer->getContentSize();
		_leftSpaceWidth -= imgSize.width;
		if (_leftSpaceWidth < 0.0f)
		{
			changeLine();
			pushToContainer(renderer);
			_leftSpaceWidth -= imgSize.width;
		}
		else
		{
			pushToContainer(renderer);
		}
	}

	void RichTextUI::changeLine()
	{
		_leftSpaceWidth = _customSize.width;
		_elementRenders.push_back(new Vector<Node*>());
	}

	void RichTextUI::formarRenderers()
	{		
		while (_elementRenders.size() > _maxline)
		{
			_elementRenders.erase(_elementRenders.begin());
		}
		float newContentSizeHeight = 0.0f;
		float *maxHeights = new float[_elementRenders.size()];

		for (size_t i = 0; i < _elementRenders.size(); i++)
		{
			Vector<Node*>* row = (_elementRenders[i]);
			float maxHeight = 0.0f;
			for (ssize_t j = 0; j < row->size(); j++)
			{
				Node* l = row->at(j);
				maxHeight = MAX(l->getContentSize().height, maxHeight);
			}
			maxHeights[i] = maxHeight + _lineSpace;
			newContentSizeHeight += maxHeights[i];
		}

		float nextPosY = newContentSizeHeight;
		for (size_t i = 0; i < _elementRenders.size(); i++)
		{
			Vector<Node*>* row = (_elementRenders[i]);
			float nextPosX = 0.0f;
			nextPosY -= maxHeights[i];

			for (ssize_t j=0; j < row->size(); j++)
			{
				Node* l = row->at(j);
				l->setAnchorPoint(Point::ZERO);
				l->setPosition(Point(nextPosX, nextPosY));
				addChild(l, 1, (int)(i * 10 + j));	
				LinkLable* la = dynamic_cast<LinkLable*>(l);
				if (la && la->getLinkline())
				{
					la->getLinkline()->setPosition(nextPosX, nextPosY - 1);
					addChild(la->getLinkline(), 1, (int)(i * 10 + j));	
				}				
				nextPosX += l->getContentSize().width;
			}
		}
		delete [] maxHeights;

		size_t length = _elementRenders.size();
		for (size_t i = 0; i<length; i++)
		{
			Vector<Node*>* l = _elementRenders[i];
			l->clear();
			delete l;
		}
		_elementRenders.clear();
		if (newContentSizeHeight < _customSize.height)
		{
			newContentSizeHeight = _customSize.height;
		}		
		setInnerContainerSize(Size(_customSize.width, newContentSizeHeight));
	}

	void RichTextUI::pushToContainer(cocos2d::Node *renderer)
	{
		if (_elementRenders.size() <= 0)
		{
			return;
		}
		_elementRenders[_elementRenders.size()-1]->pushBack(renderer);
	}

	void RichTextUI::visit(cocos2d::Renderer *renderer, const kmMat4 &parentTransform, bool parentTransformUpdated)
	{
		if (_enabled)
		{
			formatText();
			ScrollView::visit(renderer, parentTransform, parentTransformUpdated);
		}
	}

	void RichTextUI::setAnchorPoint(const Point &pt)
	{
		ScrollView::setAnchorPoint(pt);
	}

	std::string RichTextUI::getDescription() const
	{
		return "RichTextUI";
	}
}

NS_CC_END
