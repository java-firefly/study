package io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Out {
	public static void main(String[] args) throws IOException {
		File file = new File("C:/test/text.txt");
		if(!file.exists()){
			file.createNewFile();
		}
		String str = "你好\r\n我好";
		new Out().out(file.getAbsolutePath(), str);
	}
	
	public void out(String filePath, String str){
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new FileOutputStream(filePath),true);
			pw.write(str);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally{
			pw.close();
		}
	}
}
