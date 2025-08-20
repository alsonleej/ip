package kip.command;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import kip.exception.InvalidDateException;
import kip.task.Task;
import kip.task.ToDo;
import kip.task.Deadline;
import kip.task.Event;

/**
 * Utility class for parsing user input and CSV data in the Kip task management system.
 * 
 * <p>The Parser class provides comprehensive parsing functionality for:</p>
 * <ul>
 *   <li>User command input parsing</li>
 *   <li>Date and time string parsing</li>
 *   <li>CSV line parsing for task reconstruction</li>
 *   <li>Input validation and error handling</li>
 * </ul>
 * 
 * <p>This class handles multiple date formats and ensures data integrity
 * by validating input and preventing CSV format corruption.</p>
 * 
 * <p>Supported date formats:</p>
 * <ul>
 *   <li>Date only: yyyy-MM-dd (e.g., 2019-10-15)</li>
 *   <li>Date and time: yyyy-MM-dd HHmm (e.g., 2019-10-15 1800)</li>
 * </ul>
 * 
 * @author alsonleej
 * @version 1.0
 * @since 2025
 * @see Command
 * @see Instruction
 * @see InvalidDateException
 */
public class Parser {
    /** Formatter for date-only strings (yyyy-MM-dd) */
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    /** Formatter for date-time strings (yyyy-MM-dd HHmm) */
    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    
    /**
     * Validates that a string doesn't contain commas to prevent CSV parsing issues.
     * 
     * <p>Since the application uses CSV format for data storage, commas in input
     * strings would break the parsing logic. This method ensures data integrity.</p>
     * 
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
     * Parses a date string in yyyy-MM-dd or yyyy-MM-dd HHmm format to LocalDateTime.
     * 
     * <p>This method attempts to parse the input string using multiple formats.
     * If the datetime format fails, it falls back to the date-only format and
     * sets the time to 00:00 (midnight).</p>
     * 
     * <p>The method also validates that the input doesn't contain commas to
     * prevent CSV parsing issues.</p>
     * 
     * @param dateString The date string to parse
     * @param fieldName The name of the field for error messages
     * @return LocalDateTime object (time defaults to 00:00 if only date provided)
     * @throws InvalidDateException if the date format is invalid or contains commas
     */
    public static LocalDateTime parseDateTime(String dateString, String fieldName) throws InvalidDateException {
        try {
            // Validate no commas
            validateNoCommas(dateString, fieldName);
            
            // Remove any prefix if present (e.g., "by", "from", "to")
            String cleanDate = dateString.replaceFirst("^" + fieldName + "\\s*", "").trim();
            
            // Try to parse as datetime first (yyyy-MM-dd HHmm)
            try {
                return LocalDateTime.parse(cleanDate, DATETIME_FORMATTER);
            } catch (DateTimeParseException e) {
                // If datetime parsing fails, try date only (yyyy-MM-dd)
                LocalDate date = LocalDate.parse(cleanDate, DATE_FORMATTER);
                return date.atStartOfDay(); // Convert to LocalDateTime at 00:00
            }
        } catch (DateTimeParseException e) {
            throw new InvalidDateException("Invalid " + fieldName + " format. Please use yyyy-MM-dd (e.g., 2019-10-15) or yyyy-MM-dd HHmm (e.g., 2019-10-15 1800)", e);
        }
    }
    
    /**
     * Parses a single CSV line into a Task object.
     * 
     * <p>This method reconstructs Task objects from CSV data stored in the
     * storage file. It handles all three task types: ToDo, Deadline, and Event.</p>
     * 
     * <p>The expected CSV format is: type,done,description,datetime1,datetime2</p>
     * <ul>
     *   <li>type: T (ToDo), D (Deadline), or E (Event)</li>
     *   <li>done: 0 (false) or 1 (true)</li>
     *   <li>description: Task description</li>
     *   <li>datetime1: First datetime (deadline date or event start)</li>
     *   <li>datetime2: Second datetime (event end, unused for ToDo/Deadline)</li>
     * </ul>
     * 
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
            case "T": 
                task = new ToDo(description);
                break;
            case "D": // DEADLINE
                if (parts.length >= 4 && !parts[3].trim().isEmpty()) {
                    LocalDateTime dateTime = parseDateTime(parts[3].trim(), "deadline");
                    task = new Deadline(description, dateTime);
                }
                break;
            case "E": // EVENT
                if (parts.length >= 5 && !parts[3].trim().isEmpty() && !parts[4].trim().isEmpty()) {
                    LocalDateTime startDateTime = parseDateTime(parts[3].trim(), "start");
                    LocalDateTime endDateTime = parseDateTime(parts[4].trim(), "end");
                    task = new Event(description, startDateTime, endDateTime);
                }
                break;
        }
        
        if (task != null && done) {
            task.markAsDone();
        }
        
        return task;
    }
    
    /**
     * Parses user input into an Instruction object.
     * 
     * <p>This method breaks down user input into its component parts: command,
     * task description, and datetime parameters. It handles the slash-based
     * syntax used by the application.</p>
     * 
     * <p>Input format examples:</p>
     * <ul>
     *   <li><code>todo read book</code> → command: "todo", task: "read book"</li>
     *   <li><code>deadline return book /by 2019-10-15</code> → command: "deadline", task: "return book", datetime: "by 2019-10-15"</li>
     *   <li><code>event meeting /from 2019-10-15 /to 2019-10-16</code> → command: "event", task: "meeting", datetimes: ["from 2019-10-15", "to 2019-10-16"]</li>
     * </ul>
     * 
     * @param userInput The raw user input string
     * @return Instruction object containing command, task, and datetimes
     * @throws InvalidDateException if the input contains commas
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
     * Gets the date formatter for consistent date formatting across the application.
     * 
     * @return DateTimeFormatter for yyyy-MM-dd format
     */
    public static DateTimeFormatter getDateFormatter() {
        return DATE_FORMATTER;
    }
    
    /**
     * Gets the datetime formatter for consistent datetime formatting across the application.
     * 
     * @return DateTimeFormatter for yyyy-MM-dd HHmm format
     */
    public static DateTimeFormatter getDateTimeFormatter() {
        return DATETIME_FORMATTER;
    }
}
