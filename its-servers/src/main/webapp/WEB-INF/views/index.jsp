<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>首页</title>
<style type="text/css">
.index_div {
	padding-top: 5px;
	padding-left: 18px;
}

.index_a {
	text-decoration: none;
	font-size: 20px;
}
</style>
</head>
<body>
<div class="index_div"><a class="index_a" href="${ctx }/scheduleJob/list">定时任务管理</a></div>
<div class="index_div"><a class="index_a" href="${ctx }/user/list">用户管理</a></div>
<div class="index_div"><a class="index_a" href="${ctx }/role/list">角色管理</a></div>
<div class="index_div"><a class="index_a" href="${ctx }/cxf/rest/cxfRestfulSysUser/sysUserManager/admin">CXF RestFul</a></div>
</body>
</html>