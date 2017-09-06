package com.its.test.crypto.des;


import javax.crypto.SecretKey;

import org.junit.Test;

import com.its.common.crypto.des.DesCryptUtil;

public class DesCryptUtilTest {
	
	@Test
	public void test() {
		//DES加密/解密
		//SecretKey desKey = CryptUtil.createSecretKey("DES");
		SecretKey desKey = DesCryptUtil.createSecretKey("DES","TT#%%#KEY");
		// 用密匙加密信息"Hello"
		String desEncrypt = DesCryptUtil.encrypt(desKey, "123456");
		System.out.println("DES加密后:" + desEncrypt);
		// 使用这个密匙解密
		String desDecrypt = DesCryptUtil.decrypt(desKey, desEncrypt);
		System.out.println("DES解密后：" + desDecrypt);
		
	}
}