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
