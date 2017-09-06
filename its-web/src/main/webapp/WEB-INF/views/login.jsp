<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:choose>
   <c:when test="${!(empty cookie.lang.value)}"> 
    <c:set var="lang" value="${cookie.lang.value}"/> 
   </c:when>
   <c:otherwise>
 	<%
   	String language = request.getLocale().toString();
   	if (language.compareToIgnoreCase("zh_HK")==0 || language.compareToIgnoreCase("zh_TW")==0 || language.compareToIgnoreCase("zh_CN")==0){
   		language = "zh";
   	}
   %>
   <c:set var="lang" value="<%=language%>"/>
   </c:otherwise>
</c:choose>

<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<fmt:setLocale value="${lang}" />
<fmt:bundle basename="com.its.resource.lang">
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><fmt:message key="login.button" /></title>
<script type="text/javascript" src="${ctx}/js/common/jquery-2.1.4.js"></script>
<script type="text/javascript" src="${ctx}/js/common/jquery.i18n.min.js"></script>
<script type="text/javascript" src="${ctx}/js/common/jquery.form.js"></script>
<script type="text/javascript" src="${ctx}/js/common/language.js"></script>
<script type="text/javascript" src="${ctx}/js/login/login.js"></script>
</head>
<body>
	<div style="text-align: center;">
		<input type="hidden" id="ctx" name="ctx" value="${ctx }">
		<table border="1" width="560" height="300"
			style="margin: auto; margin-top: 260px; text-align: center;">
			<tr style="text-align: center;">
				<td>
					<form id="login_form" action="${ctx }/login" method="post">
					<input type="hidden" id="lang" name="lang" value="${lang}" />
						<table border="0" width="550">
							<tr>
								<td width="100"><fmt:message key="login.username" />：</td>
								<td width="300"><input style="width: 186px;" type="text"
									id="username" name="username" value="${username }" /></td>
								<td id="usernameInfo" width="100" style="color: red;"></td>
							</tr>
							<tr>
								<td width="100"><fmt:message key="login.password" />：</td>
								<td width="300"><input style="width: 186px;"
									type="password" id="password" name="password"
									value="${password}" /></td>
								<td id="passwordInfo" width="100" style="color: red;"></td>
							</tr>
							<tr>
								<td width="100"><fmt:message key="login.language" />：</td>
								<td width="300">
								<select style="width: 192px;" name="language" id="language" onchange="changeLanguage();">
										<option value="" <c:if test="${lang==''}">selected</c:if>><fmt:message key="login.language.opt" /></option>
										<option value="zh" <c:if test="${lang=='zh'}">selected</c:if>><fmt:message key="login.language.cn" /></option>
										<option value="en" <c:if test="${lang=='en'}">selected</c:if>><fmt:message key="login.language.en" /></option>
								</select>
								</td>
								<td id="passwordInfo" width="100" style="color: red;"></td>
							</tr>
							<tr>
								<td width="100"><fmt:message key="login.verifyCode" />：</td>
								<td width="300">
								<input style="width: 96px;" type="text" id="verifyCode" name="verifyCode" /> 
								<img height="26" src="${ctx }/verifyCodeServlet" width="88" height="32" style="vertical-align: middle;" id="securityCodeImg"></td>
								<td id="verifyCodeInfo" width="100" style="color: red;"></td>
							</tr>
							<tr>
								<td width="100"></td>
							<td width="300">
<!-- 									<input type="checkbox" id="autologin" name="autologin" value="1" -->
<%-- 									<c:if test="${autoLogin == '1' }">checked="checked"</c:if>> --%>
<%-- 								<fmt:message key="login.autologin" /> --%>
								<input type="checkbox" id="savePassword" name="savePassword" value="1"
									<c:if test="${savePassword == '1' }">checked="checked"</c:if>>
								<fmt:message key="login.savePassword" />
							</td>
							</tr>
						</table>
					</form>
					<button id="btn_login" onclick="loginSubmit();">
						<fmt:message key="login.button" />
					</button>
				</td>
			</tr>
		</table>
	</div>
</body>
</html>
</fmt:bundle>