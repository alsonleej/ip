import java.util.Scanner;

public class Kip {
    private static final int MAX_INPUTS = 100;

    private static void output(String text) {
        String output = "____________________________________________________________\n"
                + text + "\n"
                + "____________________________________________________________\n";
        System.out.println(output);
    }


    public static void main(String[] args) {
        output("Hello! I'm Kip\nWhat can I do for you?\n");
        
        Scanner scanner = new Scanner(System.in);
        String userInput;

        Task[] tasks = new Task[MAX_INPUTS];
        int inputCount = 0;
        
        while (true) {
            userInput = scanner.nextLine().trim();
            
            if (userInput.equalsIgnoreCase("bye")) {
                output("Bye. Hope to see you again soon!");
                break;
            } else if (userInput.equalsIgnoreCase("list")) {
                // Show all stored inputs

                String out = "Here are the tasks in your list:\n";
                for (int i = 0; i < inputCount; i++) {
                    out += (i + 1) + ". " + tasks[i] + "\n";
                }
                output(out);

            // Tasks marking and unmarking
            } else if (userInput.startsWith("mark")) {
                String[] parts = userInput.split(" ");
                int taskIndex = Integer.parseInt(parts[1]) - 1;
                tasks[taskIndex].markAsDone();
                output("Nice! I've marked this task as done:\n" + tasks[taskIndex]);
            } else if (userInput.startsWith("unmark")) {
                String[] parts = userInput.split(" ");
                int taskIndex = Integer.parseInt(parts[1]) - 1;
                tasks[taskIndex].unmarkAsDone();
                output("OK, I've marked this task as not done yet:\n" + tasks[taskIndex]);

            // Tasks adding
            } else {
                // Store the input in the array if there's space
                if (inputCount >= MAX_INPUTS) {
                    output("Array is full! Cannot store more inputs.");
                } else {
                    if (userInput.startsWith("todo")) {
                        String[] parts = userInput.split(" ");
                        String description = userInput.substring(5);
                        tasks[inputCount] = new ToDo(description);
                    } else if (userInput.startsWith("deadline")) {
                        String[] parts = userInput.split(" ");
                        String description = userInput.substring(8);
                        String by = parts[parts.length - 1];
                        tasks[inputCount] = new Deadline(description, by);
                    } else if (userInput.startsWith("event")) {
                        String[] parts = userInput.split(" ");
                        String description = userInput.substring(6);
                        String from = parts[parts.length - 2];
                        String to = parts[parts.length - 1];
                        tasks[inputCount] = new Event(description, from, to);
                    }
                    tasks[inputCount] = new Task(userInput);
                    inputCount++;
                    output("added:" + userInput);
                } else {
                    output("Array is full! Cannot store more inputs.");
                }
            }
        }
        
        scanner.close();
    }
}
