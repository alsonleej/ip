package kip.storage;

import java.io.*;
import java.util.ArrayList;
import kip.task.Task;
import kip.task.ToDo;
import kip.task.Deadline;
import kip.task.Event;
import kip.command.Parser;

// Adapted from ChatGPT
// with minor modifications

// csv file format:
// type,done,description,datetime1,datetime2
// T,0,read book,,
// D,1,read book,2025-08-19 0000,
// E,0,read book,2025-08-19 0000,2025-08-20 0000

public class Storage {
    private static final String CSV_FILE = "src/main/java/kip/storage/tasks.csv";
    private static final String CSV_HEADER = "type,done,description,datetime1,datetime2";
    
    /**
     * Loads tasks from the CSV file
     * @return ArrayList of loaded tasks
     */
    public static ArrayList<Task> loadTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        File csvFile = new File(CSV_FILE);
        
        // Create file if it doesn't exist
        if (!csvFile.exists()) {
            try {
                csvFile.createNewFile();

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
     * Saves tasks to the CSV file
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
