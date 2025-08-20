package kip.task;

/**
 * Abstract base class representing a task in the Kip task management system.
 * 
 * <p>Task is the foundation class for all task types in the system. It provides
 * common functionality such as description management, completion status tracking,
 * and basic string representation.</p>
 * 
 * <p>This class is designed to be extended by specific task types like ToDo,
 * Deadline, and Event, which add their own specialized behavior while inheriting
 * the common task functionality.</p>
 * 
 * <p>Tasks support the following operations:</p>
 * <ul>
 *   <li>Setting and retrieving task descriptions</li>
 *   <li>Marking tasks as done or undone</li>
 *   <li>Getting completion status</li>
 *   <li>String representation with completion status</li>
 * </ul>
 * 
 * @author alsonleej
 * @version 1.0
 * @since 2025
 * @see ToDo
 * @see Deadline
 * @see Event
 */
public abstract class Task {
    /** The description or title of the task */
    protected String description;
    /** Flag indicating whether the task has been completed */
    protected boolean isDone;

    /**
     * Constructs a new Task with the specified description.
     * 
     * <p>The task is initially marked as not done (incomplete).</p>
     * 
     * @param description The description or title of the task
     * @throws IllegalArgumentException if description is null or empty
     */
    public Task(String description) {
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Task description cannot be null or empty");
        }
        this.description = description.trim();
        this.isDone = false;
    }

    /**
     * Returns a visual indicator of the task's completion status.
     * 
     * @return "X" if the task is done, " " (space) if not done
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " "); 
    }

    /**
     * Returns the description of the task.
     * 
     * @return The task description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns whether the task has been completed.
     * 
     * @return true if the task is done, false otherwise
     */
    public boolean isDone() {
        return isDone;
    }

    /**
     * Marks the task as completed.
     * 
     * <p>This method sets the completion status to true, indicating
     * that the task has been finished.</p>
     */
    public void markAsDone() {
        isDone = true;
    }

    /**
     * Marks the task as not completed.
     * 
     * <p>This method sets the completion status to false, indicating
     * that the task is still pending or has been reopened.</p>
     */
    public void unmarkAsDone() {
        isDone = false;
    }

    /**
     * Returns a string representation of the task.
     * 
     * <p>The format is: [status] description, where status is either
     * a space ( ) for incomplete tasks or X for completed tasks.</p>
     * 
     * @return String representation of the task
     */
    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}