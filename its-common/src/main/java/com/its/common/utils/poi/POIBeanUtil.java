package com.its.common.utils.poi;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.its.model.annotation.Import;

/**
 * 根据Sheet构造Bean数据
 */
public class POIBeanUtil<T> {

	private static final Log log = LogFactory.getLog(POIBeanUtil.class);

	/**
	 * 根据Sheet构造Bean数据
	 * 
	 * @param sheet
	 * @param clazz
	 * @return
	 */
	public List<T> getFormList(Sheet sheet, Class<T> clazz) {
		List<T> formList = null;
		try {
			int rownum = sheet.getLastRowNum();
			formList = new ArrayList<T>();
			Row row = null;
			for (int i = 1; i <= rownum; i++) {
				row = sheet.getRow(i);
				if (row == null || isNullRow(row)) {
					continue;
				}
				formList.add(getForm(clazz, row, i));// 将Bean添加至List
			}
		} catch (Exception e) {
			log.error(e);
		}
		return formList;
	}

	/**
	 * Bean转换
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
		Field[] fields = imptClass.getDeclaredFields();// 获取该类型声明的成员
		for (Field field : fields) {
			field.setAccessible(true);
			Import annotation = field.getAnnotation(Import.class);
			if (annotation == null)// 没有注解的字段，跳过
				continue;
			Cell cell = row.getCell(annotation.columnIndex());// 根据注解获取单元格位置
			if (cell != null) {
				field.set(entity, POIUtil.getCellValue(cell));// 设置数据
			}
			field.setAccessible(false);
		}
		return entity;
	}

	/**
	 * 判断是否是空行
	 * 
	 * @param row
	 * @return
	 */
	private static boolean isNullRow(Row row) {
		boolean isNull = true;
		Iterator<Cell> it = row.cellIterator();
		while (it.hasNext()) {
			Cell cl = it.next();
			if (null != cl && !"".equals(cl.toString())) {
				isNull = false;
				break;
			}
		}
		return isNull;
	}
}