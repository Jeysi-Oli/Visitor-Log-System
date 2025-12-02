package src;

/**
 * INTERFACE: src.VisitorOperations
 * OOP PRINCIPLE: Abstraction (Interface)
 * DESCRIPTION: Defines the "Contract" or list of mandatory methods
 * that the src.VisitorSystem must implement.
 */

public interface VisitorOperations {

    void addVisitor();

    void exitVisitor();

    void viewVisitors();

    void searchVisitor();

    void deleteVisitor();
}