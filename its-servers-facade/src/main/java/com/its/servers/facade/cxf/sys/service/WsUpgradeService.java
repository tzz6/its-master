package com.its.servers.facade.cxf.sys.service;

import java.io.File;
import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;

import com.its.model.bean.UpgradeInfo;

/**
 * RCP客户端在线升级WS
 *
 */
@WebService
public interface WsUpgradeService {

	/** 检查是否有版本需要升级 */
	int isUpgrade(@WebParam(name = "client") UpgradeInfo client);

	/** 获取升级版本路径下面的全部版本号文件信息，并且按照降序排序 */
	List<File> getAllVersionFiles();

	// /** 根据客户端的升级配置文件内容搜索需要升级的版本，回传JSON字符串 */
	// STRING GETUGRADEURL(@WEBPARAM(NAME = "CLIENT") UPGRADEINFO CLIENT);

}
