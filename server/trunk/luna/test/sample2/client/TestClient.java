package sample2.client;


import org.apache.log4j.Logger;

import sample1.client.TestClientController;

import com.doteyplay.luna.client.AsynchronismClientManager;
import com.doteyplay.luna.client.ConnectionInfo;
import com.doteyplay.luna.common.message.EncoderMessage;

/**
 * Õ¨≤Ω≤‚ ‘
 * 
 */
public class TestClient {
	static Logger logger=Logger.getLogger(TestClient.class.getName());
	private int id;
	AsynchronismClientManager client=new AsynchronismClientManager();
	public TestClient(){
		
	}
	public  void start(){
		try{
			long s=System.currentTimeMillis();
			System.out.println("start:"+s);
			String str="test";
			EncoderMessage request=new EncoderMessage((short)12,true);
			request.setRoleId(80);
			request.put((byte)1);
			for(int i=0;i<3;i++){
				request.put(i);
			}
			request.put(str);
		
			client.invoke(request);
			
			client.invoke(request);
			logger.debug("return -------------------------"+(System.currentTimeMillis()-s));
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		ConnectionInfo info=new ConnectionInfo("localhost",2888,5000,5000);
		TestClient testClient=new TestClient();
		testClient.client.initial(info,new TestClientController());
		testClient.start();
	}
}
