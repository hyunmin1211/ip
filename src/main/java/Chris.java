import java.util.Scanner;

/**
 * Runs the Chris chatbot and handles user commands.
 */
public class Chris {
    private Chris() {
    }

    /**
     * Starts the chatbot command loop.
     *
     * @param args Command-line arguments; not used.
     */
    public static void main(String[] args) {
        String banner = "  ____ _          _     \n"
                + " / ___| |__  _ __(_)___ \n"
                + "| |   | '_ \\| '__| / __|\n"
                + "| |___| | | | |  | \\__ \\\n"
                + " \\____|_| |_|_|  |_|___/";
        String line = "____________________________________________________________";

        Task[] tasks = new Task[100];
        int taskCount = 0;

        System.out.println(line);
        System.out.println(banner);
        System.out.println("Hello! I'm Chris.");
        System.out.println("What can I do for you?");
        System.out.println(line);

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String input = scanner.nextLine().trim();
            String command = input.isEmpty() ? "" : input.split("\\s+", 2)[0];
            System.out.println(line);
            boolean shouldExit = false;

            try {
                switch (command) {
                case "bye" -> {
                    System.out.println("Bye. Hope to see you again soon!");
                    shouldExit = true;
                }
                case "list" -> {
                    System.out.println("Here are the tasks in your list:");
                    for (int i = 0; i < taskCount; i++) {
                        System.out.println((i + 1) + "." + tasks[i]);
                    }
                }
                case "mark" -> {
                    int taskIndex = Parser.parseTaskIndex(input, "mark", taskCount);
                    tasks[taskIndex].markAsDone();
                    System.out.println("Nice! I've marked this task as done:");
                    System.out.println("  " + tasks[taskIndex]);
                }
                case "unmark" -> {
                    int taskIndex = Parser.parseTaskIndex(input, "unmark", taskCount);
                    tasks[taskIndex].markAsNotDone();
                    System.out.println("OK, I've marked this task as not done yet:");
                    System.out.println("  " + tasks[taskIndex]);
                }
                case "todo" ->
                    taskCount = addTask(tasks, taskCount, Parser.parseTodo(input));
                case "deadline" ->
                    taskCount = addTask(tasks, taskCount, Parser.parseDeadline(input));
                case "event" ->
                    taskCount = addTask(tasks, taskCount, Parser.parseEvent(input));
                default -> throw new ChrisException("I don't recognize that command. "
                        + "Try todo, deadline, event, list, mark, unmark, or bye.");
                }
            } catch (ChrisException exception) {
                System.out.println("OOPS!!! " + exception.getMessage());
            }

            System.out.println(line);
            if (shouldExit) {
                break;
            }
        }
        scanner.close();
    }

    /**
     * Stores a task and displays confirmation of the addition.
     *
     * @param tasks Array in which tasks are stored.
     * @param taskCount Number of tasks before the addition.
     * @param task Task to add.
     * @return Number of tasks after the addition.
     * @throws ChrisException If the task list is full.
     */
    private static int addTask(Task[] tasks, int taskCount, Task task) throws ChrisException {
        if (taskCount >= tasks.length) {
            throw new ChrisException("Your task list is full. Remove a task before adding another one.");
        }
        tasks[taskCount] = task;
        int updatedTaskCount = taskCount + 1;
        showTaskAdded(task, updatedTaskCount);
        return updatedTaskCount;
    }

    /**
     * Displays confirmation that a task was added.
     *
     * @param task Task that was added.
     * @param taskCount Number of tasks after the addition.
     */
    private static void showTaskAdded(Task task, int taskCount) {
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
    }
}
