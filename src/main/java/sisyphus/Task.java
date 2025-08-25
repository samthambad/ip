package sisyphus;

public class Task {
    private String name;
    private Boolean isDone = false;

    public Task(String name) {
        this.name = name;
    }

    public boolean isDone() {
        return this.isDone;
    }

    public String getName() {
        return this.name;
    }

    public String toString() {
        if (isDone) {
            return "[X] " + this.name;
        } else {
            return "[ ] " + this.name;
        }
    }

    public void complete() {
        this.isDone = true;
    }

    public void incomplete() {
        this.isDone = false;
    }
}