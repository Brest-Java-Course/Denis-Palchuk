<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
    <body>
        <form action="" method="locale">
            <select name="locale" >
                <option value="ru_RU">ru</option>
                <option value="en_US">en</option>
            </select>
            <input type="submit" value=<spring:message code="language.change" />>
        </form>
        <h1><spring:message code="user.create" /></h1>

        <form action="/submitData" method="post">
        <table border=0>
            <tr>
                <td><label path="userLogin"><spring:message code="user.login" />:</label></td><td><input type="text" name="Login"/><br/></td>
            </tr>
            <tr>
                <td><label path="userName"><spring:message code="user.name" />:</label></td><td><input type="text" name="Name"/><br/></td>
            </tr>
            <tr>
                <td><label path="userAge"><spring:message code="user.age" />:</label></td><td><input type="text" name="Age"/><br/></td>
            </tr>
            <tr>
                <td><input type="submit" name="Submit" value=<spring:message code="user.add" />></td>
            </tr>
        </table>
        </form>
    </body>
</html>