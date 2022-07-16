package emis.betteremis;

import DAO.ConnectionPool;
import DAO.LecturerDAO;
import DAO.StudentDAO;
import DAO.UserDAO;

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
        sce.getServletContext().setAttribute("userDAO", userDAO);
        sce.getServletContext().setAttribute("studentDAO", studentDAO);
        sce.getServletContext().setAttribute("lecturerDAO", lecturerDAO);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        sce.getServletContext().removeAttribute("userDAO");
        sce.getServletContext().removeAttribute("studentDAO");
        sce.getServletContext().removeAttribute("lecturerDAO");
    }

}
