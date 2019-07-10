package com.its.test.util.xml;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.its.common.utils.xml.XmlUtil;

/**
 * 
 * @author tzz
 */
public class XmlUtilTest {

	@Test
	public void testXmlToMap() {
		String xml = "<?xml version='1.0' encoding='utf-8' ?>" + "<country>" + "<id>1</id>"
				+ "<createDate>2016-03-10 09:36:01</createDate>" + "<name>中国</name>" + "<provinces>"
				+ "<province><name>广东省</name><provCity>广州市</provCity><citys><city><name>白云区</name></city><city><name>xx区</name></city></citys></province>"
				+ "<province><name>湖南省</name><provCity>长沙市</provCity><citys><city><name>xx区1</name></city><city><name>xx区2</name></city></citys></province>"
				+ "</provinces>" + "</country>";
		Map<String, Object> map = XmlUtil.xmlToMap(xml);
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			System.out.println(entry.getKey() + "---" + entry.getValue());
		}
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testXmlToMapMax() {
		String xml = "<soapenv:Envelope xmlns:soapenv='http://schemas.xmlsoap.org/soap/envelope/'>"
				+ "<soapenv:Header/>" 
					+ "<soapenv:Body>"
					+ "<xav:XAVResponse xmlns:xav='http://www.ups.com/XMLSchema/XOLTWS/xav/v1.0'>"
						+ "<common:Response xmlns:common='http://www.ups.com/XMLSchema/XOLTWS/Common/v1.0'>"
							+ "<common:ResponseStatus>" + "<common:Code>1</common:Code>"
							+ "<common:Description>Success</common:Description>" 
						+ "</common:ResponseStatus>"
						+ "<common:TransactionReference>"
							+ "<common:CustomerContext>Your Customer Context</common:CustomerContext>"
						+ "</common:TransactionReference>" + "</common:Response>" + "<xav:AmbiguousAddressIndicator/>"
						+ "<xav:AddressClassification>" 
							+ "<xav:Code>0</xav:Code>"
							+ "<xav:Description>Unknown</xav:Description>" 
						+ "</xav:AddressClassification>" 
						+ "<xav:Candidate>"
							+ "<xav:AddressClassification>" + "<xav:Code>0</xav:Code>"
							+ "<xav:Description>Unknown</xav:Description>" + "</xav:AddressClassification>"
							+ "<xav:AddressKeyFormat>" + "<xav:AddressLine>3500-3598 MORRIS AVE</xav:AddressLine>"
							+ "<xav:PoliticalDivision2>MACON</xav:PoliticalDivision2>"
							+ "<xav:PoliticalDivision1>GA</xav:PoliticalDivision1>"
							+ "<xav:PostcodePrimaryLow>31204</xav:PostcodePrimaryLow>"
							+ "<xav:PostcodeExtendedLow>3226</xav:PostcodeExtendedLow>"
							+ "<xav:Region>MACON GA 31204-3226</xav:Region>" + "<xav:CountryCode>US</xav:CountryCode>"
							+ "</xav:AddressKeyFormat>" 
						+ "</xav:Candidate>" 
						+ "<xav:Candidate>" 
							+ "<xav:AddressClassification>"
								+ "<xav:Code>0</xav:Code>" + "<xav:Description>Unknown</xav:Description>"
							+ "</xav:AddressClassification>" 
							+ "<xav:AddressKeyFormat>"
								+ "<xav:AddressLine>3501-3599 MORRIS AVE</xav:AddressLine>"
								+ "<xav:PoliticalDivision2>MACON</xav:PoliticalDivision2>"
								+ "<xav:PoliticalDivision1>GA</xav:PoliticalDivision1>"
								+ "<xav:PostcodePrimaryLow>31204</xav:PostcodePrimaryLow>"
								+ "<xav:PostcodeExtendedLow>3225</xav:PostcodeExtendedLow>"
								+ "<xav:Region>MACON GA 31204-3225</xav:Region>" + "<xav:CountryCode>US</xav:CountryCode>"
							+ "</xav:AddressKeyFormat>" 
						+ "</xav:Candidate>" 
					+ "</xav:XAVResponse>" 
					+ "</soapenv:Body>"
				+ "</soapenv:Envelope>";
		Map<String, Object> map = XmlUtil.xmlToMap(xml);
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			System.out.println(entry.getKey() + "---" + entry.getValue());
			if (entry.getValue() instanceof HashMap) {
				HashMap<String, Object> m = (HashMap<String, Object>) entry.getValue();
				for (Map.Entry<String, Object> me : m.entrySet()) {
					System.out.println(me.getKey() + "---" + me.getValue());
				}
			}
		}

		System.out.println(XmlUtil.getMapValue(map, "CustomerContext"));
	}
	
}
