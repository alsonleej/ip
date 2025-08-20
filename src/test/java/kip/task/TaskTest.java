package kip.task;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TaskTest {
    
    @Test
    public void testTaskCreation() {
        Task task = new Task("Test task");
        assertEquals("Test task", task.getDescription());
        assertFalse(task.isDone());
    }
    
    @Test
    public void testMarkAsDone() {
        Task task = new Task("Test task");
        task.markAsDone();
        assertTrue(task.isDone());
    }
    
    @Test
    public void testUnmarkAsDone() {
        Task task = new Task("Test task");
        task.markAsDone();
        task.unmarkAsDone();
        assertFalse(task.isDone());
    }
    
    @Test
    public void testToString() {
        Task task = new Task("Test task");
        assertEquals("[ ] Test task", task.toString());
        
        task.markAsDone();
        assertEquals("[X] Test task", task.toString());
    }
}
