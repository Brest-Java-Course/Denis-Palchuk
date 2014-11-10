<html>
    <body>
        <form action="${pageContext.request.contextPath}/mvc/submitdata" method="post">
            <label path="name">Name:</label><input type="text" name="name"/><br/>
            <label path="login">Login:</label><input type="text" name="login"/><br/>
            <input type="submit" value="Submit it!" name="Submit">
        </form>
    </body>
</html>