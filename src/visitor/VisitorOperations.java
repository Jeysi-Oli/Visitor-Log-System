package visitor;

/**
 * INTERFACE: VisitorOperations
 * OOP PRINCIPLE: Abstraction (Interface)
 * DESCRIPTION: Defines the "Contract" or list of mandatory methods
 * that the VisitorSystem must implement.
 */
public interface VisitorOperations {

    void addVisitor();

    void exitVisitor();

    void viewVisitors();

    void searchVisitor();

    void deleteVisitor();
}