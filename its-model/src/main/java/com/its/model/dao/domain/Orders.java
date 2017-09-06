package com.its.model.dao.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "tab_orders")
public class Orders implements Serializable{

	private static final long serialVersionUID = 258485479423870157L;
	
	/** Id */
	@Id
	@GeneratedValue
	private Long id;
	/** 订单号 */
	private String orderNum;
	/** 创建日期 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;
	
	@ManyToOne
	@JoinColumn(name = "order_type_id")
//	@XmlTransient
	private OrderType orderType;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public OrderType getOrderType() {
		return orderType;
	}

	public void setOrderType(OrderType orderType) {
		this.orderType = orderType;
	}
	
}
