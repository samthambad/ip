package sisyphus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a task with a deadline at a specific date-time.
 * Supports inputs in "yyyy-MM-dd HH:mm" or date-only "yyyy-MM-dd" (treated as 00:00) formats.
 */
public class DeadlineTask extends Task {

    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final DateTimeFormatter INPUT_DATE_ONLY = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm");
    private LocalDateTime deadline;

    /**
     * Creates a deadline task with the given name and deadline string.
     *
     * @param name            the task description
     * @param deadlineString  the deadline date-time string
     * @throws DateTimeParseException if the deadline string is not in an accepted format
     */
    public DeadlineTask(String name, String deadlineString) throws DateTimeParseException {
        super(name);
        // Parse the input date string (yyyy-MM-dd format)
        this.deadline = parseDateTime(deadlineString.trim());
        assert this.deadline != null : "Deadline must not be null";
    }

    private LocalDateTime parseDateTime(String dateTimeString) throws DateTimeParseException {
        try {
            // Try parsing with time (yyyy-MM-dd HH:mm)
            return LocalDateTime.parse(dateTimeString, INPUT_FORMAT);
        } catch (DateTimeParseException e) {
            try {
                // No time in input so set time to 00:00
                LocalDate date = LocalDate.parse(dateTimeString, INPUT_DATE_ONLY);
                return date.atStartOfDay();
            } catch (DateTimeParseException e2) {
                throw new DateTimeParseException("Invalid date format. Use yyyy-MM-dd or yyyy-MM-dd HH:mm",
                        dateTimeString, 0);
            }
        }
    }

    /**
     * Returns the parsed deadline.
     *
     * @return deadline as LocalDateTime
     */
    public LocalDateTime getDeadline() {
        assert this.deadline != null : "Deadline should have been initialized";
        return this.deadline;
    }

    /**
     * Returns the deadline in the canonical input format yyyy-MM-dd HH:mm.
     *
     * @return formatted deadline string
     */
    public String getDeadlineString() {
        return this.deadline.format(INPUT_FORMAT);
    }

    /**
     * Returns a formatted display string including the deadline.
     *
     * @return the display string for this deadline task
     */
    public String toString() {
        // Format date for display (MMM dd yyyy format)
        String formattedDate = this.deadline.format(OUTPUT_FORMAT);
        return "[D] " + super.toString() + " (by: " + formattedDate + ")";
    }
}
