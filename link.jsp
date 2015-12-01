<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <c:import url="header.jsp" />
    <script>
    $(function() { 
        $(".aabutton").click(function(event) {
        if (this.value == "Up") id = $(this).prev('input[type=hidden]').val();
        else id = $(this).prevAll('input[type=hidden]').val()
        var $self = $(this).siblings('.aascore');
        $.ajax({
        dataType: "json",
        method: "POST",
        url: "${initParam.root}ajaxvote",
        data: { linkid: id, vote: this.value }
        })
        .done(function( msg ) {
            $self.html(msg.score);
            $('#up').html(msg.up);
            $('#down').html(msg.down);
        });
        event.preventDefault();
        });
    });
    </script>
    <div class="container container-fluid aamain">
  <div class="aalink">
      <form action="${initParam.root}Vote" method="post">
<input type="hidden" name="ref" value="${initParam.root}Link/${link.linkId}">

      <div class="gotoarrow"><a href="${link.url}" target="_blank"><span class="glyphicon glyphicon-globe" aria-hidden="true"></span></a><a href="#sharediv" class="sharelink" data="${link.url}" data2="${link.title}"><span class="glyphicon glyphicon-share-alt" aria-hidden="true"></span></a></div>
  <div class="aarrows">
<input type="hidden" name="linkid" value="${link.linkId}">
<button type="submit" class="aabutton" value="Up" name="vote"><span class="glyphicon glyphicon-arrow-up" aria-hidden="true"></span></button>
<br><span class="aascore">${link.score}</span><br>
<button type="submit" class="aabutton" value="Down" name="vote"><span class="glyphicon glyphicon-arrow-down" aria-hidden="true"></span></button>
	</div>
    <div class="linkinfo">
	<div class="linktitle"><a href="${link.url}" target="_blank">${link.title}</a></div>
    <div class="linkdescriptionfull">${link.description}</div>
    <div class="moreinfo"><a href="${initParam.root}Link/${link.linkId}">${link.commentCount} Comment<c:if test="${link.commentCount != 1}">s</c:if></a> - <strong>Posted By:</strong> <a href="${initParam.root}User/${link.user.userName}">${link.user.userName} (${link.user.rating})</a> - <strong>Skill level:</strong> ${link.skill} - <strong>Category:</strong> ${link.category.catName} - <strong>Type:</strong> ${link.type.typeName} - <strong>Date:</strong> ${link.dateTime} - <a href="#reportdiv" class="reportlink" data="${link.linkId}" style="font-weight:bold">Report Link</a></div>
    <strong><span id="up">${link.upVotes}</span> Up Vote<c:if test="${link.upVotes != 1}">s</c:if> - <span id="down">${link.downVotes}</span> Down Vote<c:if test="${link.downVotes != 1}">s</c:if></strong>
    </div> </form> </div>
        <hr>
        <h1>Comments</h1>
        <c:choose>
        <c:when test="${user.userName != null}">
            <form method="post">
                <input type="text" name="comment" maxlength="500" required>
                <input type="submit" value="submit">
            </form></c:when>
        <c:otherwise><a href="${initParam.root}Login?ref=Link/${link.linkId}">Login</a> to leave a comment.<br><br></c:otherwise>
        
    </c:choose>
        <c:forEach items="${link.comments}" var="comment">
            <a href="${initParam.root}User/${link.user.userName}">${comment.user.userName} (${comment.user.rating})</a> &nbsp;&nbsp;${comment.dateTime}<br>
            ${comment.comment}<br><br>
            
            
        </c:forEach>
        <c:if test="${empty link.comments}"><h3>Be the first to leave a comment</h3></c:if>
    </div>
<c:import url="fancyboxdivs.jsp"/>
<c:import url="footer.jsp" />
    </body>
</html>
