<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <c:import url="header.jsp" />
    <div class="container container-fluid aamain">
        <h2>Your account has been created. <br> 
        An email has been sent to ${user2.email}.
            <br>Please check your email and click the link to login.</h2>
    </div>
            <c:import url="footer.jsp" />
    </body>
</html>
