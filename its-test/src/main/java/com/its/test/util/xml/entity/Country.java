package com.its.test.util.xml.entity;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.its.common.utils.xml.JaxbDateSerializer;

/**
 * 
 * @author tzz
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "country")
@XmlType(propOrder = { "id", "createDate", "name", "provinceList" })
public class Country {

	private Integer id;
	@XmlJavaTypeAdapter(JaxbDateSerializer.class)
	private Date createDate;
	@XmlElement(name = "name")
	private String name;
	@XmlElementWrapper(name = "provinces")
	@XmlElement(name = "province")
	private List<Province> provinceList;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Province> getProvinceList() {
		return provinceList;
	}

	public void setProvinceList(List<Province> provinceList) {
		this.provinceList = provinceList;
	}
}