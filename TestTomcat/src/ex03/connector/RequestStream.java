package ex03.connector;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletInputStream;

import org.apache.catalina.util.StringManager;

public class RequestStream extends ServletInputStream{

	protected boolean closed;
	protected int count;
	protected int length;
	protected StringManager sm;
	protected InputStream stream;
	
	@Override
	public int read() throws IOException {
		return 0;
	}

}
