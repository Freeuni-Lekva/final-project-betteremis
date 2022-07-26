<%@ page import="Model.Student" %>
<%@ page import="DAO.Mapping" %>
<%@ page import="DAO.Interfaces.SubjectHistoryDAO" %>
<%@ page import="static DAO.Mapping.*" %>
<%@ page import="Model.Subject" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Map" %>
<!DOCTYPE html>
<html lang="en">
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
<%
    Student student = (Student) request.getSession().getAttribute(USER_OBJECT);
    SubjectHistoryDAO dao = (SubjectHistoryDAO) request.getServletContext().getAttribute(SUBJECT_HISTORY_DAO);
    Map<Integer, ArrayList<Subject>> data = dao.getAllSubjects(student);
    int cnt = 1;
%>
<body>
<div class="p-3 mb-2 bg-dark text-white">
    <div class="container">
        <h2 style='text-align: center'>Free University Of Georgia</h2>
        <br>
        <h3 style="text-align: center">Academic Records</h3>
        <br>
        <div style='border: 0px solid #AAA;'>
            <table style='text-align: left'>
                <tr>
                    <td >
                        <table>
                            <tr>
                                <td style="align: right">First Name:</td>
                                <td style="width: 5px">&nbsp;</td>
                                <td>Nika</td>
                            </tr>
                            <tr>
                                <td style="align: right">Second Name:</td>
                                <td style="width: 5px">&nbsp;</td>
                                <td>Glunchadze</td>
                            </tr>
                            <tr>
                                <td style="align: right">School:</td>
                                <td style="width: 5px">&nbsp;</td>
                                <td>Mathematics and Computer Science</td>
                            </tr>

                        </table>
                    </td>
                    <td></td>
                    <td style="vertical-align: top">
                        <table style="text-align: right">
                            <tr style="text-align: right">
                                <td style="width: 300px"></td>
                                <td style="text-align: right">Date of</td>
                                <td>&nbsp;Birth:&nbsp;</td>
                                <td>10/06/2002</td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </div>
        <br>
        <table class="table table-hover table-dark table-striped">
            <thead>
            <tr >
                <th style="width: 5%">#</th>
                <th style="width: 35%">Subject Name</th>
                <th style="width: 10%">Semester</th>
                <th style="width: 10%">Percentage</th>
                <th style="width: 5%">Grade</th>
                <th style="width: 23%">ECTS Credits Acquired</th>
                <th style="width: 30%">Subject Credits</th>
            </tr>
            </thead>
            <tbody>
            <%
                for(Integer sem : data.keySet()){
                    for(Subject subject : data.get(sem)){

            %>
            <td><%=(cnt++)%></td>
            <td><%=subject.getName()%></td>
            <td><%=sem%></td>
            <td>100%</td> <%-- TODO --%>
            <td><%=subject.getNumCredits()%></td> <%-- TODO --%>
            <td> <%=subject.getNumCredits()%></td>
            <%
                    }
                }
            %>
            <tr>
                <td>1</td>
                <td>Programming Abstractions</td>
                <td>2</td>
                <td>100%</td>
                <td>A</td>
                <td>8</td>
                <td>8</td>
            </tr>
            <tr>
                <td>2</td>
                <td>Philosophy</td>
                <td>3</td>
                <td>46%</td>
                <td>F</td>
                <td>0</td>
                <td>4</td>
            </tr>
            <tr>
                <td>3</td>
                <td>Theoretical Informatics</td>
                <td>4</td>
                <td>98.3%</td>
                <td>A</td>
                <td>6</td>
                <td>6</td>
            </tr>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>