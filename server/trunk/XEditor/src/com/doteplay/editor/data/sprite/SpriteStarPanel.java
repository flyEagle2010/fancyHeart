package com.doteplay.editor.data.sprite;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import com.doteyplay.game.constants.sprite.SpritePropType;
import com.doteyplay.game.constants.sprite.SpriteStarType;
import com.doteyplay.game.gamedata.data.sprite.SpriteStarData;

public class SpriteStarPanel extends JPanel
{
	private List<SpriteStarData> starDataList;

	private JScrollPane starScrollPane;
	private JTable starTable;
	private DefaultTableModel starTableModel;
	private SpriteEditPanel father;

	public SpriteStarPanel(SpriteEditPanel father)
	{
		this.father = father;

		this.setLayout(new BorderLayout());
		this.add(getStarScrollPane(), BorderLayout.CENTER);
	}

	public void init(List<SpriteStarData> starDataList)
	{
		this.starDataList = starDataList;

		if (starDataList != null)
		{
			for (SpriteStarData starData : starDataList)
			{
				Object[] rowData =
				{ starData.spriteStarType, starData.stoneId, starData.stoneNum,
						starData.upgradeMoney, starData.propRate.get(0),
						starData.propRate.get(1), starData.propRate.get(2),
						starData.propRate.get(3), starData.propRate.get(4),
						starData.propRate.get(5), starData.propRate.get(6) };
				starTableModel.addRow(rowData);
			}
		}
	}

	private JScrollPane getStarScrollPane()
	{
		if (starScrollPane == null)
		{
			starScrollPane = new JScrollPane();
			starScrollPane.setViewportView(getStarTable());
		}
		return starScrollPane;
	}

	private JTable getStarTable()
	{
		if (starTable == null)
		{
			starTable = new JTable();
			starTableModel = new DefaultTableModel()
			{
				private static final long serialVersionUID = 2378374381755226386L;

				public Class getColumnClass(int c)
				{
					return getValueAt(0, c).getClass();
				}

				public boolean isCellEditable(int row, int col)
				{
					return true;
				}
			};
			starTable.addMouseListener(new java.awt.event.MouseAdapter()
			{
				public void mouseClicked(java.awt.event.MouseEvent e)
				{

				}
			});
			String[] names =
			{ "星", "召唤石", "召唤石数量", "金钱消耗",
					SpritePropType.values()[0].toString(),
					SpritePropType.values()[1].toString(),
					SpritePropType.values()[2].toString(),
					SpritePropType.values()[3].toString(),
					SpritePropType.values()[4].toString(),
					SpritePropType.values()[5].toString(),
					SpritePropType.values()[6].toString() };
			starTableModel.setColumnIdentifiers(names);
			starTableModel
					.addTableModelListener(new javax.swing.event.TableModelListener()
					{
						@Override
						public void tableChanged(TableModelEvent e)
						{
							int row = e.getFirstRow();
							int column = e.getColumn();
							if (e.getType() != TableModelEvent.UPDATE
									|| row == -1)
								return;

							SpriteStarData starData = starDataList.get(row);
							if (starData == null)
								return;

							if (column == 0)
							{
								SpriteStarType type = (SpriteStarType) starTableModel
										.getValueAt(row, column);
								if (starData.spriteStarType != type)
								{
									starData.spriteStarType = type;
								}
							} else if (column == 1)
							{
								Integer i = (Integer) starTableModel
										.getValueAt(row, column);
								starData.stoneId = i;
							} else if (column == 2)
							{
								Integer i = (Integer) starTableModel
										.getValueAt(row, column);
								starData.stoneNum = i;
							} else if (column == 3)
							{
								Integer i = (Integer) starTableModel
										.getValueAt(row, column);
								starData.upgradeMoney = i;
							} else if (column > 3
									&& column <= 3 + SpriteStarType.values().length)
							{
								Integer i = (Integer) starTableModel
										.getValueAt(row, column);
								starData.propRate.set(column - 4, i);
							}
							father.setModified(true);
						}
					});

			starTable.setModel(starTableModel);

			TableColumn typeColumn = starTable.getColumnModel().getColumn(0);
			JComboBox comboBox = new JComboBox();
			for (SpriteStarType spriteStarType : SpriteStarType.values())
			{
				comboBox.addItem(spriteStarType);
			}
			typeColumn.setCellEditor(new DefaultCellEditor(comboBox));
		}
		return starTable;
	}
}
