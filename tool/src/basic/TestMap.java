package basic;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TestMap {
	public static void main(String[] args) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("prizeId", "-1");
		map.put("idCard", 1);
		displayMap(map);
		System.out.println("-------------------");
		map.put("idCard", 2);
		displayMap(map);
		System.out.println("-------------------");
	}
	private static void displayMap(Map<String, Object> map){
		Set<String> keys = map.keySet();
		for (String key : keys) {
			System.out.println(key+","+map.get(key));
		}
	}
}
