/**
 * @package com.doteplay.editor.component
 * @file TimeEditPanel.java
 */
package com.doteplay.editor.component;

import java.awt.Color;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.doteyplay.game.editor.ResourceDataConstants;
import com.doteplay.editor.common.InnerEditorPanel;
import com.doteplay.editor.data.common.TimeData;
import com.doteplay.editor.util.Util;


/**
 */
public class TimeEditPanel extends InnerEditorPanel
{

	private static final long serialVersionUID = 1L;
	private JLabel jLabel = null;
	protected JComboBox timeTypeComboBox = null;
	private JLabel jLabel1 = null;
	protected JTextField timeValueTextField = null;
	protected JLabel typeNameLabel = null;

	public TimeData timeData; // @jve:decl-index=0:

	/**
	 * This is the default constructor
	 */
	public TimeEditPanel()
	{
		super();
		initialize();
	}

	public void initData(TimeData timeData)
	{
		this.timeData = timeData;

		timeTypeComboBox.setEnabled(true);
		timeValueTextField.setEnabled(true);

		timeTypeComboBox.setSelectedIndex(timeData.timeType);

		typeNameLabel
				.setText(ResourceDataConstants.UNIT_NAME[timeData.timeType]);
		timeValueTextField.setText(Util
				.absolutelyTime2String(timeData.timeValue));

		inited = true;
	}

	public void clearData()
	{
		timeTypeComboBox.setSelectedIndex(0);
		timeValueTextField.setText("-1");

		timeTypeComboBox.setEnabled(false);
		timeValueTextField.setEnabled(false);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize()
	{
		typeNameLabel = new JLabel();
		typeNameLabel.setBounds(new Rectangle(218, 28, 173, 21));
		typeNameLabel.setText("");
		jLabel1 = new JLabel();
		jLabel1.setBounds(new Rectangle(4, 28, 65, 21));
		jLabel1.setText("输入时间：");
		jLabel = new JLabel();
		jLabel.setBounds(new Rectangle(4, 4, 65, 21));
		jLabel.setText("时间类型：");
		this.setSize(399, 54);
		this.setLayout(null);
		this.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
		this.add(jLabel, null);
		this.add(getTimeTypeComboBox(), null);
		this.add(jLabel1, null);
		this.add(getTimeValueTextField(), null);
		this.add(typeNameLabel, null);
	}

	/**
	 * This method initializes timeTypeComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getTimeTypeComboBox()
	{
		if (timeTypeComboBox == null)
		{
			timeTypeComboBox = new JComboBox();
			timeTypeComboBox.setBounds(new Rectangle(72, 4, 141, 21));
			timeTypeComboBox
					.addActionListener(new java.awt.event.ActionListener()
					{
						public void actionPerformed(java.awt.event.ActionEvent e)
						{
							if (!inited)
							{
								return;
							}

							byte sel = (byte) timeTypeComboBox
									.getSelectedIndex();
							if (sel != timeData.timeType)
							{
								timeData.timeValue = -1;
								timeData.timeType = sel;
								typeNameLabel
										.setText(ResourceDataConstants.UNIT_NAME[timeData.timeType]);
								setModified(true);
							}
						}
					});
			Util.setComboBoxData(timeTypeComboBox,
					ResourceDataConstants.UNIT_NAME);
		}
		return timeTypeComboBox;
	}

	/**
	 * This method initializes timeValueTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTimeValueTextField()
	{
		if (timeValueTextField == null)
		{
			timeValueTextField = new JTextField();
			timeValueTextField.setBounds(new Rectangle(72, 28, 141, 21));
			timeValueTextField
					.addFocusListener(new java.awt.event.FocusAdapter()
					{
						@Override
						public void focusLost(java.awt.event.FocusEvent e)
						{
							if (!inited)
							{
								return;
							}
							String s = timeValueTextField.getText().trim();
							if (timeData.timeType == ResourceDataConstants.TIME_TYPE_RELATIVELY)
							{
								timeData.timeValue = Util
										.String2Long(timeValueTextField
												.getText().trim());
							} else
							{
								timeData.timeValue = Util
										.String2AbsolutelyTime(s);
							}
							setModified(true);
						}
					});
		}
		return timeValueTextField;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
