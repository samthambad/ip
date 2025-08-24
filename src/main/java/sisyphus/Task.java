package sisyphus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Task {
    private String name;
    private Boolean isDone = false;

    public Task(String name) {
        this.name = name;
    }

    public boolean isDone() {
        return this.isDone;
    }

    public String getName() {
        return this.name;
    }

    public String toString() {
        if (isDone) {
            return "[X] " + this.name;
        } else {
            return "[ ] " + this.name;
        }
    }

    public void complete() {
        this.isDone = true;
    }

    public void incomplete() {
        this.isDone = false;
    }
}

class TodoTask extends Task {

    public TodoTask(String name) {
        super(name);
    }

    public String toString() {
        return "[T] " + super.toString();
    }
}

class DeadlineTask extends Task {
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

class EventTask extends Task {
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
