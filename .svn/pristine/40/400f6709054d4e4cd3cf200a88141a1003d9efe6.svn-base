//package com.doteyplay.core.messageagent.net.server;
//
//import org.apache.log4j.Logger;
//import org.apache.mina.core.session.IoSession;
//
//import com.doteyplay.core.configloader.MessageAgentManager;
//import com.doteyplay.core.messageagent.ITransferAgent;
//import com.doteyplay.luna.common.action.BaseAction;
//import com.doteyplay.luna.common.message.DecoderMessage;
//import com.doteyplay.net.message.DefaultMessage;
//
//public class MessageAgentServerAction extends BaseAction
//{
//	/**
//	 * Logger for this class
//	 */
//	private static final Logger logger = Logger
//			.getLogger(MessageAgentServerAction.class);
//
//	@Override
//	public void doAction(int arg0)
//	{
//	}
//
//	@Override
//	public void doAction(IoSession arg0, DecoderMessage arg1)
//	{
//		// 取得是本地的
//		ITransferAgent agent = MessageAgentManager.getInstance().getAgent(0);
//		byte[] data = arg1.getByteArray();
//		DefaultMessage message = new DefaultMessage();
//		message.setData(data);
//		agent.sendMessage(arg1.getRoleId(), message);
//	}
//
//}
