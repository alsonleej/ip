
**Warning:** Keep the `src\main\java` folder as the root folder for Java files (i.e., don't rename those folders or move Java files to another folder outside of this folder path), as this is the default location some tools (e.g., Gradle) expect to find Java files.

# Project structure
```
src/main/java/kip/
├── Kip.java                    # Main application class
├── task/                       # Task-related classes
│   ├── Task.java             # Base Task class
│   ├── ToDo.java             # ToDo task implementation
│   ├── Deadline.java          # Deadline task implementation
│   └── Event.java            # Event task implementation
├── command/                    # Command and parsing classes
│   ├── Command.java          # Command enum
│   ├── Instruction.java      # Instruction class
│   └── Parser.java           # Parser for user input
├── exception/                  # Custom exception classes
│   ├── IncompleteInstructionException.java
│   ├── InvalidDateException.java
│   └── UnknownCommandException.java
└── storage/                    # Storage and file handling
    ├── Storage.java           # File I/O operations
    └── tasks.csv              # Data file
```

# Note to self

## To test methods with gradle (unit tests)
```
./gradlew test
```

## To test input and outputs (regression tests)
```
PS C:\Users\alsonleej\ip\ip\text-ui-test> ./runtest.bat
```

## To update outputs
```
PS C:\Users\alsonleej\ip\ip\text-ui-test> ./runtest.bat
cp .\ACTUAL.TXT .\EXPECTED.TXT
```

## To run manually
```
in PS C:\Users\alsonleej\ip\ip>
javac -cp src/main/java -d out src/main/java/kip/Kip.java
java -cp out kip.Kip
```
