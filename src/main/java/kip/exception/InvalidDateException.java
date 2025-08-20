package kip.exception;

public class InvalidDateException extends Exception {
    
    /**
     * Constructs a new InvalidDateException with the specified detail message.
     * 
     * @param message The detail message explaining the date parsing error
     */
    public InvalidDateException(String message) {
        super(message);
    }
    
    /**
     * Constructs a new InvalidDateException with the specified detail message and cause.
     * 
     * @param message The detail message explaining the date parsing error
     * @param cause The cause of the exception (typically a DateTimeParseException)
     */
    public InvalidDateException(String message, Throwable cause) {
        super(message, cause);
    }
}
