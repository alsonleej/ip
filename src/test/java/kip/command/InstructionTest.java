package kip.command;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InstructionTest {
    
    @Test
    public void testInstructionCreation() {
        Instruction instruction = new Instruction("todo", "read book", new String[]{});
        assertEquals("todo", instruction.getCommand());
        assertEquals("read book", instruction.getTask());
        assertEquals(0, instruction.getDatetimes().length);
    }
    
    @Test
    public void testInstructionWithDatetimes() {
        String[] datetimes = {"2025-08-21", "2025-08-22"};
        Instruction instruction = new Instruction("event", "meeting", datetimes);
        assertEquals("event", instruction.getCommand());
        assertEquals("meeting", instruction.getTask());
        assertEquals(2, instruction.getDatetimes().length);
        assertEquals("2025-08-21", instruction.getDatetimes()[0]);
        assertEquals("2025-08-22", instruction.getDatetimes()[1]);
    }
    
    @Test
    public void testEmptyTask() {
        Instruction instruction = new Instruction("list", "", new String[]{});
        assertEquals("list", instruction.getCommand());
        assertEquals("", instruction.getTask());
        assertTrue(instruction.getTask().isEmpty());
    }
}
