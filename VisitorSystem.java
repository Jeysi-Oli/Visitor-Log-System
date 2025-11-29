import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class VisitorSystem {
    protected ArrayList<Visitor> visitors = new ArrayList<>();
    protected Scanner scanner = new Scanner(System.in);

    private final String logFile = "visitor_logs.txt";

    // Utility date formatter for auto date
    private final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    private final DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("hh:mm a");

    // ADD VISITOR ENTRY
    public void addVisitor() {
        System.out.println("\n=== ADD VISITOR ENTRY ===");

        System.out.print("Enter Card Number: ");
        String card = scanner.nextLine();

        System.out.print("Enter Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Department/Office to visit: ");
        String dept = scanner.nextLine();

        System.out.print("Enter Purpose: ");
        String purpose = scanner.nextLine();

        // Optional auto/manual date
        System.out.print("Use automatic date today? (yes/no): ");
        String dateChoice = scanner.nextLine();

        String date;
        if (dateChoice.equalsIgnoreCase("yes")) {
            date = LocalDate.now().format(dateFormat);
            System.out.println("Date recorded (auto): " + date);
        } else {
            System.out.print("Enter Date (MM/DD/YYYY): ");
            date = scanner.nextLine();
        }

        // Optional auto/manual time in
        System.out.print("Use automatic Time-In now? (yes/no): ");
        String timeInChoice = scanner.nextLine();

        String timeIn;
        if (timeInChoice.equalsIgnoreCase("yes")) {
            timeIn = LocalTime.now().format(timeFormat);
            System.out.println("Time-In recorded (auto): " + timeIn);
        } else {
            System.out.print("Enter Time-In (HH:MM AM/PM): ");
            timeIn = scanner.nextLine();
        }

        visitors.add(new Visitor(card, name, dept, purpose, date, timeIn));
        System.out.println("Visitor " + card + " added!\n");

        saveToFile();
    }

    // UPDATE VISITOR EXIT (by card number)
    public void exitVisitor() {
        System.out.println("\n=== LOG VISITOR EXIT ===");
        System.out.print("Enter Card Number of Visitor to update Time-Out: ");
        String card = scanner.nextLine();

        for (Visitor v : visitors) {
            if (v.getCardNumber().equalsIgnoreCase(card)) {
                System.out.println("\nVisitor Found: ");
                System.out.println(v);

                System.out.print("Use automatic Time-Out now? (yes/no): ");
                String timeOutChoice = scanner.nextLine();

                String timeOut;
                if (timeOutChoice.equalsIgnoreCase("yes")) {
                    timeOut = LocalTime.now().format(timeFormat);
                    System.out.println("Time-Out recorded (auto): " + timeOut);
                } else {
                    System.out.print("Enter Time-Out (HH:MM AM/PM): ");
                    timeOut = scanner.nextLine();
                }

                v.setTimeOut(timeOut);
                System.out.println("Time-Out updated!\n");
                saveToFile();
                return;
            }
        }
        System.out.println("Visitor not found with that card number.\n");
    }

    // VIEW ALL VISITORS (with all details included)
    public void viewVisitors() {
        System.out.println("\n=== VISITOR LOG RECORDS ===");
        if (visitors.isEmpty()) {
            System.out.println("No visitor records found.\n");
        } else {
            for (Visitor v : visitors) {
                System.out.println(v + "\n");
            }
        }
    }

    // SEARCH VISITOR by card, name, or time-in
    public void searchVisitor() {
        System.out.println("\n=== SEARCH VISITOR ===");
        System.out.println("Search by:");
        System.out.println("1 - Card Number");
        System.out.println("2 - Name");
        System.out.println("3 - Time-In");
        System.out.print("Choose option: ");
        int choice = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter keyword: ");
        String keyword = scanner.nextLine();

        boolean found = false;

        for (Visitor v : visitors) {
            if ((choice == 1 && v.getCardNumber().equalsIgnoreCase(keyword)) ||
                    (choice == 2 && v.getName().equalsIgnoreCase(keyword)) ||
                    (choice == 3 && v.getTimeIn().equalsIgnoreCase(keyword))) {

                System.out.println("\n" + v);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No visitor matched your search.\n");
        }
    }

    // DELETE VISITOR (optional but logic included)
    public void deleteVisitor() {
        System.out.println("\n=== DELETE VISITOR ===");
        System.out.print("Enter Card Number of Visitor to delete: ");
        String card = scanner.nextLine();

        Visitor toRemove = null;
        for (Visitor v : visitors) {
            if (v.getCardNumber().equalsIgnoreCase(card)) {
                toRemove = v;
                break;
            }
        }

        if (toRemove != null) {
            visitors.remove(toRemove);
            System.out.println("Visitor deleted!\n");
            saveToFile();
        } else {
            System.out.println("Visitor not found.\n");
        }
    }

    // SAVE LOGS (formatted)
    private void saveToFile() {
        try (FileWriter fw = new FileWriter(logFile, false);
             PrintWriter pw = new PrintWriter(fw)) {

            pw.println("===  VISITOR LOG REPORT ===\n");

            for (Visitor v : visitors) {
                pw.println(v);
            }

        } catch (IOException e) {
            System.out.println("Error writing logs to text file.");
        }
    }

    private void saveToFileAppend(Visitor v, String action) {
        try (FileWriter fw = new FileWriter(logFile, true);
             PrintWriter pw = new PrintWriter(fw)) {

            pw.println(action + " → " + v);

        } catch (IOException e) {
            System.out.println("Error appending logs.");
        }
    }
}
