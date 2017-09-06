package com.its.model.dao.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "tab_user")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region="javaClassName")
public class User implements Serializable{

	private static final long serialVersionUID = 3236582489765951721L;
	
	/** 用户Id */
	@Id
	@GeneratedValue
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/** 用户名 */
	private String name;
	/** 密码 */
	private String password;
	/** 用户性别 */
	private String sex;

	/** 注册日期 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date regDate;

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	/** 所属部门 */
	/** Department 与 User ManyToOne 关系 */
	@ManyToOne
	@JoinColumn(name = "departmentid")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region="javaClassName")
	private Department department;

	/** 与Role多对多的双向关联关系 */
	@ManyToMany()
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region="javaClassName")
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "uid"), inverseJoinColumns = @JoinColumn(name = "rid"))
	@OrderBy("id asc")
	private Set<Role> roles = new HashSet<Role>();

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
