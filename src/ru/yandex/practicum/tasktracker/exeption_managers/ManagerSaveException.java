package ru.yandex.practicum.tasktracker.exeption_managers;

public class ManagerSaveException extends RuntimeException {
    public ManagerSaveException(String str, Throwable cause) {
        super(str, cause);
    }
}
