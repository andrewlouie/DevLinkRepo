<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <c:import url="header.jsp" />
    <div class="container container-fluid aamain">
        <span style="color:red">${error}</span>
        <form action="${initParam.root}ChangePassword" method="post">
            <label>Current Password:</label> <input type="password" name="currentpw" maxlength="30"><br>
        <label>New Password:</label> <input type="password" name="password" maxlength="30"><br>
        <label>Repeat Password:</label> <input type="password" name="repeatpassword" maxlength="30"><br>
        <label></label> <input type="submit" value="Submit">
        </form></div>
        <c:import url="footer.jsp" />
    </body>
</html>
