package com.its.web.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.its.common.utils.Constants;
import com.its.common.utils.ImportError;
import com.its.model.annotation.Import;

/**
 * 上传Excel解析辅助类
 * 
 * @author 01115486
 *
 * @param <T>
 */
public class UploadUtil<T> {

	public static Logger logger = Logger.getLogger(UploadUtil.class);

	/**
	 * 从Excel的sheet 也中获取数据
	 * 
	 * @param sheet
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> getFormList(Sheet sheet, Class<T> clazz) {
		int iRownum;
		List<T> formList = new ArrayList<T>();
		iRownum = sheet.getLastRowNum();
		T formRow = null;
		Row row;
		for (int i = 1; i <= iRownum; i++) {
			row = sheet.getRow(i);
			if (row == null) {
				continue;
			}
			if (isNullRow(row)) {
				continue;
			}
			try {
				formRow = clazz.newInstance();
				formRow = (T) getForm(formRow.getClass(), row, i);
			} catch (Exception e) {
				logger.error(e);
			}
			formList.add(formRow);
		}
		return formList;
	}

	/**
	 * 实体转换
	 * 
	 * @param imptClass
	 * @param row
	 * @param rowIndex
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("hiding")
	public <T> T getForm(Class<T> imptClass, Row row, int rowIndex) throws Exception {

		T entity = imptClass.newInstance();
		Field[] fields = null;
		// 获取该类型声明的成员
		Field[] fieldself = imptClass.getDeclaredFields();
		Field[] fieldSuper = imptClass.getSuperclass().getDeclaredFields();
		fields = new Field[fieldself.length + fieldSuper.length];
		for (int i = 0; i < fieldself.length; i++) {
			fields[i] = fieldself[i];
		}
		for (int i = 0; i < fieldSuper.length; i++) {
			fields[fieldself.length + i] = fieldSuper[i];
		}

		for (Field field : fields) {
			field.setAccessible(true);
			// 设置rowNum
			if (field.getName().equals("rowNum")) {
				field.set(entity, rowIndex + 1);
				continue;
			}
			Import annotation = field.getAnnotation(Import.class);
			// 没有注解的字段，跳过
			if (annotation == null)
				continue;
			Cell data = row.getCell(annotation.columnIndex());
			if (data != null) {
				// Excel文件中某一单元格中的数据为数字,例如12345678910123.
				// 正常读取的话,POI需要用getNumericCellValue()来获得值,这样一来取得的值会是以科学技术法表示的一串数值.
				// 如果我们想要获取单元格中的原样数值的话
				data.setCellType(HSSFCell.CELL_TYPE_STRING);
				String cellString = data.getStringCellValue();
				field.set(entity, cellString);
			}
			field.setAccessible(false);
		}
		return entity;
	}

	/**
	 * 查体Excel表头
	 * 
	 * @param imptClass
	 * @param row
	 * @param lang
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public ImportError checkHead(Class imptClass, Row row, String lang) throws Exception {
		ImportError errorForm = new ImportError();
		String description = "";
		errorForm.setRowNum(1 + "");
		StringBuffer buffer = new StringBuffer();
		Field[] fields = imptClass.getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			Import annotation = field.getAnnotation(Import.class);
			if (annotation == null)
				continue;
			if (!StringUtils.isEmpty(annotation.description())) {
				description = ResourceBundleHelper.get(lang, annotation.description());
			}

			Cell data = row.getCell(annotation.columnIndex());
			String cellString = data.getStringCellValue();
			if (StringUtils.isBlank(cellString) || !cellString.equalsIgnoreCase(description)) {
				buffer.append(ResourceBundleHelper.get(lang, Constants.Excel.EXCEL_HEADER_COLUMN)
						+ (annotation.columnIndex() + 1)
						+ ResourceBundleHelper.get(lang, Constants.Excel.EXCEL_HEADER_SHOULD) + ":" + description);
			}
			field.setAccessible(false);
		}

		if (StringUtils.isBlank(buffer.toString())) {
			return null;
		}
		errorForm.setErrorInfo(buffer.toString());
		return errorForm;
	}

	/**
	 * 查体Excel表头
	 * 
	 * @param imptClass
	 * @param headers
	 * @param lang
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public ImportError checkHead(Class imptClass, List<String> headers, String lang) throws Exception {
		ImportError errorForm = new ImportError();
		String description = "";
		errorForm.setRowNum(1 + "");
		StringBuffer buffer = new StringBuffer();
		Field[] fields = imptClass.getDeclaredFields();
		int index = 0;
		for (Field field : fields) {
			field.setAccessible(true);
			Import annotation = field.getAnnotation(Import.class);
			if (annotation == null)
				continue;
			String anDescription = annotation.description();
			if (!StringUtils.isEmpty(anDescription)) {
				description = ResourceBundleHelper.get(lang, anDescription);
			}

			if (StringUtils.isBlank(headers.get(index)) || !headers.get(index).equalsIgnoreCase(description)) {
				buffer.append(ResourceBundleHelper.get(lang, Constants.Excel.EXCEL_HEADER_COLUMN)
						+ (annotation.columnIndex() + 1)
						+ ResourceBundleHelper.get(lang, Constants.Excel.EXCEL_HEADER_SHOULD) + ":" + description);
			}
			field.setAccessible(false);
			index++;
		}

		if (StringUtils.isBlank(buffer.toString())) {
			return null;
		}
		errorForm.setErrorInfo(buffer.toString());
		return errorForm;
	}

	/**
	 * 判断是否是空行
	 * 
	 * @param row
	 * @return
	 */
	private static boolean isNullRow(Row row) {
		boolean isnull = true;
		Iterator<Cell> it = row.cellIterator();
		while (it.hasNext()) {
			Cell cl = it.next();
			if (null != cl && !"".equals(cl.toString())) {
				isnull = false;
				break;
			}
		}
		return isnull;
	}
}