package com.doteplay.editor.common;


import javax.swing.JPanel;

import com.doteplay.editor.XEditor;

public abstract class GroupPanel extends JPanel
{
	private static final long serialVersionUID = 1L;
    public String groupId="base";
    public XEditor editor;  //  @jve:decl-index=0:
	protected JPanel thisPanel=this;
	
    public GroupPanel()
    {
		super();
    }

    
    public void init(XEditor e,String gid)
    {
        editor=e;
        groupId=gid;
    }
    public abstract void addData(BaseData bd);
    public abstract void removeData(BaseData bd);
    public abstract void updateView();
    public void newData()
    {

    }
    public Object[] getSelectObjects(){
    	return null;
    }
}
