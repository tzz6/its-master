package com.its.test.util;

import java.util.List;

import org.junit.Test;

import com.its.common.utils.bean.BeanUtil;
import com.its.model.mybatis.dao.domain.SysUser;

public class BeanUtilTest {

	@Test
	public void testGetExcelFormList() {
		try {
			String readPath2007 = "d:\\1.xlsx";
			BeanUtil<SysUser> poiBeanUtil = new BeanUtil<SysUser>();
			List<SysUser> sysUsers = poiBeanUtil.getExcelPathFormList(readPath2007, SysUser.class);
			for (SysUser sysUser : sysUsers) {
				System.out.println(sysUser.getStCode() + "--" + sysUser.getStName() + "--" + sysUser.getStPassword());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}