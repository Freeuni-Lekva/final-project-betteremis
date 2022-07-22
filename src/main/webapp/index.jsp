<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>

<head>
    <title>Emis</title>
    <script src="https://unpkg.com/bcryptjs@2.4.3/dist/bcrypt.min.js"></script>

    <script src="https://code.jquery.com/jquery-3.6.0.min.js"
            integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4="
            crossorigin="anonymous"></script>
</head>

<body>
<br />
<br />
<br />
<a style="position:absolute;right:1rem" href="https://freeuni.edu.ge/"> Go to University WebPage</a>
<br />
<a style="position:absolute;right:1rem" href="profile.jsp"> Go to Profile WebPage</a>
<br />

<div style="text-align: center">
    <h1 style="text-align: center">
        Welcome to BetterEmis.
    </h1>
    <%
        if(request.getAttribute("incorrect") != null && (boolean) request.getAttribute("incorrect")){
    %>
            <a style="color: red">* Provided Email or the Password is incorrect.</a>
            </br>
    <%
        }
    %>
    <div style="position:relative;text-align: left; zoom: 125%;display: inline-block">
        <form class="login-form" action="loginServlet" method="post">
            <a>Email:</a>
            </br>
            <input type="text" name="email" id="email" required>
            <p style="margin:8px;"></p>
            <a>Password:</a>
            </br>
            <input type="password" name="pass" id="pass" required>
            <p style="margin:10px;"></p>
            <a style="position:absolute; zoom: 80%; left: -4rem" href="register.jsp"> Don't yet have account?</a>
            <a style="position: absolute; zoom: 80%; right: -4rem" href="resetPass.jsp">Forgot Password?</a>
            </br>
            </br>
            <input style="position:absolute; right: -4rem" type="submit" value="Login" id="loginButton">
        </form>
    </div>
</div>
</body>

</html>
