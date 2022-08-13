package Servlets;

import DAO.Interfaces.StudentDAO;
import DAO.Interfaces.SubjectDAO;
import DAO.Interfaces.SubjectHistoryDAO;
import DAO.Mapping;
import Model.Student;
import Model.Subject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(name = "ServletUploader", value = "/ServletUploader")
@MultipartConfig
public class ServletUploader extends HttpServlet {

    private static final int LEN = 8;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /* Receive file uploaded to the Servlet from the HTML form */
        Part filePart = request.getPart(Mapping.FILE);
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.
        System.out.println(fileName);
        InputStream fileContent = filePart.getInputStream();
        InputStreamReader isr = new InputStreamReader(fileContent, StandardCharsets.UTF_8);
        BufferedReader br = new BufferedReader(isr);
        //Get lecturer email and subject name.
        String lecturerEmail = request.getParameter(Mapping.LEC_EMAIL);
        //TODO : CHECK LECTURER VALIDITY with the given email

        String subjectName =  request.getParameter(Mapping.SUB_NAME);
        SubjectHistoryDAO subDAO = (SubjectHistoryDAO) request.getServletContext().getAttribute(Mapping.SUBJECT_HISTORY_DAO);
        StudentDAO studentDAO = (StudentDAO) request.getServletContext().getAttribute(Mapping.STUDENT_DAO);
        SubjectDAO subjectDAO = (SubjectDAO) request.getServletContext().getAttribute(Mapping.SUBJECT_DAO);
        // Parse file and redirect client.

        br.lines().forEach(line -> parseStudentGrades(line, subjectName, studentDAO, subjectDAO, subDAO));
        response.sendRedirect("lecturerPages/subject.jsp");
    }

    private void parseStudentGrades(String line, String subjectName , StudentDAO studentDAO ,
                                    SubjectDAO subjectDAO,SubjectHistoryDAO subDAO) {
        //TODO READ : Expected format :studentEmail QUIZ HOMEWORK PROJECT PRESENTATION MIDTERM FINAL FX
        // Example : Expected format: dshis20@freeuni.edu.ge 24 12 -1 -1 24 60 -1
        String[] parsedStrings  =  line.split(" ");
        String email  = parsedStrings[0];
        if(parsedStrings.length != LEN){
            //TODO : INVALID FORMAT EXCEPTION...
            System.out.println("Invalid Format");
            return;
        }
        Student student = studentDAO.getStudentWithEmail(email);
        Subject subject = subjectDAO.getSubjectByName(subjectName);
        subDAO.updateStudentQuiz(student, subject, Double.parseDouble(parsedStrings[1]));
        subDAO.updateStudentHomework(student, subject, Double.parseDouble(parsedStrings[2]));
        subDAO.updateStudentProject(student, subject, Double.parseDouble(parsedStrings[3]));
        subDAO.updateStudentPresentation(student, subject, Double.parseDouble(parsedStrings[4]));
        subDAO.updateStudentMidterm(student, subject, Double.parseDouble(parsedStrings[5]));
        subDAO.updateStudentFinal(student, subject, Double.parseDouble(parsedStrings[6]));
        subDAO.updateStudentFX(student, subject, Double.parseDouble(parsedStrings[7]));
    }
}
