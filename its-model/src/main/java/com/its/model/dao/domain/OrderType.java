package com.its.model.dao.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 角色
 */
@Entity
@Table(name = "tab_role_type")
public class OrderType implements Serializable{

	private static final long serialVersionUID = -3948624089526543247L;

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(length = 50)
	private String orderType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	
	
}
