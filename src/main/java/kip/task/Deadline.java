package kip.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import kip.exception.InvalidDateException;
import kip.command.Parser;

public class Deadline extends Task {
    private LocalDateTime by;
    private static final DateTimeFormatter DISPLAY_FORMATTER = 
            DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm");

    public Deadline(String description, String by) throws InvalidDateException {
        super(description);
        this.by = Parser.parseDateTime(by, "by");
        // Assert that 'by' date is not null after parsing
        assert this.by != null : "Deadline 'by' date must not be null after parsing";
    }

    public Deadline(String description, LocalDateTime by) {
        super(description);
        // Assert that 'by' date is not null
        assert by != null : "Deadline 'by' date must not be null";
        this.by = by;
        // Assert that 'by' date was set correctly
        assert this.by != null : "Deadline 'by' date must not be null after setting";
    }

    public LocalDateTime getBy() {
        // Assert that 'by' date is not null
        assert by != null : "Deadline 'by' date must not be null when getting";
        return by;
    }

    @Override
    public String toString() {
        // Assert that 'by' date is not null before formatting
        assert by != null : "Deadline 'by' date must not be null for toString()";
        String result = "[D]" + super.toString() + " (by: " + by.format(DISPLAY_FORMATTER) + ")";
        // Assert that result is not null
        assert result != null : "Deadline toString() result must not be null";
        return result;
    }
}
