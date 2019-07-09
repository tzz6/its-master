package com.its.test.util;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.imageio.stream.FileImageOutputStream;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

/**
 * 
 * @author tzz
 */
@SuppressWarnings("deprecation")
public class OrderTest {
	private static final Logger logger = Logger.getLogger(OrderTest.class);

	static Map<String, Object> map = new HashMap<String, Object>();
	static Map<String, Object> orderMap = new HashMap<String, Object>();
	static Token token = new Token("60332", "xyPWT9il", "password");
	static {
		map.put("username", "60332");
		map.put("password", "xyPWT9il");
		map.put("grant_type", "password");
		orderMap.put("CompanyCode", "FXFX");
		orderMap.put("ProgramId", "1");
		orderMap.put("OrderDate", "2018-04-25");
		orderMap.put("CustomerReference1", "10000");
		orderMap.put("AccountKey", "S000068");
		orderMap.put("AccountName", "SF GLOBAL EXPRESS (M) SDN BHD");
		orderMap.put("ServiceCode", "EXP");
		orderMap.put("Commodity", "absdkjvnk");
		orderMap.put("Currency", "USD");
		orderMap.put("Value", "50");
		orderMap.put("Weight", "2");
		orderMap.put("Volume", "200");
		orderMap.put("Quantity", "2");
		orderMap.put("Uom", "PAR");
		orderMap.put("ShipperName", "sf");
		orderMap.put("ShipperAddress1", "bkdsbnnskjnsklbvndlbndls");
		orderMap.put("ShipperPostcode", "518000");
		orderMap.put("ShipperCity", "shenzhen");
		orderMap.put("ShipperCountry", "CN");
		orderMap.put("ShipperContactName", "wwww");
		orderMap.put("ShipperMobileNo", "13296649385");
		orderMap.put("ReceiverName", "gsdghd");
		orderMap.put("ReceiverAddress1", "sdgdh");
		orderMap.put("ReceiverPostcode", "1514");
		orderMap.put("ReceiverCity", "cc");
		orderMap.put("ReceiverCountry", "MY");
		orderMap.put("ReceiverContactName", "cccc");
		orderMap.put("ReceiverMobileNo", "13456981265");

	}

	public static void main(String[] args) {
		String token = "7TTn9evgksWsln9K4VD-wLzisStSh2kh7AkQBEmy-BQq5446h_Mmx_5XC15LYfrost4RpwHtuZMYSv1ry6onzdNO-8KujxEY6aMdLg8SpnnRy-DjwM1YOT888sx7wmyKC1hOL9PyuIVfBjzK_ceVNJyBMbiasoh3Bdje9ARkRvjB43em9AIuummNUwCwLdO-gm0JFy1aK0mNXhTorJt8WcSkFL2DJP0h0dxQxmD_gosytLkhnLc7Uc2wMWxzjOcGnR9j9kAI2ykQG-pLs7lzKLAdIXcGJ3W_wiWsV30srgZ3eUNNSKwXMagENjScsTS1bL6UhbqdNupzQDmTnIA78ryHf9zD6z_4oP5QzfqNeVe9lhMy4Ei-tBfzDOWUbYQkUeUt3eR6fGTkinCNn6ffu0v5_b8Goly4bCIowwgjVpRJMBaCrXSw5h_4dqyC9FNdDzrrH18vQM3ro5Jiw0wNlwqu8F8yr7nl6aVpbb1GenvyweCpdxkbQfvqryVqRp0dGsAeNoTKAqId9jvRzatJrokKCqiWTQ5vXEvFfLnjXqECCCdwvJrKHp6KXP0GeC22MPOSZ29n0Lo8PDFb-bO68UxlvzoxUELmMR8QT_fq_N4";

//		String orderResult = doPost("http://27.131.53.78:8899/api/order/congen", orderMap, token);
//		System.out.println(orderResult);
		String result = doGet("http://27.131.53.78:8899/api/order/label/MY1002921202", null, token);
		System.out.println(new String(result));

	}

	public static void byte2image(byte[] data, String path) {
	    int len = 3;
		if (data.length < len || "".equals(path)) {
		    return;
		}
		try {
			FileImageOutputStream imageOutput = new FileImageOutputStream(new File(path));
			imageOutput.write(data, 0, data.length);
			imageOutput.close();
			System.out.println("Make Picture success,Please find image in " + path);
		} catch (Exception ex) {
			System.out.println("Exception: " + ex);
			ex.printStackTrace();
		}
	}

	static class Token {
		protected String username;
		protected String password;
		protected String grant_type;

		public Token(String username, String password, String grant_type) {
			this.username = username;
			this.password = password;
			this.grant_type = grant_type;

		}

		public Token() {

		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getGrant_type() {
			return grant_type;
		}

		public void setGrant_type(String grant_type) {
			this.grant_type = grant_type;
		}
	}
	
	
	public static String doPost(String url, Map<String, Object> map, String token) {
		String result = "";
		HttpClient httpClient;
		try {
			httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url.trim());
			// httpPost.setHeader(HTTP.CONTENT_TYPE, "application/json;charset=utf-8");
			httpPost.setHeader("Authorization", "Bearer " + token);
			// 设置参数
			List<NameValuePair> list = new ArrayList<NameValuePair>();
			if (!StringUtils.isEmpty(map)) {
				Iterator<Entry<String, Object>> iterator = map.entrySet().iterator();
				while (iterator.hasNext()) {
					Entry<String, Object> elem = (Entry<String, Object>) iterator.next();
					list.add(new BasicNameValuePair(elem.getKey(), elem.getValue() + ""));
				}
			}
			if (list.size() > 0) {
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, "utf-8");
				httpPost.setEntity(entity);
			}
			HttpResponse response = httpClient.execute(httpPost);
			if (!StringUtils.isEmpty(response) && !StringUtils.isEmpty(response.getEntity())) {
				result = EntityUtils.toString(response.getEntity(), "utf-8");
			}
		} catch (Exception ex) {
			logger.info(String.format("http请求失败: %s", ex.toString()));
		}
		return result;
	}

	public static String doPost(String url, Object instance, String token) {
		String result = "";
		HttpClient httpClient = null;
		try {
			httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url.trim());
			// httpPost.setHeader(HTTP.CONTENT_TYPE, "application/json;charset=utf-8");
			httpPost.setHeader("Authorization", "Bearer " + token);
			// 设置参数
			List<NameValuePair> list = new ArrayList<NameValuePair>();
			if (!StringUtils.isEmpty(instance)) {
				Class<?> cls = instance.getClass();
				Field[] fields = cls.getDeclaredFields();
				for (Field field : fields) {
					field.setAccessible(true);
					if (StringUtils.isEmpty(field.get(instance))) {
						continue;
					} else {
						list.add(new BasicNameValuePair(field.getName(), field.get(instance).toString()));
					}
				}
			}

			if (list.size() > 0) {
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, "utf-8");
				httpPost.setEntity(entity);
			}
			HttpResponse response = httpClient.execute(httpPost);
			if (!StringUtils.isEmpty(response) && !StringUtils.isEmpty(response.getEntity())) {
				result = EntityUtils.toString(response.getEntity(), "utf-8");
			}
		} catch (Exception ex) {
			logger.info(String.format("http请求失败: %s", ex.toString()));
		}
		return result;
	}

	public static String doGet(String url, Map<String, Object> map, String token) {
		StringBuilder stringBuilder = new StringBuilder(url.trim());
		String result = "";
		HttpClient client = null;
		try {
			client = new DefaultHttpClient();
			// 拼接参数
			if (!StringUtils.isEmpty(map)) {
				Iterator<Entry<String, Object>> iterator = map.entrySet().iterator();
				String str = "?";
				if (url.contains(str)) {
					while (iterator.hasNext()) {
						Entry<String, Object> elem = (Entry<String, Object>) iterator.next();
						stringBuilder.append("&").append(elem.getKey()).append("=").append(elem.getValue());
					}
				} else {
					List<String> paramList = new ArrayList<String>(map.keySet());
					for (int i = 0; i < paramList.size(); i++) {
						if (0 == i) {
							stringBuilder.append("?").append(paramList.get(i)).append("=")
									.append(map.get(paramList.get(i)));
						} else {
							stringBuilder.append("&").append(paramList.get(i)).append("=")
									.append(map.get(paramList.get(i)));
						}
					}
				}
			}
			HttpGet request = new HttpGet(stringBuilder.toString());
			request.setHeader("Authorization", "Bearer " + token);
			// request.setHeader(HTTP.CONTENT_TYPE, "application/json;charset=utf-8");
			HttpResponse response = client.execute(request);
			// 请求发送成功，并得到响应
			if (!StringUtils.isEmpty(response) && response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				result = EntityUtils.toString(response.getEntity());
			}
		} catch (Exception e) {
			logger.info(String.format("http请求失败: %s", e.toString()));
		}

		return result;
	}

}
