package Tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.tasktracker.history_managers.HistoryManager;
import ru.yandex.practicum.tasktracker.history_managers.InMemoryHistoryManager;
import ru.yandex.practicum.tasktracker.tasks.Task;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {
    HistoryManager historyManager;

    @BeforeEach
    public void beforeEach() {
        historyManager = new InMemoryHistoryManager();
    }

    @Test
    public void testGetHistoryWithTasks() {
        Task task1 = new Task("TASK", "TASK desc",
                "10.02.23 12.00", 30);
        Task task2 = new Task("TASK2", "TASK2 desc",
                "11.02.23 12.00", 40);
        task1.setId(1);
        task2.setId(2);
        historyManager.add(task1);
        historyManager.add(task2);
        List<Task> checkHist = new ArrayList<>();
        checkHist.add(task1);
        checkHist.add(task2);
        assertEquals(checkHist, historyManager.getHistory());
    }

    @Test
    public void testRemoveTaskFromHistory() {
        Task task1 = new Task("TASK", "TASK desc",
                "10.02.23 12.00", 30);
        Task task2 = new Task("TASK2", "TASK2 desc",
                "11.02.23 12.00", 40);
        task1.setId(1);
        task2.setId(2);
        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.remove(1);
        List<Task> checkList = new ArrayList<>();
        checkList.add(task2);
        assertEquals(checkList, historyManager.getHistory());
    }

    @Test
    public void testGetHistoryWhenHistoryIsEmpty() {
        assertNull(historyManager.getHistory());
    }

    @Test
    public void testAddEqualTaskAndRemoveDouble() {
        Task task1 = new Task("TASK", "TASK desc",
                "10.02.23 12.00", 30);
        Task task2 = new Task("TASK2", "TASK2 desc",
                "11.02.23 12.00", 40);
        task1.setId(1);
        task2.setId(2);
        historyManager.add(task1);
        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(task1);
        historyManager.add(task1);
        List<Task> checkList = new ArrayList<>();
        checkList.add(task2);
        checkList.add(task1);
        assertEquals(checkList, historyManager.getHistory());
    }


}