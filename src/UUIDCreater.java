import java.util.UUID;


public class UUIDCreater {
	public static void main(String[] args) {
		for (int i = 0; i < 1; i++) {
			System.out.println(UUID.randomUUID().toString().replace("-", "")); 
		}
	}
}
