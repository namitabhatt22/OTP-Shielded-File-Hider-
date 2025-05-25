package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//START CONNECTION
public class MyConnection {
    public static Connection connection = null;
    public static Connection getConnection() {
        try {
            //THIS IS STANDARD JDBC STUFF

            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver"); //ye driver load karna hai

            // Establish connection
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_db?", "root", "Agra@2010"); //db name = project_db
            System.out.println("Database connection established successfully!");

        }
        catch (ClassNotFoundException | SQLException e){
            System.err.println("Connection failed!!");
            e.printStackTrace();
        }
        return connection;
    }

    //CLOSE CONNECTION
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Database connection closed successfully");
            }
            catch (SQLException e) { //kabhi open hi nhi hua connection
                System.err.println("Error while closing connection");
                e.printStackTrace();
            }
        }
    }
}