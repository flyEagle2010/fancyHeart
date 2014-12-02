package com.doteyplay.game.message.chat;                                                                                                                                                                                       
                                                                                                                                                                                                                             
import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;

import com.doteyplay.game.MessageCommands;
import com.doteyplay.game.domain.chat.ChatMsgBean;
import com.doteyplay.game.message.proto.ChatProBuf;
import com.doteyplay.net.message.AbstractMessage;
import com.google.protobuf.InvalidProtocolBufferException;
                                                                                                                                                                                                                             
public class ChatMsgUpdateMessage extends AbstractMessage                                                                                                                                                                               
{                                                                                                                                                                                                                            
	/**
	 * @author:Tom.Zheng
	 * @createTime:2014年12月2日 上午10:28:01
	 */
	private static final long serialVersionUID = 2030662962310675658L;


	private static final Logger logger = Logger                                                                                                                                                                              
			.getLogger(ChatMsgUpdateMessage.class);                                                                                                                                                                                     
                                                                                                                                                                                                                             

	private ChatMsgBean msgBean;
	
	public ChatMsgUpdateMessage()                                                                                                                                                                                                       
	{                                                                                                                                                                                                                        
		super(MessageCommands.CHAT_UPDATE_MESSAGE);                                                                                                                                                                                   
	}                                                                                                                                                                                                                        
                                                                                                                                                                                                                             
	@Override                                                                                                                                                                                                                
	public void decodeBody(IoBuffer in)                                                                                                                                                                                      
	{                                                                                                                                                                                                                        
	}                                                                                                                                                                                                                        
                                                                                                                                                                                                                             
	@Override                                                                                                                                                                                                                
	public void encodeBody(IoBuffer out)                                                                                                                                                                                     
	{ 
		ChatProBuf.PChatUpdate.Builder builder = ChatProBuf.PChatUpdate                                                                                                                                                    
				.newBuilder();  
		builder.setRoleId(msgBean.getSendRoleId());
		builder.setMsgName(msgBean.getSendRoleName());
		builder.setMsgType(msgBean.getSendType());
		builder.setMsg(msgBean.getMsg());
		out.put(builder.build().toByteArray());
	}                                                                                                                                                                                                                        
                                                                                                                                                                                                                             
	@Override                                                                                                                                                                                                                
	public void release() {                                                                                                                                                                                                  
		// TODO Auto-generated method stub                                                                                                                                                                                   
		                                                                                                                                                                                                                     
	}

	public ChatMsgBean getMsgBean() {
		return msgBean;
	}

	public void setMsgBean(ChatMsgBean msgBean) {
		this.msgBean = msgBean;
	}

	
	
	
	
}                                                                                                                                                                                                                            
                                                                                                                                                                                                                             
