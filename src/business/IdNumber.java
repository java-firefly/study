package business;

import java.util.Random;

public class IdNumber {
	public static void main(String[] args) {
		for (int i = 0; i < 40; i++) {
			System.out.println(createIDNumber());
		}
	}
	public static String createIDNumber(){
		Random random = new Random();
		int prefix = 100000+random.nextInt(900000);
		int year = random.nextInt(100);
		int month = random.nextInt(13);
		int day = random.nextInt(30);
		int suffix = 1000+random.nextInt(9000);
		StringBuffer sb  = new StringBuffer();
		sb.append(prefix);
		sb.append("19");
		if(year<10){
			sb.append(0);
		}
		sb.append(year);
		if(month<10){
			sb.append(0);
		}
		sb.append(month);
		if(day<10){
			sb.append(0);
		}
		sb.append(day);
		sb.append(suffix);
		return sb.toString();
	}
}
