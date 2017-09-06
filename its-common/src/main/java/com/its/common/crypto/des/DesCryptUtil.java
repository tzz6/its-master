package com.its.common.crypto.des;


import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class DesCryptUtil {
	
	/**生成一个DES算法的密匙*/
	public static SecretKey createSecretKey(String algorithm) {
		// 声明KeyGenerator对象
		KeyGenerator keygen;
		// 声明 密钥对象
		SecretKey deskey = null;
		try {
			// 返回生成指定算法的秘密密钥的 KeyGenerator 对象
			keygen = KeyGenerator.getInstance(algorithm);
			// 生成一个密钥
			deskey = keygen.generateKey();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		// 返回密匙
		return deskey;
	}
	
	/**
	 * 生成一个DES算法的密匙
	 * @param key指定密匙 
	 */
	public static SecretKey createSecretKey(String des, String key) {
		// 声明 密钥对象
		SecretKey secretKey = null;
		SecretKeyFactory secretKeyFactory = null;
		try {
			secretKeyFactory = SecretKeyFactory.getInstance(des);
			DESKeySpec keyspec = new DESKeySpec(key.getBytes());
			// 生成一个密钥
			secretKey = secretKeyFactory.generateSecret(keyspec);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 返回密匙
		return secretKey;
	}
	
	/**加密*/
	public static String encrypt(SecretKey key, String date) {
		String algorithm = "DES";//定义加密算法
		SecureRandom sr = new SecureRandom();// 加密随机数生成器 (可以不写)
		byte[] cipherByte = null;// 定义要生成的密文
		try {
			Cipher cipher = Cipher.getInstance(algorithm);//加密/解密器
			// 用指定的密钥和模式初始化Cipher对象
			cipher.init(Cipher.ENCRYPT_MODE, key, sr);
			// 对要加密的内容进行编码处理,
			cipherByte = cipher.doFinal(date.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 返回密文的十六进制形式
		return byte2hex(cipherByte);
	}
	/**解密*/
	public static String decrypt(SecretKey key, String date) {
		String algorithm = "DES";//定义加密算法
		SecureRandom sr = new SecureRandom();// 加密随机数生成器
		byte[] cipherByte = null;
		try {
			// 得到加密/解密器
			Cipher cipher = Cipher.getInstance(algorithm);
			// 用指定的密钥和模式初始化Cipher对象
			cipher.init(Cipher.DECRYPT_MODE, key, sr);
			// 对要解密的内容进行编码处理
			cipherByte = cipher.doFinal(hex2byte(date));
		} catch (Exception e) {
			e.printStackTrace();
		}
//		 return byte2hex(cipherByte);
		return new String(cipherByte);
	}
	
	
	public static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1) {
				hs = hs + "0" + stmp;
			} else {
				hs = hs + stmp;
			}
		}
		return hs.toUpperCase();
	}
	
	public static byte[] hex2byte(String hex) {
		byte[] ret = new byte[8];
		byte[] tmp = hex.getBytes();
		
		for (int i = 0; i < 8; i++) {
			ret[i] = uniteBytes(tmp[i * 2], tmp[i * 2 + 1]);
		}
		return ret;
	}
	
	public static byte uniteBytes(byte src0, byte src1) {
		byte _b0 = Byte.decode("0x" + new String(new byte[] { src0 }))
				.byteValue();
		_b0 = (byte) (_b0 << 4);
		byte _b1 = Byte.decode("0x" + new String(new byte[] { src1 }))
				.byteValue();
		byte ret = (byte) (_b0 ^ _b1);
		return ret;
	}
}
