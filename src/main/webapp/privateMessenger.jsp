<%@ page import="Model.Message" %>
<%@ page import="Model.User" %>
<%@ page import="DAO.Mapping" %><%--
  Created by IntelliJ IDEA.
  User: dito
  Date: 07.08.22
  Time: 22:26
  To change this template use File | Settings | File Templates.
--%>


<%
  User user = (User) session.getAttribute(Mapping.USER_OBJECT);
%>

<%!



  private String decorate(Message message){
    return "";
  }
%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
  <link rel="stylesheet" href="css/messengerStyle.css">
</head>
<body>
<section class="msger">
  <header class="msger-header">
    <div class="msger-header-title">
      <i class="fas fa-comment-alt"></i> SimpleChat
    </div>
    <div class="msger-header-options">
      <span><i class="fas fa-cog"></i></span>
    </div>
  </header>

  <main class="msger-chat">

    <div class="msg left-msg">

      <div
              class="msg-img"
              style="background-image: url(https://image.flaticon.com/icons/svg/327/327779.svg)"
      ></div>

      <div class="msg-bubble">

        <div class="msg-info">
          <div class="msg-info-name">BOT</div>
          <div class="msg-info-time">12:45</div>
        </div>

        <div class="msg-text">
          Hi, welcome to SimpleChat! Go ahead and send me a message. 😄
        </div>

      </div>

    </div>

    <div class="msg right-msg">
      <div
              class="msg-img"
              style="background-image: url(https://image.flaticon.com/icons/svg/145/145867.svg)"
      ></div>

      <div class="msg-bubble">
        <div class="msg-info">
          <div class="msg-info-name">Sajad</div>
          <div class="msg-info-time">12:46</div>
        </div>

        <div class="msg-text">
          You can change your name in JS section!
        </div>

      </div>

    </div>


  </main>

  <form class="msger-inputarea">
    <input type="text" class="msger-input" placeholder="Enter your message...">
    <button type="submit" class="msger-send-btn">Send</button>
  </form>
</section>
</body>
</html>
