import java.util.Scanner;

public class Kip {
    private static final int MAX_INPUTS = 100;

    private static void output(String text) {
        String output = "____________________________________________________________\n"
                + text + "\n"
                + "____________________________________________________________\n";
        System.out.println(output);
    }

    private static String[] parse(String userInput) {
        // userInput = command task /datetime
        String[] parts = userInput.split("/"); // [command task, datetime, datetime2]
        String instruction = parts[0]; // command task
        String[] instructionParts = instruction.split(" ", 2); // [command, task] - limit to 2 parts
        
        if (instructionParts.length < 2) {
            return new String[] {instructionParts[0], "", "", ""};
        }
        
        String command = instructionParts[0]; // command
        String task = instructionParts[1]; // task
        
        // Handle datetime parts if they exist
        String date = "";
        String time = "";
        if (parts.length > 1) {
            date = parts[1];
        }
        if (parts.length > 2) {
            time = parts[2];
        }
   
        return new String[] {command, task, date, time};
    }

    public static void main(String[] args) {
        output("Hello! I'm Kip\nWhat can I do for you?\n");
        
        Scanner scanner = new Scanner(System.in);
        String userInput;
        String[] command;

        Task[] tasks = new Task[MAX_INPUTS];
        int inputCount = 0;
        
        while (true) {
            userInput = scanner.nextLine().trim();
            command = parse(userInput);
            
            Command cmd = Command.fromString(command[0]);
            if (cmd == null) {
                output("Unknown command: " + command[0]);
                continue;
            }
            
            switch (cmd) {
                case BYE:
                    output("Bye. Hope to see you again soon!");
                    scanner.close();
                    return;
                    
                case LIST:
                    String out = "Here are the tasks in your list:\n";
                    for (int i = 0; i < inputCount; i++) {
                        out += (i + 1) + ". " + tasks[i] + "\n";
                    }
                    output(out);
                    break;
                    
                case MARK:
                    try {
                        String[] parts = userInput.split(" ");
                        int taskIndex = Integer.parseInt(parts[1]) - 1;
                        if (taskIndex >= 0 && taskIndex < inputCount) {
                            tasks[taskIndex].markAsDone();
                            output("Nice! I've marked this task as done:\n" + tasks[taskIndex]);
                        } else {
                            output("Invalid task number!");
                        }
                    } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                        output("Please provide a valid task number!");
                    }
                    break;
                    
                case UNMARK:
                    try {
                        String[] parts = userInput.split(" ");
                        int taskIndex = Integer.parseInt(parts[1]) - 1;
                        if (taskIndex >= 0 && taskIndex < inputCount) {
                            tasks[taskIndex].unmarkAsDone();
                            output("OK, I've marked this task as not done yet:\n" + tasks[taskIndex]);
                        } else {
                            output("Invalid task number!");
                        }
                    } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                        output("Please provide a valid task number!");
                    }
                    break;
                    
                case TODO:
                case DEADLINE:
                case EVENT:
                    if (inputCount >= MAX_INPUTS) {
                        output("Array is full! Cannot store more inputs.");
                        break;
                    }
                    
                    TaskType taskType = TaskType.fromString(command[0]);
                    if (taskType == null) {
                        output("Unknown task type: " + command[0]);
                        break;
                    }
                    
                    try {
                        switch (taskType) {
                            case TODO:
                                if (command[1].trim().isEmpty()) {
                                    output("Todo description cannot be empty!");
                                    break;
                                }
                                tasks[inputCount] = new ToDo(command[1]);
                                break;
                                
                            case DEADLINE:
                                if (command[1].trim().isEmpty() || command[2].trim().isEmpty()) {
                                    output("Deadline description and due date cannot be empty!");
                                    break;
                                }
                                tasks[inputCount] = new Deadline(command[1], command[2]);
                                break;
                                
                            case EVENT:
                                if (command[1].trim().isEmpty() || command[2].trim().isEmpty() || command[3].trim().isEmpty()) {
                                    output("Event description, start time, and end time cannot be empty!");
                                    break;
                                }
                                tasks[inputCount] = new Event(command[1], command[2], command[3]);
                                break;
                        }
                        
                        if (tasks[inputCount] != null) {
                            inputCount++;
                            output("Added: " + tasks[inputCount - 1]);
                        }
                    } catch (Exception e) {
                        output("Error creating task: " + e.getMessage());
                    }
                    break;
                    
                default:
                    output("Unknown command: " + command[0]);
                    break;
            }
        }
    }
}
