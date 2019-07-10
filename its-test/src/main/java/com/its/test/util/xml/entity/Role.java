package com.its.test.util.xml.entity;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * @author tzz
 */
@XStreamAlias("role")
public class Role {

	private Integer id;
	private String name;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
