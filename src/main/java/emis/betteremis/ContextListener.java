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
        SqlUserDAO sqlUserDAO = new SqlUserDAO(pool);
        SqlStudentDAO sqlStudentDAO = new SqlStudentDAO(pool);
        SqlLecturerDAO sqlLecturerDAO = new SqlLecturerDAO(pool);
        SqlSubjectDAO sqlSubjectDAO = new SqlSubjectDAO(pool);
        sce.getServletContext().setAttribute(Mapping.USER_DAO, sqlUserDAO);
        sce.getServletContext().setAttribute(Mapping.STUDENT_DAO, sqlStudentDAO);
        sce.getServletContext().setAttribute(Mapping.LECTURER_DAO, sqlLecturerDAO);
        sce.getServletContext().setAttribute(Mapping.SUBJECT_DAO, sqlSubjectDAO);
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
