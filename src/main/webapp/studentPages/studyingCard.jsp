<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="DAO.*" %>
<%@ page import="java.util.*" %>
<%@ page import="DAO.Interfaces.*" %>
<%@ page import="java.sql.*" %>
<%@ page import="Model.*" %>
<%@ page import="static Helper.ErrorPageRedirector.redirect" %>
<%@ page import="static Model.USERTYPE.*" %>
<%
    User user = (User) session.getAttribute(Mapping.USER_OBJECT);
    if(user == null || user.getType() != STUDENT) {
        redirect(request, response);
        return;
    }
    Student student = (Student) session.getAttribute(Mapping.USER_OBJECT);
    SubjectHistoryDAO shDAO = (SubjectHistoryDAO) application.getAttribute(Mapping.SUBJECT_HISTORY_DAO);
    LecturerDAO lecDAO = (LecturerDAO) application.getAttribute(Mapping.LECTURER_DAO);
    Map<Integer, ArrayList<Subject>> completed = shDAO.getCompletedSubjects(student);
    Map<Integer, ArrayList<Subject>> incomplete = shDAO.getIncompleteSubjects(student);
    RegistrationStatusDAO rsDAO = (RegistrationStatusDAO) request.getServletContext().getAttribute(Mapping.REGISTRATION_STATUS_DAO);
    PrerequisitesDAO pDAO = (PrerequisitesDAO)  request.getServletContext().getAttribute(Mapping.PREREQUISITES_DAO);
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

<%!
    private String decorateGrade(Map<String, Double> grades) {
        String result = "";
        for(String task : grades.keySet()){
            double grade = grades.get(task);
            if(grade != -1)
                result += task.toUpperCase() + " : " + grade + "<br>";
        }
        return result;
    }

    public String decorate (Map<Integer, ArrayList<Subject>> map, Student student, SubjectHistoryDAO shDAO, LecturerDAO lecDAO, RegistrationStatusDAO rsDAO, PrerequisitesDAO pDAO){
        String result = "";
        for(int semester : map.keySet()){
            result += " <table> " +
                    "     <thead>\n" +
                    "        <th>Semester " + semester + "</th>\n" +
                    "        <tr >\n" +
                    "            <th><h1>Subject Name</h1></th>\n" +
                    "            <th><h1>Lecturer</h1></th>\n" +
                    "            <th><h1>Credits</h1></th>\n" +
                    "            <th><h1>Score distribution</h1></th>\n" +
                    "            <th><h1>Grade</h1></th>\n" +
                    "            <th><h1>Prerequisites</h1></th>\n" +
                    "            <th><h1>Syllabus</h1></th>\n" +
                    "        </tr>\n" +
                    "    </thead>";
            for(Subject sb :  map.get(semester)){
                Map<String, Double> grades = shDAO.getGrade(student, sb);
                double mark = shDAO.getSumOfScores(student, sb);
                String grade = getGrade(mark);
                Lecturer lec = lecDAO.getLecturerWithID(sb.getLecturerID());
                result += "    <tbody>\n" +
                        "        <tr>\n" +
                        "            <td>" + sb.getName() + "</td>\n" +
                        "            <td> " + lec.getFirstName() + " " + lec.getLastName() + "</td>\n" +
                        "            <td> " + sb.getNumCredits() + "</td>\n" +
                        "            <td> " + decorateGrade(grades) + "</td>\n" +
                        "            <td> " + mark + " | " + grade + "</td>\n" +
                        "            <td> " + pDAO.getSubjectPrerequisitesByName(sb.getName()) + "</td>\n" +
                        "            <td>Syllabus is currently unavailable</td>\n";
                try {
                    if (!shDAO.isCompleted(student, sb) && rsDAO.registrationStatus())
                        result += "            <td>\n" +
                                "               <form action = \"CancelRegistrationServlet\" method = \"POST\">\n" +
                                "                   <input type = \"hidden\" name = \"subjectToRemove\" value = \""+ sb.getName() + "\"/>\n" +
                                "                   <input type = \"submit\" value = \"Cancel\"/>\n" +
                                "               </form>\n" +
                                "            </td>\n";
                } catch (SQLException e) {

                }
                result += "         </tr>\n" +
                        "     </tbody>";
            }
            result += "</table>";
        }
        return result;
    }

%>
<html>
<head>
    <title>Card</title>
    <link rel="stylesheet" href="../css/cardStyle.css">
    <style>
        a {
            color: white;
            alignment: left;
        }
    </style>
</head>
<body>

<h1>Incomplete courses</h1>
<%
    out.println(decorate(incomplete, student, shDAO, lecDAO, rsDAO, pDAO));
%>

<h1>Completed courses</h1>
<%
    out.println(decorate(completed, student, shDAO, lecDAO, rsDAO, pDAO));
%>

<a href="studentProfile.jsp">Profile</a>

</body>

</html>
