package com.doteyplay.game.constants.common;
/**
 * @className:CommonResponseType.java
 * @classDescription:
 * @author:Tom.Zheng
 * @createTime:2014年7月18日 下午6:19:36
 */
public enum CommonResponseType {

	enterNode("进入战斗"),
	;
	
	private String msg="";
	
	private CommonResponseType(String msg){
		this.msg = msg;
	}
	
	public String getMsg(){
		return msg;
	}
	
}
