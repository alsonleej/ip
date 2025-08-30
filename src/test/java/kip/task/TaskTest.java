package kip.task;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TaskTest {
    
    @Test
    public void testTaskCreation() {
        Task task = new ToDo("Test task");
        assertEquals("Test task", task.getDescription());
        assertFalse(task.isDone());
    }
    
    @Test
    public void testMarkAsDone() {
        Task task = new ToDo("Test task");
        task.markAsDone();
        assertTrue(task.isDone());
    }
    
    @Test
    public void testUnmarkAsDone() {
        Task task = new ToDo("Test task");
        task.markAsDone();
        task.unmarkAsDone();
        assertFalse(task.isDone());
    }
    
    @Test
    public void testToString() {
        Task task = new ToDo("Test task");
        assertEquals("[T][ ] Test task", task.toString());
        
        task.markAsDone();
        assertEquals("[T][X] Test task", task.toString());
    }
    
    @Test
    public void testTaskDescriptionValidation() {
        // Test that null description throws exception
        assertThrows(IllegalArgumentException.class, () -> {
            new ToDo(null);
        });
        
        // Test that empty description throws exception
        assertThrows(IllegalArgumentException.class, () -> {
            new ToDo("");
        });
        
        // Test that whitespace-only description throws exception
        assertThrows(IllegalArgumentException.class, () -> {
            new ToDo("   ");
        });
    }
}
