package com.its.test.util;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.its.web.util.MailSpringUtil;




public class MailSpringUtilTest {

	private ApplicationContext ac;
	
	@Before
	public void InitBinder(){
		ac = new ClassPathXmlApplicationContext("spring-mail.xml");
	}

	@Test
	public void testSend() {
		MailSpringUtil mailUtil = (MailSpringUtil) ac.getBean("mailSpringUtil");
		// 简单邮件
//		mailUtil.send("1094622198@qq.com", "1094622199@qq.com", "测试SpringMail", "HelloWorld");
		// 附件
		List<String> toEmails = new ArrayList<String>();
		toEmails.add("1094622198@qq.com");
		List<String> ccEmails = new ArrayList<String>();
		ccEmails.add("1094622198@qq.com");
		List<String> filePaths = new ArrayList<String>();
		filePaths.add("D:/1.jpg");
		filePaths.add("D:/1.jpg");
		mailUtil.send("1094622198@qq.com", toEmails, ccEmails, "测试SpringMail", "HelloWorld", filePaths);
		System.out.println("发送成功");
	}
}
