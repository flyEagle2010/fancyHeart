package com.doteyplay.game.constants.tollgate;
/**
 * @className:TollgateErrorType.java
 * @classDescription:
 * @author:Tom.Zheng
 * @createTime:2014年7月18日 下午6:24:50
 */
public enum GroupUpdateResultType {

	Success("成功"),
	ERROR("错误"),
	;
	
	private String msg;
	
	private GroupUpdateResultType(String msg){
		this.msg = msg;
	}
	
	public String getMsg(){
		return msg;
	}
}

