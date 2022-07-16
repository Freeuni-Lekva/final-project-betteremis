package emis.betteremis;

import DAO.LecturerDAO;
import DAO.StudentDAO;
import DAO.UserDAO;

import javax.servlet.*;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.*;

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
        sce.getServletContext().setAttribute("usrdao", usrDAO);
        sce.getServletContext().setAttribute("stdao", stDAO);
        sce.getServletContext().setAttribute("lecdao", lecDAO);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        sce.getServletContext().removeAttribute("usrdao");
        sce.getServletContext().removeAttribute("stdao");
        sce.getServletContext().removeAttribute("lecdao");
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
