<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <c:import url="header.jsp" />
    <div class="container container-fluid aamain">
        <c:choose>
            <c:when test="${done != true}">
         <span style="color:red">${error}</span>
        <form action="${initParam.root}Forgot" method="post">
            Enter your username and a link will be emailed to you<br>
            <label>Username: </label><input type="text" name="username" value="${username}"><br>
            <input type="submit"></form>
            </c:when>
            <c:otherwise>
               An email has been sent to the email address in our database with a link to reset your password.
            </c:otherwise>
        </c:choose></div>
               <c:import url="footer.jsp" />
    </body>
</html>
