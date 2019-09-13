package io.tasks.persistence;

import io.tasks.model.Category;
import io.tasks.model.Status;
import io.tasks.model.Task;
import java.util.*;
import java.util.stream.Collectors;

public class MemoryTaskRepository implements TaskRepository {

  private HashMap<Long, Task> memory = new HashMap<>();

  private long sequence = 1;

  @Override
  public List<Task> getAll() {
    return new ArrayList<>(memory.values());
  }

  @Override
  public void update(Task task) {
    memory.put(task.getId(), task);
  }

  @Override
  public void save(Task task) {
    long next = sequence++;
    task.setId(next);
    memory.put(next, task);
  }

  @Override
  public void delete(Long id) {
    memory.remove(id);
  }

  @Override
  public Optional<Task> get(Long id) {
    return Optional.ofNullable(memory.get(id));
  }

  @Override
  public List<Task> getPlannedTasks() {
    return getAll().stream()
        .filter(t -> t.getStatus().equals(Status.PLANNED) && t.getCanceledAt() == null)
        .collect(Collectors.toList());
  }

  @Override
  public List<Task> getInProgressTasks() {
    return getAll().stream()
        .filter(t -> t.getStatus().equals(Status.IN_PROGRESS) && t.getCanceledAt() == null)
        .collect(Collectors.toList());
  }

  @Override
  public List<Task> getByCategory(Category category) {
    return memory.values().stream()
        .filter(t -> t.getCategory().equals(category))
        .collect(Collectors.toList());
  }
}
