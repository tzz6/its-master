package com.its.common.utils.bean;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.its.common.utils.poi.PoiUtil;
import com.its.model.annotation.Import;

/**
 * Bean数据处理
 * 
 * @author tzz
 */
public class BeanUtil<T> {

	private static final Log log = LogFactory.getLog(BeanUtil.class);

	/**
	 * 根据Excel的Sheet构造Bean数据
	 * 
	 * @param sheet
	 * @param clazz
	 * @return
	 */
	public List<T> getExcelPathFormList(String path, Class<T> clazz) {
		List<T> formList = null;
		try {
			Sheet sheet = PoiUtil.getSheet(path, 0);
			formList = getExcelSheetFormList(sheet, clazz);
		} catch (Exception e) {
			log.error(e);
		}
		return formList;
	}
	
	/**
	 * 根据Excel的Sheet构造Bean数据
	 * 
	 * @param sheet
	 * @param clazz
	 * @return
	 */
	public List<T> getExcelSheetFormList(Sheet sheet, Class<T> clazz) {
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
				// 将Bean添加至List
				formList.add(getForm(clazz, row, i));
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
		// 获取该类型声明的成员
		Field[] fields = imptClass.getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			Import annotation = field.getAnnotation(Import.class);
			if (annotation == null){
			    // 没有注解的字段，跳过
			    continue;
			}
			// 根据注解获取单元格位置
			Cell cell = row.getCell(annotation.columnIndex());
			if (cell != null) {
			    // 设置数据
				field.set(entity, PoiUtil.getCellValue(cell));
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