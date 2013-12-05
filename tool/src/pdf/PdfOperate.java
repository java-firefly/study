package pdf;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.eplugger.core.exception.BizException;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

public class PdfOperate {
	/**
	 * 
	 * @param filepath
	 * @param basePath
	 * @return 负责人所在页面的页号
	 */
	public static int partitionSKPdf(String filepath, String basePath){
		Document document = null;
		PdfCopy copy = null;
		List<String> savePaths = new ArrayList<String>(); 
		savePaths.add(basePath+"_02.pdf");
		savePaths.add(basePath+"_04.pdf");
		try {
			PdfReader reader = new PdfReader(filepath);
			int n = reader.getNumberOfPages();
			
			//第二部分 包含第3页和第4页
			document = new Document(reader.getPageSize(1));
			copy = new PdfCopy(document, new FileOutputStream(savePaths.get(0)));
			document.open();
			for (int i = 3; i < 5; i++) {
				document.newPage();
				PdfImportedPage page = copy.getImportedPage(reader, i);
				copy.addPage(page);
			}
			document.close();
			
			//第四部分 包含第6页之后的所有页
			document = new Document(reader.getPageSize(1));
			copy = new PdfCopy(document, new FileOutputStream(savePaths.get(1)));
			document.open();
			for (int i = 6; i <= n; i++) {
				document.newPage();
				PdfImportedPage page = copy.getImportedPage(reader, i);
				copy.addPage(page);
			}
			document.close();
			return 5;
		} catch (Exception e) {
			throw new BizException("","系统出现内部错误，请联系管理员");
		} 
	}
	/**
	 * 
	 * @param filepath
	 * @param basePath
	 * @return 负责人所在页面的页号
	 */
	public static int partitionKJPdf(String filepath,String basePath){
		Document document = null;
		PdfCopy copy = null;
		List<String> savePaths = new ArrayList<String>(); 
		savePaths.add(basePath+"_02.pdf");
		savePaths.add(basePath+"_04.pdf");
		try {
			PdfReader reader = new PdfReader(filepath);
			int n = reader.getNumberOfPages();
			
			int endPageOfFirstPdf = 4;
			String str = "";
			do{
				endPageOfFirstPdf ++;
				str = PdfTextExtractor.getTextFromPage(reader, endPageOfFirstPdf);
			}while(!str.startsWith("填表说明"));
			
			//第二部分 包含第3页和第4页
			document = new Document(reader.getPageSize(1));
			copy = new PdfCopy(document, new FileOutputStream(savePaths.get(0)));
			document.open();
			for (int i = 3; i <= endPageOfFirstPdf; i++) {
				document.newPage();
				PdfImportedPage page = copy.getImportedPage(reader, i);
				copy.addPage(page);
			}
			document.close();
			
			//第四部分 包含第6页之后的所有页
			document = new Document(reader.getPageSize(1));
			copy = new PdfCopy(document, new FileOutputStream(savePaths.get(1)));
			document.open();
			for (int i = endPageOfFirstPdf+2; i <= n; i++) {
				document.newPage();
				PdfImportedPage page = copy.getImportedPage(reader, i);
				copy.addPage(page);
			}
			document.close();
			return endPageOfFirstPdf + 1;
		} catch (Exception e) {
			throw new BizException("","系统出现内部错误，请联系管理员");
		} 
	}
	
	public static void mergePdfFiles(String[] files, String savepath) {
		try {
			Document document = new Document(new PdfReader(files[0]).getPageSize(1));
			PdfCopy copy = new PdfCopy(document, new FileOutputStream(savepath));
			document.open();

			for (int i = 0; i < files.length; i++) {
				PdfReader reader = new PdfReader(files[i]);
				int n = reader.getNumberOfPages();
				// if(i==0){
				for (int j = 1; j <= n; j++) {
					document.newPage();
					PdfImportedPage page = copy.getImportedPage(reader, j);
					copy.addPage(page);
				}
			}
			document.close();

		} catch (Exception e) {
			throw new BizException("","系统出现内部错误，请联系管理员");
		}
	}
	
	/*public static void partitionPdfFile(String filepath) {
	Document document = null;
	PdfCopy copy = null;
	try {
		PdfReader reader = new PdfReader(filepath);
		int n = reader.getNumberOfPages();
		if (n < N) {
			System.out.println("The document does not have " + N + " pages to partition !");
			return;
		}

		int size = n / N;
		String staticpath = filepath.substring(0, filepath.lastIndexOf("\\") + 1);
		String savepath = null;
		ArrayList<String> savepaths = new ArrayList<String>();
		for (int i = 1; i <= N; i++) {
			if (i < 10) {
				savepath = filepath.substring(filepath.lastIndexOf("\\") + 1,filepath.length() - 4);
				savepath = staticpath + savepath + "0" + i + ".pdf";
				savepaths.add(savepath);
			} else {
				savepath = filepath.substring(filepath.lastIndexOf("\\") + 1,filepath.length() - 4);
				savepath = staticpath + savepath + i + ".pdf";
				savepaths.add(savepath);
			}
		}

		for (int i = 0; i < N - 1; i++) {
			document = new Document(reader.getPageSize(1));
			copy = new PdfCopy(document, new FileOutputStream(savepaths.get(i)));
			document.open();
			for (int j = size * i + 1; j <= size * (i + 1); j++) {
				document.newPage();
				PdfImportedPage page = copy.getImportedPage(reader, j);
				copy.addPage(page);
			}
			document.close();
		}

		document = new Document(reader.getPageSize(1));
		copy = new PdfCopy(document, new FileOutputStream(savepaths.get(N - 1)));
		document.open();
		for (int j = size * (N - 1) + 1; j <= n; j++) {
			document.newPage();
			PdfImportedPage page = copy.getImportedPage(reader, j);
			copy.addPage(page);
		}
		document.close();

	} catch (IOException e) {
		e.printStackTrace();
	} catch (DocumentException e) {
		e.printStackTrace();
	}
}*/
}
