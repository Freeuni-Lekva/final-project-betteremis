<%@ page import="DAO.Interfaces.*" %>
<%@ page import="DAO.Mapping" %>
<%@ page import="Model.*" %>
<%@ page import="java.util.List" %>
<%
    SubjectDAO subjectDAO = (SubjectDAO) application.getAttribute(Mapping.SUBJECT_DAO);
    List<Subject> allSubjects = subjectDAO.getAllSubjects();
%>

<%!
    public String decorate(Subject subject){
        return  "<tr>\n" +
                "        <td>" + subject.getName() + "</td>\n" +
                "        <td> " + subject.getNumCredits() + "</td>\n" +
                "        <td> " + subject.getLecturerID() + "</td>\n" +
                "        <td>" +
                "           <form action = \"../ServletRemoveSubject\" method = \"POST\" onsubmit=\"return confirm('Do you really want to submit the form?');\">" +
                "             <input type = \"hidden\" name = \"sub_name\" value = \""+ subject.getName() + "\"/>" +
                "             <input type = \"submit\" value = \"Click To Remove\"/>" +
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
</div>

<div class="main">
    <div class="fieldset2" >
        <div class="temp">
            <fieldset>
                <table id="example" class="display">
                    <thead>
                    <tr>
                        <th>Name</th>
                        <th>Credits</th>
                        <th>Lecturer ID</th>
                        <th>Edit</th>
                    </tr>
                    </thead>
                    <tbody>
                    <%
                        for(Subject subject : allSubjects){
                            out.println(decorate(subject));
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
