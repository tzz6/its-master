package com.its.servers.web.cxf.restful.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

import com.its.model.dao.domain.Role;


@XmlRootElement(name = "UserInfo")
public class UserInfo implements Serializable {
	
	private static final long serialVersionUID = -4411108101584733172L;
	private int id;
	private String name;
	private String email;
	private String address;
	
	private List<Role> roles;
	
	private Map<String, Role> maps;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public Map<String, Role> getMaps() {
		return maps;
	}

	public void setMaps(Map<String, Role> maps) {
		this.maps = maps;
	}
	
}
