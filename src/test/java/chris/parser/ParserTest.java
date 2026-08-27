package chris.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import chris.exception.ChrisException;
import chris.task.Deadline;

public class ParserTest {
    @Test
    public void parseDeadline_validDate_returnsDeadline() throws ChrisException {
        Deadline deadline = Parser.parseDeadline("deadline return book /by 2019-12-02");

        assertEquals("[D][ ] return book (by: Dec 2 2019)", deadline.toString());
    }

    @Test
    public void parseDeadline_invalidDate_throwsChrisException() {
        assertThrows(ChrisException.class,
                () -> Parser.parseDeadline("deadline return book /by 2019-02-30"));
    }
}
