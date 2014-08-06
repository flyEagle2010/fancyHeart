package com.doteyplay.game.message.tollgate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.mina.core.buffer.IoBuffer;

import com.doteyplay.game.MessageCommands;
import com.doteyplay.game.message.proto.GateProBuf;
import com.doteyplay.game.message.proto.GateProBuf.PNodeItem;
import com.doteyplay.game.message.proto.GateProBuf.PUpdateGates;
import com.doteyplay.game.message.proto.GateProBuf.PUpdateNode;
import com.doteyplay.game.message.proto.GateProBuf.PUpdateNode.Builder;
import com.doteyplay.game.message.proto.GateProBuf.PUpdateNodes;
import com.doteyplay.net.message.AbstractMessage;

/**
 *
* @className:TollgateAndNodeChangeInfoMessage.java
* @classDescription:向客户段展示副本及节点的增量改变消息
* @author:Tom.Zheng
* @createTime:2014年6月24日 下午6:52:10
 */
public class NodeChangeMessage extends AbstractMessage {
	/**
	 * @author:Tom.Zheng
	 * @createTime:2014年6月24日 下午6:52:28
	 */
	private static final long serialVersionUID = -2782476069461493749L;
	

	private int tollgateId;
	
	private List<GateProBuf.PUpdateNode.Builder> items = new ArrayList<GateProBuf.PUpdateNode.Builder>();
	

	public NodeChangeMessage()
	{
		super(MessageCommands.SEND_NODE_CHANGE_MESSAGE);
	}
	@Override
	public void release() {
		// TODO Auto-generated method stub

	}

	@Override
	public void decodeBody(IoBuffer in) {
		// TODO Auto-generated method stub
	
	}

	@Override
	public void encodeBody(IoBuffer out) {
		// TODO Auto-generated method stub
		//生成byte数组.发出去.
		GateProBuf.PUpdateNodes.Builder builder = GateProBuf.PUpdateNodes.newBuilder();
		builder.setGateID(tollgateId);
		for (Builder key : items) {
			builder.addNodeItems(key);
		}
		PUpdateNodes build = builder.build();
		out.put(build.toByteArray());
		
		
		
	}
	public int getTollgateId() {
		return tollgateId;
	}
	public void setTollgateId(int tollgateId) {
		this.tollgateId = tollgateId;
	}
	
	public void addUpdateItem(int nodeId,int times,int star,int type){
		if(items == null){
			items = new ArrayList<GateProBuf.PUpdateNode.Builder>();
		}
		GateProBuf.PUpdateNode.Builder itemBuilder = GateProBuf.PUpdateNode.newBuilder();
		
		itemBuilder.setType(type);
		PNodeItem.Builder builder = PNodeItem.newBuilder();
		builder.setXID(nodeId);
		builder.setStar(star);
		builder.setTimes(times);
		itemBuilder.setNodeItem(builder);
		items.add(itemBuilder);
	}
	public List<GateProBuf.PUpdateNode.Builder> getItems() {
		return items;
	}
	public void setItems(List<GateProBuf.PUpdateNode.Builder> items) {
		this.items = items;
	}
	
	
	

}
