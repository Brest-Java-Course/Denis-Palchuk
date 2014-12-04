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
    <script src="<c:url value="/resources/js/jquery-1.11.1.js" />"></script>
    <script src="<c:url value="/resources/js/anytime.5.0.5.js" />"></script>
    <script src="<c:url value="/resources/js/anytimetz.js" />"></script>
    <script src="<c:url value="/resources/js/main.js" />"></script>


</head>

<body>
    <h1 align="center"><spring:message code="message.list" /></h1>

        <form:form method="get" modelAttribute="messages">
        <ul>
            <table table class="simple-little-table" cellspacing='0'>
            <tr>
                    <th class="uid"><spring:message code="message.fromUserId" /></td>
                    <th class="uid"><spring:message code="message.toUserId" /></td>
                    <th><spring:message code="message.text" /></td>
                    <th class="date"><spring:message code="message.dateTime" /></td>
                    <th><spring:message code="user.del" /></th>
                </th>
                </tr>
                <c:forEach items="${messages}" var="message">

                <tr>
                    <td><a href='<spring:url value="/user/${message.messageFromUserId}" ></spring:url>'>${message.messageFromUserId}</a></td>
                    <td><a href='<spring:url value="/user/${message.messageToUserId}" ></spring:url>'>${message.messageToUserId}</a></td>
                    <td class="textMess">${message.messageText}</td>
                    <td><joda:format value="${message.messageDateTime}" style="SS" /></td>
                     <td class="deleteMess"><a href='<spring:url value="/deleteMessage" ><spring:param name="messageId" value="${message.messageId}" /> </spring:url>'><spring:message code="user.del" /></a></td>
                </tr>
                </c:forEach>
            </table>
        </ul>
    </form:form>
    <a class="filterLink" href="javascript:hideFilter('div1');"><spring:message code="filter.showHide" /></a>
        <div id="div1" style="display:none">
    <form id="filterForm" action="/admin/filterList" method="post">
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
    <script>
    function hideFilter(type){
    param=document.getElementById(type);
    if(param.style.display == "none") param.style.display = "block";
    else param.style.display = "none"
    }
    </script>

    </div>
    </body>
</html>