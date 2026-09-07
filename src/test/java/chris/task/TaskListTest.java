package chris.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Tests task-list behavior.
 */
public class TaskListTest {
    /**
     * Verifies that find returns matching tasks in their original order.
     */
    @Test
    public void find_matchingKeyword_returnsMatchingTasks() {
        TaskList tasks = new TaskList(List.of(
                new Todo("read book"),
                new Deadline("return book", LocalDate.of(2026, 8, 30)),
                new Todo("exercise")));

        List<Task> matchingTasks = tasks.find("book");

        assertEquals(2, matchingTasks.size());
        assertEquals("[T][ ] read book", matchingTasks.get(0).toString());
        assertEquals("[D][ ] return book (by: Aug 30 2026)", matchingTasks.get(1).toString());
    }

    /**
     * Verifies that find returns an empty list when nothing matches.
     */
    @Test
    public void find_missingKeyword_returnsEmptyList() {
        TaskList tasks = new TaskList(List.of(new Todo("exercise")));

        assertTrue(tasks.find("book").isEmpty());
    }

    /**
     * Verifies that an invalid internal task index violates the task-list precondition.
     */
    @Test
    public void delete_invalidIndex_throwsAssertionError() {
        TaskList tasks = new TaskList();

        AssertionError exception = assertThrows(AssertionError.class, () -> tasks.delete(0));

        assertEquals("Task index should be within the task list: 0", exception.getMessage());
    }
}
