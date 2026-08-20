/**
 * Represents a task that occurs between specified start and end times.
 */
public class Event extends Task {
    /** Start information as entered by the user. */
    protected String from;

    /** End information as entered by the user. */
    protected String to;

    /**
     * Creates an incomplete event task.
     *
     * @param description Description of the event task.
     * @param from Start information as entered by the user.
     * @param to End information as entered by the user.
     */
    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    /**
     * Returns this event task in its display format.
     *
     * @return The event type, status, description, and timing information.
     */
    @Override
    public String toString() {
        return "[E]" + super.toString()
                + " (from: " + this.from + " to: " + this.to + ")";
    }
}
