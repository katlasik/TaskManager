package io.tasks.persistence;


import java.util.List;
import java.util.Optional;

public interface Repository<T> {
  List<T> getAll();

  void update(T task);

  void save(T task);

  void delete(Long id);

  Optional<T> get(Long id);
}
