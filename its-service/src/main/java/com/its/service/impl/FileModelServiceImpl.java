package com.its.service.impl;

import java.io.Serializable;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.its.core.hibernate.dao.BaseDao;
import com.its.model.dao.domain.FileModel;
import com.its.service.FileModelService;

@Service("fileModelService")
public class FileModelServiceImpl extends BaseServiceImpl<FileModel> implements FileModelService {

	@Override
	@Resource(name = "fileModelDao")
	public void setBaseDao(BaseDao<FileModel> baseDao) {
		super.setBaseDao(baseDao);
	}

	/** 单值检索 */
	public Object uniqueResult(String hql, Serializable... serializables) {
		return uniqueResult(hql, serializables);
	}
}
