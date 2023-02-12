package ru.yandex.practicum.tasktracker.task_managers;

import ru.yandex.practicum.tasktracker.tasks.Epic;
import ru.yandex.practicum.tasktracker.tasks.Subtask;
import ru.yandex.practicum.tasktracker.tasks.Task;


import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Set;

public interface TasksManager {


    //Получение списка всех задач.
    List<Subtask> getSubtasks();

    List<Task> getTasks();

    List<Epic> getEpics();

    //Удаление всех задач.
    void clearSubtasks();

    void clearTasks();

    void clearEpics();

    //Получение по идентификатору.
    Subtask getFromIdSubtask(int id);

    Task getFromIdTask(int id);

    Epic getFromIdEpic(int id);

    //Создание. Сам объект должен передаваться в качестве параметра.
    void createTask(Task task);

    void createSubtask(Subtask subtask);

    void createEpic(Epic epic);

    //Обновление. Новая версия объекта с верным идентификатором передаётся в виде параметра.
    void updateTask(Task task2);

    void updateSubtask(Subtask subtask2);

    void updateEpic(Epic epic2);

    //метод, проверяющий статус эпика
    void updateStatusOfEpic(Epic epic);

    //Удаление по идентификатору.
    void deleteTaskFromId(int id);

    void deleteSubtaskFromId(int id);

    void deleteEpicFromId(int id);

    //Получение списка всех подзадач определённого эпика.
    ArrayList<Subtask> getSubtaskList(Epic epic);

    List<Task> getHistory();
    Set<Task> getPrioritizedTasks();

}




