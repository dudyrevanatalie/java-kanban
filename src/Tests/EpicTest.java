package Tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.tasktracker.task_managers.InMemoryTaskManager;
import ru.yandex.practicum.tasktracker.task_managers.TasksManager;
import ru.yandex.practicum.tasktracker.tasks.Epic;
import ru.yandex.practicum.tasktracker.tasks.Subtask;
import ru.yandex.practicum.tasktracker.tasks.TaskStatus;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {
    Epic epic;
    TasksManager tasksManager;

    @BeforeEach
    public void beforeEach() {
        epic = new Epic("epicName", "EpicDescription");
        tasksManager = new InMemoryTaskManager();
    }

    @Test
    public void shouldEpicStatusNewWhenSubtasksListIsEmpty() {
        tasksManager.createEpic(epic);
        TaskStatus status = epic.getStatus();
        assertEquals(TaskStatus.NEW, status);
    }

    @Test
    public void shouldEpicStatusNewWhenSubtasksAllNew() {
        tasksManager.createEpic(epic);
        Subtask subtask1 = new Subtask("Subtask1", "Subtask1 desc",
                "10.02.23 12.00", 30, epic.getId());
        Subtask subtask2 = new Subtask("Subtask2", "Subtask2 desc",
                "11.02.23 13.00", 10, epic.getId());
        Subtask subtask3 = new Subtask("Subtask3", "Subtask3 desc",
                "23.02.23 14.00", 15, epic.getId());
        tasksManager.createSubtask(subtask1);
        tasksManager.createSubtask(subtask2);
        tasksManager.createSubtask(subtask3);
        TaskStatus status = epic.getStatus();
        assertEquals(TaskStatus.NEW, status);
    }

    @Test
    public void shouldEpicStatusDoneWhenSubtasksAllDone() {
        tasksManager.createEpic(epic);
        Subtask subtask1 = new Subtask("Subtask1", "Subtask1 desc",
                "10.02.23 12.00", 30, epic.getId());
        Subtask subtask2 = new Subtask("Subtask2", "Subtask2 desc",
                "11.02.23 13.00", 10, epic.getId());
        Subtask subtask3 = new Subtask("Subtask3", "Subtask3 desc",
                "23.02.23 14.00", 15, epic.getId());
        tasksManager.createSubtask(subtask1);
        tasksManager.createSubtask(subtask2);
        tasksManager.createSubtask(subtask3);
        //меняем статус саб
        subtask1.setStatus(TaskStatus.DONE);
        subtask2.setStatus(TaskStatus.DONE);
        subtask3.setStatus(TaskStatus.DONE);
        tasksManager.updateSubtask(subtask1);
        tasksManager.updateSubtask(subtask2);
        tasksManager.updateSubtask(subtask3);

        TaskStatus status = epic.getStatus();
        assertEquals(TaskStatus.DONE, status);
    }

    @Test
    public void shouldEpicStatusInProgressWhenSubtasksNewAndDone() {
        tasksManager.createEpic(epic);
        Subtask subtask1 = new Subtask("Subtask1", "Subtask1 desc",
                "10.02.23 12.00", 30, epic.getId());
        Subtask subtask2 = new Subtask("Subtask2", "Subtask2 desc",
                "11.02.23 13.00", 10, epic.getId());
        Subtask subtask3 = new Subtask("Subtask3", "Subtask3 desc",
                "23.02.23 14.00", 15, epic.getId());
        tasksManager.createSubtask(subtask1);
        tasksManager.createSubtask(subtask2);
        tasksManager.createSubtask(subtask3);
        //меняем статус саб
        subtask1.setStatus(TaskStatus.NEW);
        subtask2.setStatus(TaskStatus.NEW);
        subtask3.setStatus(TaskStatus.DONE);
        tasksManager.updateSubtask(subtask1);
        tasksManager.updateSubtask(subtask2);
        tasksManager.updateSubtask(subtask3);

        TaskStatus status = epic.getStatus();
        assertEquals(TaskStatus.IN_PROGRESS, status);
    }
    @Test
    public void shouldEpicStatusInProgressWhenSubtasksAllInProgress() {
        tasksManager.createEpic(epic);
        Subtask subtask1 = new Subtask("Subtask1", "Subtask1 desc",
                "10.02.23 12.00", 30, epic.getId());
        Subtask subtask2 = new Subtask("Subtask2", "Subtask2 desc",
                "11.02.23 13.00", 10, epic.getId());
        Subtask subtask3 = new Subtask("Subtask3", "Subtask3 desc",
                "23.02.23 14.00", 15, epic.getId());
        tasksManager.createSubtask(subtask1);
        tasksManager.createSubtask(subtask2);
        tasksManager.createSubtask(subtask3);
        //меняем статус саб
        subtask1.setStatus(TaskStatus.IN_PROGRESS);
        subtask2.setStatus(TaskStatus.IN_PROGRESS);
        subtask3.setStatus(TaskStatus.IN_PROGRESS);
        tasksManager.updateSubtask(subtask1);
        tasksManager.updateSubtask(subtask2);
        tasksManager.updateSubtask(subtask3);

        TaskStatus status = epic.getStatus();
        assertEquals(TaskStatus.IN_PROGRESS, status);
    }
}
