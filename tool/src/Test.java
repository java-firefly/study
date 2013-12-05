import java.util.LinkedHashMap;
import java.util.Map;


public class Test {
	private String a;
	private String b;
	private static Map<Integer, Boolean> lights = new LinkedHashMap<Integer, Boolean>();
	private static void initLights(Map<Integer, Boolean> lights){
		for(int i = 1; i <=100; i++){
			lights.put(i, false);
		}
	}
	public static void main(String[] args) {
		/*initLights(lights);
		for(int i=1; i <=100; i++){
			for(int j=1; j <=100; j++){
				if(j%i==0){
					if(lights.get(j)==true){
						lights.put(j, false);
					}else{
						lights.put(j, true);
					}
				}
			}
		}
		display(lights);*/
		System.out.println("张志永".equals("张志永"));
	}
	private static void display(Map<Integer, Boolean> lights){
		for(int i = 1; i <=100; i++){
			if(lights.get(i)==true){
				System.out.println(i);
			}
		}
	}
}
