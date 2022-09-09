<%@ page import="Model.Student" %>
<%@ page import="DAO.Mapping" %>
<%@ page import="DAO.Interfaces.SubjectHistoryDAO" %>
<%@ page import="static DAO.Mapping.*" %>
<%@ page import="Model.Subject" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Map" %>
<!DOCTYPE html>
<html lang="en" style="background-color: #343a40">
<head>
    <title>Academic Records</title>
    <meta http-equiv="content-type" content="text/html; charset=utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="../css/records.css">
    <script src="https://cdn.jsdelivr.net/npm/jquery@3.6.0/dist/jquery.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.bundle.min.js"></script>
</head>
<%@ page import="static Helper.ErrorPageRedirector.redirect" %>
<%@ page import="Model.User" %>
<%@ page import="static Model.USERTYPE.*" %>
<%
    User user = (User) session.getAttribute(Mapping.USER_OBJECT);
    if(user == null || user.getType() != STUDENT) {
        redirect(request, response);
        return;
    }
    Student student = (Student) request.getSession().getAttribute(USER_OBJECT);
    SubjectHistoryDAO dao = (SubjectHistoryDAO) request.getServletContext().getAttribute(SUBJECT_HISTORY_DAO);
    Map<Integer, ArrayList<Subject>> data = dao.getAllSubjects(student, 0);
    int cnt = 1;
%>

<%!
    public String getGrade(double mark){
        if(91 <= mark && mark <= 100)
            return "A";
        if(81 <= mark && mark < 91)
            return "B";
        if(71 <= mark && mark < 81)
            return "C";
        if(61 <= mark && mark < 71)
            return "D";
        if(51 <= mark && mark < 61)
            return "E";
        if(mark < 51)
            return "F";
        return "";
    }
%>

<div class="wrapper-container">
<body>
        <table class="table table-hover table-dark table-striped">
            <thead>
            <tr style="color: #8BC34A">
                <th style="width: 5%; ">#</th>
                <th style="width: 35%">Subject Name</th>
                <th style="width: 10%">Semester</th>
                <th style="width: 10%">Percentage</th>
                <th style="width: 5%">Grade</th>
                <th style="width: 23%">ECTS Credits Acquired</th>
                <th style="width: 30%">Subject Credits</th>
            </tr>
            </thead>
            <tbody>
            <tr>
            <%
                for(Integer sem : data.keySet()){
                    for(Subject subject : data.get(sem)){
                        double mark = dao.getSumOfScores(student, subject);
                        String grade = getGrade(mark);
            %>
            <td><%=(cnt++)%></td>
            <td><%=subject.getName()%></td>
            <td><%=sem%></td>
            <td><%=mark%>%</td>
            <td><%=grade%></td>
            <td> <%=subject.getNumCredits()%></td>
            <td> <%=grade == "F" ? 0 : subject.getNumCredits()%></td>
            </tr>
            <%
                    }
                }
            %>
            </tbody>
        </table>
</body>
</div>
</html>