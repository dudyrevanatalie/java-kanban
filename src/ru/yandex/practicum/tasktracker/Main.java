package ru.yandex.practicum.tasktracker;

import ru.yandex.practicum.tasktracker.manager.Managers;
import ru.yandex.practicum.tasktracker.manager.TasksManager;
import ru.yandex.practicum.tasktracker.tasks.Epic;
import ru.yandex.practicum.tasktracker.tasks.Subtask;
import ru.yandex.practicum.tasktracker.tasks.Task;

import java.io.File;

public class Main {

    public static void main(String[] args) {
        //мини-тест
        TasksManager tasksManager = Managers.getDefault();
        //Немного не понимаю как и зачем создавать метод main в новом менеджере.
        //Выходит, что мы должны избавиться от класса Main? Если не трудно, объясните пожалуйста
        System.out.println("Создаем две задачи, эпик с тремя подзадачами и эпик без подзадач");
        Task task1 = new Task("Выполнить задание 6 проекта", "Протестировать код");
        tasksManager.createTask(task1);

        Task task2 = new Task("Встать в 6:30", "Приготовить завтрак");
        tasksManager.createTask(task2);

        //создаем эпик с 3 подзадачами
        Epic epic1 = new Epic("Купить подарки к новому году", "купить обертку для подарков");
        tasksManager.createEpic(epic1);
        Subtask subtask1 = new Subtask("Купить подарок для сына", "конструктор", epic1.getId());
        tasksManager.createSubtask(subtask1);
        Subtask subtask2 = new Subtask("Купить подарок для мамы", "набор ножей", epic1.getId());
        tasksManager.createSubtask(subtask2);
        Subtask subtask3 = new Subtask("Купить подарок папе", "коврики в машину", epic1.getId());
        tasksManager.createSubtask(subtask3);

        //эпик без подзадач
        Epic epic2 = new Epic("Купить продукты для ужина", "фывфывы");
        tasksManager.createEpic(epic2);

        System.out.println("Просматриваем наш subtask2 - " + tasksManager.getFromIdSubtask(subtask2.getId()));
        System.out.println("История просмотров, список - " + tasksManager.getHistory());

        System.out.println("Просматриваем task1 --->" + tasksManager.getFromIdTask(task1.getId()));
        System.out.println("История просмотров, список - " + tasksManager.getHistory());

        System.out.println("Просматриваем epic1--->" + tasksManager.getFromIdEpic(epic1.getId()));
        System.out.println("История просмотров, список - " + tasksManager.getHistory());

        System.out.println("Просматриваем task2--->" + tasksManager.getFromIdTask(task2.getId()));
        System.out.println("История просмотров, список - " + tasksManager.getHistory());

        System.out.println("Просматриваем task1--->" + tasksManager.getFromIdTask(task1.getId()));
        System.out.println("История просмотров, список - " + tasksManager.getHistory());

        System.out.println("Просматриваем epic2--->" + tasksManager.getFromIdEpic(epic2.getId()));
        System.out.println("История просмотров, список - " + tasksManager.getHistory());

        System.out.println("Просматриваем наш subtask2 - " + tasksManager.getFromIdSubtask(subtask2.getId()));
        System.out.println("История просмотров, список - " + tasksManager.getHistory());

        System.out.println("Удаляем epic1");
        tasksManager.deleteEpicFromId(epic1.getId());
        System.out.println("История просмотров, список - " + tasksManager.getHistory());

        System.out.println("-----------------------------------");
        System.out.println("новый менеджер");
        TasksManager tasksManager2 = Managers.getDefault(new File("src/resourses/", "file.csv"));
        System.out.println(tasksManager2.getHistory());


    }
}
