package src;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * CLASS: src.VisitorSystem
 * ROLE: The "Brain" or Logic Controller
 * INHERITS FROM: src.AbstractVisitorSystem
 * DESCRIPTION: Implements all the logic for adding, saving, loading,
 * and displaying visitors in a professional table format.
 * Includes validation to prevent user errors.
 */

public class VisitorSystem extends AbstractVisitorSystem {

    private final String logFile = "visitor_logs.txt";

    // CONSTRUCTOR: Load data immediately when system starts
    public VisitorSystem() {
        loadData();
    }

    /**
     * METHOD: saveData
     * DESCRIPTION: Writes all visitor records to a text file.
     * FORMAT: CSV (Comma Separated Values) for easy reading/loading.
     */

    @Override
    protected void saveData() {
        try (FileWriter fw = new FileWriter(logFile, false);
             PrintWriter pw = new PrintWriter(fw)) {

            for (Visitor v : visitors) {
                // Saving in format: card,name,dept,purpose,date,timeIn,timeOut
                pw.println(v.getCardNumber() + "," + v.getName() + "," + v.getDepartment() + "," +
                        v.getPurpose() + "," + v.getDate() + "," + v.getTimeIn() + "," + v.getTimeOut());
            }

        } catch (IOException e) {
            displayMessage("Error writing logs to file.");
        }
    }

    /**
     * METHOD: loadData
     * DESCRIPTION: Reads the CSV file and reconstructs src.Visitor objects.
     * Ensures data persists even after closing the application.
     */

    @Override
    protected void loadData() {
        File file = new File(logFile);
        if (!file.exists()) return;

        try (java.util.Scanner fileScanner = new java.util.Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] data = line.split(",");

                // Data Integrity Check: Must have 7 parts
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

    /**
     * METHOD: displayVisitor
     * DESCRIPTION: Prints a single visitor row in Table Format.
     * USES: printf for alignment and 'truncate' to handle long text.
     */
    @Override
    protected void displayVisitor(Visitor v) {
        System.out.printf("| %-10s | %-20s | %-15s | %-15s | %-12s | %-10s | %-10s |\n",
                v.getCardNumber(),
                truncate(v.getName(), 20),
                truncate(v.getDepartment(), 15),
                truncate(v.getPurpose(), 15),
                v.getDate(),
                v.getTimeIn(),
                v.getTimeOut());
    }

    // HELPER: Prints the Table Headers for a clean look
    private void printTableHeader() {
        System.out.println("-------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("| %-10s | %-20s | %-15s | %-15s | %-12s | %-10s | %-10s |\n",
                "CARD NO.", "NAME", "DEPT", "PURPOSE", "DATE", "TIME-IN", "TIME-OUT");
        System.out.println("-------------------------------------------------------------------------------------------------------------------------");
    }

    // HELPER: Cuts text if it exceeds column width so the table won't break
    private String truncate(String text, int length) {
        if (text.length() > length) {
            return text.substring(0, length - 3) + "...";
        }
        return text;
    }

    /**
     * METHOD: addVisitor
     * DESCRIPTION: Collects user input to create a new visitor record.
     * FEATURES: Includes "Yes/No" validation loops to prevent typos.
     */
    @Override
    public void addVisitor() {
        displayMessage("\n=== ADD VISITOR ENTRY ===\n");

        System.out.print("Enter Card Number: ");
        String card = scanner.nextLine();

        // Validation: Prevent duplicate entry for active visitors
        Visitor existing = findVisitorByCard(card);
        if (existing != null && existing.getTimeOut().equals("N/A")) {
            displayMessage(">> Error: Card " + card + " is currently active inside.");
            return;
        }

        System.out.print("Enter Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Department/Office to visit: ");
        String dept = scanner.nextLine();

        System.out.print("Enter Purpose: ");
        String purpose = scanner.nextLine();

        // VALIDATION LOOP: Force correct Yes/No input for DATE
        String dateChoice;
        while (true) {
            System.out.print("Use automatic date today? (yes/no): ");
            dateChoice = scanner.nextLine().trim();
            if (dateChoice.equalsIgnoreCase("yes") || dateChoice.equalsIgnoreCase("no")) {
                break; // Exit loop if input is valid
            }
            displayMessage(">> Invalid input. Please type 'yes' or 'no'.");
        }

        String date;
        if (dateChoice.equalsIgnoreCase("yes")) {
            date = getCurrentDate();
            displayMessage(">> Date recorded (auto): " + date);
        } else {
            System.out.print("Enter Date (MM/DD/YYYY): ");
            date = scanner.nextLine();
        }

        // VALIDATION LOOP: Force correct Yes/No input for TIME-IN
        String timeInChoice;
        while (true) {
            System.out.print("Use automatic Time-In now? (yes/no): ");
            timeInChoice = scanner.nextLine().trim();
            if (timeInChoice.equalsIgnoreCase("yes") || timeInChoice.equalsIgnoreCase("no")) {
                break; // Exit loop if input is valid
            }
            displayMessage(">> Invalid input. Please type 'yes' or 'no'.");
        }

        String timeIn;
        if (timeInChoice.equalsIgnoreCase("yes")) {
            timeIn = getCurrentTime();
            displayMessage(">> Time-In recorded (auto): " + timeIn);
        } else {
            System.out.print("Enter Time-In (HH:MM AM/PM): ");
            timeIn = scanner.nextLine();
        }

        visitors.add(new Visitor(card, name, dept, purpose, date, timeIn));
        displayMessage(">> src.Visitor " + card + " added successfully!\n");
        saveData(); // Auto-save after adding
    }

    /**
     * METHOD: exitVisitor
     * DESCRIPTION: Updates the Time-Out of an existing visitor.
     * FEATURES: Searches by Card Number and saves updates automatically.
     */
    @Override
    public void exitVisitor() {
        displayMessage("\n=== LOG VISITOR EXIT ===\n");
        System.out.print("Enter Card Number of src.Visitor to update Time-Out: ");
        String card = scanner.nextLine();

        Visitor visitor = findVisitorByCard(card);

        if (visitor != null && visitor.getTimeOut().equals("N/A")) {
            displayMessage("\nsrc.Visitor Found: " + visitor.getName());

            // VALIDATION LOOP: Force correct Yes/No input for TIME-OUT
            String timeOutChoice;
            while(true) {
                System.out.print("Use automatic Time-Out now? (yes/no): ");
                timeOutChoice = scanner.nextLine().trim();
                if (timeOutChoice.equalsIgnoreCase("yes") || timeOutChoice.equalsIgnoreCase("no")) {
                    break;
                }
                displayMessage(">> Invalid input. Please type 'yes' or 'no'.");
            }

            String timeOut;
            if (timeOutChoice.equalsIgnoreCase("yes")) {
                timeOut = getCurrentTime();
                displayMessage(">> Time-Out recorded (auto): " + timeOut);
            } else {
                System.out.print("Enter Time-Out (HH:MM AM/PM): ");
                timeOut = scanner.nextLine();
            }

            visitor.setTimeOut(timeOut);
            displayMessage(">> Time-Out updated successfully!\n");
            saveData(); // Auto-save update
        } else {
            displayMessage(">> src.Visitor not found or already logged out.\n");
        }
    }

    /**
     * METHOD: viewVisitors
     * DESCRIPTION: Displays all records in a structured Table Format.
     */
    @Override
    public void viewVisitors() {
        displayMessage("\n=== VISITOR LOG RECORDS ===\n");

        if (!hasVisitors()) {
            displayMessage("No visitor records found.\n");
        } else {
            printTableHeader();
            for (Visitor v : visitors) {
                displayVisitor(v);
            }
            System.out.println("-------------------------------------------------------------------------------------------------------------------------");
            displayMessage("Total Visitors: " + getVisitorCount() + "\n");
        }
    }

    /**
     * METHOD: searchVisitor
     * DESCRIPTION: Allows searching by Card, Name, or Time-In.
     * RESULTS: Displays matches in Table Format.
     */
    @Override
    public void searchVisitor() {
        displayMessage("\n=== SEARCH VISITOR ===\n");
        displayMessage("Search by:");
        displayMessage("1 - Card Number");
        displayMessage("2 - Name");
        displayMessage("3 - Time-In");
        System.out.print("Choose option: ");

        try {
            int choice = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter keyword: ");
            String keyword = scanner.nextLine();

            boolean found = false;
            boolean headerPrinted = false; // Flag to print header only once

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
                System.out.println("-------------------------------------------------------------------------------------------------------------------------");
            }

            if (!found) {
                displayMessage("No visitor matched your search.\n");
            }
        } catch (NumberFormatException e) {
            displayMessage(">> Invalid input. Please enter numbers only.");
        }
    }

    /**
     * METHOD: deleteVisitor
     * DESCRIPTION: Removes a record permanently from the list and database.
     */
    @Override
    public void deleteVisitor() {
        displayMessage("\n=== DELETE VISITOR ===\n");
        System.out.print("Enter Card Number of src.Visitor to delete: ");
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
            displayMessage(">> src.Visitor deleted successfully!\n");
            saveData();
        } else {
            displayMessage(">> src.Visitor not found.\n");
        }
    }
}