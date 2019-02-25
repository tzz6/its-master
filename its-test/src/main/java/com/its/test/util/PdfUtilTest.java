package com.its.test.util;

import org.junit.Test;

import com.its.common.utils.pdf.PdfUtil;

/**
 * 
 * @author tzz
 */
public class PdfUtilTest {

	/**htmlPath*/
	@Test
	public void testGeneratePDF() {
		String savePath = PdfUtil.generatePDF("D:/pdf");
		System.out.println(savePath);
	}

	/**HTML转PDF*/
	@Test
	public void testHtmlToPdf() {
		String htmlPath = "E:/eclipse/eclipse/workspace/web-demo/src/main/webapp/WEB-INF/pdf/pdf.html";
		String imagePath = "file:///E:/eclipse/eclipse/workspace/web-demo/src/main/webapp/image/pdf/";
		String path = "D:/pdf/html/";
		PdfUtil.htmlToPDF(htmlPath, imagePath, path);
	}

}
