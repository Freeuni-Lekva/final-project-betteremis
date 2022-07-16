package emis.betteremis;

import DAO.*;

import javax.servlet.*;
import javax.servlet.annotation.WebListener;

@WebListener
public class ContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ConnectionPool pool = new ConnectionPool(5);
        UserDAO userDAO = new UserDAO(pool);
        StudentDAO studentDAO = new StudentDAO(pool);
        LecturerDAO lecturerDAO = new LecturerDAO(pool);
        sce.getServletContext().setAttribute(Mapping.USER_DAO, userDAO);
        sce.getServletContext().setAttribute(Mapping.STUDENT_DAO, studentDAO);
        sce.getServletContext().setAttribute(Mapping.LECTURER_DAO, lecturerDAO);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        sce.getServletContext().removeAttribute(Mapping.USER_DAO);
        sce.getServletContext().removeAttribute(Mapping.STUDENT_DAO);
        sce.getServletContext().removeAttribute(Mapping.LECTURER_DAO);
    }

}
