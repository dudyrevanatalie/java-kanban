package ru.yandex.practicum.tasktracker.manager;

public class Managers {
    public static TasksManager getDefault() {
        return new InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
