package DAO;

public interface Mapping {
    //Session
    String USER_OBJECT = "usrobj";

    //Context
    String USER_DAO = "usrdao";

    String MAIL_DAO = "maildao";
    String STUDENT_DAO = "stdao";
    String LECTURER_DAO = "lecdao";
    String SUBJECT_DAO = "subjdao";
    String SUBJECT_HISTORY_DAO = "subjhistorydao";
    String PREREQUISITES_DAO = "predao";
    String TOKEN_DAO = "tokdao";
    String REGISTRATION_STATUS_DAO = "rsdao";

    //Mappings used for reading data
    String PASSWORD = "pass";
    String FIRST_NAME = "firstname";
    String LAST_NAME = "lastname";
    String EMAIL = "email";
    String PASSWORD_HASH = "passhash";
    String IS_MALE = "male";
    String PROFESSION = "profession";
    String DATE_OF_BIRTH = "birthdate";
    String ADDRESS = "address";
    String PHONE_NUMBER = "phone";
    String GROUP_NAME = "groupname";
    String IS_STUDENT = "type";
    String SCHOOL = "school";

    //Grades
    String QUIZ = "quiz";
    String HOMEWORK = "homework";

    String PROJECT = "project";
    String PRESENTATION = "presentation";
    String MIDTERM = "midterm";
    String FINAL = "final";
    String FX = "fx";

}
