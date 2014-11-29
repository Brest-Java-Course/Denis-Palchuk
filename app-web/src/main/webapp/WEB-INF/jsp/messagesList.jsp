<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="joda" uri="http://www.joda.org/joda/time/tags"%>
<!-- Tell the JSP Page that please do not ignore Expression Language -->
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <style type="text/css">
        #messages {
            width: 800px;
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


<form:form method="get" modelAttribute="messages">
<h1><spring:message code="message.list" /></h1>
<ul>
    <table id="messages">
        <th>
            <td>id</td>
            <td>FromUserId</td>
            <td>ToUserId</td>
            <td>Text</td>
            <td>Date and Time</td>
        </th>
        <c:forEach items="${messages}" var="message">
        <tr>
            <td/>
            <td>${message.messageId}</td>
            <td><a href='<spring:url value="/user/${message.messageFromUserId}" ></spring:url>'>${message.messageFromUserId}</a></td>
            <td><a href='<spring:url value="/user/${message.messageToUserId}" ></spring:url>'>${message.messageToUserId}</a></td>
            <td>${message.messageText}</td>
            <td><joda:format value="${message.messageDateTime}" style="SS" /></td>
             <td><a href='<spring:url value="/deleteMessage" ><spring:param name="messageId" value="${message.messageId}" /> </spring:url>'>Delete</a></td>
        </tr>
    </c:forEach>
    </table>
</ul>
</form:form>
<form action="/submitMessage" method="post">
    <table>
        <tr><td><label path="messageFromUserId">From user id:</label></td><td><input type="text" name="FromUserId"/><br/></td></tr>
            <td><label path="messageToUserId">To user id:</label></td><td><input type="text" name="ToUserId"/><br/></td></tr>
            <td><label path="messageText">Text:</label></td><td><input type="text" name="Text"/><br/></td></tr>
            <td><input type="submit" name="Submit"></td></tr>
    </table>
</form>
<script src="js/jquery-1.11.1.js"></script>
<script src="js/main.js"></script>
</body>
</html>