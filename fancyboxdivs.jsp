<%@page contentType="text/html" pageEncoding="UTF-8"%>
<link rel="stylesheet" href="${initParam.root}js/jquery.fancybox.css?v=2.1.5" type="text/css" media="screen" />
<script type="text/javascript" src="${initParam.root}js/jquery.fancybox.pack.js?v=2.1.5"></script>
<div id="sharediv" style="display:none">
    <h3>Share on...</h3>
        <ul class="soc">
    <li><a id="twittershare" class="soc-twitter" href="#"></a></li>
    <li><a id="facebookshare" class="soc-facebook" href="#"></a></li>
    <li><a id="googleshare" class="soc-google" href="#"></a></li>
    <li><a id="linkedinshare" class="soc-linkedin soc-icon-last" href="#"></a></li>
</ul>
    <br><label style="width:100px">Title:</label> <input type="text" id="sharetitle" value=""><br><br>
    <label style="width:100px">URL:</label> <input type="text" id="shareurl" value="">
        
    </div>
    <div id="reportdiv" style="display:none">
        <div id="thanks" style="position:absolute;background-color:white;width:100%;height:100%"><h2>Thank you for the info</h2></div>
        <p>What is the reason for reporting this link?</p>
        <form id="reportform">
              <label>
      <input type="radio" name="reason" value="Broken link" id="reason_0" checked>
      Broken link</label>
    <br>
    <label>
      <input type="radio" name="reason" value="Wrong category" id="reason_1">
      Wrong category</label>
    <br>
    <label>
      <input type="radio" name="reason" value="Not-relevant" id="reason_2">
      Not-relevant</label>
    <br>
    <label>
      <input type="radio" name="reason" value="Posted in error" id="reason_3">
      Posted in error</label>
    <br>
    <label>
      <input type="radio" name="reason" value="Other" id="reason_4">
      Other</label> <input id="other" type="text" name="other">
      <input type="hidden" id="reportlid" value="">
      <br><br>
      <input type="submit" value="Send" id="reportbtn">
        </form>
    </div>
<script>
    $('.sharelink').fancybox( {
                maxWidth	: 400,
		maxHeight	: 300,
		fitToView	: false,
		width		: '70%',
		height		: '75%',
		autoSize	: false,
		closeClick	: false,
		openEffect	: 'none',
		closeEffect	: 'none'
    })
    $('.reportlink').fancybox( {
                maxWidth	: 400,
		maxHeight	: 300,
		fitToView	: false,
		width		: '70%',
		height		: '75%',
		autoSize	: false,
		closeClick	: false,
		openEffect	: 'none',
		closeEffect	: 'none'
    })
    $('.reportlink').click(function() {
        $("#reportlid").val($(this).attr("data"));
        $("#thanks").hide();
        $("#reportform").show();
    });
    $('.sharelink').click(function() {
        $("#sharetitle").val($(this).attr("data2"));
        $("#shareurl").val($(this).attr("data"));
    })
    $('#twittershare').click(function() {
        window.open("https://twitter.com/intent/tweet?url=" + $('#shareurl').val() + "&text=" + $('#sharetitle').val(),'Share on Twitter', "width=500,height=500");
    })
    $('#facebookshare').click(function() {
        window.open("https://www.facebook.com/sharer/sharer.php?u=" + $('#shareurl').val(),'Share on Facebook', "width=500,height=500");
    })
    $('#googleshare').click(function() {
        window.open("https://plus.google.com/share?url=" + $('#shareurl').val(),'Share on Google+', "width=500,height=500");
    })
    $('#linkedinshare').click(function() {
        window.open("https://www.linkedin.com/shareArticle?mini=true&url=" + $('#shareurl').val() + "&summary=" + $('#sharetitle').val() + "&source=LinkedIn",'Share on LinkedIn', "width=500,height=500");
    })
    $("#reportbtn").click(function() {
            $.ajax({
        dataType: "html",
        method: "POST",
        url: "/ajaxreport",
        data: { linkid: $('#reportlid').val(), other: $('#other').val(), reason: $("input:radio[name='reason']:checked").val() }
        })
        .done(function() {
        });
        $("#thanks").show();
        $("#reportform").hide();
        event.preventDefault();
        });
    </script>   