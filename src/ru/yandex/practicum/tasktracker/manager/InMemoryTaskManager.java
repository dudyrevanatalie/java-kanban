package ru.yandex.practicum.tasktracker.manager;

import ru.yandex.practicum.tasktracker.tasks.Epic;
import ru.yandex.practicum.tasktracker.tasks.Subtask;
import ru.yandex.practicum.tasktracker.tasks.Task;
import ru.yandex.practicum.tasktracker.tasks.TaskStatus;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.HashMap;

public class InMemoryTaskManager implements TasksManager {
    private final HashMap<Integer, Subtask> subtasks;
    private final HashMap<Integer, Task> tasks;
    private final HashMap<Integer, Epic> epics;
    private static int generatorId;

    private final HistoryManager historyManager;

    public InMemoryTaskManager() {
        subtasks = new HashMap<>();
        tasks = new HashMap<>();
        epics = new HashMap<>();
        generatorId = 0;
        historyManager = Managers.getDefaultHistory();
    }

    //Получение списка всех задач.
    @Override
    public HashMap<Integer, Subtask> getSubtasks() {
        return subtasks;
    }


    @Override
    public HashMap<Integer, Task> getTasks() {
        return tasks;
    }

    @Override
    public HashMap<Integer, Epic> getEpics() {
        return epics;
    }


    //Удаление всех задач.
    @Override
    public void clearSubtasks() {
        subtasks.clear();
    }

    @Override
    public void clearTasks() {
        tasks.clear();
    }

    @Override
    public void clearEpics() {
        epics.clear();

    }

    //Получение по идентификатору.
    @Override
    public Subtask getFromIdSubtask(int id) {
        Subtask subtask = subtasks.get(id);
        historyManager.add(subtask);
        return subtasks.get(id);
    }

    @Override
    public Task getFromIdTask(int id) {
        Task task = tasks.get(id);
        historyManager.add(task);
        return tasks.get(id);
    }

    @Override
    public Epic getFromIdEpic(int id) {
        Epic epic = epics.get(id);
        historyManager.add(epic);
        return epics.get(id);
    }

    //Создание. Сам объект должен передаваться в качестве параметра.
    @Override
    public void createTask(Task task) {
        generatorId++;
        task.setId(generatorId);
        tasks.put(generatorId, task);
    }

    @Override
    public void createSubtask(Subtask subtask) {
        generatorId++;
        subtask.setId(generatorId);
        subtasks.put(generatorId, subtask);
        Epic epic = epics.get(subtask.getEpicId());
        epic.getSubtaskIds().add(subtask.getId());
        //проверка статуса эпика в котором лежит наш сабтаск
        updateStatusOfEpic(epic);
    }

    @Override
    public void createEpic(Epic epic) {
        generatorId++;
        epic.setId(generatorId);
        epics.put(generatorId, epic);
    }

    @Override
    //Обновление. Новая версия объекта с верным идентификатором передаётся в виде параметра.
    public void updateTask(Task task2) {
        tasks.put(task2.getId(), task2);
    }

    @Override
    public void updateSubtask(Subtask subtask2) {
        subtasks.put(subtask2.getId(), subtask2);
        //Когда меняется статус любой подзадачи в эпике,
        // вам необходимо проверить, что статус эпика изменится соответствующим образом.
        Epic epic = epics.get(subtask2.getEpicId());
        updateStatusOfEpic(epic);

    }

    @Override
    public void updateEpic(Epic epic2) {
        epics.put(epic2.getId(), epic2);

        // статус эпика
        updateStatusOfEpic(epic2);
    }

    //метод, проверяющий статус эпика
    @Override
    public void updateStatusOfEpic(Epic epic) {
        boolean isNew = true;
        boolean isDone = true;

        ArrayList<Integer> subtaskIdsList = epic.getSubtaskIds();

        if (subtaskIdsList.size() == 0) {
            epic.setStatus(TaskStatus.NEW);
        } else {
            for (Integer subId : subtaskIdsList) {
                Subtask a = subtasks.get(subId);
                if (a.getStatus() != TaskStatus.NEW) {
                    isNew = false;
                }
                if (a.getStatus() != TaskStatus.DONE) {
                    isDone = false;
                }
            }
        }

        if (isNew) {
            epic.setStatus(TaskStatus.NEW);
        } else if (isDone) {
            epic.setStatus(TaskStatus.DONE);
        } else {
            epic.setStatus(TaskStatus.IN_PROGRESS);
        }

    }

    //Удаление по идентификатору.
    @Override
    public void deleteTaskFromId(int id) {
        Task task = tasks.get(id);
        if (task != null) {
            tasks.remove(id);
        } else {
            System.out.println("Такого id не существует!");
        }
    }

    @Override
    public void deleteSubtaskFromId(int id) {
        Subtask subtask = subtasks.get(id);
        if (subtask != null) {
            int epicId = subtask.getEpicId();
            Epic epic = epics.get(epicId);
            subtasks.remove(id);
            //удаляем саб таску из эпик листа
            ArrayList<Integer> subtaskIds = epic.getSubtaskIds();

            Iterator<Integer> iterator = subtaskIds.iterator();
            while (iterator.hasNext()) {
                Integer nextCat = iterator.next();
                if (nextCat == id) {
                    subtaskIds.remove(nextCat);
                }
            }
            updateStatusOfEpic(epic);
        } else {
            System.out.println("Такого id не существует!");
        }

    }

    @Override
    public void deleteEpicFromId(int id) {
        Epic epicUse = epics.get(id);
        if (epicUse != null) {
            ArrayList<Integer> subtaskIdsList = epicUse.getSubtaskIds();
            subtaskIdsList.clear();
            epics.remove(id);
        } else {
            System.out.println("Такого id не существует!");
        }

    }

    @Override
    //Получение списка всех подзадач определённого эпика.
    public ArrayList<Subtask> getSubtaskList(Epic epic) {
        ArrayList<Integer> subtaskIdsList = epic.getSubtaskIds();
        ArrayList<Subtask> listOfSubtasks = new ArrayList<>();
        for (Integer subId : subtaskIdsList) {
            listOfSubtasks.add(subtasks.get(subId));
        }
        return listOfSubtasks;
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }


}




