<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <c:import url="header.jsp" />
    <div class="container container-fluid aamain">
        <h1>Suggest A New Category</h1>
        <form method="post">
            <label></label> <span style="color:red">${message}</span><br>
        <label>Category Name:</label> <input type="text" name="newcat" required><br>
        <label></label><input type='hidden' name='form' value='suggest' /> <input type="submit" name="submit" value="Submit">
        </form>
    </div>
        <c:import url="footer.jsp" />
    </body>
</html>
