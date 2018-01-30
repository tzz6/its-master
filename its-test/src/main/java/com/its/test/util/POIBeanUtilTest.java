package com.its.test.util;

import java.util.List;

import org.apache.poi.ss.usermodel.Sheet;
import org.junit.Test;

import com.its.common.utils.poi.POIBeanUtil;
import com.its.common.utils.poi.POIUtil;
import com.its.model.mybatis.dao.domain.SysUser;

public class POIBeanUtilTest {

	@Test
	public void testGetFormList() {
		try {
			String readPath2007 = "d:\\1.xlsx";
			POIBeanUtil<SysUser> poiBeanUtil = new POIBeanUtil<SysUser>();
			Sheet sheet = POIUtil.getSheet(readPath2007, 0);
			List<SysUser> sysUsers = poiBeanUtil.getFormList(sheet, SysUser.class);
			for (SysUser sysUser : sysUsers) {
				System.out.println(sysUser.getStCode() + "--" + sysUser.getStName() + "--" + sysUser.getStPassword());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}