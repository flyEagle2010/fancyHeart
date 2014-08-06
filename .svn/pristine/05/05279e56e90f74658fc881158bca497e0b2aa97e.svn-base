package sample1.server;

import org.apache.mina.core.session.IoSession;

import sample1.client.User;

import com.doteyplay.luna.common.action.BaseAction;
import com.doteyplay.luna.common.message.DecoderMessage;
import com.doteyplay.luna.common.message.EncoderMessage;

public class TestAction extends BaseAction {

	@Override
	public void doAction(IoSession session, DecoderMessage message) {
		System.out.println(message.getRoleId());
		System.out.println(message.getByte());
		User u = (User)message.getObject();
		System.out.println(u.id);
		System.out.println(u.name);
		EncoderMessage em = new EncoderMessage((short)0,false);
		em.setSynKey(message.getSynKey());
		em.putObject(u);
		this.messageSent(session, em);
//		System.out.println(message.getbu.hashCode());
//		System.out.println(message.getByte());
//		for(int i=0;i<33;i++){
//			System.out.println(message.getInt());
//		}
		
//		EncoderMessage encoderMessage=new EncoderMessage(message.getCommandId(),false);
//		
//		encoderMessage.put(1111);
//		encoderMessage.put("²âÊÔ");
//		encoderMessage.put((byte)1);
//		encoderMessage.put((short)34);
//		
//		this.messageSent(session, encoderMessage);
//		EncoderMessage em = new EncoderMessage((short) 123,false);
//		em.put((byte)5);
//		em.put((byte)4);
//		em.put((byte)3);
//		em.put((byte)2);
//		em.put((byte)1);
//		this.messageSent(session, em);
	}

	@Override
	public void doAction(long roleId) {
		// TODO Auto-generated method stub
		
	}

//	@Override
//	public EncoderMessage doAction(DecoderMessage message) {
//		System.out.println(message.getInt());
//		System.out.println(message.getByte());
//		System.out.println(message.getString());
//		EncoderMessage encoderMessage=new EncoderMessage(message.getCommandId(),false);
////		encoderMessage.put(1111);
////		encoderMessage.put("²âÊÔ");
////		encoderMessage.put((byte)1);
////		encoderMessage.put((short)34);
//		return encoderMessage;
//	}
}
