package service;

import dao.UserDAO;
import model.User;
import java.sql.SQLException;

public class UserService {
    // Status codes
    public static final int USER_EXISTS = 0;
    public static final int SUCCESS = 1;
    public static final int ERROR = -1;

    public static int saveUser(User user) {
        try {
            if (UserDAO.isExists(user.getEmail())) {
                return USER_EXISTS;  // Return 0 if user exists
            } else {
                UserDAO.saveUser(user);  // This is void, so we return SUCCESS after
                return SUCCESS;  // Return 1 on success
            }
        } catch (SQLException e) {
            System.err.println("Error saving user: " + e.getMessage());
            return ERROR;  // Return -1 on error
        }
    }

    /**
     * Verifies user credentials
     * @param email User's email
     * @return true if user exists, false otherwise
     */
    public static boolean verifyUser(String email) {
        try {
            return UserDAO.isExists(email);
        } catch (SQLException e) {
            System.err.println("Error verifying user: " + e.getMessage());
            return false;
        }
    }
}