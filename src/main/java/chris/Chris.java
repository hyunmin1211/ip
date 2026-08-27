package chris;

import java.nio.file.Path;

import chris.exception.ChrisException;
import chris.parser.CommandType;
import chris.parser.Parser;
import chris.storage.Storage;
import chris.task.Task;
import chris.task.TaskList;
import chris.ui.Ui;

/**
 * Coordinates the chatbot's UI, task list, parser, and storage components.
 */
public class Chris {
    private final Ui ui;
    private final Storage storage;
    private final TaskList tasks;

    /**
     * Creates a chatbot that stores tasks at the specified file path.
     *
     * @param filePath Path to the task data file.
     */
    public Chris(Path filePath) {
        this.ui = new Ui();
        this.storage = new Storage(filePath);
        this.tasks = loadTasks();
    }

    /**
     * Runs the chatbot command loop until the user exits or input ends.
     */
    public void run() {
        this.ui.showWelcome();
        boolean shouldExit = false;

        while (!shouldExit && this.ui.hasNextCommand()) {
            String input = this.ui.readCommand();
            this.ui.showLine();
            try {
                shouldExit = executeCommand(input);
            } catch (ChrisException exception) {
                this.ui.showError(exception.getMessage());
            } finally {
                this.ui.showLine();
            }
        }
        this.ui.close();
    }

    private boolean executeCommand(String input) throws ChrisException {
        CommandType commandType = Parser.parseCommandType(input);
        switch (commandType) {
            case BYE -> this.ui.showGoodbye();
            case LIST -> this.ui.showTaskList(this.tasks);
            case FIND -> this.ui.showMatchingTasks(this.tasks.find(Parser.parseFindKeyword(input)));
            case MARK -> markTask(input, commandType);
            case UNMARK -> unmarkTask(input, commandType);
            case DELETE -> deleteTask(input, commandType);
            case TODO -> addTask(Parser.parseTodo(input));
            case DEADLINE -> addTask(Parser.parseDeadline(input));
            case EVENT -> addTask(Parser.parseEvent(input));
            case UNKNOWN -> throw new ChrisException("I don't recognize that command. "
                    + "Try todo, deadline, event, list, find, mark, unmark, delete, or bye.");
        }
        return commandType == CommandType.BYE;
    }

    private void markTask(String input, CommandType commandType) throws ChrisException {
        int taskIndex = Parser.parseTaskIndex(input, commandType.getCommandWord(), this.tasks.size());
        Task markedTask = this.tasks.mark(taskIndex);
        saveTasks();
        this.ui.showTaskMarked(markedTask);
    }

    private void unmarkTask(String input, CommandType commandType) throws ChrisException {
        int taskIndex = Parser.parseTaskIndex(input, commandType.getCommandWord(), this.tasks.size());
        Task unmarkedTask = this.tasks.unmark(taskIndex);
        saveTasks();
        this.ui.showTaskUnmarked(unmarkedTask);
    }

    private void deleteTask(String input, CommandType commandType) throws ChrisException {
        int taskIndex = Parser.parseTaskIndex(input, commandType.getCommandWord(), this.tasks.size());
        Task removedTask = this.tasks.delete(taskIndex);
        saveTasks();
        this.ui.showTaskDeleted(removedTask, this.tasks.size());
    }

    private void addTask(Task task) throws ChrisException {
        this.tasks.add(task);
        saveTasks();
        this.ui.showTaskAdded(task, this.tasks.size());
    }

    private void saveTasks() throws ChrisException {
        this.storage.saveTasks(this.tasks.asList());
    }

    private TaskList loadTasks() {
        try {
            return new TaskList(this.storage.loadTasks());
        } catch (ChrisException exception) {
            this.ui.showError(exception.getMessage());
            return new TaskList();
        }
    }

    /**
     * Starts the chatbot using the default relative data-file path.
     *
     * @param args Command-line arguments; not used.
     */
    public static void main(String[] args) {
        new Chris(Path.of("data", "chris.txt")).run();
    }
}
