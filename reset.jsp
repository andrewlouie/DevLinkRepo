<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <c:import url="header.jsp" />
    <div class="container container-fluid aamain">
        <h1>Enter a new password</h1>
        <span style="color:red">${error}</span>
        <form action="${initParam.root}Reset" method="post">
            <input type="hidden" name="passreset" value="${passreset}">
              <label>Password: </label><input name="pass1" type="password">
        <label>Repeat password: </label><input name="pass2" type="password">
        <input type="submit">
        </form>
    </div>
          <c:import url="footer.jsp" />
    </body>
</html>
