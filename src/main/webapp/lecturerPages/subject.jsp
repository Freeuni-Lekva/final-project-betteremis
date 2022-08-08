<%@ page import="DAO.Mapping" %>
<%@ page import="DAO.Interfaces.*" %>
<%@ page import="Model.*" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Map" %><%--
  Created by IntelliJ IDEA.
  User: dito
  Date: 04.08.22
  Time: 14:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<%
    Lecturer lecturer = (Lecturer) session.getAttribute(Mapping.USER_OBJECT);
    SubjectHistoryDAO subDao = (SubjectHistoryDAO) application.getAttribute(Mapping.SUBJECT_HISTORY_DAO);
    String subName = (String) request.getSession().getAttribute("subjectName");
    Map<Integer, ArrayList<Student> > students = subDao.getAllStudentsOfSubject(subName);
%>

<%!
    private String decorate(Student student, int semester){
        String res = "<tr>\n" +
                "                <td>" + student.getEmail() + "</td>\n" +
                "                <td>" + semester  + "</td>\n" +
                "                <td>\n" +
                "               <form action = \"../ServletMark\" method = \"POST\">\n" +
                "                   <input type = \"hidden\" name = \"studentEmail\" value = \""+ student.getEmail() + "\"/>\n" +
                "                   <input type = \"hidden\" name = \"studentSemester\" value = \""+ semester + "\"/>\n" +
                "                   <input type = \"submit\" value = \"See More\"/>\n" +
                "               </form>\n"
                +  "</td>\n" +
                "     </tr>";

        return res;
    }
%>


<html>
<head>
    <title>Title</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <script type="text/javascript" src="https://code.jquery.com/jquery-3.3.1.js"></script>
    <script type="text/javascript" src="https://cdn.datatables.net/1.10.20/js/jquery.dataTables.min.js"></script>
    <script type="text/javascript" src="https://cdn.datatables.net/select/1.3.1/js/dataTables.select.min.js"></script>

    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.20/css/jquery.dataTables.min.css" />
    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/select/1.3.1/css/select.dataTables.min.css" />

    <link rel="stylesheet" type="text/css" href="https://rawgit.com/nobleclem/jQuery-MultiSelect/master/jquery.multiselect.css" />
    <link rel="stylesheet" href="../adminPages/adminStyle.css">
    <script type="text/javascript" src="../adminPages/adminScript.js"></script>
</head>
<body>

<div class="main">
    <div class="fieldset2" >
        <div class="temp">
            <fieldset>
                <table id="example" class="display">
                    <caption> All Students </caption>
                    <thead>
                    <tr>
                        <th>Email</th>
                        <th>Semester</th>
                        <th>See More</th>
                    </tr>
                    </thead>
                    <tbody>
                        <%
                            for (Integer semester : students.keySet()){
                                for(Student student : students.get(semester)){
                                    out.println(decorate(student, semester.intValue()));
                                }
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
