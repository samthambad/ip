package sisyphus;

import java.util.ArrayList;

public class SearchTask {
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
