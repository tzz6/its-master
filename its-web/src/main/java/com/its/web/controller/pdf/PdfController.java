package com.its.web.controller.pdf;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.its.common.utils.DateUtil;
import com.its.common.utils.pdf.PdfBoxUtils;
import com.its.common.utils.pdf.PdfUtil;
import com.its.web.common.freemarker.HtmlGenerator;
import com.its.web.common.report.jasperreports.JasperreportsService;
import com.its.web.controller.login.BaseController;
import com.its.web.util.FileUtil;

@Controller
@RequestMapping("/pdf")
public class PdfController extends BaseController {
	
	@Autowired
	private JasperreportsService jasperreportsService;

	/** 首页 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index() {
		return "/pdf/pdfManager";
	}

	/** 使用IText生成PDF */
	@RequestMapping(value = "/generatePDF", method = RequestMethod.GET)
	public void saveFileSM(HttpServletRequest request, HttpServletResponse response) {
		String savePath = FileUtil.getPath(request, FileUtil.PDF_DIR);
		// 得到文件保存的名称
		String filename = FileUtil.makeFileName("pdf");
		// 得到文件的保存目录
		String realSavePath = FileUtil.makePath(filename, savePath);
		savePath = PdfUtil.generatePDF(realSavePath);
		FileUtil.downloadFile(response, filename, savePath);
	}

	/** 使用Flying-saucer将HTML页面转为PDF */
	@RequestMapping(value = "/htmlToPDF", method = RequestMethod.GET)
	public void htmlToPDF(HttpServletRequest request, HttpServletResponse response) {
		String savePath = FileUtil.getPath(request,  FileUtil.PDF_DIR);
		// 得到文件保存的名称
		String filename = FileUtil.makeFileName("pdf");
		// 得到文件的保存目录
		String realSavePath = FileUtil.makePath(filename, savePath);
		
		String htmlPath = FileUtil.getPath(request, "/WEB-INF/views/freemarker");
		htmlPath = htmlPath + File.separator + "pdf.html";

//		ServletContext servletContext = request.getSession().getServletContext();
//		String path = servletContext.getRealPath("");
//		String imagePath ="file:/" + path + "/image/pdf";
		String imagePath = "file:///" + FileUtil.getPath(request, "/image/pdf") + File.separator;
		imagePath = imagePath.replaceAll("\\\\", "/");
		htmlPath = htmlPath.replaceAll("\\\\", "/");
		
		savePath = PdfUtil.htmlToPDF(htmlPath, imagePath, realSavePath);
		FileUtil.downloadFile(response, filename, savePath);
	}
	
	
	/** 使用(Flying-saucer + Freemarker )将HTML转为PDF */
	@RequestMapping(value = "/freemarker/htmlToPDF", method = RequestMethod.GET)
	public void freemarkerHtmlToPDF(HttpServletRequest request, HttpServletResponse response) {
		String savePath = FileUtil.getPath(request,  FileUtil.PDF_DIR);
		// 得到文件保存的名称
		String filename = FileUtil.makeFileName("pdf");
		// 得到文件的保存目录
		String realSavePath = FileUtil.makePath(filename, savePath);
		
		//模板目录
		String path = FileUtil.getPath(request, "/WEB-INF/views/freemarker") + File.separator;
		//模板名
		String templateName = "pdf.html";
		//构造数据
		Map<String, Object> variables = buildData();
		//生成Html字符串
		String htmlStr = HtmlGenerator.generate(path, templateName, variables);
		String imagePath = "file:///" + FileUtil.getPath(request, "/image/pdf")
				+ File.separator;
		imagePath = imagePath.replaceAll("\\\\", "/");
		//HTML转PDF
		savePath = PdfUtil.htmlStrToPDF(htmlStr, imagePath, realSavePath);
		FileUtil.downloadFile(response, filename, savePath);
	}
	
	
	/** 使用PDFBox将Pdf转为Image 
	 * @throws IOException 
	 * @throws MalformedURLException */
	@RequestMapping(value = "/freemarker/pdfToImage", method = RequestMethod.GET)
	public void pdfToImage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String savePath = FileUtil.getPath(request, FileUtil.PDF_DIR);
		// 得到文件保存的名称
		String filename = FileUtil.makeFileName("pdf");
		// 得到文件的保存目录
		String realSavePath = FileUtil.makePath(filename, savePath);
		//生成PDF
		savePath = PdfUtil.generatePDF(realSavePath);
		
		//将PDF转Image
		String imageFormat = "jpg";
		String saveImageDir = FileUtil.getPath(request, FileUtil.IMAGE_DIR);
		//将Pdf转为Image
		List<byte []> byteList = PdfBoxUtils.convertPdfToImage(savePath, imageFormat);
		for (byte[] bytes : byteList) {
			String imageFilename = DateUtil.getDateyyyyMMddHHmmss() + "." + imageFormat;
			// 得到文件保存的名称
			String imageSaveFilename = FileUtil.makeFileName(imageFilename);
			// 得到文件的保存目录
			String imageRealSavePath = FileUtil.makePath(imageSaveFilename, saveImageDir) + File.separator;
			//将Image保存到磁盘
			String imageDir = FileUtil.saveFile(imageRealSavePath, imageFormat, bytes);
			FileUtil.downloadFile(response, imageFilename, imageDir);
		}
	}
	
	
	/**
	 * Jasperreports+ireport生成PDF
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/Jasperreports/PDF", method = RequestMethod.GET)
	public void JasperreportsPDF(HttpServletRequest request, HttpServletResponse response)  throws Exception {
		String savePath = FileUtil.getPath(request,  FileUtil.PDF_DIR);
		// 得到文件保存的名称
		String filename = FileUtil.makeFileName("pdf");
		// 得到文件的保存目录
		String realSavePath = FileUtil.makePath(filename, savePath);

		List<Map<String, Object>> invoiceData = getInvoicePrintDataList();
		savePath = jasperreportsService.createPdf(invoiceData, realSavePath, filename);
		FileUtil.downloadFile(response, filename, savePath);
	}

	/**构造数据*/
	private Map<String, Object> buildData() {
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("username", "T001");
		variables.put("testNo", "TESTNO001");
		variables.put("password", "123456");
		variables.put("address", "深圳市南山区");
		variables.put("code", "518000");
		variables.put("name", "T");
		variables.put("insuredPingyinName", "TPinyin");
		variables.put("insuredPassportNo", "6985693256");
		variables.put("insuredSex", "MAN");
		variables.put("insuredBirthday", "1988-12-10");
		variables.put("insuredPhone", "15825825825");
		variables.put("insuredIDNo", "64654512864613");
		variables.put("remarks", "备注");
		variables.put("registerDate", "2016-01-06");
		variables.put("issueDate", "2016-01-06");
		return variables;
	}
	
	
	public List<Map<String, Object>> getInvoicePrintDataList() {
		List<Map<String, Object>> printList = new ArrayList<Map<String, Object>>();

		Map<String, Object> headMap = new HashMap<String, Object>(10);
		headMap.put("hawb", "781123123");
		headMap.put("mergerNum", "361546431");

		List<Map<String, Object>> contentList = new ArrayList<Map<String, Object>>();
		headMap.put("company", "S.F. EXPRESS CO.，LTD");
		headMap.put("address",
				"JINDITONG SF LOGISTICS PARK . BAIHUADONG 1 ST INDUSTRY PARK,  2 TH INDUSTRY STR, GUANGMING NEW DISTRICT SHENZHEN");
		headMap.put("postalcode", "518100");
		headMap.put("contactName", "SF Buy SHENZHEN WHS");
		headMap.put("telNo", "0755-8996010");

		headMap.put("toCompany", "收件公司");
		headMap.put("toAddress", "深圳南山区");
		headMap.put("toPostalcode", "邮编");
		headMap.put("toContactName", "tzz");
		headMap.put("toTelNo", "15825836966");
		headMap.put("imageurl", "report/image/any.png");

		for (int i = 0; i < 50; i++) {
			Map<String, Object> contentMap = new HashMap<String, Object>();
			contentMap.put("name", "商品名" + i);
			contentMap.put("quantity", "数量" + i);
			contentMap.put("price", 1.0);
			contentMap.put("country", "城市" + i);
			contentList.add(contentMap);
		}

		// 模板Table数据
		headMap.put("dataSource", contentList);
		// 打印模板
		headMap.put("printCode", "invoice");
		printList.add(headMap);

		return printList;
	}
}
