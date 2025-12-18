package visitor;

/**
 * CLASS: Visitor
 * ROLE: Model / Data Object
 * OOP PRINCIPLE: Encapsulation
 * DESCRIPTION: Represents a single src.visitor entity. Attributes are private
 * to ensure data integrity and accessed via Getters/Setters.
 */
public class Visitor {
    // Encapsulation: Private variables hidden from other classes
    private String cardNumber;
    private String name;
    private String department;
    private String purpose;
    private String date;
    private String timeIn;
    private String timeOut;

    /**
     * CONSTRUCTOR: Initializes a new Visitor object.
     * NOTE: timeOut is set to "N/A" by default since they just arrived.
     */
    public Visitor(String cardNumber, String name, String department,
                   String purpose, String date, String timeIn) {
        this.cardNumber = cardNumber;
        this.name = name;
        this.department = department;
        this.purpose = purpose;
        this.date = date;
        this.timeIn = timeIn;
        this.timeOut = "N/A";
    }

    // GETTERS: Allow read-only access to private data
    public String getCardNumber() { return cardNumber; }
    public String getName() { return name; }
    public String getDepartment() { return department; }
    public String getPurpose() { return purpose; }
    public String getDate() { return date; }
    public String getTimeIn() { return timeIn; }
    public String getTimeOut() { return timeOut; }

    // SETTER: Allows updating the Time-Out when src.visitor leaves
    public void setTimeOut(String timeOut) {
        this.timeOut = timeOut;
    }

    public boolean hasExited() {
        return !this.timeOut.equals("N/A");
    }

    /**
     * METHOD: toString
     * OOP PRINCIPLE: Polymorphism (Method Overriding)
     * DESCRIPTION: Returns a string representation of the object.
     */
    @Override
    public String toString() {
        return "[CARD: " + cardNumber + "] " + name + " | visited " + department +
                " | Purpose: " + purpose + " | Date: " + date +
                " | Time-In: " + timeIn + " | Time-Out: " + timeOut;
    }
}