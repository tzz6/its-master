package com.its.common.utils.poi;

import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.DateUtil;

/**
 * POIUtil
 */
public class POIUtil {

	/** Excel导入 */
	public static Map<String, List<String>> read(String path) {
		Map<String, List<String>> maps = new HashMap<String, List<String>>();
		String suffix = path.substring(path.lastIndexOf(".") + 1, path.length());
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(path);
			Workbook wb = null;
			if (suffix.equals("xls")) {
				wb = new HSSFWorkbook(inputStream);
			} else if (suffix.equals("xlsx")) {
				wb = new XSSFWorkbook(inputStream);
			} else {
				System.out.println("您输入的excel格式不正确");
			}
			Sheet sheet = wb.getSheetAt(0);
			int rowsNum = sheet.getPhysicalNumberOfRows();
			System.out.println("excel总行数：" + rowsNum);
			List<String> list = null;
			for (Row row : sheet) {
				list = new ArrayList<String>();
				for (Cell cell : row) {
					list.add(getCellValue(cell));
				}
				maps.put(row.getRowNum() + "", list);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return maps;
	}

	/** 获取单元格数据 */
	public static String getCellValue(Cell cell) {
		String value = "";
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_STRING:
			value = cell.getStringCellValue();
			break;
		case Cell.CELL_TYPE_NUMERIC:
			if (DateUtil.isCellDateFormatted(cell)) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				value = sdf.format(cell.getDateCellValue());
			} else {
				value = String.valueOf(cell.getNumericCellValue());
			}
			break;
		case Cell.CELL_TYPE_BOOLEAN:
			value = String.valueOf(cell.getBooleanCellValue());
			break;
		case Cell.CELL_TYPE_BLANK:
			value = "";
			break;
		default:
			break;
		}
		return value;
	}

	/** Excel导出 */
	public static Workbook writer(String fileType, Map<String, List<String>> maps) {
		// 创建工作文档对象
		Workbook wb = null;
		try {
			if (fileType.equals("xls")) {
				wb = new HSSFWorkbook();
			} else if (fileType.equals("xlsx")) {
				wb = new XSSFWorkbook();
			} else {
				System.out.println("您的文档格式不正确！");
			}
			// 创建sheet对象
			Sheet sheet1 = (Sheet) wb.createSheet("sheet1");
			for (Map.Entry<String, List<String>> map : maps.entrySet()) {// 循环写入行数据
				int rowNum = Integer.parseInt(map.getKey());
				System.out.println("行号：" + rowNum);
				Row row = (Row) sheet1.createRow(rowNum);
				List<String> list = map.getValue();
				for (int i = 0; i < list.size(); i++) {// 循环写入列数据
					Cell cell = row.createCell(i);
					cell.setCellValue(list.get(i));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return wb;
	}
}
