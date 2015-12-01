<%@ page import="java.time.LocalDate" %>
<footer>
&copy; Copyright 
<%
   out.print(LocalDate.now().getYear());
%>
<a href="http://andrewaarondev.com">Andrew Aaron</a></footer>