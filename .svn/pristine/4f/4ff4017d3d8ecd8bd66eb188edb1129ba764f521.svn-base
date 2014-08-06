//package com.doteyplay.core.messageagent.net.server;
//
//import org.apache.log4j.Logger;
//
//import com.doteyplay.game.config.ServerConfig;
//import com.doteyplay.luna.server.LunaServer;
//
//public class MessageAgentServerService
//{
//	private static final Logger logger = Logger
//			.getLogger(MessageAgentServerService.class);
//	
//	public static final short MESSAGE_AGENT_COMMAND = 0;
//	public static final short MESSAGE_AGENT_BROADCAST_COMMAND = 1;
//
//	MessageAgentServerController serverController;
//	// ====================================
//	LunaServer lunaServer;
//
//	public MessageAgentServerService(int messageServerPort)
//	{
//		try
//		{
//			if (ServerConfig.GATEWAY_SERVER)
//			{
//				serverController = new MessageAgentServerController();
//				initAction();
//				lunaServer = new LunaServer(messageServerPort, serverController);
//				lunaServer.start();
//			}
//		} catch (Exception e)
//		{
//			logger.error("处理消息失败", e);
//		}
//	}
//
//	private void initAction()
//	{
//		// 默认有一个处理消息，用来转发消息
//		serverController.addAction((short) MESSAGE_AGENT_COMMAND, new MessageAgentServerAction());
//		serverController.addAction((short) MESSAGE_AGENT_BROADCAST_COMMAND, new MessageAgentBroadcastAction());
//	}
//}
//
