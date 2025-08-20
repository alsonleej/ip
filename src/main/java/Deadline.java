public class Deadline extends Task {
    private String by;

    public Deadline(String description, String by) {
        super(description);
        
        // change first space to ": "
        by = by.replaceFirst(" ", ": ");

        this.by = by; /// eg this.by = "by Sunday"
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + "(" + by + ")"; // eg [D][ ] read book (by: Sunday)
    }
}
