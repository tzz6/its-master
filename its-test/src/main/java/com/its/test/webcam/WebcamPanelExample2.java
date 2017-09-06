package com.its.test.webcam;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;

/***
 * 3.如何从网络摄像头在Swing面板显示图象(基本)
 *
 */
public class WebcamPanelExample2 {

	public static String photograph(String savePath) {
		Webcam webcam = Webcam.getDefault();
		webcam.setViewSize(WebcamResolution.VGA.getSize());

		WebcamPanel panel = new WebcamPanel(webcam);
		panel.setFPSDisplayed(true);
		panel.setDisplayDebugInfo(true);
		panel.setImageSizeDisplayed(true);
		panel.setMirrored(true);

		JFrame window = new JFrame("Test webcam panel");
		JButton photegraph = new JButton("拍照");
		window.add(photegraph);
		
		window.add(panel, BorderLayout.NORTH);
		window.setResizable(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.pack();
		window.setVisible(true);
		photegraph.addActionListener(new ActionListener() {// 拍照--保存图片
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					webcam.open();
					BufferedImage image = webcam.getImage();
					SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
					String fileName = format.format(new Date()) + ".png";
					ImageIO.write(image, "PNG", new File(savePath + fileName));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				System.out.println("success");
			}
		});
		return "";
	}

	public static void main(String[] args) throws InterruptedException, IOException {
		photograph("D:\\Test\\webcam\\");
	}
}
