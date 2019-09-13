package io.tasks;

import io.tasks.persistence.CategoryRepository;
import io.tasks.persistence.TaskRepository;
import io.tasks.views.CategoryView;
import io.tasks.views.Prompts;
import io.tasks.views.TasksView;

import java.util.Scanner;

public class Application implements Prompts {

    private final TaskRepository taskRepository;
    private final CategoryRepository categoryRepository;
    private final TasksView taskView;
    private final CategoryView categoryView;

    public Application(TaskRepository taskRepository, CategoryRepository categoryRepository) {
        this.taskRepository = taskRepository;
        this.categoryRepository = categoryRepository;
        this.categoryView = new CategoryView(taskRepository, categoryRepository);
        this.taskView = new TasksView(taskRepository, categoryRepository);
    }

    void start() {

        try (Scanner scanner = new Scanner(System.in)) {
            boolean leave = false;

            while (!leave) {
                System.out.println("Witaj, wybierz opcję:");
                System.out.println(" 1. Wyświetl zadania.");
                System.out.println(" 2. Dodaj nowe zadanie.");
                System.out.println(" 3. Edytuj kategorie.");
                System.out.println(" 4. Wyjdź z aplikacji.");

                switch (choice(4, scanner)) {
                    case 1:
                        taskView.showTasks(scanner);
                        break;
                    case 2:
                        taskView.newTask(scanner, categoryRepository.getAll());
                        break;
                    case 3:
                        categoryView.showCategories(scanner);
                        break;
                    case 4:
                        leave = true;
                        break;
                }
            }
        }

    }



}
