package ru.yandex.practicum.tasktracker;

import ru.yandex.practicum.tasktracker.manager.TasksManager;
import ru.yandex.practicum.tasktracker.tasks.Epic;
import ru.yandex.practicum.tasktracker.tasks.Subtask;
import ru.yandex.practicum.tasktracker.tasks.Task;
import ru.yandex.practicum.tasktracker.tasks.TaskStatus;

public class Main {

    public static void main(String[] args) {
        //мини-тест
        TasksManager tasksManager = new TasksManager();
        System.out.println("Распечатали созданные задачи и эпики--------------------------------------------------------------------------------------");

        Task task1 = new Task("Помыть машину", "Заехать в автомойку около дома после работы");
        tasksManager.createTask(task1);

        Task task2 = new Task("Сдать 3 финальный проект", "Сделать качественно и быстро!");
        tasksManager.createTask(task2);

        //Создаем 1 эпик
        Epic epic1 = new Epic("Купить продукты для пасты", "Дома не хватает пару ингридиентов");
        tasksManager.createEpic(epic1);
        //Создаем 2 подзадачи для эпика 1
        Subtask subtask1 = new Subtask("Купить морепродукты для пасты", "Зайти в пятёрку, там дешевле",
                epic1.getId());
        tasksManager.createSubtask(subtask1);

        Subtask subtask2 = new Subtask("Купить сливки для пасты", "", epic1.getId());
        tasksManager.createSubtask(subtask2);
        //Создаем 2 эпик
        Epic epic2 = new Epic("Список упражнений в тренажерном зале, которые нужно сделать",
                "Сегодня работа на мышцы ног");
        tasksManager.createEpic(epic2);
        //Создаем подзадачу для эпика
        Subtask subtaskForEpic2 = new Subtask("Полуприсед с грифом", "4 серии по 15 раз", epic2.getId());
        tasksManager.createSubtask(subtaskForEpic2);

        //Распечатайте списки эпиков, задач и подзадач
        System.out.println(tasksManager.getTasks());
        System.out.println(tasksManager.getEpics());
        System.out.println(tasksManager.getSubtasks());

        System.out.println("Измените статусы созданных объектов, распечатайте--------------------------------------------------------------------------------------");

        //Измените статусы созданных объектов, распечатайте.
        subtask1.setStatus(TaskStatus.NEW);
        tasksManager.updateSubtask(subtask1);
        subtask2.setStatus(TaskStatus.DONE);
        tasksManager.updateSubtask(subtask2);


        subtaskForEpic2.setStatus(TaskStatus.IN_PROGRESS);
        tasksManager.updateSubtask(subtaskForEpic2);

        System.out.println(tasksManager.getTasks());
        System.out.println(tasksManager.getEpics());
        System.out.println(tasksManager.getSubtasks());

        System.out.println("удалить одну из задач и один из эпиков. --------------------------------------------------------------------------------------");

        // удалить одну из задач и один из эпиков.
        tasksManager.deleteTaskFromId(task1.getId());
        tasksManager.deleteEpicFromId(epic2.getId());

        tasksManager.deleteSubtaskFromId(subtask1.getId());

        System.out.println(tasksManager.getTasks());
        System.out.println(tasksManager.getEpics());
        System.out.println(tasksManager.getSubtasks());
    }
}
