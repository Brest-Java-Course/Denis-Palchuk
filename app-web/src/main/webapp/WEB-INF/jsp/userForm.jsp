<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <style type="text/css">
            #tabl {
                width: 1000px;
                border-collapse: collapse;
                border: 1px solid black;
            }
            TH {
                background: #b0e0e6;
            }
            #fromid {
                width: 70px;
            }
        </style>
    </head>
    <body>
        <table>
            <tr>
                <td>
                    <form:form method="get" modelAttribute="user">
                    <table class="userInfo">
                        <tr><td>UserId: </td><td>${user.userId}</td></tr>
                        <tr><td>UserLogin:</td><td>${user.userLogin}</td></tr>
                        <tr><td>UserName:</td><td>${user.userName}</td></tr>
                        <tr><td>UserAge:</td><td>${user.userAge}</td></tr>
                        <form:form method="get" modelAttribute="age">
                        <tr><td>AvAge:  </td><td>${age}</td></tr>
                        <tr><td><a href='<spring:url value="/deleteData" ><spring:param name="userId" value="${user.userId}" /> </spring:url>'>Delete</a></td><tr>
                    </table>
                        </form:form>
                        </form:form>
                </td>
                <td>
                    <table id="tabl" border="2">
                        <th><td id="fromid">FromId</td><td>Message</td></th>
                        <form:form method="get" modelAttribute="messages">
                        <c:forEach items="${messages}" var="message">
                        <tr>
                            <td></td>
                            <td id="fromid"><a href='<spring:url value="/user/${message.messageFromUserId}" ></spring:url>'>${message.messageFromUserId}</a></td>
                            <td>${message.messageText}</td>
                            <td>${message.messageDateTime}</td>
                        </tr>
                        </c:forEach>
                    </table>

                </td>
            </tr>
        </table>
    </body>
</html>