<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="joda" uri="http://www.joda.org/joda/time/tags"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html lang="en">
    <body>
        <h1><spring:message code="user.info" /></h1>
        <table class="pageTable">
            <tr>
                <td>
                    <form:form method="get" modelAttribute="user">
                    <table class="userInfo">
                        <tr><td><spring:message code="user.id" />: </td><td>${user.userId}</td></tr>
                        <tr><td><spring:message code="user.login" />:</td><td>${user.userLogin}</td></tr>
                        <tr><td><spring:message code="user.name" />:</td><td>${user.userName}</td></tr>
                        <tr><td><spring:message code="user.age" />:</td><td>${user.userAge}</td></tr>
                        <form:form method="get" modelAttribute="age">
                        <tr><td><spring:message code="user.avage" />:  </td><td>${age}</td></tr>
                        <tr><td><a href='<spring:url value="/deleteData" ><spring:param name="userId" value="${user.userId}" /> </spring:url>'><spring:message code="user.del" /></a></td><tr>
                    </table>
                        </form:form>
                        </form:form>
                </td>
                <td>
                    <table class="simple-little-table" cellspacing='0'>
                        <tr>
                        <th id="fromid"><spring:message code="message.from" /></th>
                        <th class="date"><spring:message code="message.dateTime" /></th>
                        <th><spring:message code="message.text" /></th>
                        </tr>
                        <form:form method="get" modelAttribute="messages">
                        <c:forEach items="${messages}" var="message">
                        <tr>
                            <td id="fromid"><a href='<spring:url value="/user/${message.messageFromUserId}" ></spring:url>'>${message.messageFromUserId}</a></td>
                            <td>${message.messageDateTime}</td>
                            <td>${message.messageText}</td>
                        </tr>
                        </c:forEach>
                    </table>

                </td>
            </tr>
            <tr class="addingPanel">
                <td></td>
                <td >
                    <form action="/submitMessage" method="post">
                    <input type="hidden" name="FromUserId" value="${user.userId}" />
                    <input type="text" placeHolder="<spring:message code="message.toUserId" />" class="inputId" name="ToUserId"/><br/>
                </td>
            </tr>
            <tr class="addingPanel">
            <td></td>
            <td>
                <textarea placeHolder="<spring:message code="message.text" />" id ="textMessage" name="Text"></textarea><br/>
                <input type="submit" name="Submit" value=<spring:message code="user.add" />>
            </td>
            </tr>
            </form>
        </table>
    </body>
</html>