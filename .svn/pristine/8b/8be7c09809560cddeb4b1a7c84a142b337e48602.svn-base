package com.doteplay.editor.tools.tageditor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import com.doteyplay.game.editor.ResourceDataConstants;


public class TagEditor extends JPanel {

	private JPanel basePanel = null;  //  @jve:decl-index=0:visual-constraint="26,19"
	private JToolBar toolsToolBar = null;
	private JButton colorButton = null;
	private JButton linkButton = null;
	public String content = "";// 正文 // @jve:decl-index=0:
	private JSplitPane totalSplitPane = null;
	private JScrollPane resultScrollPane = null;
	private JScrollPane wordScrollPane = null;
	/*
	 * 建立一个颜色索引和颜色类的map结构
	 */
	public Map<String, Color> colorMap = new Hashtable<String, Color>(); // @jve:decl-index=0:
	public  JTextPane resultTextPane = null;
	public  JTextPane wordTextPane = null;
	
	public Map<Integer,Integer>numberIndex = new Hashtable<Integer,Integer>();  //  @jve:decl-index=0:
	int srcNumber = 0;
	int descNumber = 0;
	public Map<String ,Icon>imageMap =  new Hashtable<String,Icon>();  //  @jve:decl-index=0:
	/**
	 * 添加按钮监听
	 */
	private ButtonAction buttonAction = null;  //  @jve:decl-index=0:
	private TeatPaneListener tpl = null;
	private JButton icoButton = null;
	private JButton openButton = null;
	private JButton saveButton = null;
	private boolean flag = false;
	private File file = null;
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
		wordTextPane.addFocusListener(tpl);
	}

	/**
	 * This method initializes
	 * 
	 */
	public TagEditor(String content,boolean flag) {
		super();
		/**
		 * 实例化颜色
		 */
		for (int i = 0; i < ResourceDataConstants.TAG_COLORS.length; i++) {
			int color = ResourceDataConstants.TAG_COLORS[i];
			colorMap.put(""+i, new Color(color));
		}
		
		/**
		 * 实例化图标
		 */
		imageMap.put("/em01", new ImageIcon(getClass().getResource("ico/01.GIF")));
		imageMap.put("/em02", new ImageIcon(getClass().getResource("ico/02.GIF")));
		imageMap.put("/em03", new ImageIcon(getClass().getResource("ico/03.GIF")));
		imageMap.put("/em04", new ImageIcon(getClass().getResource("ico/04.GIF")));
		imageMap.put("/em05", new ImageIcon(getClass().getResource("ico/05.GIF")));
		imageMap.put("/em06", new ImageIcon(getClass().getResource("ico/06.GIF")));
		imageMap.put("/em07", new ImageIcon(getClass().getResource("ico/07.GIF")));
		imageMap.put("/em08", new ImageIcon(getClass().getResource("ico/08.GIF")));
		imageMap.put("/em09", new ImageIcon(getClass().getResource("ico/10.GIF")));
		imageMap.put("/em10", new ImageIcon(getClass().getResource("ico/11.GIF")));
		imageMap.put("/em11", new ImageIcon(getClass().getResource("ico/12.GIF")));
		imageMap.put("/em12", new ImageIcon(getClass().getResource("ico/13.GIF")));
		imageMap.put("/em13", new ImageIcon(getClass().getResource("ico/14.GIF")));
		imageMap.put("/em14", new ImageIcon(getClass().getResource("ico/15.GIF")));
		imageMap.put("/em15", new ImageIcon(getClass().getResource("ico/16.GIF")));
		imageMap.put("/em16", new ImageIcon(getClass().getResource("ico/17.GIF")));
		imageMap.put("/em17", new ImageIcon(getClass().getResource("ico/18.GIF")));
		imageMap.put("/em18", new ImageIcon(getClass().getResource("ico/19.GIF")));
		imageMap.put("/em19", new ImageIcon(getClass().getResource("ico/20.GIF")));
		imageMap.put("/em20", new ImageIcon(getClass().getResource("ico/21.GIF")));
		imageMap.put("/em21", new ImageIcon(getClass().getResource("ico/22.GIF")));
		imageMap.put("/em22", new ImageIcon(getClass().getResource("ico/23.GIF")));
		imageMap.put("/em23", new ImageIcon(getClass().getResource("ico/24.GIF")));
		imageMap.put("/em24", new ImageIcon(getClass().getResource("ico/25.GIF")));
		imageMap.put("/em25", new ImageIcon(getClass().getResource("ico/26.GIF")));
		imageMap.put("/jin", new ImageIcon(getClass().getResource("ico/jin.GIF")));
		imageMap.put("/yin", new ImageIcon(getClass().getResource("ico/yin.GIF")));
		imageMap.put("/tong", new ImageIcon(getClass().getResource("ico/tong.GIF")));
		imageMap.put("/j$", new ImageIcon(getClass().getResource("ico/jinyb.GIF")));
		imageMap.put("/y$", new ImageIcon(getClass().getResource("ico/yinyb.GIF")));
		imageMap.put("/jy$", new ImageIcon(getClass().getResource("ico/j$.GIF")));
		imageMap.put("/yy$", new ImageIcon(getClass().getResource("ico/y$.GIF")));
		imageMap.put("/jb$", new ImageIcon(getClass().getResource("ico/jinq.GIF")));

		this.flag = flag; 
		buttonAction =  new ButtonAction(this);
		this.content = content;
		initialize();
		this.dealWith(this.content);
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setLayout(new BorderLayout());
		this.setBounds(new Rectangle(0, 0, 393, 177));
		this.add(getBasePanel(), BorderLayout.CENTER);

	}

	/**
	 * This method initializes basePanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getBasePanel() {
		if (basePanel == null) {
			basePanel = new JPanel();
			basePanel.setLayout(new BorderLayout());
			basePanel.setBounds(new Rectangle(1, 1, 352, 129));
			basePanel.add(getToolsToolBar(), BorderLayout.NORTH);
			basePanel.add(getTotalSplitPane(), BorderLayout.CENTER);
		}
		return basePanel;
	}

	/**
	 * This method initializes toolsToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getToolsToolBar() {
		if (toolsToolBar == null) {
			toolsToolBar = new JToolBar();
			toolsToolBar.setPreferredSize(new Dimension(87, 24));
			if(flag){
				toolsToolBar.add(getOpenButton());
				toolsToolBar.add(getSaveButton());
			}
			toolsToolBar.add(getColorButton());
			toolsToolBar.add(getLinkButton());
			toolsToolBar.add(getIcoButton());
		}
		return toolsToolBar;
	}

	/**
	 * This method initializes colorButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getColorButton() {
		if (colorButton == null) {
			colorButton = new JButton(); 
			colorButton.setIcon(new ImageIcon(getClass().getResource("/img/icon/rtf_choosecolor.gif")));
			colorButton.setPreferredSize(new Dimension(23, 23));
			colorButton.setBounds(new Rectangle(16, 1, 23, 20));
			colorButton.setToolTipText("颜色编辑");
			colorButton.setActionCommand("colorButton");
			colorButton.addActionListener(this.buttonAction);
		}
		return colorButton;
	}

	/**
	 * This method initializes linkButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getLinkButton() {
		if (linkButton == null) {
			linkButton = new JButton(); 
			linkButton.setIcon(new ImageIcon(getClass().getResource("/img/icon/discrete_attribute.gif")));
			linkButton.setPreferredSize(new Dimension(23, 23));
			linkButton.setBounds(new Rectangle(44, 1, 23, 20));
			linkButton.setToolTipText("超链接编辑");
			linkButton.setActionCommand("linkButton");
			linkButton.addActionListener(this.buttonAction);
			
		}
		return linkButton;
	}

	/**
	 * This method initializes totalSplitPane
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getTotalSplitPane() {
		if (totalSplitPane == null) {
			totalSplitPane = new JSplitPane();
			totalSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
			totalSplitPane.setContinuousLayout(false);
			totalSplitPane.setPreferredSize(new Dimension(5, 130));
			totalSplitPane.setOneTouchExpandable(true);
			totalSplitPane.setBottomComponent(getWordScrollPane());
			totalSplitPane.setTopComponent(getResultScrollPane());
		}
		return totalSplitPane;
	}

	/**
	 * This method initializes resultScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getResultScrollPane() {
		if (resultScrollPane == null) {
			resultScrollPane = new JScrollPane();
			resultScrollPane.setPreferredSize(new Dimension(3, 60));
			resultScrollPane.setViewportView(getResultTextPane());
		}
		return resultScrollPane;
	}

	/**
	 * This method initializes wordScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getWordScrollPane() {
		if (wordScrollPane == null) {
			wordScrollPane = new JScrollPane();
//			wordScrollPane.setPreferredSize(new Dimension(2, 80));
			wordScrollPane.setViewportView(getWordTextPane());
		}
		return wordScrollPane;
	}

	/**
	 * 处理字符串
	 */
	public void dealWithString(String src ) {
		SimpleAttributeSet attrSet  =  new SimpleAttributeSet();
		String str = src;
		if (str.startsWith("<#")) {
			descNumber = descNumber + str.substring(0,str.indexOf("$>")).length() + 2;
			String s = str.substring(2, str.indexOf(">"));
			String[] attributes = s.split("\\|");// 第一个和最后一个都为标签有用
			// 的就只有中间部分
			Color col = colorMap.get(attributes[1].split("=")[1]);
			StyleConstants.setForeground(attrSet, col);
			StyleConstants.setUnderline(attrSet, true);

			int beginindex = str.indexOf("$>") + 2;
			int endindex = str.indexOf("</#>");
//			System.out.println(src + "\t:\t" + beginindex+"\t::"+endindex);
			dealWithResult(str.substring(beginindex, endindex), attrSet);
			descNumber += 4;
			str = str.substring(str.indexOf("</#>") + 4);
			this.dealWithString(str);
			

		} else {// 说明开头不是标签
			String s = "";
			if (str.indexOf("<#") > 0) {
				s = str.substring(0, str.indexOf("<#") > 0 ? str.indexOf("<#")
						: str.length());
				dealWithResult(s, attrSet);// 添加显示结果

			} else {
				
				dealWithResult(str, attrSet);// 添加显示结果
			}
			if (str.indexOf("<#") >= 0) {
				str = str.substring(str.indexOf("<#"));
				this.dealWithString(str);
			}
		}
		

	}

	/**
	 * 把结果处理成效果显示内容
	 */
	public void dealWithResult(String str, SimpleAttributeSet attrSet) {
		Document doc = resultTextPane.getDocument();
		try {

			
			while( str!= null && !"".equals(str)){//循环插入图片

				String key =  this.isIcoBegin(str);
				if(!"".equals(key)){//说明是以图片开头的
					Icon ico =  imageMap.get(key);
					resultTextPane.setCaretPosition(doc.getLength());
					this.resultTextPane.insertIcon(ico);
//					str =  str.replace(key, "");
					str = str.substring(key.length());
					descNumber = descNumber+key.length();
					this.numberIndex.put(srcNumber++, descNumber);
				} else {//是以其他文字开头的
					
					String s =  "";
					if(str.indexOf("/")>0){
						s = str.substring(0,str.indexOf("/"));
					} else {
						s = str;
						
					}
					String ss = s.substring(0) ; 
//					if(ss.indexOf("\r\n") > 0){
//						while(ss != null && !"".equals(ss.trim())){
//							
//							String sss = "";
//							String ssss =  "";
//							if(ss.indexOf("\r\n")>0){
//								sss = ss.substring(0,ss.indexOf("\r\n"));
//								ssss = sss+"\r\n";
//							} else {
//								sss =  ss;
//								ssss = sss;
//								
//							}
////							descNumber = descNumber+2;
////							System.out.println("进入循环了");
////							descNumber++;
//							for(int i = 0 ;i<sss.length() ; i ++){
//								if(i!=sss.length()-1){
//									this.numberIndex.put(srcNumber++, descNumber++);	
//								} else {
////									System.out.println("--------555555--->");
////									descNumber = descNumber ++;
//									this.numberIndex.put(srcNumber, descNumber++);
//								}
//								
//							}
//							doc.insertString(doc.getLength(), ssss, attrSet);
//							this.numberIndex.put(srcNumber, descNumber);
//							if(ss.indexOf("\r\n")>0){
//								ss =  ss.substring(sss.length()+2);
//							} else {
//								ss = "";
//							}
//							
//							
//						}
//					} 
//			else {
						for(int i = 0 ;i<s.length() ; i ++){
							this.numberIndex.put(srcNumber++, descNumber++);
						}

						doc.insertString(doc.getLength(), s, attrSet);
//					}	
					if(s.indexOf("\r\n") >=0){
						descNumber = descNumber + (s.split("\\\r\\\n").length -1) ;
//						srcNumber = srcNumber + s.split("\\\r\\\n").length -1 ;
						this.numberIndex.put(srcNumber, descNumber);
					} 
//					str = str.replace(s, "");
					str = str.substring(s.length());
					
				}
				
			}
			
			
		} catch (BadLocationException e) {
			System.out.println("BadLocationException:   " + e);
		}
	}
	
	/**
	 * 判断该字符串是否是以图标开头,如果是话则返回该字符串否则返回一个空串
	 */
	public String isIcoBegin(String str){
		String s = "";
		Set set = imageMap.entrySet();
		Iterator it = set.iterator();
		while(it.hasNext()){
			Map.Entry<String,ImageIcon> me =  (Map.Entry<String,ImageIcon>)it.next();
			String key = me.getKey();
			if(str.startsWith(key)){//说明是以该图片开头的
				s =  key;
				return s;
			}
		}
		
		return "";
	}
	
	public void dealWith(String str){
		if(str == null){
			return;
		}
		str = str.trim();
		this.numberIndex =  null;
		this.numberIndex =  new Hashtable<Integer, Integer>();
		
		this.descNumber = 0;
		this.srcNumber = 0;
		wordTextPane.setText("");
		resultTextPane.setText("");
		this.dealWithString(str);
		this.dealWithWord(str);
		this.numberIndex.put(srcNumber, descNumber);
	}
	

	public void dealWithWord(String str) {
		SimpleAttributeSet attrSet  =  new SimpleAttributeSet();
		Document doc = wordTextPane.getDocument();
		try {
			doc.insertString(doc.getLength(), str, attrSet);
		} catch (BadLocationException e) {
			System.out.println("BadLocationException:   " + e);
		}
	}

	/**
	 * This method initializes resultTextPane
	 * 
	 * @return javax.swing.JTextPane
	 */
	public JTextPane getResultTextPane() {
		if (resultTextPane == null) {
			resultTextPane = new JTextPane();
			resultTextPane.setBackground(Color.WHITE);
			
		}
		return resultTextPane;
	}

	/**
	 * This method initializes wordTextPane
	 * 
	 * @return javax.swing.JTextPane
	 */
	public JTextPane getWordTextPane() {
		if (wordTextPane == null) {
			wordTextPane = new JTextPane();
			tpl =  new TeatPaneListener(this);
			wordTextPane.addFocusListener(tpl);
		}
		return wordTextPane;
	}

	
	class ButtonAction implements ActionListener{
		private TagEditor tt =  null;
		public ButtonAction(TagEditor tt){
			this.tt = tt;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if("colorButton".equals(e.getActionCommand())){//使颜色按钮
				String ct = tt.resultTextPane.getSelectedText();
				if(ct != null&&!"".equals(ct.trim())){//判断是否已经选取了文字
					FontColor fc = new FontColor("0",this.tt);
					fc.setResizable(false);
					fc.setTitle("颜色编辑器");
					int end = tt.resultTextPane.getSelectionEnd();
					int begin = tt.resultTextPane.getSelectionStart();
					Map<Integer,Integer> map  = tt.numberIndex;
					fc.setBegin(map.get(begin));//选中区域的开始位置
					fc.setEnd(map.get(end));//选中区域的结束位置
					fc.setBounds(250, 200, 255, 265);
					
					Dimension d=fc.getSize();
					Point loc = basePanel.getLocationOnScreen();
		        	loc.translate(d.width/3,0);
		        	fc.setLocation(loc);
		        	
					
					fc.setModal(true);
					fc.setVisible(true);
					
				} else {//没有选取文字
					
					JOptionPane.showMessageDialog(null,"请选择要编辑的文字","提示",1);
					
				}
			}
			
			if("linkButton".equals(e.getActionCommand())){//是超链接按钮
				String ct = tt.resultTextPane.getSelectedText();
				if(ct != null&&!"".equals(ct.trim())){//判断是否已经选取了文字
					int end = tt.resultTextPane.getSelectionEnd();
					int begin = tt.resultTextPane.getSelectionStart();
					Map<Integer,Integer> map  = tt.numberIndex;
	
					UnderLine ul =  new UnderLine(map.get(begin),map.get(end),tt);
					ul.setResizable(false);
					ul.setTitle("超链接编辑");
					ul.setContentPane(ul.getTotalPanel());
					ul.setBounds(250, 200, 255, 265);
					
					Dimension d=ul.getSize();
					Point loc = basePanel.getLocationOnScreen();
		        	loc.translate(d.width/3,0);
		        	ul.setLocation(loc);
					
					ul.setModal(true);
					ul.setVisible(true);
					
				} else {//没有选择编辑的文字
					
					JOptionPane.showMessageDialog(null,"请选择要编辑的文字","提示",1);
				}
			}
			
			if("icoButton".equals(e.getActionCommand())){//点击了图片选择按钮
//				System.out.println(resultTextPane.get);
				
				int end = tt.resultTextPane.getSelectionEnd();
				int begin = tt.resultTextPane.getSelectionStart();
				Map<Integer,Integer> map  = tt.numberIndex;
				
				Ico ico =  new Ico(tt,map.get(begin));
				ico.setTitle("图标编辑");
				ico.setResizable(false);
				ico.setContentPane(ico.getTotalPanel());
				ico.setBounds(250, 200, 255, 265);

				Dimension d=ico.getSize();
				Point loc = basePanel.getLocationOnScreen();
	        	loc.translate(d.width/3,0);
	        	ico.setLocation(loc);
	        	
	        	ico.setModal(true);
				ico.setVisible(true);
			}

		}

	}
	
	class TeatPaneListener implements FocusListener{
		private TagEditor tt = null;
		public TeatPaneListener(TagEditor tt){
			this.tt = tt;
		}
		@Override
		public void focusGained(FocusEvent e) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void focusLost(FocusEvent e) {
			// TODO Auto-generated method stub
			String content = tt.wordTextPane.getText();
			tt.setContent(content);
			tt.dealWith(content);
		}
		
	}
	
	
	
	
	
	
	/**
	 * This method initializes icoButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getIcoButton() {
		if (icoButton == null) {
			icoButton = new JButton();
			icoButton.setIcon(new ImageIcon(getClass().getResource("/img/icon/tileimg.png")));
			icoButton.setPreferredSize(new Dimension(23, 23));
			icoButton.setLocation(new Point(72, 1));
			icoButton.setSize(new Dimension(23, 23));
			icoButton.setToolTipText("图片编辑");
			icoButton.setActionCommand("icoButton");
			buttonAction = new ButtonAction(this);
			icoButton.addActionListener(buttonAction);
		}
		return icoButton;
	}

	/**
	 * This method initializes openButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getOpenButton() {
		if (openButton == null) {
			openButton = new JButton();
			openButton.setIcon(new ImageIcon(getClass().getResource("/img/icon/openFile.png")));
			openButton.setToolTipText("打开");
			openButton.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					JFileChooser chooser = new JFileChooser();
			        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);//设置选择模式，既可以选择文件又可以选择文件夹
					String fileFormat[] = { "txt" };
					FileFilter  filter = new FileNameExtensionFilter( "txt文件",fileFormat);
			        chooser.setFileFilter(filter);//设置文件后缀过滤器
			        int  retval = chooser.showOpenDialog(null);//显示"保存文件"对话框
			        file = null;//
			        if(retval == JFileChooser.APPROVE_OPTION) {//若成功打开
			        	file = chooser.getSelectedFile();//得到选择的文件名
		            }
			        /**
			         *  File f = Util.openSelectFileDialog("Excel文件","xlsx");
			         */
					if(file == null){
						return;
					}
					InputStream inputStream = null;
					try {
						inputStream = new FileInputStream(file);
						byte a[] = new byte[1024];
						content = "";
						while(true){
							int t = inputStream.read(a);
							content += new String(a);
							if(t<1024){
								break;
							}
						}
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					} catch (IOException e2) {
						e2.printStackTrace();
					}finally{
						if(inputStream != null){
							try {
								inputStream.close();
							} catch (IOException e1) {
								e1.printStackTrace();
							}							
						}
					}
					dealWith(content);
				
				}
			});
		}
		return openButton;
	}

	/**
	 * This method initializes saveButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getSaveButton() {
		if (saveButton == null) {
			saveButton = new JButton();
			saveButton.setIcon(new ImageIcon(getClass().getResource("/img/icon/saveFile.png")));
			saveButton.setToolTipText("保存");
			saveButton.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					if(file == null){
						JFileChooser chooser = new JFileChooser();
				        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);//设置选择模式，既可以选择文件又可以选择文件夹
						String fileFormat[] = { "txt" };
						FileFilter  filter = new FileNameExtensionFilter( "TXT文件",fileFormat);
				        chooser.setFileFilter(filter);//设置文件后缀过滤器
				        int  retval = chooser.showOpenDialog(null);//显示"保存文件"对话框
				        if(retval == JFileChooser.APPROVE_OPTION) {//若成功打开
				        	file = chooser.getSelectedFile();//得到选择的文件名
			            }
						if(file == null){
							return;
						}
					}
				    OutputStream os;
					try {
						os = new FileOutputStream(file);
					    byte[]  b =  content.getBytes();
					    os.write(b);
					    os.flush();
					    os.close();
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					} catch (IOException e2) {
						e2.printStackTrace();
					}
				}
			});
		}
		return saveButton;
	}

	public static void main(String[] args) {

		final JFrame jframe = new JFrame("<tag>编辑器");
		jframe.setBounds(200, 150, 404, 200);
		Container c = jframe.getContentPane();
		final TagEditor tt = new TagEditor(
				"安全:Java用来设计网路和分布系统，这带来了新的安全问题，Java可以用来构建防病毒和防攻击的System.事实证明Java在防毒这一方面做的比较好",true);
		///em1613、信言不美，美言不信。 （老子）/em0114、祸兮福之所倚，福兮祸之所伏。 （老子）15、合抱之木，/em11/em18生于毫末；九层之台，起于/em20累土；千里之行，始于足下。（老子） /em09/em13/em13/em13
		// 笑/em01东10/jin20/yin90/tong
		// ssd/r/nddssd/r/nddssd/r/nddssd/r/nddssd/r/nddssd/r/ndd
//		JPanel jp =  new JPanel(null);
		jframe.getContentPane().add(tt);
		jframe.setVisible(true);
		
		jframe.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
//				if (JOptionPane.showConfirmDialog(jframe, "你真的决定退出吗？") == JOptionPane.YES_OPTION) {
				System.out.println("结果最后为："+tt.content);
					System.exit(0);
//				}

			}
		});
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
