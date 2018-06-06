package com.its.test.util.xml;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.its.common.utils.xml.JaxbXmlUtil;
import com.its.test.util.xml.entity.Country;
import com.its.test.util.xml.entity.Province;

public class JaxbXmlUtilTest {

	@Test
	public void testBeanToXml() {
		Country country = new Country();
		country.setId(1);
		country.setCreateDate(new Date());
		country.setName("中国");
		
		List<Province> list = new ArrayList<Province>();
		Province province = new Province();
		province.setName("广东省");
		province.setProvCity("广州市");
		Province province2 = new Province();
		province2.setName("湖南省");
		province2.setProvCity("长沙市");
		list.add(province);
		list.add(province2);
		country.setProvinceList(list);

		String xml = JaxbXmlUtil.beanToXml(country);
		System.out.println(xml);
	}

	@Test
	public void testXmlToBean() {
		String xml = "<?xml version='1.0' encoding='utf-8' ?>" 
				+ "<country>" 
				+ "<id>1</id>"
				+ "<createDate>2016-03-10 09:36:01</createDate>" 
				+ "<name>中国</name>" 
				+ "<provinces>" 
				+ "<province><name>广东省</name><provCity>广州市</provCity></province>" 
				+ "<province><name>湖南省</name><provCity>长沙市</provCity></province>" 
				+ "</provinces>" 
				+ "</country>";
		Country country = JaxbXmlUtil.xmlToBean(xml, Country.class);
		System.out.println(country.getId() + "--" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.format(country.getCreateDate()) + "--" + country.getName());
		for (Province p : country.getProvinceList()) {
			System.out.println(p.getName() + "---" + p.getProvCity());
		}
	}
}
