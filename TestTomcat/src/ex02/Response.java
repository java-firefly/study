package ex02;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;

import ex01.HttpServer;

public class Response implements ServletResponse{
	
	private static final int BUFFER_SIZE = 1024;
	private OutputStream os;
	private Request request;
	private PrintWriter writer;
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
	
	public void flushBuffer() throws IOException {
	}

	public int getBufferSize() {
		return 0;
	}

	public String getCharacterEncoding() {
		return null;
	}

	public Locale getLocale() {
		return null;
	}

	public ServletOutputStream getOutputStream() throws IOException {
		return null;
	}

	public PrintWriter getWriter() throws IOException {
		writer = new PrintWriter(os,true);
		return writer;
	}

	public boolean isCommitted() {
		return false;
	}

	public void reset() {
	}

	public void resetBuffer() {
	}

	public void setBufferSize(int size) {
	}

	public void setContentLength(int len) {
	}

	public void setContentType(String type) {
	}

	public void setLocale(Locale loc) {
	}

	public String getContentType() {
		return null;
	}

	public void setCharacterEncoding(String charset) {
	}

}
