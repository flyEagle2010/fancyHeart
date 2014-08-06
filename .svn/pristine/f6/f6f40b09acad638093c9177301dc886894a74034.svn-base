package com.doteyplay.game.domain.gamebean;


/**
 * @className:RoleTollgate.java
 * @classDescription:针对数据库的Bean
 * @author:Tom.Zheng
 * @createTime:2014年6月23日 下午4:24:13
 */
public class TollgateBean extends BaseBean {

	/**
	 * @author:Tom.Zheng
	 * @createTime:2014年6月23日 下午6:48:47
	 */
	private static final long serialVersionUID = -8367737639497368100L;
	/**
	 * [数据库对应字段] 角色Id
	 */
	private long roleId;
	/**
	 * [数据库对应字段] 副本信息byte数组。
	 */
	private byte tollgateInfo[];// BLOB:最大限制到65K字节

	/**
	 * 无参数构造函数。
	 * 
	 * @author:Tom.Zheng
	 * @createTime:2014年6月24日 下午3:36:23
	 */
	public TollgateBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TollgateBean(long roleId) {
		super();
		this.roleId = roleId;
	}

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public byte[] getTollgateInfo() {
		return tollgateInfo;
	}

	public void setTollgateInfo(byte[] tollgateInfo) {
		this.tollgateInfo = tollgateInfo;
	}

	public void release() {
		// TODO Auto-generated method stub
		this.roleId = 0L;
		this.tollgateInfo = null;
	}

}
