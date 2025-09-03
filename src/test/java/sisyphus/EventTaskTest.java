package sisyphus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeParseException;
import java.util.Locale;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EventTaskTest {
    private Locale previousLocale;

    @BeforeEach
    void setUp() {
        previousLocale = Locale.getDefault();
        Locale.setDefault(Locale.ENGLISH);
    }

    @AfterEach
    void tearDown() {
        Locale.setDefault(previousLocale);
    }

    @Test
    void parsesDateTimeCorrectly() {
        EventTask et = new EventTask("project meeting", "2024-10-01 14:00", "2024-10-01 16:00");
        assertEquals("2024-10-01 14:00", et.getStartString());
        assertEquals("2024-10-01 16:00", et.getEndString());
        assertEquals("[E] [ ] project meeting (from: Oct 01 2024 14:00 to: Oct 01 2024 16:00)", et.toString());
    }

    @Test
    void parsesDateOnlyAsStartOfDay() {
        EventTask et = new EventTask("holiday", "2024-12-25", "2024-12-26");
        assertEquals("2024-12-25 00:00", et.getStartString());
        assertEquals("2024-12-26 00:00", et.getEndString());
        LocalDateTime start = et.getStart();
        assertEquals(2024, start.getYear());
        assertEquals(Month.DECEMBER, start.getMonth());
        assertEquals(25, start.getDayOfMonth());
        assertEquals(0, start.getHour());
        assertTrue(et.toString().contains("Dec 25 2024 00:00"));
    }

    @Test
    void trimsInputs() {
        EventTask et = new EventTask("trim test", " 2024-01-02 03:04 ", " 2024-01-02 04:05 ");
        assertEquals("2024-01-02 03:04", et.getStartString());
        assertEquals("2024-01-02 04:05", et.getEndString());
    }

    @Test
    void invalidFormatThrows() {
        assertThrows(DateTimeParseException.class, () -> new EventTask("bad", "25/12/2024", "2024-12-26"));
        assertThrows(DateTimeParseException.class, () -> new EventTask("bad", "2024-12-25", "26-12-2024 10:00"));
    }
}

