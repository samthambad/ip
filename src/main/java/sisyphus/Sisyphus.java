package sisyphus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Entry point and top-level types for the Sisyphus task management application.
 */
public class Sisyphus {
    public static final String DATA_PATH = "data.txt";
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
        static final String divider = "-----------------------------\n";

        /**
         * Prints the application logo, welcome message, and usage instructions.
         */
        public static String introMessage() {
            StringBuilder initMessage = new StringBuilder();
            String welcomeMessage = "Hello, I am Sisyphus, what can I do for you?";
            String manualInstructions = "type 'manual' to see a list of the commands";
            String instructions = "Enter date in yyyy-MM-dd format and date and time in yyyy-MM-dd HH:mm";
            initMessage.append(welcomeMessage).append("\n");
            initMessage.append(manualInstructions).append("\n");
            initMessage.append(instructions).append("\n");
            initMessage.append(divider);
            return initMessage.toString();
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
            }
            for (int i = 1; i <= todoList.size(); i++) {
                sb.append("    ").append(i).append(".").append(todoList.get(i)).append("\n");
            }
            return sb.toString();
        }

        /**
         * Print the tasks with the earliest DeadlineTask or earliest from time for the EventTask
         * @param todoList the list of tasks to display
         *
         */
        public static String printTasksByLatestFirst(TaskList todoList) {
            StringBuilder sb = new StringBuilder();
            if (todoList.isEmpty()) {
                return "No tasks found";
            }
            // Use Java streams to sort the tasks by time
            ArrayList<Task> sortedTaskArrayList = todoList.getTasks().stream().sorted((t1, t2) -> {
                LocalDateTime t1Time = (t1 instanceof DeadlineTask) ? ((DeadlineTask) t1).getDeadline()
                        : (t1 instanceof EventTask) ? ((EventTask) t1).getStart() : null;

                LocalDateTime t2Time = (t2 instanceof DeadlineTask) ? ((DeadlineTask) t2).getDeadline()
                        : (t2 instanceof EventTask) ? ((EventTask) t2).getStart() : null;
                if (t1Time == null && t2Time == null) {
                    return 0;
                } else if (t1Time == null) {
                    return 1; // t1 (plain Task) goes to end
                } else if (t2Time == null) {
                    return -1; // t2 (plain Task) goes to end
                }
                return t1Time.compareTo(t2Time);
            }).collect(Collectors.toCollection(ArrayList::new));
            TaskList sortedTaskList = new TaskList(sortedTaskArrayList);
            return printTasks(sortedTaskList);
        }
    }

    /**
     * Sisyphus output to GUI.
     */
    public String getResponse(String input) {
        // generate a response
        return this.enterInput(input);
    }


}
