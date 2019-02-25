package com.its.test.crypto.des;

import java.security.Key;

import org.junit.Test;

import com.its.common.crypto.des.DesUtil;
/**
 * 
 * @author tzz
 */
public class DesUtilTest {

	@Test
	public void testDesEncrypt() {
		String jdbUrl = "jdbc:mysql://localhost:3306/webdemo?autoReconnect=true&useUnicode=true&characterEncoding=UTF-8";
		String jdbcUsername = "root";
		String jdbcPassword = "root123";
		String data = "Tzz123456~!@#$%^&*()_+-={}|[]:'<>?,./";
		String jdbUrlEncrypt = DesUtil.encrypt(jdbUrl, DesUtil.KEY);
		String jdbcUsernameEncrypt = DesUtil.encrypt(jdbcUsername, DesUtil.KEY);
		String jdbcPasswordEncrypt = DesUtil.encrypt(jdbcPassword, DesUtil.KEY);
		String dateEncrypt = DesUtil.encrypt(data, DesUtil.KEY);

		System.out.println(jdbUrlEncrypt);
		System.out.println(jdbcUsernameEncrypt);
		System.out.println(jdbcPasswordEncrypt);
		System.out.println(dateEncrypt);

		System.out.println(DesUtil.decrypt(jdbUrlEncrypt, DesUtil.KEY));
		System.out.println(DesUtil.decrypt(jdbcUsernameEncrypt, DesUtil.KEY));
		System.out.println(DesUtil.decrypt(jdbcPasswordEncrypt, DesUtil.KEY));
		System.out.println(DesUtil.decrypt(dateEncrypt, DesUtil.KEY));
	}

	@Test
	public void testDes() {
		String content = "中文123456";
		String keyStr = DesUtil.initKey("QWE!@#123qwe123");
		System.out.println("原文:" + content);
		System.out.println("密钥:" + keyStr);
		Key key = DesUtil.createKey(DesUtil.decryptBASE64(keyStr));
		byte[] encryptByte = DesUtil.encrypt(content.getBytes(), key);
		String encryptStr = DesUtil.encryptBASE64(encryptByte);
		System.out.println("加密后:" + encryptStr);
		byte[] decryptByte = DesUtil.decrypt(DesUtil.decryptBASE64(encryptStr), key);
		String outputStr = new String(decryptByte);
		System.out.println("解密后:" + outputStr);
	}

}