import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

// Adapted from ChatGPT
// with minor modifications

// csv file format:
// type,done,description,datetime1,datetime2
// T,0,read book,,
// D,1,read book,2025-08-19,
// E,0,read book,2025-08-19,2025-08-20

public class Storage {
    private static final String CSV_FILE = "tasks.csv";
    private static final String CSV_HEADER = "type,done,description,datetime1,datetime2";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
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
                parseTaskLine(firstLine, tasks);
            }
            
            // Read remaining lines
            while ((line = reader.readLine()) != null) {
                parseTaskLine(line, tasks);
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
                String done = task.isDone ? "1" : "0";
                String description = task.getDescription();
                String datetime1 = "";
                String datetime2 = "";
                
                if (task instanceof ToDo) {
                    type = "T";
                } else if (task instanceof Deadline) {
                    type = "D";
                    Deadline deadline = (Deadline) task;
                    datetime1 = deadline.getBy().format(DATE_FORMATTER);
                } else if (task instanceof Event) {
                    type = "E";
                    Event event = (Event) task;
                    datetime1 = event.getFrom().format(DATE_FORMATTER);
                    datetime2 = event.getTo().format(DATE_FORMATTER);
                }
                
                writer.println(String.format("%s,%s,%s,%s,%s", 
                    type, done, description, datetime1, datetime2));
            }
        } catch (IOException e) {
            System.out.println("Error saving " + CSV_FILE + ": " + e.getMessage());
        }
    }
    
    /**
     * Parses a single CSV line into a Task object
     * @param line CSV line to parse
     * @param tasks ArrayList to add the parsed task to
     */
    private static void parseTaskLine(String line, ArrayList<Task> tasks) {
        try {
            String[] parts = line.split(",");
            if (parts.length < 3) return; // Skip invalid lines
            
            String type = parts[0].trim();
            boolean done = parts[1].trim().equals("1");
            String description = parts[2].trim();
            
            Task task = null;
            
            switch (type) {
                case "T": // TODO
                    task = new ToDo(description);
                    break;
                case "D": // DEADLINE
                    if (parts.length >= 4 && !parts[3].trim().isEmpty()) {
                        LocalDate date = LocalDate.parse(parts[3].trim(), DATE_FORMATTER);
                        task = new Deadline(description, date);
                    }
                    break;
                case "E": // EVENT
                    if (parts.length >= 5 && !parts[3].trim().isEmpty() && !parts[4].trim().isEmpty()) {
                        LocalDate startDate = LocalDate.parse(parts[3].trim(), DATE_FORMATTER);
                        LocalDate endDate = LocalDate.parse(parts[4].trim(), DATE_FORMATTER);
                        task = new Event(description, startDate, endDate);
                    }
                    break;
            }
            
            if (task != null) {
                if (done) {
                    task.markAsDone();
                }
                tasks.add(task);
            }
        } catch (Exception e) {
            System.out.println("Error parsing line: " + line + " - " + e.getMessage());
        }
    }
}
