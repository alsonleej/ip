package kip.exception;

public class UnknownCommandException extends Exception {
    private final String command;

    public UnknownCommandException(String command) {
        super(command + " is not a valid command.");
        this.command = command;
    }
}
