package sisyphus;


/**
 * Entry point and top-level types for the Sisyphus task management application.
 */
public class Sisyphus {
    public static final String DATA_PATH = "data.txt";
    private static final String divider = "-----------------------------";
    private Storage storageManager;
    private TaskList todoList;
    private Parser parser;
    private Ui ui;

    /**
     * Constructor
     */
    public Sisyphus() {
        this.storageManager = new Storage();
        this.todoList = this.storageManager.readFile(DATA_PATH);
        this.parser = new Parser();
        this.ui = new Ui();
    }

    /**
     * Calls the Parser to send the user input
     * @param input user input from GUI
     * @return Generated response by Sisyphus's Parser
     */
    public String enterInput(String input) {
        String[] inputArr = input.split(" ");
        return parser.readAndRespond(inputArr, storageManager, todoList);
    }
    /**
     * Handles console I/O lifecycle for the application.
     */
    public static class Ui {

        public Ui() {
            introMessage();
        }

        /**
         * Prints the application logo, welcome message, and usage instructions.
         */
        public void introMessage() {
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
         * Returns all tasks in the given list to as string, one per line, or a message
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
     * Sisyphus output to GUI.
     */
    public String getResponse(String input) {
        // generate a response
        return input;
    }


}
