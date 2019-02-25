package org.springframework.beans.factory.config;

import com.its.common.crypto.des.DesUtil;

/**
 * 重写Spring加载Properties文件类-实现对JDBC配置进行加解密
 *
 */
public class EncryptPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {

	private String[] encryptPropNames = { "jdbc.url", "jdbc.username", "jdbc.password" };// 加密属性名单

	@Override
	protected String convertProperty(String propertyName, String propertyValue) {
		if (isEncryptProp(propertyName)) {
			String decryptValue = DesUtil.decrypt(propertyValue, DesUtil.KEY);
			System.out.println("decrypt--" + decryptValue);
			return decryptValue;
		} else {
			return propertyValue;
		}

	}

	private boolean isEncryptProp(String propertyName) {
		for (String encryptName : encryptPropNames) {
			if (encryptName.equals(propertyName)) {
				return true;
			}
		}
		return false;
	}
}
