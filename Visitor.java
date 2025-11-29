public class Visitor {
    private String cardNumber;
    private String name;
    private String department;
    private String purpose;
    private String date;
    private String timeIn;
    private String timeOut;

    public Visitor(String cardNumber, String name, String department, String purpose, String date, String timeIn) {
        this.cardNumber = cardNumber;
        this.name = name;
        this.department = department;
        this.purpose = purpose;
        this.date = date;
        this.timeIn = timeIn;
        this.timeOut = "N/A";
    }

    public String getCardNumber() { return cardNumber; }
    public String getName() { return name; }
    public String getDepartment() { return department; }
    public String getPurpose() { return purpose; }
    public String getDate() { return date; }
    public String getTimeIn() { return timeIn; }
    public String getTimeOut() { return timeOut; }

    public void setTimeOut(String timeOut) {
        this.timeOut = timeOut;
    }

    @Override
    public String toString() {
        return "[CARD: " + cardNumber + "] " + name + " |  visited " + department +
                " | Purpose: " + purpose + " | Date: " + date +
                " | Time-In: " + timeIn + " | Time-Out: " + timeOut;
    }
}
