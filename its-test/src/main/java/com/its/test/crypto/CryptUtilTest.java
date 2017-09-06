package com.its.test.crypto;

import javax.crypto.SecretKey;

import org.junit.Test;

import com.its.common.crypto.CryptUtil;

public class CryptUtilTest {

	@Test
	public void test() {
		System.out.println("MD5加密:" + CryptUtil.encryptToMD5("123456"));
		System.out.println("SHA1加密:" + CryptUtil.encryptToSHA("123456"));
		
		//3. DES加密/解密
		//SecretKey desKey = CryptUtil.createSecretKey("DES");
		SecretKey desKey = CryptUtil.createSecretKey("DES","IF4py=18]^bhBR2.");
		// 用密匙加密信息"Hello"
		String desEncrypt = CryptUtil.encryptToDES(desKey, "123456");
		System.out.println("DES加密:" + desEncrypt);
		// 使用这个密匙解密
		String desDecrypt = CryptUtil.decryptByDES(desKey, desEncrypt);
		System.out.println("DES解密：" + desDecrypt);
		
		//3.创建公匙和私匙
		CryptUtil.createPairKey();
		// 对123456使用私匙进行签名
		CryptUtil.signToInfo("123456", "mysign.bat");
		// 利用公匙对签名进行验证。
		if (CryptUtil.validateSign("mysign.bat")) {
			System.out.println("Success!");
		} else {
			System.out.println("Fail!");
		}
	}
}