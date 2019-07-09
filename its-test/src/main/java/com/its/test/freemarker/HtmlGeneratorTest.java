package com.its.test.freemarker;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.its.web.common.freemarker.HtmlGenerator;

/**
 * 
 * @author tzz
 */
public class HtmlGeneratorTest {
	
	@Test
	public void testGenerate(){
		String path = "D:\\Eclipse\\V4.10\\eclipse\\workspace\\its-master\\its-web\\src\\main\\webapp\\WEB-INF\\views\\freemarker";
		String templateName = "pdf.html";
		Map<String, Object> variables = new HashMap<String, Object>(128);
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
		String str = HtmlGenerator.generate(path, templateName, variables);
		System.out.println(str);
	}

}
