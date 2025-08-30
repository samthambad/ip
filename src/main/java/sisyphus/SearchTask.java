package sisyphus;

import java.util.ArrayList;

/**
 * Utility operations for searching/filtering tasks.
 */
public class SearchTask {
    /**
     * Returns a new TaskList containing tasks whose names contain the given query as a substring.
     * The comparison is case-sensitive and does a simple contains check.
     *
     * @param taskList the source list to filter
     * @param query the substring to search for in task names
     * @return a new TaskList with only the matching tasks (may be empty)
     */
    public static TaskList filterTasks(TaskList taskList, String query) {
        ArrayList<Task> filteredTasks = new ArrayList<>();
        for (Task t : taskList.getTasks()) {
            if (t.getName().contains(query)) {
                filteredTasks.add(t);
            }
        }
        return new TaskList(filteredTasks);
    }
}
