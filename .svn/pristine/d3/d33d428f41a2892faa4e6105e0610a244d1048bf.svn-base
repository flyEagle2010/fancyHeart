#ifndef __RICHTEXTUI_H__
#define __RICHTEXTUI_H__
#include "ui/UIScrollView.h"
#include "RichItem.h"
#include "cocos2d.h"
NS_CC_BEGIN

namespace ui 
{
	typedef enum
	{
		RICHTEXT_ANCHOR_CLICKED,
	}
	RichTextEventType;

	typedef void (Ref::*SEL_RichTextClickEvent)(Ref*, RichTextEventType);
	#define richtextclickselector(_SELECTOR) (SEL_RichTextClickEvent)(&_SELECTOR)

	class RichTextUI : public ScrollView, public LableDelegate
	{
	public:
		RichTextUI();
		virtual ~RichTextUI();
		static RichTextUI* create();

		void clearAll();
		void insertNewLine();
		void showItemByTag(short tag);
		void insertElement(RichItem* element);

		void setMaxLine(short lineCount) {_maxline = lineCount; }
		void setLineSpace(short lineSpace) {_lineSpace = lineSpace; }

		virtual void setAnchorPoint(const Point &pt);
		virtual std::string getDescription() const override;
		virtual void visit(cocos2d::Renderer *renderer, const Mat4 &parentTransform, bool parentTransformUpdated);

		void labelClicked(LinkLable* lab);

		//add a call back function would called when checkbox is selected or unselected.
		void addEventListenerRichText(Ref* target, SEL_RichTextClickEvent selector);

CC_CONSTRUCTOR_ACCESS:
		virtual bool init() override;

	protected:
		virtual void initRenderer();

		void formatText();
		void pushToContainer(Node* renderer);
		void handleTextRenderer(RichItemText* item, const char* text);
		void handleImageRenderer(const char* fileParh, const Color3B& color, GLubyte opacity);
		void handleCustomRenderer(Node* renderer);
		void formarRenderers();
		void changeLine();

		LinkLable* createLable(RichItemText* item, const char* text);

	protected:
		short _showTag;
		short _maxline;
		short _lineSpace;
		float _leftSpaceWidth;
		bool _formatTextDirty;
		Vector<RichItem*> _richElements;
		std::vector<Vector<Node*>*> _elementRenders;
		
		Ref*					_richTextEventListener;
		SEL_RichTextClickEvent	_richTextEventSelector;

		std::string _context;
	};
}

NS_CC_END

#endif /* defined(__RichTextUI__) */

