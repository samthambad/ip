package sisyphus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DeadlineTask extends Task {
    private LocalDateTime deadline;

    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final DateTimeFormatter INPUT_DATE_ONLY = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm");

    public DeadlineTask(String name, String deadlineString) throws DateTimeParseException {
        super(name);
        // Parse the input date string (yyyy-MM-dd format)
        this.deadline = parseDateTime(deadlineString.trim());
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
                throw new DateTimeParseException("Invalid date format. Use yyyy-MM-dd or yyyy-MM-dd HH:mm", dateTimeString, 0);
            }
        }
    }

    public LocalDateTime getDeadline() {
        return this.deadline;
    }

    public String getDeadlineString() {
        return this.deadline.format(INPUT_FORMAT);
    }

    public String toString() {
        // Format date for display (MMM dd yyyy format)
        String formattedDate = this.deadline.format(OUTPUT_FORMAT);
        return "[D] " + super.toString() + " (by: " + formattedDate + ")";
    }
}
