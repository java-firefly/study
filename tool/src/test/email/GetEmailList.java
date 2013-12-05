package test.email;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class GetEmailList {
	public static void main(String[] args) {
		List<String> emails = getFileStrAsLine("D:/download/googleDownload/email4");
		for (String string : emails) {
			System.out.println(string);
		}
	}
	public static List<String> getFileStrAsLine(String filePath){
		File dir = new File(filePath);
		File[] files = dir.listFiles();
		List<String> list = new ArrayList<String>();
		for (File file : files) {
			try {
				BufferedReader br = new BufferedReader(
						new InputStreamReader(new FileInputStream(file)));
				String str = "";
				while((str = br.readLine())!=null){
					if(str.startsWith("To: ")){
						list.add(str);
						break;
					}
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
}
