<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <c:import url="header.jsp" />
    <div class="container container-fluid aamain">
<div class="row">
    <div class="col-sm-3">
<ul class="nav nav-pills nav-stacked">
  <li role="presentation" class="active"><a href="${url}">Profile</a></li>
  <li role="presentation"><a href="${url}?q=Links">Links</a></li>
  <li role="presentation"><a href="${url}?q=UpVotes">UpVotes</a></li>
</ul></div>
    <div class="col-sm-2"><img src="${initParam.root}${profilepic}" class="multiple-borders" alt="Profile Picture"></div>
    <div class="col-sm-7"><div style="margin-left:25px">
    <strong>Username: </strong>${profile.userName}<br>
    <strong>Name: </strong>${profile.firstName} ${profile.lastName}<br>
    <strong>Date Joined: </strong>${profile.datejoined}<br>
    <strong>Skill Level: </strong>${profile.skill}<br>
    <c:if test="${not empty profile.website}"><strong>Website: </strong><a href="${profile.website}">${profile.website}</a><br></c:if>
    <c:if test="${not empty profile.description}"><strong>About Me: </strong>${profile.description}<br></c:if>
    <c:if test="${not empty profile.languages}"><strong>Languages: </strong>${profile.languages}</c:if>
                </div>
    </div></div>
    </div>
        <c:import url="footer.jsp" />
    </body>
</html>
