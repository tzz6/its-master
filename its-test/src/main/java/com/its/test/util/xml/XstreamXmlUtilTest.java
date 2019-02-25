package com.its.test.util.xml;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.its.common.utils.xml.XstreamXmlUtil;
import com.its.test.util.xml.entity.Role;
import com.its.test.util.xml.entity.User;

/**
 * 
 * @author tzz
 */
public class XstreamXmlUtilTest {

	@Test
	public void testBeanToXml() {
		try {
			User user = new User();
			user.setId(1);
			user.setName("Test");
			user.setAge(20);
			user.setSex("1");
			user.setBirthday(new Date());
			Role role = new Role();
			role.setId(1);
			role.setName("角色1");
			Role role2 = new Role();
			role2.setId(2);
			role2.setName("角色2");
			List<Role> roles = new ArrayList<Role>();
			roles.add(role);
			roles.add(role2);
			user.setRoles(roles);
			String xml = XstreamXmlUtil.beanToXml(user);
			System.out.println(xml);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testXmlToBean() {
		String xml = 
		  "<user id='1'>"+
			  "<name>Test</name>"+
			  "<sex>1</sex>"+
			  "<birthday>2016-03-10 16:12:46</birthday>"+
			  "<roles>"+
			    "<id>1</id>"+
			    "<name>角色1</name>"+
			  "</roles>"+
			  "<roles>"+
			    "<id>2</id>"+
			    "<name>角色2</name>"+
			  "</roles>"+
		   "</user>";
		User user = XstreamXmlUtil.xmlToBean(xml, User.class);
		System.out.println(user.getId() + "--" + user.getName());
		List<Role> roles = user.getRoles();
		for (Role r : roles) {
			System.out.println(r.getName());
		}
	}
}
