package kip.command;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class CommandTest {
    
    @Test
    public void testCommandFromString() {
        assertEquals(Command.BYE, Command.fromString("bye"));
        assertEquals(Command.LIST, Command.fromString("list"));
        assertEquals(Command.MARK, Command.fromString("mark"));
        assertEquals(Command.UNMARK, Command.fromString("unmark"));
        assertEquals(Command.TODO, Command.fromString("todo"));
        assertEquals(Command.DEADLINE, Command.fromString("deadline"));
        assertEquals(Command.EVENT, Command.fromString("event"));
        assertEquals(Command.DELETE, Command.fromString("delete"));
    }
    
    @Test
    public void testCommandFromStringCaseInsensitive() {
        assertEquals(Command.BYE, Command.fromString("BYE"));
        assertEquals(Command.TODO, Command.fromString("Todo"));
    }
    
    @Test
    public void testCommandFromStringInvalid() {
        assertNull(Command.fromString("invalid"));
        assertNull(Command.fromString(""));
    }
    
    @Test
    public void testGetCommandString() {
        assertEquals("bye", Command.BYE.getCommandString());
        assertEquals("todo", Command.TODO.getCommandString());
        assertEquals("deadline", Command.DEADLINE.getCommandString());
    }
}
