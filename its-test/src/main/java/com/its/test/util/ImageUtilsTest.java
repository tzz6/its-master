package com.its.test.util;

import java.awt.Color;
import java.awt.Font;

import org.junit.Test;

import com.its.common.utils.ImageUtils;


public class ImageUtilsTest {

	/**测试图片合并*/
	@Test
    public void testMergeImage(){
		ImageUtils.mergeImage("D:/1.jpg", "D:/2.png", "D:/3.png");
	}
	/**测试添加图片水印*/
	@Test
	public void testAddImageWeatermark(){
		ImageUtils.addImageWeatermark("D:/imageWeatermark.jpg", "D:/test.jpg", 10, 10, 0f);
	}
	/**测试添加文字水印*/
	@Test
	public void testAddTextWeatermark(){
		ImageUtils.addTextWeatermark("D:/textWeatermark.jpg", "添加文本水印", "宋体", Font.BOLD | Font.ITALIC, 20, Color.BLACK, 0, 0, 8f);
	}
	/**图片缩放*/
	@Test
	public void testResize(){
		ImageUtils.resize("D:/resize1.jpg", 1000, 500, true);
	}
	/**将网页转化为图片*/
	@Test
	public void testHtmlToImage(){
		ImageUtils.htmlToImage("https://baidu.com", "D:/htmlToImage.jpg");
	}
}
