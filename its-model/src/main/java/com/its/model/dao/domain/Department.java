package com.its.model.dao.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 部门
 */
@Entity
@Table(name = "tab_department")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region="com.its.model.dao.domain.Department")
public class Department implements Serializable {

	private static final long serialVersionUID = 4286472756753404035L;

	/** 部门Id */
	@Id
	@GeneratedValue
	private Long id;
	/** 部门名 */
	@Column(length = 50)
	private String name;

	/** 部门描述 */
	private String description;

	/**
	 * 上级部门与下级部门一对多的关系
	 */
	/** 上级部门 */
	@ManyToOne
	@JoinColumn(name = "parentid")
	private Department parent;
	/** 下级部门 */
	@OneToMany(mappedBy = "parent")
	private Set<Department> child = new HashSet<Department>();

	/** 与员工User 一对多关系 */
	@OneToMany(mappedBy = "department")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region="com.its.model.dao.domain.User")
	private Set<User> users = new HashSet<User>();

	/** 与角角一对多的关联关系 */
	@OneToMany(mappedBy = "department")
	private Set<Role> roles;

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

	public Department getParent() {
		return parent;
	}

	public void setParent(Department parent) {
		this.parent = parent;
	}

	public Set<Department> getChild() {
		return child;
	}

	public void setChild(Set<Department> child) {
		this.child = child;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
}
