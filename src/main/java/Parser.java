import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class Parser {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    /**
     * Validates that a string doesn't contain commas to prevent CSV parsing issues
     * @param input The string to validate
     * @param fieldName The name of the field for error messages
     * @throws InvalidDateException if the input contains commas
     */
    private static void validateNoCommas(String input, String fieldName) throws InvalidDateException {
        if (input.contains(",")) {
            throw new InvalidDateException("Invalid " + fieldName + ": Cannot contain commas (,) as they break the CSV format. Please use a different character.");
        }
    }
    
    /**
     * Parses a date string in xx/ yyyy-MM-dd format to LocalDate where xx is by, from, to
     * Basically grabs the relevant datetime field from the user input
     * @param dateString The date string to parse
     * @param fieldName The name of the field for error messages
     * @return LocalDate object
     * @throws InvalidDateException if the date format is invalid
     */
    public static LocalDate parseDate(String dateString, String fieldName) throws InvalidDateException {
        try {
            // Validate no commas
            validateNoCommas(dateString, fieldName);
            
            // Remove any prefix if present (e.g., "by", "from", "to")
            String cleanDate = dateString.replaceFirst("^" + fieldName + "\\s*", "").trim();
            return LocalDate.parse(cleanDate, DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new InvalidDateException("Invalid " + fieldName + " date format. Please use yyyy-MM-dd format (e.g., 2019-10-15)", e);
        }
    }
    
    /**
     * Parses a single CSV line into a Task object
     * @param line CSV line to parse
     * @return Task object or null if parsing fails
     * @throws Exception if there's an error during parsing
     */
    public static Task parseTaskLine(String line) throws Exception {
        String[] parts = line.split(",");
        if (parts.length < 3) return null; // Skip invalid lines
        
        String type = parts[0].trim();
        boolean done = parts[1].trim().equals("1");
        String description = parts[2].trim();
        
        Task task = null;
        
        switch (type) {
            case "T": // TODO
                task = new ToDo(description);
                break;
            case "D": // DEADLINE
                if (parts.length >= 4 && !parts[3].trim().isEmpty()) {
                    LocalDate date = LocalDate.parse(parts[3].trim(), DATE_FORMATTER);
                    task = new Deadline(description, date);
                }
                break;
            case "E": // EVENT
                if (parts.length >= 5 && !parts[3].trim().isEmpty() && !parts[4].trim().isEmpty()) {
                    LocalDate startDate = LocalDate.parse(parts[3].trim(), DATE_FORMATTER);
                    LocalDate endDate = LocalDate.parse(parts[4].trim(), DATE_FORMATTER);
                    task = new Event(description, startDate, endDate);
                }
                break;
        }
        
        if (task != null && done) {
            task.markAsDone();
        }
        
        return task;
    }
    
    /**
     * Parses user input into an Instruction object
     * @param userInput The raw user input string
     * @return Instruction object containing command, task, and datetimes
     */
    public static Instruction parseUserInput(String userInput) throws InvalidDateException {
        // Validate no commas
        validateNoCommas(userInput, "user input");

        // userInput = command task /datetime
        String[] parts = userInput.split("/", 2); // [command task, datetimes]
        String instruction = parts[0]; // command task
        String[] instructionParts = instruction.split(" ", 2); // [command, task] - limit to 2 parts
        
        String command = instructionParts[0]; // command

        String task = "";
        if (instructionParts.length > 1) {
            task = instructionParts[1]; // task
        }

        String[] datetimes = new String[0];
        if (parts.length > 1) {
            datetimes = parts[1].split("/"); // [datetime, datetime2, etc]
        }
        return new Instruction(command, task, datetimes);
    }
    
    /**
     * Gets the date formatter for consistent date formatting across the application
     * @return DateTimeFormatter for yyyy-MM-dd format
     */
    public static DateTimeFormatter getDateFormatter() {
        return DATE_FORMATTER;
    }
}
