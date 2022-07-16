package emis.betteremis;

import DAO.LecturerDAO;
import DAO.Mapping;
import DAO.StudentDAO;
import DAO.UserDAO;

import javax.servlet.*;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.*;
import java.util.Map;

@WebListener
public class Listener implements ServletContextListener, HttpSessionListener, HttpSessionAttributeListener {

    private UserDAO usrDAO;
    private StudentDAO stDAO;
    private LecturerDAO lecDAO;
    public Listener() {
        usrDAO = new UserDAO();
        stDAO = new StudentDAO();
        lecDAO = new LecturerDAO();
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        sce.getServletContext().setAttribute(Mapping.USER_DAO, usrDAO);
        sce.getServletContext().setAttribute(Mapping.STUDENT_DAO, stDAO);
        sce.getServletContext().setAttribute(Mapping.LECTURER_DAO, lecDAO);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        sce.getServletContext().removeAttribute(Mapping.USER_DAO);
        sce.getServletContext().removeAttribute(Mapping.STUDENT_DAO);
        sce.getServletContext().removeAttribute(Mapping.LECTURER_DAO);
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        /* TODO: save information about user */
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        /* Session is destroyed. */
    }

    @Override
    public void attributeAdded(HttpSessionBindingEvent sbe) {
        /* This method is called when an attribute is added to a session. */
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent sbe) {
        /* This method is called when an attribute is removed from a session. */
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent sbe) {
        /* This method is called when an attribute is replaced in a session. */
    }
}
