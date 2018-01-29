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
	public void testRead() {
		String readPath2003 = "d:\\2.xls";
		String readPath2007 = "d:\\1.xlsx";
		Map<String, List<String>> maps = POIUtil.read(readPath2007);
		System.out.println("ReadExcel2003");
		for (Map.Entry<String, List<String>> map : maps.entrySet()) {
			for (String value : map.getValue()) {
				System.out.print(value + "\t");
			}
		}
		System.out.println("\n");
		Map<String, List<String>> maps2003 = POIUtil.read(readPath2003);
		System.out.println("ReadExcel2007");
		for (Map.Entry<String, List<String>> map : maps2003.entrySet()) {
			for (String value : map.getValue()) {
				System.out.print(value + "\t");
			}
		}

	}

	@Test
	public void testWrite() {
		String readPath2007 = "d:\\1.xlsx";
		try {
			Map<String, List<String>> maps = POIUtil.read(readPath2007);
			System.out.println("WriteExcel2003");
			String writerPath2003 = "d:\\3.xls";
			String suffix = writerPath2003.substring(writerPath2003.lastIndexOf(".") + 1);
			Workbook wb = POIUtil.writer(suffix, maps);
			// 创建文件流
			OutputStream stream = new FileOutputStream(writerPath2003);
			// 写入数据
			wb.write(stream);
			wb.close();
			// 关闭文件流
			stream.close();

			System.out.println("WriteExcel2007");
			String writerPath2007 = "d:\\4.xlsx";
			String suffix2007 = writerPath2007.substring(writerPath2007.lastIndexOf(".") + 1);
			Workbook wb2007 = POIUtil.writer(suffix2007, maps);
			// 创建文件流
			OutputStream stream2007 = new FileOutputStream(writerPath2007);
			// 写入数据
			wb2007.write(stream2007);
			wb2007.close();
			// 关闭文件流
			stream2007.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
