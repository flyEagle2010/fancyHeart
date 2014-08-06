//package com.doteyplay.core.configloader;
//
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
//import com.doteyplay.core.messageagent.ITransferAgent;
//import com.doteyplay.core.messageagent.RemoteTransferAgent;
//
///**
// * 消息服务管理
// * 
// */
//public class MessageAgentManager
//{
//
//	private Map<Integer, ITransferAgent> agentMap = new ConcurrentHashMap<Integer, ITransferAgent>();
//
//	private ITransferAgent localAgent;
//
//	public ITransferAgent getAgent(long serviceId)
//	{
//		if (localAgent != null)
//			return localAgent;
//		return agentMap.get(serviceId);
//	}
//
//	public void registRemoteAgent(String messageAgentIp,
//			short messageAgentPort, long serviceId)
//	{
//
//		ITransferAgent agent = new RemoteTransferAgent(messageAgentIp,
//				messageAgentPort);
//
//		ITransferAgent oldAgent = null;
//		for (ITransferAgent t : agentMap.values())
//		{
//			if (t.equals(agent))
//				oldAgent = t;
//		}
//
//		if (oldAgent == null)
//		{
//			((RemoteTransferAgent) agent).init();
//			this.agentMap.put(serviceId, agent);
//		} else
//			this.agentMap.put(serviceId, oldAgent);
//	}
//
//	public void setLocalAgent(ITransferAgent agent)
//	{
//		this.localAgent = agent;
//	}
//
//	public void relaseRemoteAgent(long serviceId)
//	{
//		this.agentMap.remove(serviceId);
//	}
//	
//	private static MessageAgentManager instance = new MessageAgentManager();
//
//	public static MessageAgentManager getInstance()
//	{
//		return instance;
//	}
//
//}
