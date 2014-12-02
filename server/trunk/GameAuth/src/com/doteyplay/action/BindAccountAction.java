package com.doteyplay.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.doteyplay.manager.AuthManager;

public class BindAccountAction extends HttpServlet
{
	/**
	 * @author:Tom.Zheng
	 * @createTime:2014年11月24日 下午6:47:25	
	 */
	private static final long serialVersionUID = 4487259839847565504L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		resp.setContentType("text/xml;charset=UTF-8");

		String UDID = req.getParameter("UDID");
		String account = req.getParameter("account");
		String password = req.getParameter("password");

		String result = AuthManager.getInstance().bindAccountAndPassword(account, password, UDID);
		resp.getWriter().print(result);
	}

}
