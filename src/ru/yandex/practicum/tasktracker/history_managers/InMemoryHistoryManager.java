package ru.yandex.practicum.tasktracker.history_managers;

import ru.yandex.practicum.tasktracker.tasks.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {
    private final Map<Integer, Node> historyMap;

    public InMemoryHistoryManager() {
        this.historyMap = new HashMap<>();
    }

    private static class Node {
        Node prev;
        Task data;
        Node next;

        private Node(Node prev, Task data, Node next) {
            this.prev = prev;
            this.data = data;
            this.next = next;
        }
    }

    private Node head;
    private Node tail;


    private Node addLast(Task task) {
        final Node oldTail = tail;
        final Node newNode = new Node(oldTail, task, null);
        tail = newNode;
        if (oldTail == null) {
            head = newNode;
        } else {
            oldTail.next = newNode;
        }
        return newNode;
    }


    private void removeNode(Node node) {
        //меняем ссылки у соседей нода который удаляем и меняем tail, head
        if (node != null) {
            final Node preNode = node.prev;
            final Node nextNode = node.next;
            if (preNode == null) {
                this.head = nextNode;
            } else {
                preNode.next = nextNode;
            }

            if (nextNode == null) {
                this.tail = preNode;
            } else {
                nextNode.prev = preNode;
            }
        } else {
            System.out.println("Переданный в метод removeNode(Node node) node  = null");
        }
    }

    private List<Task> getTasks() {
        if (historyMap.size() == 0) {
            return null;
        } else {
            List<Task> historyList = new ArrayList<>();
            Node iterator = head;
            while (iterator != null) {
                historyList.add(iterator.data);
                iterator = iterator.next;
            }
            return historyList;
        }
    }


    @Override
    public void remove(int id) {
        Node node = historyMap.remove(id);
        if (node != null) {
            removeNode(node);
        }
    }

    @Override
    public void add(Task task) {
        if (historyMap.containsKey(task.getId())) {
            //удаляем ноду
            removeNode(historyMap.remove(task.getId()));
        }
        historyMap.put(task.getId(), addLast(task));
    }

    @Override
    public List<Task> getHistory() {
        return getTasks();
    }


}
