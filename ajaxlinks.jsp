<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:forEach items="${links}" var="link">
<%@ include file="eachlink.jsp" %>
</c:forEach>