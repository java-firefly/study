package ex03.connector.http;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

public class HttpResponse implements HttpServletResponse{
	
	private int BUFFER_SIZE;
	HttpRequest request;
	OutputStream output;
	PrintWriter wirter;
	
	protected byte buffer;
	protected int bufferCount;
	protected boolean committed;
	protected int contentCount;
	protected int contentLength;
	protected String contentType;
	protected ArrayList cookies;
	protected HashMap headers;
	protected SimpleDateFormat format;
	protected String message;
	protected int status;
	
	public void setRequest(HttpRequest request) {
		this.request = request;
	}
	
	public HttpResponse(OutputStream output) {
		this.output = output;
	}	
	
	public void addCookie(Cookie cookie) {
	}

	public void addDateHeader(String name, long date) {
	}

	public void addHeader(String name, String value) {
	}

	public void addIntHeader(String name, int value) {
	}

	public boolean containsHeader(String name) {
		return false;
	}

	public String encodeRedirectURL(String url) {
		return null;
	}

	public String encodeRedirectUrl(String url) {
		return null;
	}

	public String encodeURL(String url) {
		return null;
	}

	public String encodeUrl(String url) {
		return null;
	}

	public void sendError(int sc) throws IOException {
	}

	public void sendError(int sc, String msg) throws IOException {
	}

	public void sendRedirect(String location) throws IOException {
	}

	public void setDateHeader(String name, long date) {
	}

	public void setHeader(String name, String value) {
	}

	public void setIntHeader(String name, int value) {
	}

	public void setStatus(int sc) {
	}

	public void setStatus(int sc, String sm) {
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
		return null;
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
