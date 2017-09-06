package com.its.web.common.report.jasperreports.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.its.web.common.report.jasperreports.JasperreportsService;
import com.its.web.common.report.util.ServiceConstants;
import com.its.web.common.report.util.TemplateProperties;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;

/**
 * Jasperreports服务
 *
 */
@Service(value = "jasperreportsService")
public class JasperreportsServiceImpl implements JasperreportsService {
	protected static final Logger LOGGER = Logger.getLogger(JasperreportsServiceImpl.class.getName());

	/**
	 * Jasperreports生成PDF
	 * 
	 * @param list
	 * @param savePath
	 * @return
	 */
	public String createPdf(List<Map<String, Object>> list, String savePath, String fileName) {
		File rootFile = new File(savePath);
		if (!rootFile.exists()) {
			rootFile.mkdirs();
		}
		File file = new File(savePath + fileName + ".pdf");
		LOGGER.info("hessian调用打印服务成功!");
		JRPdfExporter exporter = null;
		try {
			exporter = new JRPdfExporter();
			// 组装List<JasperPrint>
			List<JasperPrint> jasperPrintList = getJasperPrint(list);

			exporter.setExporterInput(SimpleExporterInput.getInstance(jasperPrintList));
			exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(file));
			SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
			configuration.setCreatingBatchModeBookmarks(false);
			configuration.setCompressed(true);
			exporter.setConfiguration(configuration);
			// 导出PDF
			exporter.exportReport();
			LOGGER.info("导出PDF成功");
		} catch (Exception e) {
			LOGGER.warn("填充数据报错或者生成为pdf输出流报错", e);
		}
		System.gc();
		return savePath + fileName + ".pdf";
	}

	/**
	 * 组装内容数据
	 * 
	 * @param list
	 * @return
	 * @throws JRException
	 */
	protected List<JasperPrint> getJasperPrint(List<Map<String, Object>> list) throws JRException {
		Map<String, Object> map = null;
		List<JasperPrint> jasperPrintList = new LinkedList<JasperPrint>();
		JasperPrint jasperPrint = null;
		int count = 0;
		for (Iterator<Map<String, Object>> iterator = list.iterator(); iterator.hasNext();) {
			++count;
			LOGGER.info("订单开始填充数据次数" + count);
			map = (Map<String, Object>) iterator.next();
			executionBusiness(map, jasperPrint, jasperPrintList, count);
		}

		return jasperPrintList;
	}

	@SuppressWarnings("unchecked")
	protected void executionBusiness(Map<String, Object> map, JasperPrint jasperPrint,
			List<JasperPrint> jasperPrintList, int count) throws JRException {
		LOGGER.info("Start loading SFBuy WMS print template.");
		JasperReport jasperReport = null;
		Collection<Map<String, ?>> tableData = null;

		// 打印标签标识
		String printCode = (String) map.get("printCode");

		if (StringUtils.equals(printCode, ServiceConstants.SFBUY_WMS_WAYBILL)) {
			jasperReport = TemplateProperties.getTemplateProperties().map.get("sfbuy.wms.Waybill");
		} else if (StringUtils.equals(printCode, ServiceConstants.SFBUY_WMS_INVOICE)) {
			tableData = (ArrayList<Map<String, ?>>) map.get("dataSource");
			jasperReport = TemplateProperties.getTemplateProperties().map.get("sfbuy.wms.Invoice");
		} else if (StringUtils.equals(printCode, ServiceConstants.SFBUY_WMS_GOODS_ORDER)) {
			tableData = (ArrayList<Map<String, ?>>) map.get("dataSource");
			jasperReport = TemplateProperties.getTemplateProperties().map.get("sfbuy.wms.GoodsOrder");
		}
		if (jasperReport == null) {
			LOGGER.warn("Access to print template is empty.");
		} else {
			LOGGER.info("Start print job.");
			if (StringUtils.equals(printCode, ServiceConstants.SFBUY_WMS_GOODS_ORDER)) {
				jasperPrint = JasperFillManager.fillReport(jasperReport, map, new JRMapCollectionDataSource(tableData));
			} else {
				// 表数据转换
				if (tableData != null && !tableData.isEmpty()) {
					map.put("dataSource", new JRMapCollectionDataSource(tableData));
				}
				jasperPrint = JasperFillManager.fillReport(jasperReport, map, new JREmptyDataSource());
			}

			jasperPrintList.add(jasperPrint);
			LOGGER.info("SFBuy WMS Print complete.");
		}
	}
}
