package sample1.client;

import org.apache.log4j.Logger;

import com.doteyplay.luna.client.ConnectionInfo;
import com.doteyplay.luna.client.SynchronicClientManager;
import com.doteyplay.luna.common.message.DecoderMessage;
import com.doteyplay.luna.common.message.EncoderMessage;

/**
 * Õ¨≤Ω≤‚ ‘
 * 
 */
public class TestClient {
	static Logger logger = Logger.getLogger(TestClient.class.getName());
	private int id;
	static SynchronicClientManager client = new SynchronicClientManager();
	static ConnectionInfo info = new ConnectionInfo("localhost", 8888,5000,120);
	static{
		client.initial(info);
	}

	public TestClient() {

	}
	public static void test() throws Exception{
		EncoderMessage request = new EncoderMessage((short) 12, true);
		request.setRoleId(44);
		request.put((byte) 1);
		User u = new User();
		u.id = 99;
		u.name="333";
		request.putObject(u);
		DecoderMessage message = client.synInvoke(request);
		User u2 = (User) message.getObject();
		System.out.println(u2.id);
		System.out.println(u2.name);
	}

	public void start() {
		try {
			long s = System.currentTimeMillis();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		TestClient.test();
//		Thread.sleep(1000*20);
//		TestClient.test();
//		Thread.sleep(1000*20);
//		TestClient.test();
	}
}
