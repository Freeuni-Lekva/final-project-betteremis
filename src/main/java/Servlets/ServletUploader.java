package Servlets;

import DAO.Interfaces.StudentDAO;
import DAO.Interfaces.SubjectDAO;
import DAO.Interfaces.SubjectHistoryDAO;
import DAO.Mapping;
import Model.Student;
import Model.Subject;
import Model.User;

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

import static DAO.Mapping.USER_OBJECT;
import static Helper.ErrorPageRedirector.redirect;
import static Model.USERTYPE.LECTURER;

@WebServlet(name = "ServletUploader", value = "/ServletUploader")
@MultipartConfig
public class ServletUploader extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User lecturer = (User) request.getSession().getAttribute(USER_OBJECT);
        if(lecturer == null || lecturer.getType() != LECTURER || !request.getParameter(Mapping.LEC_EMAIL).equals(lecturer.getEmail())){
            redirect(request, response);
        }
        /* Receive file uploaded to the Servlet from the HTML form */
        Part filePart = request.getPart(Mapping.FILE);
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.
        System.out.println(fileName);
        InputStream fileContent = filePart.getInputStream();
        InputStreamReader isr = new InputStreamReader(fileContent, StandardCharsets.UTF_8);
        BufferedReader br = new BufferedReader(isr);
        //Get lecturer email and subject name.
        String lecturerEmail = request.getParameter(Mapping.LEC_EMAIL);
        String subjectName =  request.getParameter(Mapping.SUB_NAME);
        SubjectHistoryDAO subDAO = (SubjectHistoryDAO) request.getServletContext().getAttribute(Mapping.SUBJECT_HISTORY_DAO);
        StudentDAO studentDAO = (StudentDAO) request.getServletContext().getAttribute(Mapping.STUDENT_DAO);
        SubjectDAO subjectDAO = (SubjectDAO) request.getServletContext().getAttribute(Mapping.SUBJECT_DAO);
        // Parse file and redirect client.
        //FIRST LINE Must contain names of the fields.
        String fields =  br.readLine();
        if(fields == null){
            //TODO: syntax error
            System.out.println("Expected fields");
            return;
        }
        String[] parts = fields.split(" ");

        while(true){
            String line =  br.readLine();
            if(line == null) break;
            parseStudentGrades(line, parts, subjectName, studentDAO, subjectDAO, subDAO);
        }

        response.sendRedirect("lecturerPages/subject.jsp");
    }

    private void parseStudentGrades(String line, String[] parts, String subjectName , StudentDAO studentDAO ,
                                    SubjectDAO subjectDAO,SubjectHistoryDAO subDAO) {
        //TODO READ : Expected format on each line :studentEmail foreach space seperated integer must
        // be the grade of the appropriate field wrote on the first line
        // Example : if first line is :quiz midterm fx (each is optional)
        // Expected format: dshis20@freeuni.edu.ge gradeOfTheQuiz gradeOfTheMidterm gradeOfTheFX
        String[] parsedStrings  =  line.split(" ");
        String email  = parsedStrings[0];
        if(parsedStrings.length != parts.length + 1){
            //TODO : INVALID FORMAT EXCEPTION...
            System.out.println("Invalid Format");
            return;
        }
        Student student = studentDAO.getStudentWithEmail(email);
        Subject subject = subjectDAO.getSubjectByName(subjectName);
        for (int i = 0; i < parts.length; i++) {
            String field = parts[i];
            switch (field) {
                case Mapping.QUIZ:
                    subDAO.updateStudentQuiz(student, subject, Double.parseDouble(parsedStrings[i+1]));
                    break;
                case Mapping.HOMEWORK:
                    subDAO.updateStudentHomework(student, subject, Double.parseDouble(parsedStrings[i+1]));
                    break;
                case Mapping.PROJECT:
                    subDAO.updateStudentProject(student, subject, Double.parseDouble(parsedStrings[i+1]));
                    break;
                case Mapping.PRESENTATION:
                    subDAO.updateStudentPresentation(student, subject, Double.parseDouble(parsedStrings[i+1]));
                    break;
                case Mapping.MIDTERM:
                    subDAO.updateStudentMidterm(student, subject, Double.parseDouble(parsedStrings[i+1]));
                    break;
                case Mapping.FINAL:
                    subDAO.updateStudentFinal(student, subject, Double.parseDouble(parsedStrings[i+1]));
                    break;
                case Mapping.FX:
                    subDAO.updateStudentFX(student, subject, Double.parseDouble(parsedStrings[i+1]));
                    break;
            }
        }
    }
}
