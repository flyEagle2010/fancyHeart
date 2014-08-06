/**
 * 
 */
package com.doteplay.editor.component.ClosableTabbed;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.doteplay.editor.XEditor;

/**
 * @author dell
 *
 */
public class ClosableTabPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private ClosableTabbedPane tabbedPane;
//	private int index;
	private ClosableTabPanel thisPanel=this;
	private String title;
	private Icon icon;
	private JLabel titleLabel = null;
	private JButton closeButton = null;

	/**
	 * This is the default constructor
	 */
	public ClosableTabPanel(ClosableTabbedPane tabbedPane, String title,Icon icon) {
		super();
		this.tabbedPane=tabbedPane;
		this.title=title;
		this.icon=icon;
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		setLayout(new FlowLayout(0,2,2));
		setSize(new Dimension(130, 22));
		setOpaque(false);
		titleLabel = new JLabel();
		titleLabel.setText(title);
		if(icon!=null)
			titleLabel.setIcon(icon);
		this.add(titleLabel, null);
		this.add(getCloseButton(), null);
	}

	public void setTitle(String title)
	{
		this.title=title;
		titleLabel.setText(title);
	}
	/**
	 * This method initializes closeButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getCloseButton() {
		if (closeButton == null) {
			closeButton = new JButton();
			closeButton.setBackground(null);
			closeButton.setForeground(null);
			closeButton.setPreferredSize(new Dimension(18, 18));
			closeButton.setIcon(new ImageIcon(getClass().getResource("/img/icon/delete.png")));
			closeButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					int index=tabbedPane.indexOfTabComponent(thisPanel);
					XEditor.xEditor.closeDataEditor(index);
//					tabbedPane.removeTabAt(index);
				}
			});
		}
		return closeButton;
	}

}
