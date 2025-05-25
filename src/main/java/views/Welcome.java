package views;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import dao.UserDAO;
import model.User;
import service.OTPService;
import service.SendOTPService;

public class Welcome {
    public void welcomeScreen() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("\n=== Welcome to File Encrypter ===");
        System.out.println("1. Login");
        System.out.println("2. Sign Up");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");

        int choice = 0;
        try {
            choice = Integer.parseInt(br.readLine());
        }
        catch (IOException e) {
            System.out.println("Input error: " + e.getMessage());
            return;
        }

        switch (choice) {
            case 1 -> login(); //method called
            case 2 -> signUp(); //method called (neeche banaye hai)
            case 0 -> {
                System.exit(0);
            }
            default -> System.out.println("Invalid choice! Please try again.");
        }
    }

    public void login() { //saare methods banao
        Scanner sc = new Scanner(System.in);
        System.out.print("\nEnter your email: ");
        String email = sc.nextLine().trim();

        try {
            if (UserDAO.isExists(email)) {
                String genOTP = OTPService.generateOTP(); //ek random otp banao
                SendOTPService.sendOTP(email, genOTP); //bhejdo email pe

                System.out.print("Enter the OTP sent to your email: ");
                String otp = sc.nextLine().trim(); //vapis input karao

                if (otp.equals(genOTP)) { //if matched then login allowed
                    System.out.println("Login successful!\n");
                    new UserView(email).home();
                }
                else {
                    System.out.println("Invalid OTP!");
                }
            }
            else { //email hi galat hai
                System.out.println("User not found! Please sign up first.");
                welcomeScreen();
            }
        }
        catch (Exception e) {
            System.out.println("Error during login: " + e.getMessage());
        }
    }



    public void signUp() { //new email
        Scanner sc = new Scanner(System.in);
        System.out.println("New User Registration");

        System.out.print("Enter your name: ");
        String name = sc.nextLine().trim();

        System.out.print("Enter your email: ");
        String email = sc.nextLine().trim();

        try {
            // Check if user already exists
            if (UserDAO.isExists(email)) {
                System.out.println("User already exists! Please login instead.");
                welcomeScreen();
                return;
            }

            //new email pe OTP bhijwa ke sign up karalo to verify if email is real

            // OTP Verification
            String genOTP = OTPService.generateOTP();
            SendOTPService.sendOTP(email, genOTP);

            System.out.print("Enter the OTP sent to your email: ");
            String otp = sc.nextLine().trim();

            if (otp.equals(genOTP)) {
                // Create new user
                User newUser = new User(name, email);
                UserDAO.saveUser(newUser);

                System.out.println("Registration successful!");
                System.out.println("Please login with your credentials.\n");
                welcomeScreen();
            }
            else {
                System.out.println("Invalid OTP!");
            }
        }
        catch (Exception e) {
            System.out.println("Error during registration: " + e.getMessage());
        }
    }
}