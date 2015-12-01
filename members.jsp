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
        $("#submit").click(function(event) {
            var err = false;
            var regex = /^[A-Za-z0-9_.]{5,30}$/;
            if (!regex.test($("#username").val())) err = true;
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
            <div class="row">
                <form action="${initParam.root}Member" enctype="multipart/form-data" method="post">
                <div class="col-sm-6">
    
        <label>First Name:</label> <input type="text" id="fname" name="firstname" value="${requestScope.user.firstName}" maxlength="30"> <span style="color:red">*</span> <span id="fnmessage"></span><br>
<label>Last Name:</label> <input type="text" id="lname" name="lastname" value="${requestScope.user.lastName}" maxlength="30"> <span style="color:red">*</span> <span id="lnmessage"></span><br>
<label>Email:</label> <input type="email" id="email" name="email" value="${requestScope.user.email}" maxlength="50"> <span style="color:red">*</span> <span id="emmessage"></span><br>
<label>Username:</label> <input type="text" name="username" id="username" value="${requestScope.user.userName}" maxlength="30"> <span style="color:red">*</span> <span id="unmessage"></span><br><label>Website:</label> <input type="url" name="website" value="${requestScope.user.website}" maxlength="500"><br>
<label>Skill Level:</label> <select name="skilllevel">
<option value="0" <c:if test="${requestScope.user.skilllevel == '0'}">selected</c:if>>Beginner</option>
<option value="1" <c:if test="${requestScope.user.skilllevel == '1'}">selected</c:if>>Intermediate</option>
<option value="2" <c:if test="${requestScope.user.skilllevel == '2'}">selected</c:if>>Advanced</option>
<option value="3" <c:if test="${requestScope.user.skilllevel == '3'}">selected</c:if>>Expert</option>
</select><br>
<label>About Me:</label><textarea name="description" maxlength="500" rows="5" cols="35">${requestScope.user.description}</textarea><br>
<label>Languages of interest:</label> <input type="text" name="languages" value="${requestScope.user.languages}" maxlength="500"><br>
<label></label> <a href="${initParam.root}ChangePassword">Change password</a><br>
<label></label> <input type="submit" id="submit" name="submit" value="Submit">
    </div><div class="col-sm-6">
         <span style="color:red">${message}</span>
         <h3>Upload a profile picture</h3>
<img src="${profilepic}" alt="Profile Picture" style="max-height:100px;max-width:100px;height:auto;">
<br><br>
<input type="file" name="file" size="50"/>
<br />
<input name="submit" type="submit" value="Upload File" />

<br><br><a href="${initParam.root}User/${requestScope.user.userName}">View your profile</a>
    </div>
</form>            </div>

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) --> 
<script src="js/jquery-1.11.2.min.js"></script>

<!-- Include all compiled plugins (below), or include individual files as needed --> 
<script src="js/bootstrap.js"></script>
</div>
<c:import url="footer.jsp" />
    </body>
</html>
