package com.doteyplay.game.service.bo.chat;

import org.apache.log4j.Logger;

import com.doteyplay.core.bhns.AbstractSimpleService;
import com.doteyplay.game.constants.chat.ChatConstant;
import com.doteyplay.game.domain.chat.ChatDataManager;
import com.doteyplay.game.domain.chat.ChatMsgBean;
import com.doteyplay.game.domain.role.Role;
import com.doteyplay.game.message.chat.ChatMessage;
import com.doteyplay.game.message.chat.ChatMsgUpdateMessage;
import com.doteyplay.game.service.bo.virtualworld.chat.ChatHanderFactory;
import com.doteyplay.game.service.bo.virtualworld.chat.ResolvePatameterFactory;
import com.doteyplay.game.service.bo.virtualworld.chat.handle.IChatHandler;
import com.doteyplay.game.service.bo.virtualworld.chat.patameter.IPatameterObject;
import com.doteyplay.game.service.bo.virtualworld.chat.resolve.IChatResolve;
import com.doteyplay.game.service.runtime.GlobalRoleCache;

/**
 * 处理聊天业务.
 * 
 * @className:ChatService.java
 * @classDescription:
 * @author:Tom.Zheng
 * @createTime:2014年11月26日 下午6:11:16
 */
public class ChatService extends AbstractSimpleService<IChatService> implements
		IChatService {
	private static Logger logger = Logger.getLogger(ChatService.class);

	@Override
	public int getPortalId() {
		// TODO Auto-generated method stub
		return PORTAL_ID;
	}

	@Override
	public void initlize() {

		logger.error("向数据中心注册用户！roleId =" + this.getServiceId());
		
	}
	

	@Override
	public void register() {
		// TODO Auto-generated method stub
		ChatDataManager.getInstance().addRole(this.getServiceId());
	}

	@Override
	public void sendMsg(String msgContent) {
		// TODO Auto-generated method stub
		ChatMsgUpdateMessage msg =new ChatMsgUpdateMessage();
		
		ChatMsgBean bean = new ChatMsgBean();
		bean.setMsg(msgContent);
		bean.setSendRoleId(this.getServiceId());
		Role role = GlobalRoleCache.getInstance().getRoleById(this.getServiceId());
		role.getUser().getUserBean().getName();
		bean.setSendTimeLong(System.currentTimeMillis());
		msg.setMsgBean(bean);
		
		ChatDataManager.getInstance().putMessage(msg);
		
	}

	@Override
	public void cancleRegister() {
		// TODO Auto-generated method stub
		ChatDataManager.getInstance().removeRole(this.getServiceId());
	}

	@Override
	public void release() {
		// TODO Auto-generated method stub
		ChatDataManager.getInstance().removeRole(this.getServiceId());
	}
	
	@Override
	public void doGm(String source)
	{
		// TODO Auto-generated method stub
		logger.debug("gm操作:" + source);

		if (source == null || source.equals(""))
		{
			return;
		}
		String msg = "操作成功!";
		try
		{
			ChatConstant chatConstantType = ResolvePatameterFactory
					.getChatConstantType(source);
			IChatResolve igmPatameter = ResolvePatameterFactory
					.getResolve(chatConstantType);
			IPatameterObject patameterObject = igmPatameter
					.handlePatameter(source);
			IChatHandler gmProcess = ChatHanderFactory.getInstance()
					.getGMProcess(chatConstantType);
			gmProcess.handle(patameterObject, this.getServiceId());
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			msg = e.getMessage();
			e.printStackTrace();
		}
		ChatMessage message = new ChatMessage();

		message.setResult(msg);

		this.sendMessage(message);
	}

}
