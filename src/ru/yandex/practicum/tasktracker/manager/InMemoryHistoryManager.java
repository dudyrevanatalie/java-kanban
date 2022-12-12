package ru.yandex.practicum.tasktracker.manager;

import ru.yandex.practicum.tasktracker.tasks.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {
    private final Map<Integer, Node> historyMap;

    public InMemoryHistoryManager() {
        this.historyMap = new HashMap<>();
    }

    //я так понимаю, тут можно было использовать LinkedHashMap? Но я решила попробывать сама создать подобие
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
        final Node preNode = node.prev;
        final Node nextNode = node.next;
        if (preNode == null) {
            this.head = nextNode;
        } else {
            preNode.next = node.next;
        }

        if (nextNode == null) {
            this.tail = preNode;
        } else {
            nextNode.prev = node.prev;
        }

    }

    private List<Task> getTasks() {
        if (historyMap.size() == 0) {
            return null;//перекладываем из мапы в arraylist
        } else {
            List<Task> historyList = new ArrayList<>();
            Node iterator = head;
            historyList.add(iterator.data);
            while (iterator != tail) {
                iterator = iterator.next;
                historyList.add(iterator.data);
            }
            return historyList;
        }
    }


    @Override
    public void remove(int id) {
        Node node = historyMap.get(id);
        if (node != null) {
            removeNode(node);
            historyMap.remove(id);
        }
    }

    @Override
    public void add(Task task) {
        if (historyMap.containsKey(task.getId())) {
            //удаляем ноду
            removeNode(historyMap.get(task.getId()));
            historyMap.remove(task.getId());
        }
        historyMap.put(task.getId(), addLast(task));
    }

    @Override
    public List<Task> getHistory() {
        return getTasks();
    }


}
