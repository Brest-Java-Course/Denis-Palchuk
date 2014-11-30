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
            #textMessage {
                width:500px;
                height:100px;
                line-height:15px;
            }
            #textMessageLabel {
                vertical-align: 5px;
            }
            #textTR {
                vertical-align: top;
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
        <h1><spring:message code="user.info" /></h1>
        <table >
            <tr>
                <td>
                    <form:form method="get" modelAttribute="user">
                    <table class="userInfo">
                        <tr><td><spring:message code="user.id" />: </td><td>${user.userId}</td></tr>
                        <tr><td><spring:message code="user.login" />:</td><td>${user.userLogin}</td></tr>
                        <tr><td><spring:message code="user.name" />:</td><td>${user.userName}</td></tr>
                        <tr><td><spring:message code="user.age" />:</td><td>${user.userAge}</td></tr>
                        <form:form method="get" modelAttribute="age">
                        <tr><td>AvAge:  </td><td>${age}</td></tr>
                        <tr><td><a href='<spring:url value="/deleteData" ><spring:param name="userId" value="${user.userId}" /> </spring:url>'><spring:message code="user.del" /></a></td><tr>
                    </table>
                        </form:form>
                        </form:form>
                </td>
                <td>
                    <table id="tabl" border="2">
                        <th><td id="fromid"><spring:message code="message.from" /></td><td>Message</td></th>
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
            <tr>
                <td align="right">
                    <form action="/submitMessage" method="post">
                    <input type="hidden" name="FromUserId" value="${user.userId}" />
                    <label  path="messageToUserId"><spring:message code="message.toUserId" />:</label>
                </td>
                <td>
                    <input type="text" name="ToUserId"/><br/>
                </td>
            </tr>
            <tr id="textTR">
                <td align="right"><label id="textMessageLabel" path="messageText"><spring:message code="message.text" />:</label></td>
                <td><textarea id ="textMessage" name="Text"></textarea><br/></td>
            </tr>
            <tr>
                <td></td>
                <td><input type="submit" name="Submit" value=<spring:message code="user.add" />></td>
            </tr>
            </form>
        </table>
    </body>
</html>