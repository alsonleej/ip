package kip.task;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ToDoTest {
    
    @Test
    public void testToDoCreation() {
        ToDo todo = new ToDo("Read book");
        assertEquals("Read book", todo.getDescription());
        assertFalse(todo.isDone());
    }
    
    @Test
    public void testToDoToString() {
        ToDo todo = new ToDo("Read book");
        assertEquals("[T][ ] Read book", todo.toString());
        
        todo.markAsDone();
        assertEquals("[T][X] Read book", todo.toString());
    }
}
