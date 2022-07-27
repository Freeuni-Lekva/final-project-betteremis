<%--
  Created by IntelliJ IDEA.
  User: dito
  Date: 27.07.22
  Time: 14:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/successfullyStyle.scss">
</head>
<body>
<div class="funds-success-message-container">
  <div class="funds-checkmark-text-container">
    <div class="funds-checkmark-container">
      <svg class="funds-checkmark" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 52 52"><circle class="funds-checkmark-circle" cx="26" cy="26" r="25" fill="none"/><path class="funds-checkmark-check" fill="none" d="M14.1 27.2l7.1 7.2 16.7-16.8"/></svg>

      <div class="funds-display-on-ie">
        <svg class="funds-ie-checkmark" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 52 52"><circle class="funds-ie-checkmark-circle" cx="26" cy="26" r="25" fill="none"/><path class="funds-ie-checkmark-check" fill="none" d="M14.1 27.2l7.1 7.2 16.7-16.8"/></svg>
      </div>
    </div>

    <h1 class="funds-success-done-text">Sent!</h1></div>

  <div class="funds-success-message">



    <h2>Verification Link Successfully Sent</h2>
    <p>Please Check Your Email Inbox.</p>
  </div>
</div>

</body>
</html>
