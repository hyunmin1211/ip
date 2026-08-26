import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Represents a task that must be completed by a specified time.
 */
public class Deadline extends Task {
    private static final DateTimeFormatter DISPLAY_FORMAT =
            DateTimeFormatter.ofPattern("MMM d yyyy", Locale.ENGLISH);

    /** Date by which this task must be completed. */
    protected LocalDate by;

    /**
     * Creates an incomplete deadline task.
     *
     * @param description Description of the deadline task.
     * @param by Date by which the task must be completed.
     */
    public Deadline(String description, LocalDate by) {
        super(description);
        this.by = by;
    }

    /**
     * Returns this deadline task in the format used for persistent storage.
     *
     * @return Serialized deadline task.
     */
    @Override
    public String toDataString() {
        return "D | " + getStatusNumber() + " | " + this.description + " | " + this.by;
    }

    /**
     * Returns this deadline task in its display format.
     *
     * @return The deadline type, status, description, and deadline information.
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + this.by.format(DISPLAY_FORMAT) + ")";
    }
}
