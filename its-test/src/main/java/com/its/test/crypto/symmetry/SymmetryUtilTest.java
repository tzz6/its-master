package com.its.test.crypto.symmetry;

import org.junit.Test;

import com.its.common.crypto.symmetry.SymmetryUtil;

/**
 * 
 * @author tzz
 */
public class SymmetryUtilTest {

	/** 将字符加密后为字节--再将字节解密为字符 */
	@Test
	public void testByte() {
		testAES();
		testDES();
		test3DES();
	}

	public void testAES() {
		String data = "Tzz123456~!@#$%^&*()_+-={}|[]:'<>?,./asdkdk";
		System.out.println("AES");
		System.out.println("加密前：" + data);
		byte[] encrypted = SymmetryUtil.encrypt(SymmetryUtil.ALGORITHM_AES, 128, SymmetryUtil.KEY, data.getBytes());
		System.out.println("加密后：" + SymmetryUtil.byteToHexString(encrypted));
		byte[] decrypted = SymmetryUtil.decrypt(SymmetryUtil.ALGORITHM_AES, 128, SymmetryUtil.KEY, encrypted);
		System.out.println("解密后：" + new String(decrypted) + "\n");
	}

	public void testDES() {
		String data = "PLaa456963~!@#$%^&*()_+-={}|[]:'<>?,./asdkdk";
		System.out.println("DES");
		System.out.println("加密前：" + data);
		byte[] encrypted = SymmetryUtil.encrypt(SymmetryUtil.ALGORITHM_DES, 56, SymmetryUtil.KEY, data.getBytes());
		System.out.println("加密后：" + SymmetryUtil.byteToHexString(encrypted));
		byte[] decrypted = SymmetryUtil.decrypt(SymmetryUtil.ALGORITHM_DES, 56, SymmetryUtil.KEY, encrypted);
		System.out.println("解密后：" + new String(decrypted) + "\n");
	}

	public void test3DES() {
		String data = "Tzz123456~!@#$%^&*()_+-={}|[]:'<>?,./asdkdk";
		System.out.println("3DES");
		System.out.println("加密前：" + data);
		byte[] encrypted = SymmetryUtil.encrypt(SymmetryUtil.ALGORITHM_3DES, 168, SymmetryUtil.KEY, data.getBytes());
		System.out.println("加密后：" + SymmetryUtil.byteToHexString(encrypted));
		byte[] decrypted = SymmetryUtil.decrypt(SymmetryUtil.ALGORITHM_3DES, 168, SymmetryUtil.KEY, encrypted);
		System.out.println("解密后：" + new String(decrypted));
	}

	/** 将字符加密后为字符--再将字符解密为字符 */
	@Test
	public void testStr() {
		testAESToStr();
	}

	public void testAESToStr() {
		String data = "Tzz123456~!@#$%^&*()_+-={}|[]:'<>?,./asdkdk";
		System.out.println("AES");
		System.out.println("加密前：" + data);
		String encrypted = SymmetryUtil.encrypt(SymmetryUtil.ALGORITHM_AES, 128, SymmetryUtil.KEY, data);
		System.out.println("加密后：" + encrypted);
		String decrypted = SymmetryUtil.decrypt(SymmetryUtil.ALGORITHM_AES, 128, SymmetryUtil.KEY, encrypted);
		System.out.println("解密后：" + decrypted + "\n");
	}

}
