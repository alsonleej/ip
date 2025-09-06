package kip.command;

public class Instruction {
    private String command;
    private String task;
    private String[] datetimes;

    public Instruction(String command, String task, String[] datetimes) {
        // Assert that parameters are not null
        assert command != null : "Command must not be null";
        assert task != null : "Task must not be null";
        assert datetimes != null : "Datetimes array must not be null";
        
        this.command = command;
        this.task = task;
        this.datetimes = datetimes;
        
        // Assert that object is in valid state after construction
        assert this.command != null : "Command must not be null after construction";
        assert this.task != null : "Task must not be null after construction";
        assert this.datetimes != null : "Datetimes array must not be null after construction";
    }

    public String getCommand() {
        // Assert that command is not null
        assert command != null : "Command must not be null when getting";
        return command;
    }

    public String getTask() {
        // Assert that task is not null
        assert task != null : "Task must not be null when getting";
        return task;
    }

    public String[] getDatetimes() {
        // Assert that datetimes array is not null
        assert datetimes != null : "Datetimes array must not be null when getting";
        return datetimes;
    }
}
