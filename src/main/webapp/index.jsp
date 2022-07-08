<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>

<head>
    <title>Emis</title>
</head>

<body>
<br />
<br />
<br />
<a style="position:absolute;right:1rem" href="https://freeuni.edu.ge/"> Go to University WebPage</a>
<br />
<br />

<div style="text-align: center">
    <h1 style="text-align: center">
        Welcome to BetterEmis.
    </h1>
    <div style="position:relative;text-align: left; zoom: 125%;display: inline-block">
        <form action="loginServlet" method="post">
            <a>Email:</a>
            </br>
            <input type="text" name="mail">
            </br>
            </br>
            <a>Password:</a>
            </br>
            <input type="password" name="pass">
            <p style="margin:10px;"></p>
            <a style="position:absolute; zoom: 80%; left: -4rem" href="register.jsp"> Don't yet have account?</a>
            <a style="position: absolute; zoom: 80%; right: -4rem" href="resetPass.jsp">Forgot Password?</a>
            </br>
            </br>
            <input style="position:absolute; right: -4rem" type="submit" name="login">
        </form>
    </div>
</div>
</body>

</html>
