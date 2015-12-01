<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <c:import url="header.jsp" />
    <div class="container container-fluid aamain">
        <h1>Contact</h1>
        <div id="contact"><form method="post" class="form-group">
                <label></label> <span style="color:red">${message}</span><br>
<label for="name">Name: </label><input type="text" name="name" id="name" class="form-control" required><br>
<label for="email">Email: </label><input type="email" name="email" id="email" class="form-control" required=""><br>
<label for="message">Message: </label><textarea rows="10" class="form-control" id="message" name="message" required></textarea><br>
<label></label><input type="hidden" name="sent" value="true"><input type="submit" class="btn btn-default" value="Submit">
 <input type='hidden' name='form' value='contact' /> </form>
</div>
</div>
    </div>
        <c:import url="footer.jsp" />
    </body>
</html>
