package com.its.test.crypto.simple;

import org.junit.Test;

import com.its.common.crypto.simple.Base64Util;
/**
 * 
 * @author tzz
 * @date 2019/02/25
 * @Introduce: Base64UtilTest
 */
public class Base64UtilTest {
	@Test
	public void test() {
		String encryptStr = Base64Util.encrypt("123456".getBytes());
		System.out.println("加密："+encryptStr);
		System.out.println("解密："+Base64Util.decrypt(encryptStr));
	}
}