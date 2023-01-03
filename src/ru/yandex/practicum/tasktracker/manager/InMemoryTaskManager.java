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
    protected final HashMap<Integer, Subtask> subtasks;
    protected final HashMap<Integer, Task> tasks;
    protected final HashMap<Integer, Epic> epics;
    protected static int generatorId;

    protected final HistoryManager historyManager;

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
        Subtask subtask = subtasks.getOrDefault(id, null);
        if (subtask != null) {
            historyManager.add(subtask);
        }

        return subtask;
    }

    @Override
    public Task getFromIdTask(int id) {
        Task task = tasks.getOrDefault(id, null);
        if (task != null) {
            historyManager.add(task);
        }
        return task;
    }

    @Override
    public Epic getFromIdEpic(int id) {
        Epic epic = epics.getOrDefault(id, null);
        if (epic != null) {
            historyManager.add(epic);
        }
        return epic;
    }

    //Создание. Сам объект должен передаваться в качестве параметра.
    @Override
    public void createTask(Task task) {
        int id = task.getId();
        Task t = tasks.get(id);

        if (t == null) {
            if (task.getId() == 0) {
                generatorId++;
                task.setId(generatorId);
            }
            tasks.put(task.getId(), task);
        } else {
            System.out.println("Task с таким id уже существует :)");
        }


    }

    @Override
    public void createSubtask(Subtask subtask) {

        int id = subtask.getId();
        Subtask s = subtasks.get(id);

        if (s == null) {
            if (subtask.getId() == 0) {
                generatorId++;
                subtask.setId(generatorId);
            }
            subtasks.put(subtask.getId(), subtask);
            Epic epic = epics.getOrDefault(subtask.getEpicId(), null);
            if (epic != null) {
                epic.getSubtaskIds().add(subtask.getId());
                //проверка статуса эпика в котором лежит наш сабтаск
                updateStatusOfEpic(epic);
            }

        } else {
            System.out.println("Subtask с таким id уже существует :)");
        }
    }

    @Override
    public void createEpic(Epic epic) {

        int id = epic.getId();
        Epic e = epics.get(id);
        if (e == null) {
            if (epic.getId() == 0) {
                generatorId++;
                epic.setId(generatorId);
            }
            epics.put(epic.getId(), epic);
        } else {
            System.out.println("Epic с таким id уже существует :)");
        }
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
            historyManager.remove(id);
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
            historyManager.remove(id);
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
            for (Integer sub : subtaskIdsList) {
                subtasks.remove(sub);
                historyManager.remove(sub);
            }
            subtaskIdsList.clear();
            epics.remove(id);
            historyManager.remove(id);
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




