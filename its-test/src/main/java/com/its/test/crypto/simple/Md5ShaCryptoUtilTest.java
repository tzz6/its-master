package com.its.test.crypto.simple;

import org.junit.Test;

import com.its.common.crypto.simple.Md5ShaCryptoUtil;
/**
 * 
 * @author tzz
 * @date 2019/02/25
 * @Introduce: Base64UtilTest
 */
public class Md5ShaCryptoUtilTest {

	
	@Test
	public void test() {
		System.out.println(Md5ShaCryptoUtil.md5Encrypt("123456"));
		System.out.println(Md5ShaCryptoUtil.shaEncrypt("123456"));
	}
	
	@Test
	public void testMd5Encrypt() {
		System.out.println(Md5ShaCryptoUtil.md5Encrypt("123456"));
	}
	@Test
	public void testSha512Encrypt() {
		String str1 = Md5ShaCryptoUtil.sha512Encrypt("123456");
		String str4 = Md5ShaCryptoUtil.sha512Encrypt("adminadmin");
		String str3 = Md5ShaCryptoUtil.sha512Encrypt("14e1b600b1fd579f47433b88e8d85291");
		String str2 = "ddaf35a193617abacc417349ae20413112e6fa4e89a97ea20a9eeee64b55d39a2192992a274fc1a836ba3c23a3feebbd454d4423643ce80e2a9ac94fa54ca49f";
		System.out.println(str1);
		System.out.println(str1.length());
		System.out.println(str3);
		System.out.println(str2);
		System.out.println(str1.equals(str2));
		System.out.println(str4);
	}
}