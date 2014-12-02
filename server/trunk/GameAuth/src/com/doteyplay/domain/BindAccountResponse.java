package com.doteyplay.domain;


public class BindAccountResponse
{
	public final static int LOGIN_STATE_SUCCESS = 0;
	public final static int LOGIN_STATE_ACCOUNT_NULL = 1;
	public final static int LOGIN_STATE_ACCOUNT_LIMIT = 2;
	public final static int LOGIN_STATE_PASSWORD_ERROR = 3;
	public final static int LOGIN_STATE_ACCOUNT_NOT_EXIST = 4;
	public final static int LOGIN_STATE_ACCOUNT_HAS_EXIST = 5;
	
	private int loginState;
	public int getLoginState()
	{
		return loginState;
	}

	public void setLoginState(int loginState)
	{
		this.loginState = loginState;
	}

	
}
