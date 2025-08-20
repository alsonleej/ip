import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Deadline extends Task {
    private LocalDate by;
    private static final DateTimeFormatter DISPLAY_FORMATTER = DateTimeFormatter.ofPattern("MMM dd yyyy");
    private static final DateTimeFormatter INPUT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public Deadline(String description, String by) throws InvalidDateException {
        super(description);
        this.by = parseDate(by);
    }

    public Deadline(String description, LocalDate by) {
        super(description);
        this.by = by;
    }

    private LocalDate parseDate(String dateString) throws InvalidDateException {
        try {
            // Remove any "by" prefix if present
            String cleanDate = dateString.replaceFirst("^by\\s*", "").trim();
            return LocalDate.parse(cleanDate, INPUT_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new InvalidDateException("Invalid date format. Please use yyyy-MM-dd format (e.g., 2019-10-15)", e);
        }
    }

    public LocalDate getBy() {
        return by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by.format(DISPLAY_FORMATTER) + ")";
    }
}
