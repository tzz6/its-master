package com.its.test.webcam;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;

/***
 * 3.如何从网络摄像头在Swing面板显示图象(基本)
 *
 */
public class WebcamPanelExample {

	public static void main(String[] args) throws InterruptedException, IOException {

		Webcam webcam = Webcam.getDefault();
		webcam.setViewSize(WebcamResolution.VGA.getSize());

		WebcamPanel panel = new WebcamPanel(webcam);
		panel.setFPSDisplayed(true);
		panel.setDisplayDebugInfo(true);
		panel.setImageSizeDisplayed(true);
		panel.setMirrored(true);

		JFrame window = new JFrame("Test webcam panel");
		window.add(panel);
		window.setResizable(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.pack();
		window.setVisible(true);
		
		//拍照--保存图片
		webcam.open();
		
		// get image
		BufferedImage image = webcam.getImage();
		
		// save image to PNG file
		ImageIO.write(image, "PNG", new File("D:\\Test\\webcam\\test1.png"));
		System.out.println("success");
	}
}
