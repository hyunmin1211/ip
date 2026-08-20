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
        while (true) {
            String input = scanner.nextLine();
            System.out.println(line);

            if (input.equals("bye")) {
                System.out.println("Bye. Hope to see you again soon!");
                System.out.println(line);
                break;
            } else if (input.equals("list")) {
                System.out.println("Here are the tasks in your list:");
                for (int i = 0; i < taskCount; i++) {
                    System.out.println((i + 1) + "." + tasks[i]);
                }
            } else if (input.startsWith("mark ")) {
                int taskNumber = Integer.parseInt(input.substring(5));
                int taskIndex = taskNumber - 1;
                tasks[taskIndex].markAsDone();
                System.out.println("Nice! I've marked this task as done:");
                System.out.println("  " + tasks[taskIndex]);
            } else if (input.startsWith("unmark ")) {
                int taskNumber = Integer.parseInt(input.substring(7));
                int taskIndex = taskNumber - 1;
                tasks[taskIndex].markAsNotDone();
                System.out.println("OK, I've marked this task as not done yet:");
                System.out.println("  " + tasks[taskIndex]);
            } else if (input.startsWith("todo ")) {
                Task task = new Todo(input.substring(5).trim());
                tasks[taskCount] = task;
                taskCount++;
                showTaskAdded(task, taskCount);
            } else if (input.startsWith("deadline ")) {
                String details = input.substring(9);
                String[] parts = details.split(" /by ", 2);
                Task task = new Deadline(parts[0].trim(), parts[1].trim());
                tasks[taskCount] = task;
                taskCount++;
                showTaskAdded(task, taskCount);
            } else if (input.startsWith("event ")) {
                String details = input.substring(6);
                String[] fromParts = details.split(" /from ", 2);
                String[] toParts = fromParts[1].split(" /to ", 2);
                Task task = new Event(fromParts[0].trim(), toParts[0].trim(), toParts[1].trim());
                tasks[taskCount] = task;
                taskCount++;
                showTaskAdded(task, taskCount);
            }

            System.out.println(line);
        }
        scanner.close();
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
