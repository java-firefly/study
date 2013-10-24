package string;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SplitStr {
	public static void main(String[] args) {
		String str = "nationalityId#=#1,0" +
			"#and#user.id#=#user.id" +
			"#or#unit.code#=#unit.code" +
			"#and#aaaa#=#aaaa";
		main2(str);
	}
	
	public static Map<String, List<String>> main2(String str){
		List<String> andConditions = new ArrayList<String>();
		List<String> orConditions = new ArrayList<String>();
		Map<String, List<String>> conditionMap = new HashMap<String, List<String>>();
		conditionMap.put("and", andConditions);
		conditionMap.put("or", orConditions);
		
		String[] strs = str.split("#and#");
		
		for (String string : strs) {
			if(string.contains("#or#")){
				String[] strs2 = string.split("#or#");
				andConditions.add(strs2[0]);
				for (int i = 1; i < strs2.length; i++) {
					orConditions.add(strs2[i]);
				}
			}else{
				andConditions.add(string);
			}
		}
		return conditionMap;
	}
}
