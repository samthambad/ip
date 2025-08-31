package sisyphus;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;


public class StorageTest {
    @Test
    public void writeAndReadTest() {
        // write to a data.txt file
        final String DATA_PATH = "data.txt";
        ArrayList<Task> tasks = new ArrayList<>();
        EventTask et = new EventTask("project meeting", "2024-10-01 14:00", "2024-10-01 16:00");
        tasks.add(et);
        Storage storage = new Storage();
        storage.saveFile(tasks, DATA_PATH);
        // read from the data.txt file
        ArrayList<Task> readTasks = storage.readFile(DATA_PATH).getTasks();
        // compare the two ArrayLists
        assertEquals(tasks.size(), readTasks.size());
        for (int i = 0; i < tasks.size(); i++) {
            assertEquals(tasks.get(i).toString(), readTasks.get(i).toString());
        }
        // delete data.txt
        File deleteData = new File(DATA_PATH);
        if (deleteData.exists()) { // Check if the file exists before attempting deletion
            deleteData.delete();
        }
    }
}
