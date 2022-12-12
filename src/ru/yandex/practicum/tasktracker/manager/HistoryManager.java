package ru.yandex.practicum.tasktracker.manager;

import ru.yandex.practicum.tasktracker.tasks.Task;

import java.util.List;

public interface HistoryManager {
    void remove(int id);
    void add(Task task);

    List<Task> getHistory();
}
