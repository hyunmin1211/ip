package chris.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Tests task-list search behavior.
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
}
