package sample2.server;

import org.apache.mina.core.session.IoSession;

import com.doteyplay.luna.common.action.BaseAction;
import com.doteyplay.luna.common.message.DecoderMessage;

public class TestAction extends BaseAction {

	@Override
	public void doAction(IoSession session, DecoderMessage message) {
		System.out.println(message.getInt());
		System.out.println(message.getByte());
		boolean flag=true;
		while(flag){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println(message.getString());
		
//		EncoderMessage encoderMessage=new EncoderMessage(message.getCommandId(),false);
//		encoderMessage.put(1111);
//		encoderMessage.put("≤‚ ‘");
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
////		encoderMessage.put("≤‚ ‘");
////		encoderMessage.put((byte)1);
////		encoderMessage.put((short)34);
//		return encoderMessage;
//	}
}
