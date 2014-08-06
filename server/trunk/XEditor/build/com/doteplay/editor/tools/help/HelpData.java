package com.doteplay.editor.tools.help;
/**
 * 
 * @author wyc
 * 节点对象
 */
public class HelpData {
	/**
	 * 主键
	 */
	private int PKId = 0;
	/**
	 * 名字
	 */
	private String name = "";
	/**
	 * 说明
	 */
	private String description = "";
	/**
	 * 父节点
	 * 没有为-1
	 */
	private int parentId = -1;
	/**
	 * 类型
	 */
	private int type = 0;
	
	public int getPKId() {
		return PKId;
	}
	public void setPKId(int id) {
		PKId = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
	@Override
	public String toString() {
		return "insert into t_game_help(PKId,name,description,parentId,type) values("+
		PKId+",'"+name+"','"+description+"',"+parentId+","+type+")";
	}
	
	
}
