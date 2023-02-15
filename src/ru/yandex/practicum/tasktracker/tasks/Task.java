package ru.yandex.practicum.tasktracker.tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Task {
    protected String name;
    protected int id;

    protected String description;
    protected TaskStatus status;
    protected int duration;
    protected LocalDateTime startTime;

    public Task(String name, String description) {
        this.name = name;
        this.description = description;
        this.status = TaskStatus.NEW;
    }

    public Task(String name, String description, String startTime, int duration) {
        this.name = name;
        this.description = description;
        this.status = TaskStatus.NEW;
        this.startTime = setStartTime(startTime);
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    protected LocalDateTime setStartTime(String dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy HH.mm");
        return LocalDateTime.parse(dateTime, formatter);
    }

    public LocalDateTime getEndTime() {
        return startTime.plusMinutes(duration);
    }

    public int getId() {
        return id;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return id + "," + TypesOfTasks.TASK + "," + name + "," + status + "," + description + "," +
                startTime.format(DateTimeFormatter.ofPattern("dd.MM.yy HH.mm")) + "," + duration + "," +
                getEndTime().format(DateTimeFormatter.ofPattern("dd.MM.yy HH.mm")) + ",";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id &&
                Objects.equals(name, task.name) &&
                Objects.equals(description, task.description) &&
                status == task.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id, description, status, duration, startTime);
    }


}
