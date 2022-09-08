<%@ page import="DAO.Interfaces.*" %>
<%@ page import="DAO.Mapping" %>
<%@ page import="Model.*" %>
<%@ page import="java.util.List" %>
<%@ page import="static Helper.ErrorPageRedirector.redirect" %>
<%@ page import="DAO.Mapping" %>
<%@ page import="Model.User" %>
<%@ page import="Model.USERTYPE" %>
<%
    User user = (User) session.getAttribute(Mapping.USER_OBJECT);
    if(user == null || user.getType() != USERTYPE.ADMIN) {
        redirect(request, response);
        return;
    }
    SubjectDAO subjectDAO = (SubjectDAO) application.getAttribute(Mapping.SUBJECT_DAO);
    PrerequisitesDAO prerequisitesDAO = (PrerequisitesDAO) application.getAttribute(Mapping.PREREQUISITES_DAO);
    List<Subject> allSubjects = subjectDAO.getAllSubjects();
%>

<%!
    public String decorate(Subject subject, PrerequisitesDAO prerequisitesDAO){
        String temp = prerequisitesDAO.getSubjectPrerequisitesByName(subject.getName());
        String pres ="Prerequisites :\n" + (temp.equals("")?"No Prerequisites found": temp);
        return  "<tr>\n" +
                "        <td>" + subject.getName() + "</td>\n" +
                "        <td> " + subject.getNumCredits() + "</td>\n" +
                "        <td> " + subject.getLecturerID() + "</td>\n" +
                "        <td>" +
                "           <form action = \"../ServletRemoveSubject\" method = \"POST\" onsubmit=\"return confirm('Do you really want to submit the form?');\">" +
                "             <input type = \"hidden\" name = \"sub_name\" value = \""+ subject.getName() + "\"/>" +
                "             <input type = \"submit\" value = \"Click To Remove\"/>" +
                "           </form></td>\n" +
                "       <td>" +
                "       <button id=\"prerequisites" + subject.getName() + "\" onclick = \"changePre(\'"+subject.getName()+"\')\">Appear</button> </td>\n" +
                "<td>" +
                "<div id = \"prerequisites_d" + subject.getName() + "\" style=\"\n" +
                "        display: none;\n" +
                "        border-radius: 5px;\n" +
                "        background-color: #016ba8;\n" +
                "        width: 200px;\n" +
                "        height: 125px; \">" + "<form id = \"pre" +subject.getName() + "\" action=\"../ServletChangePrerequisite\" method=\"post\">\n" +
                "           <input type=\"hidden\" name=\"" + Mapping.SUB_NAME + "\" value=\"" +subject.getName() +"\">\n" +
                "           <textarea style=\"width: 190px; height: 50px\" disabled>" + pres + "</textarea>\n"+
                "           <textarea type=\"text\"  form = \"pre" + subject.getName() + "\" placeholder =\"Enter Prerequisites\" name=\"" + Mapping.PREREQUISITE + "\" style=\"border-color: white; height: 30px; border-radius: 5px\"></textarea>\n" +
                "           </br>\n"+
                "           <input type=\"submit\" value=\"Change Prerequisites\" style=\"color: black\">\n" +
                "       </form>" +
                " </div> </td>\n" +
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
    <script type="text/javascript">
        function changePre(name) {
            const btn = document.getElementById("prerequisites" + name);
            const pre = document.getElementById("prerequisites_d" + name);
            if (btn.innerText == 'Appear') {
                pre.style.display = 'block';
                btn.innerText = 'Disappear';
            } else if (btn.innerText == 'Disappear') {
                pre.style.display = 'none';
                btn.innerText = 'Appear';
            }
        }
    </script>
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
                        <th>Remove</th>
                        <th>Edit Prerequisites</th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    <%
                        for(Subject subject : allSubjects){
                            out.println(decorate(subject,prerequisitesDAO));
                        }
                    %>
                    </tbody>

                </table>
            </fieldset>
        </div>
    </div>

</div>


<% if(session.getAttribute(Mapping.WRONG)!=null){

    out.println("\n" +
            "<script type=\"text/javascript\" >\n" +
            "    alert('Invalid Expression!')\n" +
            "</script>");
    session.removeAttribute(Mapping.WRONG);
}
%>
</body>
</html>
