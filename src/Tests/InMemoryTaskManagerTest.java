package Tests;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.tasktracker.exeption_managers.ManagerSaveException;
import ru.yandex.practicum.tasktracker.task_managers.InMemoryTaskManager;
import ru.yandex.practicum.tasktracker.tasks.Task;

import static org.junit.jupiter.api.Assertions.assertThrows;


class InMemoryTaskManagerTest extends TasksManagerTest<InMemoryTaskManager> {

    @Override
    InMemoryTaskManager createManager() {
        return new InMemoryTaskManager();
    }

    @Test
    void shouldThrowException() {


        final ManagerSaveException exception = assertThrows(
                ManagerSaveException.class,
                () -> {
                    Task task1 = new Task("TASK", "TASK desc",
                            "10.02.23 12.00", 30);
                    manager.createTask(task1);
                    Task newTask = new Task("NEwNAme", "New desc",
                            "10.02.23 12.00", 24);
                    manager.createTask(newTask);
                });
    }


}