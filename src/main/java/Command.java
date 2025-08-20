public enum Command {
    BYE("bye"),
    LIST("list"),
    MARK("mark"),
    UNMARK("unmark"),
    TODO("todo"),
    DEADLINE("deadline"),
    EVENT("event"),
    DELETE("delete");

    private final String commandString;

    Command(String commandString) {
        this.commandString = commandString;
    }

    public String getCommandString() {
        return commandString;
    }

    public static Command fromString(String text) {
        for (Command command : Command.values()) {
            if (command.commandString.equalsIgnoreCase(text)) {
                return command;
            }
        }
        return null;
    }
}
