package com.its.model.dao.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * 定时任务表
 */
@Entity
@Table(name = "tab_schedule_job")
public class ScheduleJob implements Serializable{

	private static final long serialVersionUID = 2367440756812646930L;
	
	@Id
	@GeneratedValue
	private Long id;
	/**的任务名 */
	private String jobName;
	/**的任务组名 */
	@Column(name="job_group")
//	private String jobGroup = Scheduler.DEFAULT_GROUP;
	private String jobGroup = "DEFAULT";
	/**0--删除，1--运行，2--暂停，3--删除后不可重新创建*/
	private String jobStatus;
	/**触发器名 */
	private String triggerName;
	/**触发器组名 */
//	private String triggerGroup = Scheduler.DEFAULT_GROUP;
	private String triggerGroup = "DEFAULT";
	/**类名*/
	private String className;
	private String cronExpression;
	private String description;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getJobGroup() {
		return jobGroup;
	}
	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}
	public String getJobStatus() {
		return jobStatus;
	}
	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}
	public String getTriggerName() {
		return triggerName;
	}
	public void setTriggerName(String triggerName) {
		this.triggerName = triggerName;
	}
	public String getTriggerGroup() {
		return triggerGroup;
	}
	public void setTriggerGroup(String triggerGroup) {
		this.triggerGroup = triggerGroup;
	}
	
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	
	@SuppressWarnings("rawtypes")
	public Class getClazz() throws ClassNotFoundException {
		return Class.forName(getClassName());
	}
	public String getCronExpression() {
		return cronExpression;
	}
	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
