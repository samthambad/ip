package sisyphus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class EventTask extends Task {
    private LocalDateTime start;
    private LocalDateTime end;

    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final DateTimeFormatter INPUT_DATE_ONLY = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm");

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
                throw new DateTimeParseException("Invalid date format. Use yyyy-MM-dd or yyyy-MM-dd HH:mm", dateTimeString, 0);
            }
        }
    }

    public LocalDateTime getStart() {
        return this.start;
    }

    public LocalDateTime getEnd() {
        return this.end;
    }

    public String getStartString() {
        return this.start.format(INPUT_FORMAT);
    }

    public String getEndString() {
        return this.end.format(INPUT_FORMAT);
    }

    public String toString() {
        String formattedStart = this.start.format(OUTPUT_FORMAT);
        String formattedEnd = this.end.format(OUTPUT_FORMAT);
        return "[E] " + super.toString() + " (from: " + formattedStart + " to: " + formattedEnd + ")";
    }
}
