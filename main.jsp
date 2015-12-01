<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    <c:import url="header.jsp" />
    <script src="${initParam.root}js/ajaxvote.js"></script>
    <script>
        var ajaxing = false;
        var pageId = "${pageId}";
        $(function(){
        function getMore() {
        if (!ajaxing) {
            ajaxing = true;
            $.ajax({
        url: '/ajaxresults',
        type: 'POST',
        dataType: 'html',
        data: { pageid: pageId }
        }).done(function(data) {  
        $('#aamain').append(data);
        ajaxing = false;
        });
        }
        };
        $(window).scroll(function(){
        var wintop = $(window).scrollTop(), docheight = $(document).height(), winheight = $(window).height();
        var  scrolltrigger = 0.99;
        if  ((wintop/(docheight-winheight)) > scrolltrigger) {
         getMore();
        }
    });
    });
    </script>


    <div class="container container-fluid aamain" id="aamain">
        <c:forEach items="${links}" var="link">
   <%@ include file="eachlink.jsp" %>
    </c:forEach>
        <noscript>
        <fmt:formatNumber var="page" value="${((offset - (offset % 50)) / 50 + 1)}" maxFractionDigits="0"/>
        <fmt:formatNumber var="lastpage" value="${((linkstotal - (linkstotal % 50)) / 50 + 1)}" maxFractionDigits="0"/>
        <c:choose>
            <c:when test="${page-2 <= 1}">
                <c:set var="firstpage" value="1"/>
            </c:when>    
            <c:otherwise>
               <c:set var="firstpage" value="${page-2}"/>
            </c:otherwise>
        </c:choose>
        <nav>
  <ul class="pagination">
    <li>
      <a href="${url}?<c:if test="${search != null && search != ''}">search=${search}&</c:if><c:if test="${selectedcat != 0}">category=${selectedcat}&</c:if><c:if test="${selectedtype != 0}">type=${selectedtype}&</c:if><c:if test="${selectedskill != '-1'}">skilllevel=${selectedskill}&</c:if>offset=0" aria-label="Previous">
        <span aria-hidden="true">&laquo;</span>
      </a>
    </li>
                <li <c:if test="${page == firstpage}">class="active"</c:if>><a href="${url}?<c:if test="${search != null && search != ''}">search=${search}&</c:if><c:if test="${selectedcat != 0}">category=${selectedcat}&</c:if><c:if test="${selectedtype != 0}">type=${selectedtype}&</c:if><c:if test="${selectedskill != '-1'}">skilllevel=${selectedskill}&</c:if>offset=${(firstpage-1)*50}">${firstpage}</a></li>
    <li <c:if test="${page == firstpage+1}">class="active"</c:if><c:if test="${lastpage < firstpage+1}">class="disabled"</c:if>><a href="${url}?<c:if test="${search != null && search != ''}">search=${search}&</c:if><c:if test="${selectedcat != 0}">category=${selectedcat}&</c:if><c:if test="${selectedtype != 0}">type=${selectedtype}&</c:if><c:if test="${selectedskill != '-1'}">skilllevel=${selectedskill}&</c:if>offset=${firstpage*50}">${firstpage+1}</a></li>
    <li <c:if test="${page == firstpage+2}">class="active"</c:if><c:if test="${lastpage < firstpage+2}">class="disabled"</c:if>><a href="${url}?<c:if test="${search != null && search != ''}">search=${search}&</c:if><c:if test="${selectedcat != 0}">category=${selectedcat}&</c:if><c:if test="${selectedtype != 0}">type=${selectedtype}&</c:if><c:if test="${selectedskill != '-1'}">skilllevel=${selectedskill}&</c:if>offset=${(firstpage+1)*50}">${firstpage+2}</a></li>
    <li <c:if test="${page == firstpage+3}">class="active"</c:if><c:if test="${lastpage < firstpage+3}">class="disabled"</c:if>><a href="${url}?<c:if test="${search != null && search != ''}">search=${search}&</c:if><c:if test="${selectedcat != 0}">category=${selectedcat}&</c:if><c:if test="${selectedtype != 0}">type=${selectedtype}&</c:if><c:if test="${selectedskill != '-1'}">skilllevel=${selectedskill}&</c:if>offset=${(firstpage+2)*50}">${firstpage+3}</a></li>
    <li <c:if test="${page == firstpage+4}">class="active"</c:if><c:if test="${lastpage < firstpage+4}">class="disabled"</c:if>><a href="${url}?<c:if test="${search != null && search != ''}">search=${search}&</c:if><c:if test="${selectedcat != 0}">category=${selectedcat}&</c:if><c:if test="${selectedtype != 0}">type=${selectedtype}&</c:if><c:if test="${selectedskill != '-1'}">skilllevel=${selectedskill}&</c:if>offset=${(firstpage+3)*50}">${firstpage+4}</a></li>
    <li>
      <a href="${url}?<c:if test="${search != null && search != ''}">search=${search}&</c:if><c:if test="${selectedcat != 0}">category=${selectedcat}&</c:if><c:if test="${selectedtype != 0}">type=${selectedtype}&</c:if><c:if test="${selectedskill != '-1'}">skilllevel=${selectedskill}&</c:if>offset=${(lastpage-1)*50}" aria-label="Next">
        <span aria-hidden="true">&raquo;</span>
      </a>
    </li>
  </ul>
</nav></noscript>
    <c:if test="${empty links}"><h1>Sorry, no items found.</h1></c:if>
    </div>
    <c:import url="fancyboxdivs.jsp"/>
    <c:import url="footer.jsp" />
</body>
</html>