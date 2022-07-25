<%--
  Created by IntelliJ IDEA.
  User: bena
  Date: 08.07.22
  Time: 17:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.min.js" i--%>
<%--        ntegrity="sha384-QJHtvGhmr9XOIpI6YVutG+2QOK9T+ZnN4kzFN1RtK3zEFEIsxhlmWl5/YESvpZ13"--%>
<%--        crossorigin="anonymous"></script>--%>
<%--<script src="https://code.jquery.com/jquery-3.6.0.min.js"--%>
<%--        integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4="--%>
<%--        crossorigin="anonymous"></script>--%>
<%--<script src="https://unpkg.com/bcryptjs@2.4.3/dist/bcrypt.min.js"></script>--%>
<%--<script src="registerHashing.js"></script>--%>
<html>
<head>
    <title>Registration</title>
    <link href='https://fonts.googleapis.com/css?family=Open+Sans:400,300,300italic,400italic,600' rel='stylesheet' type='text/css'>
    <link href="//netdna.bootstrapcdn.com/font-awesome/3.1.1/css/font-awesome.css" rel="stylesheet">
    <link rel="stylesheet" href="registerStyle.scss">
</head>
<body>
<script>
    $(document).ready(function(){
        $('input[name="type"]').click(function(){
            const inputValue = $(this).attr("value");
            $('div.gend').hide().find(':input').attr('required', false);
            $("#"+inputValue).show().find(':input').attr('required', true);
        });
    });
</script>

<form action="registerServlet" method="POST" name="main_form">
    <h4>Registration info</h4>
    <div><label for="username">Firstname</label></div>
    <input name="firstname" id="username" type="text" placeholder="Enter name" maxlength="50" pattern="[A-Za-z]{1,50}" title="Name can not contain numbers" required>
    <div><label for="userLastname">Lastname</label></div>
    <input name="lastname" id="userLastname" type="text" placeholder="Enter lastname" pattern="[A-Za-z]{1,50}" maxlength="50" title="Lastname can not contain numbers">
    <div class="dgend">Gender</div>
    <span class="gend">
                    M<input type="radio" name="Gender" value="M" id="male" checked="checked">
                    F<input type="radio" name="Gender" value="F" id="female" >
    </span>
    <div><label for="userbday">Birthday</label></div>
    <input type="date" name="dateofbirth" id="userbday" min='1899-01-01' title="Wrong min date">
    <div><label for="useremail">Email Address</label></div>
    <input name="email" id="useremail" type="email" placeholder="Enter email" maxlength="50">
    <div><label for="userPass">Password</label></div>
    <input name="pass" id="userPass" type="password" placeholder="Enter password" maxlength="20" pattern=".{6,20}"  title="6 to 20 characters">
    <span id="info"></span>
    <div><label for="addr">Address</label></div>
    <input name="address" id="addr" type="text" placeholder="Enter password" maxlength="20" pattern=".{6,20}"  title="6 to 20 characters">
    <div><label for="tell">Phone</label></div>
    <input name="number" id="tell" type="tel" placeholder="Enter password" maxlength="20" pattern=".{6,20}"  title="6 to 20 characters">

    <div class="dgend">User type</div>
    <span class="gend">
                    M<input type="radio" name="type" value="S" id="STUDENT" checked="checked">
                    F<input type="radio" name="type" value="L" id="LECTURER" >
    </span>
    <div class = "desc">
        <div><label for="group">Group</label></div>
        <input name="groupname" id="group" type="text" placeholder="Enter password" maxlength="20" pattern=".{6,20}"  title="6 to 20 characters">
        <div><label for="school">School</label></div>
        <input name="school" id="school" type="tel" placeholder="Enter password" maxlength="20" pattern=".{6,20}"  title="6 to 20 characters">
    </div>
    <input class="button" type="submit" value="Send" name="submit">
</form>

<%--<h1>Please fill the Registration form</h1>--%>
<%--<div style="display: inline-block; overflow: hidden;">--%>
<%--    <form class="reg-form" action="registerServlet" method="POST">--%>
<%--        <div style="display: inline-block; text-align: center; float: left;">--%>

<%--            <a>First Name</a>--%>
<%--            </br>--%>
<%--            <input type="text" name="firstname" required>--%>
<%--            </br>--%>
<%--            </br>--%>
<%--            <a>Last Name</a>--%>
<%--            </br>--%>
<%--            <input type="text" name="lastname" required>--%>
<%--            </br>--%>
<%--            </br>--%>
<%--            <a>Email</a>--%>
<%--            </br>--%>
<%--            <input type="text" name="email" required>--%>
<%--            </br>--%>
<%--            </br>--%>
<%--            <a>Password</a>--%>
<%--            </br>--%>
<%--            <input type="password" name="pass" required>--%>
<%--            </br>--%>
<%--            </br>--%>
<%--            <a>Gender</a>--%>
<%--            </br>--%>
<%--            <input type="radio" value="Male" name="Gender" checked> <label>Male</label>--%>
<%--            <input type="radio" value="Female" name="Gender"> <label>Female</label>--%>
<%--            </br>--%>
<%--            </br>--%>
<%--            <a>Profession</a>--%>
<%--            </br>--%>
<%--            <input type="text" name="profession" required>--%>
<%--            </br>--%>
<%--            </br>--%>
<%--            <a>Date of Birth</a>--%>
<%--            </br>--%>
<%--            <input type="date" name="dateofbirth" required>--%>
<%--            </br>--%>
<%--            </br>--%>
<%--            <a>Address</a>--%>
<%--            </br>--%>
<%--            <input type="text" name="address" required>--%>
<%--            </br>--%>
<%--            </br>--%>
<%--            <a>Phone Number</a>--%>
<%--            </br>--%>
<%--            <input type="tel" name="number" required>--%>
<%--            </br>--%>
<%--            </br>--%>
<%--        </div>--%>

<%--        <div style="text-align:center ; margin-left: 320px;">--%>
<%--            <a>Select User type</a>--%>
<%--            <input type="radio" name="type" value="student" checked> <label>Student</label>--%>
<%--            <input type="radio" name="type" value="lecturer"> <label>Lecturer</label>--%>
<%--            <div class="desc" id="student">--%>
<%--                <a>Group Name</a>--%>
<%--                </br>--%>
<%--                <input type="text" name="groupname">--%>
<%--                </br>--%>
<%--                </br>--%>
<%--                <a>School</a>--%>
<%--                </br>--%>
<%--                <input type="text" name="school" required>--%>
<%--                </br>--%>
<%--                </br>--%>
<%--            </div>--%>
<%--            <div class="desc" style="display: none" name="lecturer">--%>
<%--                </br>--%>
<%--            </div>--%>
<%--            <input type="submit" value="Register" id="registerButton">--%>
<%--        </div>--%>
<%--    </form>--%>
<%--</div>--%>
<%--<script src="./registerHashing.js" type="text/javascript"></script>--%>
</body>
</html>
