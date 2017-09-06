package com.its.test.crypto.simple;

import org.junit.Test;

import com.its.common.crypto.simple.BASE64Util;

public class BASE64UtilTest {
	@Test
	public void test() {
		String encryptStr = BASE64Util.encrypt("123456".getBytes());
		System.out.println("加密："+encryptStr);
		System.out.println("解密："+BASE64Util.decrypt(encryptStr));
	}
}