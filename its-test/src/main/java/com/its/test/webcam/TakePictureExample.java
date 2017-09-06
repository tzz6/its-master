package com.its.test.webcam;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import com.github.sarxos.webcam.Webcam;

/**
 * 2.如何拍照并保存文件
 *
 */
public class TakePictureExample {

	public static void main(String[] args) {

		try {
			// get default webcam and open it
			Webcam webcam = Webcam.getDefault();
			webcam.open();
			
			// get image
			BufferedImage image = webcam.getImage();
			
			// save image to PNG file
			ImageIO.write(image, "PNG", new File("D:\\testxx.png"));
			System.out.println("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
