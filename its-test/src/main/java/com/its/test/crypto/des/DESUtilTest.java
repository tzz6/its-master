package com.its.test.crypto.des;

import java.security.Key;

import org.junit.Test;

import com.its.common.crypto.des.DESUtil;

public class DESUtilTest {

	@Test
	public void testDesEncrypt() {
		String content = "中文123456";
		String encryptStr = DESUtil.encrypt(content, DESUtil.KEY);
		System.out.println(encryptStr);
		String decryptStr = DESUtil.decrypt(encryptStr, DESUtil.KEY);
		System.out.println(decryptStr);
	}

	@Test
	public void testDes() {
		String content = "中文123456";
		String keyStr = DESUtil.initKey("QWE!@#123qwe123");
		String keyStr2 = DESUtil.initKey("QWE!@#123qwe123");
		System.out.println("原文:" + content);
		System.out.println("密钥:" + keyStr);
		System.out.println("密钥:" + keyStr2);
		Key key = DESUtil.createKey(DESUtil.decryptBASE64(keyStr));
		byte[] encryptByte = DESUtil.encrypt(content.getBytes(), key);
		String encryptStr = DESUtil.encryptBASE64(encryptByte);
		System.out.println("加密后:" + encryptStr);
		byte[] decryptByte = DESUtil.decrypt(DESUtil.decryptBASE64(encryptStr), key);
		String outputStr = new String(decryptByte);
		System.out.println("解密后:" + outputStr);
	}

	@Test
	public void testDesEncryptJDBC() {
		String jdbUrl = "jdbc:mysql://localhost:3306/webdemo?useUnicode=true&amp;characterEncoding=UTF8";
		String jdbcUsername = "root";
		String jdbcPassword = "root123";
		String jdbUrlEncrypt = DESUtil.encrypt(jdbUrl, DESUtil.KEY);
		String jdbcUsernameEncrypt = DESUtil.encrypt(jdbcUsername, DESUtil.KEY);
		String jdbcPasswordEncrypt = DESUtil.encrypt(jdbcPassword, DESUtil.KEY);

		System.out.println(jdbUrlEncrypt);
		System.out.println(jdbcUsernameEncrypt);
		System.out.println(jdbcPasswordEncrypt);

		System.out.println(DESUtil.decrypt(jdbUrlEncrypt, DESUtil.KEY));
		System.out.println(DESUtil.decrypt(jdbcUsernameEncrypt, DESUtil.KEY));
	}
}