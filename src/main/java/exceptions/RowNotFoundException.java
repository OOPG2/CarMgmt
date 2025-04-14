/**
 * The RowNotFoundException class represents a custom exception used to indicate
 * that a specific row was not found during an operation.
 * It extends the base Exception class.
 */
package exceptions;

/**
 * RowNotFoundException is thrown when a row in a dataset or database cannot be located.
 */
public class RowNotFoundException extends Exception {

    /**
     * Serial version UID for serialization.
     */
    private static final long serialVersionUID = -8838486308079386864L;

    /**
     * Constructs a new RowNotFoundException with the specified error message.
     *
     * @param errorMessage the detail message explaining the reason for the exception
     */
    public RowNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
