package com.its.test.util.xml.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "name", "provCity" })
public class Province {

	@XmlElement(name = "name")
	private String name;
	@XmlElement(name = "provCity")
	private String provCity;

	public String getProvCity() {
		return provCity;
	}

	public void setProvCity(String provCity) {
		this.provCity = provCity;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
