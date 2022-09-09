<%@ page import="DAO.Interfaces.UserDAO" %>
<%@ page import="DAO.Mapping" %>
<%@ page import="Model.User" %>
<%@ page import="java.util.List" %>
<%@ page import="Model.Subject" %>
<%@ page import="DAO.Interfaces.SubjectDAO" %>
<%@ page import="DAO.Interfaces.RegistrationStatusDAO" %>
<%@ page import="static Helper.ErrorPageRedirector.redirect" %>
<%@ page import="DAO.Mapping" %>
<%@ page import="Model.User" %>
<%@ page import="Model.USERTYPE" %>
<%@ page import="static Servlets.ErrorMessages.ERROR_MESSAGE" %>
<%
    UserDAO userDAO = (UserDAO) application.getAttribute(Mapping.USER_DAO);
    RegistrationStatusDAO regStatusDAO = (RegistrationStatusDAO) application.getAttribute(Mapping.REGISTRATION_STATUS_DAO);
    User admin = (User) session.getAttribute(Mapping.USER_OBJECT);
    if(admin == null || admin.getType() != USERTYPE.ADMIN) {
        redirect(request, response);
        return;
    }
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
        <a class="active" href="adminProfile.jsp">Users</a>
        <a href="subjectsForAdmin.jsp">Subjects</a>
        <a href="addSubject.jsp">Add Subject</a>
    </div>
</div>

<div class="sidenav">
    <a href="../LogOutServlet"> Log Out</a>
    <form action="../ServletChangeRegistration" method="POST">
        <%
            String inp1 = "<input type=\"submit\" name=\"Close\" value=\"Close Registration\">";
            String inp2 = "<input type=\"submit\" name=\"Open\" value=\"Open Registration\">";
            boolean isOpen = regStatusDAO.registrationStatus();
            if(isOpen){
                out.println(inp1);
            }else{
                out.println(inp2);
            }
        %>
    </form>
    <form action="../StartNewSemesterServlet" method="POST">
        <%
            out.println("<input type=\"submit\" value=\"Start New Semester\">");
        %>
    </form>
</div>
<%
    if(session.getAttribute(ERROR_MESSAGE) != null){
%>
<p> <%=session.getAttribute(ERROR_MESSAGE)%></p>

<%
        session.removeAttribute(ERROR_MESSAGE);
    }
%>
<div class="main">
    <div class="fieldset2" >
        <div class="temp">
        <fieldset>
            <table id="example" class="display">
                <thead>
                <tr>
                    <th>Email</th>
                    <th>Privilege</th>
                    <th>See More</th>
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
