package Listeners;

import DAO.*;
import DAO.Interfaces.*;
import Services.FriendService;

import javax.servlet.*;
import javax.servlet.annotation.WebListener;

import static DAO.Mapping.*;

@WebListener
public class ContextListener implements ServletContextListener {
    ConnectionPool pool;
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        pool = new ConnectionPool(10);
        ServletContext context = sce.getServletContext();
        UserDAO sqlUserDAO = new SqlUserDAO(pool);
        StudentDAO sqlStudentDAO = new SqlStudentDAO(pool);
        LecturerDAO sqlLecturerDAO = new SqlLecturerDAO(pool);
        SubjectDAO sqlSubjectDAO = new SqlSubjectDAO(pool);
        SubjectHistoryDAO sqlSubjectHistoryDAO = new SqlSubjectHistoryDAO(pool);
        PrerequisitesDAO sqlPrerequisitesDAO = new SqlPrerequisitesDAO(pool);
        TokenDAO sqlTokenDAO = new SqlTokenDAO(pool);
        RegistrationStatusDAO sqlRegistrationStatusDAO = new SqlRegistrationStatusDAO(pool);
        MailDAO mailDAO = new SqlMailDAO(pool);
        FriendsDAO friendsDAO = new SqlFriendsDAO(pool);
        context.setAttribute(USER_DAO, sqlUserDAO);
        context.setAttribute(STUDENT_DAO, sqlStudentDAO);
        context.setAttribute(LECTURER_DAO, sqlLecturerDAO);
        context.setAttribute(SUBJECT_DAO, sqlSubjectDAO);
        context.setAttribute(SUBJECT_HISTORY_DAO, sqlSubjectHistoryDAO);
        context.setAttribute(PREREQUISITES_DAO, sqlPrerequisitesDAO);
        context.setAttribute(TOKEN_DAO, sqlTokenDAO);
        context.setAttribute(REGISTRATION_STATUS_DAO, sqlRegistrationStatusDAO);
        context.setAttribute(MAIL_DAO, mailDAO);
        context.setAttribute(FRIENDS_DAO, friendsDAO);

        FriendService service = new FriendService();
        context.setAttribute(FRIEND_SERVICE, service);

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        sce.getServletContext().removeAttribute(Mapping.USER_DAO);
        sce.getServletContext().removeAttribute(Mapping.STUDENT_DAO);
        sce.getServletContext().removeAttribute(Mapping.LECTURER_DAO);
        sce.getServletContext().removeAttribute(Mapping.SUBJECT_DAO);
        sce.getServletContext().removeAttribute(Mapping.SUBJECT_HISTORY_DAO);
        sce.getServletContext().removeAttribute(Mapping.PREREQUISITES_DAO);
        sce.getServletContext().removeAttribute(Mapping.TOKEN_DAO);
        pool.close();
    }

}
