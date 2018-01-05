package com.its.common.crypto.symmetry;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 对称加密(AES/DES/3DES) 推荐使用AES
 */
public class SymmetryUtil {
	public static String ALGORITHM_AES = "AES";
	public static String ALGORITHM_DES = "DES";
	public static String ALGORITHM_3DES = "DESede";
	public static String KEY = "QWE!@#123qwe123@*()342%";

	/**
	 * 创建密钥key对象
	 * 
	 * @param algorithm
	 * @param keysize
	 * @param key
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static SecretKey createKey(String algorithm, int keysize, String key) {
		KeyGenerator keyGenerator = null;
		SecretKey secretKey = null;
		try {
			keyGenerator = KeyGenerator.getInstance(algorithm);
			keyGenerator.init(keysize, new SecureRandom(key.getBytes()));
			secretKey = keyGenerator.generateKey();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return secretKey;
	}

	public static byte[] encrypt(String algorithm, int keysize, String key, byte[] data) {
		try {
			SecretKey secretKey = createKey(algorithm, keysize, key);
			Cipher cipher = Cipher.getInstance(algorithm);
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			byte[] encryptByte = cipher.doFinal(data);
			return encryptByte;
		} catch (Exception e) {
			System.out.println("exception:" + e.toString());
		}
		return null;
	}

	public static byte[] decrypt(String algorithm, int keysize, String key, byte[] data) {
		try {
			SecretKey secretKey = createKey(algorithm, keysize, key);
			Cipher cipher = Cipher.getInstance(algorithm);
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			byte[] decryptByte = cipher.doFinal(data);
			return decryptByte;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String encrypt(String algorithm, int keysize, String key, String data) {
		byte[] encryptByte = encrypt(algorithm, keysize, key, data.getBytes());
		String encryptStr = encryptBASE64(encryptByte);
		return encryptStr;
	}

	public static String decrypt(String algorithm, int keysize, String key, String data) {
		byte[] decryptByte = decrypt(algorithm, keysize, key, decryptBASE64(data));
		String decryptStr = new String(decryptByte);
		return decryptStr;
	}

	/** 字节转字符 */
	public static String byteToHexString(byte[] bytes) {
		StringBuffer sb = new StringBuffer(bytes.length);
		String sTemp;
		for (int i = 0; i < bytes.length; i++) {
			sTemp = Integer.toHexString(0xFF & bytes[i]);
			if (sTemp.length() < 2) {
				sb.append(0);
			}
			sb.append(sTemp.toUpperCase());
		}
		return sb.toString();
	}

	/** 字节转字符 */
	public static String encryptBASE64(byte[] data) {
		return new BASE64Encoder().encodeBuffer(data);
	}

	/** 字符转字节 */
	public static byte[] decryptBASE64(String data) {
		try {
			return new BASE64Decoder().decodeBuffer(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
