package com.doteyplay.messageconstants;

import com.doteyplay.messageconstants.constants.IMsgConstants;

/**
 * ªÒ»°MsgPolymer
 *
 */
public class MsgPolymer {

	private IMsgConstants constant;
	
	private String[] params;

	public MsgPolymer(IMsgConstants constant, String... param) {
		this.constant = constant;
		params = param;
	}

	public IMsgConstants getConstant() {
		return this.constant;
	}
	
	public String[] getParams(){
		return params;
	}
	
	public String getMessage(){
		return constant.getMessage(params);
	}
}
