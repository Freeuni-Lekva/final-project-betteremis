package DAO.Interfaces;

import java.sql.SQLException;

public interface RegistrationStatusDAO {
    /**
     * Allows students to register on subjects by updating column in REGISTRATION_STATUS table.
     * @return true if method finished successfully, false otherwise.
     */
    boolean openRegistration();
    /**
     * Prevents students from registering on subjects by updating column in REGISTRATION_STATUS table.
     * @return true if method finished successfully, false otherwise.
     */
    boolean closeRegistration();

    /**
     * @return returns the boolean that is in the table to determine if registration is open or not.
     */
    boolean registrationStatus() throws SQLException;
}
