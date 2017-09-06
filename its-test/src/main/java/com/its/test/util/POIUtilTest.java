package com.its.test.util;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;

import com.its.common.utils.poi.POIUtil;


public class POIUtilTest {

	@Test
	public void testRead(){
		String readPath2003 = "d:\\2.xls";
		String readPath2007 = "d:\\1.xlsx";
		Map<String, List<String>> maps = POIUtil.read(readPath2007);
		for (Map.Entry<String, List<String>> map : maps.entrySet()) {
			System.out.println("行号：" + map.getKey());
			for (String value : map.getValue()) {
				System.out.println(value);
			}
		}
		Map<String, List<String>> maps2003 = POIUtil.read(readPath2003);
		for (Map.Entry<String, List<String>> map : maps2003.entrySet()) {
			System.out.println("行号：" + map.getKey());
			for (String value : map.getValue()) {
				System.out.println(value);
			}
		}
		
	}
	@Test
	public void testWrite(){
		String readPath2007 = "d:\\1.xlsx";
		try {
			Map<String, List<String>> maps = POIUtil.read(readPath2007);
			
			String writerPath2003 = "d:\\3.xls";
			String writerPath2007 = "d:\\4.xlsx";
			String suffix = writerPath2003.substring(writerPath2003.lastIndexOf(".") + 1, writerPath2003.length());
			Workbook wb = POIUtil.writer(suffix,maps);
			// 创建文件流
			OutputStream stream = new FileOutputStream(writerPath2007);
			// 写入数据
			wb.write(stream);
			// 关闭文件流
			stream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
