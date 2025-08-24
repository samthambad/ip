import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Sisyphus {

    public static class DataManager {
        private String DATA_PATH = "../../../data.txt";

        public void saveFile(ArrayList<Task> listToSave) {
            try (PrintWriter writer = new PrintWriter(new FileWriter(DATA_PATH))) {
                for (Task t : listToSave) {
                    String taskName = t.getName();
                    int isDone = t.isDone() ? 1 : 0;

                    String taskType = t.getClass().getSimpleName();
                    switch (taskType) {
                    case "TodoTask":
                        writer.println("T | " + isDone + " | " + taskName);
                        break;
                    case "DeadlineTask":
                        DeadlineTask dt = (DeadlineTask) t;
                        writer.println("D | " + isDone + " | " + taskName + " | " + dt.getDeadlineString());
                        break;
                    case "EventTask":
                        EventTask et = (EventTask) t;
                        writer.println("E | " + isDone + " | " + taskName + " | " + et.getStartString() + " | " + et.getEndString());
                        break;
                    }
                }
            } catch (IOException e) {
                System.out.println("Error saving file: "+ e.getMessage());
            }
            System.out.println("Tasks saved.");
        }
        public ArrayList<Task> readFile() {
            ArrayList<Task> todoList = new ArrayList<>();
            try (Scanner fileReader = new Scanner(new File(DATA_PATH))) {
                while (fileReader.hasNextLine()) {
                    String line = fileReader.nextLine();
                    String[] parts = line.split(" \\| ");
                    if (parts.length < 3) {
                        continue;
                    }
                    String taskType = parts[0];
                    boolean isDone = parts[1].equals("1");
                    String taskName = parts[2];
                    Task task = null;
                    switch (taskType) {
                    case "T":
                        task = new TodoTask(taskName);
                        break;
                    case "D":
                        task = new DeadlineTask(taskName, parts[3]);
                        break;
                    case "E":
                        task = new EventTask(taskName, parts[3], parts[4]);
                        break;
                    }
                    if (task != null) {
                        if (isDone) {
                            task.complete();
                        }
                        todoList.add(task);
                    }
                }
                System.out.println(todoList.size() + " tasks loaded.");
            } catch (FileNotFoundException e) {
                System.out.println("No file found, starting fresh!");
            } catch (Exception e) {
                System.out.println("Error reading file: " + e.getMessage());
            }
            return todoList;
        }
    }

    public static class Task {
        private String name;
        private Boolean isDone = false;

        public Task(String name) {
            this.name = name;
        }

        public boolean isDone() {
            return this.isDone;
        }

        public String getName() {
            return this.name;
        }

        public String toString() {
            if (isDone) {
                return "[X] " + this.name;
            } else {
                return "[ ] " + this.name;
            }
        }

        public void complete() {
            this.isDone = true;
        }

        public void incomplete() {
            this.isDone = false;
        }
    }

    public static class TodoTask extends Task {

        public TodoTask(String name) {
            super(name);
        }

        public String toString() {
            return "[T] " + super.toString();
        }
    }

    public static class DeadlineTask extends Task {
        private LocalDateTime deadline;

        private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        private static final DateTimeFormatter INPUT_DATE_ONLY = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm");

        public DeadlineTask(String name, String deadlineString) throws DateTimeParseException {
            super(name);
            // Parse the input date string (yyyy-MM-dd format)
            this.deadline = parseDateTime(deadlineString.trim());
        }

        private LocalDateTime parseDateTime(String dateTimeString) throws DateTimeParseException {
            try {
                // Try parsing with time (yyyy-MM-dd HH:mm)
                return LocalDateTime.parse(dateTimeString, INPUT_FORMAT);
            } catch (DateTimeParseException e) {
                try {
                    // No time in input so set time to 00:00
                    LocalDate date = LocalDate.parse(dateTimeString, INPUT_DATE_ONLY);
                    return date.atStartOfDay();
                } catch (DateTimeParseException e2) {
                    throw new DateTimeParseException("Invalid date format. Use yyyy-MM-dd or yyyy-MM-dd HH:mm", dateTimeString, 0);
                }
            }
        }

        public LocalDateTime getDeadline() {
            return this.deadline;
        }

        public String getDeadlineString() {
            return this.deadline.format(INPUT_FORMAT);
        }

        public String toString() {
            // Format date for display (MMM dd yyyy format)
            String formattedDate = this.deadline.format(OUTPUT_FORMAT);
            return "[D] " + super.toString() + " (by: " + formattedDate + ")";
        }
    }

    public static class EventTask extends Task {
        private LocalDateTime start;
        private LocalDateTime end;

        private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        private static final DateTimeFormatter INPUT_DATE_ONLY = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm");

        public EventTask(String name, String startString, String endString) throws DateTimeParseException {
            super(name);
            this.start = parseDateTime(startString.trim());
            this.end = parseDateTime(endString.trim());
        }

        // same as in EventTask
        private LocalDateTime parseDateTime(String dateTimeString) throws DateTimeParseException {
            try {
                return LocalDateTime.parse(dateTimeString, INPUT_FORMAT);
            } catch (DateTimeParseException e) {
                try {
                    LocalDate date = LocalDate.parse(dateTimeString, INPUT_DATE_ONLY);
                    return date.atStartOfDay();
                } catch (DateTimeParseException e2) {
                    throw new DateTimeParseException("Invalid date format. Use yyyy-MM-dd or yyyy-MM-dd HH:mm", dateTimeString, 0);
                }
            }
        }

        public LocalDateTime getStart() {
            return this.start;
        }

        public LocalDateTime getEnd() {
            return this.end;
        }

        public String getStartString() {
            return this.start.format(INPUT_FORMAT);
        }

        public String getEndString() {
            return this.end.format(INPUT_FORMAT);
        }

        public String toString() {
            String formattedStart = this.start.format(OUTPUT_FORMAT);
            String formattedEnd = this.end.format(OUTPUT_FORMAT);
            return "[E] " + super.toString() + " (from: " + formattedStart + " to: " + formattedEnd + ")";
        }
    }
    static String divider = "-----------------------------";

    public static void introMessage() {
        String logo = """
                ░▒▓███████▓▒░▒▓█▓▒░░▒▓███████▓▒░▒▓█▓▒░░▒▓█▓▒░▒▓███████▓▒░░▒▓█▓▒░░▒▓█▓▒░▒▓█▓▒░░▒▓█▓▒░░▒▓███████▓▒░
                ░▒▓█▓▒░      ░▒▓█▓▒░▒▓█▓▒░      ░▒▓█▓▒░░▒▓█▓▒░▒▓█▓▒░░▒▓█▓▒░▒▓█▓▒░░▒▓█▓▒░▒▓█▓▒░░▒▓█▓▒░▒▓█▓▒░
                ░▒▓█▓▒░      ░▒▓█▓▒░▒▓█▓▒░      ░▒▓█▓▒░░▒▓█▓▒░▒▓█▓▒░░▒▓█▓▒░▒▓█▓▒░░▒▓█▓▒░▒▓█▓▒░░▒▓█▓▒░▒▓█▓▒░
                ░▒▓██████▓▒░░▒▓█▓▒░░▒▓██████▓▒░ ░▒▓██████▓▒░░▒▓███████▓▒░░▒▓████████▓▒░▒▓█▓▒░░▒▓█▓▒░░▒▓██████▓▒░
                      ░▒▓█▓▒░▒▓█▓▒░      ░▒▓█▓▒░  ░▒▓█▓▒░   ░▒▓█▓▒░      ░▒▓█▓▒░░▒▓█▓▒░▒▓█▓▒░░▒▓█▓▒░      ░▒▓█▓▒░
                      ░▒▓█▓▒░▒▓█▓▒░      ░▒▓█▓▒░  ░▒▓█▓▒░   ░▒▓█▓▒░      ░▒▓█▓▒░░▒▓█▓▒░▒▓█▓▒░░▒▓█▓▒░      ░▒▓█▓▒░
                ░▒▓███████▓▒░░▒▓█▓▒░▒▓███████▓▒░   ░▒▓█▓▒░   ░▒▓█▓▒░      ░▒▓█▓▒░░▒▓█▓▒░░▒▓██████▓▒░░▒▓███████▓▒░
                  """;
        String welcomeMessage = "Hello, I am Sisyphus, what can I do for you?";
        String instructions = "Enter date in yyyy-MM-dd format and date and time in yyyy-MM-dd HH:mm";
        System.out.println(logo);
        System.out.println(welcomeMessage);
        System.out.println(instructions);
        System.out.println(divider);
    }

    public static void main(String[] args) {
        DataManager dm = new DataManager();
        ArrayList<Task> todoList = dm.readFile();
        introMessage();
        String input = "";
        Scanner myObj = new Scanner(System.in);
        while (!input.equals("bye")) {
            input = myObj.nextLine();
            String[] inputArr = input.split(" ");
            System.out.println(divider);

            String taskString = "";
            boolean recordTask = true;
            switch (inputArr[0]) {
            case "bye":
                if (!todoList.isEmpty()) {
                    System.out.println("You have tasks pending, type y/n whether to save.");
                    Scanner anotherScanner = new Scanner(System.in);
                    boolean whetherSave = anotherScanner.nextLine().equals("y");
                    if (whetherSave) {
                        dm.saveFile(todoList);
                    }
                }
                System.out.println("    See you!");
                break;
            case "list":
                for (int i = 0; i < todoList.size(); i++) {
                    int listIdx = i + 1;
                    System.out.println("    " + listIdx + "." + todoList.get(i));
                }
                break;
            case "mark":
                if (inputArr[1].length() > 0 && Integer.parseInt(inputArr[1]) <= todoList.size()) {
                    int number = Integer.parseInt(inputArr[1]);
                    todoList.get(number - 1).complete();
                    System.out.println("Okay, task " + number + " is done");
                    for (int i = 0; i < todoList.size(); i++) {
                        int listIdx = i + 1;
                        System.out.println("    " + listIdx + "." + todoList.get(i));
                    }
                } else {
                    System.out.println("Task " + inputArr[1] + " does not exist");
                }
                break;
            case "unmark":
                if (inputArr[1].length() > 0 && Integer.parseInt(inputArr[1]) <= todoList.size()) {
                    int number = Integer.parseInt(inputArr[1]);
                    todoList.get(number - 1).incomplete();
                    System.out.println("Okay, task " + number + " is not done yet");
                    for (int i = 0; i < todoList.size(); i++) {
                        int listIdx = i + 1;
                        System.out.println("    " + listIdx + "." + todoList.get(i));
                    }
                } else {
                    System.out.println("Task " + inputArr[1] + " does not exist");
                }
                break;
            case "todo":
                if (inputArr.length == 1) {
                    System.out.println("The description of a todo cannot be empty!");
                    break;
                }
                for (int i = 0; i < inputArr.length; i++) {
                    if (i == 0)
                        continue;
                    taskString += inputArr[i] + " ";
                }
                TodoTask newTodoTask = new TodoTask(taskString);
                todoList.add(newTodoTask);
                System.out.println("    added: " + newTodoTask);
                System.out.println("    You now have " + todoList.size() + " tasks in the list.");
                break;
            case "deadline":
                if (inputArr.length == 1) {
                    System.out.println("The description of a deadline task cannot be empty!");
                    break;
                }
                String deadlineString = "";
                for (int i = 0; i < inputArr.length; i++) {
                    if (i == 0)
                        continue;
                    else if (inputArr[i].equals("/by")) {
                        recordTask = false;
                        continue;
                    }
                    if (recordTask) {
                        taskString += inputArr[i] + " ";
                    } else {
                        deadlineString += inputArr[i] + " ";
                    }
                }
                if (deadlineString.length() == 0) {
                    System.out.println("No deadline specified!");
                    break;
                }
                DeadlineTask newDeadlineTask = new DeadlineTask(taskString, deadlineString);
                todoList.add(newDeadlineTask);
                System.out.println("    added: " + newDeadlineTask);
                System.out.println("    You now have " + todoList.size() + " tasks in the list.");
                break;
            case "event":
                if (inputArr.length == 1) {
                    System.out.println("The description of a event task cannot be empty!");
                    break;
                }
                boolean recordFrom = true;
                String fromString = "";
                String toString = "";
                for (int i = 0; i < inputArr.length; i++) {
                    if (i == 0)
                        continue;
                    else if (inputArr[i].equals("/from")) {
                        recordTask = false;
                        continue;
                    } else if (inputArr[i].equals("/to")) {
                        recordFrom = false;
                        continue;
                    }
                    if (recordTask) {
                        taskString += inputArr[i] + " ";
                    } else if (recordFrom) {
                        fromString += inputArr[i] + " ";
                    } else {
                        toString += inputArr[i] + " ";
                    }
                }
                if (fromString.length() == 0) {
                    System.out.println("No from specified!");
                    break;
                }
                if (toString.length() == 0) {
                    System.out.println("No to specified!");
                    break;
                }
                try {
                    EventTask newEventTask = new EventTask(taskString, fromString, toString);
                    todoList.add(newEventTask);
                    System.out.println("    added: " + newEventTask);
                    System.out.println("    You now have " + todoList.size() + " tasks in the list.");
                } catch (DateTimeException e) {
                    continue;
                }
                break;
            case "delete":
                if (inputArr.length == 1) {
                    System.out.println("Task to delete has not been specified!");
                    break;
                } else if (inputArr.length > 2) {
                    System.out.println("Incorrect input!");
                    break;
                } else if (todoList.size() == 0) {
                    System.out.println("There aren't any tasks to delete!");
                    break;
                }
                int taskToDelete = Integer.parseInt(inputArr[1]);
                if (taskToDelete > todoList.size() || taskToDelete < 0) {
                    System.out.println("The task to delete does not exist!");
                    break;
                }
                System.out.println("I have removed this task: " + todoList.get(taskToDelete - 1));
                todoList.remove(taskToDelete - 1);
                System.out.println("You now have " + todoList.size() + " tasks in the list.");
                break;
            default:
                System.out.println("    Invalid command, you are wrong.");
                break;
            }
            System.out.println(divider);
        }
    }
}
