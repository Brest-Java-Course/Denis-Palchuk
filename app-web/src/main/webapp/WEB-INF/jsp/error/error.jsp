<%@ page language="java" isErrorPage="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- Tell the JSP Page that please do not ignore Expression Language -->
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
    <title>Error page</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<button onclick="history.back()">Back to Previous Page</button>


<p><b>Date:</b> ${datetime}</p>
<p><b>Error code:</b> ${exception}</p>
<p><b>Request URI:</b> ${url}</p><br/>
<c:if test = "${exception == 'Empty list of Users'}">
<a href='<spring:url value="/inputForm" />'> <spring:message code="user.create" /></a>
</c:if>
</body>
</html>