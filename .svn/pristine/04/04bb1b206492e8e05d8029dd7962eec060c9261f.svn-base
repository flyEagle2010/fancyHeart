package com.doteyplay.domain;

import java.util.List;

import com.doteyplay.bean.AreaBean;
import com.doteyplay.bean.UserBean;

public class LoginResponse
{
	public final static int LOGIN_STATE_SUCCESS = 0;
	public final static int LOGIN_STATE_ACCOUNT_NULL = 1;
	public final static int LOGIN_STATE_ACCOUNT_LIMIT = 2;
	
	private int loginState;

	private String key;
	
	private UserBean userBean;

	private List<AreaBean> areaList;

	public int getLoginState()
	{
		return loginState;
	}

	public void setLoginState(int loginState)
	{
		this.loginState = loginState;
	}

	public String getKey()
	{
		return key;
	}

	public void setKey(String key)
	{
		this.key = key;
	}

	public List<AreaBean> getAreaList()
	{
		return areaList;
	}

	public void setAreaList(List<AreaBean> areaList)
	{
		this.areaList = areaList;
	}

	public UserBean getUserBean()
	{
		return userBean;
	}

	public void setUserBean(UserBean userBean)
	{
		this.userBean = userBean;
	}
}
