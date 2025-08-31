package sisyphus;

import java.time.DateTimeException;

/**
 * Entry point and top-level types for the Sisyphus task management application.
 */
public class Sisyphus {
    public static final String DATA_PATH = "data.txt";
    private static final String divider = "-----------------------------";
    private static boolean isInitialized = false;
    private Storage storageManager;
    private TaskList todoList;

    public void initStorage() {
        this.storageManager = new Storage();
    }

    public void initTodoList() {
        this.todoList = this.storageManager.readFile(DATA_PATH);
    }

    /**
     * Handles console I/O lifecycle for the application.
     */
    public static class Ui {
        /**
         * Initializes storage, loads tasks, prints the intro, and enters the REPL loop
         * reading commands until the user enters "bye".
         */
        public String init() {

            Parser p = new Parser();
            while (!input.equals("bye")) {
                String[] inputArr = input.split(" ");
                System.out.println(divider);

                try {
                    return p.readAndRespond(inputArr, todoList);
                } catch (DateTimeException e) {
                    continue;
                }
            }
            return null;
        }

        /**
         * Prints all tasks in the given list to standard output, one per line, or a message
         * when the list is empty.
         *
         * @param todoList the list of tasks to display
         */
        public static String printTasks(TaskList todoList) {
            StringBuilder sb = new StringBuilder();
            if (todoList.isEmpty()) {
                return "No tasks found";
            } else {
                for (int i = 1; i <= todoList.size(); i++) {
                    sb.append("    ").append(i).append(".").append(todoList.get(i)).append("\n");
                }
            }
            return sb.toString();
        }
    }


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
     * Sisyphus output to GUI.
     */
    public String getResponse(String input) {
        // generate a response
        Ui ui = null;
        if (!isInitialized) {
            this.initStorage();
            this.initTodoList();
            introMessage();
            ui = new Ui();
            isInitialized = true;
        }
        assert ui != null;
        ui.init(input);
        return input;
    }


    /**
     * Application entry point. Launches the UI loop.
     *
     * @param args CLI arguments (unused)
     */
    public static void main(String[] args) {
    }
}
