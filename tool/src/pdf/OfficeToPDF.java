package pdf;

import java.io.File;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;
import com.eplugger.core.exception.BizException;


public class OfficeToPDF {
	/**
	 * 将Office文档转换为PDF. 运行该函数需要用到OpenOffice, OpenOffice下载地址为
	 * http://www.openoffice.org/
	 * 
	 * <pre>
	 * 方法示例:
	 * String sourcePath = &quot;F:\\office\\source.doc&quot;;
	 * String destFile = &quot;F:\\pdf\\dest.pdf&quot;;
	 * Converter.office2PDF(sourcePath, destFile);
	 * </pre>
	 * 
	 * @param sourceFile
	 *            源文件, 绝对路径. 可以是Office2003-2007全部格式的文档, Office2010的没测试. 包括.doc,
	 *            .docx, .xls, .xlsx, .ppt, .pptx等. 示例: F:\\office\\source.doc
	 * @param destFile
	 *            目标文件. 绝对路径. 示例: F:\\pdf\\dest.pdf
	 * @return 操作成功与否的提示信息. 如果返回 -1, 表示找不到源文件, 或url.properties配置错误; 如果返回 0,
	 *         则表示操作成功; 返回1, 则表示转换失败
	 */
	public static int convert(String sourceFile, String destFile) {
		OpenOfficeConnection connection = null;
		Process pro = null;
		try {
			File inputFile = new File(sourceFile);
			if (!inputFile.exists()) {
				return -1;// 找不到源文件, 则返回-1
			}

			// 如果目标路径不存在, 则新建该路径
			File outputFile = new File(destFile);
			if (!outputFile.getParentFile().exists()) {
				outputFile.getParentFile().mkdirs();
			}

			String OpenOffice_HOME = "C:\\Program Files\\OpenOffice 4";// 这里是OpenOffice的安装目录,
			// 在我的项目中,为了便于拓展接口,没有直接写成这个样子,但是这样是绝对没问题的
			// 如果从文件中读取的URL地址最后一个字符不是 '\'，则添加'\'
			if (OpenOffice_HOME.charAt(OpenOffice_HOME.length() - 1) != '\\') {
				OpenOffice_HOME += "\\";
			}
			// 启动OpenOffice的服务
			String command = OpenOffice_HOME
					+ "program\\soffice.exe -headless -accept=\"socket,host=127.0.0.1,port=8100;urp;\"";
			pro = Runtime.getRuntime().exec(command);
			// connect to an OpenOffice.org instance running on port 8100
			connection = new SocketOpenOfficeConnection("127.0.0.1", 8100);
			connection.connect();

			// convert
			DocumentConverter converter = new OpenOfficeDocumentConverter(
					connection);
			converter.convert(inputFile, outputFile);
			return 0;
		}catch (Exception e) {
			throw new BizException("系统出现内部异常",e.getMessage());
		}finally{
			try {
				if(connection != null){// close the connection
					connection.disconnect();
				}
			} catch (Exception e) {
				
			}finally{
				if(pro != null){// 关闭OpenOffice服务的进程
					pro.destroy();
				}
			}
		}
	}

	public static void main(String[] args) {
		OfficeToPDF c = new OfficeToPDF();
		c.convert("d:\\55.doc", "d:\\5.pdf");
		// System.out.println(c.get);
	}
}