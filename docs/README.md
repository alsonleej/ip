# Kip User Guide

![Kip Chatbot GUI](docs/Ui.png)

**Kip** is a desktop task management application that helps you organize and track your tasks efficiently. Whether you're managing daily todos, tracking project deadlines, or scheduling events, Kip provides a simple yet powerful command-line interface to keep your tasks organized.

*Table of Contents:*
- [Quick Start](#quick-start)
- [Features](#features)
- [Command Reference](#command-reference)
- [Data Storage](#data-storage)
- [Troubleshooting](#troubleshooting)
- [FAQ](#faq)

---

## Quick Start

1. [**Download**](**link here**) the latest version of Kip
2. **Run** the application:
    - For GUI, use `./gradlew run`
    - For CLI, use `java -cp build/classes/java/main kip.Kip`
3. **Type** a command and press Enter to execute it
4. **Type** `bye` to exit the application

### First Time Setup

When you first run Kip, it will:
- Create a data file to store your tasks
- Show you the supported date formats

---

## Features

### 📝 **Task Management**
- **ToDo Tasks**: Simple tasks without specific deadlines
- **Deadline Tasks**: Tasks with specific due dates and times
- **Event Tasks**: Tasks with start and end times

### ✅ **Task Operations**
- Mark tasks as done or undone
- Delete tasks you no longer need
- Search for specific tasks
- View all your tasks in a organized list

### 💾 **Data Persistence**
- All tasks are automatically saved to a CSV file
- Your data persists between application sessions
- No manual saving required

### 🔍 **Smart Search**
- Find tasks quickly using keywords
- Search works across task descriptions

---

## Command Reference

### Adding Tasks

#### Adding a ToDo Task
Adds a simple task without any specific deadline.

**Format:** `todo <description>`

**Example:**
```
todo buy groceries
```

**Expected Output:**
```
____________________________________________________________
Got it. I've added this task:
[T][ ] buy groceries
Now you have 1 tasks in the list.
____________________________________________________________
```

#### Adding a Deadline Task
Adds a task with a specific due date and time.

**Format:** `deadline <description> /by <date>`

**Example:**
```
deadline submit report /by 2025-01-15 1800
```

**Expected Output:**
```
____________________________________________________________
Got it. I've added this task:
[D][ ] submit report (by: Jan 15 2025 18:00)
Now you have 2 tasks in the list.
____________________________________________________________
```

#### Adding an Event Task
Adds a task with both start and end times.

**Format:** `event <description> /from <start_date> /to <end_date>`

**Example:**
```
event team meeting /from 2025-01-20 1400 /to 2025-01-20 1600
```

**Expected Output:**
```
____________________________________________________________
Got it. I've added this task:
[E][ ] team meeting (from: Jan 20 2025 14:00 to: Jan 20 2025 16:00)
Now you have 3 tasks in the list.
____________________________________________________________
```

### Managing Tasks

#### Listing All Tasks
Displays all your tasks in a numbered list.

**Format:** `list`

**Example:**
```
list
```

**Expected Output:**
```
____________________________________________________________
Here are the tasks in your list:
1. [T][ ] buy groceries
2. [D][ ] submit report (by: Jan 15 2025 18:00)
3. [E][ ] team meeting (from: Jan 20 2025 14:00 to: Jan 20 2025 16:00)
Now you have 3 tasks in the list.
____________________________________________________________
```

#### Marking Tasks as Done
Marks a specific task as completed.

**Format:** `mark <task_number>`

**Example:**
```
mark 1
```

**Expected Output:**
```
____________________________________________________________
Nice! I've marked this task as done:
[T][X] buy groceries
____________________________________________________________
```

#### Marking Tasks as Undone
Marks a completed task as not done yet.

**Format:** `unmark <task_number>`

**Example:**
```
unmark 1
```

**Expected Output:**
```
____________________________________________________________
OK, I've marked this task as not done yet:
[T][ ] buy groceries
____________________________________________________________
```

#### Deleting Tasks
Removes a task from your list permanently.

**Format:** `delete <task_number>`

**Example:**
```
delete 2
```

**Expected Output:**
```
____________________________________________________________
Noted. I've removed this task:
[D][ ] submit report (by: Jan 15 2025 18:00)
Now you have 2 tasks in the list.
____________________________________________________________
```

#### Finding Tasks
Searches for tasks containing specific keywords.

**Format:** `find <keyword>`

**Example:**
```
find meeting
```

**Expected Output:**
```
____________________________________________________________
Here are the matching tasks in your list:
1. [E][ ] team meeting (from: Jan 20 2025 14:00 to: Jan 20 2025 16:00)
____________________________________________________________
```

#### Asking for Help
Displays a list of available commands.

**Format:** `help`

### Exiting the Application

#### Exiting Kip
Safely exits the application and saves all your data.

**Format:** `bye`

**Example:**
```
bye
```

**Expected Output:**
```
____________________________________________________________
Bye. Hope to see you again soon!
____________________________________________________________
```

---

## Data Storage

### File Location
Kip stores all your tasks in a CSV file located at:
```
src/main/java/kip/storage/tasks.csv
```

### File Format
The CSV file uses the following format:
```csv
type,done,description,datetime1,datetime2
T,0,buy groceries,,
D,1,submit report,2025-01-15 1800,
E,0,team meeting,2025-01-20 1400,2025-01-20 1600
```

**Field Descriptions:**
- **type**: T (ToDo), D (Deadline), or E (Event)
- **done**: 0 (false) or 1 (true)
- **description**: Task description
- **datetime1**: Deadline date or event start time
- **datetime2**: Event end time (unused for ToDo/Deadline)

### Data Persistence
- Tasks are automatically saved after every modification
- No manual saving is required
- Data persists between application sessions
- The file is created automatically if it doesn't exist

---

## Date and Time Formats

Kip supports two date/time formats:

### Date Only Format
**Format:** `yyyy-MM-dd`

**Examples:**
- `2025-01-15` (January 15, 2025)
- `2025-12-31` (December 31, 2025)

### Date and Time Format
**Format:** `yyyy-MM-dd HHmm`

**Examples:**
- `2025-01-15 1800` (January 15, 2025 at 6:00 PM)
- `2025-12-31 2359` (December 31, 2025 at 11:59 PM)

### Important Notes
- **24-hour format**: Times are in 24-hour format (00:00 to 23:59)
- **No commas**: Task descriptions and dates cannot contain commas (,) as they break the CSV format
- **Leading zeros**: Use leading zeros for single-digit months and days

---

## Troubleshooting

### Common Issues

#### "Invalid task number!" Error
**Problem:** You're trying to mark, unmark, or delete a task that doesn't exist.

**Solution:** 
- Use `list` to see all available tasks
- Use the correct task number from the list
- Task numbers start from 1

#### "Invalid date format" Error
**Problem:** The date you provided doesn't match the supported formats.

**Solution:**
- Use `yyyy-MM-dd` for dates only (e.g., `2025-01-15`)
- Use `yyyy-MM-dd HHmm` for dates with time (e.g., `2025-01-15 1800`)
- Ensure you're using 24-hour format for times

#### "Cannot contain commas" Error
**Problem:** Your task description or date contains commas.

**Solution:**
- Remove commas from task descriptions
- Use different characters instead of commas
- Commas are reserved for CSV formatting

#### "No matching tasks found" Message
**Problem:** Your search keyword doesn't match any task descriptions.

**Solution:**
- Check your spelling
- Try a different keyword
- Use partial words (e.g., "meet" instead of "meeting")

### File Access Issues

#### "Error creating/saving tasks.csv" Message
**Problem:** Kip cannot access the storage file.

**Solution:**
- Ensure you have write permissions in the application directory
- Check if the file is being used by another program
- Restart the application

---

## FAQ

### Q: Can I edit a task after creating it?
A: Currently, Kip doesn't support editing tasks directly. You can delete the task and create a new one with the correct information.

### Q: How many tasks can I store?
A: There's no hard limit on the number of tasks. However, very large lists may take longer to load and save.

### Q: Can I use Kip on different operating systems?
A: Yes! Kip is a Java application and runs on any system with Java 17 or higher installed.

### Q: What happens if I close Kip without typing 'bye'?
A: Your tasks are automatically saved after every command, so you won't lose any data. However, it's good practice to use the `bye` command to exit properly.

### Q: Can I backup my tasks?
A: Yes! Simply copy the `tasks.csv` file to create a backup. You can restore it by replacing the file and restarting Kip.

### Q: Why can't I use commas in task descriptions?
A: Commas are used as separators in the CSV file format. Using commas would break the file structure and cause data corruption.

### Q: Can I change the date format?
A: Currently, Kip only supports the `yyyy-MM-dd` and `yyyy-MM-dd HHmm` formats. This ensures consistency and prevents parsing errors.

---

## Getting Help

If you encounter any issues not covered in this guide:

1. **Check the error message** - Kip provides descriptive error messages
2. **Verify your command format** - Refer to the Command Reference section
3. **Check your data** - Use `list` to see your current tasks
4. **Restart the application** - Sometimes a simple restart resolves issues

---

*Happy task managing with Kip! 🎉*