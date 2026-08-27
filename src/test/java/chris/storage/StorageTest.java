package chris.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import chris.exception.ChrisException;
import chris.task.Deadline;
import chris.task.Task;
import chris.task.Todo;

/**
 * Tests loading and saving tasks through file storage.
 */
public class StorageTest {
    @TempDir
    private Path temporaryDirectory;

    /**
     * Verifies that loading from a missing file produces an empty task list.
     *
     * @throws ChrisException If loading unexpectedly fails.
     */
    @Test
    public void loadTasks_missingFile_returnsEmptyList() throws ChrisException {
        Storage storage = new Storage(this.temporaryDirectory.resolve("missing.txt"));

        assertTrue(storage.loadTasks().isEmpty());
    }

    /**
     * Verifies that saved tasks and their completion states can be restored.
     *
     * @throws ChrisException If saving or loading unexpectedly fails.
     */
    @Test
    public void saveAndLoadTasks_validTasks_restoresTasks() throws ChrisException {
        Storage storage = new Storage(this.temporaryDirectory.resolve("data").resolve("chris.txt"));
        Todo todo = new Todo("read book");
        todo.markAsDone();
        Deadline deadline = new Deadline("return book", LocalDate.of(2019, 12, 2));

        storage.saveTasks(List.of(todo, deadline));
        ArrayList<Task> loadedTasks = storage.loadTasks();

        assertEquals(2, loadedTasks.size());
        assertEquals("[T][X] read book", loadedTasks.get(0).toString());
        assertEquals("[D][ ] return book (by: Dec 2 2019)", loadedTasks.get(1).toString());
    }
}
