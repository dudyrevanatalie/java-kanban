package ru.yandex.practicum.tasktracker.tasks;

public class Subtask extends Task {
    private final int epicId;

    public Subtask(String name, String description, int epicId) {
        super(name, description);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    @Override
    public String toString() {
        return id + "," + TypesOfTasks.SUBTASK.toString() + "," + name + "," + status + "," + description + "," + epicId;
    }
}
