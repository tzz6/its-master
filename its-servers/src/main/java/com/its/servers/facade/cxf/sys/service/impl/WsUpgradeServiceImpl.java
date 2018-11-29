package com.its.servers.facade.cxf.sys.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.jws.WebService;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.its.common.utils.Constants;
import com.its.common.utils.json.JacksonUtil;
import com.its.model.bean.UpgradeInfo;
import com.its.servers.facade.cxf.sys.service.WsUpgradeService;

/**
 * RCP客户端在线升级WS
 */
@WebService(endpointInterface = "com.its.servers.facade.cxf.sys.service.WsUpgradeService", serviceName = "wsUpgradeServiceImpl")
public class WsUpgradeServiceImpl implements WsUpgradeService {
	private final static Logger logger = Logger.getLogger(WsUpgradeServiceImpl.class);

	// 客户端安装包服务器磁盘目录
	private String upgradeDiscUrl;
	// 客户端安装包服务器HttpUrl
	private String upgradeUrl;

	public void setUpgradeDiscUrl(String upgradeDiscUrl) {
		this.upgradeDiscUrl = upgradeDiscUrl;
	}

	public void setUpgradeUrl(String upgradeUrl) {
		this.upgradeUrl = upgradeUrl;
	}

	/** 检查是否有版本需要升级 */
	@Override
	public int isUpgrade(UpgradeInfo client) {
		logger.info("客户端升级配置信息：" + JacksonUtil.nonDefaultMapper().toJson(client));

		String clientVersion = client.getVersion();// 客户端当前版本号
		String maxVersion = getMaxVersion();// 服务器上最大版本号为
		logger.info("服务器上最大版本号为：" + maxVersion);
		if (StringUtils.isBlank(clientVersion) || StringUtils.isBlank(maxVersion)) {
			return 0;
		}
		int result = maxVersion.compareTo(clientVersion);
		logger.info("是否需要升级版本：" + result);
		return result;
	}

	/** 获取升级版本路径下面的全部版本号文件信息，并且按照降序排序 */
	@Override
	public List<File> getAllVersionFiles() {
		if (StringUtils.isBlank(upgradeDiscUrl)) {
			throw new IllegalArgumentException("升级版本路径为空");
		}
		File upgradeFile = new File(upgradeDiscUrl);

		if (upgradeFile != null) {
			File[] childFiles = upgradeFile.listFiles();
			if (childFiles != null && childFiles.length > 0) {
				List<File> list = new ArrayList<File>();
				File[] tempFiles = null;
				for (File file : childFiles) {// 判断版本文件下面是否存在升级文件
					tempFiles = file.listFiles();
					if (null == tempFiles || tempFiles.length <= 0) {
						continue;
					}
					list.add(file);
				}
				// 倒序排序
				Collections.reverse(list);
				logger.info("升级路径所有版本文件列表："  + JacksonUtil.nonDefaultMapper().toJson(list));
				return list;
			}

		}
		return null;
	}

	/** 获取最大版本号 */
	private String getMaxVersion() {
		List<File> files = getAllVersionFiles();
		if (files != null && files.size() > 0) {
			return files.get(0).getName();
		}
		return null;
	}

	/** 获取升级版本URL地址 */
	@SuppressWarnings("unused")
	private String getUpgradeUrl() {
		String url = null;
		if (StringUtils.isNotBlank(upgradeUrl)) {
			String tempUrl = upgradeUrl.replace(Constants.UPGRADE.SLASH, Constants.UPGRADE.ALTSLASH);
			if (!tempUrl.endsWith(Constants.UPGRADE.ALTSLASH)) {
				url = tempUrl + Constants.UPGRADE.ALTSLASH;
			}
		}
		url = upgradeUrl;
		logger.info("配置升级下载链接地址为：" + url);
		return url;
	}

	/**
	 * 搜索下载的版本文件
	 * 
	 * @param version
	 *            版本号
	 * @param files
	 *            文件列表
	 * @param downloadUrl
	 *            下载链接地址
	 * @return
	 */
	@SuppressWarnings("unused")
	private ArrayList<String> buildDownloadUrl(String version, File[] files, String downloadUrl) {
		ArrayList<String> urls = new ArrayList<String>();
		StringBuffer buffer = null;
		for (File file : files) {
			// 拼接文件下载URL地址，下载地址值文件服务器提供的下载链接
			buffer = new StringBuffer(downloadUrl);
			buffer.append(version).append(Constants.UPGRADE.ALTSLASH).append(file.getName());

			urls.add(buffer.toString());
		}
		return urls;
	}

	public static void main(String[] args) {
		WsUpgradeServiceImpl wsUpgradeServiceImpl = new WsUpgradeServiceImpl();
		wsUpgradeServiceImpl.setUpgradeDiscUrl("E:\\develop\\Apache2.2\\htdocs\\sfbuy-wms-client");
		List<File> files = wsUpgradeServiceImpl.getAllVersionFiles();
		for (File file : files) {
			System.out.println(file.getName());
		}
		System.out.println("------------------------");
		System.out.println(wsUpgradeServiceImpl.getMaxVersion());
	}

}
