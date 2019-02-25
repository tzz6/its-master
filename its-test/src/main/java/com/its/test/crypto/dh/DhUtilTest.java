package com.its.test.crypto.dh;

import java.util.Map;

import org.junit.Test;

import com.its.common.crypto.dh.DhUtil;

/**
 * 
 * @author tzz
 */
public class DhUtilTest {
	
	@Test
	public void testDH() throws Exception {
		DhUtil dhUtil = new DhUtil();
		// 生成甲方密钥对儿
		Map<String, Object> aKeyMap = dhUtil.initKey();
		String aPublicKey = dhUtil.getPublicKey(aKeyMap);
		String aPrivateKey = dhUtil.getPrivateKey(aKeyMap);
		System.out.println("甲方公钥:\r" + aPublicKey);
		System.out.println("甲方私钥:\r" + aPrivateKey);
		
		// 由甲方公钥产生本地密钥对儿
		Map<String, Object> bKeyMap = dhUtil.initKey(aPublicKey);
		String bPublicKey = dhUtil.getPublicKey(bKeyMap);
		String bPrivateKey = dhUtil.getPrivateKey(bKeyMap);
		System.out.println("乙方公钥:\r" + bPublicKey);
		System.out.println("乙方私钥:\r" + bPrivateKey);
		
		String aInput = "abcdef ";
		System.out.println("原文: " + aInput);

		// 由甲方公钥，乙方私钥构建密文
		byte[] aCode = dhUtil.encrypt(aInput.getBytes(), aPublicKey, bPrivateKey);

		// 由乙方公钥，甲方私钥解密
		byte[] aDecode = dhUtil.decrypt(aCode, bPublicKey, aPrivateKey);
		String aOutput = (new String(aDecode));
		System.out.println("解密: " + aOutput);

		/**反过来加密解密*/
		String bInput = "123456 ";
		System.out.println("原文: " + bInput);

		// 由乙方公钥，甲方私钥构建密文
		byte[] bCode = dhUtil.encrypt(bInput.getBytes(), bPublicKey, aPrivateKey);

		// 由甲方公钥，乙方私钥解密
		byte[] bDecode = dhUtil.decrypt(bCode, aPublicKey, bPrivateKey);
		String bOutput = (new String(bDecode));
		System.out.println("解密: " + bOutput);
	}

}