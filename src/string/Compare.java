package string;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;


public class Compare {
	public static void main(String[] args) {
		String filePath1 = "c:/1.xml";
		String filePath2 = "c:/2.xml";
		comStrInTwoFile(filePath1, filePath2);
	}
	private static void comStrInTwoFile(String filePath1, String filePath2){
		File file1 = new File(filePath1);
		File file2 = new File(filePath2);
		try {
			BufferedReader br1 = new BufferedReader(new InputStreamReader(new FileInputStream(file1)));
			BufferedReader br2 = new BufferedReader(new InputStreamReader(new FileInputStream(file2)));
			String str1 = "";
			while((str1 = br1.readLine()) != null){
				System.out.println("======");
				if(!str1.equals(br2.readLine())){
					System.out.println(str1);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
