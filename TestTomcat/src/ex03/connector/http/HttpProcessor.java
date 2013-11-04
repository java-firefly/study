package ex03.connector.http;

import java.io.EOFException;
import java.io.OutputStream;
import java.net.Socket;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;

import org.apache.catalina.util.RequestUtil;
import org.apache.catalina.util.StringManager;

import ex03.ServletProcessor;
import ex03.StaticResourceProcessor;

/**
 * 创建请求和响应对象
 * @author 张志永
 *
 */
public class HttpProcessor {
	
	private HttpConnector connector;
	private HttpRequest request;
	private HttpResponse response;
	private HttpRequestLine requestLine = new HttpRequestLine();
	
	protected String method;
	protected String queryString;
	protected StringManager sm;
	
	public HttpProcessor(HttpConnector connector) {
		this.connector = connector;
	}
	public void process(Socket socket){
		//4.传递HttpRequest和HttpResponse对象给ServletProcessor或StaticResourceProcessor
		SocketInputStream input = null;
		OutputStream output = null;
		try {
			input = new SocketInputStream(socket.getInputStream(),2048);
			output = socket.getOutputStream();
			//创建一个HttpRequest
			request = new HttpRequest(input);
			//创建一个HttpResponse
			response = new HttpResponse(output);
			response.setRequest(request);
			response.setHeader("Server", "Pyrmont Servlet Container");
			//解析HTTP请求的第一行和headers,并填充HttpRequest对象
			parseRequest(input, output);
			parseHeasers(input);
			if(request.getUri().startsWith("/servlet/")){
				ServletProcessor processor = new ServletProcessor();
				processor.process(request, response);
			}else{
				StaticResourceProcessor processor = new StaticResourceProcessor();
				processor.process(request, response);
			}
			socket.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
	/**
	 * 解析请求行和头部
	 * @throws ServletException 
	 * @throws EOFException 
	 */
	public void parseRequest(SocketInputStream input, OutputStream output) throws ServletException, EOFException{
		input.readRequestLine(requestLine);
		String method = new String(requestLine.method,0,requestLine.methodEnd);
		String uri = null;
		String protocol = new String(requestLine.protocol, 0, requestLine.protocolEnd);
		if(method.length() < 1){
			throw new ServletException("Missing HTTP request method");
		}else if(requestLine.uriEnd < 1){
			throw new ServletException("Missing HTTP request URI");
		}
		
		int question = requestLine.indexOf("?");
		if(question >= 0){
			request.setQueryString(new String(requestLine.uri,
					question+1, requestLine.uriEnd - question -1));
			uri = new String(requestLine.uri, 0, question);
		}else{
			request.setQueryString(null);
			uri = new String(requestLine.uri, 0, requestLine.uriEnd);
		}
		if(!uri.startsWith("/")){
			int pos = uri.indexOf("://");
			if(pos != -1){
				pos = uri.indexOf('/', pos+3);
				if(pos == -1){
					uri = "";
				}else{
					uri = uri.substring(pos);
				}
			}
		}
		String match = ";jsessionid=";
		int semicolon = uri.indexOf(match);
		if(semicolon >= 0){
			String rest = uri.substring(semicolon + match.length());
			int semicolon2 = rest.indexOf(';');
			if(semicolon2 >= 0){
				request.setRequestSessionId(rest.substring(0, semicolon2));
				rest = rest.substring(semicolon2);
			}else{
				request.setRequestSessionId(rest);
				rest = "";
			}
			request.setRequestSessionURL(true);
			uri = uri.substring(0, semicolon) + rest;
		}else{
			request.setRequestSessionId(null);
			request.setRequestSessionURL(false);
		}
		String normalizedUri = normalize(uri);
		request.setMethod(method);
		request.setProtocol(protocol);
		if(normalizedUri != null){
			request.setRequestURI(normalizedUri);
		}else{
			request.setRequestURI(uri);
		}
		if(normalizedUri == null){
			throw new ServletException("Invalid URI:"+uri);
		}
	}
	public void parseHeasers(SocketInputStream input) throws ServletException{
		while(true){
			HttpHeader header = new HttpHeader();
			input.readHeader(header);
			if(header.nameEnd == 0){
				if(header.valueEnd == 0){
					return ;
				}
			}else{
				throw new ServletException("httpProcessor.parseHeaders.colon");
			}
			String name = new String(header.name, 0, header.nameEnd);
			String value = new String(header.value, 0, header.valueEnd);
			request.addHeader(name, value);
			
			if(name.equals("cookie")){
				Cookie cookies[] = RequestUtil.parseCookieHeader(value);
				for (int i = 0; i < cookies.length; i++) {
					if(cookies[i].getName().equals("jsessionid")){
						if(!request.isRequestedSessionIdFromCookie()){
							request.setRequestSessionId(cookies[i].getValue());
							request.setRequestSessionCookie(true);
							request.setRequestSessionURL(false);
						}
					}
					request.addCookie(cookies[i]);
				}
			}else if(name.equals("content-length")){
				int n = -1;
				n = Integer.parseInt(value);
				request.setContentLength(n);
			}else if(name.equals("content-type")){
				request.setContentType(value);
			}
		}
	}
	
	/**
	 * 返回一个以"/"开头的相对上下文的路径。
	 * @param path
	 * @return
	 */
	protected String normalize(String path){
		if(path == null){
			return null;
		}
		String normalized = path;
		if(normalized.startsWith("/%7E") || normalized.startsWith("/%7e")){
			normalized = "/~" + normalized.substring(4);
		}
		// Prevent encoding '%', '/', '.' and '\', which are special reserved
	    // characters
	    if ((normalized.indexOf("%25") >= 0)
	      || (normalized.indexOf("%2F") >= 0)
	      || (normalized.indexOf("%2E") >= 0)
	      || (normalized.indexOf("%5C") >= 0)
	      || (normalized.indexOf("%2f") >= 0)
	      || (normalized.indexOf("%2e") >= 0)
	      || (normalized.indexOf("%5c") >= 0)) {
	      return null;
	    }
	    if(normalized.equals("/.")){
	    	return "/";
	    }
	    if(normalized.indexOf('\\') >= 0){
	    	normalized = normalized.replace('\\', '/');
	    }
	    if(!normalized.startsWith("/")){
	    	normalized = "/" + normalized;
	    }
	    
	    while(true){
	    	int index = normalized.indexOf("//");
	    	if(index < 0){
	    		break;
	    	}
	    	normalized = normalized.substring(0, index) +
	    		normalized.substring(index + 1);
	    }
	    while(true){
	    	int index = normalized.indexOf("/./");
	    	if(index < 0){
	    		break;
	    	}
	    	normalized = normalized.substring(0, index) +
	    	 normalized.substring(index + 2);
	    }
	    while(true){
	    	int index = normalized.indexOf("/../");
	    	if(index < 0){
	    		break;
	    	}
	    	if(index == 0){
	    		return null;
	    	}
	    	int index2 = normalized.lastIndexOf('/',index-1);
	    	normalized = normalized.substring(0, index2) +
	    	 normalized.substring(index + 3);
	    }
	    if(normalized.indexOf("/...") >= 0){
	    	return null;
	    }
		return normalized;
	}
	public static void main(String[] args) {
		System.out.println((char)(0x7E));
		System.out.println((char)(0x25));
		System.out.println((char)(0x2F));
		System.out.println((char)(0x2E));
		System.out.println((char)(0x5C));
		System.out.println((char)(0x2f));
		System.out.println((char)(0x2e));
		System.out.println((char)(0x5c));
	}
}
