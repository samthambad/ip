package sisyphus;

import java.util.ArrayList;

/**
 * A simple wrapper around an ArrayList of tasks that provides basic operations
 * used by the application (add, remove, access, and size checks).
 */
public class TaskList {
    private ArrayList<Task> tasks;

    /**
     * Creates a TaskList backed by the provided task list.
     *
     * @param tasks the initial list of tasks
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Adds a task to the end of the list.
     *
     * @param task the task to add
     */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Removes the task at the given 1-based position.
     *
     * @param number 1-based index of the task to remove
     */
    public void removeTask(int number) {
        tasks.remove(number - 1);
    }

    /**
     * Returns whether the list has no tasks.
     *
     * @return true if empty, otherwise false
     */
    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return size of the list
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Returns the task at the given 0-based index.
     *
     * @param index 0-based index
     * @return the task at the index
     */
    public Task get(int index) {
        return tasks.get(index - 1);
    }

    /**
     * Returns the underlying mutable task list.
     *
     * @return the tasks backing this TaskList
     */
    public ArrayList<Task> getTasks() {
        return tasks;
    }
}
