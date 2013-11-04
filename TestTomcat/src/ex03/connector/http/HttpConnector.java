package ex03.connector.http;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 等待HTTP请求
 * 创建一个服务器套接字，用来等待前来的HTTP请求。
 * @author 张志永
 *
 */
public class HttpConnector implements Runnable{
	
	boolean stopped;
	private String scheme = "http";
	
	public String getScheme() {
		return scheme;
	}
	
	public void run() {
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(8080,1,InetAddress.getByName("127.0.0.1"));
		} catch (Exception e) {
			System.out.println(e.toString());
			System.exit(1);
		}
		while(!stopped){
			Socket socket = null;
			try {
				//等待HTTP请求
				socket = serverSocket.accept();
			} catch (IOException e) {
				System.out.println(e.toString());
				continue;
			}
			//为每个请求创建HttpProcessor
			HttpProcessor processor = new HttpProcessor(this);
			//调用HttpProcessor的process方法。
			processor.process(socket);
		}
	}
	
	public void start(){
		Thread thread = new Thread(this);
		thread.start();
	}
	
}
