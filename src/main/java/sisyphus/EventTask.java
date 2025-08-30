package sisyphus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents an event with a start and end date-time.
 * Accepts input in "yyyy-MM-dd HH:mm" or date-only "yyyy-MM-dd" (treated as 00:00) formats.
 */
public class EventTask extends Task {

    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final DateTimeFormatter INPUT_DATE_ONLY = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm");
    private LocalDateTime start;
    private LocalDateTime end;

    /**
     * Creates a new event task with a name, start, and end date-times.
     * Whitespace is trimmed and date-only inputs default to 00:00.
     *
     * @param name        the event description
     * @param startString the start date-time string
     * @param endString   the end date-time string
     * @throws DateTimeParseException if any date string is not in an accepted format
     */
    public EventTask(String name, String startString, String endString) throws DateTimeParseException {
        super(name);
        this.start = parseDateTime(startString.trim());
        this.end = parseDateTime(endString.trim());
    }

    // same as in EventTask
    private LocalDateTime parseDateTime(String dateTimeString) throws DateTimeParseException {
        try {
            return LocalDateTime.parse(dateTimeString, INPUT_FORMAT);
        } catch (DateTimeParseException e) {
            try {
                LocalDate date = LocalDate.parse(dateTimeString, INPUT_DATE_ONLY);
                return date.atStartOfDay();
            } catch (DateTimeParseException e2) {
                throw new DateTimeParseException("Invalid date format. Use yyyy-MM-dd or yyyy-MM-dd HH:mm",
                        dateTimeString, 0);
            }
        }
    }

    /**
     * Returns the start date-time of this event.
     *
     * @return the start as a LocalDateTime
     */
    public LocalDateTime getStart() {
        return this.start;
    }

    /**
     * Returns the end date-time of this event.
     *
     * @return the end as a LocalDateTime
     */
    public LocalDateTime getEnd() {
        return this.end;
    }

    /**
     * Returns the start in the canonical input format yyyy-MM-dd HH:mm.
     *
     * @return formatted start string
     */
    public String getStartString() {
        return this.start.format(INPUT_FORMAT);
    }

    /**
     * Returns the end in the canonical input format yyyy-MM-dd HH:mm.
     *
     * @return formatted end string
     */
    public String getEndString() {
        return this.end.format(INPUT_FORMAT);
    }

    /**
     * Returns a formatted string representation for display, including start and end.
     *
     * @return the display string for this event task
     */
    public String toString() {
        String formattedStart = this.start.format(OUTPUT_FORMAT);
        String formattedEnd = this.end.format(OUTPUT_FORMAT);
        return "[E] " + super.toString() + " (from: " + formattedStart + " to: " + formattedEnd + ")";
    }
}
