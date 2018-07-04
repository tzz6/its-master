package com.its.web.controller.file;

import java.io.File;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.its.common.utils.Constants;
import com.its.common.utils.ImportError;
import com.its.common.utils.ImportResult;
import com.its.common.utils.poi.POIUtil;
import com.its.core.mongodb.dao.impl.CountryMongoDaoImpl;
import com.its.model.mongodb.dao.domain.City;
import com.its.model.mongodb.dao.domain.Country;
import com.its.model.mybatis.dao.domain.SysUser;
import com.its.servers.facade.dubbo.sys.service.SysUserFacade;
import com.its.web.controller.login.BaseController;
import com.its.web.util.DBHelper;
import com.its.web.util.FileUtil;
import com.its.web.util.ImportExcelHandler;
import com.its.web.util.ResourceBundleHelper;
import com.its.web.util.UserSession;

/**
 * Excel
 *
 */
@Controller
@RequestMapping("/excel")
public class ExcelController extends BaseController {

	private static final Log log = LogFactory.getLog(ExcelController.class);
	@Autowired
	private SysUserFacade sysUserFacade;
	@Autowired
	private CountryMongoDaoImpl countryMongoDao;

	/**
	 * 导入
	 * @param request
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/import")
	public ImportResult saxImport(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("imptType") String imptType, @RequestParam("saveType") String saveType,
			@RequestParam("imptFile") CommonsMultipartFile file, ModelMap model) {
		long start = System.currentTimeMillis();
		SysUser currSysUser = UserSession.getUser();
		String lang = currSysUser.getLanguage();
		ImportResult msg = null;// 导入的结果信息
		int count = 0;// 成功数量
		List<ImportError> errors = new ArrayList<ImportError>();
		List<SysUser> list = null;
		File targetFile = null;
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
			// 2.读入Excel数据
			log.info("Excel解析方式：" + imptType);
			ImportExcelHandler excelReader = new ImportExcelHandler(lang);
			if (imptType.equals("SAX")) {//POI SAX方式解析Excel
				//Excel超大数据读取，抽象Excel2007读取器 excel2007的底层数据结构是xml文件，采用SAX的事件驱动的方法解析xml，
				//需要继承DefaultHandler，在遇到文件内容时，事件会触发，这种做法可以大大降低 内存的耗费，特别使用于大数据量的文件。
				excelReader.process(realSavePath + saveFilename, 1);
				errors = excelReader.getErrors();// 错误信息
				if (CollectionUtils.isNotEmpty(errors)) {// 对errors按照行号进行排序
					Collections.sort(errors);
				}
				if (!errors.isEmpty()) {
					msg = new ImportResult(false, errors, count);
					return msg;
				}
				list = excelReader.getDatas();// Excel数据
			}
			if (imptType.equals("POI")) {//POI方式解析Excel
				Map<String, List<String>> maps = POIUtil.read(realSavePath + saveFilename);
				list = new ArrayList<SysUser>();
				for (Map.Entry<String, List<String>> entry : maps.entrySet()) {
					SysUser sysUser = excelReader.setSysUser(maps.get(entry.getKey()),
							Integer.parseInt(entry.getKey()));
					list.add(sysUser);// Excel数据
				}
			}
			long excelDataEnd = System.currentTimeMillis();
			log.info("Excel数据处理共耗时：" + (System.currentTimeMillis() - start));
			// 3.保存Excel数据
			if (list == null || list.size() == 0) {// 数据为空
				msg = new ImportResult(false, ResourceBundleHelper.get(lang, Constants.Excel.NO_SUCCESS_RECORD), count);
			} else {
				Map<String, String> maps = new HashMap<String, String>();// 提示信息
				maps.put(Constants.Excel.IMPORT_EXCEPTION,
						ResourceBundleHelper.get(lang, Constants.Excel.IMPORT_EXCEPTION));
				msg = saveData(list, currSysUser.getStCode(), maps, saveType);
			}
			log.info("Excel数据批处理耗时：" + (System.currentTimeMillis() - excelDataEnd));
		} catch (Exception e) {
			log.error("导入异常", e);
			msg = new ImportResult(false, ResourceBundleHelper.get(lang, Constants.Excel.IMPORT_EXCEPTION), count);
		} finally {
			// targetFile.delete();// 删除文件
			log.info("Excel数据导入共耗时：" + (System.currentTimeMillis() - start));
		}
		return msg;
	}

	/**
	 * 保存数据
	 * 
	 * @param list
	 * @param stCode
	 * @param maps
	 * @return
	 */
	public ImportResult saveData(List<SysUser> list, String stCode, Map<String, String> maps, String saveType) {
		ImportResult msg = null;
		List<ImportError> errors = new ArrayList<ImportError>();
		try {
			int count = 0;// 成功数量
			if ("Mysql".equals(saveType)) {//保存Mysql数据库
				Map<Integer, List<SysUser>> map = makeMapPage(list);// 构造分页数据
				for (Map.Entry<Integer, List<SysUser>> entry : map.entrySet()) {
					int batchSaveSize = batchSave(map.get(entry.getKey()), stCode, "sys_user");// 保存数据
					count = count + batchSaveSize;
				}
			}
			if ("MongDB".equals(saveType)) {//保存MongDB
				mongdbSave(list);
			}
			if ("Cache".equals(saveType)) {//保存在内存
				cacheSave(list);
			}
			msg = new ImportResult(true, "success", count);
		} catch (Exception e) {
			log.error("导入异常", e);
			ImportError importError = new ImportError(maps.get(Constants.Excel.IMPORT_EXCEPTION), e.getMessage());
			errors.add(importError);
			msg = new ImportResult(false, errors, 0);
		}
		return msg;
	}

	private void mongdbSave(List<SysUser> list) {
		List<Country> countries = new ArrayList<Country>();
		for (int i = 0; i < list.size(); i++) {
			SysUser sysUser = list.get(i);
			Country country = new Country();
			Integer id = 0;
			country.setId(i);
			country.setName(sysUser.getStCode());
			country.setEnName(sysUser.getStName());
			country.setCreateDate(new Date());
			List<City> citys = new ArrayList<City>();
			City city = new City();
			city.setId(id);
			city.setName("深圳");
			citys.add(city);
			country.setCitys(citys);
			countries.add(country);
		}
		countryMongoDao.insertAll(countries);
	}
	
	private void cacheSave(List<SysUser> list) {
		List<Country> countries = new ArrayList<Country>();
		for (int i = 0; i < list.size(); i++) {
			SysUser sysUser = list.get(i);
			Country country = new Country();
			Integer id = 0;
			country.setId(i);
			country.setName(sysUser.getStCode());
			country.setEnName(sysUser.getStName());
			country.setCreateDate(new Date());
			List<City> citys = new ArrayList<City>();
			City city = new City();
			city.setId(id);
			city.setName("深圳");
			citys.add(city);
			country.setCitys(citys);
			countries.add(country);
		}
		countryMongoDao.insertAll(countries);
	}

	/**
	 * 构造分页数据
	 * 
	 * @param list
	 * @return
	 */
	public Map<Integer, List<SysUser>> makeMapPage(List<SysUser> list) {
		int batchNum = 50000;
		Map<Integer, List<SysUser>> map = new HashMap<Integer, List<SysUser>>(); // 用map存起来新的分页后数据
		int index = 1;
		int listSize = list.size();
		for (int i = 0; i < listSize; i += batchNum) {
			log.info("构造Map分页--页编号" + index + "--分页数" + batchNum);
			if (list.size() - i < batchNum) {
				map.put(index, list.subList(i, i + (listSize - i)));
				index++;
			} else {
				map.put(index, list.subList(i, index * batchNum));
				index++;
			}
		}
		return map;
	}

	/**
	 * JDBC批量保存
	 * 
	 * @param list
	 * @param stCode
	 * @param tableName
	 */
	public int batchSave(List<SysUser> list, String stCode, String tableName) {
		int size = list.size();
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
			for (int i = 0; i < size; i++) {
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
		return size;
	}
	
	/** 导出 */
	@RequestMapping(value = "/export")
	public void export(HttpServletRequest request, HttpServletResponse response) {
		try {
			List<SysUser> sysUsers = sysUserFacade.getAllSysUserList();
			Map<String, List<String>> maps = new LinkedHashMap<String, List<String>>();
			List<String> excelHead = new ArrayList<String>();
			excelHead.add("用户名");
			excelHead.add("姓名");
			excelHead.add("密码");
			maps.put("0", excelHead);
			int rowNum = 1;
			for (SysUser sysUser : sysUsers) {
				List<String> list = new ArrayList<String>();
				list.add(sysUser.getStCode());
				list.add(sysUser.getStName());
				list.add(sysUser.getStPassword());
				maps.put(rowNum + "", list);
				rowNum++;
			}
			Workbook workbook = POIUtil.writer("xlsx", maps);
			String fileName = "用户列表.xlsx";
			response.setContentType("text/html;charset=UTF-8");
			response.setContentType("application/x-msdownload");
			response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
			OutputStream out = response.getOutputStream();
			workbook.write(out);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}