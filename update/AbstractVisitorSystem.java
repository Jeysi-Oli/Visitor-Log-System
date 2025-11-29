import java.time.LocalDate;
import java. time.LocalTime;
import java. time.format.DateTimeFormatter;
import java.util. ArrayList;
import java.util.Scanner;

public abstract class AbstractVisitorSystem implements VisitorOperations {

    protected ArrayList<Visitor> visitors = new ArrayList<>();
    protected Scanner scanner = new Scanner(System.in);

    protected final DateTimeFormatter dateFormat = DateTimeFormatter. ofPattern("MM/dd/yyyy");
    protected final DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("hh:mm a");

    protected abstract void saveData();

    protected abstract void loadData();

    protected abstract void displayMessage(String message);

    protected abstract void displayVisitor(Visitor visitor);

    protected String getCurrentDate() {
        return LocalDate.now().format(dateFormat);
    }

    protected String getCurrentTime() {
        return LocalTime.now().format(timeFormat);
    }

    protected Visitor findVisitorByCard(String cardNumber) {
        for (Visitor v : visitors) {
            if (v. getCardNumber().equalsIgnoreCase(cardNumber)) {
                return v;
            }
        } 
        return null;   
    }

    protected boolean hasVisitors() {
        return ! visitors.isEmpty();
    }

    protected int getVisitorCount() {
        return visitors. size();
    }
}