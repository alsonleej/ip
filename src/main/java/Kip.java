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
            } else {
                // Store the input in the array if there's space
                if (inputCount < MAX_INPUTS) {
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
