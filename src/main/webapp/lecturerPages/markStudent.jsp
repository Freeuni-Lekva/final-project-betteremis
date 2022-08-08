<%@ page import="DAO.Interfaces.SubjectHistoryDAO" %>
<%@ page import="DAO.Mapping" %>
<%@ page import="DAO.Interfaces.*" %>
<%@ page import="Model.Student" %>
<%@ page import="Model.Subject" %>
<%@ page import="java.util.Map" %><%--
  Created by IntelliJ IDEA.
  User: dito
  Date: 06.08.22
  Time: 16:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String subName = (String) request.getSession().getAttribute("subjectName");
    String email = (String) request.getSession().getAttribute("studentEmail");
    SubjectHistoryDAO subDao = (SubjectHistoryDAO) application.getAttribute(Mapping.SUBJECT_HISTORY_DAO);
    StudentDAO studentDAO = (StudentDAO) application.getAttribute(Mapping.STUDENT_DAO);
    SubjectDAO subjectDAO = (SubjectDAO) application.getAttribute(Mapping.SUBJECT_DAO);
    Student student = studentDAO.getStudentWithEmail(email);
    Subject subject = subjectDAO.getSubjectByName(subName);
    Map<String, Double> grades = subDao.getGrade(student,subject);
    int semester = (int) request.getSession().getAttribute("studentSemester");

    // TODO : Check current semester. For testing purposes now it is editable, so that lecturer can enter student scores.
//    if(semester == currentSemester){
//        input field must be required
//    }else{
//        input field must be readonly
//    }

%>

<%!
    private String decorate(String name, double value){
        String res = " <div><label for=\"" + name + "\" style = \"color:white;\">" + name + "</label></div>\n" +
                "<input name=\"" + name + "\" placeholder=\"Enter -1 to discard this field\" type=\"number\" value =\"" + value+"\"  step=\"any\" min=\"-1\" max=\"100\" required>";
        return res;
    }
%>


<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="markStudent.css">
</head>
<body>
    <br>
    <label style="color: #A7A1AE"> Edit <%= student.getFirstName()%>'s scores </label>
    <br>
    <br>
    <div class = "mainPanel">
        <form class="markform" action="../ServletChangeMarks" method="POST">
            <%
                for(String name : grades.keySet()){
                    if(grades.get(name)!=-1){
                        out.println(decorate(name, grades.get(name)));
                    }
                }
            %>
            <input type="hidden" name= "subname" value= <%= subName %> >
            <input type="hidden" name= "email" value= <%= email %> >
            <br>
            <br>
            <label>Total Score: <%= subDao.getSumOfScores(student,subject) %></label>
            <input type="submit" value="Submit scores">
        </form>

        <form class="resetForm" action="../ServletChangeMarks" method="POST" >
            <input type="hidden" name= "subname" value= <%= subName %> >
            <input type="hidden" name= "email" value= <%= email %> >
            <input type = "hidden" name = "reset" value="Reset">
            <input type = "submit" value="Reset Default">
        </form>

        <a href="lecturerProfile.jsp">Go To Profile</a>
    </div>
</body>
</html>
