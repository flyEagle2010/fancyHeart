/**
 * 
 */
package com.doteplay.editor.data.skill;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.tree.DefaultMutableTreeNode;

import com.doteplay.editor.XEditor;
import com.doteplay.editor.common.BaseData;
import com.doteplay.editor.common.DataManager;
import com.doteplay.editor.common.EditorPanel;
import com.doteplay.editor.data.buff.BuffData;
import com.doteplay.editor.util.Util;
import com.doteyplay.game.CommonConstants;
import com.doteyplay.game.constants.ActivateEvent;
import com.doteyplay.game.constants.DamageType;
import com.doteyplay.game.constants.skill.SkillActionType;
import com.doteyplay.game.constants.skill.SkillAmmoType;
import com.doteyplay.game.constants.skill.SkillEffectRange;
import com.doteyplay.game.constants.skill.SkillEffectType;
import com.doteyplay.game.constants.skill.SkillTargetSelectType;
import com.doteyplay.game.constants.sprite.SpritePropType;
import com.doteyplay.game.gamedata.data.skill.SkillLevelGameData;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.DropMode;

/**      
 */
public class SkillEditPanel extends EditorPanel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8147312179751206278L;

	public SkillData skillData;

	private JPanel attributePanel = null;
	private JComboBox actionTypeComboBox = null;
	private JLabel nameLabel = null;
	private JLabel dscpLabel = null;
	private JLabel zhudongTypeLabel;
	private JLabel Label1;
	private DefaultMutableTreeNode stateTreeRoot = null; // @jve:decl-index=0:
	private JComboBox effectRangeComboBox = null;
	private JLabel affectLabel;
	private JTabbedPane jTabbedPane = null;
	private JPanel jPanel3 = null;
	private JLabel DamageTypejLabel;
	private JComboBox damageTypeComboBox = null;

	private JComboBox ammoComboBox;
	private JLabel Ammolabel;
	private JComboBox eventComboBox;
	private JLabel eventlabel;
	private JLabel label_2;
	private JTextField levelCountField;
	private JTextField resSkillActionField;
	private JTextField resSkillEffectField;
	private JTextField resBeAttackField;

	private JButton nameButton;
	private JButton descButton;

	private JComboBox selectComboBox;
	private JTextField eventParamField;
	private JLabel label_3;
	private JLabel label_4;
	private JTextField leadCountField;
	private JLabel label_5;
	private JTextField leadTimeDeltaField;

	/**
	 * This method initializes
	 * 
	 */
	public SkillEditPanel()
	{
		super();
		initialize();
	}

	@Override
	public void release()
	{
		super.release();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize()
	{
		this.setLayout(new BorderLayout());
		this.setSize(new Dimension(998, 601));
		this.add(getJTabbedPane(), BorderLayout.CENTER);
	}

	@Override
	public BaseData getData()
	{
		return skillData;
	}

	@Override
	protected void initData(BaseData bd)
	{
		// TODO initData
		inited = false;

		skillData = (SkillData) bd;

		this.nameButton.setText(DataManager.getTextById(skillData.nameId));
		this.descButton.setText(DataManager
				.getTextById(skillData.gameData.descId));

		this.actionTypeComboBox.setSelectedItem(skillData.gameData.actionType);
		this.effectRangeComboBox
				.setSelectedItem(skillData.gameData.effectRange);
		this.damageTypeComboBox.setSelectedItem(skillData.gameData.damageType);
		this.eventComboBox.setSelectedItem(skillData.gameData.event);
		this.selectComboBox.setSelectedItem(skillData.gameData.selectType);
		this.ammoComboBox.setSelectedItem(skillData.gameData.ammoType);

		this.resSkillActionField.setText(skillData.gameData.resSkillActionId);
		this.resSkillEffectField.setText(skillData.gameData.resSkillEffectId);
		this.resBeAttackField.setText(skillData.gameData.resBeAttackId);

		this.eventParamField.setText(skillData.gameData.eventParam);
		this.levelCountField.setText(String
				.valueOf(skillData.gameData.levelCount));
		this.leadCountField.setText(String
				.valueOf(skillData.gameData.leadCount));
		this.leadTimeDeltaField.setText(String
				.valueOf(skillData.gameData.leadTimeDelta));

		this.storageTimeField.setText(String
				.valueOf(skillData.gameData.storageTime));
		this.storageStartRateField.setText(String
				.valueOf(skillData.gameData.storageStartRate));
		this.storageStartValueField.setText(String
				.valueOf(skillData.gameData.storageStartValue));
		this.storageEndRateField.setText(String
				.valueOf(skillData.gameData.storageEndRate));
		this.storageEndValueField.setText(String
				.valueOf(skillData.gameData.storageEndValue));
		
		this.costField.setText(String.valueOf(skillData.gameData.cost));

		this.updatePropTable();
		inited = true;
	}

	@Override
	protected boolean saveData()
	{
		return true;
	}

	/**
	 * This method initializes attributePanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getAttributePanel()
	{
		if (attributePanel == null)
		{
			DamageTypejLabel = new JLabel();
			DamageTypejLabel.setBounds(new Rectangle(188, 65, 57, 18));
			DamageTypejLabel.setText("伤害类型:");
			affectLabel = new JLabel();
			affectLabel.setBounds(new Rectangle(4, 64, 57, 18));
			affectLabel.setText("影响类型:");
			Label1 = new JLabel();
			Label1.setBounds(new Rectangle(4, 126, 57, 21));
			Label1.setText("\u65BD\u6CD5\u52A8\u4F5C:");
			zhudongTypeLabel = new JLabel();
			zhudongTypeLabel.setBounds(new Rectangle(188, 4, 57, 18));
			zhudongTypeLabel.setText("\u4E3B\u52A8\u7C7B\u578B:");
			dscpLabel = new JLabel();
			dscpLabel.setBounds(new Rectangle(4, 28, 57, 18));
			dscpLabel.setText("技能描述:");
			nameLabel = new JLabel();
			nameLabel.setBounds(new Rectangle(4, 4, 57, 18));
			nameLabel.setText("技能名称:");
			attributePanel = new JPanel();
			attributePanel.setLayout(null);
			attributePanel.setPreferredSize(new Dimension(1, 260));
			attributePanel.add(nameLabel, null);
			attributePanel.add(dscpLabel, null);
			attributePanel.add(getActionTypeComboBox(), null);
			attributePanel.add(zhudongTypeLabel, null);
			attributePanel.add(Label1, null);
			attributePanel.add(getEffectRangeComboBox(), null);
			attributePanel.add(affectLabel, null);
			attributePanel.add(DamageTypejLabel, null);
			attributePanel.add(getDamageTypeComboBox(), null);

			nameButton = new JButton("");
			nameButton.setBounds(64, 4, 122, 23);

			nameButton.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					skillData.nameId = selectText(nameButton);
				}
			});

			attributePanel.add(nameButton);

			descButton = new JButton("");
			descButton.setBounds(64, 32, 304, 23);

			descButton.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					skillData.gameData.descId = selectText(descButton);
				}
			});

			attributePanel.add(descButton);

			selectComboBox = new JComboBox();
			selectComboBox.setBounds(new Rectangle(413, 67, 121, 21));
			selectComboBox.setBounds(64, 95, 121, 21);

			selectComboBox.addItemListener(new java.awt.event.ItemListener()
			{
				public void itemStateChanged(java.awt.event.ItemEvent e)
				{
					if (!inited)
						return;
					skillData.gameData.selectType = (SkillTargetSelectType) selectComboBox
							.getSelectedItem();
					setModified(true);
				}
			});

			for (int i = 0; i < SkillTargetSelectType.values().length; i++)
				selectComboBox.addItem(SkillTargetSelectType.values()[i]);

			attributePanel.add(selectComboBox);

			JLabel selectlabel = new JLabel();
			selectlabel.setText("\u9009\u62E9\u7B56\u7565:");
			selectlabel.setBounds(new Rectangle(353, 67, 57, 18));
			selectlabel.setBounds(4, 95, 57, 18);
			attributePanel.add(selectlabel);
			attributePanel.add(getAmmoComboBox());
			attributePanel.add(getAmmolabel());
			attributePanel.add(getEventComboBox());
			attributePanel.add(getEventlabel());

			JLabel label = new JLabel();
			label.setText("\u65BD\u6CD5\u7279\u6548:");
			label.setBounds(new Rectangle(4, 126, 57, 21));
			label.setBounds(153, 126, 57, 21);
			attributePanel.add(label);

			attributePanel.add(getResSkillEffectField());

			JLabel label_1 = new JLabel();
			label_1.setText("\u53D7\u51FB\u7279\u6548:");
			label_1.setBounds(new Rectangle(4, 126, 57, 21));
			label_1.setBounds(297, 126, 57, 21);
			attributePanel.add(label_1);

			attributePanel.add(getLabel_2());
			attributePanel.add(getLevelCountField());
			attributePanel.add(getResSkillActionField());
			attributePanel.add(getResBeAttackField());
			attributePanel.add(getEventParamField());
			attributePanel.add(getLabel_3());
			attributePanel.add(getLabel_4());
			attributePanel.add(getLeadCountField());
			attributePanel.add(getLabel_5());
			attributePanel.add(getLeadTimeDeltaField());
			attributePanel.add(getLabel_6());
			attributePanel.add(getStorageTimeField());
			attributePanel.add(getLabel_7());
			attributePanel.add(getStorageStartRateField());
			attributePanel.add(getLabel_8());
			attributePanel.add(getTextField_2());
			attributePanel.add(getLabel_9());
			attributePanel.add(getTextField_2_1());
			attributePanel.add(getLabel_10());
			attributePanel.add(getStorageEndValueField());
			attributePanel.add(getCostField());
			attributePanel.add(getLabel_11());
		}
		return attributePanel;
	}

	private JTextField getResBeAttackField()
	{
		if (resBeAttackField == null)
		{
			resBeAttackField = new JTextField();
			resBeAttackField.addFocusListener(new java.awt.event.FocusAdapter()
			{
				public void focusLost(java.awt.event.FocusEvent e)
				{
					if (!inited)
						return;
					skillData.gameData.resBeAttackId = resBeAttackField
							.getText();
					setModified(true);
				}
			});
			resBeAttackField.setBounds(new Rectangle(64, 126, 79, 21));
			resBeAttackField.setBounds(353, 126, 79, 21);
		}
		return resBeAttackField;
	}

	private JTextField getResSkillEffectField()
	{
		if (resSkillEffectField == null)
		{
			resSkillEffectField = new JTextField();
			resSkillEffectField
					.addFocusListener(new java.awt.event.FocusAdapter()
					{
						public void focusLost(java.awt.event.FocusEvent e)
						{
							if (!inited)
								return;
							skillData.gameData.resSkillEffectId = resSkillEffectField
									.getText();
							setModified(true);
						}
					});
			resSkillEffectField.setBounds(new Rectangle(64, 126, 79, 21));
			resSkillEffectField.setBounds(208, 126, 79, 21);
		}
		return resSkillEffectField;
	}

	/**
	 * This method initializes dataTypeComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getActionTypeComboBox()
	{
		if (actionTypeComboBox == null)
		{
			actionTypeComboBox = new JComboBox();
			actionTypeComboBox.setPreferredSize(new Dimension(80, 27));
			actionTypeComboBox.setBounds(new Rectangle(248, 4, 121, 21));
			actionTypeComboBox
					.addItemListener(new java.awt.event.ItemListener()
					{
						public void itemStateChanged(java.awt.event.ItemEvent e)
						{
							if (!inited)
								return;
							skillData.gameData.actionType = (SkillActionType) actionTypeComboBox
									.getSelectedItem();
							setModified(true);
						}
					});

			for (int i = 0; i < SkillActionType.values().length; i++)
				actionTypeComboBox.addItem(SkillActionType.values()[i]);
		}
		return actionTypeComboBox;
	}

	/**
	 * This method initializes affectLimitComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getEffectRangeComboBox()
	{
		if (effectRangeComboBox == null)
		{
			effectRangeComboBox = new JComboBox();
			effectRangeComboBox.setBounds(new Rectangle(64, 64, 121, 21));
			effectRangeComboBox
					.addItemListener(new java.awt.event.ItemListener()
					{
						public void itemStateChanged(java.awt.event.ItemEvent e)
						{
							if (!inited)
								return;
							skillData.gameData.effectRange = (SkillEffectRange) effectRangeComboBox
									.getSelectedItem();
							setModified(true);
						}
					});

			for (int i = 0; i < SkillEffectRange.values().length; i++)
				effectRangeComboBox.addItem(SkillEffectRange.values()[i]);
		}
		return effectRangeComboBox;
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
			jTabbedPane.setPreferredSize(new Dimension(120, 7));
			jTabbedPane.addTab("基础参数", null, getJPanel3(), null);
		}
		return jTabbedPane;
	}

	/**
	 * This method initializes jPanel3
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel3()
	{
		if (jPanel3 == null)
		{
			jPanel3 = new JPanel();
			jPanel3.setLayout(new BorderLayout());
			jPanel3.add(getAttributePanel(), BorderLayout.NORTH);
			jPanel3.add(getLevelChangePanel(), BorderLayout.CENTER);
		}
		return jPanel3;
	}

	private JTabbedPane levelChangePanel;
	private JScrollPane jScrollPane2 = null;
	private DefaultTableModel propTableModel = null;
	private JTable propTable = null;
	private boolean initPropTable = false;
	private JLabel label_6;
	private JTextField storageTimeField;
	private JLabel label_7;
	private JTextField storageStartRateField;
	private JLabel label_8;
	private JTextField storageStartValueField;
	private JLabel label_9;
	private JTextField storageEndRateField;
	private JLabel label_10;
	private JTextField storageEndValueField;
	private JTextField costField;
	private JLabel label_11;

	/**
	 * This method initializes jScrollPane2
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane2()
	{
		if (jScrollPane2 == null)
		{
			jScrollPane2 = new JScrollPane();
			jScrollPane2.setViewportView(getPropTable());
		}
		return jScrollPane2;
	}

	/**
	 * This method initializes propTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getPropTable()
	{
		if (propTable == null)
		{
			propTable = new JTable();
			propTableModel = new DefaultTableModel()
			{
				private static final long serialVersionUID = 2378374381755226386L;

				public Class getColumnClass(int c)
				{
					return getValueAt(0, c).getClass();
				}

				public boolean isCellEditable(int row, int col)
				{
					if (col == 0 || col == 5)
					{
						return false;
					} else
						return true;
				}
			};

			propTableModel
					.addTableModelListener(new javax.swing.event.TableModelListener()
					{
						public void tableChanged(
								javax.swing.event.TableModelEvent e)
						{
							propTableModel_tableChanged(e);
						}
					});

			propTable.addMouseListener(new java.awt.event.MouseAdapter()
			{
				public void mouseClicked(java.awt.event.MouseEvent e)
				{
					if (e.getClickCount() < 2)
						return;
					int row = propTable.getSelectedRow();
					int column = propTable.getSelectedColumn();
					if (row == -1)
						return;

					SkillLevelGameData data = skillData.gameData.levelDataList
							.get(row);
					switch (column)
					{

					case 5:
						inited = false;
						BuffData bData = selectBuffData();
						if (bData == null)
						{
							data.buffId = 0;
							propTableModel.setValueAt("", row, column);
						} else
						{
							propTableModel.setValueAt(bData.toString(), row,
									column);
							data.buffId = bData.getIntId();
						}
						inited = true;
						setModified(true);
						break;

					}
				}
			});

			propTable.setModel(propTableModel);

			String[] names =
			{ "等级列表", "被动触发概率", "范围影响参数", "效果类型", "效果参数", "buff", "buff触发概率" };
			propTableModel.setColumnIdentifiers(names);

			TableColumn typeColumn = propTable.getColumnModel().getColumn(3);
			JComboBox comboBox = new JComboBox();
			for (SkillEffectType type : SkillEffectType.values())
			{
				comboBox.addItem(type);
			}
			typeColumn.setCellEditor(new DefaultCellEditor(comboBox));

		}
		return propTable;
	}

	private BuffData selectBuffData()
	{
		Object o = Util.selectData(this, "buff", null);
		if (o != null)
		{
			BuffData bd = (BuffData) o;
			return bd;
		}
		return null;
	}

	private void propTableModel_tableChanged(TableModelEvent e)
	{
		if (!initPropTable)
			return;
		int row = e.getFirstRow();
		int column = e.getColumn();
		if (column == 0)
			return;

		SkillLevelGameData data = skillData.gameData.levelDataList.get(row);

		if (column == 1)
		{
			String s = (String) propTableModel.getValueAt(row, column);
			data.skillTriggerPercent = Integer.parseInt(s);
		} else if (column == 2)
		{
			String s = (String) propTableModel.getValueAt(row, column);
			data.effectRangeParam = s;
		} else if (column == 3)
		{
			data.effectType = (SkillEffectType) propTableModel.getValueAt(row,
					column);
		} else if (column == 4)
		{
			String s = (String) propTableModel.getValueAt(row, column);
			data.effectDataParam = s;
		} else if (column == 6)
		{
			Integer s = (Integer) propTableModel.getValueAt(row, column);
			data.buffTriggerPercent = s;
		}

		setModified(true);
	}

	private void updatePropTable()
	{
		initPropTable = false;
		for (int j = propTableModel.getRowCount() - 1; j >= 0; j--)
		{
			propTableModel.removeRow(j);
		}

		Object[] rowData = new Object[7];
		int size = skillData.gameData.levelCount;

		for (int i = 1; i < size + 1; i++)
		{
			rowData[0] = "等级" + i;
			SkillLevelGameData data = skillData.gameData.levelDataList
					.get(i - 1);

			BuffData bd = (BuffData) DataManager.getBaseData("buff",
					String.valueOf(data.buffId));
			rowData[1] = String.valueOf(data.skillTriggerPercent);
			rowData[2] = String.valueOf(data.effectRangeParam);
			rowData[3] = data.effectType;
			rowData[4] = data.effectDataParam;
			rowData[5] = bd == null ? "" : bd.toString();
			rowData[6] = data.buffTriggerPercent;

			propTableModel.addRow(rowData);
		}

		initPropTable = true;
		propTable.updateUI();
	}

	public JTabbedPane getLevelChangePanel()
	{
		if (levelChangePanel == null)
		{
			levelChangePanel = new JTabbedPane();
			levelChangePanel.setPreferredSize(new Dimension(120, 7));
			levelChangePanel.setBorder(BorderFactory.createLineBorder(
					Color.gray, 1));
			levelChangePanel.addTab("等级参数", null, getJScrollPane2(), null);
		}
		return levelChangePanel;
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
			damageTypeComboBox.setBounds(new Rectangle(248, 64, 93, 21));
			damageTypeComboBox
					.addItemListener(new java.awt.event.ItemListener()
					{
						public void itemStateChanged(java.awt.event.ItemEvent e)
						{
							if (e.getStateChange() == e.DESELECTED)
								return;
							if (!inited)
								return;
							skillData.gameData.damageType = (DamageType) damageTypeComboBox
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

	private JComboBox getAmmoComboBox()
	{
		if (ammoComboBox == null)
		{
			ammoComboBox = new JComboBox();
			ammoComboBox.setBounds(new Rectangle(413, 67, 121, 21));
			ammoComboBox.setBounds(409, 64, 95, 21);

			ammoComboBox.addItemListener(new java.awt.event.ItemListener()
			{
				public void itemStateChanged(java.awt.event.ItemEvent e)
				{
					if (e.getStateChange() == e.DESELECTED)
						return;
					if (!inited)
						return;
					skillData.gameData.ammoType = (SkillAmmoType) ammoComboBox
							.getSelectedItem();
					setModified(true);
				}
			});

			for (int i = 0; i < SkillAmmoType.values().length; i++)
			{
				ammoComboBox.addItem(SkillAmmoType.values()[i]);
			}
		}
		return ammoComboBox;
	}

	private JLabel getAmmolabel()
	{
		if (Ammolabel == null)
		{
			Ammolabel = new JLabel();
			Ammolabel.setText("\u5F39\u9053\u7C7B\u578B:");
			Ammolabel.setBounds(new Rectangle(353, 67, 57, 18));
			Ammolabel.setBounds(353, 67, 57, 18);
		}
		return Ammolabel;
	}

	private JComboBox getEventComboBox()
	{
		if (eventComboBox == null)
		{
			eventComboBox = new JComboBox();
			eventComboBox.setBounds(new Rectangle(413, 67, 121, 21));
			eventComboBox.setBounds(248, 93, 121, 21);

			eventComboBox.addItemListener(new java.awt.event.ItemListener()
			{
				public void itemStateChanged(java.awt.event.ItemEvent e)
				{
					if (e.getStateChange() == e.DESELECTED)
						return;
					if (!inited)
						return;
					skillData.gameData.event = (ActivateEvent) eventComboBox
							.getSelectedItem();
					setModified(true);
				}
			});

			for (int i = 0; i < ActivateEvent.values().length; i++)
			{
				eventComboBox.addItem(ActivateEvent.values()[i]);
			}
		}
		return eventComboBox;
	}

	private JLabel getEventlabel()
	{
		if (eventlabel == null)
		{
			eventlabel = new JLabel();
			eventlabel.setText("\u4E8B\u4EF6\u89E6\u53D1:");
			eventlabel.setBounds(new Rectangle(353, 67, 57, 18));
			eventlabel.setBounds(188, 96, 57, 18);
		}
		return eventlabel;
	}

	private JLabel getLabel_2()
	{
		if (label_2 == null)
		{
			label_2 = new JLabel();
			label_2.setText("\u7B49\u7EA7\u603B\u6570:");
			label_2.setBounds(new Rectangle(4, 126, 57, 21));
			label_2.setBounds(4, 157, 57, 21);
		}
		return label_2;
	}

	private JTextField getLevelCountField()
	{
		if (levelCountField == null)
		{
			levelCountField = new JTextField();
			levelCountField.setBounds(new Rectangle(64, 126, 79, 21));
			levelCountField.setBounds(64, 157, 79, 21);
			levelCountField.addFocusListener(new java.awt.event.FocusAdapter()
			{
				public void focusLost(java.awt.event.FocusEvent e)
				{
					if (!inited)
						return;

					int size = Integer.parseInt(levelCountField.getText());
					int num = skillData.gameData.levelDataList.size();

					int i;
					if (size >= num)
					{
						for (i = num; i < size; i++)
						{
							SkillLevelGameData levelData = new SkillLevelGameData();
							levelData.level = i + 1;

							skillData.gameData.levelDataList.add(levelData);
						}
					} else if (size < num)
					{
						for (i = num; i > size; i--)
						{
							skillData.gameData.levelDataList.remove(i - 1);
						}
					}

					skillData.gameData.levelCount = size;

					updatePropTable();
					setModified(true);
				}
			});
		}
		return levelCountField;
	}

	private JTextField getResSkillActionField()
	{
		if (resSkillActionField == null)
		{
			resSkillActionField = new JTextField();
			resSkillActionField
					.addFocusListener(new java.awt.event.FocusAdapter()
					{
						public void focusLost(java.awt.event.FocusEvent e)
						{
							if (!inited)
								return;
							skillData.gameData.resSkillActionId = resSkillActionField
									.getText();
							setModified(true);
						}
					});
			resSkillActionField.setBounds(new Rectangle(64, 126, 79, 21));
			resSkillActionField.setBounds(64, 126, 79, 21);
		}
		return resSkillActionField;
	}

	private JTextField getEventParamField()
	{
		if (eventParamField == null)
		{
			eventParamField = new JTextField();
			eventParamField.setBounds(new Rectangle(64, 126, 79, 21));
			eventParamField.setBounds(443, 95, 93, 21);

			eventParamField.addFocusListener(new java.awt.event.FocusAdapter()
			{
				public void focusLost(java.awt.event.FocusEvent e)
				{
					if (!inited)
						return;
					skillData.gameData.eventParam = eventParamField.getText();
					setModified(true);
				}
			});
		}
		return eventParamField;
	}

	private JLabel getLabel_3()
	{
		if (label_3 == null)
		{
			label_3 = new JLabel();
			label_3.setText("\u4E8B\u4EF6\u53C2\u6570:");
			label_3.setBounds(new Rectangle(4, 126, 57, 21));
			label_3.setBounds(383, 95, 57, 21);
		}
		return label_3;
	}

	private JLabel getLabel_4()
	{
		if (label_4 == null)
		{
			label_4 = new JLabel();
			label_4.setText("\u5F15\u5BFC\u6B21\u6570:");
			label_4.setBounds(new Rectangle(4, 126, 57, 21));
			label_4.setBounds(153, 157, 57, 21);
		}
		return label_4;
	}

	private JTextField getLeadCountField()
	{
		if (leadCountField == null)
		{
			leadCountField = new JTextField();
			leadCountField.setBounds(new Rectangle(64, 126, 79, 21));
			leadCountField.setBounds(208, 157, 79, 21);

			leadCountField.addFocusListener(new java.awt.event.FocusAdapter()
			{
				public void focusLost(java.awt.event.FocusEvent e)
				{
					if (!inited)
						return;
					skillData.gameData.leadCount = Integer
							.parseInt(leadCountField.getText());
					setModified(true);
				}
			});
		}
		return leadCountField;
	}

	private JLabel getLabel_5()
	{
		if (label_5 == null)
		{
			label_5 = new JLabel();
			label_5.setText("\u5F15\u5BFC\u95F4\u9694:");
			label_5.setBounds(new Rectangle(4, 126, 57, 21));
			label_5.setBounds(297, 157, 57, 21);
		}
		return label_5;
	}

	private JTextField getLeadTimeDeltaField()
	{
		if (leadTimeDeltaField == null)
		{
			leadTimeDeltaField = new JTextField();
			leadTimeDeltaField.setBounds(new Rectangle(64, 126, 79, 21));
			leadTimeDeltaField.setBounds(352, 157, 79, 21);
			leadTimeDeltaField
					.addFocusListener(new java.awt.event.FocusAdapter()
					{
						public void focusLost(java.awt.event.FocusEvent e)
						{
							if (!inited)
								return;
							skillData.gameData.leadTimeDelta = Integer
									.parseInt(leadTimeDeltaField.getText());
							setModified(true);
						}
					});
		}
		return leadTimeDeltaField;
	}

	private JLabel getLabel_6()
	{
		if (label_6 == null)
		{
			label_6 = new JLabel();
			label_6.setText("\u84C4\u529B\u65F6\u95F4:");
			label_6.setBounds(new Rectangle(4, 126, 57, 21));
			label_6.setBounds(4, 188, 57, 21);
		}
		return label_6;
	}

	private JTextField getStorageTimeField()
	{
		if (storageTimeField == null)
		{
			storageTimeField = new JTextField();
			storageTimeField.setBounds(new Rectangle(64, 126, 79, 21));
			storageTimeField.setBounds(64, 186, 79, 21);
			storageTimeField.addFocusListener(new java.awt.event.FocusAdapter()
			{
				public void focusLost(java.awt.event.FocusEvent e)
				{
					if (!inited)
						return;
					skillData.gameData.storageTime = Integer
							.parseInt(storageTimeField.getText());
					setModified(true);
				}
			});
		}
		return storageTimeField;
	}

	private JLabel getLabel_7()
	{
		if (label_7 == null)
		{
			label_7 = new JLabel();
			label_7.setText("\u84C4\u529B\u8D77\u59CB\u4E07\u5206\u6BD4:");
			label_7.setBounds(new Rectangle(4, 126, 57, 21));
			label_7.setBounds(148, 188, 90, 21);
		}
		return label_7;
	}

	private JTextField getStorageStartRateField()
	{
		if (storageStartRateField == null)
		{
			storageStartRateField = new JTextField();
			storageStartRateField.setBounds(new Rectangle(64, 126, 79, 21));
			storageStartRateField.setBounds(248, 188, 79, 21);
			storageStartRateField
					.addFocusListener(new java.awt.event.FocusAdapter()
					{
						public void focusLost(java.awt.event.FocusEvent e)
						{
							if (!inited)
								return;
							skillData.gameData.storageStartRate = Integer
									.parseInt(storageStartRateField.getText());
							setModified(true);
						}
					});
		}
		return storageStartRateField;
	}

	private JLabel getLabel_8()
	{
		if (label_8 == null)
		{
			label_8 = new JLabel();
			label_8.setText("\u84C4\u529B\u8D77\u59CB\u5B9E\u6570:");
			label_8.setBounds(new Rectangle(4, 126, 57, 21));
			label_8.setBounds(337, 188, 90, 21);
		}
		return label_8;
	}

	private JTextField getTextField_2()
	{
		if (storageStartValueField == null)
		{
			storageStartValueField = new JTextField();
			storageStartValueField.setBounds(new Rectangle(64, 126, 79, 21));
			storageStartValueField.setBounds(422, 188, 79, 21);
			storageStartValueField
					.addFocusListener(new java.awt.event.FocusAdapter()
					{
						public void focusLost(java.awt.event.FocusEvent e)
						{
							if (!inited)
								return;
							skillData.gameData.storageStartValue = Integer
									.parseInt(storageStartValueField.getText());
							setModified(true);
						}
					});
		}
		return storageStartValueField;
	}

	private JLabel getLabel_9()
	{
		if (label_9 == null)
		{
			label_9 = new JLabel();
			label_9.setText("\u84C4\u529B\u8FC7\u7A0B\u4E07\u5206\u6BD4:");
			label_9.setBounds(new Rectangle(4, 126, 57, 21));
			label_9.setBounds(4, 218, 90, 21);
		}
		return label_9;
	}

	private JTextField getTextField_2_1()
	{
		if (storageEndRateField == null)
		{
			storageEndRateField = new JTextField();
			storageEndRateField.setBounds(new Rectangle(64, 126, 79, 21));
			storageEndRateField.setBounds(104, 218, 79, 21);
			storageEndRateField
					.addFocusListener(new java.awt.event.FocusAdapter()
					{
						public void focusLost(java.awt.event.FocusEvent e)
						{
							if (!inited)
								return;
							skillData.gameData.storageEndRate = Integer
									.parseInt(storageEndRateField.getText());
							setModified(true);
						}
					});
		}
		return storageEndRateField;
	}

	private JLabel getLabel_10()
	{
		if (label_10 == null)
		{
			label_10 = new JLabel();
			label_10.setText("\u84C4\u529B\u8FC7\u7A0B\u5B9E\u6570:");
			label_10.setBounds(new Rectangle(4, 126, 57, 21));
			label_10.setBounds(188, 218, 90, 21);
		}
		return label_10;
	}

	private JTextField getStorageEndValueField()
	{
		if (storageEndValueField == null)
		{
			storageEndValueField = new JTextField();
			storageEndValueField.setBounds(new Rectangle(64, 126, 79, 21));
			storageEndValueField.setBounds(270, 218, 79, 21);
			storageEndValueField
					.addFocusListener(new java.awt.event.FocusAdapter()
					{
						public void focusLost(java.awt.event.FocusEvent e)
						{
							if (!inited)
								return;
							skillData.gameData.storageEndValue = Integer
									.parseInt(storageEndValueField.getText());
							setModified(true);
						}
					});
		}
		return storageEndValueField;
	}
	private JTextField getCostField() {
		if (costField == null) {
			costField = new JTextField();
			costField.setBounds(new Rectangle(64, 126, 79, 21));
			costField.setBounds(443, 4, 93, 21);
			
			costField
			.addFocusListener(new java.awt.event.FocusAdapter()
			{
				public void focusLost(java.awt.event.FocusEvent e)
				{
					if (!inited)
						return;
					skillData.gameData.cost = Integer
							.parseInt(costField.getText());
					setModified(true);
				}
			});
		}
		return costField;
	}
	private JLabel getLabel_11() {
		if (label_11 == null) {
			label_11 = new JLabel();
			label_11.setText("\u6CD5\u672F\u6D88\u8017:");
			label_11.setBounds(new Rectangle(4, 126, 57, 21));
			label_11.setBounds(383, 4, 57, 21);
		}
		return label_11;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
