import java.util.ArrayList;
import java.util.HashMap;

public class TasksManager {
    private HashMap<Integer, Subtask> subtasks;
    private HashMap<Integer, Task> tasks;
    private HashMap<Integer, Epic> epics;
    private static int generatorId;

    TasksManager() {
        subtasks = new HashMap<>();
        tasks = new HashMap<>();
        epics = new HashMap<>();
        generatorId = 0;
    }

    //Получение списка всех задач.
    public HashMap<Integer, Subtask> getSubtasks() {
        return subtasks;
    }

    public HashMap<Integer, Task> getTasks() {
        return tasks;
    }

    public HashMap<Integer, Epic> getEpics() {
        return epics;
    }

    //Удаление всех задач.
    public void clearSubtasks() {
        subtasks.clear();
    }

    public void clearTasks() {
        tasks.clear();
    }

    public void clearEpics() {
        epics.clear();

    }

    //Получение по идентификатору.
    public Subtask getFromIdSubtask(int id) {
        return subtasks.get(id);
    }

    public Task getFromIdTask(int id) {
        return tasks.get(id);
    }

    public Epic getFromIdEpic(int id) {
        return epics.get(id);
    }

    //Создание. Сам объект должен передаваться в качестве параметра.
    public void createTask(Task task) {
        generatorId++;
        task.setId(generatorId);
        tasks.put(generatorId, task);
    }

    public void createSubtask(Subtask subtask) {
        generatorId++;
        subtask.setId(generatorId);
        subtasks.put(generatorId, subtask);
        Epic epic = epics.get(subtask.getEpicId());
        epic.getSubtaskIds().add(subtask.getId());
        //проверка статуса эпика в котором лежит наш сабтаск
        updateStatusOfEpic(epic);
    }

    public void createEpic(Epic epic) {
        generatorId++;
        epic.setId(generatorId);
        epics.put(generatorId, epic);
    }

    //Обновление. Новая версия объекта с верным идентификатором передаётся в виде параметра.
    public void updateTask(Task task2) {
        tasks.put(task2.getId(), task2);
    }

    public void updateSubtask(Subtask subtask2) {
        subtasks.put(subtask2.getId(), subtask2);

        //Когда меняется статус любой подзадачи в эпике,
        // вам необходимо проверить, что статус эпика изменится соответствующим образом.
        Epic epic = epics.get(subtask2.getEpicId());
        updateStatusOfEpic(epic);

    }

    public void updateEpic(Epic epic2) {
        epics.put(epic2.getId(), epic2);

        // статус эпика
        updateStatusOfEpic(epic2);
    }

    //метод, проверяющий статус эпика
    private void updateStatusOfEpic(Epic epic) {
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
    public void deleteTaskFromId(int id) {
        tasks.remove(id);
    }

    public void deleteSubtaskFromId(int id) {
        subtasks.remove(id);
    }

    public void deleteEpicFromId(int id) {
        epics.remove(id);
    }

    //Получение списка всех подзадач определённого эпика.
    public ArrayList<Subtask> getSubtaskList(Epic epic) {
        ArrayList<Integer> subtaskIdsList = epic.getSubtaskIds();
        ArrayList<Subtask> listOfSubtasks = new ArrayList<>();
        for (Integer subId : subtaskIdsList) {
            listOfSubtasks.add(subtasks.get(subId));
        }
        return listOfSubtasks;
    }


}




