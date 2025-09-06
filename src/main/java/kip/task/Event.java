package kip.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import kip.exception.InvalidDateException;
import kip.command.Parser;

public class Event extends Task {
    private LocalDateTime from;
    private LocalDateTime to;
    private static final DateTimeFormatter DISPLAY_FORMATTER = 
        DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm");

    public Event(String description, String from, String to) throws InvalidDateException {
        super(description);
        this.from = Parser.parseDateTime(from, "from");
        this.to = Parser.parseDateTime(to, "to");
        // Assert that both dates are not null after parsing
        assert this.from != null : "Event 'from' date must not be null after parsing";
        assert this.to != null : "Event 'to' date must not be null after parsing";
    }

    public Event(String description, LocalDateTime from, LocalDateTime to) {
        super(description);
        // Assert that both dates are not null
        assert from != null : "Event 'from' date must not be null";
        assert to != null : "Event 'to' date must not be null";
        this.from = from;
        this.to = to;
        // Assert that dates were set correctly
        assert this.from != null : "Event 'from' date must not be null after setting";
        assert this.to != null : "Event 'to' date must not be null after setting";
    }

    public LocalDateTime getFrom() {
        // Assert that 'from' date is not null
        assert from != null : "Event 'from' date must not be null when getting";
        return from;
    }

    public LocalDateTime getTo() {
        // Assert that 'to' date is not null
        assert to != null : "Event 'to' date must not be null when getting";
        return to;
    }

    @Override
    public String toString() {
        // Assert that both dates are not null before formatting
        assert from != null : "Event 'from' date must not be null for toString()";
        assert to != null : "Event 'to' date must not be null for toString()";
        String result = "[E]" + super.toString() + " (from: " + from.format(DISPLAY_FORMATTER) 
                + " to: " + to.format(DISPLAY_FORMATTER) + ")";
        // Assert that result is not null
        assert result != null : "Event toString() result must not be null";
        return result;
    }
}
