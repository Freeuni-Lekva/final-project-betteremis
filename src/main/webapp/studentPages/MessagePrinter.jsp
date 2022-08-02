<%--
  Created by IntelliJ IDEA.
  User: dito
  Date: 25.07.22
  Time: 16:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
<head>
    <meta chartset="utf-8">
    <title>Message</title>
    <link rel="stylesheet" href="<%= request.getAttribute("cssPath")%>">
</head>
<body>
<div class="container">
    <span class="text1"><%= request.getAttribute("userMessage") %></span>
    <span class="text2"><a href="<%= request.getAttribute("path") %>">Go to profile</a></span>
</div>



</body>
</html>
