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
        ClassroomDAO classroomDAO = new SqlClassroomDAO(pool);
        ClassroomPostsDAO classroomPostsDAO = new SqlClassroomPostsDAO(pool);
        StudentClassroomDAO studentClassroomDAO = new SqlStudentClassroomDAO(pool);
        CommentsDAO commentsDAO = new SqlCommentsDAO(pool);
        CurrentSemesterDAO currentSemesterDAO = new SqlCurrentSemesterDAO(pool);
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
        context.setAttribute(CLASSROOM_DAO, classroomDAO);
        context.setAttribute(CLASSROOM_POSTS_DAO, classroomPostsDAO);
        context.setAttribute(STUDENT_CLASSROOM_DAO, studentClassroomDAO);
        context.setAttribute(COMMENTS_DAO, commentsDAO);
        context.setAttribute(CURRENT_SEMESTER_DAO, currentSemesterDAO);

        FriendService service = new FriendService();
        context.setAttribute(FRIEND_SERVICE, service);

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        sce.getServletContext().removeAttribute(USER_DAO);
        sce.getServletContext().removeAttribute(STUDENT_DAO);
        sce.getServletContext().removeAttribute(LECTURER_DAO);
        sce.getServletContext().removeAttribute(SUBJECT_DAO);
        sce.getServletContext().removeAttribute(SUBJECT_HISTORY_DAO);
        sce.getServletContext().removeAttribute(PREREQUISITES_DAO);
        sce.getServletContext().removeAttribute(TOKEN_DAO);
        sce.getServletContext().removeAttribute(CLASSROOM_DAO);
        sce.getServletContext().removeAttribute(CLASSROOM_POSTS_DAO);
        sce.getServletContext().removeAttribute(COMMENTS_DAO);
        sce.getServletContext().removeAttribute(STUDENT_CLASSROOM_DAO);
        pool.close();
    }

}
