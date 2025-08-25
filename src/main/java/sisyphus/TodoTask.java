package sisyphus;

/**
 * Represents a simple to-do task with only a description and completion status.
 */
public class TodoTask extends Task {

    /**
     * Creates a new to-do task with the given description.
     *
     * @param name the task description
     */
    public TodoTask(String name) {
        super(name);
    }

    /**
     * Returns the formatted string representation with the to-do tag.
     *
     * @return the display string for this to-do task
     */
    public String toString() {
        return "[T] " + super.toString();
    }
}
