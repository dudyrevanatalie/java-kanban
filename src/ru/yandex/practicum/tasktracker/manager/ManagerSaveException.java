package ru.yandex.practicum.tasktracker.manager;

public class ManagerSaveException extends RuntimeException {
    public ManagerSaveException(String str) {
        super(str);
    }
}
