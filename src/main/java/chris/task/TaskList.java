package chris.task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Stores tasks and provides operations for managing them.
 */
public class TaskList {
    private final ArrayList<Task> tasks;

    /**
     * Creates an empty task list.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Creates a task list containing the specified tasks.
     *
     * @param tasks Initial tasks.
     */
    public TaskList(List<Task> tasks) {
        this.tasks = new ArrayList<>(tasks);
    }

    /**
     * Adds a task to the list.
     *
     * @param task Task to add.
     */
    public void add(Task task) {
        this.tasks.add(task);
    }

    /**
     * Removes and returns a task from the list.
     *
     * @param index Zero-based index of the task.
     * @return Removed task.
     */
    public Task delete(int index) {
        return this.tasks.remove(index);
    }

    /**
     * Marks a task as completed and returns it.
     *
     * @param index Zero-based index of the task.
     * @return Updated task.
     */
    public Task mark(int index) {
        Task task = this.tasks.get(index);
        task.markAsDone();
        return task;
    }

    /**
     * Marks a task as incomplete and returns it.
     *
     * @param index Zero-based index of the task.
     * @return Updated task.
     */
    public Task unmark(int index) {
        Task task = this.tasks.get(index);
        task.markAsNotDone();
        return task;
    }

    /**
     * Returns the task at the specified index.
     *
     * @param index Zero-based index of the task.
     * @return Task at the specified index.
     */
    public Task get(int index) {
        return this.tasks.get(index);
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return Number of stored tasks.
     */
    public int size() {
        return this.tasks.size();
    }

    /**
     * Returns tasks whose descriptions contain the specified keyword.
     *
     * @param keyword Keyword to search for.
     * @return Matching tasks in their original order.
     */
    public List<Task> find(String keyword) {
        ArrayList<Task> matchingTasks = new ArrayList<>();
        for (Task task : this.tasks) {
            if (task.containsKeyword(keyword)) {
                matchingTasks.add(task);
            }
        }
        return matchingTasks;
    }

    /**
     * Returns an unmodifiable view of the stored tasks.
     *
     * @return Unmodifiable task view.
     */
    public List<Task> asList() {
        return Collections.unmodifiableList(this.tasks);
    }
}
