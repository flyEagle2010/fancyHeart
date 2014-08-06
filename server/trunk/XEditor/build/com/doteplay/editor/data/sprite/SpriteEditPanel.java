package com.doteplay.editor.data.sprite;

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
import com.doteyplay.game.constants.DamageType;
import com.doteyplay.game.constants.sprite.SpritePositionType;
import com.doteyplay.game.constants.sprite.SpritePropType;
import com.doteyplay.game.constants.sprite.SpriteQualityType;

public class SpriteEditPanel extends EditorPanel<SpriteData>
{

	/** * */
	private static final long serialVersionUID = 1L;
	public SpriteData data; // @jve:decl-index=0:

	private JTabbedPane jTabbedPane = null; // @jve:decl-index=0:visual-constraint="625,103"
	private JPanel basejPanel = null;
	private JButton nameButton;
	private JButton addPropButton = null;
	// private JToolBar proptoolBar;

	private JTabbedPane propJtabbedPane = null;

	private SpriteStarPanel starPanel = null;
	private List<SpriteQualityPropPanel> propPanelList = new ArrayList<SpriteQualityPropPanel>(
			SpriteQualityType.values().length);
	private JLabel label_1;
	private JButton button;

	private JComboBox attackTypeComboBox = null;
	private JComboBox positionTypeComboBox = null;
	private JLabel label_2;
	private JLabel label_3;
	private JLabel label_4;
	private JTextField imageIdField;
	private JTextField cardIdField;

	/**
	 * This method initializes
	 */
	public SpriteEditPanel()
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
			jTabbedPane.addTab("基础数据", null, getBasejPanel(), null);
			jTabbedPane.addTab("属性", null, getPropJtabbedPane(), null);
			jTabbedPane.addTab("星阶", null, getStarJPanel(), null);
		}
		return jTabbedPane;
	}

	private JComboBox getAttackTypeComboBox()
	{
		if (attackTypeComboBox == null)
		{
			attackTypeComboBox = new JComboBox();
			attackTypeComboBox.setBounds(new Rectangle(319, 10, 99, 21));

			attackTypeComboBox
					.addItemListener(new java.awt.event.ItemListener()
					{
						public void itemStateChanged(java.awt.event.ItemEvent e)
						{
							if (!inited)
								return;
							int i = attackTypeComboBox.getSelectedIndex();
							if (i == data.gameData.attackType.ordinal())
								return;
							data.gameData.attackType = DamageType
									.values()[i];
							setModified(true);
						}
					});
			for (DamageType type : DamageType.values())
			{
				attackTypeComboBox.addItem(type);
			}
		}
		return attackTypeComboBox;
	}

	private JComboBox getPositionTypeComboBox()
	{
		if (positionTypeComboBox == null)
		{
			positionTypeComboBox = new JComboBox();
			positionTypeComboBox.setBounds(new Rectangle(319, 42, 99, 21));

			positionTypeComboBox
					.addItemListener(new java.awt.event.ItemListener()
					{
						public void itemStateChanged(java.awt.event.ItemEvent e)
						{
							if (!inited)
								return;
							int i = positionTypeComboBox.getSelectedIndex();
							if (i == data.gameData.positionType.ordinal())
								return;
							data.gameData.positionType = SpritePositionType
									.values()[i];
							setModified(true);
						}
					});
			for (SpritePositionType type : SpritePositionType.values())
			{
				positionTypeComboBox.addItem(type);
			}
		}
		return positionTypeComboBox;
	}
	private JTabbedPane getPropJtabbedPane()
	{
		if (propJtabbedPane == null)
		{
			propJtabbedPane = new JTabbedPane();
			propJtabbedPane.setSize(new Dimension(52, 350));
			for (SpriteQualityType type : SpriteQualityType.values())
			{
				propJtabbedPane.addTab(type.toString(), null,
						getPropjPanel(type), null);
				// break;
			}
		}
		return propJtabbedPane;
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

			JLabel label = new JLabel("\u7CBE\u7075\u540D\u79F0\uFF1A");
			label.setBounds(10, 10, 64, 15);
			basejPanel.add(label);
			basejPanel.add(getLabel_1());
			basejPanel.add(getButton());

			nameButton.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					data.nameId = selectText(nameButton);
				}
			});
			
			basejPanel.add(getAttackTypeComboBox());
			basejPanel.add(getPositionTypeComboBox());
			basejPanel.add(getLabel_2());
			basejPanel.add(getLabel_3());
			basejPanel.add(getLabel_4());
			
			imageIdField = new JTextField();
			imageIdField.setBounds(72, 76, 99, 21);
			basejPanel.add(imageIdField);
			imageIdField.setColumns(10);
			imageIdField
			.addFocusListener(new java.awt.event.FocusAdapter() {
				public void focusLost(java.awt.event.FocusEvent e) {
					if (!inited)
						return;
					data.gameData.imageId = imageIdField.getText();
					setModified(true);
				}
			});
			
			cardIdField = new JTextField();
			cardIdField.setColumns(10);
			cardIdField.setBounds(72, 104, 99, 21);
			
			cardIdField
			.addFocusListener(new java.awt.event.FocusAdapter() {
				public void focusLost(java.awt.event.FocusEvent e) {
					if (!inited)
						return;
					data.gameData.cardId = cardIdField.getText();
					setModified(true);
				}
			});
			
			basejPanel.add(cardIdField);
			
			JLabel label_5 = new JLabel("\u5361\u724C\u56FE\u7247\uFF1A");
			label_5.setBounds(10, 107, 64, 15);
			basejPanel.add(label_5);
		}
		return basejPanel;
	}

	private JPanel getStarJPanel()
	{
		if (starPanel == null)
			starPanel = new SpriteStarPanel(this);
		return starPanel;
	}

	/**
	 * This method initializes basejPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPropjPanel(SpriteQualityType type)
	{
		SpriteQualityPropPanel propJpanel = null;

		if (propPanelList.size() > type.ordinal())
			propJpanel = propPanelList.get(type.ordinal());

		if (propJpanel == null)
		{
			propJpanel = new SpriteQualityPropPanel(type, this);
			propPanelList.add(type.ordinal(), propJpanel);
		}
		return propJpanel;
	}

	/**
	 * This method initializes addRemoveConditionButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getAddPropButton()
	{
		if (addPropButton == null)
		{
			addPropButton = new JButton();
			addPropButton.setIcon(new ImageIcon(getClass().getResource(
					"/img/icon/new.png")));
			addPropButton.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					// SpriteBasePropData propData = new SpriteBasePropData();
					// data.spriteData.propDataList.add(propData);
					//
					// Object[] rowData =
					// { SpritePropType.values()[0], new Integer(0),
					// new Integer(0), new Integer(0) };
					// propTableModel.addRow(rowData);
					// setModified(true);
				}
			});
		}
		return addPropButton;
	}

	/**
	 * 
	 */
	@Override
	public SpriteData getData()
	{
		return data;
	}

	@Override
	protected void initData(SpriteData bd)
	{
		data = bd;
		this.nameButton.setText(DataManager.getTextById(data.nameId));
		this.getButton().setText(
				DataManager.getTextById(data.gameData.descId));
		
		this.attackTypeComboBox.setSelectedItem(data.gameData.attackType);
		this.positionTypeComboBox.setSelectedItem(data.gameData.positionType);
		
		this.imageIdField.setText(data.gameData.imageId);
		this.cardIdField.setText(data.gameData.cardId);

		for (SpriteQualityPropPanel panel : propPanelList)
		{
			panel.init(data.gameData.propDataList.subList(panel
					.getQualityType().ordinal()
					* SpritePropType.values().length, (panel.getQualityType()
					.ordinal() + 1) * SpritePropType.values().length));
		}

		this.starPanel.init(data.gameData.starDataList);

		inited = true;

	}

	@Override
	protected boolean saveData()
	{
		return true;
	}

	// private JToolBar getProptoolBar()
	// {
	// if (proptoolBar == null)
	// {
	// proptoolBar = new JToolBar();
	// proptoolBar.setBounds(0, 0, 680, 25);
	// proptoolBar.add(getAddPropButton());
	// }
	// return proptoolBar;
	// }

	private JLabel getLabel_1()
	{
		if (label_1 == null)
		{
			label_1 = new JLabel("\u7CBE\u7075\u63CF\u8FF0\uFF1A");
			label_1.setBounds(10, 45, 64, 15);
		}
		return label_1;
	}

	private JButton getButton()
	{
		if (button == null)
		{
			button = new JButton();
			button.setBounds(new Rectangle(72, 152, 321, 21));
			button.setBounds(72, 41, 165, 21);
			button.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					data.gameData.descId = selectText(button);
				}
			});
		}
		return button;
	}
	private JLabel getLabel_2() {
		if (label_2 == null) {
			label_2 = new JLabel("\u653B\u51FB\u65B9\u5F0F\uFF1A");
			label_2.setBounds(260, 10, 64, 21);
		}
		return label_2;
	}
	private JLabel getLabel_3() {
		if (label_3 == null) {
			label_3 = new JLabel("\u7AD9\u4F4D\uFF1A");
			label_3.setBounds(260, 41, 64, 21);
		}
		return label_3;
	}
	private JLabel getLabel_4() {
		if (label_4 == null) {
			label_4 = new JLabel("\u5F62\u8C61\u56FE\u7247\uFF1A");
			label_4.setBounds(10, 79, 64, 15);
		}
		return label_4;
	}
}