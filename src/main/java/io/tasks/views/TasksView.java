package io.tasks.views;

import io.tasks.model.Category;
import io.tasks.model.Status;
import io.tasks.model.Task;
import io.tasks.persistence.CategoryRepository;
import io.tasks.persistence.TaskRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class TasksView implements Prompts {

  private final TaskRepository taskRepository;
  private final CategoryRepository categoryRepository;

  public TasksView(TaskRepository taskRepository, CategoryRepository categoryRepository) {
    this.taskRepository = taskRepository;
    this.categoryRepository = categoryRepository;
  }

  public void showTasks(Scanner scanner) {

    List<Category> categories = categoryRepository.getAll();
    List<Task> plannedTasks = taskRepository.getPlannedTasks();
    List<Task> inProgressTasks = taskRepository.getInProgressTasks();

    if (categories.size() == 0) {
      System.out.println("Dodaj kategorię!");
      System.out.println();
      return;
    }

    if (plannedTasks.isEmpty() && inProgressTasks.isEmpty()) {
      System.out.println("Brak zadań!");
      System.out.println();
      return;
    }

    int i = 1;

    if (!plannedTasks.isEmpty()) {
      System.out.println();
      System.out.println("Zaplanowane zadania:");
      for (Task task : plannedTasks) {
        System.out.println(
            String.format("%d. [%s] %s", i++, task.getCategory().getName(), task.getDescription()));
      }
    }

    if (!inProgressTasks.isEmpty()) {
      System.out.println();
      System.out.println("Rozpoczętę zadania:");
      for (Task task : inProgressTasks) {
        System.out.println(
            String.format("%d. [%s] %s", i++, task.getCategory().getName(), task.getDescription()));
      }
    }

    System.out.println();
    System.out.println("Wybierz zadanie:");

    plannedTasks.addAll(inProgressTasks);

    int choice = choice(plannedTasks.size(), scanner);
    Task chosen = plannedTasks.get(choice - 1);
    System.out.printf(
        "Wybrałeś zadanie %s: '[%s] %s'.",
        choice, chosen.getCategory().getName(), chosen.getDescription());

    boolean isStarted = chosen.getStatus().equals(Status.IN_PROGRESS);

    System.out.println();
    System.out.println("Co chcesz zrobić?");
    System.out.printf(" 1. Zmień status zadania na %s.", isStarted ? "zakończone" : "rozpoczęte");
    System.out.println();
    System.out.println(" 2. Anuluj zadanie.");
    System.out.println(" 3. Edytuj zadanie.");
    System.out.println(" 4. Wyjdź.");

    switch (choice(4, scanner)) {
      case 1:
        if (isStarted) {
          chosen.setStatus(Status.COMPLETED);
        } else {
          chosen.setStatus(Status.IN_PROGRESS);
        }
        taskRepository.update(chosen);
        break;
      case 2:
        chosen.setCanceledAt(LocalDate.now());
        taskRepository.update(chosen);
        break;
      case 3:
        Task updated = editTask(scanner, categories);
        updated.setId(chosen.getId());
        taskRepository.update(updated);
        break;
      case 4:
        break;
    }
  }

  private Task editTask(Scanner scanner, List<Category> categories) {

    System.out.println("Podaj opis zadania:");
    String description = text(scanner);
    System.out.println("Podaj planowany termin wykonania zadania (yyyy-mm-dd):");
    LocalDate deadline = date(scanner);
    System.out.println("Wybierz kategorię:");
    int i = 1;
    for (Category category : categories) {
      System.out.println(String.format("%d. %s", i++, category.getName()));
    }
    Category category = categories.get(choice(categories.size(), scanner) - 1);

    return new Task(null, description, deadline, LocalDate.now(), category, Status.PLANNED, null);
  }

  public void newTask(Scanner scanner, List<Category> categories) {

    if (categories.size() == 0) {
      System.out.println("Dodaj kategorię!");
      return;
    }

    Task task = editTask(scanner, categories);

    System.out.printf(
        "Chcesz dodać zadanie '%s' z kategorii '%s' zaplanowanene na '%s'? [t/n]",
        task.getDescription(), task.getCategory().getName(), task.getDeadline());

    if (yesNo(scanner)) {
      taskRepository.save(task);
      System.out.println("Zadanie zostało dodane.");
    }
  }
}
