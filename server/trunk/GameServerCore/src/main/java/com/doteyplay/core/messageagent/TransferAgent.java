package com.doteyplay.core.messageagent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.mina.core.buffer.IoBuffer;

import com.doteyplay.core.bhns.BOServiceManager;
import com.doteyplay.core.bhns.gateway.IGateWayService;
import com.doteyplay.core.bhns.location.ISvrLocationService;
import com.doteyplay.net.message.AbstractMessage;

/**
 * 消息转发代理
 * 
 * @author
 * 
 */
public class TransferAgent implements ITransferAgent
{

	private TransferAgent()
	{
	}
	
	
	public void init()
	{
	}

	public void sendMessage(long roleId, AbstractMessage message)
	{
		IGateWayService service = BOServiceManager.findService(
				IGateWayService.PORTAL_ID, roleId);

		IoBuffer buffer = message.encodeIoBuffer();
		int byteLength = buffer.limit();
		byte[] tmpMessageBytes = new byte[byteLength];
		buffer.get(tmpMessageBytes, 0, byteLength);
		service.sendMessage(tmpMessageBytes);
	}

	public void broadcastMessage(List<Long> roleIdLst,
			AbstractMessage message)
	{
		ISvrLocationService service = BOServiceManager
				.findLocalDefaultService(ISvrLocationService.PORTAL_ID);

		Map<Byte, List<Long>> map = new HashMap<Byte, List<Long>>();
		for (long roleId : roleIdLst)
		{
			byte endpointId = service.getEndpointIdOfserviceId(
					IGateWayService.PORTAL_ID, roleId);

			List<Long> tmp = map.get(endpointId);
			if (tmp == null)
			{
				tmp = new ArrayList<Long>();
				map.put(endpointId, tmp);
			}
			tmp.add(roleId);
		}

		for (Entry<Byte, List<Long>> e : map.entrySet())
		{
			IGateWayService gateWayService = BOServiceManager
					.findDefaultService(IGateWayService.PORTAL_ID, e.getKey());
			gateWayService.broadcastMessage(e.getValue(), message);
		}
	}

	
	//*********************************************
	private static TransferAgent instance = new TransferAgent();
	
	public static TransferAgent getInstance()
	{
		return instance;
	}
}
