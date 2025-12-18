package visitor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * CLASS: VisitorSystem
 * ROLE: The "Brain" or Logic Controller
 * INHERITS FROM: AbstractVisitorSystem
 * DESCRIPTION: Implements all the logic for adding, saving, loading,
 * and displaying visitors.
 * FIXED: Strict Duplicate Checker (Scans all records for active cards).
 */
public class VisitorSystem extends AbstractVisitorSystem {

    private final String logFile = "visitor_logs.txt";

    public VisitorSystem() {
        loadData();
    }

    @Override
    protected void saveData() {
        try (FileWriter fw = new FileWriter(logFile, false);
             PrintWriter pw = new PrintWriter(fw)) {

            for (Visitor v : visitors) {
                pw.println(v.getCardNumber() + "," + v.getName() + "," + v.getDepartment() + "," +
                        v.getPurpose() + "," + v.getDate() + "," + v.getTimeIn() + "," + v.getTimeOut());
            }

        } catch (IOException e) {
            displayMessage("Error writing logs to file.");
        }
    }

    @Override
    protected void loadData() {
        File file = new File(logFile);
        if (!file.exists()) return;

        try (java.util.Scanner fileScanner = new java.util.Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] data = line.split(",");

                if (data.length == 7) {
                    Visitor v = new Visitor(data[0], data[1], data[2], data[3], data[4], data[5]);
                    v.setTimeOut(data[6]);
                    visitors.add(v);
                }
            }
            displayMessage("Database loaded successfully.");
        } catch (FileNotFoundException e) {
            displayMessage("Starting new database...");
        }
    }

    @Override
    protected void displayMessage(String message) {
        System.out.println(message);
    }

    @Override
    protected void displayVisitor(Visitor v) {
        // Full Text Display
        System.out.printf("| %-10s | %-25s | %-20s | %-35s | %-12s | %-10s | %-10s |\n",
                v.getCardNumber(),
                v.getName(),
                v.getDepartment(),
                v.getPurpose(),
                v.getDate(),
                v.getTimeIn(),
                v.getTimeOut());
    }

    private void printTableHeader() {
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("| %-10s | %-25s | %-20s | %-35s | %-12s | %-10s | %-10s |\n",
                "CARD NO.", "NAME", "DEPT", "PURPOSE", "DATE", "TIME-IN", "TIME-OUT");
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------");
    }

    /**
     * METHOD: addVisitor
     * FIXED LOGIC: Loops through ALL visitors to find if the card is CURRENTLY active.
     */
    @Override
    public void addVisitor() {
        displayMessage("\n=== ADD VISITOR ENTRY ===\n");

        String card;
        // VALIDATION LOOP
        while (true) {
            System.out.print("Enter Card Number: ");
            card = scanner.nextLine().trim();

            if (card.matches("\\d+")) {
                // LOGIC FIX: Check if this card is currently INSIDE (Active)
                boolean isCardActive = false;

                for (Visitor v : visitors) {
                    // Check if Card matches AND TimeOut is still "N/A"
                    if (v.getCardNumber().equals(card) && v.getTimeOut().equals("N/A")) {
                        isCardActive = true;
                        break; // Stop checking, we found a duplicate active user
                    }
                }

                if (isCardActive) {
                    displayMessage(">> Error: Card " + card + " is currently active inside. Please verify.");
                } else {
                    break; // Card is clear to use
                }

            } else {
                displayMessage(">> Invalid input. Card Number must contain NUMBERS only.");
            }
        }

        System.out.print("Enter Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Department/Office to visit: ");
        String dept = scanner.nextLine();

        System.out.print("Enter Purpose: ");
        String purpose = scanner.nextLine();

        // AUTOMATIC LOGGING
        String date = getCurrentDate();
        String timeIn = getCurrentTime();

        visitors.add(new Visitor(card, name, dept, purpose, date, timeIn));

        // ORGANIZED OUTPUT
        displayMessage("\n>> SUCCESS: New Entry Added");
        displayMessage("   --------------------------------");
        displayMessage("   NAME    : " + name);
        displayMessage("   DATE    : " + date);
        displayMessage("   TIME-IN : " + timeIn);
        displayMessage("   --------------------------------\n");
        saveData();
    }

    /**
     * METHOD: exitVisitor
     * UPDATE: Finds the specific ACTIVE src.visitor record for this card.
     */
    @Override
    public void exitVisitor() {
        displayMessage("\n=== LOG VISITOR EXIT ===\n");

        String card;
        while (true) {
            System.out.print("Enter Card Number of Visitor to update Time-Out: ");
            card = scanner.nextLine().trim();

            if (card.matches("\\d+")) {
                break;
            } else {
                displayMessage(">> Invalid input. Please enter numbers only.");
            }
        }

        // LOGIC FIX: Find the src.visitor who is currently INSIDE using this card
        Visitor visitorFound = null;
        for (Visitor v : visitors) {
            if (v.getCardNumber().equals(card) && v.getTimeOut().equals("N/A")) {
                visitorFound = v;
                break;
            }
        }

        if (visitorFound != null) {
            displayMessage("\nVisitor Found: " + visitorFound.getName());

            // AUTOMATIC LOGGING
            String timeOut = getCurrentTime();
            visitorFound.setTimeOut(timeOut);

            // ORGANIZED OUTPUT
            displayMessage("\n>> SUCCESS: Visitor Logged Out");
            displayMessage("   --------------------------------");
            displayMessage("   NAME     : " + visitorFound.getName());
            displayMessage("   DATE     : " + visitorFound.getDate());
            displayMessage("   TIME-OUT : " + timeOut);
            displayMessage("   --------------------------------\n");
            saveData();
        } else {
            displayMessage(">> Visitor not found or already logged out.\n");
        }
    }

    @Override
    public void viewVisitors() {
        displayMessage("\n=== VISITOR LOG RECORDS ===\n");

        if (!hasVisitors()) {
            displayMessage("No src.visitor records found.\n");
        } else {
            printTableHeader();
            for (Visitor v : visitors) {
                displayVisitor(v);
            }
            System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------");
            displayMessage("Total Visitors: " + getVisitorCount() + "\n");
        }
    }

    @Override
    public void searchVisitor() {
        displayMessage("\n=== SEARCH VISITOR ===\n");
        displayMessage("Search by: \n");
        displayMessage("1 - Card Number");
        displayMessage("2 - Name");
        displayMessage("3 - Time-In");
        System.out.print("\nChoose option: ");

        try {
            int choice = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter keyword: ");
            String keyword = scanner.nextLine();

            boolean found = false;
            boolean headerPrinted = false;

            for (Visitor v : visitors) {
                if ((choice == 1 && v.getCardNumber().equalsIgnoreCase(keyword)) ||
                        (choice == 2 && v.getName().equalsIgnoreCase(keyword)) ||
                        (choice == 3 && v.getTimeIn().equalsIgnoreCase(keyword))) {

                    if (!headerPrinted) {
                        printTableHeader();
                        headerPrinted = true;
                    }
                    displayVisitor(v);
                    found = true;
                }
            }

            if (headerPrinted) {
                System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------");
            }

            if (!found) {
                displayMessage("No src.visitor matched your search.\n");
            }
        } catch (NumberFormatException e) {
            displayMessage(">> Invalid input. Please enter numbers only.");
        }
    }

    @Override
    public void deleteVisitor() {
        displayMessage("\n=== DELETE VISITOR ===\n");
        System.out.print("Enter Card Number of Visitor to delete: ");
        String card = scanner.nextLine();

        Visitor toRemove = null;
        for(Visitor v : visitors) {
            if(v.getCardNumber().equalsIgnoreCase(card)) {
                toRemove = v;
                break;
            }
        }

        if (toRemove != null) {
            visitors.remove(toRemove);
            displayMessage(">> Visitor deleted successfully!\n");
            saveData();
        } else {
            displayMessage(">> Visitor not found.\n");
        }
    }
}