package ex01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class TestSocket {
	public static void main(String[] args) {
		test1();
	}
	public static void test1(){
		try {
			Socket socket = new Socket("127.0.0.1",8080);
			InputStream is = socket.getInputStream();
			OutputStream os = socket.getOutputStream();
			PrintWriter pw = new PrintWriter(os,true);
			BufferedReader br = new BufferedReader(
					new InputStreamReader(is));
			pw.println("GET /servlet/ModernServlet HTTP/1.1");
			pw.println("Host: 127.0.0.1");
			pw.println("Connection: Close");
			pw.println();
			pw.flush();
			boolean loop = true;
			
			StringBuffer sb = new StringBuffer(8096);
			while(loop){
				if(br.ready()){
					int i = 0;
					while(i != -1){
						i = br.read();
						sb.append((char)i);
					}
					loop = false;
				}
				Thread.sleep(50);
			}
			System.out.println(sb.toString());
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
}
