package emis.betteremis;

import DAO.LecturerDAO;
import DAO.Mapping;
import DAO.StudentDAO;
import DAO.UserDAO;
import Helper.Utils;
import Model.Lecturer;
import Model.Student;
import Model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet(name = "registerServlet", value = "/registerServlet")
public class registerServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        map contains all the necessary information needed for registration
        Map<String, Object> map = Utils.parseJson(req);
        User newUser = new User(map);
        UserDAO uDAO = (UserDAO) req.getServletContext().getAttribute(Mapping.USER_DAO);
        int userID = uDAO.addUser(newUser);
        if(userID == -1){
            System.out.println("Not registered");
            //TODO: print corresponding information.
        }
        else {
            if ((boolean) map.get(Mapping.IS_STUDENT)) {
                StudentDAO sDAO = (StudentDAO) req.getServletContext().getAttribute(Mapping.STUDENT_DAO);
                sDAO.addStudent(new Student(map, userID));
            } else {
                LecturerDAO lDAO = (LecturerDAO) req.getServletContext().getAttribute(Mapping.LECTURER_DAO);
                lDAO.addLecturer(new Lecturer(map, userID));
            }
            System.out.println("registered " + userID);
            //TODO: print corresponding information.
        }
    }
}
