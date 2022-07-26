package DAO.Interfaces;

public interface TokenDAO {

    /**
     * Adds the token into the database.
     * @param token
     */
    boolean addToken(String token);

    /**
     * Checks if the token is presented in the database.
     * @param token Token
     * @return true if the token is valid, and false otherwise.
     */
    boolean isValidToken(String token);

}
