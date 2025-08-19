public enum TaskType {
    TODO("todo"),
    DEADLINE("deadline"),
    EVENT("event");

    private final String typeString;

    TaskType(String typeString) {
        this.typeString = typeString;
    }

    public String getTypeString() {
        return typeString;
    }

    public static TaskType fromString(String text) {
        for (TaskType type : TaskType.values()) {
            if (type.typeString.equalsIgnoreCase(text)) {
                return type;
            }
        }
        return null;
    }
}
