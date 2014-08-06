
public class CGHTest {

	static byte a;
	static int b;
	static int c;
	public static void init(byte a1,int b1)
	{
		a = a1;
		b = b1;
	}
	
	public static void encode()
	{
		int tmp = 0xFFFFFFFF &(a << 24);
		System.out.println(Integer.toBinaryString(tmp));
		System.out.println(Integer.toBinaryString((0|b)));
		int tmp2 = (0x00<<24)|b;
		int tmp3 = tmp | tmp2;
		System.out.println(Integer.toBinaryString(tmp3));
		c = tmp3;
	}
	
	public static void decode()
	{
		System.out.println((c <<8) >> 8);
		
	}
	public static void main(String[] args) 
	{
		init((byte)8,4);
		encode();
		decode();
	}
}
