package com.doteyplay.game.service.bo.chat;

import com.doteyplay.core.bhns.ISimpleService;
import com.doteyplay.game.BOConst;

/**
 * 
 * @className:IChatService.java
 * @classDescription:聊天服务
 * @author:Tom.Zheng
 * @createTime:2014年11月26日 下午5:56:04
 */
public interface IChatService extends ISimpleService {

	public final static int PORTAL_ID = BOConst.BO_CHAT;

	/**
	 * 初使化服务模块
	 */
	public void initlize();

	/**
	 * 释放资源
	 */
	public void release();
	
	/**
	 * 注册监听
	 */
	public void register();
	
	/**
	 * 发送信息
	 */
	public void sendMsg(String msgContent);
	
	/**
	 * 取消注册
	 */
	public void cancleRegister();
	/**
	 * 处理GM需求
	 * @param source
	 */
	public void doGm(String source);

}
