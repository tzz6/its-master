package com.its.test.webcam;

import com.github.sarxos.webcam.Webcam;
/**
 * 1.如何检测摄像头
 *
 */
public class DetectWebcamExample {

	public static void main(String[] args) {
		Webcam webcam = Webcam.getDefault();
		if (webcam != null) {
			System.out.println("Webcam: " + webcam.getName());
		} else {
			System.out.println("No webcam detected");
		}
	}
}
