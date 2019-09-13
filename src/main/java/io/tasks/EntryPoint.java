package io.tasks;

import io.tasks.persistence.CategoryRepository;
import io.tasks.persistence.MemoryCategoryRepository;
import io.tasks.persistence.MemoryTaskRepository;
import io.tasks.persistence.TaskRepository;

public class EntryPoint {

    public static void main(String[] args) {

        TaskRepository taskRepository = new MemoryTaskRepository();
        CategoryRepository categoryRepository = new MemoryCategoryRepository();

        Application application = new Application(taskRepository, categoryRepository);

        application.start();
    }
}
