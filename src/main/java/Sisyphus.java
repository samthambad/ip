import java.util.Scanner;
import java.util.ArrayList;

public class Sisyphus {
    public static class Task {
        private String name;
        private Boolean isDone = false;

        public Task(String name) {
            this.name = name;
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
        private String deadline;

        public DeadlineTask(String name, String deadline) {
            super(name);
            this.deadline = deadline;
        }

        public String toString() {
            return "[D] " + super.toString() + " ( by:" + this.deadline + ")";
        }
    }

    public static class EventTask extends Task {
        private String start;
        private String end;

        public EventTask(String name, String start, String end) {
            super(name);
            this.start = start;
            this.end = end;
        }

        public String toString() {
            return "[E] " + super.toString() + " ( from: " + this.start + " to: " + this.end + ")";
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
        System.out.println(logo);
        System.out.println(welcomeMessage);
        System.out.println(divider);
    }

    public static void main(String[] args) {
        introMessage();
        ArrayList<Task> todoList = new ArrayList<>();
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
                    DeadlineTask newDeadlineTask = new DeadlineTask(taskString, deadlineString);
                    todoList.add(newDeadlineTask);
                    System.out.println("    added: " + newDeadlineTask);
                    System.out.println("    You now have " + todoList.size() + " tasks in the list.");
                    break;
                case "event":
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
                    EventTask newEventTask = new EventTask(taskString, fromString, toString);
                    todoList.add(newEventTask);
                    System.out.println("    added: " + newEventTask);
                    System.out.println("    You now have " + todoList.size() + " tasks in the list.");
                    break;
                default:
                    if (input.length() == 0)
                        break;
                    Task newTask = new Task(input);
                    todoList.add(newTask);
                    System.out.println("    added: " + input);
                    break;
            }
            System.out.println(divider);
        }
    }
}
