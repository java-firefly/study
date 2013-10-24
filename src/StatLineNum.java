import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class StatLineNum {
	public static void main(String[] args) {
		StatLineOfFile("D:/workspace/tomcat6/src/org/apache/catalina/connector/Connector.java");
		System.out.println(count);
	}
	private static int count = 0;
	private static Map<String,Integer> map = new HashMap<String, Integer>();
	public static void StaLineNumOfFilesOfDir(String dirPath){
		File dir = new File(dirPath);
		if(dir.isDirectory()){
			File[] files = dir.listFiles();
			for (File file : files) {
				if(file.isDirectory()){
					StaLineNumOfFilesOfDir(file.getAbsolutePath());
				}else{
					StatLineOfFile(file.getAbsolutePath());
				}
			}
		}
	}
	public static void StatLineOfFile(String filePath){
		BufferedReader br;
		try {
			br = new BufferedReader(new InputStreamReader(
					new FileInputStream(filePath)));
			String str = "";
			while((str = br.readLine()) != null){
				count++;
			} 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
