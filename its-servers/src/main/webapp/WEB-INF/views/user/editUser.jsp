<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/common.jsp" %> 
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="spr"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改用户信息</title>
</head>
<body>
<center>
	<form action="${ctx }/user/save" method="post">
		<input type="hidden" name="id" value="${user.id }">
		用戶名:<input type="text" name="name" value="${user.name }"></br><br>
		注册日期：<input type="text" name="regDate" value="${user.regDate }"></br><br>
		性别:<input type="radio" name="sex" value="男" checked="${user.sex=='男'?"'checked'":''}" >男
	     	 <input type="radio" name="sex" value="女" checked="${user.sex=='女'?"'checked'":''}">女
	     	 </br>
		部门：<select name="departmentId">
				<c:forEach items="${departments}" var="department">
					<option value="${department.id}">${department.name}</option>
				</c:forEach>
			</select></br>
		岗位:
		<c:forEach items="${roles}" var="role">
			<input type="checkbox" name="roleIds" value="${role.id }">${role.name }	
		</c:forEach>
			<br>
		<input type="submit" value="注册">
	</form>
</center>	
</body>
</html>