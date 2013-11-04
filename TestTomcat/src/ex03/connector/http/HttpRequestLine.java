package ex03.connector.http;

public class HttpRequestLine {
	
	public HttpRequestLine() {
		
	}
	public HttpRequestLine(char[] method, int methodEnd, 
			char[] uri, int uriEnd, char[] protocol, int protocolEnd){
		
	}
	
	int INITIAL_METHOD_SIZE;
	int INITIAL_URI_SIZE;
	int INITIAL_PROTECOL_SIZE;
	int MAX_METHDO_SIZE;
	int MAX_URI_SIZE;
	int MAX_PROTOCOL_SIZE;
	char[] method;
	int methodEnd;
	char[] uri;
	int uriEnd;
	char[] protocol;
	int protocolEnd;
	
	/**
	 * 释放所有的对象引用，初始化实例变量，为重新使用该对象做准备
	 */
	public void recycle(){
		methodEnd = 0;
		uriEnd = 0;
		protocolEnd = 0;
	}
	
	public int indexOf(char[] buf){
		return 1;
	}
	
	public int indexOf(char[] buf, int end){
		return 1;
	}
	
	public int indexOf(String str){
		return 1;
	}
	public int indexOf(char c, int start){
		return 1;
	}
	@Override
	public int hashCode() {
		return super.hashCode();
	}
	
	@Override
	public boolean equals(Object arg0) {
		return super.equals(arg0);
	}
	
}
