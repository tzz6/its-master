package com.its.test.util;

import org.junit.Test;

import com.its.common.utils.HttpUtil;

/**
 * 
 * @author tzz
 */
public class HttpUtilTest {


	@Test
	public void testSendHttpPost6() {
		
		String content = "<logisticsEventsRequest>"+
	    "<logisticsEvent>"+
	    "<eventHeader>"+
	      "<eventType>TRADE_WAREHOUSE_ENTRY</eventType>"+
	      "<eventTime>订单入库时间</eventTime>"+
	      "<eventSource>集运商代号</eventSource>"+
	      "<eventTarget>TAOBAO</eventTarget>"+
	    "</eventHeader>"+
	    "<eventBody>"+  
	      "<tradeDetail>"+
	        "<tradeOrders>"+  
	          "<tradeOrder>"+
	            "<tradeOrderId>Z1381382356</tradeOrderId>"+
	      "<tradeRemarkCode>4个汉字的订单简短描述</tradeRemarkCode>"+
	           "<tradeRemark>订单详细描述</tradeRemark>"+
	          "</tradeOrder>"+
	        "</tradeOrders> "+ 
	      "</tradeDetail>"+
	      "<logisticsDetail>"+
	        "<feeType>类型0：普货 1:特货</feeType>"+
	        "<transferable>true</transferable>"+
	        "<combinable>true</combinable>"+
	        "<totalWeight>2.322</totalWeight>"+
	        "<adjustedFee></adjustedFee>"+
	      "</logisticsDetail>"+
	    "</eventBody>"+
	  "</logisticsEvent>"+
	"</logisticsEventsRequest>";
		
		String url = "http://10.118.71.63:8089/lscm/tm/callback";
		String timestamp = "2016-01-12";
//		content = "&topic=sfbuy&sign=sfbuy&sign_method=md5&content&timestamp=" + timestamp
//				+ "&input_charset=utf-8&app_key=sfbuy&content =" + content;
		content = "topic=sfbuy&sign=sfbuy&sign_method=md5&timestamp=" + timestamp
				+ "&input_charset=utf-8&app_key=sfbuy&content=" + content;
		

		String result;
		try {
			result = HttpUtil.sendByPost(url, content);
			System.out.println("reponse content:" + result);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
