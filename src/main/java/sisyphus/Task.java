package sisyphus;

/**
 * Represents a generic task with a name and completion status.
 */
public class Task {
    private String name;
    private Boolean isDone = false;

    /**
     * Creates a new task with the given name. The task is initially not done.
     *
     * @param name the description/name of the task
     */
    public Task(String name) {
        this.name = name;
    }

    /**
     * Returns whether this task has been marked as done.
     *
     * @return true if done, false otherwise
     */
    public boolean isDone() {
        return this.isDone;
    }

    /**
     * Returns the name/description of this task.
     *
     * @return the task name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns a human-readable representation including completion status and name.
     *
     * @return the formatted string for display
     */
    public String toString() {
        if (isDone) {
            return "[X] " + this.name;
        } else {
            return "[ ] " + this.name;
        }
    }

    /**
     * Marks this task as completed.
     */
    public void complete() {
        this.isDone = true;
    }

    /**
     * Marks this task as not completed.
     */
    public void incomplete() {
        this.isDone = false;
    }
}