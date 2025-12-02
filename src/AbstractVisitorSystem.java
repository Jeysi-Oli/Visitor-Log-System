package src;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * ABSTRACT CLASS: src.AbstractVisitorSystem
 * OOP PRINCIPLE: Abstraction & Inheritance
 * DESCRIPTION: Provides the blueprint and shared resources (List, Scanner)
 * for the main system. Contains both abstract methods (to be implemented)
 * and concrete methods (ready to use).
 */

public abstract class AbstractVisitorSystem implements VisitorOperations {

    // Shared Resources available to subclasses
    protected ArrayList<Visitor> visitors = new ArrayList<>();
    protected Scanner scanner = new Scanner(System.in);

    // Date Formatters for standardization
    protected final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    protected final DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("hh:mm a");

    // Abstract Methods: Must be implemented by the Child Class
    protected abstract void saveData();
    protected abstract void loadData();
    protected abstract void displayMessage(String message);
    protected abstract void displayVisitor(Visitor visitor);

    // Helper Methods: Pre-written logic for dates and finding visitors
    protected String getCurrentDate() {
        return LocalDate.now().format(dateFormat);
    }

    protected String getCurrentTime() {
        return LocalTime.now().format(timeFormat);
    }

    protected Visitor findVisitorByCard(String cardNumber) {
        for (Visitor v : visitors) {
            if (v.getCardNumber().equalsIgnoreCase(cardNumber)) {
                return v;
            }
        }
        return null;
    }

    protected boolean hasVisitors() {
        return !visitors.isEmpty();
    }

    protected int getVisitorCount() {
        return visitors.size();
    }
}