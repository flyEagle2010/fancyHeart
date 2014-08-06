package com.doteplay.editor.tools.tageditor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class UnderLine extends JDialog{

	private JPanel totalPanel = null; // @jve:decl-index=0:visual-constraint="164,56"
	private JLabel backLabel = null;
	private JTextField backTextField = null;
	private JLabel isLoadLabel = null;
	private ButtonGroup isloadButtonGroup = null;
	private JRadioButton isloadingYesRadioButton = null;
	private JPanel isloadGroupPanel = null;
	private JRadioButton isloadingNoRadioButton = null;
	private JLabel parameterLabel = null;
	private JScrollPane parameterScrollPane = null;
	private JTextArea parameterTextArea = null;
	private JButton yesButton = null;
	private JButton noButton = null;
	private JTextArea explainTextArea = null;

	/**
	 * 传过来的参数
	 */
	private int begin = 0;
	private int end = 0;
	private TagEditor tt = null;

	public String backOrder = ""; // 回调命令
	public String isLoading = "1"; // 是否等待 // @jve:decl-index=0:
	public String parameter = "";// 参数  //  @jve:decl-index=0:

	/**
	 * 监听
	 */
	private ButtonActionListener buttonAction = null;

	/*
	 * 构造函数
	 */
	public UnderLine(int begin, int end, TagEditor tt) {
		this.begin = begin;
		this.end = end;
		this.tt = tt;

	}

	/**
	 * This method initializes totalPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	public JPanel getTotalPanel() {
		if (totalPanel == null) {
			parameterLabel = new JLabel();
			parameterLabel.setBounds(new Rectangle(1, 61, 63, 18));
			parameterLabel.setPreferredSize(new Dimension(55, 18));
			parameterLabel.setText("参         数:");
			isLoadLabel = new JLabel();
			isLoadLabel.setBounds(new Rectangle(0, 32, 63, 18));
			isLoadLabel.setText("是否等待:");
			backLabel = new JLabel();
			backLabel.setBounds(new Rectangle(0, 5, 63, 18));
			backLabel.setText("回调指令:");
			totalPanel = new JPanel();
			totalPanel.setLayout(null);
			totalPanel.setBounds(new Rectangle(0, 0, 255, 238));
			totalPanel.add(backLabel, null);
			totalPanel.add(getBackTextField(), null);
			totalPanel.add(isLoadLabel, null);
			totalPanel.add(getIsloadGroupPanel(), null);
			totalPanel.add(parameterLabel, null);
			totalPanel.add(getParameterScrollPane(), null);
			totalPanel.add(getYesButton(), null);
			totalPanel.add(getNoButton(), null);
			totalPanel.add(getExplainTextArea(), null);
			
			
			/*
			 * 初始化控件里面的数据-----begin
			 */
			if ((begin>0)&&'>' == tt.wordTextPane.getText().charAt(
					begin - 1)
					&& '$' == tt.wordTextPane.getText().charAt(
							begin - 2)) {// 则要看是不是在原来有的标签中
//				if (tt.resultTextPane.getSelectionStart() != 0) {
					String s = tt.wordTextPane.getText().substring(tt.wordTextPane.getText()
							   .substring(0, begin).lastIndexOf("<#"),begin);
					String[] sArray = s.split("\\|");
					String[] chaolianjie = sArray[2].split("@");
					this.backOrder = chaolianjie[0].split("=")[1];
					if(chaolianjie.length>1){
						this.isLoading = chaolianjie[1];
					}
					if(chaolianjie.length>2){
						String tempparameter = chaolianjie[2].replaceAll("\\*", ","); 
						this.parameter = tempparameter; 
					}
					this.backTextField.setText(this.backOrder);
					if("0".equals(this.isLoading.trim())){
						this.isloadingNoRadioButton.setSelected(true);
					}
					if("1".equals(this.isLoading.trim())){
						this.isloadingYesRadioButton.setSelected(true);
					}
					this.parameterTextArea.setText(this.parameter);
//				}
			}

			/*
			 * 初始化控件里面的数据-----end
			 */
			
			

		}
		return totalPanel;
	}

	/**
	 * This method initializes backTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getBackTextField() {
		if (backTextField == null) {
			backTextField = new JTextField();
			backTextField.setBounds(new Rectangle(64, 3, 185, 22));
			backTextField.addFocusListener(new FocusListener() {
				@Override
				public void focusGained(FocusEvent e) {
					// TODO Auto-generated method stub
				}

				@Override
				public void focusLost(FocusEvent e) {
					// TODO Auto-generated method stub
					backOrder = backTextField.getText();
				}

			});
		}
		return backTextField;
	}

	/**
	 * This method initializes isloadingYesRadioButton
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getIsloadingYesRadioButton() {
		if (isloadingYesRadioButton == null) {
			isloadingYesRadioButton = new JRadioButton();
			isloadingYesRadioButton.setText("是");
			isloadingYesRadioButton.setSelected(true);
			isloadingYesRadioButton.setBounds(new Rectangle(0, 0, 45, 24));
			// isloadingNoRadioButton.setActionCommand("1");
			isloadingYesRadioButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					isLoading = "1";// 没有选中
				}
			});
		}
		return isloadingYesRadioButton;
	}

	/**
	 * This method initializes isloadGroupPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getIsloadGroupPanel() {
		if (isloadGroupPanel == null) {
			isloadButtonGroup = new ButtonGroup();
			isloadGroupPanel = new JPanel();
			isloadGroupPanel.setLayout(null);
			isloadGroupPanel.setBounds(new Rectangle(64, 30, 185, 22));
			isloadButtonGroup.add(getIsloadingYesRadioButton());
			isloadButtonGroup.add(getIsloadingNoRadioButton());
			isloadGroupPanel.add(getIsloadingYesRadioButton(), null);
			isloadGroupPanel.add(getIsloadingNoRadioButton(), null);
		}
		return isloadGroupPanel;
	}

	/**
	 * This method initializes isloadingNoRadioButton
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getIsloadingNoRadioButton() {
		if (isloadingNoRadioButton == null) {
			isloadingNoRadioButton = new JRadioButton();
			isloadingNoRadioButton.setBounds(new Rectangle(45, 0, 45, 24));
			isloadingNoRadioButton.setText("否");
			// isloadingNoRadioButton.setActionCommand("0");
			isloadingNoRadioButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					isLoading = "0";// 没有选中
				}
			});
		}
		return isloadingNoRadioButton;
	}

	/**
	 * This method initializes parameterScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getParameterScrollPane() {
		if (parameterScrollPane == null) {
			parameterScrollPane = new JScrollPane();
			parameterScrollPane.setBounds(new Rectangle(64, 62, 185, 141));
			parameterScrollPane.setViewportView(getParameterTextArea());
		}
		return parameterScrollPane;
	}

	/**
	 * This method initializes parameterTextArea
	 * 
	 * @return javax.swing.JTextArea
	 */
	private JTextArea getParameterTextArea() {
		if (parameterTextArea == null) {
			parameterTextArea = new JTextArea();
			parameterTextArea.setLineWrap(true);
			parameterTextArea.addFocusListener(new FocusListener() {
				@Override
				public void focusGained(FocusEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void focusLost(FocusEvent e) {
					// TODO Auto-generated method stub
					parameter = parameterTextArea.getText();
				}

			});
		}
		return parameterTextArea;
	}

	/**
	 * This method initializes yesButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getYesButton() {
		if (yesButton == null) {
			yesButton = new JButton();
			yesButton.setBounds(new Rectangle(74, 207, 60, 25));
			yesButton.setText("确定");
			yesButton.setActionCommand("yesButton");
			buttonAction = new ButtonActionListener(this);
			yesButton.addActionListener(buttonAction);

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
			noButton.setBounds(new Rectangle(140, 207, 60, 25));
			noButton.setActionCommand("yesButton");
			buttonAction = new ButtonActionListener(this);
			noButton.addActionListener(buttonAction);
			noButton.setText("取消");
		}
		return noButton;
	}

	/**
	 * This method initializes explainTextArea
	 * 
	 * @return javax.swing.JTextArea
	 */
	private JTextArea getExplainTextArea() {
		if (explainTextArea == null) {
			explainTextArea = new JTextArea();
			explainTextArea.setBounds(new Rectangle(0, 84, 62, 93));
			explainTextArea.setLineWrap(true);
			explainTextArea.setBackground(new Color(238, 238, 238));
			explainTextArea.setFont(new Font("Dialog", Font.PLAIN, 12));
			explainTextArea.setText("注意：参数与参数之间请用\",\"隔开。如：1,张三,23,...。");
			explainTextArea.setForeground(Color.red);
		}
		return explainTextArea;
	}


	class ButtonActionListener implements ActionListener {
		private UnderLine ul = null;

		public ButtonActionListener(UnderLine ul) {

			this.ul = ul;

		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String actionCommand = e.getActionCommand();
			if ("yesButton".equals(actionCommand)) {// 点击确定按钮
				if (e.getActionCommand().equals("yesButton")) {// 点击确定按钮
					String str = "";
					// 两种情况第一种在原有的tag标签中编辑

					// 第二种建立新的tag标签
					if (begin == 0) {// 说明是起始位置---此事说明绝对不会是在原有的基础上编辑
						str += "<#|c=0|a=" + this.ul.backOrder + "@"
								+ this.ul.isLoading + "@";
						String tempParam = this.ul.parameter.replaceAll(",",
								"*");
						str += tempParam + "|$>";
						str += this.ul.tt.resultTextPane.getSelectedText();
						str += "</#>";
//						str += ul.tt.wordTextPane.getText().substring(end);
						if('>'==ul.tt.wordTextPane.getText().charAt(end-1)&&'$'==ul.tt.wordTextPane.getText().charAt(end-2)){
							str += ul.tt.wordTextPane.getText().substring(ul.tt.wordTextPane.getText().substring(0,end).lastIndexOf("<#"));
						} else {
							str += ul.tt.wordTextPane.getText().substring(end);
						} 
						ul.tt.setContent(str);
						ul.tt.dealWith(str);

					} else {

						if ('>' == ul.tt.wordTextPane.getText().charAt(
								begin - 1)
								&& '$' == ul.tt.wordTextPane.getText().charAt(
										begin - 2)) {// 则要看是不是在原来有的标签中
							if (ul.tt.resultTextPane.getSelectionStart() != 0) {
								String s = ul.tt.wordTextPane.getText()
										.substring(
												ul.tt.wordTextPane.getText()
														.substring(0, begin)
														.lastIndexOf("<#"),
												begin);
								String[] sArray = s.split("\\|");
								String tempParam = this.ul.parameter.replace(
										",", "*");
								sArray[2] = "a=" + this.ul.backOrder + "@"
										+ this.ul.isLoading + "@" + tempParam;

								String ss = "";
								for (int i = 0; i < sArray.length; i++) {
									ss += sArray[i];
									if (i != sArray.length - 1) {
										ss += "|";
									}
								}
								// ss += fc.tt.resultTextPane.getSelectedText()
								// + "</#>";
								String str2 = "";
								str2 += ul.tt.wordTextPane.getText().substring(
										0,
										ul.tt.wordTextPane.getText().substring(
												0, begin).lastIndexOf("<#"));
								str2 += ss;
								str2 += ul.tt.wordTextPane.getText().substring(
										begin);
								ul.tt.setContent(str2);
								ul.tt.dealWith(str2);

							} else {// 在开始位置为0的地方编辑
								String s = ul.tt.wordTextPane.getText()
										.substring(
												ul.tt.wordTextPane.getText()
														.substring(0, begin)
														.lastIndexOf("<#"),
												begin);
								String[] sArray = s.split("\\|");
								String tempParam = this.ul.parameter.replace(
										",", "*");
								sArray[2] = "a=" + this.ul.backOrder + "@"
										+ this.ul.isLoading + "@" + tempParam;

								String ss = "";
								for (int i = 0; i < sArray.length; i++) {
									ss += sArray[i];
									if (i != sArray.length - 1) {
										ss += "|";
									}
								}
								String str2 = "";
								str2 += ss;
								str2 += ul.tt.resultTextPane.getSelectedText()
										+ "</#>";
//								str2 += ul.tt.wordTextPane.getText().substring(end);
								if('>'==ul.tt.wordTextPane.getText().charAt(end-1)&&'$'==ul.tt.wordTextPane.getText().charAt(end-2)){
									str2 += ul.tt.wordTextPane.getText().substring(ul.tt.wordTextPane.getText().substring(0,end).lastIndexOf("<#"));
								} else {
									str2 += ul.tt.wordTextPane.getText().substring(end);
								} 
								ul.tt.setContent(str2);
								ul.tt.dealWith(str2);
							}

						} else {// 不是的话直接拼接字符串
							str += ul.tt.wordTextPane.getText().substring(0,
									begin);
							str += "<#|c=0|a=" + this.ul.backOrder + "@"
									+ this.ul.isLoading + "@";
							String tempParam = this.ul.parameter.replace(",",
									"*");
							str += tempParam + "|$>";

							str += ul.tt.resultTextPane.getSelectedText();
							str += "</#>";
//							str += ul.tt.wordTextPane.getText().substring(end);
							if('>'==ul.tt.wordTextPane.getText().charAt(end-1)&&'$'==ul.tt.wordTextPane.getText().charAt(end-2)){
								str += ul.tt.wordTextPane.getText().substring(ul.tt.wordTextPane.getText().substring(0,end).lastIndexOf("<#"));
							} else {
								str += ul.tt.wordTextPane.getText().substring(end);
							} 
							ul.tt.setContent(str);
							ul.tt.dealWith(str);

						}

					}

//					this.ul.jf.setVisible(false);
//					this.ul.jf = null;// 释放内存
					dispose();
				}

				if ("noButton".equals(actionCommand)) {// 点击取消按钮
//					this.ul.jf.setVisible(false);
//					this.ul.jf = null;// 释放内存
					dispose();
				}

			}

		}

	}

}
