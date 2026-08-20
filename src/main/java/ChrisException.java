/**
 * Represents an error caused by an invalid command entered by the user.
 */
public class ChrisException extends Exception {
    private static final long serialVersionUID = 1L;

    /**
     * Creates an exception with a user-facing explanation of the error.
     *
     * @param message Explanation of the invalid command.
     */
    public ChrisException(String message) {
        super(message);
    }
}
