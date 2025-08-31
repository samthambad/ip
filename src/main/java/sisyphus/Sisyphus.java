package sisyphus;

import java.time.DateTimeException;
import java.util.Scanner;

/**
 * Entry point and top-level types for the Sisyphus task management application.
 */
public class Sisyphus {
    public static final String DATA_PATH = "data.txt";


    /**
     * Handles console I/O lifecycle for the application.
     */
    public static class Ui {
        /**
         * Initializes storage, loads tasks, prints the intro, and enters the REPL loop
         * reading commands until the user enters "bye".
         */
        public void init() {
            Storage storageManager = new Storage();
            TaskList todoList = storageManager.readFile(DATA_PATH);

            introMessage();
            String input = "";
            Scanner myObj = new Scanner(System.in);
            while (!input.equals("bye")) {
                input = myObj.nextLine();
                String[] inputArr = input.split(" ");
                System.out.println(divider);

                Parser p = new Parser();
                try {
                    p.readAndRespond(inputArr, storageManager, todoList);
                } catch (DateTimeException e) {
                    continue;
                }
                System.out.println(divider);
            }
        }

        /**
         * Prints all tasks in the given list to standard output, one per line, or a message
         * when the list is empty.
         *
         * @param todoList the list of tasks to display
         */
        public static void printTasks(TaskList todoList) {
            if (todoList.isEmpty()) {
                System.out.println("No tasks found");
            } else {
                for (int i = 1; i <= todoList.size(); i++) {
                    System.out.println("    " + i + "." + todoList.get(i));
                }
            }
        }
    }

    /**
     * Parses user input and performs actions on storage and the in-memory task list.
     */
    public static class Parser {
        /**
         * Parses a tokenized user command and updates storage and the task list accordingly.
         * Supported commands: bye, list, find, mark, unmark, todo, deadline, event, delete.
         *
         * @param inputArr        the tokenized input (split by spaces)
         * @param storageManager  the storage instance used for persistence on exit
         * @param todoList        the task list to operate on
         */
        public void readAndRespond(String[] inputArr, Storage storageManager, TaskList todoList) {
            boolean recordTask = true;
            String taskString = "";
            switch (inputArr[0]) {
            case "bye":
                if (!todoList.isEmpty()) {
                    System.out.println("You have tasks pending, type y/n whether to save.");
                    Scanner anotherScanner = new Scanner(System.in);
                    boolean whetherSave = anotherScanner.nextLine().equals("y");
                    if (whetherSave) {
                        storageManager.saveFile(todoList.getTasks(), DATA_PATH);
                    }
                }
                System.out.println("    See you!");
                break;
            case "list":
                Ui.printTasks(todoList);
                break;
            case "find":
                if (inputArr.length < 2 || inputArr.length > 3) {
                    System.out.println("Invalid input");
                    break;
                }
                System.out.println("Filtering based on query: " + inputArr[1]);
                System.out.println(divider);
                Ui.printTasks(SearchTask.filterTasks(todoList, inputArr[1]));
                break;
            case "mark":
                if (!inputArr[1].isEmpty() && Integer.parseInt(inputArr[1]) <= todoList.size()) {
                    int number = Integer.parseInt(inputArr[1]);
                    todoList.get(number).complete();
                    System.out.println("Okay, task " + number + " is done");
                    for (int i = 1; i <= todoList.size(); i++) {
                        System.out.println("    " + i + "." + todoList.get(i));
                    }
                } else {
                    System.out.println("Task " + inputArr[1] + " does not exist");
                }
                break;
            case "unmark":
                if (!inputArr[1].isEmpty() && Integer.parseInt(inputArr[1]) <= todoList.size()) {
                    int number = Integer.parseInt(inputArr[1]);
                    todoList.get(number).incomplete();
                    System.out.println("Okay, task " + number + " is not done yet");
                    for (int i = 1; i <= todoList.size(); i++) {
                        System.out.println("    " + i + "." + todoList.get(i));
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
                    if (i == 0) {
                        continue;
                    }
                    taskString += inputArr[i] + " ";
                }
                TodoTask newTodoTask = new TodoTask(taskString);
                todoList.addTask(newTodoTask);
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
                    if (i == 0) {
                        continue;
                    } else if (inputArr[i].equals("/by")) {
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
                todoList.addTask(newDeadlineTask);
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
                    if (i == 0) {
                        continue;
                    } else if (inputArr[i].equals("/from")) {
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
                if (fromString.isEmpty()) {
                    System.out.println("No from specified!");
                    break;
                }
                if (toString.isEmpty()) {
                    System.out.println("No to specified!");
                    break;
                }
                EventTask newEventTask = new EventTask(taskString, fromString, toString);
                todoList.addTask(newEventTask);
                System.out.println("    added: " + newEventTask);
                System.out.println("    You now have " + todoList.size() + " tasks in the list.");
                break;
            case "delete":
                if (inputArr.length == 1) {
                    System.out.println("Task to delete has not been specified!");
                    break;
                } else if (inputArr.length > 2) {
                    System.out.println("Incorrect input!");
                    break;
                } else if (todoList.isEmpty()) {
                    System.out.println("There aren't any tasks to delete!");
                    break;
                }
                int taskToDelete = Integer.parseInt(inputArr[1]);
                if (taskToDelete > todoList.size() || taskToDelete < 0) {
                    System.out.println("The task to delete does not exist!");
                    break;
                }
                System.out.println("I have removed this task: " + todoList.get(taskToDelete));
                todoList.removeTask(taskToDelete);
                System.out.println("You now have " + todoList.size() + " tasks in the list.");
                break;
            default:
                System.out.println("    Invalid command, you are wrong.");
                break;
            }
        }
    }

    private static final String divider = "-----------------------------";

    /**
     * Prints the application logo, welcome message, and usage instructions.
     */
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

    /**
     * Generates a response for the user's chat message.
     */
    public String getResponse(String input) {
        return "Sisyphus heard: " + input;
    }

    /**
     * Application entry point. Launches the UI loop.
     *
     * @param args CLI arguments (unused)
     */
    public static void main(String[] args) {
        Ui ui = new Ui();
        ui.init();
    }
}
