import java.util.Scanner;

public class Kip {
    public static void main(String[] args) {
        String greeting = "____________________________________________________________\n"
                + "Hello! I'm Kip\n"
                + "What can I do for you?\n"
                + "____________________________________________________________\n";
        
        System.out.println(greeting);
        
        Scanner scanner = new Scanner(System.in);
        String userInput;
        
        // Command loop
        while (true) {
            
            userInput = scanner.nextLine().trim();
            
            if (userInput.equalsIgnoreCase("bye")) {
                System.out.println("Bye. Hope to see you again soon!");
                break;
            } else {
                System.out.println(userInput);
            }
        }
        
        scanner.close();
    }
}
