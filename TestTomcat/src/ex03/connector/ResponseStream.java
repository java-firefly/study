package ex03.connector;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletOutputStream;

import ex03.connector.http.HttpResponse;


public class ResponseStream extends ServletOutputStream{

	protected boolean closed;
	protected boolean commit;
	protected int count;
	protected int length;
	protected HttpResponse response;
	protected OutputStream stream;
	
	@Override
	public void write(int b) throws IOException {
	}
	

}
