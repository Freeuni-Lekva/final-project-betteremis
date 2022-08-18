<%@ page import="static DAO.Mapping.USER_OBJECT" %>
<%@ page import="Model.User" %>
<%@ page import="static Servlets.ErrorMessages.ERROR_PAGE_NOT_FOUND" %>
<html>
<head>
    <title>Chat Tutorial</title>
    <link rel="stylesheet" href="style.css"/>
    <%
        User user = (User) session.getAttribute(USER_OBJECT);
        if(user == null){
            request.setAttribute("incorrect", true);
            request.setAttribute("mess", ERROR_PAGE_NOT_FOUND);
            request.getRequestDispatcher("/invalidUser.jsp").forward(request,response);
        }
    %>
    <script>
        document.addEventListener("DOMContentLoaded", function(){
            document.getElementById("message").addEventListener("keypress", function(event){
                if(event.keyCode === 13){
                    document.getElementById("send").click();
                }
            });
        });
    </script>
</head>
<body>
<header>
    <button id="connect">Connect</button>
    <button id="disconnect" disabled>Disconnect</button>
    <input type="hidden" id="username" value="<%=user.getEmail()%>"  placeholder="Username..." autofocus>
    <input type="button" onclick="location.href='<%=request.getContextPath()%>/index.jsp';" value="Return to homepage" />
</header>
<aside>
    <h5>Online User(s)</h5>
    <ul id="userList">
        <li id="all" class="hoverable">All</li>
    </ul>
</aside>
<article>
    <div id="dialog">
        <span>Chat to <span id="chatTo">All</span></span>
        <div id="message-board"></div>
        <hr>
        <textarea id="message" placeholder="message.." on></textarea>
        <button id="send">Send</button>
    </div>
</article>
<script src="script.js"></script>
</body>
</html>