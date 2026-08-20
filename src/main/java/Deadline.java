/**
 * Represents a task that must be completed by a specified time.
 */
public class Deadline extends Task {
    /** Deadline information as entered by the user. */
    protected String by;

    /**
     * Creates an incomplete deadline task.
     *
     * @param description Description of the deadline task.
     * @param by Deadline information as entered by the user.
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    /**
     * Returns this deadline task in its display format.
     *
     * @return The deadline type, status, description, and deadline information.
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + this.by + ")";
    }
}
