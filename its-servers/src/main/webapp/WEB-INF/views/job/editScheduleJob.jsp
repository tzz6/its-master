<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/common.jsp"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改定时任务</title>
</head>
<body>
<center>
	<form action="${ctx }/scheduleJob/save" method="post">
	
		<input type="hidden" name="id" value="${scheduleJob.id }">
		cronExpression：<input type="text" name="cronExpression" value="${scheduleJob.cronExpression }"><br><br>
		<input type="submit" value="修改">
	</form>
</center>	
</body>
</html>