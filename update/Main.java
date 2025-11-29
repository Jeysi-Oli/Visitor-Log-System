import java.util.Scanner;

public class Main extends VisitorSystem {

    public static void main(String[] args) {
        Main system = new Main();
        system.guardLogin();
        system. mainMenu();
    }

    public void guardLogin() {
        final String USERNAME = "guard";
        final String PASSWORD = "0000";

        displayMessage("===================================");
        displayMessage("   ");
        displayMessage("     BSU Lipa Campus — Guard Login");
        displayMessage("   ");
        displayMessage("===================================\n");

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.print("Enter Guard Username: ");
            String user = sc.nextLine();

            System.out.print("Enter Guard Password: ");
            String pass = sc.nextLine();

            if (user.equals(USERNAME) && pass.equals(PASSWORD)) {
                displayMessage("\nGuard authentication successful!\n");
                break;
            } else {
                displayMessage("\nAccess denied. Try again.\n");
            }
        }
    }

    public void mainMenu() { 
        while (true) {
            displayMessage("\n=== BSU VISITOR LOG MENU ===");
            displayMessage("1 - Add Visitor Entry");
            displayMessage("2 - Log Visitor Exit (Update Time-Out by Card)");
            displayMessage("3 - View All Visitor Records");
            displayMessage("4 - Search Visitor (Card/Name/Time-In)");
            displayMessage("5 - Delete Visitor Record");
            displayMessage("6 - Exit System");

            System.out. print("\nSelect option: ");
            String input = scanner.nextLine();

            int choice;
            try {
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
                    displayMessage("System terminated.  Have a safe day, Guard.");
                    return;
                default:
                    displayMessage("Invalid option. Please select 1-6.\n");
                    break; 
            } 
        }
    }
}