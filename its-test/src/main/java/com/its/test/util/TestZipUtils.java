package com.its.test.util;

import com.its.common.utils.zip.ZipUtils;

public class TestZipUtils {

	public static void main(String[] args) {
//		ZipUtils.ZipFile("D:/test/1.txt", "D:/test/zip/压缩单个文件.zip");
//		ZipUtils.ZipContraFile("D:/test/zip/压缩单个文件.zip","D:/test/zip/un.zip", "1.txt");
//		ZipUtils.ZipFile("D:/test/中文名.txt", "D:/test/zip/压缩单个文件.zip");
//		ZipUtils.ZipContraMultiFile("D:/test/zip/压缩单个文件.zip","D:/test/unzip");
		ZipUtils.ZipFile("D:/test/zip", "D:/test/zip/压缩多个文件.zip");
//		ZipUtils.ZipContraMultiFile("D:/test/zip/压缩多个文件.zip","D:/test/unzip");
		System.out.println("********执行成功**********");
	}

}
