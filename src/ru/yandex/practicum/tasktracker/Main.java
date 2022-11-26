package ru.yandex.practicum.tasktracker;

import ru.yandex.practicum.tasktracker.manager.Managers;
import ru.yandex.practicum.tasktracker.manager.TasksManager;
import ru.yandex.practicum.tasktracker.tasks.Epic;
import ru.yandex.practicum.tasktracker.tasks.Subtask;
import ru.yandex.practicum.tasktracker.tasks.Task;

public class Main {

    public static void main(String[] args) {
        //мини-тест
        TasksManager tasksManager = Managers.getDefault();
        System.out.println("Распечатали созданные задачи и эпики--------------------------------------------------------------------------------------");

        Task task1 = new Task("Помыть машину", "Заехать в автомойку около дома после работы");
        tasksManager.createTask(task1);

        //Создаем 1 эпик
        Epic epic1 = new Epic("Купить продукты для пасты", "Дома не хватает пару ингридиентов");
        tasksManager.createEpic(epic1);

        //Создаем 2 подзадачи для эпика 1
        Subtask subtask1 = new Subtask("Купить морепродукты для пасты", "Зайти в пятёрку, там дешевле",
                epic1.getId());
        tasksManager.createSubtask(subtask1);

        Subtask subtask2 = new Subtask("Купить сливки для пасты", "", epic1.getId());

        tasksManager.createSubtask(subtask2);
        tasksManager.createSubtask(subtask2);
        tasksManager.createSubtask(subtask2);
        tasksManager.createSubtask(subtask2);
        Subtask s = tasksManager.getFromIdSubtask(subtask2.getId());
        tasksManager.createSubtask(s);
        Subtask s2 = tasksManager.getFromIdSubtask(subtask2.getId());
        tasksManager.createSubtask(s2);
        Subtask s3 = tasksManager.getFromIdSubtask(subtask2.getId());
        tasksManager.createSubtask(s3);

        //Распечатайте списки эпиков, задач и подзадач
        System.out.println(tasksManager.getTasks());
        System.out.println(tasksManager.getEpics());
        System.out.println(tasksManager.getSubtasks());

        /*
        System.out.println("Просматриваем наш task - " + tasksManager.getFromIdTask(task1.getId()));
        System.out.println("История просмотров, список - " + tasksManager.getHistory());

        System.out.println("Просматриваем наш subtask - " + tasksManager.getFromIdSubtask(subtask1.getId()));
        System.out.println("История просмотров, список - " + tasksManager.getHistory());

        System.out.println("Просматриваем наш subtask - " + tasksManager.getFromIdSubtask(subtask2.getId()));
        System.out.println("История просмотров, список - " + tasksManager.getHistory());

        System.out.println("Просматриваем наш subtask - " + tasksManager.getFromIdSubtask(subtask1.getId()));
        System.out.println("История просмотров, список - " + tasksManager.getHistory());
        System.out.println("Просматриваем наш subtask - " + tasksManager.getFromIdSubtask(subtask1.getId()));
        System.out.println("История просмотров, список - " + tasksManager.getHistory());
        System.out.println("Просматриваем наш subtask - " + tasksManager.getFromIdSubtask(subtask1.getId()));
        System.out.println("История просмотров, список - " + tasksManager.getHistory());
        System.out.println("Просматриваем наш subtask - " + tasksManager.getFromIdSubtask(subtask1.getId()));
        System.out.println("История просмотров, список - " + tasksManager.getHistory());
        System.out.println("Просматриваем наш subtask - " + tasksManager.getFromIdSubtask(subtask1.getId()));
        System.out.println("История просмотров, список - " + tasksManager.getHistory());


        System.out.println("Просматриваем наш epic - " + tasksManager.getFromIdEpic(epic1.getId()));
        System.out.println("Просматриваем наш epic - " + tasksManager.getFromIdEpic(epic1.getId()));


        System.out.println("Просматриваем наш epic - " + tasksManager.getFromIdEpic(epic1.getId()));//11 элемент
        System.out.println("История просмотров, список - " + tasksManager.getHistory());


        System.out.println("Размер списка истории = " + tasksManager.getHistory().size());

        System.out.println("Просматриваем наш epic - " + tasksManager.getFromIdEpic(epic1.getId()));//12
        System.out.println("История просмотров, список - " + tasksManager.getHistory());
        System.out.println("Размер списка истории = " + tasksManager.getHistory().size());

        */
    }
}
