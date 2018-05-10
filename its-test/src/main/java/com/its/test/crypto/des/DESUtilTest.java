package com.its.test.crypto.des;

import java.security.Key;

import org.junit.Test;

import com.its.common.crypto.des.DESUtil;

public class DESUtilTest {

	@Test
	public void testDesEncrypt() {
		String jdbUrl = "jdbc:mysql://localhost:3306/webdemo?autoReconnect=true&useUnicode=true&characterEncoding=UTF-8";
		String jdbcUsername = "root";
		String jdbcPassword = "root123";
		String data = "Tzz123456~!@#$%^&*()_+-={}|[]:'<>?,./";
		String jdbUrlEncrypt = DESUtil.encrypt(jdbUrl, DESUtil.KEY);
		String jdbcUsernameEncrypt = DESUtil.encrypt(jdbcUsername, DESUtil.KEY);
		String jdbcPasswordEncrypt = DESUtil.encrypt(jdbcPassword, DESUtil.KEY);
		String dateEncrypt = DESUtil.encrypt(data, DESUtil.KEY);

		System.out.println(jdbUrlEncrypt);
		System.out.println(jdbcUsernameEncrypt);
		System.out.println(jdbcPasswordEncrypt);
		System.out.println(dateEncrypt);

		System.out.println(DESUtil.decrypt(jdbUrlEncrypt, DESUtil.KEY));
		System.out.println(DESUtil.decrypt(jdbcUsernameEncrypt, DESUtil.KEY));
		System.out.println(DESUtil.decrypt(jdbcPasswordEncrypt, DESUtil.KEY));
		System.out.println(DESUtil.decrypt(dateEncrypt, DESUtil.KEY));
	}

	@Test
	public void testDes() {
		String content = "中文123456";
		String keyStr = DESUtil.initKey("QWE!@#123qwe123");
		System.out.println("原文:" + content);
		System.out.println("密钥:" + keyStr);
		Key key = DESUtil.createKey(DESUtil.decryptBASE64(keyStr));
		byte[] encryptByte = DESUtil.encrypt(content.getBytes(), key);
		String encryptStr = DESUtil.encryptBASE64(encryptByte);
		System.out.println("加密后:" + encryptStr);
		byte[] decryptByte = DESUtil.decrypt(DESUtil.decryptBASE64(encryptStr), key);
		String outputStr = new String(decryptByte);
		System.out.println("解密后:" + outputStr);
	}

}