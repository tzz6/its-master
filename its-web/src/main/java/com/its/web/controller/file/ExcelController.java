package com.its.web.controller.file;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.its.common.utils.Constants;
import com.its.common.utils.ImportError;
import com.its.common.utils.ImportResult;
import com.its.model.mybatis.dao.domain.SysUser;
import com.its.web.controller.login.BaseController;
import com.its.web.util.DBHelper;
import com.its.web.util.FileUtil;
import com.its.web.util.ImportExcelHandler;
import com.its.web.util.ResourceBundleHelper;
import com.its.web.util.UserSession;

@Controller
@RequestMapping("/excel")
public class ExcelController extends BaseController {

	private static final Log log = LogFactory.getLog(ExcelController.class);

	/**
	 * Excel超大数据读取，抽象Excel2007读取器 excel2007的底层数据结构是xml文件，采用SAX的事件驱动的方法解析
	 * xml，需要继承DefaultHandler，在遇到文件内容时，事件会触发，这种做法可以大大降低 内存的耗费，特别使用于大数据量的文件。
	 *
	 * @param request
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/import")
	public ImportResult saxImport(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("imptFile") CommonsMultipartFile file, ModelMap model) {
		long start = System.currentTimeMillis();
		SysUser sysUser = UserSession.getUser();
		String lang = sysUser.getLanguage();
		ImportResult msg = null;// 导入的结果信息
		int count = 0;// 成功数量
		List<ImportError> errors = new ArrayList<ImportError>();
		List<SysUser> list = null;
		File targetFile = null;
		ImportExcelHandler excelReader = null;
		try {
			// 1.将上传的文件到本地
			String savePath = FileUtil.getPath(request, FileUtil.FILE_UPLOAD_DIR);
			String filename = file.getOriginalFilename();
			String fileExtName = filename.substring(filename.lastIndexOf(".") + 1);
			log.info("上传的文件的文件名：" + filename + "----扩展名：" + fileExtName);
			String saveFilename = FileUtil.makeFileName(fileExtName);// 生成文件保存的名称
			String realSavePath = FileUtil.makePath(saveFilename, savePath);// 生成文件的保存目录
			targetFile = new File(realSavePath, saveFilename);// 创建目标文件
			if (!targetFile.exists()) {
				targetFile.mkdirs();
			}
			file.transferTo(targetFile);// 文件写入目标文件
			// 2.读入数据
			SimpleDateFormat f = new SimpleDateFormat("yyyyMMddHHmmssSSS");
			Date date = new Date();
			String batchCode = f.format(date);
			excelReader = new ImportExcelHandler(lang);
			excelReader.setBatchCode(batchCode);
			excelReader.process(realSavePath + saveFilename, 1);
			// 3.错误信息
			errors = excelReader.getErrors();
			// 4.对errors按照行号进行排序
			if (CollectionUtils.isNotEmpty(errors)) {
				Collections.sort(errors);
			}
			if (!errors.isEmpty()) {
				msg = new ImportResult(false, errors, count);
				return msg;
			}
			// 5.Excel数据
			list = excelReader.getDatas();
			long excelDataEnd = System.currentTimeMillis();
			log.info("Excel数据处理共耗时：" + (excelDataEnd - start));

			Map<String, String> maps = new HashMap<String, String>();// 提示信息
			maps.put(Constants.Excel.ST_CODE, ResourceBundleHelper.get(lang, Constants.Excel.ST_CODE));

			// 6.保存Excel数据
			msg = saveData(list, sysUser.getStCode(), maps);
			if (msg != null) {
				return msg;
			}
		} catch (Exception e) {
			log.error("导入异常", e);
			// 错误信息返回
			errors = excelReader.getErrors();
			if (!errors.isEmpty()) {
				msg = new ImportResult(false, errors, count);
				return msg;
			}
		} finally {
			// 删除文件
			// targetFile.delete();
			long end = System.currentTimeMillis();
			log.info("Excel数据导入共耗时：" + (end - start));
		}
		if (null != list) {
			count = list.size();
		}
		return new ImportResult(true, errors, count);
	}

	/**
	 * 保存数据
	 * 
	 * @param list
	 * @param stCode
	 * @param maps
	 * @return
	 */
	public ImportResult saveData(List<SysUser> list, String stCode, Map<String, String> maps) {
		ImportResult msg = null;
		List<ImportError> errors = new ArrayList<ImportError>();
		try {
			int count = 0;// 成功数量
			// 把集合放入临时表
			long jdbcBatchSaveStart = System.currentTimeMillis();
			jdbcBatchSave(list, stCode, "sys_user");
			long jdbcBatchSaveEnd = System.currentTimeMillis();
			log.info("保存数据共耗时：" + (jdbcBatchSaveEnd - jdbcBatchSaveStart));
			if (list.size() == 0) {
				msg = new ImportResult(false, maps.get(Constants.Excel.NO_SUCCESS_RECORD), count);
			} else {
				count = list.size();
				msg = new ImportResult(true, "success", count);
			}
		} catch (Exception e) {
			log.error("导入异常", e);
			// 错误信息返回
			ImportError importError = new ImportError(maps.get(Constants.Excel.IMPORT_EXCEPTION), e.getMessage());
			errors.add(importError);
			msg = new ImportResult(false, errors, 0);
		}
		return msg;
	}

	/***
	 * 分页处理数据
	 * 
	 * @param list
	 * @param stCode
	 * @param tableName
	 * @return
	 */
	public int jdbcBatchSave(List<SysUser> list, String stCode, String tableName) {
		try {
			int batchNum = 50000;
			int count = 0;
			Map<Integer, List<SysUser>> map = new HashMap<Integer, List<SysUser>>(); // 用map存起来新的分页后数据
			int index = 1;
			int listSize = list.size();
			for (int i = 0; i < listSize; i += batchNum) {
				log.info("Map分页--分数)" + batchNum + "页号：" + index);
				if (list.size() - i < batchNum) {
					map.put(index, list.subList(i, i + (listSize - i)));
					index++;
				} else {
					map.put(index, list.subList(i, index * batchNum));
					index++;
				}
			}

			for (Map.Entry<Integer, List<SysUser>> entry : map.entrySet()) {
				batchSave(map.get(entry.getKey()), stCode, tableName);// 保存数据
			}

			return count;
		} catch (Exception e) {
			log.error("保存错误", e);
			throw new RuntimeException("保存错误");
		}
	}

	/**
	 * JDBC批量保存
	 * 
	 * @param list
	 * @param stCode
	 * @param tableName
	 */
	public void batchSave(List<SysUser> list, String stCode, String tableName) {
		if (null == list || list.size() == 0) {
			return;
		}
		StringBuffer sqlSb = new StringBuffer();
		sqlSb.append("insert into ").append(tableName).append(
				"(ST_ID,ST_Code,ST_Name,ST_Password,CREATE_BY,CREATE_TM,UPDATE_BY,UPDATE_TM) values(?,?,?,?,?,?,?,?)");
		DBHelper dbHelper = new DBHelper(sqlSb.toString(), false);
		try {
			Connection conn = dbHelper.getConnection();
			PreparedStatement pst = dbHelper.getPrepareStatement();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String currDate = format.format(new Date());
			// 构建完整sql
			for (int i = 0; i < list.size(); i++) {
				SysUser sysUser = list.get(i);
				pst.setString(1, sysUser.getStId());
				pst.setString(2, sysUser.getStCode());
				pst.setString(3, sysUser.getStName());
				pst.setString(4, sysUser.getStPassword());
				pst.setString(5, stCode);
				pst.setString(6, currDate);
				pst.setString(7, stCode);
				pst.setString(8, currDate);
				pst.addBatch();
				// if(i%1000 == 0){
				// pst.executeBatch();
				// pst.clearBatch();
				// }

			}

			// 添加执行
			// pst.addBatch(sqlSb.toString());
			// 执行操作
			pst.executeBatch();
			// 提交事务
			conn.commit();
			log.info("JDBC事务提交");
		} catch (SQLException e) {
			log.error("批量错误", e);
			throw new RuntimeException("批量插入错误");
		} finally {
			dbHelper.close();
		}
	}

}
