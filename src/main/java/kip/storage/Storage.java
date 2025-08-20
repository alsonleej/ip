package kip.storage;

import java.io.*;
import java.util.ArrayList;
import kip.task.Task;
import kip.task.ToDo;
import kip.task.Deadline;
import kip.task.Event;
import kip.command.Parser;

/**
 * Handles persistent storage of tasks using CSV file format in the Kip task management system.
 * 
 * <p>The Storage class provides functionality to save and load tasks from a CSV file,
 * ensuring data persistence across application sessions. It automatically creates
 * the storage file if it doesn't exist and handles all file I/O operations.</p>
 * 
 * <p>The CSV format used is:</p>
 * <pre>
 * type,done,description,datetime1,datetime2
 * T,0,read book,,
 * D,1,return book,2025-08-19 0000,
 * E,0,meeting,2025-08-19 0000,2025-08-20 0000
 * </pre>
 * 
 * <p>Where:</p>
 * <ul>
 *   <li><strong>type</strong>: T (ToDo), D (Deadline), or E (Event)</li>
 *   <li><strong>done</strong>: 0 (false) or 1 (true)</li>
 *   <li><strong>description</strong>: Task description</li>
 *   <li><strong>datetime1</strong>: Deadline date or event start time</li>
 *   <li><strong>datetime2</strong>: Event end time (unused for ToDo/Deadline)</li>
 * </ul>
 * 
 * <p>All file operations are performed on the tasks.csv file located in the
 * storage package directory.</p>
 * 
 * @author alsonleej
 * @version 1.0
 * @since 2025
 * @see Task
 * @see Parser
 */
public class Storage {
    /** Path to the CSV file for storing tasks */
    private static final String CSV_FILE = "src/main/java/kip/storage/tasks.csv";
    /** Header line for the CSV file */
    private static final String CSV_HEADER = "type,done,description,datetime1,datetime2";
    
    /**
     * Loads tasks from the CSV file.
     * 
     * <p>This method reads the CSV file and reconstructs Task objects from the stored data.
     * If the file doesn't exist, it creates a new empty file. The method handles all
     * three task types and automatically marks tasks as done if they were previously
     * completed.</p>
     * 
     * <p>Error handling is implemented to skip invalid lines and continue loading
     * valid tasks, ensuring the application remains robust even with corrupted data.</p>
     * 
     * @return ArrayList of loaded tasks, empty list if file is new or empty
     */
    public static ArrayList<Task> loadTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        File csvFile = new File(CSV_FILE);
        
        // Create file if it doesn't exist
        if (!csvFile.exists()) {
            try {
                csvFile.createNewFile();
                System.out.println("Created new " + CSV_FILE + " file");
            } catch (IOException e) {
                System.out.println("Error creating " + CSV_FILE + ": " + e.getMessage());
            }
            return tasks; // Return empty list for new file
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
            String line;
            
            // Skip header if exists
            String firstLine = reader.readLine();
            if (firstLine != null && firstLine.startsWith(CSV_HEADER)) {
                // Skip header line
            } else if (firstLine != null) {
                // First line is data, parse it
                try {
                    Task task = Parser.parseTaskLine(firstLine);
                    if (task != null) {
                        tasks.add(task);
                    }
                } catch (Exception e) {
                    System.out.println("Error parsing first line: " + firstLine 
                            + " - " + e.getMessage());
                }
            }
            
            // Read remaining lines
            while ((line = reader.readLine()) != null) {
                try {
                    Task task = Parser.parseTaskLine(line);
                    if (task != null) {
                        tasks.add(task);
                    }
                } catch (Exception e) {
                    System.out.println("Error parsing line: " + line 
                            + " - " + e.getMessage());
                }
            }
            
            System.out.println("Loaded " + tasks.size() + " tasks from " + CSV_FILE);
        } catch (IOException e) {
            System.out.println("Error reading " + CSV_FILE + ": " + e.getMessage());
        }
        
        return tasks;
    }
    
    /**
     * Saves tasks to the CSV file.
     * 
     * <p>This method writes all tasks in the provided list to the CSV file, overwriting
     * any existing content. The file is created if it doesn't exist. Each task is
     * converted to its CSV representation based on its type.</p>
     * 
     * <p>The method handles all task types:</p>
     * <ul>
     *   <li><strong>ToDo</strong>: Only type and description are stored</li>
     *   <li><strong>Deadline</strong>: Type, description, and deadline date are stored</li>
     *   <li><strong>Event</strong>: Type, description, start time, and end time are stored</li>
     * </ul>
     * 
     * @param tasks ArrayList of tasks to save
     */
    public static void saveTasks(ArrayList<Task> tasks) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(CSV_FILE))) {
            // Write header
            writer.println(CSV_HEADER);
            
            // Write each task
            for (Task task : tasks) {
                String type = "";
                String done = task.isDone() ? "1" : "0";
                String description = task.getDescription();
                String datetime1 = "";
                String datetime2 = "";
                
                if (task instanceof ToDo) {
                    type = "T";
                } else if (task instanceof Deadline) {
                    type = "D";
                    Deadline deadline = (Deadline) task;
                    datetime1 = deadline.getBy().format(Parser.getDateTimeFormatter());
                } else if (task instanceof Event) {
                    type = "E";
                    Event event = (Event) task;
                    datetime1 = event.getFrom().format(Parser.getDateTimeFormatter());
                    datetime2 = event.getTo().format(Parser.getDateTimeFormatter());
                }
                
                writer.println(String.format("%s,%s,%s,%s,%s", 
                        type, done, description, datetime1, datetime2));
            }
        } catch (IOException e) {
            System.out.println("Error saving " + CSV_FILE + ": " + e.getMessage());
        }
    }
}
