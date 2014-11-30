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
<form action="" method="locale">
            <select name="locale" >
                <option value="ru_RU">ru</option>
                <option value="en_US">en</option>
            </select>
            <input type="submit" value=<spring:message code="language.change" />>
        </form>
<h1><spring:message code="user.list" /></h1>
<form:form method="get" modelAttribute="users">
<ul>
    <table class="userInfo">
        <th>
            <td><spring:message code="user.id" /></td>
            <td><spring:message code="user.login" /></td>
            <td><spring:message code="user.name" /></td>
            <td><spring:message code="user.age" /></td>
            <td><spring:message code="user.delete" /></td>
        </th>
        <c:forEach items="${users}" var="user">
        <tr>
            <td/>
            <td><a href='<spring:url value="/user/${user.userId}" ></spring:url>'>${user.userId}</a></td>
            <td>${user.userLogin}</td>
            <td>${user.userName}</td>
            <td>${user.userAge}
             <td><a href='<spring:url value="/deleteData" ><spring:param name="userId" value="${user.userId}" /> </spring:url>'><spring:message code="user.del" /></a></td>
        </tr>
    </c:forEach>
    </table>
</ul>
</form:form>
<form action="/submitData" method="post">
    <table >
        <tr><td><label path="userLogin"><spring:message code="user.login" />:</label></td><td><input type="text" name="Login"/><br/></td></tr>
        <tr><td><label path="userName"><spring:message code="user.name" />:</label></td><td><input type="text" name="Name"/><br/></td></tr>
        <tr><td><label path="userAge"><spring:message code="user.age" />:</label></td><td><input type="text" name="Age"/><br/></td></tr>
        <tr><td><input type="submit" name="Submit" value=<spring:message code="user.add" />></td></tr>
    </table>
</form>
<script src="js/jquery-1.11.1.js"></script>
<script src="js/main.js"></script>
</body>
</html>