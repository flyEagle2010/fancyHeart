package com.doteplay.editor.data.text;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import com.doteplay.editor.common.EditorPanel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

public class TextEditPanel extends EditorPanel<TextData>
{

	/** * */
	private static final long serialVersionUID = 1L;
	private JPanel topPanel = null;
	private JLabel chTextLabel = null;
	private JLabel newTextLabel = null;
	private JLabel unitLabel = null;
	public TextData data; // @jve:decl-index=0:
	JTextArea newText;
	JTextArea chText;
	/**
	 * This method initializes
	 */
	public TextEditPanel()
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
		this.add(getTopPanel(), BorderLayout.CENTER);
	}


	/**
	 * 
	 */
	@Override
	public TextData getData()
	{
		return data;
	}

	@Override
	protected void initData(TextData bd)
	{
		data = bd;
		this.newText.setText(bd.gameData.text);
		this.chText.setText(bd.gameData.name);
		inited = true;

	}

	@Override
	protected boolean saveData()
	{
		return true;
	}


	/**
	 * This method initializes topPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getTopPanel()
	{
		if (topPanel == null)
		{
			newTextLabel = new JLabel();
			newTextLabel.setBounds(new Rectangle(12, 104, 35, 18));
			newTextLabel.setText("З­вы:");
			chTextLabel = new JLabel();
			chTextLabel.setText("\u8BF4\u660E:");
			chTextLabel.setBounds(new Rectangle(10, 12, 37, 18));
			topPanel = new JPanel();
			topPanel.setLayout(null);
			topPanel.setPreferredSize(new Dimension(404, 354));
			topPanel.add(chTextLabel, null);
			topPanel.add(newTextLabel, null);
			
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setBounds(new Rectangle(72, 28, 345, 85));
			scrollPane.setBounds(47, 12, 345, 85);
			topPanel.add(scrollPane);
			
			chText = new JTextArea();
			scrollPane.setViewportView(chText);
			chText.addFocusListener(new java.awt.event.FocusAdapter()
			{
				@Override
				public void focusLost(java.awt.event.FocusEvent e)
				{
					if (!inited)
						return;
					String s = chText.getText();
					if (s.equals(data.nameId))
						return;
					data.gameData.name = s;
					setModified(true);
				}
			});
			
			JScrollPane scrollPane_1 = new JScrollPane();
			scrollPane_1.setBounds(new Rectangle(72, 28, 345, 85));
			scrollPane_1.setBounds(47, 104, 345, 85);
			topPanel.add(scrollPane_1);
			
			newText = new JTextArea();
			scrollPane_1.setViewportView(newText);
			newText.addFocusListener(new java.awt.event.FocusAdapter()
			{
				@Override
				public void focusLost(java.awt.event.FocusEvent e)
				{
					if (!inited)
						return;
					String s = newText.getText();
					if (s.equals(data.gameData.text))
						return;
					data.gameData.text = s;
					setModified(true);
				}
			});
		}
		return topPanel;
	}
} 
