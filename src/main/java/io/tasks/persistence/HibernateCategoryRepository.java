package io.tasks.persistence;

import io.tasks.model.Category;
import java.util.*;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

public class HibernateCategoryRepository implements CategoryRepository {

  private final EntityManager entityManager;

  public HibernateCategoryRepository(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public List<Category> getAll() {
    return entityManager.createQuery("from Category", Category.class).getResultList();
  }

  @Override
  public void update(Category category) {
    entityManager.getTransaction().begin();
    entityManager.merge(category);
    entityManager.getTransaction().commit();
  }

  @Override
  public void save(Category category) {
    entityManager.getTransaction().begin();
    entityManager.persist(category);
    entityManager.getTransaction().commit();
  }

  @Override
  public void delete(Long id) {
    entityManager.getTransaction().begin();
    entityManager.remove(entityManager.getReference(Category.class, id));
    entityManager.getTransaction().commit();
  }

  @Override
  public Optional<Category> get(Long id) {
    return Optional.ofNullable(entityManager.find(Category.class, id));
  }

  @Override
  public Optional<Category> getByName(String name) {
    try {
      return Optional.of(
          entityManager
              .createQuery("from Category c where c.name = :name", Category.class)
              .setParameter("name", name)
              .getSingleResult());
    } catch (NoResultException e) {
      return Optional.empty();
    }
  }
}
