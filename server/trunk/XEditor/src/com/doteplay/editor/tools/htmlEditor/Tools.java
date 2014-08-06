package com.doteplay.editor.tools.htmlEditor;

import java.awt.Color;
import java.io.File;
import java.util.Vector;

import javax.swing.Icon;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.text.AttributeSet;
import javax.swing.text.Document;
import javax.swing.text.EditorKit;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.text.StyledEditorKit;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
/**
 * Paragraph
 * 段落样式设置
 * @author Azrael_Guan
 *
 */
public class Tools {
	/**
	 * 文字对齐方式
	 */
	public final static int LEFT = 0;
	/**
	 * 文字对齐方式
	 */
	public final static int CENTER = 1;
	/**
	 * 文字对齐方式
	 */
	public final static int RIGHT = 2;
	/**
	 * 设置字体
	 * @param editor
	 * @param family
	 */
	public static void setFontFamily(JTextPane editor, String family) {
		if (editor != null) {
			if (family!=null) {
				MutableAttributeSet attr = new SimpleAttributeSet();
			    StyleConstants.setFontFamily(attr, family);
			    setCharacterAttributes(editor, attr, false); 
			} else {
				UIManager.getLookAndFeel().provideErrorFeedback(editor);
			}
		}
	}
	
	/**
	 * 设置字体大小
	 * @param editor
	 * @param size
	 */
	public static void setFontSize(JTextPane editor, int size) {
		if (editor != null) {
			if ((size > 0) && (size < 512)) {
				MutableAttributeSet attr = new SimpleAttributeSet();
				StyleConstants.setFontSize(attr, size);
				setCharacterAttributes(editor, attr, false);
			} else {
				UIManager.getLookAndFeel().provideErrorFeedback(editor);
			}
		}
	}

	/**
	 * 设置文字加粗
	 * @param editor
	 */
	public static void setBold(JTextPane editor) {
		if (editor != null) {
			StyledEditorKit kit = getStyledEditorKit(editor);
			MutableAttributeSet attr = kit.getInputAttributes();
			boolean bold = (StyleConstants.isBold(attr)) ? false : true;
			SimpleAttributeSet sas = new SimpleAttributeSet();
			StyleConstants.setBold(sas, bold);
			setCharacterAttributes(editor, sas, false);
		}
	}
	
	/**
	 * 设置文字倾斜
	 * @param editor
	 */
	public static void setFontItalic(JTextPane editor) {
		if (editor != null) {
			StyledEditorKit kit = getStyledEditorKit(editor);
			MutableAttributeSet attr = kit.getInputAttributes();
			boolean italic = (StyleConstants.isItalic(attr)) ? false : true;
			SimpleAttributeSet sas = new SimpleAttributeSet();
			StyleConstants.setItalic(sas, italic);
			setCharacterAttributes(editor, sas, false);
		}
	}
	
	/**
	 * 设置文字下划线
	 * @param editor
	 */
	public static void setFontUnderline(JTextPane editor) {
		if (editor != null) {
			StyledEditorKit kit = getStyledEditorKit(editor);
			MutableAttributeSet attr = kit.getInputAttributes();
			boolean underline = (StyleConstants.isUnderline(attr)) ? false : true;
			SimpleAttributeSet sas = new SimpleAttributeSet();
			StyleConstants.setUnderline(sas, underline);
			setCharacterAttributes(editor, sas, false);
		}
	}
	/**
	 * 设置文字前景色
	 * @param editor
	 * @param fg
	 */
	public static void setFontForegroundColor(JTextPane editor,Color fg) {
		  if (editor != null) {
			   if (fg != null) {
			    MutableAttributeSet attr = new SimpleAttributeSet();
			    StyleConstants.setForeground(attr, fg);
			    setCharacterAttributes(editor, attr, false);
			   } else {
			    UIManager.getLookAndFeel().provideErrorFeedback(editor);
			   }
			  }
	}
	/**
	 * 设置文字背景色
	 * @param editor
	 * @param bg
	 */
	@Deprecated
	public static void setFontBackgroundColor(JTextPane editor,Color bg) {
		if (editor != null) {
			if (bg != null) {
				MutableAttributeSet attr = new SimpleAttributeSet();
				StyleConstants.setBackground(attr, bg);
				setCharacterAttributes(editor, attr, false);
			} else {
				UIManager.getLookAndFeel().provideErrorFeedback(editor);
			}
		}
	}
	
	/**
	 * 插入回车
	 * @param editor 文本面板
	 */
	public static void insertEnter(JTextPane editor) {
		if (editor != null) {
//			String ss = editor.getText();
//			int index = ss.indexOf("<p-implied $ename=\"icon\">");
//			ss = ss.substring(0, index)+html+ss.substring(index+"<p-implied $ename=\"icon\">".length());
//			ss.replaceFirst("<p-implied $ename=icon>", html);
//			editor.setText(ss);
			HTMLEditorKit kit = new HTMLEditorKit(); 
//			kit.install(editor); 
			try { 
				kit.insertHTML((HTMLDocument) editor.getDocument(), editor.getCaretPosition(), 
						"<br>", 0, 0, HTML.Tag.BR); 
			} catch (Exception e1) { 
				JOptionPane.showMessageDialog(null,"回车符不能添加","错误提示",JOptionPane.ERROR_MESSAGE); 
			} 
//			kit.deinstall(editor);
		}
	}
	
	/**
	 * 插入表情图片
	 * @param editor 文本面板
	 * @param html 图片标签
	 */
	public static void insertFace(JTextPane editor,String html) {
		if (editor != null) {
//			String ss = editor.getText();
//			int index = ss.indexOf("<p-implied $ename=\"icon\">");
//			ss = ss.substring(0, index)+html+ss.substring(index+"<p-implied $ename=\"icon\">".length());
//			ss.replaceFirst("<p-implied $ename=icon>", html);
//			editor.setText(ss);
			HTMLEditorKit kit = new HTMLEditorKit(); 
//			kit.install(editor); 
			try { 
				kit.insertHTML((HTMLDocument) editor.getDocument(), editor.getCaretPosition(), 
						html, 0, 0, HTML.Tag.IMG); 
			} catch (Exception e1) { 
				JOptionPane.showMessageDialog(null,"图片不能添加","错误提示",JOptionPane.ERROR_MESSAGE); 
			} 
//			kit.deinstall(editor);
		}
	}
	
	/**
	 * 将Unicode字符集转换成对应的汉字GB2312
	 * @param content 要转换的字符串
	 * @return String 转换完的字符串
	 */
	public static String changeFont(String content) {
		StringBuffer STSTR = new StringBuffer(); 
		int index = 0;
		int start = 0;
		while (content.indexOf("&#",index) != -1) {
			index = content.indexOf("&#",index);
			STSTR.append(content.substring(start, index)); 
			int endIndex = content.indexOf(";",index);
			if(endIndex < 0) {
				index ++;
				start = index;
				continue;
			}
			try {
				char c_Back = (char)Integer.parseInt(content.substring(index+2,endIndex) ); 
				STSTR.append(c_Back);
			} catch (NumberFormatException e) {
				STSTR.append(content.substring(index,endIndex));
				System.err.println("字符转换失败<==>字符串为："+content.substring(index,endIndex));
			} 
			index +=(endIndex - index) + 1;
			start = index;
		}
		STSTR.append(content.substring(start, content.length())); 
//		System.out.print( "<BR>结果=" +STSTR.toString());
		return STSTR.toString();
	}
	/**
	 * 看样好像不能用这个了
	 * 添加表情图片
	 * @param editor
	 * @param icon
	 */
	@Deprecated
	public static void addFace(JTextPane editor, Icon icon) {
		if (editor != null) {
			MutableAttributeSet attr = new SimpleAttributeSet();
			StyleConstants.setIcon(attr, icon);
			setFaceCharacterAttributes(editor, attr, false);
		}
	}
	
	/**
	 * 设置表情
	 * @param editor
	 * @param attr
	 * @param replace
	 */
	private static final void setFaceCharacterAttributes(JTextPane editor,
			AttributeSet attr, boolean replace) {
		int ps = editor.getSelectionStart();
		int pe = editor.getSelectionEnd();
		StyledDocument doc = getStyledDocument(editor);
		doc.setCharacterAttributes(ps, pe - ps, attr, replace);
		StyledEditorKit k = getStyledEditorKit(editor);
		MutableAttributeSet inputAttributes = k.getInputAttributes();
		if (replace) {
			inputAttributes.removeAttributes(inputAttributes);
		}
		inputAttributes.addAttributes(attr);
	}
	/**
	 * 设置对齐方式
	 * @param editor
	 * @param align :0：左，1：中，2：右
	 * StyleConstants.ALIGN_LEFT ALIGN_CENTER ALIGN_RIGHT ALIGN_JUSTIFIED
	 */
	public static void setFontAttributes(JTextPane editor,int align) {
		MutableAttributeSet attr = new SimpleAttributeSet();
		StyleConstants.setAlignment(attr, align);
		setParagraphAttributes(editor, attr, false);
	}
	/**
	 * 设置段落的对齐方式
	 * @param editor
	 * @param attr
	 * @param replace
	 */
	private static final void setParagraphAttributes(JTextPane editor,
			AttributeSet attr, boolean replace) {
		int ps = editor.getSelectionStart();
		int pe = editor.getSelectionEnd();
		StyledDocument doc = getStyledDocument(editor);
		doc.setParagraphAttributes(ps, pe - ps, attr, replace);
	}
	/**
	 * 每个字符设置样式
	 * @param editor
	 * @param attr
	 * @param replace
	 */
	private static final void setCharacterAttributes(JTextPane editor,
			AttributeSet attr, boolean replace) {
		int ps = editor.getSelectionStart();
		int pe = editor.getSelectionEnd();
		if (ps != pe) {
			StyledDocument doc = getStyledDocument(editor);
			doc.setCharacterAttributes(ps, pe - ps, attr, replace);
		}
		StyledEditorKit k = getStyledEditorKit(editor);
		MutableAttributeSet inputAttributes = k.getInputAttributes();
		if (replace) {
			inputAttributes.removeAttributes(inputAttributes);
		}
		inputAttributes.addAttributes(attr);
	}
	/**
	 * 删除样式
	 * @param editor
	 */
	public static final void removeStyle(JTextPane editor) {
		StyledEditorKit kit = getStyledEditorKit(editor);
		MutableAttributeSet attr = kit.getInputAttributes();
//		StyleConstants.getFontFamily(attr);
//		System.out.println(StyleConstants.getFontFamily(attr));
		SimpleAttributeSet sas = new SimpleAttributeSet();
		StyleConstants.setFontFamily(sas, "Monospaced");//设置字体
		StyleConstants.setFontSize(sas, 14);//设置字体大小
		StyleConstants.setBold(sas, false);//设置粗体
		StyleConstants.setItalic(sas, false);//设置倾斜
		StyleConstants.setUnderline(sas, false);//设置下划线
		StyleConstants.setForeground(sas, Color.BLACK);
		setCharacterAttributes(editor, sas, false);
		editor.setText(crealString(editor.getText()));
	}
	
	public static String crealString(String str) {
		String[] temp = {
				"<font size=\"4\" face=\"Monospaced\" color=\"#000000\">",
				"<font size=\"4\" color=\"#000000\" face=\"Monospaced\">",
				"<font face=\"Monospaced\" size=\"4\" color=\"#000000\">",
				"<font face=\"Monospaced\" color=\"#000000\" size=\"4\">",
				"<font color=\"#000000\" size=\"4\" face=\"Monospaced\">",
				"<font color=\"#000000\" face=\"Monospaced\" size=\"4\">",
		};
		String temp2 = "</font>";
		StringBuffer STSTR = new StringBuffer(); 
		int index = 0;
		int start = 0;
		for (int i = 0; i < temp.length; i++) {
			while (str.indexOf(temp[i],index) != -1) {
				index = str.indexOf(temp[i],index);
				String s = str.substring(start, index);
				STSTR.append(s); 
				index +=temp[i].length();
				start = index;
				index = str.indexOf(temp2,index);
				s = str.substring(start, index);
				STSTR.append(s); 
				index +=temp2.length();
				start = index;
			}
		}
		String s = str.substring(start, str.length());
		STSTR.append(s); 
//		System.out.println(STSTR);
		return STSTR.toString();
	}
	
//	/**
//	 * 删除样式
//	 * @param editor
//	 */
//	public static final void removeStyle(JTextPane editor) {
//		createStyle("Style01", editor, 14, 0, 0, 0, Color.BLACK, Font.SANS_SERIF);
//	}
//	public static void createStyle(String style, JTextPane editor, int size, int bold, int italic, int underline, Color color, String fontName) { 
//		Style sys = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);
//		StyledDocument doc = getStyledDocument(editor);
//		try { 
//			doc.removeStyle(style); 
//		} catch (Exception e) { 
//		} //先删除这种Style,假使他存在 
//		Style s = doc.addStyle(style, sys); // 加入 
//        StyleConstants.setFontSize(s, size); // 大小 
//        StyleConstants.setBold(s, (bold == 1) ? true : false); // 粗体 
//        StyleConstants.setItalic(s, (italic == 1) ? true : false); // 斜体 
//        StyleConstants.setUnderline(s, (underline == 1) ? true : false); // 下划线 
//        StyleConstants.setForeground(s, color); // 颜色 
//        StyleConstants.setFontFamily(s, fontName);  // 字体 
//        setCharacterAttributes(editor, s, false);
//	}
	
	private static final StyledDocument getStyledDocument(JTextPane e) {
		Document d = e.getDocument();
		if (d instanceof StyledDocument) {
			return (StyledDocument) d;
		}
		throw new IllegalArgumentException("document must be StyledDocument");
	}

	private static final StyledEditorKit getStyledEditorKit(JTextPane e) {
		EditorKit k = e.getEditorKit();
		if (k instanceof StyledEditorKit) {
			return (StyledEditorKit) k;
		}
		throw new IllegalArgumentException("EditorKit must be StyledEditorKit");
	}
	public static String delHtml(String s) {
		if(s.indexOf("<body>") == -1)return s;
		s = s.substring(s.indexOf("<body>")+4);
		s = s.substring(7, s.indexOf("</body>")-3);
		s = setMM(s);
//		s = s.trim();
		return s;
	}
	//<p style="margin-top: 0">
	public static String setMM(String coneter) {
		StringBuffer sb = new StringBuffer();
		String s = "<p style=\"margin-top: 0\">";
		if(coneter.indexOf("<p style=") != -1) {
			sb.append(coneter.substring(coneter.indexOf("<p style=")+s.length(), coneter.indexOf("</p>")));
		}else {
			return coneter.trim();
		}
		return sb.toString().trim(); 
	}
	public static String setHtml(String s) {
		s = "<html>\r\n<head>\r\n</head>\r\n<body>\r\n"+s+"\r\n</body>\r\n</html>";
		return s;
	}
	
	/**
	 * 返回此目录path并且后缀名为postfix的文件名称数组
	 * @param path 路径
	 * @param postfix 后缀名
	 * @return 返回文件名称数组
	 */
	public static String[] readFileNumber(String path,String postfix) {
		String[] name = null;
		try {
			Vector<String> vp = new Vector<String>();
			File f = new File(path);
			name = null;
			String[] temp = f.list();
			if(temp != null)
			for (int i = 0; i < temp.length; i++) {
				if(temp[i].endsWith(postfix)) {
					vp.add(temp[i]);
				}
			}
			name = new String[vp.size()];
			vp.copyInto(name);
			System.out.println(vp);
			vp = null;
		} catch (Exception e) {
			System.out.println("获得文件个数出错"+e);
			return name;
		}
		return name;
	}
}
