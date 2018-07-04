package com.its.test.util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.junit.Test;

import com.its.common.utils.HttpClientUtil;


public class HttpClientUtilTest {

	/********************SFBUY*******************/
	/**CMSP预审结果推送回SFBUY*/
	@Test
	public void sendHttpPostSfbuyCmsp() {
		String xml = "<?xml version='1.0' encoding='UTF-8'?><ExamResult><orderId>2017021510123739838007450</orderId><status>1</status>"
				+ "<rmark>HKG 香港口岸</rmark></ExamResult>";
		System.out.println("content:" + xml);
		Map<String, String> maps = new HashMap<String, String>();
		maps.put("content", xml);
		String responseContent = HttpClientUtil.getInstance().sendHttpPost("http://localhost:8080/sfbuy/examResult",
				maps);
		System.out.println("reponse content:" + responseContent);
	}
	
	/** 接收'BSP路由推送'(路由推送为批量成功或失败)*/
	@Test
	public void sendHttpPostSfbuyBsp() {
		String xml = "<?xml version='1.0' encoding='UTF-8'?><Request service='RoutePushService' lang='zh-CN'>"
				+ "<Body><WaybillRoute id='10148383553' mailno='604181429660' orderid='9A6C9FB23EDE490E8B8EABFF565B5838' "
				+ "acceptTime='2016-11-30 13:52:26' acceptAddress='澳门' remark='派送成功' opCode='80'/></Body></Request>";
		System.out.println("content:" + xml);
		Map<String, String> maps = new HashMap<String, String>();
		maps.put("content", xml);
		String responseContent = HttpClientUtil.getInstance().sendHttpPost("http://10.118.65.47:8081/bsp/route/push",
				maps);
		System.out.println("reponse content:" + responseContent);
	}
	
	@Test
	public void sendHttpPostSfbuylscm() {
		
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
	            "<tradeOrderId>100002</tradeOrderId>"+
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
		
		Map<String, String> maps = new HashMap<String, String>();
		maps.put("content", content);
//		String responseContent = HttpClientUtil.getInstance()
//				.sendHttpPost("http://10.118.71.63:8089/lscm/callback", "content="+content);
		String responseContent = HttpClientUtil.getInstance()
				.sendHttpPost("http://sfbuy-admin.sf-express.com/lscm/tm/callback", maps);

		System.out.println("reponse content:" + responseContent);
	}
	
	
	/******************** its-web测试 ********************/
	@Test
	public void testSendHttpPost1() {
		String responseContent = HttpClientUtil.getInstance()
				.sendHttpPost("http://localhost:8081/test/send?username=test01&password=123456");
		System.out.println("reponse content:" + responseContent);
	}

	@Test
	public void testSendHttpPost2() {
		String responseContent = HttpClientUtil.getInstance().sendHttpPost("http://localhost:8081/test/send",
				"username=test01&password=123456");
		System.out.println("reponse content:" + responseContent);
	}

	@Test
	public void testSendHttpPost3() {
		Map<String, String> maps = new HashMap<String, String>();
		maps.put("username", "test01");
		maps.put("password", "123456");
		String responseContent = HttpClientUtil.getInstance().sendHttpPost("http://localhost:8081/test/send",
				maps);
		System.out.println("reponse content:" + responseContent);
	}

	@Test
	public void sendHttpPostSendFile() {
		Map<String, String> maps = new HashMap<String, String>();
		maps.put("username", "test01");
		maps.put("password", "123456");
		List<File> fileLists = new ArrayList<File>();
		fileLists.add(new File("D://test//httpclient//1.png"));
		fileLists.add(new File("D://test//httpclient//1.txt"));
		String responseContent = HttpClientUtil.getInstance()
				.sendHttpPost("http://localhost:8081/test/send/file", maps, fileLists);
		System.out.println("reponse content:" + responseContent);
	}

	@Test
	public void testSendHttpGet() {
		String responseContent = HttpClientUtil.getInstance()
				.sendHttpGet("http://localhost:8081/test/send?username=test01&password=123456");
		System.out.println("reponse content:" + responseContent);
	}

	@Test
	public void testSendHttpsGet() {
		String responseContent = HttpClientUtil.getInstance().sendHttpsGet("https://www.baidu.com");
		System.out.println("reponse content:" + responseContent);
	}
	@Test
	public void testSendHttpPostJson() {
		JSONObject params = new JSONObject();
		params.put("id", 123);
		params.put("name", "test01");
		params.put("password", "test0001");
		String responseContent = HttpClientUtil.getInstance().sendHttpPostJson("http://localhost:8081/test/agentSelect",
				"{\"id\":123,\"name\":\"aa\",\"password\":\"cc\"}"
);
		System.out.println("reponse content:" + responseContent);
	}
	
	
	
	
	@Test
	public void testSendHttpPostJson2() {
		JSONObject params = new JSONObject();
		params.put("waybillNo", "070123");
		params.put("gatewayCode", "C901");
		params.put("customersCode", "");
		params.put("senderAdress", "");
		params.put("senderCompany", "");
		params.put("senderPhone", "");
		params.put("senderCode", "");
		params.put("receiverCode", "");
		params.put("receiverAdress", "");
		params.put("receiverCompany", "");
		params.put("receiverPhone", "");
		params.put("shipmentName", "");
		params.put("productType", "MYR");
		params.put("declaredValue", "100.00");
		params.put("actualWeight", "");
		params.put("monthlyAccount", "");
		params.put("goodsName", "");
		params.put("payMethod", "");
		params.put("originPlace", "");
		params.put("currencyCode", "RMB");
		
		String json = params.toString();
//		System.out.println(json);
		String responseContent = HttpClientUtil.getInstance().sendHttpPostJson("http://iop-mcs.sit.sf-express.com/mcs/agentSelect", json);
//		"{'waybillNo': '070123','gatewayCode': 'MIA','productType': 'C901','declaredValue': '2.596','currencyCode': 'MYR','customersCode':'','senderAdress':'','senderCompany':'','senderPhone':'','senderCode':'','receiverCode':'','receiverAdress':'','receiverCompany':'','receiverPhone':'','shipmentName':'','actualWeight':'','monthlyAccount':'','goodsName':'','payMethod':'', 'originPlace':''}"
		
		System.out.println("reponse content:" + responseContent);
	}
	
	/******************** IBU-IOP-CORE测试 ********************/
	/** FMX代理接口 */
	@Test
	public void sendHttpPostIopCoreFMX() {
		String responseContent = HttpClientUtil.getInstance().sendHttpPost(
				"https://api.freightmark.asia:4445/token","username=60332&password=xyPWT9il&grant_type=password");
		System.out.println("reponse content:" + responseContent);
	}
	
	
	/**Iteye*/
	@Test
	public void testSendHttpIteye(){
		for (int i = 0; i < 5; i++) {
			try {
//				System.out.println("----------------" + i + "----------------");
				List<String> urls = new ArrayList<String>();
				urls.add("http://tzz6.iteye.com/blog/1775580");
				urls.add("http://tzz6.iteye.com/blog/1775585");
				urls.add("http://tzz6.iteye.com/blog/1775587");
				urls.add("http://tzz6.iteye.com/blog/1806639");
				urls.add("http://tzz6.iteye.com/blog/1831008");
				urls.add("http://tzz6.iteye.com/blog/1860612");
				urls.add("http://tzz6.iteye.com/blog/1906429");
				urls.add("http://tzz6.iteye.com/blog/1906432");
				urls.add("http://tzz6.iteye.com/blog/1909031");
				urls.add("http://tzz6.iteye.com/blog/1909043");
				urls.add("http://tzz6.iteye.com/blog/1915448");
				urls.add("http://tzz6.iteye.com/blog/1944849");
				urls.add("http://tzz6.iteye.com/blog/1988525");
				urls.add("http://tzz6.iteye.com/blog/2015767");
				urls.add("http://tzz6.iteye.com/blog/2038787");
				urls.add("http://tzz6.iteye.com/blog/2043646");
				urls.add("http://tzz6.iteye.com/blog/2077829");
				urls.add("http://tzz6.iteye.com/blog/2088955");
				urls.add("http://tzz6.iteye.com/blog/2104397");
				urls.add("http://tzz6.iteye.com/blog/2139478");
				urls.add("http://tzz6.iteye.com/blog/2139368");
				urls.add("http://tzz6.iteye.com/blog/2139483");
				urls.add("http://tzz6.iteye.com/blog/2139485");
				urls.add("http://tzz6.iteye.com/blog/2139487");
				urls.add("http://tzz6.iteye.com/blog/2164703");
				urls.add("http://tzz6.iteye.com/blog/2166022");
				urls.add("http://tzz6.iteye.com/blog/2166026");
				urls.add("http://tzz6.iteye.com/blog/2166070");
				urls.add("http://tzz6.iteye.com/blog/2175447");
				urls.add("http://tzz6.iteye.com/blog/2173788");
				urls.add("http://tzz6.iteye.com/blog/2172827");
				urls.add("http://tzz6.iteye.com/blog/2172826");
				urls.add("http://tzz6.iteye.com/blog/2172414");
				urls.add("http://tzz6.iteye.com/blog/2171239");
				urls.add("http://tzz6.iteye.com/blog/2169744");
				urls.add("http://tzz6.iteye.com/blog/2169736");
				urls.add("http://tzz6.iteye.com/blog/2166074");
				urls.add("http://tzz6.iteye.com/blog/2187207");
				urls.add("http://tzz6.iteye.com/blog/2187206");
				urls.add("http://tzz6.iteye.com/blog/2181770");
				urls.add("http://tzz6.iteye.com/blog/2181452");
				urls.add("http://tzz6.iteye.com/blog/2181346");
				urls.add("http://tzz6.iteye.com/blog/2180939");
				urls.add("http://tzz6.iteye.com/blog/2180938");
				urls.add("http://tzz6.iteye.com/blog/2176734");
				urls.add("http://tzz6.iteye.com/blog/2175875");
				urls.add("http://tzz6.iteye.com/blog/2175842");
				urls.add("http://tzz6.iteye.com/blog/2175811");
				urls.add("http://tzz6.iteye.com/blog/2175498");
				urls.add("http://tzz6.iteye.com/blog/2175474");
				urls.add("http://tzz6.iteye.com/blog/2175464");
				urls.add("http://tzz6.iteye.com/blog/2175454");
				urls.add("http://tzz6.iteye.com/blog/2205566");
				urls.add("http://tzz6.iteye.com/blog/2201306");
				urls.add("http://tzz6.iteye.com/blog/2201288");
				urls.add("http://tzz6.iteye.com/blog/2197416");
				urls.add("http://tzz6.iteye.com/blog/2196769");
				urls.add("http://tzz6.iteye.com/blog/2196742");
				urls.add("http://tzz6.iteye.com/blog/2196728");
				urls.add("http://tzz6.iteye.com/blog/2196074");
				urls.add("http://tzz6.iteye.com/blog/2195181");
				urls.add("http://tzz6.iteye.com/blog/2195078");
				urls.add("http://tzz6.iteye.com/blog/2194836");
				urls.add("http://tzz6.iteye.com/blog/2187634");
				urls.add("http://tzz6.iteye.com/blog/2187630");
				urls.add("http://tzz6.iteye.com/blog/2224981");
				urls.add("http://tzz6.iteye.com/blog/2224757");
				urls.add("http://tzz6.iteye.com/blog/2218323");
				urls.add("http://tzz6.iteye.com/blog/2218141");
				urls.add("http://tzz6.iteye.com/blog/2211048");
				urls.add("http://tzz6.iteye.com/blog/2211043");
				urls.add("http://tzz6.iteye.com/blog/2210480");
				urls.add("http://tzz6.iteye.com/blog/2205798");
				urls.add("http://tzz6.iteye.com/blog/2205769");
				urls.add("http://tzz6.iteye.com/blog/2205700");
				urls.add("http://tzz6.iteye.com/blog/2269441");
				urls.add("http://tzz6.iteye.com/blog/2269229");
				urls.add("http://tzz6.iteye.com/blog/2269214");
				urls.add("http://tzz6.iteye.com/blog/2212952");
				urls.add("http://tzz6.iteye.com/blog/2262983");
				urls.add("http://tzz6.iteye.com/blog/2262904");
				urls.add("http://tzz6.iteye.com/blog/2260750");
				urls.add("http://tzz6.iteye.com/blog/2259743");
				urls.add("http://tzz6.iteye.com/blog/2258149");
				urls.add("http://tzz6.iteye.com/blog/2256597");
				urls.add("http://tzz6.iteye.com/blog/2242758");
				urls.add("http://tzz6.iteye.com/blog/2232643");
				for (String url : urls) {
//					System.out.println(url);
//					Thread.sleep(1000*1);
					HttpClientUtil.getInstance().sendHttpGet(url);
				}
				System.out.println("--------------sleep(1000*6)--------------");
				Thread.sleep(1000*6);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println("success");
	}
	
	
	
	@Test
	public void testSendHttpPostDZ() {
		for (int i = 0; i < 1; i++) {
			String responseContent = HttpClientUtil.getInstance().sendHttpPost(
					"http://imsp.sf-express.com/service/core/comment/zanCommentFromPc.pub",
					"commentId=58920121801115486&empCode=TURFek11YzJNak4" + i);
			System.out.println("reponse content:" + responseContent);
		}

	}

}
