package com.its.test.report.jasperreports;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.its.web.common.report.jasperreports.JasperreportsService;
import com.its.web.common.report.jasperreports.impl.JasperreportsServiceImpl;


public class TestJasperreportsService {

   public static void main(String [] args){
		try {
			JasperreportsService hessianTagService = new JasperreportsServiceImpl();
			List<Map<String, Object>> invoiceData = getInvoicePrintDataList();
			String fileName = UUID.randomUUID().toString().replaceAll("-", "")
					+ new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
			String printUrl = hessianTagService.createPdf(invoiceData, "D:\\Jasperreports\\pdf\\", fileName);
			System.out.println(printUrl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static List<Map<String, Object>> getInvoicePrintDataList() {
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
