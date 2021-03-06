package com.its.test.crypto.des;


import javax.crypto.SecretKey;

import org.junit.Test;

import com.its.common.crypto.des.DesCryptUtil;

/**
 * 
 * @author tzz
 */
public class DesCryptUtilTest {
	
	
	/** 测试DES加密 */
	@Test
	public void testDES() {
	    // 算法
		String desAlgorithm = "DES";
		// 密钥
		String key = "QWE!@#123qwe123";
		// DES加密/解密
		// SecretKey desKey = DesCryptUtil.createSecretKey(desAlgorithm);
		SecretKey desKey = DesCryptUtil.createSecretKey(desAlgorithm, key);
		// 用密匙加密信息"Hello"
		String desEncrypt = DesCryptUtil.encrypt(desAlgorithm, desKey, "中文");
		System.out.println(desAlgorithm + "加密后:" + desEncrypt);
		// 使用这个密匙解密
		String desDecrypt = DesCryptUtil.decrypt(desAlgorithm, desKey, desEncrypt);
		System.out.println(desAlgorithm + "解密后：" + desDecrypt);

	}
	/** 测试DES加密 */
	@Test
	public void testDES2() {
	    // 算法
		String desAlgorithm = "DES";
		// 密钥
		String key = "QWE!@#123qwe123";
		// DES加密/解密
		// SecretKey desKey = DesCryptUtil.createSecretKey(desAlgorithm);
		SecretKey desKey = DesCryptUtil.createSecretKey(desAlgorithm, key);
		// 用密匙加密信息"Hello"
//		String desEncrypt = DesCryptUtil.encryptByte(desAlgorithm, desKey, "中文文");
		byte [] desEncryptByte =  DesCryptUtil.encryptByte(desAlgorithm, desKey, "中文文TEST测试");
		System.out.println(desAlgorithm + "加密后:" + DesCryptUtil.byte2hex(desEncryptByte));
		// 使用这个密匙解密
		String desDecrypt = DesCryptUtil.decryptByte(desAlgorithm, desKey, desEncryptByte);
		System.out.println(desAlgorithm + "解密后：" + desDecrypt);
		
	}
}