package com.its.web.util;

import java.util.HashMap;
import java.util.Map;

public class BldLanguageUtil {

	public static Map<String, String> bldLanguageMaps = new HashMap<String, String>();

	public static Map<String, String> getBldLanguageMaps() {
		return bldLanguageMaps;
	}

	/**
	 * 获取语言值
	 * 
	 * @param key
	 *            语言CODE
	 * @param lang
	 *            语言
	 * @return
	 */
	public static String getValue(String key, String lang) {
		return bldLanguageMaps.get(key + "_" + lang);
	}

}
