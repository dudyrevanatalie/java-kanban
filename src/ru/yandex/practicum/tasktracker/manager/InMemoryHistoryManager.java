package ru.yandex.practicum.tasktracker.manager;

import ru.yandex.practicum.tasktracker.tasks.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    private final List<Task> historyList;

    public InMemoryHistoryManager() {
        this.historyList = new ArrayList<>();
    }

    @Override
    public void add(Task task) {
        historyList.add(task);

        if (historyList.size() > 10) {
            historyList.remove(0);
        }
    }

    @Override
    public List<Task> getHistory() {
        return historyList;
    }
}
