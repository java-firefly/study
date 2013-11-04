package ex01;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Response {
	private static final int BUFFER_SIZE = 1024;
	private OutputStream os;
	private Request request;
	public Response(OutputStream os) {
		this.os = os;
	}
	public void setRequest(Request request) {
		this.request = request;
	}
	public void sendStaticResource() throws IOException{
		byte[] bytes = new byte[BUFFER_SIZE];
		FileInputStream fis = null;
		File file = new File(HttpServer.WEB_ROOT,request.getUri());
		try {
			if(file.exists()){
				fis = new FileInputStream(file);
				int ch = fis.read(bytes, 0, BUFFER_SIZE);
				while(ch != -1){
					os.write(bytes, 0, ch);
					ch = fis.read(bytes, 0, BUFFER_SIZE);
				}
			}else{
				String errorMessage = 
					  "Content-Type: text/html\r\n" 
					+ "Content-Length: 23\r\n" 
					+ "\r\n" 
					+ "<h1>File Not Found</h1>";
					os.write(errorMessage.getBytes());
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		} finally{
			if(fis != null){
				fis.close();
			}
		}
		
	}
}
