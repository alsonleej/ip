import java.util.Scanner;
import java.util.ArrayList;

public class Kip {

    private static void output(String text) {
        String output = "____________________________________________________________\n"
                + text + "\n"
                + "____________________________________________________________\n";
        System.out.println(output);
    }

    private static Instruction parse(String userInput) {
        // userInput = command task /datetime
        String[] parts = userInput.split("/", 2); // [command task, datetimes]
        String instruction = parts[0]; // command task
        String[] instructionParts = instruction.split(" ", 2); // [command, task] - limit to 2 parts
        
        String command = instructionParts[0]; // command

        String task = "";
        if (instructionParts.length > 1) {
            task = instructionParts[1]; // task
        }

        String[] datetimes = new String[0];
        if (parts.length > 1) {
            datetimes = parts[1].split("/"); // [datetime, datetime2, etc]
        }
        return new Instruction(command, task, datetimes);
    }

    

    public static void main(String[] args) {
        output("Hello! I'm Kip\nWhat can I do for you?\n");
        
        Scanner scanner = new Scanner(System.in);
        String userInput;
        Instruction instruction;
        int taskIndex;

        ArrayList<Task> tasks = new ArrayList<>();
        
        while (true) {
            try {
                userInput = scanner.nextLine().trim();

                instruction = parse(userInput);

                Command cmd = Command.fromString(instruction.getCommand());
                if (cmd == null) {
                    throw new UnknownCommandException(instruction.getCommand());
                }
                
                switch (cmd) {
                    case BYE: // eg: bye
                        output("Bye. Hope to see you again soon!");
                        scanner.close();
                        return;
                        
                    case LIST: // eg: list
                        String out = "Here are the tasks in your list:\n";
                        for (int i = 0; i < tasks.size(); i++) {
                            out += (i + 1) + ". " + tasks.get(i) + "\n";
                        }
                        out += "Now you have " + tasks.size() + " tasks in the list.";
                        output(out);
                        break;
                        
                    case MARK: // eg: mark 1
                        taskIndex = Integer.parseInt(instruction.getTask()) - 1;
                        if (taskIndex >= 0 && taskIndex < tasks.size()) {
                            tasks.get(taskIndex).markAsDone();
                            output("Nice! I've marked this task as done:\n" + tasks.get(taskIndex));
                        } else {
                            throw new NumberFormatException("Invalid task number!");
                        }
                        break;

                        
                    case UNMARK: // eg: unmark 1
                        taskIndex = Integer.parseInt(instruction.getTask()) - 1;
                        if (taskIndex >= 0 && taskIndex < tasks.size()) {
                            tasks.get(taskIndex).unmarkAsDone();
                            output("OK, I've marked this task as not done yet:\n" + tasks.get(taskIndex));
                        } else {
                            throw new NumberFormatException("Invalid task number!");
                        }
                        break;
     
                    //TASK ADDING   
                    case TODO: // eg: todo read book
                        if (instruction.getTask().isEmpty()) {
                            throw new IncompleteInstructionException("todo", "task description");
                        }

                        tasks.add(new ToDo(instruction.getTask()));
                        output("Got it. I've added this task:\n" + tasks.get(tasks.size() - 1) + "\nNow you have " + tasks.size() + " tasks in the list.");
                        break;      

                    case DEADLINE: // eg: deadline read book /by 2025-08-19
                        if (instruction.getTask().isEmpty()) {
                            throw new IncompleteInstructionException("deadline", "task description");
                        }

                        if (instruction.getDatetimes().length == 0) {
                            throw new IncompleteInstructionException("deadline", "date and time");
                        }

                        tasks.add(new Deadline(instruction.getTask(), instruction.getDatetimes()[0]));
                        output("Got it. I've added this task:\n" + tasks.get(tasks.size() - 1) + "\nNow you have " + tasks.size() + " tasks in the list.");
                        break;

                    case EVENT: // eg: event read book /from 2025-08-19 /to 2025-08-20
                        if (instruction.getTask().isEmpty()) {
                            throw new IncompleteInstructionException("event", "task description");
                        }

                        if (instruction.getDatetimes().length < 2) {
                            throw new IncompleteInstructionException("event", "date and time");
                        }

                        tasks.add(new Event(instruction.getTask(), instruction.getDatetimes()[0], instruction.getDatetimes()[1]));
                        output("Got it. I've added this task:\n" + tasks.get(tasks.size() - 1) + "\nNow you have " + tasks.size() + " tasks in the list.");
                        break;
                }
            } catch (IncompleteInstructionException e) {
                output("ERROR!!! " + e.getMessage());
            } catch (UnknownCommandException e) {
                output("ERROR!!! " + e.getMessage());
            } catch (NumberFormatException e) {
                output("ERROR!!! Please provide a valid task number.");
            } catch (Exception e) {
                output("ERROR!!! An unexpected error occurred: " + e.getMessage());
            }
        }
    }
}
