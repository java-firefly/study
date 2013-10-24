package io;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class In {
	public List<String> getFileStrAsLine(String filePath){
		List<String> list = new ArrayList<String>();
		try {
			BufferedReader br = new BufferedReader(
					new InputStreamReader(new FileInputStream(filePath)));
			String str = "";
			while((str = br.readLine())!=null){
				list.add(str);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}
}
