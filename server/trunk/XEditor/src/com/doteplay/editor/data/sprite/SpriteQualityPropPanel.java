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
import com.doteyplay.game.constants.sprite.SpriteQualityType;
import com.doteyplay.game.gamedata.data.sprite.SpriteBasePropData;

public class SpriteQualityPropPanel extends JPanel
{
	private SpriteQualityType type;
	private List<SpriteBasePropData> propDataList;
	
	private JScrollPane propScrollPane;
	private JTable propTable;
	private DefaultTableModel propTableModel;
	private SpriteEditPanel father;
	
	public SpriteQualityPropPanel(SpriteQualityType type,SpriteEditPanel father)
	{
		this.type = type;
		this.father = father;
		
		this.setLayout(new BorderLayout());
		this.add(getPropScrollPane(), BorderLayout.CENTER);
	}
	
	public void init(List<SpriteBasePropData> propDataList)
	{
		this.propDataList = propDataList;
		
		if (propDataList != null)
		{
			for (SpriteBasePropData propData : propDataList)
			{
				Object[] rowData =
				{ propData.spritePropType,
						propData.baseValue, propData.rate };
				propTableModel.addRow(rowData);
			}
		}
	}
	
	private JScrollPane getPropScrollPane()
	{
		if (propScrollPane == null)
		{
			propScrollPane = new JScrollPane();
			propScrollPane.setViewportView(getPropTable());
		}
		return propScrollPane;
	}

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
					return true;
				}
			};
			propTable.addMouseListener(new java.awt.event.MouseAdapter()
			{
				public void mouseClicked(java.awt.event.MouseEvent e)
				{

				}
			});
			String[] names =
			{ "属性", "初始值", "增长率" };
			propTableModel.setColumnIdentifiers(names);
			propTableModel
					.addTableModelListener(new javax.swing.event.TableModelListener()
					{
						@Override
						public void tableChanged(TableModelEvent e)
						{
							int row = e.getFirstRow();
							int column = e.getColumn();
							if(e.getType()!=TableModelEvent.UPDATE || row==-1)
								return;
							
							SpriteBasePropData propData = propDataList.get(row);
							if(propData==null)
								return;
							
							if(column==0)
							{
								SpritePropType type = (SpritePropType)propTableModel.getValueAt(row, column);
								if(propData.spritePropType != type)
								{
									propData.spritePropType = type;
								}
							}
							else if(column==1)
							{
								Integer i = (Integer)propTableModel.getValueAt(row, column);
								propData.baseValue = i;
							}
							else if(column==2)
							{
								Integer i = (Integer)propTableModel.getValueAt(row, column);
								propData.rate = i;
							}
							father.setModified(true);
						}
					});

			propTable.setModel(propTableModel);

			TableColumn typeColumn = propTable.getColumnModel().getColumn(0);
			JComboBox comboBox = new JComboBox();
			for (SpritePropType spritePropType : SpritePropType.values())
			{
				comboBox.addItem(spritePropType);
			}
			typeColumn.setCellEditor(new DefaultCellEditor(comboBox));
		}
		return propTable;
	}
	
	public SpriteQualityType getQualityType()
	{
		return this.type;
	}
}
