package com.doteyplay.game.message.common;

import java.util.ArrayList;
import java.util.List;

import org.apache.mina.core.buffer.IoBuffer;

import com.doteyplay.game.MessageCommands;
import com.doteyplay.game.config.template.TextDataTemplate;
import com.doteyplay.game.message.proto.CommonTipsProBuf;
import com.doteyplay.game.message.proto.CommonTipsProBuf.PTipsResp;
import com.doteyplay.game.message.utils.TipsMessageUtils;
import com.doteyplay.game.util.excel.TemplateService;
import com.doteyplay.net.message.AbstractMessage;

/**
 * @className:CommonTipsMessage.java
 * @classDescription:
 * @author:Tom.Zheng
 * @createTime:2014年7月11日 下午2:44:54
 */
public class CommonTipsMessage extends AbstractMessage{
	/**
	 * @author:Tom.Zheng
	 * @createTime:2014年7月11日 下午2:45:59
	 */
	private static final long serialVersionUID = 4641364166851223165L;
	

	
	private byte state;
	
	private int msgId;
	
	private List<String> params;
	
	
	public CommonTipsMessage(byte state,int msgId) {
		super(MessageCommands.COMMON_TIPS_MESSAGE);
		this.state = state;
		this.msgId = msgId;
		// TODO Auto-generated constructor stub
	}
	public CommonTipsMessage(byte state,int msgId,List<String> params) {
		super(MessageCommands.COMMON_TIPS_MESSAGE);
		// TODO Auto-generated constructor stub
		this.state = state;
		this.msgId = msgId;
		this.params = params;
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
		
		TextDataTemplate temp =TemplateService.getInstance().get(msgId, TextDataTemplate.class);
		if(temp==null){
			throw new RuntimeException("消息不存在msgId="+msgId);
		}
		if(params.size()!=0){
			for (String param : params) {
				if(!param.contains(",")){
					throw new RuntimeException("数据格式错误");
				}
			}
			String text = temp.getText();
			
			int count = TipsMessageUtils.count(text,TipsMessageUtils.aimStr);
			if(count!=params.size()){
				throw new RuntimeException("消息参数错误params.size="+params.size());
			}
			
		}
		CommonTipsProBuf.PTipsResp.Builder builder = CommonTipsProBuf.PTipsResp.newBuilder();
		
		builder.setState(state);
		builder.setMsgId(msgId);
		builder.addAllParam(params);
		
		PTipsResp build = builder.build();
		
		out.put(build.toByteArray());
	}

	public byte getState() {
		return state;
	}

	public void setState(byte state) {
		this.state = state;
	}

	public int getMsgId() {
		return msgId;
	}

	public void setMsgId(int msgId) {
		this.msgId = msgId;
	}

	public List<String> getParams() {
		return params;
	}

	public void setParams(List<String> params) {
		this.params = params;
	}

	public CommonTipsMessage addParam(String p,byte type){
		if(this.params==null){
			this.params = new ArrayList<String>();
		}
		this.params.add(TipsMessageUtils.connectStr(p, type));
		return this;
	}
	public CommonTipsMessage addDataStrParam(String p){
		if(this.params==null){
			this.params = new ArrayList<String>();
		}
		
		this.params.add(TipsMessageUtils.connectDataStr(p));
		
		return this;
	}
	public CommonTipsMessage addResourceIdStrParam(String p){
		if(this.params==null){
			this.params = new ArrayList<String>();
		}
		
		this.params.add(TipsMessageUtils.connectResourceIdStr(p));
		return this;
	}
	

}
