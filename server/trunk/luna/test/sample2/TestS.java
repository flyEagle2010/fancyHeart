package sample2;

import org.apache.mina.core.buffer.IoBuffer;

public class TestS {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
//			Socket socket =new Socket("192.68.1.118",2888);
//			InputStream is=socket.getInputStream();
			IoBuffer buf = IoBuffer.allocate(100);
			buf.put((byte)65);
			buf.put((byte)68);
			buf.flip();
			System.out.println(buf.getShort());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
