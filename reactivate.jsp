<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <c:import url="header.jsp" />
    <div class="container container-fluid aamain">
        <h1>Resend Activation Email</h1>
        Welcome enter your username and password and a valid email address.<br>
        <span style="color:red">${error}</span>
        <form action="${initParam.root}Reactivate" method="post">
            <label>Username: </label><input type="text" name="username" value="${username}"><br>
            <label>Password: </label><input type="password" name="password"><br>
            <label>Email: </label><input type="email" name="email" value="${email}"><br>
        <input type="submit" name="submit">
        </form>
    </div>
        <c:import url="footer.jsp" />
    </body>
</html>
