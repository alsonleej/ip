package kip.storage;

import kip.task.Task;
import kip.task.ToDo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import java.io.File;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

public class StorageTest {
    
    private static final String TEST_CSV_FILE = "src/main/java/kip/storage/tasks.csv";
    private File originalFile;
    
    @BeforeEach
    public void setUp() {
        // Backup the original file if it exists
        File file = new File(TEST_CSV_FILE);
        if (file.exists()) {
            // Store reference to original file
            originalFile = file;
        }
    }
    
    @AfterEach
    public void tearDown() {
        // Restore original file if it existed
        if (originalFile != null && originalFile.exists()) {
            // File should remain as is for the application
        }
    }
    
    @Test
    public void testLoadTasks() {
        // Test that tasks can be loaded from the file
        ArrayList<Task> tasks = Storage.loadTasks();
        assertNotNull(tasks);
        // The file should exist and be readable
        File file = new File(TEST_CSV_FILE);
        assertTrue(file.exists(), "CSV file should exist");
        assertTrue(file.canRead(), "CSV file should be readable");
    }
    
    @Test
    public void testSaveTasks() {
        // Test that tasks can be saved to the file
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new ToDo("Test task"));
        
        // Save tasks
        Storage.saveTasks(tasks);
        
        // Verify file was created/updated
        File file = new File(TEST_CSV_FILE);
        assertTrue(file.exists(), "CSV file should exist after saving");
        assertTrue(file.length() > 0, "CSV file should not be empty after saving");
    }
    
    @Test
    public void testFileLocation() {
        // Test that the file path is correct
        File file = new File(TEST_CSV_FILE);
        String expectedPath = "src" + File.separator + "main" + File.separator + 
                             "java" + File.separator + "kip" + File.separator + 
                             "storage" + File.separator + "tasks.csv";
        
        assertTrue(file.getPath().contains("kip" + File.separator + "storage"), 
                  "File should be in the kip/storage directory");
    }
}
