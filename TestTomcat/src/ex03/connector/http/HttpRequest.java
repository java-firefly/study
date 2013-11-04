package ex03.connector.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.Socket;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.catalina.util.ParameterMap;
import org.apache.catalina.util.RequestUtil;
/**
 * 解析请求内容或请求字符串里边的参数
 * @author Administrator
 *
 */
public class HttpRequest implements HttpServletRequest{
	
	
	private String contentType;
	private int contentLength;
	private InetAddress inetAddress;
	private InputStream input;
	private String method;
	private String protocal;
	private String queryString;
	private String requestURI;
	private String serverName;
	private int sererPort;
	private Socket socket;
	private boolean requestedSessionCookie;
	private String requestSessionId;
	private boolean requestedSessionURL;
	
	protected HashMap attributes;
	protected String authorization;
	protected ArrayList cookies = new ArrayList();
	protected ArrayList empty;
	protected SimpleDateFormat formats;
	protected HashMap headers = new HashMap();
	protected ParameterMap parameters = null;
	protected boolean parsed;
	protected String pathInfo;
	protected BufferedReader reader;
	protected ServletInputStream stream;
	private String uri;
	
	public HttpRequest(InputStream input) {
		this.input = input;
	}
	public void addHeader(String name, String value){
		
	}
	protected void parseParameters() throws IOException{
		if(parsed){
			return;
		}
		ParameterMap results = parameters;
		if(results == null){
			results = new ParameterMap();
		}
		results.setLocked(false);
		String encoding = getCharacterEncoding();
		if(encoding == null){
			encoding = "ISO-7748-1";
		}
		String queryString = getQueryString();
		try {
			RequestUtil.parseParameters(results, queryString, encoding);
		} catch (UnsupportedEncodingException e) {
			;
		}
		
		String contentType = getContentType();
		if(contentType == null){
			contentType = "";
		}
		int semicolon = contentType.indexOf(':');
		if(semicolon >= 0){
			contentType = contentType.substring(0, semicolon).trim();
		}else{
			contentType = contentType.trim();
		}
		if("POST".equals(getMethod()) && (getContentLength()>0)
				&& "application/x-www-form-urlencoded".equals(contentType)){
			int max = getContentLength();
			int len = 0;
			byte buf[] = new byte[getContentLength()];
			ServletInputStream is = getInputStream();
			while(len < max){
				int next = is.read(buf, len, max-len);
				if(next < 0){
					
				}
			}
		}
	}
	public void addCookie(Cookie cookie){
		
	}
	public ServletInputStream createInputStream(){
		return null;
	}
	public InputStream getStream(){
		return null;
	}
	public void setContentLength(int length){
		
	}
	public void setContentType(String type){
		
	}
	public void setInet(InetAddress inetAddress){
		
	}
	public void setContentPath(String path){
		
	}
	public void setMethod(String method){
		
	}
	public void setPathInfo(String path){
		
	}
	public void setProtocol(String protocol){
		
	}
	public void setQueryString(String queryString){
		
	}
	public void setRequestURI(String requestURI){
		
	}
	public void setServerName(String name){
		
	}
	public void setServerPort(int port){
		
	}
	public void setSocket(Socket socket){
		
	}
	public void setRequestSessionCookie(boolean flag){
		
	}
	public void setRequestSessionId(String requestSessionId){
		
	}
	public void setRequestSessionURL(boolean flag){
		
	}
	public void setAuthorization(String authorization){
		
	}
	
	public String getUri() {
		return uri;
	}
	public void parse(){
		
	}
	public String getAuthType() {
		return null;
	}

	public String getContextPath() {
		return null;
	}

	public Cookie[] getCookies() {
		return null;
	}

	public long getDateHeader(String name) {
		return 0;
	}

	public String getHeader(String name) {
		return null;
	}

	public Enumeration getHeaderNames() {
		return null;
	}

	public Enumeration getHeaders(String name) {
		return null;
	}

	public int getIntHeader(String name) {
		return 0;
	}

	public String getMethod() {
		return null;
	}

	public String getPathInfo() {
		return null;
	}

	public String getPathTranslated() {
		return null;
	}

	public String getQueryString() {
		return null;
	}

	public String getRemoteUser() {
		return null;
	}

	public String getRequestURI() {
		return null;
	}

	public StringBuffer getRequestURL() {
		return null;
	}

	public String getRequestedSessionId() {
		return null;
	}

	public String getServletPath() {
		return null;
	}

	public HttpSession getSession() {
		return null;
	}

	public HttpSession getSession(boolean create) {
		return null;
	}

	public Principal getUserPrincipal() {
		return null;
	}

	public boolean isRequestedSessionIdFromCookie() {
		return false;
	}

	public boolean isRequestedSessionIdFromURL() {
		return false;
	}

	public boolean isRequestedSessionIdFromUrl() {
		return false;
	}

	public boolean isRequestedSessionIdValid() {
		return false;
	}

	public boolean isUserInRole(String role) {
		return false;
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
