package com.its.test.crypto.certificate;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.its.common.crypto.certificate.CertificateCryptUtil;

/**
 * 测试--Java加密技术——数字证书
 * 
 */
public class CertificateCryptUtilTest {
	private String password = "123456789";
	private String alias = "www.tzz-sf.com";
	//生成证书
	//keytool -genkey -validity 1 -alias www.tzz-sf.com -keyalg RSA -keystore d:\tzz-sf.keystore
	//keytool -export -keystore d:\tzz-sf.keystore -alias www.tzz-sf.com -file d:\tzz-sf.cer -rfc
	private String keyStorePath = "d:/tzz-sf.keystore";
	private String certificatePath = "d:/tzz-sf.cer";

	/**公钥加密——私钥解密*/
	@Test
	public void test() throws Exception {
		String inputStr = "加密字符123456Abc";
		byte[] data = inputStr.getBytes();
		byte[] encrypt = CertificateCryptUtil.encryptByPublicKey(data, certificatePath);

		byte[] decrypt = CertificateCryptUtil.decryptByPrivateKey(encrypt, keyStorePath, alias, password);
		String outputStr = new String(decrypt);
		System.err.println("加密前: " + inputStr + "-----" + "解密后: " + outputStr);
		// 验证数据一致
		assertArrayEquals(data, decrypt);
		// 验证证书有效
		assertTrue(CertificateCryptUtil.verifyCertificate(certificatePath));
	}

	/**私钥加密——公钥解密*/
	@Test
	public void testSign() throws Exception {
		String inputStr = "加密字符123456Abc";
		byte[] data = inputStr.getBytes();
		byte[] encodedData = CertificateCryptUtil.encryptByPrivateKey(data, keyStorePath, alias, password);

		byte[] decodedData = CertificateCryptUtil.decryptByPublicKey(encodedData, certificatePath);

		String outputStr = new String(decodedData);
		System.err.println("加密前: " + inputStr + "----" + "解密后: " + outputStr);
		assertEquals(inputStr, outputStr);

		// 产生签名
		String sign = CertificateCryptUtil.sign(encodedData, keyStorePath, alias, password);
		System.err.println("签名:\r" + sign);
		// 验证签名
		boolean status = CertificateCryptUtil.verify(encodedData, sign, certificatePath);
		System.err.println("状态:\r" + status);
		assertTrue(status);

	}
}