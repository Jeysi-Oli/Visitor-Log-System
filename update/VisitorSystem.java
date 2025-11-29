import java.io. FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class VisitorSystem extends AbstractVisitorSystem {

    private final String logFile = "visitor_logs.txt";

    @Override
    protected void saveData() {
        try (FileWriter fw = new FileWriter(logFile, false);
             PrintWriter pw = new PrintWriter(fw)) {

            pw.println("=== BSU LIPA VISITOR LOG REPORT ===\n");

            for (Visitor v : visitors) {
                pw.println(v);
            }

        } catch (IOException e) {
            displayMessage("Error writing logs to file.");
        }
    }

    @Override
    protected void loadData() {
        displayMessage("Data loaded successfully.");
    }

    @Override
    protected void displayMessage(String message) {
        System. out.println(message);
    }

    @Override
    protected void displayVisitor(Visitor visitor) {
        System.out.println(visitor. toString());
    }

    @Override
    public void addVisitor() {
        displayMessage("\n=== ADD VISITOR ENTRY ===");

        System.out.print("Enter Card Number: ");
        String card = scanner.nextLine();

        System.out. print("Enter Name: ");
        String name = scanner.nextLine();

        System. out.print("Enter Department/Office to visit: ");
        String dept = scanner.nextLine();

        System. out.print("Enter Purpose: ");
        String purpose = scanner.nextLine();

        System.out.print("Use automatic date today?  (yes/no): ");
        String dateChoice = scanner.nextLine();

        String date;
        if (dateChoice.equalsIgnoreCase("yes")) {
            date = getCurrentDate();
            displayMessage("Date recorded (auto): " + date);
        } else {
            System.out.print("Enter Date (MM/DD/YYYY): ");
            date = scanner.nextLine();
        }

        System.out.print("Use automatic Time-In now? (yes/no): ");
        String timeInChoice = scanner.nextLine();

        String timeIn;
        if (timeInChoice.equalsIgnoreCase("yes")) {
            timeIn = getCurrentTime();
            displayMessage("Time-In recorded (auto): " + timeIn);
        } else {
            System.out.print("Enter Time-In (HH:MM AM/PM): ");
            timeIn = scanner. nextLine();
        }

        visitors.add(new Visitor(card, name, dept, purpose, date, timeIn));
        displayMessage("Visitor " + card + " added successfully!\n");

        saveData();
    }

    @Override
    public void exitVisitor() {  
        displayMessage("\n=== LOG VISITOR EXIT ===");
        System.out.print("Enter Card Number of Visitor to update Time-Out: ");
        String card = scanner.nextLine();

        Visitor visitor = findVisitorByCard(card);

        if (visitor != null) {
            displayMessage("\nVisitor Found: ");
            displayVisitor(visitor);

            System.out.print("Use automatic Time-Out now? (yes/no): ");
            String timeOutChoice = scanner.nextLine();

            String timeOut;
            if (timeOutChoice.equalsIgnoreCase("yes")) {
                timeOut = getCurrentTime();
                displayMessage("Time-Out recorded (auto): " + timeOut);
            } else {
                System.out.print("Enter Time-Out (HH:MM AM/PM): ");
                timeOut = scanner.nextLine();
            }

            visitor.setTimeOut(timeOut);
            displayMessage("Time-Out updated successfully!\n");
            saveData();
        } else { 
            displayMessage("Visitor not found with that card number.\n");
        }
    }

    @Override
    public void viewVisitors() {
        displayMessage("\n=== VISITOR LOG RECORDS ===");

        if (! hasVisitors()) {
            displayMessage("No visitor records found.\n");
        } else {
            displayMessage("Total Visitors: " + getVisitorCount() + "\n");
            for (Visitor v : visitors) {
                displayVisitor(v);
                System.out.println();
            }
        }
    }

    @Override
    public void searchVisitor() {
        displayMessage("\n=== SEARCH VISITOR ===");
        displayMessage("Search by:");
        displayMessage("1 - Card Number");
        displayMessage("2 - Name");
        displayMessage("3 - Time-In");
        System.out.print("Choose option: ");
        int choice = Integer.parseInt(scanner. nextLine());

        System.out.print("Enter keyword: ");
        String keyword = scanner.nextLine();

        boolean found = false;

        for (Visitor v : visitors) {
            if ((choice == 1 && v.getCardNumber().equalsIgnoreCase(keyword)) ||
                    (choice == 2 && v.getName().equalsIgnoreCase(keyword)) ||
                    (choice == 3 && v.getTimeIn().equalsIgnoreCase(keyword))) {

                displayMessage("");
                displayVisitor(v);
                found = true;
            }
        }

        if (!found) {
            displayMessage("No visitor matched your search.\n");
        }
    }

    @Override
    public void deleteVisitor() {
        displayMessage("\n=== DELETE VISITOR ===");
        System.out.print("Enter Card Number of Visitor to delete: ");
        String card = scanner.nextLine();

        Visitor toRemove = findVisitorByCard(card);

        if (toRemove != null) {
            visitors.remove(toRemove);
            displayMessage("Visitor deleted successfully!\n");
            saveData();
        } else {
            displayMessage("Visitor not found.\n");
        }
    }
}