package sisyphus;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Handles persistence of tasks to and from a simple text file in the project directory.
 * The file format stores task type, completion flag, and task-specific fields separated by " | ".
 */
public class Storage {
    private final String DATA_PATH = "data.txt";

    /**
     * Saves todolist to data.txt in the DATA_PATH specified
     *
     * @param listToSave todoList
     * @see data.txt
     */
    public void saveFile(TaskList listToSave) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(DATA_PATH))) {
            for (Task t : listToSave.getTasks()) {
                String taskName = t.getName();
                int isDone = t.isDone() ? 1 : 0;

                String taskType = t.getClass().getSimpleName();
                switch (taskType) {
                case "TodoTask":
                    writer.println("T | " + isDone + " | " + taskName);
                    break;
                case "DeadlineTask":
                    DeadlineTask dt = (DeadlineTask) t;
                    writer.println("D | " + isDone + " | " + taskName + " | " + dt.getDeadlineString());
                    break;
                case "EventTask":
                    EventTask et = (EventTask) t;
                    writer.println("E | " + isDone + " | " + taskName + " | " + et.getStartString() + " | " + et.getEndString());
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("Error saving file: "+ e.getMessage());
        }
        System.out.println("Tasks saved.");
    }

    /**
     * Takes data.txt from DATA_PATH and converts it to TaskList to be used
     *
     * @return todoList
     */
    public TaskList readFile() {
        ArrayList<Task> todoList = new ArrayList<>();
        try (Scanner fileReader = new Scanner(new File(DATA_PATH))) {
            while (fileReader.hasNextLine()) {
                String line = fileReader.nextLine();
                String[] parts = line.split(" \\| ");
                if (parts.length < 3) {
                    continue;
                }
                String taskType = parts[0];
                boolean isDone = parts[1].equals("1");
                String taskName = parts[2];
                Task task = null;
                switch (taskType) {
                case "T":
                    task = new TodoTask(taskName);
                    break;
                case "D":
                    task = new DeadlineTask(taskName, parts[3]);
                    break;
                case "E":
                    task = new EventTask(taskName, parts[3], parts[4]);
                    break;
                }
                if (task != null) {
                    if (isDone) {
                        task.complete();
                    }
                    todoList.add(task);
                }
            }
            System.out.println(todoList.size() + " tasks loaded.");
        } catch (FileNotFoundException e) {
            System.out.println("No file found, starting fresh!");
        } catch (Exception e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return new TaskList(todoList);
    }
}
