package dao;

import model.User;
import db.MyConnection;
import java.sql.*;

public class UserDAO {

    // Checks if user with given email exists
    public static boolean isExists(String email) throws SQLException {
        Connection connection = MyConnection.getConnection();
        PreparedStatement ps = connection.prepareStatement("SELECT email FROM users");
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            String e = rs.getString(1);
            if (e.equals(email)) {
                return true;
            }
        }
        return false;
    }


    //Saves new user to database
    public static void saveUser(User user) throws SQLException {
        Connection con = MyConnection.getConnection();
        PreparedStatement ps = con.prepareStatement("INSERT INTO users VALUES (default, ?, ?)" ); //pehla col = auto inc
        ps.setString(1, user.getName());
        ps.setString(2, user.getEmail());
        ps.executeUpdate(); // Removed 'return' to fix the error
    }

    //Gets user by email
    public static User getUserByEmail(String email) throws SQLException {
        Connection con = MyConnection.getConnection();
        PreparedStatement ps = con.prepareStatement("SELECT * FROM users WHERE email = ?");
        ps.setString(1, email);

        ResultSet rs = ps.executeQuery();
        User user = null;

        if (rs.next()) {
            user = new User(
                    rs.getString("name"),
                    rs.getString("email")
            );
        }

        rs.close();
        ps.close();
        con.close();

        return user;
    }

    //Deletes user by email
    public static int deleteUser(String email) throws SQLException {
        Connection con = MyConnection.getConnection();
        PreparedStatement ps = con.prepareStatement("DELETE FROM users WHERE email = ?");
        ps.setString(1, email);

        int rowsAffected = ps.executeUpdate();

        ps.close();
        con.close();

        return rowsAffected;
    }
}