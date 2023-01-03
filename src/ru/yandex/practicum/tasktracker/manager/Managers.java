package ru.yandex.practicum.tasktracker.manager;

import java.io.File;


public class Managers {
    public static TasksManager getDefault() {
        return new FileBackedTasksManager(new File("src/resourses/file.csv"));
    }

    public static TasksManager getDefault(File file) {
        return FileBackedTasksManager.loadFromFile(file);
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
