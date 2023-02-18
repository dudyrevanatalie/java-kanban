package Tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.tasktracker.server.KVServer;
import ru.yandex.practicum.tasktracker.task_managers.HttpTaskManager;
import ru.yandex.practicum.tasktracker.tasks.Epic;
import ru.yandex.practicum.tasktracker.tasks.Subtask;
import ru.yandex.practicum.tasktracker.tasks.Task;
import ru.yandex.practicum.tasktracker.tasks.TaskStatus;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpTaskManagerTest extends TasksManagerTest<HttpTaskManager> {
    KVServer kvServer;

    @Override
    HttpTaskManager createManager() throws IOException {
        return new HttpTaskManager("http://localhost");
    }

    @BeforeEach
    public void beforeEach() {
        try {
            kvServer = new KVServer();
            kvServer.start();
            manager = createManager();
        } catch (IOException e) {
            System.out.println("Error at starting kvserver");
        }
    }

    @AfterEach
    public void stopKvServer() {
        kvServer.stop();
    }

    @Test
    public void testLoadingStateFromKVServerToOtherManager() {
        Task task = new Task("Title", "Desc", "30.01.23 09.00", 45);
        Epic epic = new Epic("Epic title", "Epic description");
        Subtask subtask = new Subtask("Subtask title", "Subtask description", "31.01.23 10.00", 15, epic.getId());

        manager.createEpic(epic);
        manager.createTask(task);
        manager.createSubtask(subtask);


        manager.getFromIdSubtask(subtask.getId());
        manager.getFromIdTask(task.getId());

        HttpTaskManager manager2 = HttpTaskManager.load();

        assertEquals(task, manager2.getFromIdTask(task.getId()));
        assertEquals(subtask, manager2.getFromIdSubtask(subtask.getId()));
        assertEquals(epic, manager2.getFromIdEpic(epic.getId()));
    }

    @Test
    public void testLoadingHistoryFromKVServerToOtherManager() {
        Task task = new Task("Title", "Desc", "30.01.23 09.00", 45);
        Epic epic = new Epic("Epic title", "Epic description");
        Subtask subtask = new Subtask("Subtask title", "Subtask description", "31.01.23 10.00", 15, epic.getId());

        manager.createEpic(epic);
        manager.createTask(task);
        manager.createSubtask(subtask);


        manager.getFromIdSubtask(subtask.getId());
        manager.getFromIdTask(task.getId());

        HttpTaskManager manager2 = HttpTaskManager.load();
        assertArrayEquals(manager.getHistory().toArray(), manager2.getHistory().toArray());
    }

    @Test
    public void getPrioritizedTaskTest() {
        Task task = new Task("Title", "Desc", "30.01.23 09.00", 45);
        Task task1 = new Task("Title", "Desc", "02.02.23 11.00", 15);
        Task task2 = new Task("Title", "Desc", "15.01.23 23.00", 60);
        Epic epic = new Epic("Epic title", "Epic description");
        Subtask subtask = new Subtask("Subtask title", "Subtask description", "31.01.23 10.00", 15, epic.getId());


        manager.createTask(task);
        manager.createTask(task1);
        manager.createTask(task2);

        manager.createEpic(epic);
        manager.createSubtask(subtask);


        manager.getFromIdSubtask(subtask.getId());
        manager.getFromIdTask(task.getId());

        HttpTaskManager manager2 = HttpTaskManager.load();

        assertArrayEquals(manager.getPrioritizedTasks().toArray(), manager2.getPrioritizedTasks().toArray());
    }

}

