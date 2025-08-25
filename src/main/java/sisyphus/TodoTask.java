package sisyphus;

public class TodoTask extends Task {

    public TodoTask(String name) {
        super(name);
    }

    public String toString() {
        return "[T] " + super.toString();
    }
}
