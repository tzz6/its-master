package com.its.web.util;

import java.io.File;
import java.util.List;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

/**
 * 邮件发送
 */
public class MailSpringUtil {

	private JavaMailSender javaMailSender;

	public void setJavaMailSender(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	public JavaMailSender getJavaMailSender() {
		return javaMailSender;
	}

	/**
	 * 简单文本邮件发送
	 * 
	 * @param fromEmail
	 *            发件人邮箱
	 * @param toEmail
	 *            接收人邮箱
	 * @param subject
	 *            标题
	 * @param content
	 *            内容
	 */
	public void send(String fromEmail, String toEmail, String subject, String content) {
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setFrom(fromEmail);
		mail.setTo(toEmail);
		mail.setSubject(subject);
		mail.setText(content);
		javaMailSender.send(mail);
	}

	/**
	 * 邮件发送
	 * 
	 * @param fromEmail
	 *            发件人邮箱
	 * @param toEmails
	 *            接收人邮箱
	 * @param ccEmails
	 *            抄送邮箱
	 * @param subject
	 *            标题
	 * @param content
	 *            内容
	 * @param filePaths
	 *            附件
	 */
	public void send(String fromEmail, List<String> toEmails, List<String> ccEmails, String subject, String content,
			List<String> filePaths) {
		MimeMessage mime = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = null;
		FileSystemResource attachment = null;
		try {
			helper = new MimeMessageHelper(mime, true, "utf-8");
			helper.setFrom(fromEmail);
			if (toEmails != null && toEmails.size() > 0) {// 接收人邮箱
				int size = toEmails.size();
				InternetAddress[] internetAddresses = new InternetAddress[size];
				for (int i = 0; i < size; i++) {
					internetAddresses[i] = new InternetAddress(toEmails.get(i));
				}
				helper.setTo(internetAddresses);
			}
			// 抄送人邮箱
			if (ccEmails != null && ccEmails.size() > 0) {
				int size = ccEmails.size();
				InternetAddress[] internetAddresses = new InternetAddress[size];
				for (int i = 0; i < size; i++) {
					internetAddresses[i] = new InternetAddress(ccEmails.get(i));
				}
				helper.setCc(internetAddresses);
			}
			helper.setSubject(subject);// 标题
			// 需要将附件显示在html中
			helper.setText(content, true);
			for (String filePath : filePaths) {// 附件
				attachment = new FileSystemResource(new File(filePath));
				String fileName = getFileName(filePath);
				helper.addAttachment(MimeUtility.encodeWord(fileName), attachment);// 解决附件中文编码问题
			}
			javaMailSender.send(mime);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** 获取附件名 */
	private String getFileName(String filePath) {
		return filePath.substring(filePath.lastIndexOf(File.separator) + 1, filePath.length());
	}

}
