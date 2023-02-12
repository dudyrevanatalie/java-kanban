package ru.yandex.practicum.tasktracker.task_managers;

import ru.yandex.practicum.tasktracker.exeption_managers.ManagerSaveException;
import ru.yandex.practicum.tasktracker.history_managers.HistoryManager;
import ru.yandex.practicum.tasktracker.tasks.Epic;
import ru.yandex.practicum.tasktracker.tasks.Subtask;
import ru.yandex.practicum.tasktracker.tasks.Task;
import ru.yandex.practicum.tasktracker.tasks.TaskStatus;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileBackedTasksManager extends InMemoryTaskManager {
    private final File file;

    public FileBackedTasksManager(File file) {
        this.file = file;
    }

    public void save() throws ManagerSaveException {// будет сохранять текущее состояние менеджера в указанный файл.

        try (Writer writer = new FileWriter(file)) {
            writer.write("id,type,name,status,description,start time,duration,end time,epic\n");
            for (Task task : tasks.values()) {
                writer.write(task.toString() + "\n");
            }
            for (Task subtask : subtasks.values()) {
                writer.write(subtask.toString() + "\n");
            }
            for (Task epic : epics.values()) {
                writer.write(epic.toString() + "\n");
            }
            writer.write("\n");
            writer.write(historyToString(historyManager));
        } catch (IOException e) {
            throw new ManagerSaveException("метод Save() -> ошибка", new Throwable());
        }
    }

    public static String historyToString(HistoryManager manager) {
        List<Task> historyList = manager.getHistory();
        if (historyList != null) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < historyList.size() - 1; i++) {
                stringBuilder.append(historyList.get(i).getId() + ",");
            }
            stringBuilder.append(historyList.get(historyList.size() - 1).getId());

            return stringBuilder.toString();
        } else {
            return " ";
        }

    }

    public static List<Integer> historyFromString(String value) {
        List<Integer> integers = new ArrayList<>();
        if (value.equals("")) {
            return integers;
        }
        String[] historyArr = value.split(",");
        for (String strId : historyArr) {
            integers.add(Integer.parseInt(strId));
        }
        return integers;
    }

    private static Task fromString(String value) {
        String[] strings = value.split(",");
        Task taskNew = null;
        switch (strings[1]) {
            case "TASK":
                taskNew = new Task(
                        strings[2],
                        strings[4],
                        strings[5],
                        Integer.parseInt(strings[6])
                );
                taskNew.setStatus(TaskStatus.valueOf(strings[3]));
                taskNew.setId(Integer.parseInt(strings[0]));
                break;
            case "SUBTASK":
                taskNew = new Subtask(
                        strings[2],
                        strings[4],
                        strings[5],
                        Integer.parseInt(strings[6]),
                        Integer.parseInt(strings[8])
                );
                taskNew.setStatus(TaskStatus.valueOf(strings[3]));
                taskNew.setId(Integer.parseInt(strings[0]));
                break;
            case "EPIC":
                taskNew = new Epic(strings[2], strings[4]);
                taskNew.setStatus(TaskStatus.valueOf(strings[3]));
                taskNew.setId(Integer.parseInt(strings[0]));
                break;
        }
        return taskNew;
    }

    public static FileBackedTasksManager loadFromFile(File file) {
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(file);
        try (BufferedReader buf = new BufferedReader(new FileReader(file))) {
            StringBuilder stringBuilder = new StringBuilder();

            while (buf.ready()) {
                stringBuilder.append(buf.readLine());
                stringBuilder.append("\n");
            }
            String stringFromFile = stringBuilder.toString();
            String[] stack = stringFromFile.split("\n\n");

            String[] stringsOfTasks = stack[0].split("\n");
            List<Integer> historyList = historyFromString(stack[1].trim());

            Map<Integer, List<Integer>> epicToSub = new HashMap<>();

            for (int i = 1; i < stringsOfTasks.length; i++) {
                String typeOfTask = stringsOfTasks[i].split(",")[1];

                switch (typeOfTask) {
                    case "TASK":
                        fileBackedTasksManager.createTask(fromString(stringsOfTasks[i]));
                        break;
                    case "SUBTASK":
                        Subtask subtask = (Subtask) fromString(stringsOfTasks[i]);
                        List<Integer> subtaskIds = epicToSub.getOrDefault(subtask.getEpicId(), null);
                        if (subtaskIds != null) {
                            subtaskIds.add(subtask.getId());
                        } else {
                            subtaskIds = new ArrayList<>();
                            subtaskIds.add(subtask.getId());
                        }
                        epicToSub.put(subtask.getEpicId(), subtaskIds);
                        fileBackedTasksManager.createSubtask(subtask);
                        break;
                    case "EPIC":
                        Epic epic = (Epic) fromString(stringsOfTasks[i]);
                        List<Integer> subtaskList = epicToSub.getOrDefault(epic.getId(), null);
                        if (subtaskList != null) {
                            for (Integer id : subtaskList) {
                                epic.getSubtaskIds().add(id);
                            }
                        }
                        fileBackedTasksManager.createEpic(epic);
                        break;
                    default:
                        System.out.println("Задача из файла не создалась");
                }
            }
            for (Integer id : historyList) {
                fileBackedTasksManager.getFromIdTask(id);
                fileBackedTasksManager.getFromIdSubtask(id);
                fileBackedTasksManager.getFromIdEpic(id);
            }

        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка при создании файла", new Throwable());
        }

        return fileBackedTasksManager;
    }

    @Override
    public void createTask(Task task) {
        super.createTask(task);
        save();
    }

    @Override
    public void createSubtask(Subtask subtask) {
        super.createSubtask(subtask);
        save();
    }

    @Override
    public void createEpic(Epic epic) {
        super.createEpic(epic);
        save();
    }

    @Override
    public void clearTasks() {
        super.clearTasks();
        save();
    }

    @Override
    public void clearSubtasks() {
        super.clearSubtasks();
        save();
    }

    @Override
    public void clearEpics() {
        super.clearEpics();
        save();
    }

    @Override
    public void updateTask(Task task2) {
        super.updateTask(task2);
        save();
    }

    @Override
    public void updateSubtask(Subtask subtask2) {
        super.updateSubtask(subtask2);
    }

    @Override
    public void updateEpic(Epic epic2) {
        super.updateEpic(epic2);
        save();
    }

    @Override
    public void deleteTaskFromId(int id) {
        super.deleteTaskFromId(id);
        save();
    }

    @Override
    public void deleteSubtaskFromId(int id) {
        super.deleteSubtaskFromId(id);
        save();
    }

    @Override
    public void deleteEpicFromId(int id) {
        super.deleteEpicFromId(id);
        save();
    }

    @Override
    public Task getFromIdTask(int id) {
        Task task = super.getFromIdTask(id);
        save();
        return task;
    }

    @Override
    public Subtask getFromIdSubtask(int id) {
        Subtask subtask = super.getFromIdSubtask(id);
        save();
        return subtask;
    }

    @Override
    public Epic getFromIdEpic(int id) {
        Epic epic = super.getFromIdEpic(id);
        save();
        return epic;
    }
}
