package io.tasks.persistence;

import io.tasks.model.Category;

import java.util.Optional;

public interface CategoryRepository extends Repository<Category> {
    Optional<Category> getByName(String name);
}
