<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/common.jsp"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>定时任务列表</title>
<script type="text/javascript" src="${ctx}/js/common/jquery-2.1.4.js"></script>  
<script type="text/javascript" src="${ctx}/js/common/jquery-2.1.4.js"></script>
<script type="text/javascript" src="${ctx}/js/schedulejob/list.js"></script>
</head>
<body>
	<table>
		<tr>
			<td>
				<font size="6">定时任务管理列表<br>
				</font>
<!-- 				<a href="###" onclick="stopAll()" style="padding-left: 5px;">全部暂停</a>/ -->
<!-- 				<a href="###" onclick="recoverAll()" style="padding-left: 5px;">全部恢复</a> -->
			</td>
		<tr>
		<tr>
			<td>任务id</td>
			<td>&nbsp;job名</td>
			<td>&nbsp;job组</td>
			<td>&nbsp;类名</td>
			<td>&nbsp;cronExpression</td>
			<td>&nbsp;Description</td>
			<td>&nbsp;操作</td>
		<tr>
		<c:forEach items="${scheduleJobs}" var="scheduleJob">
			<tr>
				<td>${scheduleJob.id }</td>
				<td>&nbsp;${scheduleJob.jobName }</td>
				<td>&nbsp;${scheduleJob.jobGroup }</td>
				<td>&nbsp;${scheduleJob.className }</td>
				<td>&nbsp;${scheduleJob.cronExpression }</td>
				<td>&nbsp;${scheduleJob.description }</td>
				<td>&nbsp;
				<c:if test="${scheduleJob.jobStatus == '0'}">
					<a href="###" onclick="create(${scheduleJob.id})">创建</a>/
				</c:if>
				<c:if test="${scheduleJob.jobStatus == '1' || scheduleJob.jobStatus =='3'}">
					<a href="###" onclick="runJob(${scheduleJob.id})">执行</a>/
					<a href="${ctx}/scheduleJob/${scheduleJob.id}/toUpdate">修改</a>/
					<a href="###" onclick="stop(${scheduleJob.id})">暂停</a>/
				</c:if>
				<c:if test="${scheduleJob.jobStatus == '2'}">
					<a href="###" onclick="recover(${scheduleJob.id})">恢复</a>/
			    </c:if>
				<c:if test="${scheduleJob.jobStatus eq '1'}">
					<a href="###" onclick="deleteJob(${scheduleJob.id})">删除</a>
				</c:if>
				</td>
			<tr>
		</c:forEach>
	</table>
</body>
</html>