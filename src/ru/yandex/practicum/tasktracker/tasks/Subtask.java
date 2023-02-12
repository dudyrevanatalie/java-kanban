package ru.yandex.practicum.tasktracker.tasks;

import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Subtask extends Task {
    private final int epicId;

    public Subtask(String name, String description, int epicId) {
        super(name, description);
        this.epicId = epicId;
    }

    public Subtask(String name, String description, String startTime, int duration, int epicId) {
        super(name, description, startTime, duration);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Subtask subtask = (Subtask) o;
        return epicId == subtask.epicId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), epicId);
    }

    @Override
    public String toString() {
        return id + "," + TypesOfTasks.SUBTASK + "," + name + "," + status + "," + description + "," +
                startTime.format(DateTimeFormatter.ofPattern("dd.MM.yy HH.mm")) + "," + duration + "," +
                getEndTime().format(DateTimeFormatter.ofPattern("dd.MM.yy HH.mm")) + "," + epicId;
    }
}
