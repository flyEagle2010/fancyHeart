package com.doteyplay.game.message.chat;                                                                                                                                                                                       
                                                                                                                                                                                                                             
import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;

import com.doteyplay.game.MessageCommands;
import com.doteyplay.game.message.proto.ChatProBuf;
import com.doteyplay.net.message.AbstractMessage;
import com.google.protobuf.InvalidProtocolBufferException;
                                                                                                                                                                                                                             
public class ChatTagMessage extends AbstractMessage                                                                                                                                                                               
{                                                                                                                                                                                                                            
	/**
	 * @author:Tom.Zheng
	 * @createTime:2014年12月2日 上午10:33:51
	 */
	private static final long serialVersionUID = -6266885241074826387L;

	private static final Logger logger = Logger                                                                                                                                                                              
			.getLogger(ChatTagMessage.class);                                                                                                                                                                                     
                                                                                                                                                                                                                             
	public int type;
	
	public ChatTagMessage()                                                                                                                                                                                                       
	{                                                                                                                                                                                                                        
		super(MessageCommands.CHAT_TAG_MESSAGE);                                                                                                                                                                                   
	}                                                                                                                                                                                                                        
                                                                                                                                                                                                                             
	@Override                                                                                                                                                                                                                
	public void decodeBody(IoBuffer in)                                                                                                                                                                                      
	{                                                                                                                                                                                                                        
		byte[] bytes = getProtoBufBytes(in);                                                                                                                                                                                 
		try                                                                                                                                                                                                                  
		{                                                                                                                                                                                                                    
			ChatProBuf.PChatTag req = ChatProBuf.PChatTag                                                                                                                                                            
					.parseFrom(bytes);                                                                                                                                                                                       
			type = req.getType();                                                                                                                                                                                       
		} catch (InvalidProtocolBufferException e)                                                                                                                                                                           
		{                                                                                                                                                                                                                    
			e.printStackTrace();                                                                                                                                                                                             
		}                                                                                                                                                                                                                    
	}                                                                                                                                                                                                                        
                                                                                                                                                                                                                             
	@Override                                                                                                                                                                                                                
	public void encodeBody(IoBuffer out)                                                                                                                                                                                     
	{                                                                                                                                                                                                                        
		
	}                                                                                                                                                                                                                        
                                                                                                                                                                                                                             
	@Override                                                                                                                                                                                                                
	public void release() {                                                                                                                                                                                                  
		// TODO Auto-generated method stub                                                                                                                                                                                   
		                                                                                                                                                                                                                     
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}


	
	
	
	
	
}                                                                                                                                                                                                                            
                                                                                                                                                                                                                             
