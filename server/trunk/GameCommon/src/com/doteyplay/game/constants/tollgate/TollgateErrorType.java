package com.doteyplay.game.constants.tollgate;
/**
 * @className:TollgateErrorType.java
 * @classDescription:
 * @author:Tom.Zheng
 * @createTime:2014年7月18日 下午6:24:50
 */
public enum TollgateErrorType {

	Success("成功"),
	NoNode("结点不存在"),
	;
	
	private String msg;
	
	private TollgateErrorType(String msg){
		this.msg = msg;
	}
	
	public String getMsg(){
		return msg;
	}
}

