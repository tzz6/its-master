package com.its.test.util.xml;

import java.util.Map;

import org.dom4j.DocumentException;
import org.junit.Test;

import com.its.common.utils.xml.XmlUtil;


public class TestXmlUtil {
	
	@Test
	public void testXmlToMap() {
		String xml = "<?xml version='1.0' encoding='utf-8' ?>" 
				+ "<country>" 
				+ "<id>1</id>"
				+ "<createDate>2016-03-10 09:36:01</createDate>" 
				+ "<name>中国</name>" 
				+ "<provinces>" 
				+ "<province><name>广东省</name><provCity>广州市</provCity><citys><city><name>白云区</name></city><city><name>xx区</name></city></citys></province>" 
				+ "<province><name>湖南省</name><provCity>长沙市</provCity><citys><city><name>xx区1</name></city><city><name>xx区2</name></city></citys></province>" 
				+ "</provinces>" 
				+ "</country>";
		try {
			Map<String, Object> map = XmlUtil.xmlToMap(xml);
			for (Map.Entry<String, Object> entry : map.entrySet()){
				System.out.println(entry.getKey() + "---" + entry.getValue());
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
	
}
