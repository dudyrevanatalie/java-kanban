package ru.yandex.practicum.tasktracker;

import ru.yandex.practicum.tasktracker.server.HttpTaskServer;
import ru.yandex.practicum.tasktracker.server.KVServer;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        try {
            new KVServer().start();
            new HttpTaskServer().start();
        } catch (IOException e) {
            System.out.println("Ошибка при запуске сервера");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}