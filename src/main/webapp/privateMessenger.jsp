<%@ page import="Model.Message" %>
<%@ page import="Model.User" %>
<%@ page import="DAO.Mapping" %>
<%@ page import="DAO.Interfaces.*" %>
<%@ page import="java.util.List" %>
<%@ page import="static Helper.ErrorPageRedirector.redirect" %><%--
  Created by IntelliJ IDEA.
  User: dito
  Date: 07.08.22
  Time: 22:26
  To change this template use File | Settings | File Templates.
--%>

<%
  User user = (User) session.getAttribute(Mapping.USER_OBJECT);
  if(user == null) {
    redirect(request, response);
    return;
  }
  MailDAO mailDAO = (MailDAO) application.getAttribute(Mapping.MAIL_DAO);
  UserDAO userDAO = (UserDAO) application.getAttribute(Mapping.USER_DAO);
  String receiver = request.getParameter(Mapping.RECEIVER);
  User recUser = userDAO.getUserByEmail(receiver);
  List<Message> messageList = mailDAO.getAllMails(user.getEmail(), receiver, true);

%>

<%!

  private String decorate(Message message, String order){
    String res = "<div class=\"msg "+order+"-msg\">\n" +
            "      <div\n" +
            "              class=\"msg-img\"\n" +
            "               style = style=\"background-color: #00a5db\" "+
            "      ></div>\n" +
            "\n" +
            "      <div class=\"msg-bubble\">\n" +
            "        <div class=\"msg-info\">\n" +
            "          <div class=\"msg-info-name\">" + message.getSender() + "</div>\n" +
            "          <div class=\"msg-info-time\">" + message.getDate().toString() + "</div>\n" +
            "        </div>\n" +
            "\n" +
            "        <div class=\"msg-text\">\n" +
            "          " + message.getMessage() + "\n" +
            "        </div>\n" +
            "\n" +
            "      </div>\n" +
            "\n" +
            "    </div>";

    return res;
  }
%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
  <link rel="stylesheet" href="css/messengerStyle.css">
</head>
<body style ="background-color: #363b41">
<section class="msger">
  <header class="msger-header">
    <div class="msger-header-title">
      <i class="fas fa-comment-alt"></i> <%= "Chat with " + recUser.getType().toString()%>
    </div>
    <div class="msger-header-options">
      <span><i class="fas fa-cog"></i></span>
    </div>
  </header>

  <main class="msger-chat" id = "messField">

    <%
      for(Message message : messageList){
        String order = message.getSender() == user.getEmail() ? "right" : "left" ;
        out.println(decorate(message,order));
      }
    %>

  </main>

  <form class="msger-inputarea" action="ServletDirectSend" method="post">
    <input type = "hidden" name = <%=Mapping.SENDER%> value= <%=user.getEmail()%> >
    <input type = "hidden"  name = <%=Mapping.RECEIVER%> value=<%=receiver%>>
    <input type="text" name = "sendMessage" class="msger-input" placeholder="Enter your message...">
    <button type="submit" class="msger-send-btn">Send</button>
  </form>
</section>

<script type="text/javascript">

  //To be scrolled at the bottom of the chat when screen is loaded.
  window.onload = function (){
    const element = document.getElementById("messField");
    element.scrollTop = element.scrollHeight;
  };

</script>

<a href="friends.jsp"> Go to friends list </a>
</body>
</html>
