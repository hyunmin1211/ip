/**
 * Parses and validates commands entered by the user.
 */
public final class Parser {
    private Parser() {
    }

    /**
     * Creates a todo task from a todo command.
     *
     * @param input Complete todo command.
     * @return Todo task described by the command.
     * @throws ChrisException If the description is empty.
     */
    public static Todo parseTodo(String input) throws ChrisException {
        String description = input.substring("todo".length()).trim();
        if (description.isEmpty()) {
            throw new ChrisException("I need a description for that todo. Try: todo borrow book");
        }
        return new Todo(description);
    }

    /**
     * Creates a deadline task from a deadline command.
     *
     * @param input Complete deadline command.
     * @return Deadline task described by the command.
     * @throws ChrisException If its description or deadline information is missing.
     */
    public static Deadline parseDeadline(String input) throws ChrisException {
        String details = input.substring("deadline".length()).trim();
        int byPosition = details.indexOf("/by");
        if (byPosition < 0) {
            throw new ChrisException("A deadline needs '/by'. Try: deadline return book /by Sunday");
        }

        String description = details.substring(0, byPosition).trim();
        String by = details.substring(byPosition + "/by".length()).trim();
        if (description.isEmpty()) {
            throw new ChrisException("I need a description before '/by'.");
        }
        if (by.isEmpty()) {
            throw new ChrisException("I need deadline information after '/by'.");
        }
        return new Deadline(description, by);
    }

    /**
     * Creates an event task from an event command.
     *
     * @param input Complete event command.
     * @return Event task described by the command.
     * @throws ChrisException If its description, start, or end information is missing.
     */
    public static Event parseEvent(String input) throws ChrisException {
        String details = input.substring("event".length()).trim();
        int fromPosition = details.indexOf("/from");
        if (fromPosition < 0) {
            throw new ChrisException("An event needs '/from'. Try: event meeting /from 2pm /to 4pm");
        }

        String description = details.substring(0, fromPosition).trim();
        String timing = details.substring(fromPosition + "/from".length()).trim();
        int toPosition = timing.indexOf("/to");
        if (toPosition < 0) {
            throw new ChrisException("An event needs '/to'. Try: event meeting /from 2pm /to 4pm");
        }

        String from = timing.substring(0, toPosition).trim();
        String to = timing.substring(toPosition + "/to".length()).trim();
        if (description.isEmpty()) {
            throw new ChrisException("I need an event description before '/from'.");
        }
        if (from.isEmpty()) {
            throw new ChrisException("I need a start time after '/from'.");
        }
        if (to.isEmpty()) {
            throw new ChrisException("I need an end time after '/to'.");
        }
        return new Event(description, from, to);
    }

    /**
     * Extracts and validates a task index from a mark, unmark, or delete command.
     *
     * @param input Complete mark, unmark, or delete command.
     * @param command Command word being parsed.
     * @param taskCount Current number of stored tasks.
     * @return Zero-based index of the selected task.
     * @throws ChrisException If the task number is missing, invalid, or out of range.
     */
    public static int parseTaskIndex(String input, String command, int taskCount) throws ChrisException {
        String numberText = input.substring(command.length()).trim();
        if (numberText.isEmpty()) {
            throw new ChrisException("Tell me which task to " + command + ". Try: " + command + " 2");
        }

        int taskNumber;
        try {
            taskNumber = Integer.parseInt(numberText);
        } catch (NumberFormatException exception) {
            throw new ChrisException("The task number must be a whole number.");
        }

        if (taskNumber < 1 || taskNumber > taskCount) {
            throw new ChrisException("Task " + taskNumber + " does not exist. Choose a number from the list.");
        }
        return taskNumber - 1;
    }
}
