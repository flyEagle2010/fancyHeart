package com.doteplay.editor.data.buff;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import com.doteplay.editor.common.DataManager;
import com.doteplay.editor.common.EditorPanel;
import com.doteplay.editor.data.buff.BuffData;
import com.doteyplay.game.constants.DamageType;
import javax.swing.JCheckBox;

public class BuffEditPanel extends EditorPanel<BuffData>
{

	/** * */
	private static final long serialVersionUID = 1L;
	public BuffData data; // @jve:decl-index=0:

	private JTabbedPane jTabbedPane = null; // @jve:decl-index=0:visual-constraint="625,103"
	private JPanel basejPanel = null;
	private JButton nameButton;
	private JLabel label_4;
	private JTextField damageField;
	private JTextField healthField;
	private JCheckBox canRemovecheckBox;
	private JCheckBox canMovecheckBox;
	private JCheckBox canUseSkillcheckBox;
	private JCheckBox canHealthcheckBox;
	private JCheckBox isPdcheckBox;
	private JCheckBox isMdcheckBox;
	private JCheckBox isOfflineSavecheckBox;
	private JCheckBox isOfflineReducecheckBox;
	private JTextField apTextField;
	private JLabel label;
	private JTextField pdTextField;
	private JLabel label_1;
	private JTextField mdTextField;
	private JLabel label_2;
	private JTextField crhTextField;
	private JLabel label_3;
	private JTextField dodgeTextField;
	private JLabel label_6;
	private JTextField timeField;
	private JLabel label_7;
	
	private JComboBox damageTypeComboBox = null;

	/**
	 * This method initializes
	 */
	public BuffEditPanel()
	{
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 */
	private void initialize()
	{
		this.setLayout(new BorderLayout());
		this.setSize(new Dimension(685, 377));
		this.add(getJTabbedPane(), BorderLayout.CENTER);
	}

	/**
	 * This method initializes jTabbedPane
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getJTabbedPane()
	{
		if (jTabbedPane == null)
		{
			jTabbedPane = new JTabbedPane();
			jTabbedPane.setSize(new Dimension(52, 350));
			jTabbedPane.addTab("»ù´¡Êý¾Ý", null, getBasejPanel(), null);
		}
		return jTabbedPane;
	}

	/**
	 * This method initializes basejPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getBasejPanel()
	{
		if (basejPanel == null)
		{
			basejPanel = new JPanel();
			basejPanel.setLayout(null);
			basejPanel.setSize(new Dimension(607, 273));

			nameButton = new JButton();
			nameButton.setBounds(new Rectangle(72, 152, 321, 21));
			nameButton.setBounds(72, 10, 165, 21);
			basejPanel.add(nameButton);

			JLabel lblBuff = new JLabel("buff\u540D\u79F0\uFF1A");
			lblBuff.setBounds(10, 10, 64, 15);
			basejPanel.add(lblBuff);

			nameButton.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					data.nameId = selectText(nameButton);
				}
			});
			basejPanel.add(getLabel_4());
			
			damageField = new JTextField();
			damageField.setBounds(312, 10, 66, 21);
			basejPanel.add(damageField);
			damageField.setColumns(10);
			damageField
			.addFocusListener(new java.awt.event.FocusAdapter() {
				public void focusLost(java.awt.event.FocusEvent e) {
					if (!inited)
						return;
					data.gameData.damage = Integer.parseInt(damageField.getText());
					setModified(true);
				}
			});
			
			healthField = new JTextField();
			healthField.setColumns(10);
			healthField.setBounds(450, 10, 75, 21);
			
			healthField
			.addFocusListener(new java.awt.event.FocusAdapter() {
				public void focusLost(java.awt.event.FocusEvent e) {
					if (!inited)
						return;
					data.gameData.health =Integer.parseInt(healthField.getText()) ;
					setModified(true);
				}
			});
			
			basejPanel.add(healthField);
			
			JLabel label_5 = new JLabel("\u6301\u7EED\u6062\u590D\uFF1A");
			label_5.setBounds(388, 13, 113, 15);
			basejPanel.add(label_5);
			
			canRemovecheckBox = new JCheckBox();
			canRemovecheckBox.setText("\u662F\u5426\u88AB\u6253\u65AD");
			canRemovecheckBox.setBounds(new Rectangle(5, 315, 89, 21));
			canRemovecheckBox.setBounds(0, 95, 89, 21);
			
			canRemovecheckBox.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if(!inited)
						return;
					data.gameData.canRemove=canRemovecheckBox.isSelected();
					setModified(true);
				}
			});
			
			basejPanel.add(canRemovecheckBox);
			
			canMovecheckBox = new JCheckBox();
			canMovecheckBox.setText("\u662F\u5426\u53EF\u79FB\u52A8");
			canMovecheckBox.setBounds(new Rectangle(5, 315, 89, 21));
			canMovecheckBox.setBounds(102, 94, 89, 21);
			
			canMovecheckBox.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if(!inited)
						return;
					data.gameData.canMove=canMovecheckBox.isSelected();
					setModified(true);
				}
			});
			
			basejPanel.add(canMovecheckBox);
			
			canUseSkillcheckBox = new JCheckBox();
			canUseSkillcheckBox.setText("\u662F\u5426\u53EF\u4F7F\u7528\u6280\u80FD");
			canUseSkillcheckBox.setBounds(new Rectangle(5, 315, 89, 21));
			canUseSkillcheckBox.setBounds(199, 94, 109, 21);
			
			canUseSkillcheckBox.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if(!inited)
						return;
					data.gameData.canUseSkill=canUseSkillcheckBox.isSelected();
					setModified(true);
				}
			});
			
			basejPanel.add(canUseSkillcheckBox);
			
			canHealthcheckBox = new JCheckBox();
			canHealthcheckBox.setText("\u662F\u5426\u80FD\u88AB\u6CBB\u7597");
			canHealthcheckBox.setBounds(new Rectangle(5, 315, 89, 21));
			canHealthcheckBox.setBounds(316, 94, 99, 21);
			
			canHealthcheckBox.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if(!inited)
						return;
					data.gameData.canHealth=canHealthcheckBox.isSelected();
					setModified(true);
				}
			});
			
			basejPanel.add(canHealthcheckBox);
			
			isPdcheckBox = new JCheckBox();
			isPdcheckBox.setText("\u662F\u5426\u7269\u7406\u514D\u75AB");
			isPdcheckBox.setBounds(new Rectangle(5, 315, 89, 21));
			isPdcheckBox.setBounds(416, 94, 109, 21);
			
			isPdcheckBox.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if(!inited)
						return;
					data.gameData.isPd=isPdcheckBox.isSelected();
					setModified(true);
				}
			});
			
			basejPanel.add(isPdcheckBox);
			
			isMdcheckBox = new JCheckBox();
			isMdcheckBox.setText("\u662F\u5426\u6CD5\u672F\u514D\u75AB");
			isMdcheckBox.setBounds(new Rectangle(5, 315, 89, 21));
			isMdcheckBox.setBounds(528, 95, 109, 21);
			
			isMdcheckBox.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if(!inited)
						return;
					data.gameData.isMd=isMdcheckBox.isSelected();
					setModified(true);
				}
			});
			
			basejPanel.add(isMdcheckBox);
			
			isOfflineSavecheckBox = new JCheckBox();
			isOfflineSavecheckBox.setText("\u662F\u5426\u79BB\u7EBF\u5B58\u50A8");
			isOfflineSavecheckBox.setBounds(new Rectangle(5, 315, 89, 21));
			isOfflineSavecheckBox.setBounds(0, 118, 109, 21);
			
			isOfflineSavecheckBox.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if(!inited)
						return;
					data.gameData.isOfflineSave=isOfflineSavecheckBox.isSelected();
					setModified(true);
				}
			});
			
			basejPanel.add(isOfflineSavecheckBox);
			
			isOfflineReducecheckBox = new JCheckBox();
			isOfflineReducecheckBox.setText("\u662F\u5426\u79BB\u7EBF\u8BA1\u65F6");
			isOfflineReducecheckBox.setBounds(new Rectangle(5, 315, 89, 21));
			isOfflineReducecheckBox.setBounds(112, 118, 109, 21);
			
			isOfflineReducecheckBox.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if(!inited)
						return;
					data.gameData.isOfflineReduce=isOfflineReducecheckBox.isSelected();
					setModified(true);
				}
			});
			
			basejPanel.add(isOfflineReducecheckBox);
			
			apTextField = new JTextField();
			apTextField.setColumns(10);
			apTextField.setBounds(201, 41, 99, 21);
			
			apTextField
			.addFocusListener(new java.awt.event.FocusAdapter() {
				public void focusLost(java.awt.event.FocusEvent e) {
					if (!inited)
						return;
					data.gameData.ap = Integer.parseInt(apTextField.getText());
					setModified(true);
				}
			});
			
			basejPanel.add(apTextField);
			
			label = new JLabel("\u653B\u51FB\u529B\uFF1A");
			label.setBounds(152, 44, 64, 15);
			basejPanel.add(label);
			
			pdTextField = new JTextField();
			pdTextField.setColumns(10);
			pdTextField.setBounds(341, 41, 99, 21);
			
			pdTextField
			.addFocusListener(new java.awt.event.FocusAdapter() {
				public void focusLost(java.awt.event.FocusEvent e) {
					if (!inited)
						return;
					data.gameData.pd = Integer.parseInt(pdTextField.getText());
					setModified(true);
				}
			});
			
			basejPanel.add(pdTextField);
			
			label_1 = new JLabel("\u7269\u9632\uFF1A");
			label_1.setBounds(308, 44, 64, 15);
			basejPanel.add(label_1);
			
			mdTextField = new JTextField();
			mdTextField.setColumns(10);
			mdTextField.setBounds(341, 72, 99, 21);
			
			mdTextField
			.addFocusListener(new java.awt.event.FocusAdapter() {
				public void focusLost(java.awt.event.FocusEvent e) {
					if (!inited)
						return;
					data.gameData.md = Integer.parseInt(mdTextField.getText());
					setModified(true);
				}
			});
			
			
			basejPanel.add(mdTextField);
			
			label_2 = new JLabel("\u6CD5\u9632\uFF1A");
			label_2.setBounds(308, 75, 64, 15);
			basejPanel.add(label_2);
			
			crhTextField = new JTextField();
			crhTextField.setColumns(10);
			crhTextField.setBounds(59, 72, 99, 21);
			
			crhTextField
			.addFocusListener(new java.awt.event.FocusAdapter() {
				public void focusLost(java.awt.event.FocusEvent e) {
					if (!inited)
						return;
					data.gameData.crh = Integer.parseInt(crhTextField.getText());
					setModified(true);
				}
			});
			
			basejPanel.add(crhTextField);
			
			label_3 = new JLabel("\u66B4\u51FB\uFF1A");
			label_3.setBounds(10, 72, 64, 15);
			basejPanel.add(label_3);
			
			dodgeTextField = new JTextField();
			dodgeTextField.setColumns(10);
			dodgeTextField.setBounds(199, 72, 99, 21);
			
			dodgeTextField
			.addFocusListener(new java.awt.event.FocusAdapter() {
				public void focusLost(java.awt.event.FocusEvent e) {
					if (!inited)
						return;
					data.gameData.dodge = Integer.parseInt(dodgeTextField.getText());
					setModified(true);
				}
			});
			
			basejPanel.add(dodgeTextField);
			
			label_6 = new JLabel("\u95EA\u907F\uFF1A");
			label_6.setBounds(166, 75, 64, 15);
			basejPanel.add(label_6);
			
			timeField = new JTextField();
			timeField.setColumns(10);
			timeField.setBounds(597, 10, 66, 21);
			
			timeField
			.addFocusListener(new java.awt.event.FocusAdapter() {
				public void focusLost(java.awt.event.FocusEvent e) {
					if (!inited)
						return;
					data.gameData.time = Integer.parseInt(timeField.getText());
					setModified(true);
				}
			});
			
			basejPanel.add(timeField);
			
			label_7 = new JLabel("\u6301\u7EED\u65F6\u95F4\uFF1A");
			label_7.setBounds(535, 13, 64, 15);
			basejPanel.add(label_7);
			
			JLabel label_8 = new JLabel();
			label_8.setText("\u4F24\u5BB3\u7C7B\u578B:");
			label_8.setBounds(new Rectangle(188, 65, 57, 18));
			label_8.setBounds(0, 42, 57, 18);
			basejPanel.add(label_8);
			
			basejPanel.add(getDamageTypeComboBox());
			
		}
		return basejPanel;
	}

	/**
	 * This method initializes damageTypeComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getDamageTypeComboBox()
	{
		if (damageTypeComboBox == null)
		{
			damageTypeComboBox = new JComboBox();
			damageTypeComboBox.setBounds(new Rectangle(59, 41, 89, 21));
			damageTypeComboBox
					.addItemListener(new java.awt.event.ItemListener()
					{
						public void itemStateChanged(java.awt.event.ItemEvent e)
						{
							if (e.getStateChange() == e.DESELECTED)
								return;
							if (!inited)
								return;
							data.gameData.damageType = (DamageType) damageTypeComboBox
									.getSelectedItem();
							setModified(true);
						}
					});

			for (int i = 0; i < DamageType.values().length; i++)
			{
				damageTypeComboBox.addItem(DamageType.values()[i]);
			}
		}
		return damageTypeComboBox;
	}
	
	/**
	 * 
	 */
	@Override
	public BuffData getData()
	{
		return data;
	}

	@Override
	protected void initData(BuffData bd)
	{
		data = bd;
		this.nameButton.setText(DataManager.getTextById(data.nameId));
		this.damageTypeComboBox.setSelectedItem(data.gameData.damageType);
		
		this.damageField.setText(String.valueOf(data.gameData.damage));
		this.healthField.setText(String.valueOf(data.gameData.health));
		this.timeField.setText(String.valueOf(data.gameData.time));
		this.apTextField.setText(String.valueOf(data.gameData.ap));
		this.pdTextField.setText(String.valueOf(data.gameData.pd));
		this.mdTextField.setText(String.valueOf(data.gameData.md));
		this.crhTextField.setText(String.valueOf(data.gameData.crh));
		this.dodgeTextField.setText(String.valueOf(data.gameData.dodge));
		
		this.canRemovecheckBox.setSelected(data.gameData.canRemove);
		this.canMovecheckBox.setSelected(data.gameData.canMove);
		this.canUseSkillcheckBox.setSelected(data.gameData.canUseSkill);
		this.canHealthcheckBox.setSelected(data.gameData.canHealth);
		this.isPdcheckBox.setSelected(data.gameData.isPd);
		this.isMdcheckBox.setSelected(data.gameData.isMd);
		this.isOfflineSavecheckBox.setSelected(data.gameData.isOfflineSave);
		this.isOfflineReducecheckBox.setSelected(data.gameData.isOfflineReduce);

		inited = true;

	}

	@Override
	protected boolean saveData()
	{
		return true;
	}
	private JLabel getLabel_4() {
		if (label_4 == null) {
			label_4 = new JLabel("\u6301\u7EED\u4F24\u5BB3\uFF1A");
			label_4.setBounds(250, 13, 64, 15);
		}
		return label_4;
	}
}
