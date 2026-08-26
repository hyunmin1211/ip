import java.nio.file.Path;
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

        Storage storage = new Storage(Path.of("data", "chris.txt"));
        TaskList tasks = loadTasks(storage);

        System.out.println(line);
        System.out.println(banner);
        System.out.println("Hello! I'm Chris.");
        System.out.println("What can I do for you?");
        System.out.println(line);

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String input = scanner.nextLine().trim();
            String commandWord = input.isEmpty() ? "" : input.split("\\s+", 2)[0];
            CommandType commandType = CommandType.parseCommandWord(commandWord);
            System.out.println(line);
            boolean shouldExit = false;

            try {
                switch (commandType) {
                    case BYE -> {
                        System.out.println("Bye. Hope to see you again soon!");
                        shouldExit = true;
                    }
                    case LIST -> {
                        System.out.println("Here are the tasks in your list:");
                        for (int i = 0; i < tasks.size(); i++) {
                            System.out.println((i + 1) + "." + tasks.get(i));
                        }
                    }
                    case MARK -> {
                        int taskIndex = Parser.parseTaskIndex(input, commandType.getCommandWord(), tasks.size());
                        Task markedTask = tasks.mark(taskIndex);
                        storage.saveTasks(tasks.asList());
                        System.out.println("Nice! I've marked this task as done:");
                        System.out.println("  " + markedTask);
                    }
                    case UNMARK -> {
                        int taskIndex = Parser.parseTaskIndex(input, commandType.getCommandWord(), tasks.size());
                        Task unmarkedTask = tasks.unmark(taskIndex);
                        storage.saveTasks(tasks.asList());
                        System.out.println("OK, I've marked this task as not done yet:");
                        System.out.println("  " + unmarkedTask);
                    }
                    case DELETE -> {
                        int taskIndex = Parser.parseTaskIndex(input, commandType.getCommandWord(), tasks.size());
                        Task removedTask = tasks.delete(taskIndex);
                        storage.saveTasks(tasks.asList());
                        System.out.println("Noted. I've removed this task:");
                        System.out.println("  " + removedTask);
                        showTaskCount(tasks.size());
                    }
                    case TODO ->
                        addTask(tasks, Parser.parseTodo(input), storage);
                    case DEADLINE ->
                        addTask(tasks, Parser.parseDeadline(input), storage);
                    case EVENT ->
                        addTask(tasks, Parser.parseEvent(input), storage);
                    case UNKNOWN -> throw new ChrisException("I don't recognize that command. "
                            + "Try todo, deadline, event, list, mark, unmark, delete, or bye.");
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
     * Loads saved tasks, or starts with an empty list if loading fails.
     *
     * @param storage Storage from which tasks are loaded.
     * @return Loaded tasks, or an empty list when the data cannot be loaded.
     */
    private static TaskList loadTasks(Storage storage) {
        try {
            return new TaskList(storage.loadTasks());
        } catch (ChrisException exception) {
            System.out.println("OOPS!!! " + exception.getMessage());
            return new TaskList();
        }
    }

    /**
     * Stores a task and displays confirmation of the addition.
     *
     * @param tasks List in which tasks are stored.
     * @param task Task to add.
     * @param storage Storage to update after adding the task.
     * @throws ChrisException If the updated task list cannot be saved.
     */
    private static void addTask(TaskList tasks, Task task, Storage storage) throws ChrisException {
        tasks.add(task);
        storage.saveTasks(tasks.asList());
        showTaskAdded(task, tasks.size());
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
        showTaskCount(taskCount);
    }

    /**
     * Displays the current number of tasks with correct singular or plural grammar.
     *
     * @param taskCount Current number of tasks.
     */
    private static void showTaskCount(int taskCount) {
        String taskWord = taskCount == 1 ? "task" : "tasks";
        System.out.println("Now you have " + taskCount + " " + taskWord + " in the list.");
    }
}
