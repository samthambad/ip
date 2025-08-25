package sisyphus;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TaskListTest {

    @Test
    void newListIsEmpty() {
        TaskList list = new TaskList(new ArrayList<>());
        assertTrue(list.isEmpty());
        assertEquals(0, list.size());
    }

    @Test
    void addIncreasesSizeAndGetReturnsTask() {
        TaskList list = new TaskList(new ArrayList<>());
        TodoTask t1 = new TodoTask("read book");
        list.addTask(t1);

        assertFalse(list.isEmpty());
        assertEquals(1, list.size());
        assertSame(t1, list.get(1));
        assertEquals("[T] [ ] read book", list.get(1).toString());
    }

    @Test
    void addMultipleAndRemoveByOneBasedIndex() {
        TaskList list = new TaskList(new ArrayList<>());
        list.addTask(new TodoTask("task a"));
        list.addTask(new TodoTask("task b"));
        list.addTask(new TodoTask("task c"));
        assertEquals(3, list.size());

        // remove first (1-based)
        list.removeTask(1);
        assertEquals(2, list.size());
        assertEquals("[T] [ ] task b", list.get(1).toString());
        assertEquals("[T] [ ] task c", list.get(2).toString());

        // remove last (1-based relative to current size)
        list.removeTask(2);
        assertEquals(1, list.size());
        assertEquals("[T] [ ] task b", list.get(1).toString());
    }

    @Test
    void removeOutOfBoundsThrows() {
        TaskList list = new TaskList(new ArrayList<>());
        list.addTask(new TodoTask("only one"));
        assertThrows(IndexOutOfBoundsException.class, () -> list.removeTask(0)); // 0 is invalid (1-based)
        assertThrows(IndexOutOfBoundsException.class, () -> list.removeTask(2)); // beyond size
    }
}

