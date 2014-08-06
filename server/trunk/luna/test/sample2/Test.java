package sample2;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		System.out.println(Integer.toBinaryString((1<<7)|(2&0xf)));
//		System.out.println(((1<<7)|(2&0xf))^(1<<7));
//		System.out.println((1^3));
		
		
	      outer: // cant have statements here
	      for(; true ;) { // infinite loop
	           inner: // cant have statements here
	           for(int i=0; i < 10; i++) {
	                System.out.println("i = " + i);
	                if(i == 2) {
	                	System.out.println("continue");
	                    continue;
	                }
	                if(i == 3) {
	                	System.out.println("break");
//	                    i++; // otherwise i never
	                          // gets incremented.
	                    break;
	                }
	                if(i == 7) {
	                	System.out.println("continue outer");
	                    i++; // otherwise i never
	                           // gets incremented.
	                    continue outer;
	                }
	                if(i == 8) {
	                	System.out.println("break outer");
	                    break outer;
	                }

	                for(int k = 0; k < 5; k++) {
	                    if(k == 3) {
	                    	System.out.println("continue inner");
	                         continue inner;
	                    }
	                }
	           }
	      }

	}
}
