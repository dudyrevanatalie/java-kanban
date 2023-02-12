package Tests;

import ru.yandex.practicum.tasktracker.task_managers.InMemoryTaskManager;


class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager> {

    @Override
    InMemoryTaskManager createManager() {
        return new InMemoryTaskManager();
    }

}