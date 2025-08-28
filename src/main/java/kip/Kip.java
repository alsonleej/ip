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
import kip.KipService;

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
        KipService kipService = new KipService();
        
        while (true) {
            try {
                userInput = scanner.nextLine().trim();
                String response = kipService.processCommand(userInput);
                
                if (response.equals("Bye. Hope to see you again soon!")) {
                    output(response);
                    scanner.close();
                    return;
                }
                
                output(response);
                
            } catch (Exception e) {
                output("ERROR!!! An unexpected error occurred: " + e.getMessage());
            }
        }
    }
}
