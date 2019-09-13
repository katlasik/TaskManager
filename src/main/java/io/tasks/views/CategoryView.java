package io.tasks.views;

import io.tasks.persistence.CategoryRepository;
import io.tasks.persistence.TaskRepository;
import io.tasks.model.Category;

import java.util.List;
import java.util.Scanner;

public class CategoryView implements Prompts{

    private final TaskRepository taskRepository;
    private final CategoryRepository categoryRepository;

    public CategoryView(TaskRepository taskRepository, CategoryRepository categoryRepository) {
        this.taskRepository = taskRepository;
        this.categoryRepository = categoryRepository;
    }

    public void showCategories(Scanner scanner) {
        List<Category> categories = categoryRepository.getAll();

        if(categories.isEmpty()) {
            System.out.println("Brak dodanych kategorii.");
        } else {
            System.out.println("Twoje kategorie:");
            int i = 1;
            for (Category category: categories) {
                System.out.println(String.format("%d. %s", i++, category.getName()));
            }
        }

        System.out.println();
        System.out.println("Co chcesz zrobić?");
        System.out.println(" 1. Dodaj kategorię.");
        System.out.println(" 2. Usuń kategorię.");
        System.out.println(" 3. Wyjdź.");


        switch (choice(3, scanner)) {
            case 1:
                addCategory(scanner);
                break;
            case 2:
                removeCategory(scanner, categories);
                break;
            case 3:
                break;
        }

    }

    private void addCategory(Scanner scanner) {
        System.out.println("Podaj nazwę kategorii:");

        String name = text(scanner);
        if (categoryRepository.getByName(name).isPresent()) {
            System.out.println("Taka kategoria już istnieje!\n");
        } else {
            categoryRepository.save(new Category(null, name));
            System.out.printf("Kategoria '%s' została dodana.", name);
            System.out.println("\n");
        }
    }

    private void removeCategory(Scanner scanner, List<Category> categories) {
        System.out.println("Wybierz kategorię do usunięcia:");

        Category category = categories.get(choice(categories.size(), scanner) - 1);

        if (taskRepository.getByCategory(category).isEmpty()) {
            categoryRepository.delete(category.getId());
            System.out.printf("Kategoria %s została usunięta.\n\n", category.getName());
        } else {
            System.out.println("Ta kategoria jest przyporządkowana do zadań!\n");
        }

    }

}
