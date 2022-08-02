<%@ page import="DAO.Interfaces.UserDAO" %>
<%@ page import="DAO.Mapping" %>
<%@ page import="Model.User" %>
<%@ page import="java.util.List" %>
<%
    UserDAO userDAO = (UserDAO) application.getAttribute(Mapping.USER_DAO);
    User admin = (User) application.getAttribute(Mapping.USER_OBJECT);
    List<User> allUsers = userDAO.getAllUsers();
%>

<%!
    public String decorate(User user){
        return  "<tr>\n" +
                "        <td>" + user.getEmail() + "</td>\n" +
                "        <td> " + user.getType().toString() + "</td>\n" +
                "        <td>" +
                "           <form action = \"../ServletStatus\" method = \"POST\">" +
                "             <input type = \"hidden\" name = \"email\" value = \""+ user.getEmail() + "\"/>" +
                "             <input type = \"submit\" value = \"Click to edit\"/>" +
                "           </form></td>\n" +
                "</tr>\n";
    }
%>


<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <script type="text/javascript" src="https://code.jquery.com/jquery-3.3.1.js"></script>
    <script type="text/javascript" src="https://cdn.datatables.net/1.10.20/js/jquery.dataTables.min.js"></script>
    <script type="text/javascript" src="https://cdn.datatables.net/select/1.3.1/js/dataTables.select.min.js"></script>

    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.20/css/jquery.dataTables.min.css" />
    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/select/1.3.1/css/select.dataTables.min.css" />

    <link rel="stylesheet" type="text/css" href="https://rawgit.com/nobleclem/jQuery-MultiSelect/master/jquery.multiselect.css" />
    <link rel="stylesheet" href="adminStyle.css">
    <script type="text/javascript" src="adminScript.js"></script>

</head>
<body>

<div class="header">
    <div class="header-right">
        <a class="active" href="#home">Home</a>
        <a href="#contact">Contact</a>
        <a href="#about">About</a>
    </div>
</div>

<div class="sidenav">
    <a href="#about">link</a>
    <a href="#services">link</a>
    <a href="#clients">link</a>
    <a href="#contact">link</a>
</div>

<div class="main">
    <div class="fieldset2" >
        <div class="temp">
        <fieldset>
            <table id="example" class="display">
                <thead>
                <tr>
                    <th>Email</th>
                    <th>Privilege</th>
                    <th>Edit Profile</th>
                </tr>
                </thead>
                <tbody>
                    <%
                        for(User user : allUsers){
                            out.println(decorate(user));
                        }
                    %>
                </tbody>

            </table>
        </fieldset>
        </div>
    </div>

</div>
</body>
</html>
