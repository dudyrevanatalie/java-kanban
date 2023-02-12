package Tests;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.tasktracker.task_managers.FileBackedTasksManager;
import ru.yandex.practicum.tasktracker.tasks.Epic;
import ru.yandex.practicum.tasktracker.tasks.Task;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class FileBackedTasksManagerTest extends TaskManagerTest<FileBackedTasksManager> {
    @Override
    FileBackedTasksManager createManager() {
        return new FileBackedTasksManager(new File("src/resourses/file.csv"));
    }

    @Test
    public void testSaveToFileAndLoadFromFileWithoutHistory() {
        Task task = new Task("TASK", "TASK desc",
                "10.02.23 12.00", 30);
        manager.createTask(task);
        File file = new File("src/resourses/", "file.csv");
        FileBackedTasksManager fileBackedTasksManager = FileBackedTasksManager.loadFromFile(file);
        assertEquals(task, fileBackedTasksManager.getFromIdTask(task.getId()));
    }

    @Test
    public void testSaveToFileAndLoadFromFileWithHistory() {//с историей
        Task task = new Task("TASK", "TASK desc",
                "10.02.23 12.00", 30);
        manager.createTask(task);
        manager.getFromIdTask(task.getId());
        File file = new File("src/resourses/", "file.csv");
        FileBackedTasksManager fileBackedTasksManager = FileBackedTasksManager.loadFromFile(file);
        assertEquals(task, fileBackedTasksManager.getFromIdTask(task.getId()));
    }
    @Test
    public void testSaveToFileAndLoadFromFileWithoutTasks(){
        manager.save();
        File file = new File("src/resourses/", "file.csv");
        FileBackedTasksManager fileBackedTasksManager = FileBackedTasksManager.loadFromFile(file);
        assertEquals(0, fileBackedTasksManager.getTasks().size());
        assertEquals(0, fileBackedTasksManager.getSubtasks().size());
        assertEquals(0, fileBackedTasksManager.getEpics().size());
    }
    @Test
    public void testLoadToFileEpicWithoutSubtasks() {
        Epic epic = new Epic("Epic","desc");
        manager.createEpic(epic);
        File file = new File("src/resourses/", "file.csv");
        FileBackedTasksManager fileBackedTasksManager = FileBackedTasksManager.loadFromFile(file);
        assertEquals(epic, fileBackedTasksManager.getFromIdEpic(epic.getId()));
    }


}