package ex03.connector.http;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.catalina.util.StringManager;

public class SocketInputStream extends InputStream {
	/** 回车*/
	private static final byte CR = '\r';
	/** 换行 */
	private static final byte LF = '\n';// 
	/** 空格 */
	private static final byte SP = ' ';
	/** 水平制表符 */
	private static final byte HT = '\t';//
	/** 冒号 */
	private static final byte COLON = ':';
	private static final int LC_OFFSET = 'A' - 'a';

	protected byte[] buf;
	protected int count;
	protected int pos;
	protected InputStream is;
	protected StringManager sm;

	public SocketInputStream(InputStream is, int bufferSize) {
		this.is = is;
		buf = new byte[bufferSize];
	}

	public void readRequestLine(HttpRequestLine requestLine) throws EOFException {
		if (requestLine.methodEnd != 0) {
			requestLine.recycle();
		}
		int chr = 0;
		do{
			try {
				chr = read();
			} catch (IOException e) {
				chr = -1;
			}
		}while((chr == CR) || (chr == LF));
		
		if(chr == -1){
			throw new EOFException("requestStream.readline.error");
		}
		pos--;
		
		int maxRead = requestLine.method.length;
		int readStart = pos;
		int readCount = 0;
		boolean space = false;
		
		while(!space){
			if(readCount <= maxRead){
				
			}
			
			if(pos <= count){
				
			}
		}
	}

	public void readHeader(HttpHeader header) {
		
	}
	//TODO 看不懂
	@Override
	public int read() throws IOException {
		if(pos >= count){
			fill();
			if(pos >= count){
				return -1;
			}
		}
		return buf[pos++] & 0xff;
	}

	public int available() {
		return 1;
	}

	public void close() {

	}

	/**
	 * 使用基本的输入流中的数据填充内部的缓存
	 * 
	 * @throws IOException
	 */
	protected void fill() throws IOException {
		pos = 0;
		count = 0;
		int nRead = is.read(buf, 0, buf.length);
		if (nRead > 0) {
			count = nRead;
		}
	}
}
