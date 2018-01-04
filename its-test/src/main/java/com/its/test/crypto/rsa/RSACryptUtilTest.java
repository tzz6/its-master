package com.its.test.crypto.rsa;

import java.util.Map;

import org.junit.Test;

import com.its.common.crypto.rsa.RSACryptUtil;
import com.its.common.crypto.simple.MD5SHACryptoUtil;

public class RSACryptUtilTest {

	private static String publicKey;
	private static String privateKey;
	
	static {
		Map<String, Object> keyMap = RSACryptUtil.initKey();
		publicKey = RSACryptUtil.getPublicKey(keyMap);
		privateKey = RSACryptUtil.getPrivateKey(keyMap);
		System.out.println("公钥: \n\r" + publicKey);
		System.out.println("私钥： \n\r" + privateKey);
	}
	
	@Test
	public void test(){
		try {
			System.out.println("公钥加密---私钥解密");
			String date = "123456公钥加密---私钥解密";
			byte[] encodedData = RSACryptUtil.encryptByPublicKey(date.getBytes(), publicKey);
			String encodedStr = RSACryptUtil.encryptBASE64(encodedData);
			byte[] decodedData = RSACryptUtil.decryptByPrivateKey(RSACryptUtil.decryptBASE64(encodedStr), privateKey);
			String outputStr = new String(decodedData);
			System.out.println("加密前: " + date + "\n\r" + "加密后: " + encodedStr + "\n\r" + "解密后: " + outputStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testSign() throws Exception {
		try {
			System.out.println("私钥加密---公钥解密");
			String inputStr = "123456";
			byte[] data = inputStr.getBytes();
			
			byte[] encodedData = RSACryptUtil.encryptByPrivateKey(data, privateKey);
			
			byte[] decodedData = RSACryptUtil.decryptByPublicKey(encodedData, publicKey);
			
			String outputStr = new String(decodedData);
			System.out.println("加密前: " + inputStr + "\n\r" + "解密后: " + outputStr);
			
			System.out.println("私钥签名——公钥验证签名");
			// 产生签名
			String sign = RSACryptUtil.sign(encodedData, privateKey);
			System.out.println("签名:\r" + sign);
			// 验证签名
			boolean status = RSACryptUtil.verify(encodedData, publicKey, sign);
			System.out.println("状态:\r" + status);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void testPublic(){
		try {
			System.out.println("公钥加密---私钥解密");
			String date = "123456公钥加密---私钥解密";
			String pubKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDgSCCL8R3E2cNAO38TkBsk6c/Q2tJXNwkquGGuY9fiWVyOj7m/TzGuq7prAu6PoAtikXA8TgGMB/Z/MxYz6BfomlHPKAjfjpHXf4A5g0RsHHESxNHRE9QP4ir/MJ5PwVVJgA1ibw8dkHzX7ID3f+3V/XdqBHiuyELwi7gao7Ja6wIDAQAB";
			byte[] encodedData = RSACryptUtil.encryptByPublicKey(date.getBytes(), pubKey);
			String encodedStr = RSACryptUtil.encryptBASE64(encodedData);
			String priKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAOBIIIvxHcTZw0A7fxOQGyTpz9Da"
					+ "0lc3CSq4Ya5j1+JZXI6Pub9PMa6rumsC7o+gC2KRcDxOAYwH9n8zFjPoF+iaUc8oCN+Okdd/gDmD"
					+ "RGwccRLE0dET1A/iKv8wnk/BVUmADWJvDx2QfNfsgPd/7dX9d2oEeK7IQvCLuBqjslrrAgMBAAEC"
					+ "gYAQvrHXYOglE1EVkZuaPU8ZgW9nm37KziwcCWoZmBC9MIjNiAOJOgNulBm19aEUDhHriQpFJlnN"
					+ "N6b6tji5JWHrcwgJk2R8WlG3kArerWLHIq5V93SDI/OQdHTBA6c2gIK2HAJ+ZgpDzh56Mq3p+erl"
					+ "Ud/fie/wmojn2cL6LeLWWQJBAP9FJOzjVYRIiae5RZ8g7k+xoclf1JKHlIcVZhENiGTWVdnO0eLI"
					+ "T2AEJja4FyjVsVVtE1Zo1Jh0zZGiRwQQq8UCQQDg7Ey2niY5eZntn8eq7fDuasEYMi1ztgqogap4"
					+ "3QAXG+DUM6kpno22IiQSmAceLV9e/fgzWOoekL7awTqYg+bvAkBTOsAnXJftYZlATnAcyifpZAlU"
					+ "FyLAA+SxhpCYzsjB2AB127EjOBxpOfEbtjoW3lXLfJzpd5SZgLvl1/s/oA/hAkBUAi5M7xjb0r1Z"
					+ "cZpED4czpY/ll6g+Vbn5YiTn67OC7hi1aW4/a0cGxg2vHDVcYhoDAtzXYNhg/jMqxY07NdjlAkEA"
					+ "gtTLxrw1WrQQ3Qj76l556ihm9xTYr/OYm+rq+oXmULmk/ud9MzEQ8mP0Pz/DmxV3KmU73JOrCfR3"
					+ "V9mrVTbe4Q==";
			byte[] decodedData = RSACryptUtil.decryptByPrivateKey(RSACryptUtil.decryptBASE64("CY/3yVGvkm6h2Im+cUl91kmKYfmhUyYoldytxr5E7htYlKz8G6xymItDvTprT5q6SoEd7dXH1x1s6/gaU8IeVYbfHoH1jgsOcI5ewZ3M5b2yP3A5NjLX2WCde081xm3Ju+bf+L+nUkpzVDm8iDcbxbxyHk/ZmAlPiyqQaf0uPKc="), priKey);
			String outputStr = new String(decodedData);
			System.out.println("加密前: " + date + "\n\r" + "加密后: " + encodedStr + "\n\r" + "解密后: " + outputStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}