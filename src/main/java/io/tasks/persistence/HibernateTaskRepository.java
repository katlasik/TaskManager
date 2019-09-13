package io.tasks.persistence;

import io.tasks.model.Category;
import io.tasks.model.Status;
import io.tasks.model.Task;
import java.util.*;
import javax.persistence.EntityManager;

public class HibernateTaskRepository implements TaskRepository {

  private EntityManager entityManager;

  public HibernateTaskRepository(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  public List<Task> getAll() {
    return entityManager.createQuery("from Task", Task.class).getResultList();
  }

  @Override
  public void update(Task category) {
    entityManager.getTransaction().begin();
    entityManager.merge(category);
    entityManager.getTransaction().commit();
  }

  @Override
  public void save(Task category) {
    entityManager.getTransaction().begin();
    entityManager.persist(category);
    entityManager.getTransaction().commit();
  }

  @Override
  public void delete(Long id) {
    entityManager.getTransaction().begin();
    entityManager.remove(entityManager.getReference(Task.class, id));
    entityManager.getTransaction().commit();
  }

  @Override
  public Optional<Task> get(Long id) {
    return Optional.ofNullable(entityManager.find(Task.class, id));
  }

  @Override
  public List<Task> getPlannedTasks() {
    return entityManager
        .createQuery("from Task t where t.status = :status", Task.class)
        .setParameter("status", Status.PLANNED)
        .getResultList();
  }

  @Override
  public List<Task> getInProgressTasks() {
    return entityManager
        .createQuery("from Task t where t.status = :status", Task.class)
        .setParameter("status", Status.IN_PROGRESS)
        .getResultList();
  }

  @Override
  public List<Task> getByCategory(Category category) {
    return entityManager
        .createQuery("from Task t where t.category = :category", Task.class)
        .setParameter("category", category)
        .getResultList();
  }
}
