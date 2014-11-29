<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html lang="en">
    <body>
        <form:form method="get" modelAttribute="map">
            <table class="userInfo">
                <tr><td>UserId: </td><td>${map["user"].userId}</td></tr>
                <tr><td>UserLogin:</td><td>${map["user"].userLogin}</td></tr>
                <tr><td>UserName:</td><td>${map["user"].userName}</td></tr>
                <tr><td>UserAge:</td><td>${map["user"].userAge}</td></tr>
                <tr><td>AvAge:  </td><td><c:out value="${user['age']}"/></td></tr>
                <tr><td><a href='<spring:url value="/deleteData" ><spring:param name="userId" value="${user.userId}" /> </spring:url>'>Delete</a></td><tr>
            </table>
        </form:form>
    </body>
</html>