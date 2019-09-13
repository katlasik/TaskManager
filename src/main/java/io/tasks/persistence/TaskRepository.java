package io.tasks.persistence;

import io.tasks.model.Category;
import io.tasks.model.Task;

import java.util.Collection;
import java.util.List;

public interface TaskRepository extends Repository<Task> {

    List<Task> getPlannedTasks();
    List<Task> getInProgressTasks();
    List<Task> getByCategory(Category category);
}
