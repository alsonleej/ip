package kip.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import kip.exception.InvalidDateException;
import kip.command.Parser;

public class Deadline extends Task {
    private LocalDateTime by;
    private static final DateTimeFormatter DISPLAY_FORMATTER = DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm");

    public Deadline(String description, String by) throws InvalidDateException {
        super(description);
        this.by = Parser.parseDateTime(by, "by");
    }

    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }

    public LocalDateTime getBy() {
        return by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by.format(DISPLAY_FORMATTER) + ")";
    }
}
