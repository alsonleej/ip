public class IncompleteInstructionException extends Exception {
    private final String command;
    private final String missingField;
    
    public IncompleteInstructionException(String command, String missingField) {
        super("Incomplete instruction for command '" + command + "': missing " + missingField);
        this.command = command;
        this.missingField = missingField;
    }
    
    public String getCommand() {
        return command;
    }
    
    public String getMissingField() {
        return missingField;
    }
}
