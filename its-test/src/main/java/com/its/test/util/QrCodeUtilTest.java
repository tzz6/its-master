package com.its.test.util;

import org.junit.Test;

import com.its.common.utils.qrcode.QrCodeUtil;

/**
 * 二维码生成、解析测试
 * @author tzz
 */
public class QrCodeUtilTest {

	/**生成二维码图片*/
	@Test
	public void testGenerateQRCode() {
		QrCodeUtil.generateQRCode("http://localhost:8080/web-demo", "D:/test.jpg");
	}

	/**解析二维码图片*/
	@Test
	public void testAnalysisQRCode() {
		String filePath = "D:/test.jpg";
		String url = QrCodeUtil.analysisQRCode(filePath);
		System.out.println(url);
	}

}
