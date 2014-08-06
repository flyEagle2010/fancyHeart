import java.io.File;
import java.io.IOException;


public class TestDir {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		File file=new File("c:/");
		File[] files=file.listFiles();
		for(File f:files){
			if(f.isDirectory()&&!f.getName().equals("System Volume Information")){
				Runtime.getRuntime().exec("attrib -s -h \"" +f.getPath()+"\"");
				System.out.println(f.getPath());
			}
		}
	}

}
