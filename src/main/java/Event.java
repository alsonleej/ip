import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Event extends Task {
    private LocalDate from;
    private LocalDate to;
    private static final DateTimeFormatter DISPLAY_FORMATTER = DateTimeFormatter.ofPattern("MMM dd yyyy");
    private static final DateTimeFormatter INPUT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public Event(String description, String from, String to) throws InvalidDateException {
        super(description);
        this.from = parseDate(from, "from");
        this.to = parseDate(to, "to");
    }

    public Event(String description, LocalDate from, LocalDate to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    private LocalDate parseDate(String dateString, String fieldName) throws InvalidDateException {
        try {
            // Remove any prefix if present
            String cleanDate = dateString.replaceFirst("^" + fieldName + "\\s*", "").trim();
            return LocalDate.parse(cleanDate, INPUT_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new InvalidDateException("Invalid " + fieldName + " date format. Please use yyyy-MM-dd format (e.g., 2019-10-15)", e);
        }
    }

    public LocalDate getFrom() {
        return from;
    }

    public LocalDate getTo() {
        return to;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from.format(DISPLAY_FORMATTER) + " to: " + to.format(DISPLAY_FORMATTER) + ")";
    }
}
