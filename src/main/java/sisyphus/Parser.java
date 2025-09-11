package sisyphus;

/**
 * Parses user input and performs actions on storage and the in-memory task list.
 */
public class Parser {
    // Command literals
    private static final String CMD_MANUAL = "manual";
    private static final String CMD_BYE = "bye";
    private static final String CMD_LIST = "list";
    private static final String CMD_SORTED = "latest";
    private static final String CMD_FIND = "find";
    private static final String CMD_MARK = "mark";
    private static final String CMD_UNMARK = "unmark";
    private static final String CMD_TODO = "todo";
    private static final String CMD_DEADLINE = "deadline";
    private static final String CMD_EVENT = "event";
    private static final String CMD_DELETE = "delete";

    // Common messages
    private static final String MSG_INVALID = "Invalid command, you are wrong.";
    private static final String MSG_MISSING_DESC_TODO = "The description of a todo cannot be empty!\n";
    private static final String MSG_MISSING_DESC_DEADLINE = "The description of a deadline task cannot be empty!\n";
    private static final String MSG_MISSING_DESC_EVENT = "The description of a event task cannot be empty!";
    private static final String MSG_MISSING_DEADLINE = "No deadline specified!\n";
    private static final String MSG_MISSING_FROM = "No from specified!";
    private static final String MSG_MISSING_TO = "No to specified!";
    private static final String MSG_TASK_NOT_FOUND_PREFIX = "Task ";
    private static final String MSG_TASK_NOT_FOUND_SUFFIX = " does not exist";
    private static final String MSG_DELETE_NOT_SPECIFIED = "Task to delete has not been specified!\n";
    private static final String MSG_DELETE_INCORRECT = "Incorrect input!\n";
    private static final String MSG_DELETE_EMPTY = "There aren't any tasks to delete!\n";
    private static final String MSG_DELETE_NOT_EXIST = "The task to delete does not exist!\n";

    /**
     * Parses a tokenized user command and updates storage and the task list accordingly.
     * Supported commands: bye, list, find, mark, unmark, todo, deadline, event, delete.
     *
     * @param tokens         the tokenized input (split by spaces)
     * @param storageManager the storage instance used for persistence on exit
     * @param todoList       the task list to operate on
     * @return response message
     */
    public String readAndRespond(String[] tokens, Storage storageManager, TaskList todoList) {
        if (tokens == null || tokens.length == 0 || tokens[0].isBlank()) {
            return MSG_INVALID;
        }
        String command = tokens[0];
        return switch (command) {
        case CMD_MANUAL -> handleManual();
        case CMD_BYE -> handleBye(storageManager, todoList);
        case CMD_LIST -> Sisyphus.Ui.printTasks(todoList);
        case CMD_SORTED -> Sisyphus.Ui.printTasksByLatestFirst(todoList);
        case CMD_FIND -> handleFind(tokens, todoList);
        case CMD_MARK -> handleMark(tokens, todoList, true);
        case CMD_UNMARK -> handleMark(tokens, todoList, false);
        case CMD_TODO -> handleTodo(tokens, todoList);
        case CMD_DEADLINE -> handleDeadline(tokens, todoList);
        case CMD_EVENT -> handleEvent(tokens, todoList);
        case CMD_DELETE -> handleDelete(tokens, todoList);
        default -> MSG_INVALID;
        };
    }

    /** Manual help text. */
    private String handleManual() {
        return """
                todo <task> for a simple todo
                deadline <task> /by <time> for a time that ends by a certain time
                event <task> /from <time> /to <time> for a time that ends by a certain time
                list to show all tasks
                latest to show all tasks from earliest to latest deadline/starting time""";
    }

    /** Handles bye command including saving tasks if any exist. */
    private String handleBye(Storage storageManager, TaskList todoList) {
        StringBuilder sb = new StringBuilder();
        if (!todoList.isEmpty()) {
            storageManager.saveFile(todoList.getTasks(), Sisyphus.DATA_PATH);
            sb.append("Saved.\n");
        }
        sb.append("    See you!");
        return sb.toString();
    }

    /** Handles find command. */
    private String handleFind(String[] tokens, TaskList todoList) {
        if (tokens.length < 2) {
            return "Invalid input"; // Keep legacy wording
        }
        String query = tokens[1]; // original implementation used only token[1]
        StringBuilder sb = new StringBuilder();
        sb.append("Filtering based on query: ").append(query).append("\n");
        sb.append(Sisyphus.Ui.divider);
        sb.append(Sisyphus.Ui.printTasks(SearchTask.filterTasks(todoList, query)));
        return sb.toString();
    }

    /** Handles mark/unmark logic. */
    private String handleMark(String[] tokens, TaskList todoList, boolean mark) {
        if (tokens.length < 2) {
            return MSG_TASK_NOT_FOUND_PREFIX + "?" + MSG_TASK_NOT_FOUND_SUFFIX; // ambiguous input
        }
        int index;
        try {
            index = Integer.parseInt(tokens[1]);
        } catch (NumberFormatException e) {
            return MSG_TASK_NOT_FOUND_PREFIX + tokens[1] + MSG_TASK_NOT_FOUND_SUFFIX;
        }
        if (index < 1 || index > todoList.size()) {
            return MSG_TASK_NOT_FOUND_PREFIX + tokens[1] + MSG_TASK_NOT_FOUND_SUFFIX;
        }
        if (mark) {
            todoList.get(index).complete();
        } else {
            todoList.get(index).incomplete();
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Okay, task ").append(index).append(mark ? " is done\n" : " is not done yet\n");
        sb.append(Sisyphus.Ui.printTasks(todoList));
        return sb.toString();
    }

    /** Handles todo command. */
    private String handleTodo(String[] tokens, TaskList todoList) {
        if (tokens.length == 1) {
            return MSG_MISSING_DESC_TODO;
        }
        String description = join(tokens, 1, tokens.length).trim();
        TodoTask newTodoTask = new TodoTask(description);
        todoList.addTask(newTodoTask);
        return buildAddResponse(newTodoTask, todoList.size(), false);
    }

    /** Handles deadline command. */
    private String handleDeadline(String[] tokens, TaskList todoList) {
        if (tokens.length == 1) {
            return MSG_MISSING_DESC_DEADLINE;
        }
        int byIndex = indexOf(tokens, "/by");
        if (byIndex == -1) {
            return MSG_MISSING_DEADLINE; // legacy message
        }
        String description = join(tokens, 1, byIndex).trim();
        String deadline = join(tokens, byIndex + 1, tokens.length).trim();
        if (deadline.isEmpty()) {
            return MSG_MISSING_DEADLINE;
        }
        DeadlineTask dt = new DeadlineTask(description + " ", deadline + " "); // preserve spacing legacy
        todoList.addTask(dt);
        return buildAddResponse(dt, todoList.size(), false);
    }

    /** Handles event command. */
    private String handleEvent(String[] tokens, TaskList todoList) {
        if (tokens.length == 1) {
            return MSG_MISSING_DESC_EVENT;
        }
        int fromIdx = indexOf(tokens, "/from");
        int toIdx = indexOf(tokens, "/to");
        if (fromIdx == -1) {
            return MSG_MISSING_FROM;
        }
        if (toIdx == -1 || toIdx < fromIdx) {
            return MSG_MISSING_TO;
        }
        String description = join(tokens, 1, fromIdx).trim();
        String fromString = join(tokens, fromIdx + 1, toIdx).trim();
        String toString = join(tokens, toIdx + 1, tokens.length).trim();
        if (fromString.isEmpty()) {
            return MSG_MISSING_FROM;
        }
        if (toString.isEmpty()) {
            return MSG_MISSING_TO;
        }
        EventTask et = new EventTask(description + " ", fromString + " ", toString + " ");
        todoList.addTask(et);
        return buildAddResponse(et, todoList.size(), true);
    }

    /** Handles delete command. */
    private String handleDelete(String[] tokens, TaskList todoList) {
        if (tokens.length == 1) {
            return MSG_DELETE_NOT_SPECIFIED;
        }
        if (tokens.length > 2) {
            return MSG_DELETE_INCORRECT;
        }
        if (todoList.isEmpty()) {
            return MSG_DELETE_EMPTY;
        }
        int index;
        try {
            index = Integer.parseInt(tokens[1]);
        } catch (NumberFormatException e) {
            return MSG_DELETE_NOT_EXIST;
        }
        if (index < 1 || index > todoList.size()) {
            return MSG_DELETE_NOT_EXIST;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("I have removed this task: ").append(todoList.get(index));
        todoList.removeTask(index);
        sb.append("You now have ").append(todoList.size()).append(" tasks in the list.\n");
        return sb.toString();
    }

    /** Builds the standard add-task response. */
    private String buildAddResponse(Task task, int size, boolean indent) {
        String prefix = indent ? "    " : "";
        StringBuilder sb = new StringBuilder();
        sb.append(prefix).append("added: ").append(task).append("\n");
        sb.append(prefix).append("You now have ").append(size).append(" tasks in the list.\n");
        return sb.toString();
    }

    /** Returns index of token, or -1. */
    private int indexOf(String[] tokens, String target) {
        for (int i = 0; i < tokens.length; i++) {
            if (target.equals(tokens[i])) {
                return i;
            }
        }
        return -1;
    }

    /** Joins tokens from start (inclusive) to end (exclusive) separated by spaces. */
    private String join(String[] tokens, int start, int end) {
        if (start >= end) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = start; i < end; i++) {
            sb.append(tokens[i]);
            if (i < end - 1) {
                sb.append(' ');
            }
        }
        return sb.toString();
    }
}
