package com.its.test.crypto.rsa;

import java.util.Map;

import org.junit.Test;

import com.its.common.crypto.rsa.RSACryptUtil;

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
}