#ifndef __RICHITEM_H__
#define __RICHITEM_H__

#include "ui/UIWidget.h"
#include "2d/CCLabel.h"
#include "cocos2d.h"
NS_CC_BEGIN

namespace ui 
{	
	typedef enum 
	{
		RICH_ITEM_TEXT,
		RICH_ITEM_NEWLINE,
		RICH_ITEM_IMAGE,
		RICH_ITEM_CUSTOM
	}
	RichItemType;

	class RichItem : public Ref
	{
	public:
		RichItem(){};
		virtual ~RichItem(){};
		bool init(int tag, const Color3B& color, GLubyte opacity);

		std::string getContext() {return context;}
		void setContext(std::string cxt) {context = cxt;}

	protected:
		RichItemType _type;
		int _tag;
		Color3B _color;
		GLubyte _opacity;
		std::string context;
		friend class RichTextUI;
	};

	class RichItemNewLine : public RichItem
	{
	public:
		RichItemNewLine(){_type = RICH_ITEM_NEWLINE;};
		virtual ~RichItemNewLine(){};
		bool init(int tag);
		static RichItemNewLine* create(int tag);
	protected:
		friend class RichTextUI;    
	};

	class RichItemText : public RichItem
	{
	public:
		RichItemText(){_type = RICH_ITEM_TEXT;};
		virtual ~RichItemText(){};
		bool init(int tag, const Color3B& color, GLubyte opacity, const char* text, const char* fontName, float fontSize);
		static RichItemText* create(int tag, const Color3B& color, GLubyte opacity, const char* text, const char* fontName, float fontSize);
		void enableOutLine(const Color4B& outcolor, GLubyte outlinesize) 
		{
			_outcolor = outcolor;
			_outlinesize = outlinesize;
		}
		void enableLinkLine(const Color4B& linkcolor, GLubyte linksize) 
		{
			_linkcolor = linkcolor;
			_linksize = linksize;
		}

	protected:
		std::string _text;
		std::string _fontName;
		float _fontSize;
		Color4B _outcolor;
		Color4B _linkcolor;
		GLubyte _linksize;
		GLubyte _outlinesize;
		friend class RichTextUI;
	};

	class RichItemImage : public RichItem
	{
	public:
		RichItemImage(){_type = RICH_ITEM_IMAGE;};
		virtual ~RichItemImage(){};
		bool init(int tag, const Color3B& color, GLubyte opacity, const char* filePath);
		static RichItemImage* create(int tag, const Color3B& color, GLubyte opacity, const char* filePath);
	protected:
		std::string _filePath;
		Rect _textureRect;
		int _textureType;
		friend class RichTextUI;
	};

	class RichItemCustom : public RichItem
	{
	public:
		RichItemCustom(){_type = RICH_ITEM_CUSTOM;};
		virtual ~RichItemCustom(){CC_SAFE_RELEASE(_customNode);};
		bool init(int tag, const Color3B& color, GLubyte opacity, Node* customNode);
		static RichItemCustom* create(int tag, const Color3B& color, GLubyte opacity, Node* customNode);
	protected:
		Node* _customNode;
		friend class RichTextUI;
	};

	class LinkLable;
	class LableDelegate
	{
	public:
		virtual void labelClicked(LinkLable* lab) = 0;
	};

	class LinkLable : public Label
	{
	public:
		LinkLable(FontAtlas *atlas = nullptr, TextHAlignment hAlignment = TextHAlignment::LEFT, 
			TextVAlignment vAlignment = TextVAlignment::TOP, bool useDistanceField = false, bool useA8Shader = false);	

		virtual ~LinkLable();

		static LinkLable* create(const std::string& text, const std::string& fontFile, float fontSize, const Size& dimensions = Size::ZERO,
			TextHAlignment hAlignment = TextHAlignment::LEFT, TextVAlignment vAlignment = TextVAlignment::TOP);

		void setLableDelegate(LableDelegate* ld) { _delegate = ld; }
		void enableLinkLine(const Color4B& linkcolor, GLubyte linksize) ;
		bool onTouchBegan(Touch *touch, Event *unusedEvent);
		void onTouchEnded(Touch *touch, Event *unusedEvent);

		LayerColor* getLinkline() { return _linkline;}

	protected:
		EventListenerTouchOneByOne* _touchListener;
		LayerColor* _linkline;
		LableDelegate* _delegate;
		Color4B _linkcolor;
		GLubyte _linksize;
	};
}

NS_CC_END

#endif /* defined(__RichItem__) */

