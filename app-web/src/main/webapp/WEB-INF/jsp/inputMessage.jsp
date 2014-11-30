<html>
    <body>
        <form action="" method="locale">
            <select name="locale" >
                <option value="ru_RU">ru</option>
                <option value="en_US">en</option>
            </select>
            <input type="submit" value=<spring:message code="language.change" />>
        </form>
        <h1><spring:message code="message.create" /></h1>

        <form action="/submitMessage" method="post">
            <label path="messageFromUserId"><spring:message code="message.fromUserId" />:</label><input type="text" name="FromUserId"/><br/>
            <label path="messageToUserId"><spring:message code="message.toUserId" />:</label><input type="text" name="ToUserId"/><br/>
            <label path="messageText"><spring:message code="message.text" />:</label><input type="text" name="Text"/><br/>
            <input type="submit" name="Submit" value=<spring:message code="user.add" />>
        </form>
    </body>
</html>