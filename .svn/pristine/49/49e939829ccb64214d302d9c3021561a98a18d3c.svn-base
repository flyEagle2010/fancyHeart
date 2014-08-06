/**
 * 
 */
package com.doteplay.editor.component.ClosableTabbed;

import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JTabbedPane;

/**
 * @author dell
 *
 */
public class ClosableTabbedPane extends JTabbedPane {

	private static final long serialVersionUID = 1L;
	/**
	 * This is the default constructor
	 */
	public ClosableTabbedPane() {
		super();
	}

	@Override
	public void addTab(String title, Component component) {
		// TODO Auto-generated method stub
		super.addTab(title, component);
		
		changeTabComponent();
	}

	private void changeTabComponent()
	{
		int index = this.getTabCount()-1;
		String title=this.getTitleAt(index);
		Icon icon=this.getIconAt(index);
		ClosableTabPanel closableTabPanel=new ClosableTabPanel(this,title,icon);
		
		this.setTabComponentAt(index, closableTabPanel);
	}	
	@Override
	public void addTab(String title, Icon icon, Component component, String tip) {
		// TODO Auto-generated method stub
		super.addTab(title, icon, component, tip);
		changeTabComponent();
	}

	@Override
	public void addTab(String title, Icon icon, Component component) {
		// TODO Auto-generated method stub
		super.addTab(title, icon, component);
		changeTabComponent();
	}

	@Override
	public void setTitleAt(int index, String title) {
//		super.setTitleAt(index, title);
		ClosableTabPanel closableTabPanel=(ClosableTabPanel)this.getTabComponentAt(index);
		closableTabPanel.setTitle(title);
	}
	
}
