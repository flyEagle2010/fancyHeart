package com.doteyplay.game.message.chat;                                                                                                                                                                                       
                                                                                                                                                                                                                             
import org.apache.log4j.Logger;                                                                                                                                                                                              
import org.apache.mina.core.buffer.IoBuffer;                                                                                                                                                                                 
                                                                                                                                                                                                                             

import com.doteyplay.game.MessageCommands;                                                                                                                                                                                   
import com.doteyplay.game.message.proto.AccountProBuf;                                                                                                                                                                       
import com.doteyplay.game.message.proto.ChatProBuf;
import com.doteyplay.net.message.AbstractMessage;                                                                                                                                                                            
import com.google.protobuf.InvalidProtocolBufferException;                                                                                                                                                                   
                                                                                                                                                                                                                             
public class ChatMessage extends AbstractMessage                                                                                                                                                                               
{                                                                                                                                                                                                                            
	private static final Logger logger = Logger                                                                                                                                                                              
			.getLogger(ChatMessage.class);                                                                                                                                                                                     
                                                                                                                                                                                                                             
	private String source;     
	
	private String result;
	public ChatMessage()                                                                                                                                                                                                       
	{                                                                                                                                                                                                                        
		super(MessageCommands.CHAT_MESSAGE);                                                                                                                                                                                   
	}                                                                                                                                                                                                                        
                                                                                                                                                                                                                             
	@Override                                                                                                                                                                                                                
	public void decodeBody(IoBuffer in)                                                                                                                                                                                      
	{                                                                                                                                                                                                                        
		byte[] bytes = getProtoBufBytes(in);                                                                                                                                                                                 
		try                                                                                                                                                                                                                  
		{                                                                                                                                                                                                                    
			ChatProBuf.PChatReq req = ChatProBuf.PChatReq                                                                                                                                                            
					.parseFrom(bytes);                                                                                                                                                                                       
			source = req.getMsg();                                                                                                                                                                                       
		} catch (InvalidProtocolBufferException e)                                                                                                                                                                           
		{                                                                                                                                                                                                                    
			e.printStackTrace();                                                                                                                                                                                             
		}                                                                                                                                                                                                                    
	}                                                                                                                                                                                                                        
                                                                                                                                                                                                                             
	@Override                                                                                                                                                                                                                
	public void encodeBody(IoBuffer out)                                                                                                                                                                                     
	{                                                                                                                                                                                                                        
		ChatProBuf.PChatResp.Builder builder = ChatProBuf.PChatResp                                                                                                                                                    
				.newBuilder();  
		builder.setMsg(result);
		out.put(builder.build().toByteArray());
	}                                                                                                                                                                                                                        
                                                                                                                                                                                                                             
	@Override                                                                                                                                                                                                                
	public void release() {                                                                                                                                                                                                  
		// TODO Auto-generated method stub                                                                                                                                                                                   
		                                                                                                                                                                                                                     
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}  
	
	
	
}                                                                                                                                                                                                                            
                                                                                                                                                                                                                             
