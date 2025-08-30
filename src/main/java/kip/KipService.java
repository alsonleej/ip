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
    }
    
    public String processCommand(String userInput) {
        try {
            Instruction instruction = Parser.parseUserInput(userInput);
            Command cmd = Command.fromString(instruction.getCommand());
            
            if (cmd == null) {
                throw new UnknownCommandException(instruction.getCommand());
            }
            
            return executeCommand(cmd, instruction);
            
        } catch (Exception e) {
            return "ERROR!!! " + e.getMessage();
        }
    }
    
    private String executeCommand(Command cmd, Instruction instruction) throws Exception {
        int taskIndex;
        String out;
        
        switch (cmd) {
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
            if (taskIndex >= 0 && taskIndex < tasks.size()) {
                tasks.get(taskIndex).markAsDone();
                out = "Nice! I've marked this task as done:\n" + tasks.get(taskIndex);
                Storage.saveTasks(tasks);
                return out;
            } else {
                throw new NumberFormatException("Invalid task number!");
            }
            
        case UNMARK:
            taskIndex = Integer.parseInt(instruction.getTask()) - 1;
            if (taskIndex >= 0 && taskIndex < tasks.size()) {
                tasks.get(taskIndex).unmarkAsDone();
                out = "OK, I've marked this task as not done yet:\n" + tasks.get(taskIndex);
                out += "\n" + tasks.get(taskIndex);
                Storage.saveTasks(tasks);
                return out;
            } else {
                throw new NumberFormatException("Invalid task number!");
            }
            
        case DELETE:
            taskIndex = Integer.parseInt(instruction.getTask()) - 1;
            if (taskIndex >= 0 && taskIndex < tasks.size()) {
                Task removedTask = tasks.remove(taskIndex);
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
            tasks.add(new kip.task.ToDo(instruction.getTask()));
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
            tasks.add(new kip.task.Deadline(instruction.getTask(), instruction.getDatetimes()[0]));
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
            tasks.add(new kip.task.Event(instruction.getTask(), instruction.getDatetimes()[0], instruction.getDatetimes()[1]));
            out = "Got it. I've added this task:\n" + tasks.get(tasks.size() - 1) 
                    + "\nNow you have " + tasks.size() + " tasks in the list.";
            Storage.saveTasks(tasks);
            return out;
            
        default:
            throw new UnknownCommandException(instruction.getCommand());
        }
    }
    
    public ArrayList<Task> getTasks() {
        return new ArrayList<>(tasks);
    }
}
