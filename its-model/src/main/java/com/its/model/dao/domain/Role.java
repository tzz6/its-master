package com.its.model.dao.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 角色
 */
@Entity
@Table(name = "tab_role")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region="com.its.model.dao.domain.Role")
public class Role implements Serializable{

	private static final long serialVersionUID = 8888233717801480088L;
	
	@Id
	@GeneratedValue
	private Long id;
	@Column(length = 50)
	private String name;

	/** 与User多对多的双向关联关系 */
	@ManyToMany(mappedBy = "roles")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region="com.its.model.dao.domain.User")
	private Set<User> users = new HashSet<User>();

	/** Department与Role多对一的关系 */
	@ManyToOne
	@JoinColumn(name = "departmentId")
	private Department department;

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Department getDepartment() {
		return department;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
