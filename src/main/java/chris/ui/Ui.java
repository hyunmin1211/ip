package chris.ui;

import java.util.List;
import java.util.Scanner;

import chris.task.Task;
import chris.task.TaskList;

/**
 * Handles console input and output for the chatbot.
 */
public class Ui {
    private static final String BANNER = "  ____ _          _     \n"
            + " / ___| |__  _ __(_)___ \n"
            + "| |   | '_ \\| '__| / __|\n"
            + "| |___| | | | |  | \\__ \\\n"
            + " \\____|_| |_|_|  |_|___/";
    private static final String LINE = "____________________________________________________________";

    private final Scanner scanner;

    /**
     * Creates a UI that reads from standard input.
     */
    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Displays the chatbot greeting.
     */
    public void showWelcome() {
        showLine();
        System.out.println(BANNER);
        System.out.println("Hello! I'm Chris.");
        System.out.println("What can I do for you?");
        showLine();
    }

    /**
     * Returns whether another command can be read.
     *
     * @return {@code true} if another command is available.
     */
    public boolean hasNextCommand() {
        return this.scanner.hasNextLine();
    }

    /**
     * Reads and trims the next user command.
     *
     * @return Next command entered by the user.
     */
    public String readCommand() {
        return this.scanner.nextLine().trim();
    }

    /**
     * Displays the horizontal divider line.
     */
    public void showLine() {
        System.out.println(LINE);
    }

    /**
     * Displays the farewell message.
     */
    public void showGoodbye() {
        System.out.println("Bye. Hope to see you again soon!");
    }

    /**
     * Displays all tasks with one-based numbering.
     *
     * @param tasks Tasks to display.
     */
    public void showTaskList(TaskList tasks) {
        System.out.println("Here are the tasks in your list:");
        for (int index = 0; index < tasks.size(); index++) {
            System.out.println((index + 1) + "." + tasks.get(index));
        }
    }

    /**
     * Displays tasks that match a search keyword.
     *
     * @param matchingTasks Matching tasks to display.
     */
    public void showMatchingTasks(List<Task> matchingTasks) {
        System.out.println("Here are the matching tasks in your list:");
        for (int index = 0; index < matchingTasks.size(); index++) {
            System.out.println((index + 1) + "." + matchingTasks.get(index));
        }
    }

    /**
     * Displays confirmation that a task was marked as completed.
     *
     * @param task Updated task.
     */
    public void showTaskMarked(Task task) {
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("  " + task);
    }

    /**
     * Displays confirmation that a task was marked as incomplete.
     *
     * @param task Updated task.
     */
    public void showTaskUnmarked(Task task) {
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println("  " + task);
    }

    /**
     * Displays confirmation that a task was removed.
     *
     * @param task Removed task.
     * @param taskCount Number of remaining tasks.
     */
    public void showTaskDeleted(Task task, int taskCount) {
        System.out.println("Noted. I've removed this task:");
        System.out.println("  " + task);
        showTaskCount(taskCount);
    }

    /**
     * Displays confirmation that a task was added.
     *
     * @param task Added task.
     * @param taskCount Number of stored tasks.
     */
    public void showTaskAdded(Task task, int taskCount) {
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        showTaskCount(taskCount);
    }

    /**
     * Displays a user-facing error message.
     *
     * @param message Explanation of the error.
     */
    public void showError(String message) {
        System.out.println("OOPS!!! " + message);
    }

    /**
     * Closes the input scanner.
     */
    public void close() {
        this.scanner.close();
    }

    private void showTaskCount(int taskCount) {
        String taskWord = taskCount == 1 ? "task" : "tasks";
        System.out.println("Now you have " + taskCount + " " + taskWord + " in the list.");
    }
}
