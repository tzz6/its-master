package com.its.test.util;

import com.its.common.utils.zip.ChZipUtils;

/**
 * 
 * @author tzz
 */
public class ChZipUtilsTest {

	public static void main(String[] args) throws Exception {
//		String sourceFolder = "D:/test/1.txt";
//		String sourceFolder = "D:/test/中文名.txt";
//		String sourceFolder = "D:/test/cms";
		String zipFilePath = "D:/test/zip/压缩多个文件.zip";
		String unZipPath = "D:/test/unzip";
//		CHZipUtils.zip(sourceFolder, zipFilePath);
		ChZipUtils.unZip(zipFilePath, unZipPath);
		System.out.println("********执行成功**********");
	}

}
