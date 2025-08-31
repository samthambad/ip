package sisyphus;

/**
 * Parses user input and performs actions on storage and the in-memory task list.
 */
public class Parser {
    /**
     * Parses a tokenized user command and updates storage and the task list accordingly.
     * Supported commands: bye, list, find, mark, unmark, todo, deadline, event, delete.
     *
     * @param inputArr       the tokenized input (split by spaces)
     * @param storageManager the storage instance used for persistence on exit
     * @param todoList       the task list to operate on
     */
    public String readAndRespond(String[] inputArr, Storage storageManager, TaskList todoList) {
        StringBuilder output = new StringBuilder();
        boolean recordTask = true;
        String taskString = "";
        switch (inputArr[0]) {
        case "bye":
            if (!todoList.isEmpty()) {
                storageManager.saveFile(todoList.getTasks(), Sisyphus.DATA_PATH);
                output.append("    Saved.\n");
            }
            output.append("    See you!");
            return output.toString();
        case "list":
            return Sisyphus.Ui.printTasks(todoList);
        case "find":
            if (inputArr.length < 2 || inputArr.length > 3) {
                output.append("Invalid input");
                break;
            }
            output.append("Filtering based on query: ").append(inputArr[1]).append("\n");
            output.append(Sisyphus.Ui.divider);
            output.append(Sisyphus.Ui.printTasks(SearchTask.filterTasks(todoList, inputArr[1])));
            return output.toString();
        case "mark":
            if (!inputArr[1].isEmpty() && Integer.parseInt(inputArr[1]) <= todoList.size()) {
                int number = Integer.parseInt(inputArr[1]);
                todoList.get(number).complete();
                output.append("Okay, task ").append(number).append(" is done\n");
                output.append(Sisyphus.Ui.printTasks(SearchTask.filterTasks(todoList, inputArr[1])));
            } else {
                output.append("Task ").append(inputArr[1]).append(" does not exist");
            }
            return output.toString();
        case "unmark":
            if (!inputArr[1].isEmpty() && Integer.parseInt(inputArr[1]) <= todoList.size()) {
                int number = Integer.parseInt(inputArr[1]);
                todoList.get(number).incomplete();
                output.append("Okay, task ").append(number).append(" is not done yet\n");
                output.append(Sisyphus.Ui.printTasks(SearchTask.filterTasks(todoList, inputArr[1])));
            } else {
                output.append("Task ").append(inputArr[1]).append(" does not exist");
            }
            return output.toString();
        case "todo":
            if (inputArr.length == 1) {
                output.append("The description of a todo cannot be empty!\n");
                return output.toString();
            }
            for (int i = 0; i < inputArr.length; i++) {
                if (i == 0) {
                    continue;
                }
                taskString += inputArr[i] + " ";
            }
            TodoTask newTodoTask = new TodoTask(taskString);
            todoList.addTask(newTodoTask);
            output.append("    added: ").append(newTodoTask).append("\n");
            output.append("    You now have ").append(todoList.size()).append(" tasks in the list.\n");
            return output.toString();
        case "deadline":
            if (inputArr.length == 1) {
                output.append("The description of a deadline task cannot be empty!\n");
                return output.toString();
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
            if (deadlineString.isEmpty()) {
                output.append("No deadline specified!\n");
                return output.toString();
            }
            DeadlineTask newDeadlineTask = new DeadlineTask(taskString, deadlineString);
            todoList.addTask(newDeadlineTask);
            output.append("    added: ").append(newDeadlineTask).append("\n");
            output.append("    You now have ").append(todoList.size()).append(" tasks in the list.\n");
            return output.toString();
        case "event":
            if (inputArr.length == 1) {
                output.append("The description of a event task cannot be empty!");
                return output.toString();
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
                output.append("No from specified!");
                return output.toString();
            }
            if (toString.isEmpty()) {
                output.append("No to specified!");
                return output.toString();
            }
            EventTask newEventTask = new EventTask(taskString, fromString, toString);
            todoList.addTask(newEventTask);
            output.append("    added: ").append(newEventTask).append("\n");
            output.append("    You now have ").append(todoList.size()).append(" tasks in the list.\n");
            return output.toString();
        case "delete":
            if (inputArr.length == 1) {
                output.append("Task to delete has not been specified!\n");
                return output.toString();
            } else if (inputArr.length > 2) {
                output.append("Incorrect input!\n");
                return output.toString();
            } else if (todoList.isEmpty()) {
                output.append("There aren't any tasks to delete!\n");
                return output.toString();
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
            output.append("    Invalid command, you are wrong.");
            return output.toString();
        }
        return output.toString();
    }
}
