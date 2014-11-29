<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- Tell the JSP Page that please do not ignore Expression Language -->
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <style type="text/css">
        table {
            width: 300px;
            border-collapse: collapse;
        }
        td , th {
            padding: 3px;
            border: 1px solid black;
        }
        TH {
            background: #b0e0e6;
        }
    </style>

</head>

<body>


<form:form method="get" modelAttribute="users">
<h1><spring:message code="user.list" /></h1>
<ul>
    <table class="userInfo">
        <th>
            <td>id</td>
            <td>login</td>
            <td>name</td>
            <td>age</td>
            <td>delete</td>
        </th>
        <c:forEach items="${users}" var="user">
        <tr>
            <td/>
            <td><a href='<spring:url value="/user/${user.userId}" ></spring:url>'>${user.userId}</a></td>
            <td>${user.userLogin}</td>
            <td>${user.userName}</td>
            <td>${user.userAge}
             <td><a href='<spring:url value="/deleteData" ><spring:param name="userId" value="${user.userId}" /> </spring:url>'>Delete</a></td>
        </tr>
    </c:forEach>
    </table>
</ul>
</form:form>
<form action="/submitData" method="post">
    <table >
        <tr><td><label path="userLogin">Login:</label></td><td><input type="text" name="Login"/><br/></td></tr>
        <tr><td><label path="userName">Name:</label></td><td><input type="text" name="Name"/><br/></td></tr>
        <tr><td><label path="userAge">Age:</label></td><td><input type="text" name="Age"/><br/></td></tr>
        <tr><td><input type="submit" name="Submit"></td></tr>
    </table>
</form>
<script src="js/jquery-1.11.1.js"></script>
<script src="js/main.js"></script>
</body>
</html>