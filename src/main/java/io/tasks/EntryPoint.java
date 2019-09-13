package io.tasks;

import io.tasks.persistence.CategoryRepository;
import io.tasks.persistence.HibernateCategoryRepository;
import io.tasks.persistence.HibernateTaskRepository;
import io.tasks.persistence.TaskRepository;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntryPoint {

  public static void main(String[] args) {

    EntityManagerFactory factory = Persistence.createEntityManagerFactory("tasks");

    TaskRepository taskRepository = new HibernateTaskRepository(factory.createEntityManager());
    CategoryRepository categoryRepository =
        new HibernateCategoryRepository(factory.createEntityManager());

    Application application = new Application(taskRepository, categoryRepository);

    application.start();
  }
}
