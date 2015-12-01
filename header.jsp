<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="title" content="DLR - Dev Link Repo">
	<meta name="description" content="A repository of developer and designer links.  Share your favourites and browse through others.">
    <meta name="keywords" content="jquery, javascript, html5, html, css, css3, php, design, fonts, ruby, android, ios, apple, wordpress, sql, mysql, sqlserver, c#, vb.net, python, django, java, framework, asp, microsoft, links, resources, plugins, tutorials, snippits, guides">
	<meta name="author" content="Andrew Aaron">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="shortcut icon" href="favicon.ico" type="image/x-icon">
<link rel="icon" href="${initParam.root}favicon.ico" type="image/x-icon">
<link href="${initParam.root}css/bootstrap.css" rel="stylesheet" type="text/css">
<link href="${initParam.root}css/stylesheet.css" rel="stylesheet" type="text/css">
<title>Dev Link Repo  - ${title}</title>
<link href='https://fonts.googleapis.com/css?family=Karma' rel='stylesheet' type='text/css'>
  <!--[if lt IE 9]>
		  <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
		  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
	<![endif]-->
  </head>
  <body>
<script>
  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
  })(window,document,'script','//www.google-analytics.com/analytics.js','ga');

  ga('create', 'UA-68695744-3', 'auto');
  ga('send', 'pageview');

</script>
  <header>
<a href="${initParam.root}"><img src="${initParam.root}includes/iconme.png" width="417" height="417" alt="Logo" class="aasmall"/></a>
  <nav class="sitenav aanav">

<ul>
	<li><a href="${initParam.root}FAQ">F A Q</a></li>
	<li><a href="${initParam.root}About">About</a></li>
	<li><a href="${initParam.root}Rules">Rules</a></li>
	<li><a href="${initParam.root}Suggest">Suggest A New Category</a></li>
	<li><a href="${initParam.root}Contact">Contact</a></li>
</ul>
  </nav>
        <div class="row aawelcome"><div class="col-xs-12">
                <span class="summary">Browse developer resources and share your favourites!</span>
                <c:if test="${user.userName != null}"><span class="aawell pull-right">Welcome, ${user.userName}</span></c:if>
            </div>
    </div>
<span class="aaaddlink">
    <a href="${initParam.root}SubmitLink"><img src="${initParam.root}includes/add.png" width="128" height="128" alt="Add" class="aaicon aasubmit"/><span class="aatextlink">Submit A Link</span><span class="mobtextlink">Add</span></a></span>
 <nav class="membernav aanav">
 <ul>
<form action="${initParam.root}">
 	<li class="aaboxed">
        <label>Search:</label> <input type="text" name="search" value="">
        <br>
        <span class="aasearchoptions">
        <label>Category:</label> <select name="category">
    <option <c:if test="${selectedcat == null}">selected</c:if>>All</option>
    <c:forEach var="cat" items="${Categories}">
         <option value="${cat.catId}" <c:if test="${selectedcat == cat.catId}">selected</c:if>>${cat.catName}</option>  
    </c:forEach>
</select>
<label>Type:</label> <select name="type">
    <option value="" <c:if test="${selectedtype == null}">selected</c:if>>All</option>

<c:forEach var="type" items="${Types}">
    <option value="${type.typeId}" <c:if test="${selectedtype == type.typeId}">selected</c:if>>${type.typeName}</option>  
</c:forEach>
</select>
    <label>Skill Level:</label> <select name="skilllevel">
<option value=""<c:if test="${selectedskill == null}"> selected</c:if>>All</option>
        <option value="0"<c:if test="${selectedskill == 0}"> selected</c:if>>Beginner</option>
<option value="1"<c:if test="${selectedskill == 1}"> selected</c:if>>Intermediate</option>
<option value="2"<c:if test="${selectedskill == 2}"> selected</c:if>>Advanced</option>
<option value="3"<c:if test="${selectedskill == 3}"> selected</c:if>>Expert</option>    </select></span></li>
<li class="mobhidden">        <div class="sicon"><input type="image" src="${initParam.root}includes/search.png" width="128" height="103" alt="Search" class="aasearchicon"/><span class="mobtextlink mobtextlink2">Search</span></div></li></form>
<c:choose><c:when test="${user.userName == null}">
<li class="wider"><a href="${initParam.root}Register"><figure><img src="${initParam.root}includes/profile.png" width="1181" height="1672" alt="Register" class="aaicon"/><figcaption>Register</figcaption></figure></a></li>
 	<li class="wider"><a href="${initParam.root}Login"><figure><img src="${initParam.root}includes/enter.png" width="128" height="128" alt="Login" class="aaicon"/><figcaption>Login</figcaption></figure></a></li>
    </c:when><c:otherwise>
<li class="wider"><a href="${initParam.root}Member"><figure><img src="${initParam.root}includes/profile.png" width="1181" height="1672" alt="Profile" class="aaicon"/><figcaption>Profile</figcaption></figure></a></li>
 	<li class="wider"><a href="${initParam.root}?action=logout"><figure><img src="${initParam.root}includes/logout.png" width="128" height="128" alt="Logout" class="aaicon"/><figcaption>Logout</figcaption></figure></a></li>        
        
    </c:otherwise></c:choose>

     


 </ul>
 </nav>
<div class="row aanavrow">
<div class="col-sm-2 text-center"><a href="${initParam.root}"><img src="${initParam.root}includes/dlr.png" width="445" height="390" alt="DLR Logo" class="mainimg"/></a>
</div><div class="col-sm-10">
 <nav class="categorynav aanav">
 <ul><c:forEach var="cat" items="${Categories}">
        <li><a href="${initParam.root}${aaparam}?category=${cat.catId}">${cat.catName}</a></li>
    </c:forEach>
 </ul>
 </nav>
 </div></div>
<div class="mobsearch">
 

    
<form action="${initParam.root}">    
    <label id="mobsearch"><a id="mobsearchlink" href="#">Search <span class="glyphicon glyphicon-menu-down" aria-hidden="true"></span></a></label><br>
    <div id="slideout">
    <input type="text" name="search" value="">
        <br>
        <label>Category:</label> <br> <select name="category">
    <option <c:if test="${selectedcat == null}">selected</c:if>>All</option>
    <c:forEach var="cat" items="${Categories}">
         <option value="${cat.catId}" <c:if test="${selectedcat == cat.catId}">selected</c:if>>${cat.catName}</option>  
    </c:forEach>
        </select><br>
        <label>Type:</label><br><select name="type">
    <option value="" <c:if test="${selectedtype == null}">selected</c:if>>All</option>

<c:forEach var="type" items="${Types}">
    <option value="${type.typeId}" <c:if test="${selectedtype == type.typeId}">selected</c:if>>${type.typeName}</option>  
</c:forEach>
    </select><br>
    <label>Skill Level:</label><br><select name="skilllevel">
<option value=""<c:if test="${selectedskill == null}"> selected</c:if>>All</option>
        <option value="0"<c:if test="${selectedskill == 0}"> selected</c:if>>Beginner</option>
<option value="1"<c:if test="${selectedskill == 1}"> selected</c:if>>Intermediate</option>
<option value="2"<c:if test="${selectedskill == 2}"> selected</c:if>>Advanced</option>
<option value="3"<c:if test="${selectedskill == 3}"> selected</c:if>>Expert</option>    </select></span><br>
<input type="image" src="${initParam.root}includes/search.png" width="128" height="103" alt="Search" class="aasearchicon"/>
    </div></form>    
    
</div>
 <nav class="mainnav aanav">

 <ul class="tabrow">
     <li <c:if test="${page == '/'}"> class="selected"</c:if>><a href="${initParam.root}">Hot</a></li>
 <li <c:if test="${page == '/Top'}"> class="selected"</c:if>><a href="${initParam.root}Top">Top</a></li>
 <li <c:if test="${page == '/TopUsers'}"> class="selected"</c:if>><a href="${initParam.root}TopUsers">Top Users</a></li>
 <li <c:if test="${page == '/New'}"> class="selected"</c:if>><a href="${initParam.root}New">New</a></li>
</ul></nav>
</header>
	<script src="${initParam.root}js/jquery-1.11.2.min.js"></script>
	<script src="${initParam.root}js/bootstrap.js"></script>
        <script>
    $('#mobsearchlink').click(function(event) {
        $('#slideout').slideToggle("slow");
        event.preventDefault();
    })
</script>
  