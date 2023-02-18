package ru.yandex.practicum.tasktracker.server;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import ru.yandex.practicum.tasktracker.task_managers.Managers;
import ru.yandex.practicum.tasktracker.task_managers.TasksManager;
import ru.yandex.practicum.tasktracker.tasks.Epic;
import ru.yandex.practicum.tasktracker.tasks.Subtask;
import ru.yandex.practicum.tasktracker.tasks.Task;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Set;

public class HttpTaskServer {
    private final int PORT = 8080;

    TasksManager manager = Managers.getDefault();
    HttpServer httpServer;
    Gson gson = new Gson();

    public HttpTaskServer() throws IOException, InterruptedException {
    }

    public void start() throws IOException {
        httpServer = HttpServer.create();
        httpServer.bind(new InetSocketAddress(PORT), 0);
        httpServer.createContext("/tasks/task", new TaskHandler());
        httpServer.createContext("/tasks/subtask", new SubtaskHandler());
        httpServer.createContext("/tasks/epic", new EpicHandler());
        httpServer.createContext("/tasks/subtask/epic", new SubtaskByEpicHandler());
        httpServer.createContext("/tasks/history", new HistoryHandler());
        httpServer.createContext("/tasks", new TasksHandler());
        httpServer.start();

        System.out.println("Сервер запущен на порт: " + PORT);
    }

    private void writeResponse(HttpExchange exchange,
                               String responseString,
                               int responseCode) throws IOException {
        if (responseString.isBlank()) {
            exchange.sendResponseHeaders(responseCode, 0);
        } else {
            byte[] bytes = responseString.getBytes(StandardCharsets.UTF_8);
            exchange.sendResponseHeaders(responseCode, bytes.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(bytes);
            }
        }
        exchange.close();
    }

    public void stop() {
        httpServer.stop(0);
    }


    class TaskHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String method = exchange.getRequestMethod();

            switch (method) {
                case "GET":
                    String getQuery = exchange.getRequestURI().getQuery();
                    if (getQuery == null) {
                        List<Task> tasks = manager.getTasks();
                        String tasksJson = gson.toJson(tasks);
                        writeResponse(exchange, tasksJson, 200);
                    } else {
                        int id = Integer.parseInt(getQuery.split("=")[1]);
                        Task task = manager.getFromIdTask(id);
                        if (task == null) {
                            writeResponse(exchange, "Задача с запрошенным id не найдена", 404);
                            break;
                        }
                        String taskJson = gson.toJson(task);
                        writeResponse(exchange, taskJson, 200);
                    }
                    break;
                case "POST":
                    InputStream is = exchange.getRequestBody();
                    String requestBody = new String(is.readAllBytes(), StandardCharsets.UTF_8);
                    if (requestBody.isBlank()) {
                        writeResponse(exchange, "Данные не переданы", 404);
                        break;
                    }
                    Task task = gson.fromJson(requestBody, Task.class);
                    if (manager.getFromIdTask(task.getId()) == null) {
                        manager.createTask(task);
                        writeResponse(exchange, "Задача успешно добавлена", 200);
                    } else {
                        manager.updateTask(task);
                        writeResponse(exchange, "Задача успешно обновлена", 200);
                    }
                    break;
                case "DELETE":
                    String deleteQuery = exchange.getRequestURI().getQuery();
                    if (deleteQuery == null) {
                        manager.clearTasks();
                        writeResponse(exchange, "Все задачи успешно удалены", 200);
                    } else {
                        int id = Integer.parseInt(deleteQuery.split("=")[1]);
                        if (manager.getFromIdTask(id) == null) {
                            writeResponse(exchange, "Задача с id:" + id + " не найдена", 404);
                            break;
                        }
                        manager.deleteTaskFromId(id);
                        writeResponse(exchange, "Задача id:" + id + " успешно удалена", 200);
                    }
                    break;
                default:
                    writeResponse(exchange, "Неизвестный метод", 404);
            }
        }
    }

    class SubtaskHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String method = exchange.getRequestMethod();
            switch (method) {
                case "GET":
                    String getQuery = exchange.getRequestURI().getQuery();
                    if (getQuery == null) {
                        List<Subtask> subtasks = manager.getSubtasks();
                        String subtasksJson = gson.toJson(subtasks);
                        writeResponse(exchange, subtasksJson, 200);
                    } else {
                        int id = Integer.parseInt(getQuery.split("=")[1]);
                        Subtask subtask = manager.getFromIdSubtask(id);
                        if (subtask == null) {
                            writeResponse(exchange, "Подзадача с запрошенным id не найдена", 404);
                            break;
                        }
                        String subtaskJson = gson.toJson(subtask);
                        writeResponse(exchange, subtaskJson, 200);
                    }
                    break;
                case "POST":
                    InputStream is = exchange.getRequestBody();
                    String requestBody = new String(is.readAllBytes(), StandardCharsets.UTF_8);
                    if (requestBody.isBlank()) {
                        writeResponse(exchange, "Данные не переданы", 404);
                        break;
                    }
                    Subtask subtask = gson.fromJson(requestBody, Subtask.class);
                    if (manager.getFromIdSubtask(subtask.getId()) == null) {
                        manager.createSubtask(subtask);
                        writeResponse(exchange, "Подзадача успешно добавлена", 200);
                    } else {
                        manager.updateSubtask(subtask);
                        writeResponse(exchange, "Подзадача успешно обновлена", 200);
                    }
                    break;
                case "DELETE":
                    String deleteQuery = exchange.getRequestURI().getQuery();
                    if (deleteQuery == null) {
                        manager.clearSubtasks();
                        writeResponse(exchange, "Все подзадачи успешно удалены", 200);
                    } else {
                        int id = Integer.parseInt(deleteQuery.split("=")[1]);
                        if (manager.getFromIdSubtask(id) == null) {
                            writeResponse(exchange, "Подзадача с id:" + id + " не найдена", 404);
                            break;
                        }
                        manager.deleteSubtaskFromId(id);
                        writeResponse(exchange, "Подзадача id:" + id + " успешно удалена", 200);
                    }
                    break;
                default:
                    writeResponse(exchange, "Неизвестный метод", 404);
            }
        }
    }

    class EpicHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String method = exchange.getRequestMethod();
            switch (method) {
                case "GET":
                    String getQuery = exchange.getRequestURI().getQuery();
                    if (getQuery == null) {
                        List<Epic> epics = manager.getEpics();
                        String epicsJson = gson.toJson(epics);
                        writeResponse(exchange, epicsJson, 200);
                    } else {
                        int id = Integer.parseInt(getQuery.split("=")[1]);
                        Epic epic = manager.getFromIdEpic(id);
                        if (epic == null) {
                            writeResponse(exchange, "Эпик с запрошенным id не найдена", 404);
                            break;
                        }
                        String epicJson = gson.toJson(epic);
                        writeResponse(exchange, epicJson, 200);
                    }
                    break;
                case "POST":
                    InputStream is = exchange.getRequestBody();
                    String requestBody = new String(is.readAllBytes(), StandardCharsets.UTF_8);
                    if (requestBody.isBlank()) {
                        writeResponse(exchange, "Данные не переданы", 404);
                        break;
                    }
                    Epic epic = gson.fromJson(requestBody, Epic.class);
                    if (manager.getFromIdEpic(epic.getId()) == null) {
                        manager.createEpic(epic);
                        writeResponse(exchange, "Эпик успешно добавлен", 200);
                    } else {
                        manager.updateEpic(epic);
                        writeResponse(exchange, "Эпик успешно обновлен", 200);
                    }
                    break;
                case "DELETE":
                    String deleteQuery = exchange.getRequestURI().getQuery();
                    if (deleteQuery == null) {
                        manager.clearEpics();
                        writeResponse(exchange, "Все эпики успешно удалены", 200);
                    } else {
                        int id = Integer.parseInt(deleteQuery.split("=")[1]);
                        if (manager.getFromIdEpic(id) == null) {
                            writeResponse(exchange, "Эпик с id:" + id + " не найден", 404);
                            break;
                        }
                        manager.deleteEpicFromId(id);
                        writeResponse(exchange, "Эпик id:" + id + " успешно удален", 200);
                    }
                    break;
            }
        }
    }

    class SubtaskByEpicHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            int id = Integer.parseInt(exchange.getRequestURI().getQuery().split("=")[1]);
            Epic epic = manager.getFromIdEpic(id);
            if (epic == null) {
                writeResponse(exchange, "Эпик с id: " + id + " не найден", 404);
            }
            List<Subtask> subtasks = manager.getSubtaskList(epic);
            String subtasksJson = gson.toJson(subtasks);
            writeResponse(exchange, subtasksJson, 200);
        }
    }

    class HistoryHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            List<Task> history = manager.getHistory();
            String historyJson = gson.toJson(history);
            writeResponse(exchange, historyJson, 200);
        }
    }

    class TasksHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            List<Task> prioritizedTasks = manager.getPrioritizedTasks();
            String tasksJson = gson.toJson(prioritizedTasks);
            writeResponse(exchange, tasksJson, 200);
        }
    }

}
