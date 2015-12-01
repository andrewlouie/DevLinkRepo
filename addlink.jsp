<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <c:import url="header.jsp" />
    <div class="container container-fluid aamain">
           <c:if test="${not empty errors}">Please correct the following errors:<ul>
    <c:forEach var="err" items="${errors}">
        <li>${err}</li>      
    </c:forEach>
    </ul></c:if>
        <form action="${initParam.root}SubmitLink" method="post">
<label>Title:</label> <input type="text" name="title"  maxlength="75" value="${link.title}" style="width: 250px;"><br>
<label>Description:</label> <input type="text" name="description" maxlength="500" value="${link.description}" style="width: 250px;"><br>
<label>URL:</label> <input type="url" name="url" value="${link.url}" style="width: 250px;" maxlength="500" ><br>
<label>Category:</label> <select name="category">
     <c:forEach var="cat" items="${Categories}">
         <option value="${cat.catId}" <c:if test="${link.category.catId == cat.catId}">selected</c:if>>${cat.catName}</option>  
    </c:forEach>
</select><br>
<label>Type:</label> <select name="type">
<c:forEach var="type" items="${Types}">
    <option value="${type.typeId}" <c:if test="${link.type.typeId == type.typeId}">selected</c:if>>${type.typeName}</option>  
    </c:forEach>
</select><br>
<label>Skill Level:</label> <select name="skilllevel">
<option value="0" <c:if test="${link.skilllevel == '0'}">selected</c:if>>Beginner</option>
<option value="1" <c:if test="${link.skilllevel == '1'}">selected</c:if>>Intermediate</option>
<option value="2" <c:if test="${link.skilllevel == '2'}">selected</c:if>>Advanced</option>
<option value="3" <c:if test="${link.skilllevel == '3'}">selected</c:if>>Expert</option>
</select><br><label></label><span style="color:red;">Warning: You cannot edit or delete a link after posting</span><br>
<label></label> <input type="submit" name="submit" value="Submit">
</div>
<c:import url="footer.jsp" />
    </body>
</html>
