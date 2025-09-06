package kip.command;

/**
 * Enumeration representing all available commands in the Kip task management system.
 * 
 * <p>This enum defines the set of commands that users can issue to interact with
 * the application. Each command has an associated string representation that is
 * used for parsing user input.</p>
 * 
 * <p>The supported commands include:</p>
 * <ul>
 *   <li><strong>BYE</strong> - Exits the application</li>
 *   <li><strong>LIST</strong> - Displays all tasks</li>
 *   <li><strong>MARK</strong> - Marks a task as completed</li>
 *   <li><strong>UNMARK</strong> - Marks a task as incomplete</li>
 *   <li><strong>TODO</strong> - Adds a new ToDo task</li>
 *   <li><strong>DEADLINE</strong> - Adds a new Deadline task</li>
 *   <li><strong>EVENT</strong> - Adds a new Event task</li>
 *   <li><strong>DELETE</strong> - Removes a task</li>
 *   <li><strong>FIND</strong> - Finds a task</li>
 *   <li><strong>HELP</strong> - Displays all commands</li>
 * </ul>
 * 
 * <p>Commands are case-insensitive when parsing user input, providing a
 * user-friendly experience.</p>
 * 
 * @author alsonleej
 * @version 1.0
 * @since 2025
 * @see Instruction
 * @see Parser
 */
public enum Command {
    /** Command to exit the application */
    BYE("bye"),
    /** Command to list all tasks */
    LIST("list"),
    /** Command to mark a task as done */
    MARK("mark"),
    /** Command to mark a task as undone */
    UNMARK("unmark"),
    /** Command to add a ToDo task */
    TODO("todo"),
    /** Command to add a Deadline task */
    DEADLINE("deadline"),
    /** Command to add an Event task */
    EVENT("event"),
    DELETE("delete"),
    FIND("find"),
    HELP("help");

    /** The string representation of the command */
    private final String commandString;

    /**
     * Constructs a Command enum value with the specified string representation.
     * 
     * @param commandString The string that represents this command
     */
    Command(String commandString) {
        this.commandString = commandString;
    }

    /**
     * Returns the string representation of this command.
     * 
     * @return The command string
     */
    public String getCommandString() {
        return commandString;
    }

    /**
     * Converts a string to its corresponding Command enum value.
     * 
     * <p>This method performs case-insensitive matching, so "BYE", "bye", and "Bye"
     * will all return the BYE command.</p>
     * 
     * <p>If no matching command is found, null is returned.</p>
     * 
     * @param text The string to convert to a Command
     * @return The corresponding Command enum value, or null if no match is found
     */
    public static Command fromString(String text) {
        if (text == null) {
            return null;
        }
        
        // Assert that text is not empty when not null
        assert !text.trim().isEmpty() : "Command text must not be empty when not null";
        
        for (Command command : Command.values()) {
            if (command.commandString.equalsIgnoreCase(text)) {
                // Assert that we found a valid command
                assert command != null : "Found command must not be null";
                return command;
            }
        }
        return null;
    }
}
