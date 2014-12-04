<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
 <head>
 <link href="<c:url value="/resources/css/style.css" />" rel="stylesheet">
 <title><sitemesh:write property='title'/></title>
 <sitemesh:write property='head'/>
 </head>
  <header>
  <table class="headerAdminLink">
  <tr>
    <td>
        <form action="" method="locale">
  <div class="new-select-style-wpandyou">
            <select name="locale" >
                <option value="ru_RU">ru</option>
                <option value="en_US">en</option>
            </select>
  </div>
            <input type="submit" value=<spring:message code="language.change" />>
        </form>
        </td>
        <td>
  <div>
    <a href='<spring:url value="/admin/messagesList" ></spring:url>'>(Admin)All Messages</a>
     </div>
     </td>
     <td>
       <div>
         <a href='<spring:url value="/admin/usersList" ></spring:url>'>(Admin)All Users</a>
          </div>
          </td>
     </tr>
     </table>
          <sitemesh:write property='header'/>
   </header>
 <body>
 <sitemesh:write property='body'/>
 </body>
</html>