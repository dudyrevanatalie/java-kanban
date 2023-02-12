package ru.yandex.practicum.tasktracker;

import ru.yandex.practicum.tasktracker.task_managers.Managers;
import ru.yandex.practicum.tasktracker.task_managers.TasksManager;
import ru.yandex.practicum.tasktracker.tasks.Epic;
import ru.yandex.practicum.tasktracker.tasks.Subtask;
import ru.yandex.practicum.tasktracker.tasks.Task;
import ru.yandex.practicum.tasktracker.tasks.TaskStatus;

import java.io.File;

public class Main {

    public static void main(String[] args) {

        TasksManager tasksManager = Managers.getDefault();
        Epic epic1 = new Epic("Epic1", "descOfEpic");
        tasksManager.createEpic(epic1);
        Subtask subtask1 = new Subtask("Subtask1", "Subtask1 desk", "10.02.23 12.00",
                30, epic1.getId());
        Subtask subtask2 = new Subtask("Subtask2", "Subtask2 desk", "10.02.23 13.00",
                15, epic1.getId());
        tasksManager.createSubtask(subtask1);
        tasksManager.createSubtask(subtask2);
        System.out.println("Просматриваем сабы");
        tasksManager.getFromIdSubtask(subtask1.getId());
        tasksManager.getFromIdSubtask(subtask2.getId());
        tasksManager.getFromIdEpic(epic1.getId());
        tasksManager.getHistory();


        System.out.println("-----------------------------------");
        System.out.println("новый менеджер");
        TasksManager tasksManager2 = Managers.getDefault(new File("src/resourses/", "file.csv"));
        System.out.println("история");
        System.out.println(tasksManager2.getHistory());
        System.out.println("список задач в порядке приоритета");
        System.out.println(tasksManager2.getPrioritizedTasks());



    }
}
