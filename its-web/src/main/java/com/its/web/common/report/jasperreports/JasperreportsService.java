package com.its.web.common.report.jasperreports;

import java.util.List;
import java.util.Map;

public interface JasperreportsService {

	/**
	 *  Jasperreports生成PDF
	 * @param list
	 * @param savePath
	 * @param fileName
	 * @return
	 */
	String createPdf(List<Map<String, Object>> list, String savePath, String fileName);
}
