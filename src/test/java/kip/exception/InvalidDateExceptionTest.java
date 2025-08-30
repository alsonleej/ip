package kip.exception;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class InvalidDateExceptionTest {
    
    @Test
    public void testInvalidDateExceptionMessage() {
        String message = "Invalid date format";
        InvalidDateException exception = new InvalidDateException(message);
        assertEquals(message, exception.getMessage());
    }
    
    @Test
    public void testInvalidDateExceptionWithCause() {
        String message = "Invalid date format";
        Exception cause = new RuntimeException("Parse error");
        InvalidDateException exception = new InvalidDateException(message, cause);
        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }
}
