package emis.betteremis;

import DAO.*;

import javax.servlet.*;
import javax.servlet.annotation.WebListener;

@WebListener
public class ContextListener implements ServletContextListener {
    ConnectionPool pool;
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        pool = new ConnectionPool(5);
        UserDAO userDAO = new UserDAO(pool);
        StudentDAO studentDAO = new StudentDAO(pool);
        LecturerDAO lecturerDAO = new LecturerDAO(pool);
        SubjectDAO subjectDAO = new SubjectDAO(pool);
        sce.getServletContext().setAttribute(Mapping.USER_DAO, userDAO);
        sce.getServletContext().setAttribute(Mapping.STUDENT_DAO, studentDAO);
        sce.getServletContext().setAttribute(Mapping.LECTURER_DAO, lecturerDAO);
        sce.getServletContext().setAttribute(Mapping.SUBJECT_DAO, subjectDAO);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        sce.getServletContext().removeAttribute(Mapping.USER_DAO);
        sce.getServletContext().removeAttribute(Mapping.STUDENT_DAO);
        sce.getServletContext().removeAttribute(Mapping.LECTURER_DAO);
        sce.getServletContext().removeAttribute(Mapping.SUBJECT_DAO);
        pool.close();
    }

}
