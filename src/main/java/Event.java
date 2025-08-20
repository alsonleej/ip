public class Event extends Task {
    private String from;
    private String to;

    public Event(String description, String from, String to) {
        super(description);

        // change first space to ": "
        from = from.replaceFirst(" ", ": ");
        to = to.replaceFirst(" ", ": ");

        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
 
        return "[E]" + super.toString() + " (" + from + to + ")"; // eg [E][ ] read book (from: Sunday to: Monday)
    }
}
