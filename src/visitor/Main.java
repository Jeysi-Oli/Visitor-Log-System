package visitor;

import java.util.Scanner;

/**
 * CLASS: Main
 * ROLE: Entry Point & User Interface
 * OOP PRINCIPLE: Inheritance (Extends VisitorSystem)
 * DESCRIPTION: Handles the Guard Login and displays the Main Menu.
 */
public class Main extends VisitorSystem {

    public static void main(String[] args) {
        Main system = new Main();
        system.guardLogin();
        system.mainMenu();
    }

    /**
     * METHOD: guardLogin
     * DESCRIPTION: Simple authentication to secure the system.
     * Hardcoded credentials: security / security12345
     */
    public void guardLogin() {
        final String USERNAME = "security";
        final String PASSWORD = "security12345";

        // MAIN HEADER: Professional "Security Terminal" Header.
        displayMessage("\n#########################################################");
        displayMessage("#           BATANGAS STATE UNIVERSITY - LIPA            #");
        displayMessage("#              VISITOR SECURITY TERMINAL                #");
        displayMessage("#########################################################");
        displayMessage("               [ SYSTEM ACCESS REQUIRED ]                \n");

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.print("Enter Guard Username: ");
            String user = sc.nextLine();

            System.out.print("Enter Guard Password: ");
            String pass = sc.nextLine();

            if (user.equals(USERNAME) && pass.equals(PASSWORD)) {
                displayMessage("\n>> ACCESS GRANTED. Welcome, Officer.");
                displayMessage(">> Loading Modules...\n");
                break;
            } else {
                displayMessage("\n>> ACCESS DENIED. Invalid Credentials.");
                displayMessage(">> Please try again.\n");
            }
        }
    }

    /**
     * METHOD: mainMenu
     * DESCRIPTION: Displays options and handles user input safely.
     * USES: try-catch block to prevent crashing on invalid inputs.
     */
    public void mainMenu() {
        while (true) {
            displayMessage("\n=== BSU VISITOR LOG MENU ===\n");
            displayMessage("1 - Add Visitor Entry");
            displayMessage("2 - Log Visitor Exit (Update Time-Out by Card)");
            displayMessage("3 - View All Visitor Records");
            displayMessage("4 - Search Visitor (Card/Name/Time-In)");
            displayMessage("5 - Delete Visitor Record");
            displayMessage("6 - Exit System");

            System.out.print("\nSelect option: ");
            String input = scanner.nextLine();

            int choice;
            try {
                // Input Validation: Ensures user enters a number
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                displayMessage("Invalid input. Please enter a number.\n");
                continue;
            }

            System.out.println();

            switch (choice) {
                case 1:
                    addVisitor();
                    break;
                case 2:
                    exitVisitor();
                    break;
                case 3:
                    viewVisitors();
                    break;
                case 4:
                    searchVisitor();
                    break;
                case 5:
                    deleteVisitor();
                    break;
                case 6:
                    displayMessage("System terminated. Have a safe day, Guard.");
                    System.exit(0); // Cleanly stops the program
                    break;
                default:
                    displayMessage("Invalid option. Please select 1-6.\n");
                    break;
            }
        }
    }
}