package ex02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;

public class Request implements ServletRequest{
	
	private InputStream is;
	
	private String uri;
	
	public Request(InputStream is) {
		this.is = is;
	}

	public void parse(){
		StringBuffer request = new StringBuffer(2048);
		int i;
		byte[] buffer = new byte[2048];
		try {
			i = is.read(buffer);
		} catch (IOException e) {
			e.printStackTrace();
			i = -1;
		}
		for (int j = 0; j < i; j++) {
			request.append((char)buffer[j]);
		}
		System.out.println(request.toString());
		uri = parseUri(request.toString());
	}
	
	private String parseUri(String requestString){
		int index1,index2;
		index1 = requestString.indexOf(' ');
		if(index1 != -1){
			index2 = requestString.indexOf(' ', index1+1);
			if(index2 > index1){
				return requestString.substring(index1+1, index2);
			}
		}
		return null;
	}
	
	public String getUri(){
		return uri;
	}
	
	public Object getAttribute(String name) {
		return null;
	}

	public Enumeration getAttributeNames() {
		return null;
	}

	public String getCharacterEncoding() {
		return null;
	}

	public int getContentLength() {
		return 0;
	}

	public String getContentType() {
		return null;
	}

	public ServletInputStream getInputStream() throws IOException {
		return null;
	}

	public Locale getLocale() {
		return null;
	}

	public Enumeration getLocales() {
		return null;
	}

	public String getParameter(String name) {
		return null;
	}

	public Map getParameterMap() {
		return null;
	}

	public Enumeration getParameterNames() {
		return null;
	}

	public String[] getParameterValues(String name) {
		return null;
	}

	public String getProtocol() {
		return null;
	}

	public BufferedReader getReader() throws IOException {
		return null;
	}

	public String getRealPath(String path) {
		return null;
	}

	public String getRemoteAddr() {
		return null;
	}

	public String getRemoteHost() {
		return null;
	}

	public RequestDispatcher getRequestDispatcher(String path) {
		return null;
	}

	public String getScheme() {
		return null;
	}

	public String getServerName() {
		return null;
	}

	public int getServerPort() {
		return 0;
	}

	public boolean isSecure() {
		return false;
	}

	public void removeAttribute(String name) {
	}

	public void setAttribute(String name, Object o) {
	}

	public void setCharacterEncoding(String env)
			throws UnsupportedEncodingException {
	}

	public String getLocalAddr() {
		return null;
	}

	public String getLocalName() {
		return null;
	}

	public int getLocalPort() {
		return 0;
	}

	public int getRemotePort() {
		return 0;
	}

}
