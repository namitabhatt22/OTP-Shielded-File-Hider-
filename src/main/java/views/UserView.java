package views;

import dao.DataDAO;
import dao.UserDAO;
import model.Data;
import model.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class UserView {
    private String email;

    public UserView(String email) {
        this.email = email;
    }

    public void home() {
        do {
            System.out.println("\nWelcome " + this.email);
            System.out.println("Press 1 to show hidden files");
            System.out.println("Press 2 to hide a new file");
            System.out.println("Press 3 to unhide a hidden file");
            System.out.println("Press 0 to logout");
            System.out.print("Enter your choice: ");

            Scanner sc = new Scanner(System.in);
            int ch = Integer.parseInt(sc.nextLine());;

            try {
                switch (ch) {

                    //show ALL hidden files
                    case 1 -> {
                        try {
                            List<Data> files = DataDAO.getAllFiles(this.email);
                            System.out.println("ID - File Name");
                            for (Data file : files) { //ek ek karke nikal rhe hai
                                System.out.println(file.getId() + " - " + file.getFileName());
                            }
                        }
                        catch (SQLException e) {
                            System.out.println(e.getMessage());
                        }
                    }

                    //hide a new file
                    case 2 -> {
                        System.out.println("Enter the file path:");
                        String path = sc.nextLine();
                        File f = new File(path); //yaha proper path daalna hai

                        if (f.exists()) {

                            Data file = new Data(0, f.getName(), path, this.email); //id 0 dedo for simplicity

                            try {
                                DataDAO.hideFile(file);
                                System.out.println("File hidden successfully!");
                            }
                            catch (Exception e) {
                            System.out.println(e.getMessage());
                            }
                        }
                        else {
                            System.out.println("File not found at specified path");
                        }
                    }

                    //unhide a hidden file
                    case 3 -> {
                        List<Data> files = null;
                        try {
                            files = DataDAO.getAllFiles(this.email);
                            System.out.println("ID - File Name");
                            for (Data file : files) {
                                System.out.println(file.getId() + " - " + file.getFileName());
                            }
                            System.out.println("Enter the id of file to unhide");
                            int id = Integer.parseInt(sc.nextLine());
                            boolean isValidID = false;
                            for (Data file : files) {
                                if (file.getId() == id) {
                                    isValidID = true;
                                    break;
                                }
                            }
                            if (isValidID) {
                                DataDAO.unhideFile(id);
                            }
                            else {
                                System.out.println("Wrong ID");
                            }
                        }
                        catch (SQLException | IOException e) {
                            e.printStackTrace();
                        }
                    }
                    case 0 -> {
                        System.out.println("Logging out...");
                        System.exit(0);
                    }
                    default -> System.out.println("Invalid choice!");
                }
            }
            catch (NumberFormatException e) {
                System.out.println("Please enter a valid number!");
                ch = -1;
            }
        } while (true);
    }
}