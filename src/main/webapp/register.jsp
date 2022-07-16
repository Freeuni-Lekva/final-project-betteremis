<%--
  Created by IntelliJ IDEA.
  User: bena
  Date: 08.07.22
  Time: 17:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/js/bootstrap.min.js"></script>
<html>
<head>
    <title>Registration</title>
</head>
<body>
    <h1>Please fill the Registration form</h1>
    <div style="display: inline-block; overflow: hidden;">
        <form>
            <div style="display: inline-block; text-align: center; float: left;">

                <a>First Name</a>
                </br>
                <input type="text" id="firstname" required>
                </br>
                </br>
                <a>Last Name</a>
                </br>
                <input type="text" id="lastname" required>
                </br>
                </br>
                <a>Email</a>
                </br>
                <input type="text" id="email" required>
                </br>
                </br>
                <a>Password</a>
                </br>
                <input type="password" id="pass" required>
                </br>
                </br>
                <a>Gender</a>
                </br>
                <input type="radio" value="Male" name="Gender" checked> <label>Male</label>
                <input type="radio" value="Female" name="Gender"> <label>Female</label>
                </br>
                </br>
                <a>Proffesion</a>
                </br>
                <input type="text" id="proffesion" required>
                </br>
                </br>
                <a>Nationality</a>
                </br>
                <input type="text" id="nationality" required>
                </br>
                </br>
                <a>Date of Birth</a>
                </br>
                <input type="date" id="dateofbirth" required>
                </br>
                </br>
                <a>Address</a>
                </br>
                <input type="text" id="address" required>
                </br>
                </br>
                <a>Phone Number</a>
                </br>
                <input type="number" id="number" required>
                </br>
                </br>
            </div>

            <div style="text-align:center ; margin-left: 320px;">
                <a>Select User type</a>
                <input type="radio" name="type" value="student"  data-target="#studentform" checked> <label>Student</label>
                <input type="radio" name="type" value="lecturer" data-target="#lecturerform"> <label>Lecturer</label>
                <div id="studentform"> this is for student</div>
                <div id="lecturerform">this is for lecturer</div>


            </div>
        </form>
    </div>
</body>
</html>
