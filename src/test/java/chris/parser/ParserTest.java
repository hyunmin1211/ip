package chris.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import chris.exception.ChrisException;
import chris.task.Deadline;

/**
 * Tests command parsing and validation behavior.
 */
public class ParserTest {
    /**
     * Verifies that a valid deadline command produces the expected task.
     *
     * @throws ChrisException If the valid command is unexpectedly rejected.
     */
    @Test
    public void parseDeadline_validDate_returnsDeadline() throws ChrisException {
        Deadline deadline = Parser.parseDeadline("deadline return book /by 2019-12-02");

        assertEquals("[D][ ] return book (by: Dec 2 2019)", deadline.toString());
    }

    /**
     * Verifies that an impossible calendar date is rejected.
     */
    @Test
    public void parseDeadline_invalidDate_throwsChrisException() {
        assertThrows(ChrisException.class,
                () -> Parser.parseDeadline("deadline return book /by 2019-02-30"));
    }
}
