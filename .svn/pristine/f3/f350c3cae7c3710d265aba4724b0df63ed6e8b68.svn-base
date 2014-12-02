package com.doteyplay.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.doteyplay.manager.AuthManager;
import com.doteyplay.utils.IPUtils;

public class QuickLoginAction extends HttpServlet
{
	/**
	 * @author:Tom.Zheng
	 * @createTime:2014年11月24日 下午3:49:29
	 */
	private static final long serialVersionUID = -5064049928781674708L;

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
		
		if(UDID==null||"".equals(UDID)){
			return;
		}

		String result = AuthManager.getInstance().quickLogin(UDID,
				IPUtils.getIpAddr(req));
		resp.getWriter().print(result);
	}


}
