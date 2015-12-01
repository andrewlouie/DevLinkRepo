<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <c:import url="header.jsp" />
<script src="${initParam.root}js/ajaxvote.js"></script>
    <div class="container container-fluid aamain">
        <div class="row">
    <div class="col-sm-3">
<ul class="nav nav-pills nav-stacked">
  <li role="presentation"><a href="${url}">Profile</a></li>
  <li role="presentation"><a href="${url}?q=Links">Links</a></li>
  <li role="presentation" class="active"><a href="${url}?q=UpVotes">UpVotes</a></li>
</ul></div>
<div class="col-sm-9"><c:if test="${empty links}">
            <h1>This user has no UpVotes yet</h1>
        </c:if>
        <c:forEach items="${links}" var="link">
        <%@ include file="eachlink.jsp" %>
        </c:forEach>
</div></div></div>
<c:import url="fancyboxdivs.jsp"/>
        <c:import url="footer.jsp" />
    </body>
</html>
