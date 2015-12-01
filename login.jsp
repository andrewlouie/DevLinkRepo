<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <c:import url="header.jsp" />
        <div class="container container-fluid aamain"><h1>Login</h1>
        Welcome enter your username or email address and password to login.<br>
        <span style="color:red">${error}</span>
        <form action="${initParam.root}Login" method="post">
            <label>Login:</label> <input type="text" name="username" value="${username}" required><br>
        <input type="hidden" name="ref" value="${ref}">
        <label>Password:</label> <input type="password" name="password" required><br>
        <label>Remember me:</label> <input name="remember" value="1" type="checkbox"> <br>
        <label></label> <input type="submit" name="submit">
        <br>
        <label></label> <a href="${initParam.root}Register">Register</a><br>
        <label></label> <a href="${initParam.root}Forgot">I forgot my password</a>
        </form></div>
        <c:import url="footer.jsp" />
    </body>
</html>
