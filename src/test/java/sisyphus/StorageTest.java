package sisyphus;

import sisyphus.EventTask;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class StorageTest {
    @Test
    public void writeAndReadTest() {
        // write to a data.txt file
        ArrayList<Task> tasks = new ArrayList<>();
        EventTask et = new EventTask("project meeting", "2024-10-01 14:00", "2024-10-01 16:00");
        tasks.add(et);
        Storage storage = new Storage();
        storage.saveFile(tasks);
        // read from the data.txt file
        ArrayList<Task> readTasks = storage.readFile();
        // compare the two ArrayLists
        assertEquals(tasks.size(), readTasks.size());
        for (int i = 0; i < tasks.size(); i++) {
            assertEquals(tasks.get(i).toString(), readTasks.get(i).toString());
        }
    }
}