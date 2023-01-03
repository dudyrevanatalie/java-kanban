package ru.yandex.practicum.tasktracker.tasks;

import ru.yandex.practicum.tasktracker.manager.TypesOfTasks;

import java.util.ArrayList;

public class Epic extends Task {
    private final ArrayList<Integer> subtaskIds;

    public Epic(String name, String description) {
        super(name, description);
        this.subtaskIds = new ArrayList<>();
    }

    public ArrayList<Integer> getSubtaskIds() {
        return subtaskIds;
    }

    @Override
    public String toString() {
        return id + "," + TypesOfTasks.EPIC.toString() + "," + name + "," + status + "," + description + ",";
    }
}
