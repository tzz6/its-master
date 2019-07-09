package com.its.test.webcam;

import java.awt.BorderLayout;
import java.awt.Dimension;
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

/**
 * 
 * @author tzz
 * @工号:
 * @date 2019/07/09
 * @Introduce:Webcam拍照
 */
public class WebcamPanelExample {

    /** 1/2.检测推送并拍照保存文件 */
    public static void test1() {
        // get default webcam and open it
        // 检测摄像头
        Webcam webcam = Webcam.getDefault();
        if (webcam != null) {
            System.out.println("Webcam: " + webcam.getName());
        } else {
            System.out.println("No webcam detected");
        }
        // 拍照--保存图片
        webcam.open();

        // get image
        BufferedImage image = webcam.getImage();

        // save image to PNG file
        writeImage(image, "PNG", "D:\\Test\\webcam\\test1.png");
        System.out.println("success");
    }

    /** 3.网络摄像头在Swing面板显示图象(基本)-运行自动拍一张照 */
    public static void test2() {

        // 检测摄像头
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

        // 拍照--保存图片
        webcam.open();

        // get image
        BufferedImage image = webcam.getImage();

        // save image to PNG file
        writeImage(image, "PNG", "D:\\Test\\webcam\\test1.png");
        System.out.println("success");
    }

    /** 3.网络摄像头在Swing面板显示图象(基本)-点击按钮拍照 */
    public static void test3() {
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
        // 拍照--保存图片
        photegraph.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                webcam.open();
                BufferedImage image = webcam.getImage();
                SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
                String fileName = format.format(new Date()) + ".png";
                writeImage(image, "PNG", "D:\\Test\\webcam\\" + fileName);
                System.out.println("success");
            }
        });
    }

    /** 6.如何配置捕获分辨率 */
    public static void customResolution() {
        /**
         * When you set custom resolutions you have to be sure that your webcam device will handle them!
         */

        //@formatter:off
        Dimension[] nonStandardResolutions = new Dimension[] {
            WebcamResolution.PAL.getSize(),
            WebcamResolution.HD720.getSize(),
            new Dimension(2000, 1000),
            new Dimension(1000, 500),
        };
        //@formatter:on

        // your camera have to support HD720p to run this code
        Webcam webcam = Webcam.getDefault();
        webcam.setCustomViewSizes(nonStandardResolutions);
        webcam.setViewSize(WebcamResolution.HD720.getSize());
        webcam.open();

        BufferedImage image = webcam.getImage();

        System.out.println(image.getWidth() + "x" + image.getHeight());
    }

    /** 保存图片 */
    private static void writeImage(BufferedImage image, String postfix, String path) {
        try {
            ImageIO.write(image, postfix, new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // 1/2.检测推送并拍照保存文件
        // test1();
        // 网络摄像头在Swing面板显示图象(基本)-运行自动拍一张照
        // test2();
        // 网络摄像头在Swing面板显示图象(基本)-点击按钮拍照
         test3();
        // 如何配置捕获分辨率
        // customResolution();
    }
}
