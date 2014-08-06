package sample1.client;

import org.apache.mina.core.session.IoSession;

import com.doteyplay.luna.common.action.BaseAction;
import com.doteyplay.luna.common.message.DecoderMessage;

public class ClientAction extends BaseAction {

	@Override
	public void doAction(IoSession session, DecoderMessage message) {
		System.out.println(message.getByte());
		for(int i=0;i<3;i++){
			System.out.println(message.getInt());
		}
		System.out.println(message.getString());
//		EncoderMessage encoderMessage=new EncoderMessage(message.getCommandId(),false);
//		encoderMessage.put(1111);
//		encoderMessage.put("²âÊÔ");
//		encoderMessage.put((byte)1);
//		encoderMessage.put((short)34);
//		this.messageSent(session, encoderMessage);
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
