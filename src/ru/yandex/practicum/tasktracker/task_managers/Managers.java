package ru.yandex.practicum.tasktracker.task_managers;

import ru.yandex.practicum.tasktracker.history_managers.HistoryManager;
import ru.yandex.practicum.tasktracker.history_managers.InMemoryHistoryManager;

import java.io.File;


public class Managers {
    public static TasksManager getDefault() {
        return HttpTaskManager.load();
    }

    public static TasksManager getDefault(File file) {
        return FileBackedTasksManager.loadFromFile(file);
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
