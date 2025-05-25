import views.Welcome;

public class Main {
    public static void main(String[] args) { //main method yaha hai duh
        try {
            // Initialize application
            Welcome welcome = new Welcome();

            // Main application loop
            while (true) {
                try {
                    welcome.welcomeScreen();
                } catch (Exception e) {
                    System.err.println("error: " + e.getMessage());
                }
            }
        }
        catch (Exception mainException) {
            System.err.println("error: " + mainException.getMessage());
            System.exit(1); // Exit with error code
        }
    }
}