<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:choose>
   <c:when test="${!(empty cookie.lang.value)}"> 
    <c:set var="lang" value="${cookie.lang.value}"/> 
   </c:when>
   <c:otherwise>
 	<%
   	String language = request.getLocale().toString();
   	if (language.compareToIgnoreCase("zh_HK")==0 || language.compareToIgnoreCase("zh_TW")==0){
   		language = "zh_CN";
   	}
   %>
   	<c:set var="lang" value="<%=language%>"/>
   </c:otherwise>
</c:choose>
<input type="hidden" id="lang" value="${lang}" />
<fmt:setLocale value="${lang}" />