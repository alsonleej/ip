package kip;

import java.util.Scanner;
import java.util.ArrayList;
import kip.task.Task;
import kip.task.ToDo;
import kip.task.Deadline;
import kip.task.Event;
import kip.command.Command;
import kip.command.Instruction;
import kip.command.Parser;
import kip.exception.UnknownCommandException;
import kip.exception.IncompleteInstructionException;
import kip.exception.InvalidDateException;
import kip.storage.Storage;

/**
 * Kip is a command-line task management application that allows users to manage
 * different types of tasks including ToDo, Deadline, and Event tasks.
 * 
 * <p>The application supports the following operations:</p>
 * <ul>
 *   <li>Adding tasks (todo, deadline, event)</li>
 *   <li>Listing all tasks</li>
 *   <li>Marking tasks as done/undone</li>
 *   <li>Deleting tasks</li>
 *   <li>Persistent storage using CSV format</li>
 * </ul>
 * 
 * <p>Tasks are automatically saved to a CSV file after each modification
 * to ensure data persistence across application sessions.</p>
 * 
 * @author alsonleej
 * @version 1.0
 * @since 2025
 */
public class Kip {

    /**
     * Displays formatted output with decorative borders for better user experience.
     * 
     * @param text The text to be displayed
     */
    private static void output(String text) {
        String output = "____________________________________________________________\n"
                + text + "\n"
                + "____________________________________________________________\n";
        System.out.println(output);
    }

    /**
     * Main entry point for the Kip application.
     * 
     * <p>This method initializes the application, loads existing tasks from storage,
     * and enters the main command loop where it continuously processes user input
     * until the user issues a 'bye' command.</p>
     * 
     * <p>The application supports the following command formats:</p>
     * <ul>
     *   <li><code>bye</code> - Exits the application</li>
     *   <li><code>list</code> - Shows all tasks</li>
     *   <li><code>mark &lt;task_number&gt;</code> - Marks a task as done</li>
     *   <li><code>unmark &lt;task_number&gt;</code> - Marks a task as undone</li>
     *   <li><code>delete &lt;task_number&gt;</code> - Removes a task</li>
     *   <li><code>todo &lt;description&gt;</code> - Adds a ToDo task</li>
     *   <li><code>deadline &lt;description&gt; /by &lt;date&gt;</code> - Adds a Deadline task</li>
     *   <li><code>event &lt;description&gt; /from &lt;date&gt; /to &lt;date&gt;</code> - Adds an Event task</li>
     * </ul>
     * 
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        output("Hello! I'm Kip\nWhat can I do for you?\n\n"
                + "Note: Task descriptions and dates cannot contain commas (,) "
                + "as they break the CSV format.\n"
                + "Supported date formats: yyyy-MM-dd (e.g., 2019-10-15) "
                + "or yyyy-MM-dd HHmm (e.g., 2019-10-15 1800)");
        
        Scanner scanner = new Scanner(System.in);
        String userInput;
        Instruction instruction;
        int taskIndex;

        ArrayList<Task> tasks = Storage.loadTasks();
        
        while (true) {
            try {
                userInput = scanner.nextLine().trim();

                instruction = Parser.parseUserInput(userInput);

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

                // After every modification to the task list, save all tasks to the file. 
                // Could be optimized by saving only the modified task to the file.
                    
                case MARK: // eg: mark 1
                    taskIndex = Integer.parseInt(instruction.getTask()) - 1;
                    if (taskIndex >= 0 && taskIndex < tasks.size()) {
                        tasks.get(taskIndex).markAsDone();
                        output("Nice! I've marked this task as done:\n" 
                                + tasks.get(taskIndex));
                        Storage.saveTasks(tasks);
                    } else {
                        throw new NumberFormatException("Invalid task number!");
                    }
                    break;

                    
                case UNMARK: // eg: unmark 1
                    taskIndex = Integer.parseInt(instruction.getTask()) - 1;
                    if (taskIndex >= 0 && taskIndex < tasks.size()) {
                        tasks.get(taskIndex).unmarkAsDone();
                        output("OK, I've marked this task as not done yet:\n" 
                                + tasks.get(taskIndex));
                        Storage.saveTasks(tasks);
                    } else {
                        throw new NumberFormatException("Invalid task number!");
                    }
                    break;

                    
                case DELETE: // eg: delete 1
                    taskIndex = Integer.parseInt(instruction.getTask()) - 1;
                    if (taskIndex >= 0 && taskIndex < tasks.size()) {
                        Task removedTask = tasks.remove(taskIndex);
                        output("Noted. I've removed this task:\n" + removedTask 
                                + "\nNow you have " + tasks.size() + " tasks in the list.");
                        Storage.saveTasks(tasks);
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
                    output("Got it. I've added this task:\n" 
                            + tasks.get(tasks.size() - 1) 
                            + "\nNow you have " + tasks.size() + " tasks in the list.");
                    Storage.saveTasks(tasks);
                    break;      

                case DEADLINE: // eg: deadline read book /by 2019-10-15 or deadline read book /by 2019-10-15 1800
                    if (instruction.getTask().isEmpty()) {
                        throw new IncompleteInstructionException("deadline", "task description");
                    }

                    if (instruction.getDatetimes().length == 0) {
                        throw new IncompleteInstructionException("deadline", "date and time");
                    }

                    tasks.add(new Deadline(instruction.getTask(), 
                            instruction.getDatetimes()[0]));
                    output("Got it. I've added this task:\n" 
                            + tasks.get(tasks.size() - 1) 
                            + "\nNow you have " + tasks.size() + " tasks in the list.");
                    Storage.saveTasks(tasks);
                    break;

                case EVENT: // eg: event read book /from 2019-10-15 /to 2019-10-16 or event read book /from 2019-10-15 1800 /to 2019-10-16 2000
                    if (instruction.getTask().isEmpty()) {
                        throw new IncompleteInstructionException("event", "task description");
                    }

                    if (instruction.getDatetimes().length < 2) {
                        throw new IncompleteInstructionException("event", "date and time");
                    }

                    tasks.add(new Event(instruction.getTask(), 
                            instruction.getDatetimes()[0], 
                            instruction.getDatetimes()[1]));
                    output("Got it. I've added this task:\n" 
                            + tasks.get(tasks.size() - 1) 
                            + "\nNow you have " + tasks.size() + " tasks in the list.");
                    Storage.saveTasks(tasks);
                    break;
                }
            } catch (IncompleteInstructionException e) {
                output("ERROR!!! " + e.getMessage());
            } catch (UnknownCommandException e) {
                output("ERROR!!! " + e.getMessage());
            } catch (NumberFormatException e) {
                output("ERROR!!! Please provide a valid task number.");
            } catch (InvalidDateException e) {
                output("ERROR!!! " + e.getMessage());
            } catch (Exception e) {
                output("ERROR!!! An unexpected error occurred: " + e.getMessage());
            }
        }
    }
}
