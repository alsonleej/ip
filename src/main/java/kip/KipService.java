package kip;

import java.util.ArrayList;
import kip.task.Task;
import kip.command.Command;
import kip.command.Instruction;
import kip.command.Parser;
import kip.exception.IncompleteInstructionException;
import kip.exception.UnknownCommandException;
import kip.storage.Storage;

public class KipService {
    private ArrayList<Task> tasks;
    
    public KipService() {
        this.tasks = Storage.loadTasks();
        // Assert that tasks list is not null after loading
        assert this.tasks != null : "Tasks list must not be null after loading from storage";
    }
    
    public String processCommand(String userInput) {
        // Assert that userInput is not null
        assert userInput != null : "User input must not be null";
        
        try {
            Instruction instruction = Parser.parseUserInput(userInput);
            // Assert that instruction is not null
            assert instruction != null : "Instruction must not be null after parsing";
            
            Command cmd = Command.fromString(instruction.getCommand());
            
            if (cmd == null) {
                throw new UnknownCommandException(instruction.getCommand());
            }
            
            // Assert that cmd is not null after validation
            assert cmd != null : "Command must not be null after validation";
            
            return executeCommand(cmd, instruction);
            
        } catch (Exception e) {
            return "ERROR!!! " + e.getMessage();
        }
    }
    
    private String executeCommand(Command cmd, Instruction instruction) throws Exception {
        // Assert that parameters are not null
        assert cmd != null : "Command must not be null";
        assert instruction != null : "Instruction must not be null";
        assert tasks != null : "Tasks list must not be null";
        
        int taskIndex;
        String out;
        
        switch (cmd) {
        // as each case has a return, break is not needed
        case BYE:
            return "Bye. Hope to see you again soon!";
            
        case LIST:
            out = "Here are the tasks in your list:\n";
            for (int i = 0; i < tasks.size(); i++) {
                out += (i + 1) + ". " + tasks.get(i) + "\n";
            }
            out += "Now you have " + tasks.size() + " tasks in the list.";
            return out;
            
        case MARK:
            taskIndex = Integer.parseInt(instruction.getTask()) - 1;
            // Assert that taskIndex is within valid range
            assert taskIndex >= 0 && taskIndex < tasks.size() : "Task index must be within valid range";
            if (taskIndex >= 0 && taskIndex < tasks.size()) {
                Task taskToMark = tasks.get(taskIndex);
                // Assert that task exists
                assert taskToMark != null : "Task to mark must not be null";
                taskToMark.markAsDone();
                // Assert that task is marked as done
                assert taskToMark.isDone() : "Task should be marked as done";
                out = "Nice! I've marked this task as done:\n" + taskToMark;
                Storage.saveTasks(tasks);
                return out;
            } else {
                throw new NumberFormatException("Invalid task number!");
            }
            
        case UNMARK:
            taskIndex = Integer.parseInt(instruction.getTask()) - 1;
            // Assert that taskIndex is within valid range
            assert taskIndex >= 0 && taskIndex < tasks.size() : "Task index must be within valid range";
            if (taskIndex >= 0 && taskIndex < tasks.size()) {
                Task taskToUnmark = tasks.get(taskIndex);
                // Assert that task exists
                assert taskToUnmark != null : "Task to unmark must not be null";
                taskToUnmark.unmarkAsDone();
                // Assert that task is unmarked
                assert !taskToUnmark.isDone() : "Task should be unmarked";
                out = "OK, I've marked this task as not done yet:\n" + taskToUnmark;
                out += "\n" + taskToUnmark;
                Storage.saveTasks(tasks);
                return out;
            } else {
                throw new NumberFormatException("Invalid task number!");
            }
            
        case DELETE:
            taskIndex = Integer.parseInt(instruction.getTask()) - 1;
            // Assert that taskIndex is within valid range
            assert taskIndex >= 0 && taskIndex < tasks.size() : "Task index must be within valid range";
            if (taskIndex >= 0 && taskIndex < tasks.size()) {
                int originalSize = tasks.size();
                Task removedTask = tasks.remove(taskIndex);
                // Assert that task was removed and size decreased
                assert removedTask != null : "Removed task must not be null";
                assert tasks.size() == originalSize - 1 : "Task list size should decrease by 1 after removal";
                out = "Noted. I've removed this task:\n" + removedTask 
                        + "\nNow you have " + tasks.size() + " tasks in the list.";
                Storage.saveTasks(tasks);
                return out;
            } else {
                throw new NumberFormatException("Invalid task number!");
            }
            
        case FIND:
            String keyword = instruction.getTask();
            ArrayList<Task> matchingTasks = new ArrayList<>();
            for (Task task : tasks) {
                if (task.getDescription().contains(keyword)) {
                    matchingTasks.add(task);
                }
            }
            
            if (matchingTasks.isEmpty()) {
                return "No matching tasks found.";
            } else {
                out = "Here are the matching tasks in your list:\n";
                for (int i = 0; i < matchingTasks.size(); i++) {
                    out += (i + 1) + ". " + matchingTasks.get(i) + "\n";
                }
                return out;
            }
            
        case TODO:
            if (instruction.getTask().isEmpty()) {
                throw new IncompleteInstructionException("todo", "task description");
            }
            // Assert that task description is not empty
            assert !instruction.getTask().isEmpty() : "Todo task description must not be empty";
            
            int originalSize = tasks.size();
            Task newTodo = new kip.task.ToDo(instruction.getTask());
            // Assert that new task is not null
            assert newTodo != null : "New todo task must not be null";
            tasks.add(newTodo);
            // Assert that task was added and size increased
            assert tasks.size() == originalSize + 1 : "Task list size should increase by 1 after adding todo";
            out = "Got it. I've added this task:\n" + tasks.get(tasks.size() - 1) 
                    + "\nNow you have " + tasks.size() + " tasks in the list.";
            Storage.saveTasks(tasks);
            return out;
            
        case DEADLINE:
            if (instruction.getTask().isEmpty()) {
                throw new IncompleteInstructionException("deadline", "task description");
            }
            if (instruction.getDatetimes().length == 0) {
                throw new IncompleteInstructionException("deadline", "date and time");
            }
            // Assert that deadline has required components
            assert !instruction.getTask().isEmpty() : "Deadline task description must not be empty";
            assert instruction.getDatetimes().length > 0 : "Deadline must have at least one datetime";
            
            int originalSizeDeadline = tasks.size();
            Task newDeadline = new kip.task.Deadline(instruction.getTask(), instruction.getDatetimes()[0]);
            // Assert that new deadline is not null
            assert newDeadline != null : "New deadline task must not be null";
            tasks.add(newDeadline);
            // Assert that task was added and size increased
            assert tasks.size() == originalSizeDeadline + 1 : "Task list size should increase by 1 after adding deadline";
            out = "Got it. I've added this task:\n" + tasks.get(tasks.size() - 1) 
                    + "\nNow you have " + tasks.size() + " tasks in the list.";
            Storage.saveTasks(tasks);
            return out;
            
        case EVENT:
            if (instruction.getTask().isEmpty()) {
                throw new IncompleteInstructionException("event", "task description");
            }
            if (instruction.getDatetimes().length < 2) {
                throw new IncompleteInstructionException("event", "date and time");
            }
            // Assert that event has required components
            assert !instruction.getTask().isEmpty() : "Event task description must not be empty";
            assert instruction.getDatetimes().length >= 2 : "Event must have at least two datetimes";
            
            int originalSizeEvent = tasks.size();
            Task newEvent = new kip.task.Event(instruction.getTask(), instruction.getDatetimes()[0], instruction.getDatetimes()[1]);
            // Assert that new event is not null
            assert newEvent != null : "New event task must not be null";
            tasks.add(newEvent);
            // Assert that task was added and size increased
            assert tasks.size() == originalSizeEvent + 1 : "Task list size should increase by 1 after adding event";
            out = "Got it. I've added this task:\n" + tasks.get(tasks.size() - 1) 
                    + "\nNow you have " + tasks.size() + " tasks in the list.";
            Storage.saveTasks(tasks);
            return out;
            
        default:
            throw new UnknownCommandException(instruction.getCommand());
        }
    }
    
    public ArrayList<Task> getTasks() {
        // Assert that tasks list is not null
        assert tasks != null : "Tasks list must not be null when getting tasks";
        return new ArrayList<>(tasks);
    }
}
