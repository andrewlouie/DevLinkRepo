<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <c:import url="header.jsp" />
    <script>
        $(function() { 
        //validate username
            $("#username").change(function() {
                un = this.value;
                var regex = /^[A-Za-z0-9_.]{5,30}$/;
                //check regex
                if (!regex.test(un)) { $("#unmessage").html("<br><label></label>Username is not valid. <br><label></label>Must be 5-30 characters and can contain _ and ."); }
                else{
                    $("#unmessage").html("");
             $.ajax({
        dataType: "json",
        method: "POST",
        url: "/ajaxun",
        data: { username: un }
        })
        .done(function( msg ) {
            if (msg) { $("#unmessage").html("<br><label></label>Username is already taken"); }
            else { $("#unmessage").html(""); }
        });
        }
        });
        $("#email").change(function() {
             $.ajax({
        dataType: "json",
        method: "POST",
        url: "/ajaxem",
        data: { email: this.value }
        })
        .done(function( msg ) {
            if (msg) { $("#emmessage").html("<br><label></label>Email is already registered"); }
            else { $("#emmessage").html(""); }
        });
        });
        $("#password").change(function() {
            un = this.value;
                var regex = /^.{8,30}$/;
                //check regex
                if (!regex.test(un)) { $("#pwmessage").html("<br><label></label>Password is not valid. <br><label></label>Must be 8-30 characters long"); }
                else{
                    $("#pwmessage").html("");
                }
        });
        $("#password2").change(function() {
            pw = this.value;
            if (pw != $("#password").val()) { $("#pw2message").html("<br><label></label>Password does not match"); }
                else{
                    $("#pw2message").html("");
                }
        });
        $("#submit").click(function(event) {
            var err = false;
            var pwregex = /^.{8,30}$/;
            var regex = /^[A-Za-z0-9_.]{5,30}$/;
            if ($("#password2").val() != $("#password").val()) err = true;
            else if (!regex.test($("#username").val())) err = true;
            else if (!pwregex.test($("#password").val())) err = true;
            if ($("#fname").val() == "") { $("#fnmessage").html("<br><label></label>First Name is required."); err = true; }
            if ($("#lname").val() == "") { $("#lnmessage").html("<br><label></label>Last Name is required."); err = true; }
            if ($("#email").val() == "") { $("#emmessage").html("<br><label></label>Email is required."); err = true; }
            if (err) { alert("Please correct the errors on the form before submitting"); event.preventDefault(); }
        });
    });
    </script>
    <div class="container container-fluid aamain">
    <c:if test="${not empty errors}">Please correct the following errors:<ul>
    <c:forEach var="err" items="${errors}">
        <li>${err}</li>      
    </c:forEach>
    </ul></c:if>
    
<div class="row"><form action="${initParam.root}Register" enctype="multipart/form-data" method="post">
    <div class="col-sm-6">
        <label></label> <span style="color:red">* = required</span>
        <input type="hidden" name="pic" value="${pic}"><br>
        <label>First Name:</label> <input type="text" id="fname" name="firstname" value="${requestScope.user2.firstName}" maxlength="30"> <span style="color:red">*</span> <span id="fnmessage"></span><br>
<label>Last Name:</label> <input type="text" id="lname" name="lastname" value="${requestScope.user2.lastName}" maxlength="30"> <span style="color:red">*</span> <span id="lnmessage"></span><br>
<label>Email:</label> <input type="email" id="email" name="email" value="${requestScope.user2.email}" maxlength="50"> <span style="color:red">*</span> <span id="emmessage"></span><br>
<label>Username:</label> <input type="text" name="username" id="username" value="${requestScope.user2.userName}" maxlength="30"> <span style="color:red">*</span> <span id="unmessage"></span><br>
<label>Password:</label> <input type="password" id="password" name="password" maxlength="30"> <span style="color:red">*</span> <span id="pwmessage"></span><br>
<label>Repeat Password:</label> <input type="password" id="password2" name="repeatpassword" maxlength="30"> <span style="color:red">*</span>  <span id="pw2message"></span><br>
<label>Website:</label> <input type="url" name="website" value="${requestScope.user2.website}" maxlength="500">  <br>
<label>Skill Level:</label> <select name="skilllevel">
<option value="0" <c:if test="${requestScope.user2.skilllevel == '0'}">selected</c:if>>Beginner</option>
<option value="1" <c:if test="${requestScope.user2.skilllevel == '1'}">selected</c:if>>Intermediate</option>
<option value="2" <c:if test="${requestScope.user2.skilllevel == '2'}">selected</c:if>>Advanced</option>
<option value="3" <c:if test="${requestScope.user2.skilllevel == '3'}">selected</c:if>>Expert</option>
</select><br>
<label>About Me:</label> <textarea name="description" maxlength="500" rows="5" cols="35">${requestScope.user2.description}</textarea><br>
<label>Languages of interest:</label> <input type="text" name="languages" value="${requestScope.user2.languages}" maxlength="500"><br>
<label></label> <input type="submit" id="submit" name="submit" value="Submit">
</div>
<div class="col-sm-6 profpicture">
    <span style="color:red">${message}</span>
    <h3>Upload a profile picture</h3>
    <img src="${profilepic}" alt="Profile Picture" style="max-height:100px;max-width:100px;height:auto;"><br><br>
    
<input type="file" name="file" size="50"/>
<br />
<input name="submit" type="submit" value="Upload File" />
</div></form></div>


<!-- jQuery (necessary for Bootstrap's JavaScript plugins) --> 
<script src="js/jquery-1.11.2.min.js"></script>

<!-- Include all compiled plugins (below), or include individual files as needed --> 
<script src="js/bootstrap.js"></script></div>
<c:import url="footer.jsp" />
</body>
</html>
