public class Instruction {
    private String command;
    private String task;
    private String[] datetimes;

    public Instruction(String command, String task, String[] datetimes) {
        this.command = command;
        this.task = task;
        this.datetimes = datetimes;
    }

    public String getCommand() {
        return command;
    }

    public String getTask() {
        return task;
    }

    public String[] getDatetimes() {
        return datetimes;
    }
}
