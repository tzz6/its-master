<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/common.jsp"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户信息列表</title>
</head>
<body>
	<table>
		<tr>
			<td>
				<font size="6">用户信息列表</font><br>
				<a href="${ctx }/user/addUser" style="padding-left: 5px;">添加</a>
			</td>
		<tr>
		<tr>
			<td>用户id</td>
			<td>用户名</td>
			<td>性别</td>
			<td>注册日期</td>
			<td>部门</td>
			<td>岗位</td>
			<td>操作</td>
		<tr>
		<c:forEach items="${users }" var="user">
			<tr>
				<td>${user.id }</td>
				<td>${user.name }</td>
				<td>${user.sex }</td>
				<td>${user.regDate }</td>
				<td>${user.department.name }</td>
				<td>
					<c:forEach items="${user.roles }" var="role">
						${role.name }
					</c:forEach>
				</td>
				<td>
					<a href="${ctx}/user/delete/${user.id}">删除</a>/
					<a href="${ctx}/user/${user.id}/toUpdate">修改</a>
				</td>
			<tr>
		</c:forEach>
	</table>
</body>
</html>