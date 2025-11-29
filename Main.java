import java.util.Scanner;

public class Main extends VisitorSystem {

    public static void main(String[] args) {
        Main system = new Main();
        system.guardLogin();
        system.mainMenu();
    }

    // Guard Login to enter the main system
    public void guardLogin() {
        String USERNAME = "guard";
        String PASSWORD = "0000";

        System.out.println("===================================");
        System.out.println("   ");
        System.out.println("     BSU Lipa Campus — Guard Login");
        System.out.println("   ");
        System.out.println("===================================\n");

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.print("Enter Guard Username: ");
            String user = sc.nextLine();

            System.out.print("Enter Guard Password: ");
            String pass = sc.nextLine();

            if (user.equals(USERNAME) && pass.equals(PASSWORD)) {
                System.out.println("\nGuard authentication successful!\n");
                break;
            } else {
                System.out.println("\nAccess denied. Try again.\n");
            }
        }
    }

    //Main Menu
    public void mainMenu() {
        while (true) {
            System.out.println("\n=== BSU VISITOR LOG MENU ===");
            System.out.println("1 - Add Visitor Entry");
            System.out.println("2 - Log Visitor Exit (Update Time-Out by Card)");
            System.out.println("3 - View All Visitor Records");
            System.out.println("4 - Search Visitor (Card/Name/Time-In)");
            System.out.println("5 - Delete Visitor Record");
            System.out.println("6 - Exit System");

            System.out.print("\nSelect option: ");
            String input = scanner.nextLine();
            int choice = Integer.parseInt(input);

            System.out.println();

            switch (choice) {
                case 1:
                    addVisitor();    //Call to Add Visitor
                    break;
                case 2:
                    exitVisitor();   //Call TO Exit Visitor
                    break;
                case 3:
                    viewVisitors();   // ← VIEW FUNCTION
                    break;
                case 4:
                    searchVisitor();  //Call to Search for Visitor
                    break;
                case 5:
                    deleteVisitor();  //Call to Delete a Visitor
                    break;
                case 6:
                    System.out.println("System terminated. Have a safe day, Guard.");  //Exit System
                    return;
                default:
                    System.out.println("Invalid option. Please try again.\n");  //Invalid input, choose only from 1-6
                    break;
            }
        }
    }
}