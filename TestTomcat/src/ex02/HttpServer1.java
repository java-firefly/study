package ex02;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer1 {
	private static final String SHUTDOWN_COMMAND = "/SHUTDOWN";
	private boolean shutdown = false;
	public static void main(String[] args) {
		HttpServer1 httpServer1 = new HttpServer1();
		httpServer1.await();
	}
	public void await(){
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(
					8080,1,InetAddress.getByName("127.0.0.1"));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		while(!shutdown){
			Socket socket = null;
			InputStream is = null;
			OutputStream os = null;
			try {
				socket = serverSocket.accept();
				is = socket.getInputStream();
				os = socket.getOutputStream();
				Request request = new Request(is);
				request.parse();
				
				Response response = new Response(os);
				response.setRequest(request);
				
				if(request.getUri().startsWith("/servlet/")){
					ServletProcessor1 servletProcessor = new ServletProcessor1();
					servletProcessor.process(request, response);
				}else{
					StaticResourceProcessor staticResourceProcessor = 
						new StaticResourceProcessor();
					staticResourceProcessor.process(request, response);
				}
				socket.close();
				shutdown = request.getUri().equals(SHUTDOWN_COMMAND);
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
	}
}
