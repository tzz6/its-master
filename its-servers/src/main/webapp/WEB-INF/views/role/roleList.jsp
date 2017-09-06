<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/common.jsp"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>角色信息列表</title>
</head>
<body>
	<table>
		<tr>
			<td>
				<font size="6">角色信息列表</font><br>
				<a href="${ctx }/role/addRole" style="padding-left: 5px;">添加</a>
			</td>
		<tr>
		<tr>
			<td>角色id</td>
			<td>角色名</td>
			<td>部门</td>
			<td>操作</td>
		<tr>
		<c:forEach items="${roles }" var="role">
			<tr>
				<td>${role.id }</td>
				<td>${role.name }</td>
				<td>${role.department.name }</td>
				<td>
					<a href="${ctx}/role/delete/${role.id}.do">删除</a>
				</td>
			<tr>
		</c:forEach>
	</table>
</body>
</html>