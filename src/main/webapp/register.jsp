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
</head>
<body>
<%--<script>--%>
<%--    $(document).ready(function(){--%>
<%--        $('input[name="type"]').click(function(){--%>
<%--            const inputValue = $(this).attr("value");--%>
<%--            $('div.desc').hide().find(':input').attr('required', false);--%>
<%--            $("#"+inputValue).show().find(':input').attr('required', true);--%>
<%--        });--%>
<%--    });--%>
<%--</script>--%>
<h1>Please fill the Registration form</h1>
<div style="display: inline-block; overflow: hidden;">
    <form class="reg-form" action="registerServlet" method="POST">
        <div style="display: inline-block; text-align: center; float: left;">

            <a>First Name</a>
            </br>
            <input type="text" name="firstname" required>
            </br>
            </br>
            <a>Last Name</a>
            </br>
            <input type="text" name="lastname" required>
            </br>
            </br>
            <a>Email</a>
            </br>
            <input type="text" name="email" required>
            </br>
            </br>
            <a>Password</a>
            </br>
            <input type="password" name="pass" required>
            </br>
            </br>
            <a>Gender</a>
            </br>
            <input type="radio" value="Male" name="Gender" checked> <label>Male</label>
            <input type="radio" value="Female" name="Gender"> <label>Female</label>
            </br>
            </br>
            <a>Profession</a>
            </br>
            <input type="text" name="profession" required>
            </br>
            </br>
            <a>Date of Birth</a>
            </br>
            <input type="date" name="dateofbirth" required>
            </br>
            </br>
            <a>Address</a>
            </br>
            <input type="text" name="address" required>
            </br>
            </br>
            <a>Phone Number</a>
            </br>
            <input type="tel" name="number" required>
            </br>
            </br>
        </div>

        <div style="text-align:center ; margin-left: 320px;">
            <a>Select User type</a>
            <input type="radio" name="type" value="student" checked> <label>Student</label>
            <input type="radio" name="type" value="lecturer"> <label>Lecturer</label>
            <div class="desc" id="student">
                <a>Group Name</a>
                </br>
                <input type="text" name="groupname">
                </br>
                </br>
                <a>School</a>
                </br>
                <input type="text" name="school" required>
                </br>
                </br>
            </div>
            <div class="desc" style="display: none" name="lecturer">
                </br>
            </div>
            <input type="submit" value="Register" id="registerButton">
        </div>
    </form>
</div>
<%--<script src="./registerHashing.js" type="text/javascript"></script>--%>
</body>
</html>
