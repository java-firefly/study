package ex03.connector.http;

public class HttpHeader {
	int INTIAL_NAME_SIZE;
	int INTIAL_VALUE_SIZE;
	int MAX_NAME_SIZE;
	int MAX_VALUE_SIZE;
	char[] name;
	int nameEnd;
	char[] value;
	int valueEnd;
	int hashCode;
	public HttpHeader() {
		
	}
}
