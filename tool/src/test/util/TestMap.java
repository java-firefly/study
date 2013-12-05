package test.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class TestMap {
	public static void main(String[] args) {
		Map<String,String> map = new HashMap<String, String>();
		map.put("one", "1");
		map.put("two", "2");
		map.put("three", "3");
		map.put("four", "4");
		map.put("five", "5");
		map.put("six", "6");
		map.put("seven", "7");
		map.put("eight", "8");
		map.put("nine", "9");
		map.put("ten", "10");
		long start = System.currentTimeMillis();
		Collection<String> cols = map.values();
		long end = System.currentTimeMillis();
		System.out.println(end-start);
		/*for (String string : cols) {
			System.out.println(string);
		}*/
	}
}
