package com.its.common.utils.poi;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

/**
 * 操作Excel的工具类
 */
public class ExcelUtil {
    /**
     * Excel 97-2003 文件格式 xls
     */
    public static final int EXCEL2003TYPE = 97;
    /**
     * Excel 2007 文件格式 xlsx
     */
    public static final int EXCEL2007TYPE = 2007;

    /**
     * 导出Excel文件
     *
     * @param file     输出文件
     * @param isHeart 表头数据 可以为空
     * @param dataList 所需导出的数据
     * @throws IOException
     */
    public static void writeExcel(File file,  List<Map<String, Object>> dataList,boolean isHeart) throws IOException {
        ByteArrayOutputStream baos = writeExcel(dataList, isHeart);
        if (baos == null) {
            return;
        }
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(baos.toByteArray());
        fos.close();
    }

    /**
     * @param dataList
     * @param isheart  是否输出表头
     * @return
     * @throws IOException
     */
    public static ByteArrayOutputStream writeExcel(List<Map<String, Object>> dataList, boolean isheart) throws IOException {
        if (null == dataList) {
            return null;
        }
        Workbook wb = new HSSFWorkbook();
        CellStyle style = getCellStyle(wb);
        Sheet sheet = wb.createSheet();
        if(dataList.size() > 0){
        	int startRow=0;
        	
        	/**
        	 * 设置Excel表的第一行即表头
        	 */
        	Map<String, Object> maxSize = getMaxSize(dataList);
        	ArrayList<String> headList = new ArrayList<String>(maxSize.keySet());
        	if (isheart) {
        		Row row = sheet.createRow(0);
        		for (int i = 0; i < headList.size(); i++) {
        			Cell headCell = row.createCell(i);
        			headCell.setCellType(Cell.CELL_TYPE_STRING);
        			headCell.setCellStyle(style);//设置表头样式
        			if(i==0){
        				headCell.setCellValue("序号");
        			}else{
        				headCell.setCellValue(String.valueOf(headList.get(i)));
        			}
        		}
        		startRow=1;
        	}
        	for (int i = 0; i < dataList.size(); i++) {
        		Row rowdata = sheet.createRow(i+startRow);//创建数据行
        		Map<String, Object> datas = dataList.get(i);
        		
        		
        		for (int j = 0; j < headList.size(); j++) {
        			String heart = headList.get(j);
        			String strdata = String.valueOf(datas.get(heart));
        			strdata = getValidCellValue(strdata);
        			
        			Cell celldata = rowdata.createCell(j);
        			if (isNum(strdata)) {
        				celldata.setCellType(Cell.CELL_TYPE_NUMERIC);
        				celldata.setCellValue(Double.parseDouble(strdata));
        			}else{
        				celldata.setCellType(Cell.CELL_TYPE_STRING);
        				celldata.setCellValue(strdata);
        			}
        		}
        	}
        }


        ByteArrayOutputStream os = new ByteArrayOutputStream();
        os.flush();
        wb.write(os);
        os.close();
        return os;
    }
    /**
     * @param dataList
     * @param isheart  是否输出表头
     * @param searchMaps 查询条件
     * @return
     * @throws IOException
     */
    public static ByteArrayOutputStream writeExcel(List<Map<String, Object>> dataList, boolean isheart, Map<String, String> searchMaps) throws IOException {
    	if (null == dataList) {
    		return null;
    	}
    	Workbook wb = new HSSFWorkbook();
    	CellStyle style = getCellStyle(wb);
    	Sheet sheet = wb.createSheet();
    	if(dataList.size() > 0){
    		int startRow = exprotSearch(searchMaps, sheet);//导出查询条件
    		
    		/**
    		 * 设置Excel表的第一行即表头
    		 */
    		Map<String, Object> maxSize = getMaxSize(dataList);
    		ArrayList<String> headList = new ArrayList<String>(maxSize.keySet());
    		if (isheart) {
    			Row row = sheet.createRow(startRow);
    			for (int i = 0; i < headList.size(); i++) {
    				Cell headCell = row.createCell(i);
    				headCell.setCellType(Cell.CELL_TYPE_STRING);
    				headCell.setCellStyle(style);//设置表头样式
    				if(i==0){
    					headCell.setCellValue("序号");
    				}else{
    					headCell.setCellValue(String.valueOf(headList.get(i)));
    				}
    			}
    			startRow=startRow+1;
    		}
    		for (int i = 0; i < dataList.size(); i++) {
    			Row rowdata = sheet.createRow(i+startRow);//创建数据行
    			Map<String, Object> datas = dataList.get(i);
    			
    			
    			for (int j = 0; j < headList.size(); j++) {
    				String heart = headList.get(j);
    				String strdata = String.valueOf(datas.get(heart));
    				strdata = getValidCellValue(strdata);
    				
    				Cell celldata = rowdata.createCell(j);
    				if (isNum(strdata)) {
    					celldata.setCellType(Cell.CELL_TYPE_NUMERIC);
    					celldata.setCellValue(Double.parseDouble(strdata));
    				}else{
    					celldata.setCellType(Cell.CELL_TYPE_STRING);
    					celldata.setCellValue(strdata);
    				}
    			}
    		}
    	}
    	
    	
    	ByteArrayOutputStream os = new ByteArrayOutputStream();
    	os.flush();
    	wb.write(os);
    	os.close();
    	return os;
    }

    /**
     * 导出查询条件
     * @param searchMaps
     * @param sheet
     * @return
     */
	private static int exprotSearch(Map<String, String> searchMaps, Sheet sheet) {
		int startRow = 0;
		if(searchMaps != null && searchMaps.size()>0){
			int searchIndex = 0;
			for(Entry<String, String> entry : searchMaps.entrySet()){
				Row row = sheet.createRow(searchIndex);
				String key = entry.getKey();
				String value = entry.getValue();
				Cell headCellKey = row.createCell(0);
				Cell headCellValue = row.createCell(1);
				headCellKey.setCellType(Cell.CELL_TYPE_STRING);
				headCellKey.setCellType(Cell.CELL_TYPE_STRING);
				headCellKey.setCellValue(key);
				headCellValue.setCellValue(value);
				searchIndex ++;
			}
			startRow = searchIndex;
		}
		return startRow;
	}
    
    
    /**
     * @param dataList
     * @param isheart  是否输出表头
     * @return
     * @throws IOException
     */
    public static ByteArrayOutputStream writeCouponBatchGivingExcel(List<Map<String, Object>> dataList, boolean isheart) throws IOException {
        if (null == dataList) {
            return null;
        }
        Workbook wb = new HSSFWorkbook();
        CellStyle style = getCellStyle(wb);
        Sheet sheet = wb.createSheet();
        int startRow=0;

        /**
         * 设置Excel表的第一行即表头
         */
        Map<String, Object> maxSize = getMaxSize(dataList);
        ArrayList<String> headList = new ArrayList<String>(maxSize.keySet());
        if (isheart) {
            Row row = sheet.createRow(0);
            for (int i = 0; i < headList.size(); i++) {
                Cell headCell = row.createCell(i);
                headCell.setCellType(Cell.CELL_TYPE_STRING);
                headCell.setCellStyle(style);//设置表头样式
                headCell.setCellValue(String.valueOf(headList.get(i)));
            }
            startRow=1;
        }
        for (int i = 0; i < dataList.size(); i++) {
            Row rowdata = sheet.createRow(i+startRow);//创建数据行
            Map<String, Object> datas = dataList.get(i);
            

            for (int j = 0; j < headList.size(); j++) {
                String heart = headList.get(j);
                String strdata = String.valueOf(datas.get(heart));
                
                Cell celldata = rowdata.createCell(j);
                if (isNum(strdata)) {
                    celldata.setCellType(Cell.CELL_TYPE_NUMERIC);
                    celldata.setCellValue(Double.parseDouble(strdata));
                }else{
                    celldata.setCellType(Cell.CELL_TYPE_STRING);
                    celldata.setCellValue(strdata);
                }
            }
        }


        ByteArrayOutputStream os = new ByteArrayOutputStream();
        os.flush();
        wb.write(os);
        os.close();
        return os;
    }
   
    /**
     * 生成多个Sheet的xls
     * @param dataList
     * @param isheart
     * @param totalPageSize
     * @return
     * @throws IOException
     */
    public static Workbook writeMoreSheetExcel(List<List<Map<String,Object>>> pagedataList, boolean isheart,int totalPage) throws IOException {
    	if (null == pagedataList) {
    		return null;
    	}
    	Workbook wb = new HSSFWorkbook();
    	CellStyle style = getCellStyle(wb);
    	
    	for(int page =0; page<totalPage; page++){
    		List<Map<String,Object>> dataList = pagedataList.get(page);
    		Sheet sheet = wb.createSheet("sheet"+(page+1));
    		int startRow=0;
    		
    		/**
    		 * 设置Excel表的第一行即表头
    		 */
    		Map<String, Object> maxSize = getMaxSize(dataList);
    		ArrayList<String> headList = new ArrayList<String>(maxSize.keySet());
    		if (isheart) {
    			Row row = sheet.createRow(0);
    			for (int i = 0; i < headList.size(); i++) {
    				Cell headCell = row.createCell(i);
    				headCell.setCellType(Cell.CELL_TYPE_STRING);
        			headCell.setCellStyle(style);//设置表头样式
    				if(i==0){
    					headCell.setCellValue("序号");
    				}else{
    					headCell.setCellValue(String.valueOf(headList.get(i)));
    				}
    			}
    			startRow=1;
    		}
    		for (int i = 0; i < dataList.size(); i++) {
    			Row rowdata = sheet.createRow(i+startRow);//创建数据行
    			Map<String, Object> datas = dataList.get(i);
    			
    			
    			for (int j = 0; j < headList.size(); j++) {
    				String heart = headList.get(j);
    				String strdata = String.valueOf(datas.get(heart));
    				strdata = getValidCellValue(strdata);
    				
    				Cell celldata = rowdata.createCell(j);
    				if (isNum(strdata)) {
    					celldata.setCellType(Cell.CELL_TYPE_NUMERIC);
    					celldata.setCellValue(Double.parseDouble(strdata));
    				}else{
    					celldata.setCellType(Cell.CELL_TYPE_STRING);
    					celldata.setCellValue(strdata);
    				}
    			}
    		}
    	}
    	return wb;
    }
    

    private static String getValidCellValue(String value) {
        if (value.toUpperCase().equals("NULL")){
            value = StringUtils.EMPTY;
        }
        return value;
    }

    /**
     * @param wb
     * @return
     * @Title: getCellStyle
     * @Description: TODO（设置表头样式）
     */
    private static CellStyle getCellStyle(Workbook wb) {
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 12);//设置字体大小
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//加粗
        style.setFillForegroundColor(HSSFColor.LIME.index);// 设置背景色
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setAlignment(HSSFCellStyle.SOLID_FOREGROUND);//让单元格居中
        //style.setWrapText(true);//设置自动换行
        style.setFont(font);
        return style;
    }

    /**
     * 获取单元格数据内容为字符串类型的数据
     *
     * @param cell Excel单元格
     * @return String 单元格数据内容
     */
    @SuppressWarnings("unused")
	public static String getStringCellValue(Cell cell) {
        String strCell = "";
        switch (cell.getCellType()) {
            case HSSFCell.CELL_TYPE_STRING:
                strCell = cell.getStringCellValue();
                break;
            case HSSFCell.CELL_TYPE_NUMERIC:
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    Date date = cell.getDateCellValue();
                    if (date != null) {
                        strCell = new SimpleDateFormat("yyyy-MM-dd").format(date);
                    } else {
                        strCell = "";
                    }
                } else {
                    strCell = new DecimalFormat("0").format(cell
                            .getNumericCellValue());
                }
                break;
            case HSSFCell.CELL_TYPE_BOOLEAN:
                strCell = String.valueOf(cell.getBooleanCellValue());
                break;
            case HSSFCell.CELL_TYPE_BLANK:
                strCell = "";
                break;
            default:
                strCell = "";
                break;
        }
        if (strCell.equals("") || strCell == null) {
            return "";
        }
        if (cell == null) {
            return "";
        }
        return strCell;
    }

    /**
     * \
     * 读取Excel返回二维数组
     *
     * @param file     Excel文件
     * @param startRow
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static String[][] readExcel(File file, int startRow) throws FileNotFoundException, IOException {
        FileInputStream fis = new FileInputStream(file);
        int type = getExcelType(file.getName());
        return readExcel(fis, startRow, type);
    }

    public static int getExcelType(String filename) {
        String fileType = filename.substring(filename.lastIndexOf("."), filename.length());
        int type = -1;
        if (".xls".equals(fileType.trim().toLowerCase())) {
            type = EXCEL2003TYPE;
        } else if (".xlsx".equals(fileType.trim().toLowerCase())) {
            type = EXCEL2007TYPE;
        }
        return type;
    }

    /**
     * 读取excel
     *
     * @param fis
     * @param startRow
     * @param type
     * @return 返回excel数据二维数组
     * @throws IOException
     */
    @SuppressWarnings("resource")
	public static String[][] readExcel(InputStream fis, int startRow, int type) throws IOException {
        List<String[]> result = new ArrayList<String[]>();
        int rowSize = 0;
        BufferedInputStream in = new BufferedInputStream(fis);
        // 打开HSSFWorkbook
        Workbook wb = null;
        if (type == EXCEL2003TYPE) {
            wb = new HSSFWorkbook(in);
        } else {
            wb = new XSSFWorkbook(in);
        }
        for (int sheetIndex = 0; sheetIndex < wb.getNumberOfSheets(); sheetIndex++) {
            Sheet st = wb.getSheetAt(sheetIndex);
            Row row = st.getRow(0);
            if (row == null) {
                continue;
            }
            for (Iterator<Row> it = st.iterator(); it.hasNext(); ) {
                row = it.next();
                String[] values = getRowValue(row);
                if (values.length > 0) {
                    result.add(values);
                }
            }


        }
        in.close();
        String[][] returnArray = new String[result.size()][rowSize];
        for (int i = 0; i < returnArray.length; i++) {
            returnArray[i] = (String[]) result.get(i);
        }
        return returnArray;
    }

    /**
     * 取得某行中各列的值
     *
     * @param row
     */
    public static String[] getRowValue(Row row) {
        Cell cell = null;
        short lastCellNum = row.getLastCellNum();
        String[] values = new String[lastCellNum];
        for (int columnIndex = 0; columnIndex < lastCellNum; columnIndex++) {
            String value = "";
            cell = row.getCell(columnIndex);
            if (cell != null) {
                value = getStringCellValue(cell);
            }
            if (columnIndex == 0 && value.trim().equals("")) {
                break;
            }
            values[columnIndex] = value.trim();
        }
        return values;
    }

    private static Map<String, Object> getMaxSize(List<Map<String, Object>> execute) {
        int index = -1;
        int max=-1;
        for (int i = 0; i < execute.size(); i++) {
            Map<String, Object> map = execute.get(i);
            if (map.size() > max) {
                max=map.size();
                index = i;
            }
        }
        return execute.get(index);
    }
    
    /**
     * 判断一个字符串是不是数字
     * @param str
     * @return
     */
    @SuppressWarnings("unused")
	public static boolean isNum(String str){
        if(str==null||str.isEmpty()||str.length()>11){
            return false;
        }
        try {
            Float aFloat = Float.valueOf(str);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}
