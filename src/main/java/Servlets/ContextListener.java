package Servlets;

import DAO.*;
import DAO.Interfaces.*;

import javax.servlet.*;
import javax.servlet.annotation.WebListener;

@WebListener
public class ContextListener implements ServletContextListener {
    ConnectionPool pool;
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        pool = new ConnectionPool(5);
        UserDAO sqlUserDAO = new SqlUserDAO(pool);
        StudentDAO sqlStudentDAO = new SqlStudentDAO(pool);
        LecturerDAO sqlLecturerDAO = new SqlLecturerDAO(pool);
        SubjectDAO sqlSubjectDAO = new SqlSubjectDAO(pool);
        SubjectHistoryDAO sqlSubjectHistoryDAO = new SqlSubjectHistoryDAO(pool);
        sce.getServletContext().setAttribute(Mapping.USER_DAO, sqlUserDAO);
        sce.getServletContext().setAttribute(Mapping.STUDENT_DAO, sqlStudentDAO);
        sce.getServletContext().setAttribute(Mapping.LECTURER_DAO, sqlLecturerDAO);
        sce.getServletContext().setAttribute(Mapping.SUBJECT_DAO, sqlSubjectDAO);
        sce.getServletContext().setAttribute(Mapping.SUBJECT_HISTORY_DAO, sqlSubjectHistoryDAO);
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
