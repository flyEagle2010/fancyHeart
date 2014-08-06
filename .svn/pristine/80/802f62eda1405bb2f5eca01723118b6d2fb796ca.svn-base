//package com.doteyplay.core.messageagent.net.server;
//
//import org.apache.log4j.Logger;
//import org.apache.mina.core.session.IoSession;
//
//import com.doteyplay.core.messageagent.ITransferAgent;
//import com.doteyplay.luna.common.action.BaseAction;
//import com.doteyplay.luna.common.message.DecoderMessage;
//import com.doteyplay.net.message.DefaultMessage;
//
//public class MessageAgentBroadcastAction extends BaseAction
//{
//	/**
//	 * Logger for this class
//	 */
//	private static final Logger logger = Logger
//			.getLogger(MessageAgentBroadcastAction.class);
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
//
//		DefaultMessage message = new DefaultMessage();
//		byte[] data = arg1.getByteArray();
//		message.setData(data);
//		int size = arg1.getShort();
//
//		for (int i = 0; i < size; i++)
//			agent.sendMessage(arg1.getInt(), message);
//
//	}
//
//}
