package com.doteyplay.game.message.tollgate;

import java.util.Collection;

import org.apache.mina.core.buffer.IoBuffer;

import com.doteyplay.game.MessageCommands;
import com.doteyplay.game.domain.tollgate.NodeInfo;
import com.doteyplay.game.domain.tollgate.TollgateInfo;
import com.doteyplay.game.message.proto.GateProBuf;
import com.doteyplay.net.message.AbstractMessage;

/**
 * @className:ShowInstanceDetail.java
 * @classDescription: 向客户段展示副本的详细信息。
 * @author:Tom.Zheng
 * @createTime:2014年6月24日 下午4:02:46
 */
public class ShowTollgateDetailMessage extends AbstractMessage {

	/**
	 * @author:Tom.Zheng
	 * @createTime:2014年6月24日 下午4:03:39
	 */
	private static final long serialVersionUID = 5889386302827062340L;
	

	private Collection<TollgateInfo> values ;
	
	
	
	public ShowTollgateDetailMessage()
	{
		super(MessageCommands.SHOW_TOLLGATE_DETAIL_MESSAGE);
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
		 out.put(getPGateResp().toByteArray());
		
	}
	public Collection<TollgateInfo> getValues() {
		return values;
	}
	public void setValues(Collection<TollgateInfo> values) {
		this.values = values;
	}
	
	public GateProBuf.PGateResp getPGateResp(){
		 GateProBuf.PGateResp.Builder newBuilder = GateProBuf.PGateResp.newBuilder();
		 for (TollgateInfo info : values) {
			 GateProBuf.PGateItem.Builder pGateItemBuilder = GateProBuf.PGateItem.newBuilder();
			 pGateItemBuilder.setIsLock(true);
			 pGateItemBuilder.setGateID(info.getTollgateId());
			 Collection<NodeInfo> nodes = info.getValues();
			 for (NodeInfo nodeInfo : nodes) {
				 //循环节点
				 GateProBuf.PNodeItem.Builder nodeBuilder = GateProBuf.PNodeItem.newBuilder();
				 nodeBuilder.setStar(nodeInfo.getStarResult());
				 nodeBuilder.setXID(nodeInfo.getNodeId());
				 nodeBuilder.setTimes(nodeInfo.getTimes());
				 pGateItemBuilder.addItems(nodeBuilder);
			}
			 newBuilder.addGates(pGateItemBuilder);
		 }
		 GateProBuf.PGateResp resp = newBuilder.build();
		 
		 return resp;
	}
	
	

}
