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
        assert task != null : "Cannot add null task";
        int oldSize = tasks.size();
        tasks.add(task);
        assert tasks.size() == oldSize + 1 : "Size should increase by 1 after add";
    }

    /**
     * Removes the task at the given 1-based position.
     *
     * @param number 1-based index of the task to remove
     */
    public void removeTask(int number) {
        assert number > 0 : "Index must be positive (1-based)";
        assert number <= tasks.size() : "Index out of bounds for remove";
        int oldSize = tasks.size();
        tasks.remove(number - 1);
        assert tasks.size() == oldSize - 1 : "Size should decrease by 1 after remove";
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
     * Returns the task at the given 1-based index.
     *
     * @param index 1-based index
     * @return the task at the index
     */
    public Task get(int index) {
        assert index > 0 : "Index must be positive (1-based)";
        assert index <= tasks.size() : "Index out of bounds for get";
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
