package io.tasks.persistence;

import io.tasks.model.Category;
import java.util.*;

public class MemoryCategoryRepository implements CategoryRepository {

  private HashMap<Long, Category> memory = new HashMap<>();

  private long sequence = 1;

  @Override
  public List<Category> getAll() {
    return new ArrayList<>(memory.values());
  }

  @Override
  public void update(Category category) {
    memory.put(category.getId(), category);
  }

  @Override
  public void save(Category category) {
    long next = sequence++;
    category.setId(next);
    memory.put(next, category);
  }

  @Override
  public void delete(Long id) {
    memory.remove(id);
  }

  @Override
  public Optional<Category> get(Long id) {
    return Optional.ofNullable(memory.get(id));
  }

  @Override
  public Optional<Category> getByName(String name) {
    return memory.values().stream().filter(s -> s.getName().equals(name)).findFirst();
  }
}
