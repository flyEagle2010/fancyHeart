package com.doteplay.editor.tools.tageditor;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Hashtable;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Ico extends JDialog{

	private JPanel TotalPanel = null;  //  @jve:decl-index=0:visual-constraint="72,23"
	private JPanel icoPanel = null;
	private JLabel icoLabel = null;
	private JLabel xiaoguoLabel = null;
	private JButton yesButton = null;
	private JButton noButton = null;
	private JTextField titleTextField = null;
	private JButton ico01Button = null;
	private JButton ico02Button = null;
	private JButton ico03Button = null;
	private JButton ico04Button = null;
	private JButton ico05Button = null;
	private JButton ico06Button = null;
	private JButton ico07Button = null;
	private JButton ico08Button = null;
	private JButton ico09Button = null;
	private JButton ico10Button = null;
	private JButton ico11Button = null;
	private JButton ico12Button = null;
	private JButton ico13Button = null;
	private JButton ico14Button = null;
	private JButton ico15Button = null;
	private JButton ico16Button = null;
	private JButton ico17Button = null;
	private JButton ico18Button = null;
	private JButton ico19Button = null;
	private JButton ico20Button = null;
	private JButton ico21Button = null;
	private JButton ico22Button = null;
	private JButton ico23Button = null;
	private JButton ico24Button = null;
	private JButton ico25Button = null;
	private JPanel moneyPanel = null;
	private JButton money01Button = null;
	private JButton money02Button = null;
	private JButton money03Button = null;
	private JButton money04Button = null;
	private JButton money05Button = null;
	private JButton money06Button = null;
	private JButton money07Button = null;
	private JButton money08Button = null;
	private ButtonAction  buttonAction = null;  //  @jve:decl-index=0:
	
	private Map<String,String> map  = new Hashtable<String, String>();  //  @jve:decl-index=0:
	
	private int index = 0;
	
	/*
	 * 按钮集合
	 */
	private TagEditor tt = null;
	private JScrollPane icoScrollPane = null;
	private JLabel icoLabel1 = null;
	public Ico(TagEditor tt ,int index){
		this.tt = tt ;
		this.index =  index;
		
		/*
		 * 新构建一个图片路径和图片值的对应情况
		 */
		map.put("/01.GIF", "/em01");
		map.put("/02.GIF", "/em02");
		map.put("/03.GIF", "/em03");
		map.put("/04.GIF", "/em04");
		map.put("/05.GIF", "/em05");
		map.put("/06.GIF", "/em06");
		map.put("/07.GIF", "/em07");
		map.put("/08.GIF", "/em08");
		map.put("/10.GIF", "/em09");
		map.put("/11.GIF", "/em10");
		map.put("/12.GIF", "/em11");
		map.put("/13.GIF", "/em12");
		map.put("/14.GIF", "/em13");
		map.put("/15.GIF", "/em14");
		map.put("/16.GIF", "/em15");
		map.put("/17.GIF", "/em16");
		map.put("/18.GIF", "/em17");
		map.put("/19.GIF", "/em18");
		map.put("/20.GIF", "/em19");
		map.put("/21.GIF", "/em20");
		map.put("/22.GIF", "/em21");
		map.put("/23.GIF", "/em22");
		map.put("/24.GIF", "/em23");
		map.put("/25.GIF", "/em24");
		map.put("/26.GIF", "/em25");
		
		map.put("/jin.GIF", "/jin");
		map.put("/yin.GIF", "/yin");
		map.put("/tong.GIF", "/tong");
		map.put("/jingyb.GIF", "/j$");
		map.put("/yinyb.GIF", "/y$");
		map.put("/j$.GIF", "/jy$");
		map.put("/y$.GIF", "/yy$");
		map.put("/jinq.GIF", "/jb$");
		

	}
	
	/**
	 * This method initializes TotalPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	public  JPanel getTotalPanel() {
		if (TotalPanel == null) {
			xiaoguoLabel = new JLabel();
			xiaoguoLabel.setBounds(new Rectangle(154, 12, 81, 18));
			xiaoguoLabel.setHorizontalAlignment(SwingConstants.CENTER);
			xiaoguoLabel.setText("预览效果");
			
			TotalPanel = new JPanel();
			TotalPanel.setLayout(null);
			TotalPanel.setBounds(new Rectangle(0, 0, 255, 238));
			TotalPanel.add(getIcoPanel(), null);
//			TotalPanel.add(icoLabel, null);
			TotalPanel.add(xiaoguoLabel, null);
			TotalPanel.add(getYesButton(), null);
			TotalPanel.add(getNoButton(), null);
			TotalPanel.add(getTitleTextField(), null);
			TotalPanel.add(getMoneyPanel(), null);

			TotalPanel.add(getIcoScrollPane(), null);
//			icoLabel.setIcon(new ImageIcon(getClass().getResource("/01.GIF")));
		}
		return TotalPanel;
	}
	/**
	 * This method initializes icoPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getIcoPanel() {
		if (icoPanel == null) {
			GridLayout gridLayout = new GridLayout();
			gridLayout.setRows(5);
			gridLayout.setColumns(5);
			icoPanel = new JPanel();
			icoPanel.setLayout(gridLayout);
			icoPanel.setBounds(new Rectangle(0, 0, 142, 142));
			icoPanel.add(getIco01Button(), null);
			icoPanel.add(getIco02Button(), null);
			icoPanel.add(getIco03Button(), null);
			icoPanel.add(getIco04Button(), null);
			icoPanel.add(getIco05Button(), null);
			icoPanel.add(getIco06Button(), null);
			icoPanel.add(getIco07Button(), null);
			icoPanel.add(getIco08Button(), null);
			icoPanel.add(getIco09Button(), null);
			icoPanel.add(getIco10Button(), null);
			icoPanel.add(getIco11Button(), null);
			icoPanel.add(getIco12Button(), null);
			icoPanel.add(getIco13Button(), null);
			icoPanel.add(getIco14Button(), null);
			icoPanel.add(getIco15Button(), null);
			icoPanel.add(getIco16Button(), null);
			icoPanel.add(getIco17Button(), null);
			icoPanel.add(getIco18Button(), null);
			icoPanel.add(getIco19Button(), null);
			icoPanel.add(getIco20Button(), null);
			icoPanel.add(getIco21Button(), null);
			icoPanel.add(getIco22Button(), null);
			icoPanel.add(getIco23Button(), null);
			icoPanel.add(getIco24Button(), null);
			icoPanel.add(getIco25Button(), null);
		}
		return icoPanel;
	}
	/**
	 * This method initializes yesButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getYesButton() {
		if (yesButton == null) {
			yesButton = new JButton();
			yesButton.setBounds(new Rectangle(52, 190, 60, 28));
			yesButton.setActionCommand("yesButton");
			buttonAction = new ButtonAction(this);
			yesButton.addActionListener(buttonAction);
			yesButton.setText("确认");
		}
		return yesButton;
	}
	/**
	 * This method initializes noButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getNoButton() {
		if (noButton == null) {
			noButton = new JButton();
			noButton.setBounds(new Rectangle(131, 190, 60, 28));
			noButton.setActionCommand("noButton");
			buttonAction = new ButtonAction(this);
			noButton.addActionListener(buttonAction);
			noButton.setText("取消");
		}
		return noButton;
	}
	/**
	 * This method initializes titleTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTitleTextField() {
		if (titleTextField == null) {
			titleTextField = new JTextField();
			titleTextField.setBounds(new Rectangle(152, 124, 93, 18));
			titleTextField.addKeyListener(new KeyListener(){
				@Override
				public void keyPressed(KeyEvent e) {
					// TODO Auto-generated method stub
					if(e.getKeyCode() == KeyEvent.VK_ENTER){
						icoLabel1.setIcon(new ImageIcon(getClass().getResource(titleTextField.getText())));
					}
				}
				@Override
				public void keyReleased(KeyEvent e) {
					// TODO Auto-generated method stub
				}
				@Override
				public void keyTyped(KeyEvent e) {
					// TODO Auto-generated method stub
				}
				
			});
			
		}
		return titleTextField;
	}
	/**
	 * This method initializes ico01Button	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getIco01Button() {
		if (ico01Button == null) {
			ico01Button = new JButton();
			ico01Button.setMnemonic(KeyEvent.VK_UNDEFINED);
			ico01Button.setPreferredSize(new Dimension(28, 28));
			ico01Button.setActionCommand("/01.GIF");
			ico01Button.setIcon(new ImageIcon(getClass().getResource("ico/01.GIF")));
			buttonAction = new ButtonAction(this);
			ico01Button.addActionListener(buttonAction);
		}
		return ico01Button;
	}
	/**
	 * This method initializes ico02Button	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getIco02Button() {
		if (ico02Button == null) {
			ico02Button = new JButton();
			ico02Button.setActionCommand("/02.GIF");
			ico02Button.setIcon(new ImageIcon(getClass().getResource("ico/02.GIF")));

			buttonAction = new ButtonAction(this);
			ico02Button.addActionListener(buttonAction);
		}
		return ico02Button;
	}
	/**
	 * This method initializes ico03Button	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getIco03Button() {
		if (ico03Button == null) {
			ico03Button = new JButton();
			ico03Button.setActionCommand("/03.GIF");
			ico03Button.setIcon(new ImageIcon(getClass().getResource("ico/03.GIF")));

			buttonAction = new ButtonAction(this);
			ico03Button.addActionListener(buttonAction);
		}
		return ico03Button;
	}
	/**
	 * This method initializes ico04Button	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getIco04Button() {
		if (ico04Button == null) {
			ico04Button = new JButton();
			ico04Button.setActionCommand("/04.GIF");
			ico04Button.setIcon(new ImageIcon(getClass().getResource("ico/04.GIF")));

			buttonAction = new ButtonAction(this);
			ico04Button.addActionListener(buttonAction);
		}
		return ico04Button;
	}
	/**
	 * This method initializes ico05Button	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getIco05Button() {
		if (ico05Button == null) {
			ico05Button = new JButton();
			ico05Button.setActionCommand("/05.GIF");
			ico05Button.setIcon(new ImageIcon(getClass().getResource("ico/05.GIF")));

			buttonAction = new ButtonAction(this);
			ico05Button.addActionListener(buttonAction);
		}
		return ico05Button;
	}
	/**
	 * This method initializes ico06Button	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getIco06Button() {
		if (ico06Button == null) {
			ico06Button = new JButton();
			ico06Button.setActionCommand("/06.GIF");
			ico06Button.setIcon(new ImageIcon(getClass().getResource("ico/06.GIF")));

			buttonAction = new ButtonAction(this);
			ico06Button.addActionListener(buttonAction);
		}
		return ico06Button;
	}
	/**
	 * This method initializes ico07Button	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getIco07Button() {
		if (ico07Button == null) {
			ico07Button = new JButton();
			ico07Button.setActionCommand("/07.GIF");
			ico07Button.setIcon(new ImageIcon(getClass().getResource("ico/07.GIF")));

			buttonAction = new ButtonAction(this);
			ico07Button.addActionListener(buttonAction);
		}
		return ico07Button;
	}
	/**
	 * This method initializes ico08Button	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getIco08Button() {
		if (ico08Button == null) {
			ico08Button = new JButton();
			ico08Button.setActionCommand("/08.GIF");
			ico08Button.setIcon(new ImageIcon(getClass().getResource("ico/08.GIF")));

			buttonAction = new ButtonAction(this);
			ico08Button.addActionListener(buttonAction);
		}
		return ico08Button;
	}
	/**
	 * This method initializes ico09Button	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getIco09Button() {
		if (ico09Button == null) {
			ico09Button = new JButton();
			ico09Button.setActionCommand("/10.GIF");
			ico09Button.setIcon(new ImageIcon(getClass().getResource("ico/10.GIF")));

			buttonAction = new ButtonAction(this);
			ico09Button.addActionListener(buttonAction);
		}
		return ico09Button;
	}
	/**
	 * This method initializes ico10Button	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getIco10Button() {
		if (ico10Button == null) {
			ico10Button = new JButton();
			ico10Button.setMnemonic(KeyEvent.VK_UNDEFINED);
			ico10Button.setActionCommand("/11.GIF");
			ico10Button.setIcon(new ImageIcon(getClass().getResource("ico/11.GIF")));

			buttonAction = new ButtonAction(this);
			ico10Button.addActionListener(buttonAction);
		}
		return ico10Button;
	}
	/**
	 * This method initializes ico11Button	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getIco11Button() {
		if (ico11Button == null) {
			ico11Button = new JButton();
			ico11Button.setActionCommand("/12.GIF");
			ico11Button.setIcon(new ImageIcon(getClass().getResource("ico/12.GIF")));

			buttonAction = new ButtonAction(this);
			ico11Button.addActionListener(buttonAction);
		}
		return ico11Button;
	}
	/**
	 * This method initializes ico12Button	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getIco12Button() {
		if (ico12Button == null) {
			ico12Button = new JButton();
			ico12Button.setActionCommand("/13.GIF");
			ico12Button.setIcon(new ImageIcon(getClass().getResource("ico/13.GIF")));

			buttonAction = new ButtonAction(this);
			ico12Button.addActionListener(buttonAction);
		}
		return ico12Button;
	}
	/**
	 * This method initializes ico13Button	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getIco13Button() {
		if (ico13Button == null) {
			ico13Button = new JButton();
			ico13Button.setActionCommand("/14.GIF");
			ico13Button.setIcon(new ImageIcon(getClass().getResource("ico/14.GIF")));

			buttonAction = new ButtonAction(this);
			ico13Button.addActionListener(buttonAction);
		}
		return ico13Button;
	}
	/**
	 * This method initializes ico14Button	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getIco14Button() {
		if (ico14Button == null) {
			ico14Button = new JButton();
			ico14Button.setActionCommand("/15.GIF");
			ico14Button.setIcon(new ImageIcon(getClass().getResource("ico/15.GIF")));

			buttonAction = new ButtonAction(this);
			ico14Button.addActionListener(buttonAction);
		}
		return ico14Button;
	}
	/**
	 * This method initializes ico15Button	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getIco15Button() {
		if (ico15Button == null) {
			ico15Button = new JButton();
			ico15Button.setActionCommand("/16.GIF");
			ico15Button.setIcon(new ImageIcon(getClass().getResource("ico/16.GIF")));

			buttonAction = new ButtonAction(this);
			ico15Button.addActionListener(buttonAction);
		}
		return ico15Button;
	}
	/**
	 * This method initializes ico16Button	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getIco16Button() {
		if (ico16Button == null) {
			ico16Button = new JButton();
			ico16Button.setActionCommand("/17.GIF");
			ico16Button.setIcon(new ImageIcon(getClass().getResource("ico/17.GIF")));

			buttonAction = new ButtonAction(this);
			ico16Button.addActionListener(buttonAction);
		}
		return ico16Button;
	}
	/**
	 * This method initializes ico17Button	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getIco17Button() {
		if (ico17Button == null) {
			ico17Button = new JButton();
			ico17Button.setActionCommand("/18.GIF");
			ico17Button.setIcon(new ImageIcon(getClass().getResource("ico/18.GIF")));

			buttonAction = new ButtonAction(this);
			ico17Button.addActionListener(buttonAction);
		}
		return ico17Button;
	}
	/**
	 * This method initializes ico18Button	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getIco18Button() {
		if (ico18Button == null) {
			ico18Button = new JButton();
			ico18Button.setActionCommand("/19.GIF");
			ico18Button.setIcon(new ImageIcon(getClass().getResource("ico/19.GIF")));

			buttonAction = new ButtonAction(this);
			ico18Button.addActionListener(buttonAction);
		}
		return ico18Button;
	}
	/**
	 * This method initializes ico19Button	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getIco19Button() {
		if (ico19Button == null) {
			ico19Button = new JButton();
			ico19Button.setActionCommand("/20.GIF");
			ico19Button.setIcon(new ImageIcon(getClass().getResource("ico/20.GIF")));

			buttonAction = new ButtonAction(this);
			ico19Button.addActionListener(buttonAction);
		}
		return ico19Button;
	}
	/**
	 * This method initializes ico20Button	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getIco20Button() {
		if (ico20Button == null) {
			ico20Button = new JButton();
			ico20Button.setMnemonic(KeyEvent.VK_UNDEFINED);
			ico20Button.setActionCommand("/21.GIF");
			ico20Button.setIcon(new ImageIcon(getClass().getResource("ico/21.GIF")));

			buttonAction = new ButtonAction(this);
			ico20Button.addActionListener(buttonAction);
		}
		return ico20Button;
	}
	/**
	 * This method initializes ico21Button	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getIco21Button() {
		if (ico21Button == null) {
			ico21Button = new JButton();
			ico21Button.setActionCommand("/22.GIF");
			ico21Button.setIcon(new ImageIcon(getClass().getResource("ico/22.GIF")));

			buttonAction = new ButtonAction(this);
			ico21Button.addActionListener(buttonAction);
		}
		return ico21Button;
	}
	/**
	 * This method initializes ico22Button	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getIco22Button() {
		if (ico22Button == null) {
			ico22Button = new JButton();
			ico22Button.setActionCommand("/23.GIF");
			ico22Button.setIcon(new ImageIcon(getClass().getResource("ico/23.GIF")));

			buttonAction = new ButtonAction(this);
			ico22Button.addActionListener(buttonAction);
		}
		return ico22Button;
	}
	/**
	 * This method initializes ico23Button	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getIco23Button() {
		if (ico23Button == null) {
			ico23Button = new JButton();
			ico23Button.setActionCommand("/24.GIF");
			ico23Button.setIcon(new ImageIcon(getClass().getResource("ico/24.GIF")));

			buttonAction = new ButtonAction(this);
			ico23Button.addActionListener(buttonAction);
		}
		return ico23Button;
	}
	/**
	 * This method initializes ico24Button	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getIco24Button() {
		if (ico24Button == null) {
			ico24Button = new JButton();
			ico24Button.setActionCommand("/25.GIF");
			ico24Button.setIcon(new ImageIcon(getClass().getResource("ico/25.GIF")));

			buttonAction = new ButtonAction(this);
			ico24Button.addActionListener(buttonAction);
		}
		return ico24Button;
	}
	/**
	 * This method initializes ico25Button	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getIco25Button() {
		if (ico25Button == null) {
			ico25Button = new JButton();
			ico25Button.setActionCommand("/26.GIF");
			ico25Button.setIcon(new ImageIcon(getClass().getResource("ico/26.GIF")));

			buttonAction = new ButtonAction(this);
			ico25Button.addActionListener(buttonAction);
		}
		return ico25Button;
	}
	/**
	 * This method initializes moneyPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getMoneyPanel() {
		if (moneyPanel == null) {
			GridLayout gridLayout1 = new GridLayout();
			gridLayout1.setRows(1);
			gridLayout1.setColumns(8);
			moneyPanel = new JPanel();
			moneyPanel.setLayout(gridLayout1);
			moneyPanel.setBounds(new Rectangle(0, 154, 224, 28));
			moneyPanel.add(getMoney01Button(), null);
			moneyPanel.add(getMoney02Button(), null);
			moneyPanel.add(getMoney03Button(), null);
			moneyPanel.add(getMoney04Button(), null);
			moneyPanel.add(getMoney05Button(), null);
			moneyPanel.add(getMoney06Button(), null);
			moneyPanel.add(getMoney07Button(), null);
			moneyPanel.add(getMoney08Button(), null);
		}
		return moneyPanel;
	}
	/**
	 * This method initializes money01Button	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getMoney01Button() {
		if (money01Button == null) {
			money01Button = new JButton();
			money01Button.setActionCommand("/j$.GIF");
			money01Button.setIcon(new ImageIcon(getClass().getResource("ico/j$.GIF")));

			buttonAction = new ButtonAction(this);
			money01Button.addActionListener(buttonAction);
		}
		return money01Button;
	}
	/**
	 * This method initializes money02Button	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getMoney02Button() {
		if (money02Button == null) {
			money02Button = new JButton();
			money02Button.setActionCommand("/y$.GIF");
			money02Button.setIcon(new ImageIcon(getClass().getResource("ico/y$.GIF")));

			buttonAction = new ButtonAction(this);
			money02Button.addActionListener(buttonAction);
		}
		return money02Button;
	}
	/**
	 * This method initializes money03Button	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getMoney03Button() {
		if (money03Button == null) {
			money03Button = new JButton();
			money03Button.setActionCommand("/jin.GIF");
			money03Button.setIcon(new ImageIcon(getClass().getResource("ico/jin.GIF")));

			buttonAction = new ButtonAction(this);
			money03Button.addActionListener(buttonAction);
		}
		return money03Button;
	}
	/**
	 * This method initializes money04Button	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getMoney04Button() {
		if (money04Button == null) {
			money04Button = new JButton();
			money04Button.setActionCommand("/yin.GIF");
			money04Button.setIcon(new ImageIcon(getClass().getResource("ico/yin.GIF")));

			buttonAction = new ButtonAction(this);
			money04Button.addActionListener(buttonAction);
		}
		return money04Button;
	}
	/**
	 * This method initializes money05Button	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getMoney05Button() {
		if (money05Button == null) {
			money05Button = new JButton();
			money05Button.setActionCommand("/tong.GIF");
			money05Button.setIcon(new ImageIcon(getClass().getResource("ico/tong.GIF")));

			buttonAction = new ButtonAction(this);
			money05Button.addActionListener(buttonAction);
		}
		return money05Button;
	}
	/**
	 * This method initializes money06Button	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getMoney06Button() {
		if (money06Button == null) {
			money06Button = new JButton();
			money06Button.setActionCommand("/jinyb.GIF");
			money06Button.setIcon(new ImageIcon(getClass().getResource("ico/jinyb.GIF")));

			buttonAction = new ButtonAction(this);
			money06Button.addActionListener(buttonAction);
		}
		return money06Button;
	}
	/**
	 * This method initializes money07Button	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getMoney07Button() {
		if (money07Button == null) {
			money07Button = new JButton();
			money07Button.setActionCommand("/yinyb.GIF");
			money07Button.setIcon(new ImageIcon(getClass().getResource("ico/yinyb.GIF")));

			buttonAction = new ButtonAction(this);
			money07Button.addActionListener(buttonAction);
		}
		return money07Button;
	}
	/**
	 * This method initializes money08Button	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getMoney08Button() {
		if (money08Button == null) {
			money08Button = new JButton();
			money08Button.setActionCommand("/jinq.GIF");
			money08Button.setIcon(new ImageIcon(getClass().getResource("ico/jinq.GIF")));

			buttonAction = new ButtonAction(this);
			money08Button.addActionListener(buttonAction);
		}
		return money08Button;
	}
	
	/**
	 * 按钮监听类
	 */
	class ButtonAction implements ActionListener{
		private Ico ico = null;
		public ButtonAction(Ico ico){
			this.ico = ico;
			
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String actionCommand = e.getActionCommand();
			if("yesButton".equals(actionCommand)){//单击的是确定按钮

				String title = this.ico.titleTextField.getText();
				if(title != null && title.startsWith("ico")){
					String key = this.ico.titleTextField.getText().replace("ico", "").trim();
					String value = this.ico.map.get(key);
					String s =  "";
					System.out.println();
					s += tt.wordTextPane.getText().substring(0,ico.index);
					s += value;
					s += tt.wordTextPane.getText().substring(ico.index);
					
					ico.tt.setContent(s);
					ico.tt.dealWith(s);
					
				}
				dispose();
			} else if("noButton".equals(actionCommand)){//单击是取消按钮
				dispose();
			} else {
				String path = e.getActionCommand();
				path = "ico" + path;
				titleTextField.setText(path);
				icoLabel1.setIcon(new ImageIcon(getClass().getResource(path)));
			}
			
			
		}
		
		
	}

	/**
	 * This method initializes icoScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getIcoScrollPane() {
		if (icoScrollPane == null) {
			icoLabel1 = new JLabel();
			icoLabel1.setText("");
			icoLabel1.setBounds(new Rectangle(165, 46, 63, 63));
			icoLabel1.setHorizontalAlignment(SwingConstants.CENTER);
			icoScrollPane = new JScrollPane();
			icoScrollPane.setBounds(new Rectangle(165, 46, 63, 63));
			icoScrollPane.setViewportView(icoLabel1);
		}
		return icoScrollPane;
	}

}
