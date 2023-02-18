package Tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.tasktracker.history_managers.HistoryManager;
import ru.yandex.practicum.tasktracker.task_managers.Managers;
import ru.yandex.practicum.tasktracker.task_managers.TasksManager;
import ru.yandex.practicum.tasktracker.tasks.Epic;
import ru.yandex.practicum.tasktracker.tasks.Subtask;
import ru.yandex.practicum.tasktracker.tasks.Task;
import ru.yandex.practicum.tasktracker.tasks.TaskStatus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

abstract class TasksManagerTest<T extends TasksManager> {
    T manager;

    abstract T createManager() throws IOException;

    @BeforeEach
    public void beforeEach() throws IOException {
        manager = createManager();
    }

    @Test
    public void doesSubtasksHaveEpicAndEpicHaveSubtasksId() { //Для подзадач нужно дополнительно проверить наличие эпика
        Epic epic = new Epic("epicName", "EpicDescription");
        manager.createEpic(epic);

        Subtask subtask1 = new Subtask("Subtask1", "Subtask1 desc",
                "10.02.23 12.00", 30, epic.getId());
        manager.createSubtask(subtask1);

        assertEquals(epic.getId(), manager.getFromIdSubtask(subtask1.getId()).getEpicId());
        assertEquals(subtask1.getId(), manager.getFromIdEpic(epic.getId()).getSubtaskIds().get(0));
    }

    @Test
    public void shouldEpicStatusDoneWhenSubtasksDone() {//Проверка статуса эпика
        Epic epic = new Epic("epicName", "EpicDescription");
        manager.createEpic(epic);

        Subtask subtask1 = new Subtask("Subtask1", "Subtask1 desc",
                "10.02.23 12.00", 30, epic.getId());
        manager.createSubtask(subtask1);

        Subtask subtask2 = new Subtask("Subtask1", "Subtask1 desc",
                "10.02.23 13.00", 30, epic.getId());
        manager.createSubtask(subtask2);

        subtask1.setStatus(TaskStatus.DONE);
        subtask2.setStatus(TaskStatus.DONE);

        manager.updateSubtask(subtask1);
        manager.updateSubtask(subtask2);

        assertEquals(TaskStatus.DONE, manager.getFromIdEpic(epic.getId()).getStatus());
    }

    @Test
    public void shouldCreateNewTaskAndGetThisTaskFromHashMap() {
        Task task = new Task("TASK", "TASK desc",
                "10.02.23 12.00", 30);
        manager.createTask(task);
        assertEquals(task, manager.getFromIdTask(task.getId()));
    }

    @Test
    public void shouldCreateNewSubTaskAndGetThisSubTaskFromHashMap() {
        Subtask subtask = new Subtask("SUB", "SUB desc",
                "10.02.23 12.00", 30, 0);
        manager.createSubtask(subtask);
        assertEquals(subtask, manager.getFromIdSubtask(subtask.getId()));
    }

    @Test
    public void shouldCreateNewEpicAndGetThisEpicFromHashMap() {
        Epic epic = new Epic("Epic", "EPic desc");
        manager.createEpic(epic);
        assertEquals(epic, manager.getFromIdEpic(epic.getId()));
    }

    @Test
    public void testGetTasks() {
        Task task1 = new Task("TASK", "TASK desc",
                "10.02.23 12.00", 30);
        Task task2 = new Task("TASK2", "TASK2 desc",
                "11.02.23 12.00", 30);
        manager.createTask(task1);
        manager.createTask(task2);
        List<Task> taskList = new ArrayList<>();
        taskList.add(task1);
        taskList.add(task2);
        assertEquals(taskList, manager.getTasks());
    }

    @Test
    public void testGetSubtasks() {
        Subtask subtask1 = new Subtask("TASK", "TASK desc",
                "10.02.23 12.00", 30, 1);
        Subtask subtask2 = new Subtask("TASK2", "TASK2 desc",
                "11.02.23 12.00", 30, 1);
        manager.createSubtask(subtask1);
        manager.createSubtask(subtask2);
        List<Subtask> subtasks = new ArrayList<>();
        subtasks.add(subtask1);
        subtasks.add(subtask2);
        assertEquals(subtasks, manager.getSubtasks());
    }

    @Test
    public void testGetEpics() {
        Epic epic1 = new Epic("Epic", "Epic desc");
        Epic epic2 = new Epic("Epic2", "Epic2 desc");
        manager.createEpic(epic1);
        manager.createEpic(epic2);
        List<Epic> epics = new ArrayList<>();
        epics.add(epic1);
        epics.add(epic2);
        assertEquals(epics, manager.getEpics());
    }

    @Test
    public void TestClearTasks() {
        Task task1 = new Task("TASK", "TASK desc",
                "10.02.23 12.00", 30);
        Task task2 = new Task("TASK2", "TASK2 desc",
                "11.02.23 12.00", 30);
        manager.createTask(task1);
        manager.createTask(task2);
        manager.clearTasks();
        assertEquals(0, manager.getTasks().size());
    }

    @Test
    public void TestClearSubtasks() {
        Subtask subtask1 = new Subtask("TASK", "TASK desc",
                "10.02.23 12.00", 30, 1);
        Subtask subtask2 = new Subtask("TASK2", "TASK2 desc",
                "11.02.23 12.00", 30, 1);
        manager.createSubtask(subtask1);
        manager.createSubtask(subtask2);
        manager.clearSubtasks();
        assertEquals(0, manager.getSubtasks().size());
    }

    @Test
    public void TestClearEpics() {
        Epic epic1 = new Epic("Epic", "Epic desc");
        Epic epic2 = new Epic("Epic2", "Epic2 desc");
        manager.createEpic(epic1);
        manager.createEpic(epic2);
        manager.clearEpics();
        assertEquals(0, manager.getEpics().size());
    }

    @Test
    public void shouldReturnTaskIfIdIsWrong() {
        Task task1 = new Task("TASK", "TASK desc",
                "10.02.23 12.00", 30);
        manager.createTask(task1);
        assertNull(manager.getFromIdTask(3));
    }

    @Test
    public void shouldReturnTaskById() {
        Task task1 = new Task("TASK", "TASK desc",
                "10.02.23 12.00", 30);
        manager.createTask(task1);
        assertEquals(task1, manager.getFromIdTask(1));
    }

    @Test
    public void shouldReturnSubtaskIfIdIsWrong() {
        Subtask subtask1 = new Subtask("TASK2", "TASK2 desc",
                "11.02.23 12.00", 30, 1);
        manager.createSubtask(subtask1);
        assertNull(manager.getFromIdSubtask(3));
    }

    @Test
    public void shouldReturnSubtaskById() {
        Subtask subtask1 = new Subtask("TASK2", "TASK2 desc",
                "11.02.23 12.00", 30, 1);
        manager.createSubtask(subtask1);
        assertEquals(subtask1, manager.getFromIdSubtask(1));
    }

    @Test
    public void shouldReturnEpicIfIdIsWrong() {
        Epic epic1 = new Epic("Epic1", "Epic1 desc");
        manager.createEpic(epic1);
        assertNull(manager.getFromIdEpic(2));
    }

    @Test
    public void shouldReturnEpicById() {
        Epic epic1 = new Epic("Epic1", "Epic1 desc");
        manager.createEpic(epic1);
        assertEquals(epic1, manager.getFromIdEpic(1));
    }

    @Test
    public void testUpdateTask() {
        Task task1 = new Task("TASK", "TASK desc",
                "10.02.23 12.00", 30);
        manager.createTask(task1);
        Task newTask = new Task("NEwNAme", "New desc",
                "11.11.23 11.11", 24);
        newTask.setStatus(TaskStatus.DONE);
        newTask.setId(task1.getId());
        manager.updateTask(newTask);
        assertEquals(newTask, manager.getFromIdTask(task1.getId()));
    }

    @Test
    public void testUpdateSubtask() {
        Epic epic = new Epic("NAme Epic", "desc");
        manager.createEpic(epic);
        Subtask subtask1 = new Subtask("TASK2", "TASK2 desc",
                "11.02.23 12.00", 30, epic.getId());
        manager.createSubtask(subtask1);
        Subtask newSubtask = new Subtask("NEW SUB", "NEW desc",
                "01.01.23 12.00", 32, epic.getId());
        newSubtask.setStatus(TaskStatus.IN_PROGRESS);
        newSubtask.setId(subtask1.getId());
        manager.updateSubtask(newSubtask);
        assertEquals(newSubtask, manager.getFromIdSubtask(subtask1.getId()));
    }

    @Test
    public void testUpdateEpic() {
        Epic epic1 = new Epic("Epic1", "Epic1 desc");
        Epic newEpic = new Epic("NewName Epic", "New DESC");
        manager.createEpic(epic1);

        newEpic.setId(epic1.getId());
        manager.updateEpic(newEpic);
        assertEquals(newEpic, manager.getFromIdEpic(epic1.getId()));
    }

    @Test
    public void testDeleteTaskFromId() {
        Task task1 = new Task("TASK", "TASK desc",
                "10.02.23 12.00", 30);
        manager.createTask(task1);
        manager.deleteTaskFromId(task1.getId());
        assertEquals(0, manager.getTasks().size());
    }

    @Test
    public void testDeleteSubtaskFromId() {//Не могу понять почему выкидывает такую ошибку. Нужна помощь)
        Epic epic1 = new Epic("Epic1", "Epic1 desc");
        manager.createEpic(epic1);
        Subtask subtask1 = new Subtask("TASK2", "TASK2 desc",
                "11.02.23 12.00", 30, epic1.getId());
        manager.createSubtask(subtask1);
        manager.deleteSubtaskFromId(subtask1.getId());
        assertEquals(0, manager.getSubtasks().size());
    }

    @Test
    public void testDeleteEpicFromId() {
        Epic epic1 = new Epic("Epic1", "Epic1 desc");
        manager.createEpic(epic1);
        manager.deleteEpicFromId(epic1.getId());
        assertEquals(0, manager.getEpics().size());

    }

    @Test
    public void shouldGetSubtaskListByEpic() {
        Epic epic1 = new Epic("Epic1", "Epic1 desc");
        manager.createEpic(epic1);
        Subtask subtask1 = new Subtask("TASK", "TASK desc",
                "10.02.23 12.00", 30, epic1.getId());
        Subtask subtask2 = new Subtask("TASK2", "TASK2 desc",
                "11.02.23 12.00", 30, epic1.getId());
        manager.createSubtask(subtask1);
        manager.createSubtask(subtask2);
        List<Subtask> subList = new ArrayList<>();
        subList.add(subtask1);
        subList.add(subtask2);
        assertEquals(subList, manager.getSubtaskList(epic1));
    }

    @Test
    public void shouldReturnHistory() {
        HistoryManager historyCheck = Managers.getDefaultHistory();
        assertEquals(historyCheck.getHistory(), manager.getHistory());
    }


}