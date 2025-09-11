package sisyphus;

/**
 * Parses user input and performs actions on storage and the in-memory task list.
 */
public class Parser {
    // Command literals
    private static final String CMD_MANUAL = "manual";
    private static final String CMD_BYE = "bye";
    private static final String CMD_LIST = "list";
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
        switch (command) {
        case CMD_MANUAL:   return handleManual();
        case CMD_BYE:      return handleBye(storageManager, todoList);
        case CMD_LIST:     return Sisyphus.Ui.printTasks(todoList);
        case CMD_FIND:     return handleFind(tokens, todoList);
        case CMD_MARK:     return handleMark(tokens, todoList, true);
        case CMD_UNMARK:   return handleMark(tokens, todoList, false);
        case CMD_TODO:     return handleTodo(tokens, todoList);
        case CMD_DEADLINE: return handleDeadline(tokens, todoList);
        case CMD_EVENT:    return handleEvent(tokens, todoList);
        case CMD_DELETE:   return handleDelete(tokens, todoList);
        default:           return MSG_INVALID;
        }
    }

            }
            int taskToDelete = Integer.parseInt(inputArr[1]);
            if (taskToDelete > todoList.size() || taskToDelete < 0) {
                output.append("The task to delete does not exist!\n");
                return output.toString();
            }
            output.append("I have removed this task: ").append(todoList.get(taskToDelete));
            todoList.removeTask(taskToDelete);
            output.append("You now have ").append(todoList.size()).append(" tasks in the list.\n");
            return output.toString();
        default:
            output.append("Invalid command, you are wrong.");
            return output.toString();
        }
        return output.toString();
    }
}
