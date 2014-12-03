<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="joda" uri="http://www.joda.org/joda/time/tags"%>
<!-- Tell the JSP Page that please do not ignore Expression Language -->
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <link href="<c:url value="/resources/css/anytime.5.0.5.min.css" />" rel="stylesheet">
    <style type="text/css">
           #messages {
               width: 800px;
               border-collapse: collapse;
           }
           #messages td , th {
               padding: 3px;
               border: 1px solid black;
           }
           TH {
               background: #b0e0e6;
           }
    </style>
     <script src="<c:url value="/resources/js/jquery-1.11.1.js" />"></script>
    <script src="<c:url value="/resources/js/anytime.5.0.5.js" />"></script>
    <script src="<c:url value="/resources/js/anytimetz.js" />"></script>
    <script src="<c:url value="/resources/js/main.js" />"></script>


</head>

<body>
    <form action="" method="locale">
        <select name="locale" >
            <option value="ru_RU">ru</option>
            <option value="en_US">en</option>
        </select>
        <input type="submit" value=<spring:message code="language.change" />>
    </form>
    <h1 align="center"><spring:message code="message.list" /></h1>
    <table>
        <tr align="center">
            <td>


    <form:form method="get" modelAttribute="messages">
    <ul>
        <table id="messages">
            <th>
                <td><spring:message code="user.id" /></td>
                <td><spring:message code="message.fromUserId" /></td>
                <td><spring:message code="message.toUserId" /></td>
                <td><spring:message code="message.text" /></td>
                <td><spring:message code="message.dateTime" /></td>
            </th>
            <c:forEach items="${messages}" var="message">
            <tr>
                <td/>
                <td>${message.messageId}</td>
                <td><a href='<spring:url value="/user/${message.messageFromUserId}" ></spring:url>'>${message.messageFromUserId}</a></td>
                <td><a href='<spring:url value="/user/${message.messageToUserId}" ></spring:url>'>${message.messageToUserId}</a></td>
                <td>${message.messageText}</td>
                <td><joda:format value="${message.messageDateTime}" style="SS" /></td>
                 <td><a href='<spring:url value="/deleteMessage" ><spring:param name="messageId" value="${message.messageId}" /> </spring:url>'><spring:message code="user.del" /></a></td>
            </tr>
            </c:forEach>
        </table>
    </ul>
    </form:form>

    <form id="filterForm" action="/filterList" method="post">
    From:<input type="text" id="dateTimeField" name="fromDate"/>
        <script>
            var loc="${pageContext.response.locale}";
            if (loc=='en_US') {
                AnyTime.picker('dateTimeField',
                    { format: "%m/%d/%Y %r", firstDOW: 0 } );

            } else {
                AnyTime.picker('dateTimeField',
                    {   format: "%Y-%m-%d %H:%i:%s",
                        formatUtcOffset: "%: (%@)"} );

             }
        </script>
    To: <input type="text" id="dateTimeField2" name="toDate"/>

       <script>
                   var loc="${pageContext.response.locale}";
                   if (loc=='en_US') {
                       AnyTime.picker('dateTimeField2',
                           { format: "%m/%d/%Y %r", firstDOW: 0 } );
                   } else {
                       AnyTime.picker('dateTimeField2',
                           {   format: "%Y-%m-%d %H:%i:%s",
                               formatUtcOffset: "%: (%@)"} );

                    }
               </script></br>

    <input type="submit" name="Submit">
    </form>
    <script>
    if ("${pageContext.response.locale}"=='en_US') {
        $( "#filterForm" ).submit(function() {
            var englishVer=$('#dateTimeField').val();
            var conv= new AnyTime.Converter({format: "%m/%d/%Y %r"});
            var dt=conv.parse($('#dateTimeField').val());
            var conv=new AnyTime.Converter({format: "%Y-%m-%d %H:%i:%s"});
            var result= conv.format( dt );
            $('#dateTimeField').val(result);
            var englishVer=$('#dateTimeField2').val();
            var conv= new AnyTime.Converter({format: "%m/%d/%Y %r"});
            var dt=conv.parse($('#dateTimeField2').val());
            var conv=new AnyTime.Converter({format: "%Y-%m-%d %H:%i:%s"});
            var result= conv.format( dt );
            $('#dateTimeField2').val(result);
        })
    };
    </script>
    </tr>
    <tr>
    <td>
    <form action="/submitMessage" method="post">
        <table>
            <tr><td><label path="messageFromUserId"><spring:message code="message.fromUserId" />:</label></td><td><input type="text" name="FromUserId"/><br/></td></tr>
                <td><label path="messageToUserId"><spring:message code="message.toUserId" />:</label></td><td><input type="text" name="ToUserId"/><br/></td></tr>
                <td><label path="messageText"><spring:message code="message.text" />:</label></td><td><input type="text" name="Text"/><br/></td></tr>
                <td><input type="submit" name="Submit" value=<spring:message code="user.add" />></td></tr>
        </table>
    </form>
    </td>
    </tr>
</body>
</html>