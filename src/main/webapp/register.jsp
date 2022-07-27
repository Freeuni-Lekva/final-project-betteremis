<%--
  Created by IntelliJ IDEA.
  User: bena
  Date: 08.07.22
  Time: 17:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<head>
    <title>Registration</title>
    <link href='https://fonts.googleapis.com/css?family=Open+Sans:400,300,300italic,400italic,600' rel='stylesheet' type='text/css'>
    <link href="//netdna.bootstrapcdn.com/font-awesome/3.1.1/css/font-awesome.css" rel="stylesheet">
    <link rel="stylesheet" href="css/registerStyle.scss">
</head>
<body>
<script>
    function adjustableHeightCheck() {
        if (document.getElementById("LECTURER").checked) {
            document.getElementById("fields").style.display = "none";
        } else {
            document.getElementById("fields").style.display = "block";
        }
    }
</script>

<form action="registerServlet" method="POST" name="main_form">
    <h4>Registration info</h4>
    <div><label for="profession">Profession</label></div>
    <input name="profession" id="profession" type="text" placeholder="Enter profession" maxlength="50" pattern="[A-Za-z]{1,50}" title="Profession can not contain numbers" required>
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
    <input name="address" id="addr" type="text" placeholder="Enter address" maxlength="20" pattern=".{6,20}"  title="6 to 20 characters">
    <div><label for="tell">Phone</label></div>
    <input name="number" id="tell" type="tel" placeholder="Enter phone number" maxlength="20" pattern=".{6,20}"  title="6 to 20 characters">

    <div class="dgend">User type</div>
    <span class="gend">
                    S<input type="radio" name="type" value="student" id="STUDENT" checked="checked" onclick="adjustableHeightCheck()">
                    L<input type="radio" name="type" value="lecturer" id="LECTURER" onclick="adjustableHeightCheck()">
    </span>
    <div class = "desc" id="fields">
        <div><label for="group">Group</label></div>
        <input name="groupname" id="group" type="text" placeholder="Enter group name" maxlength="20" pattern=".{6,20}"  title="6 to 20 characters">
        <div><label for="school">School</label></div>
        <input name="school" id="school" type="tel" placeholder="Enter school name" maxlength="20" pattern=".{6,20}"  title="6 to 20 characters">
    </div>
    <input class="button" type="submit" value="Send" name="submit">
</form>
</body>
</html>
