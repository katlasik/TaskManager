package io.tasks.persistence;

import io.tasks.model.Category;
import io.tasks.model.Status;
import io.tasks.model.Task;
import java.sql.Date;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public class JdbcTaskRepository implements TaskRepository {

  private static final RowMapper<Task> MAPPER =
      (resultSet, i) ->
          new Task(
              resultSet.getLong("id"),
              resultSet.getString("description"),
              resultSet.getDate("created_at").toLocalDate(),
              resultSet.getDate("deadline").toLocalDate(),
              new Category(resultSet.getLong("category_id"), resultSet.getString("category_name")),
              Status.valueOf(resultSet.getString("status")),
              Optional.ofNullable(resultSet.getDate("canceled_at"))
                  .map(Date::toLocalDate)
                  .orElse(null));

  private static final ResultSetExtractor<Optional<Task>> EXTRACTOR =
      resultSet -> {
        if (resultSet.next()) {
          return Optional.of(
              new Task(
                  resultSet.getLong("id"),
                  resultSet.getString("description"),
                  resultSet.getDate("created_at").toLocalDate(),
                  resultSet.getDate("deadline").toLocalDate(),
                  new Category(
                      resultSet.getLong("category_id"), resultSet.getString("category_name")),
                  Status.valueOf(resultSet.getString("status")),
                  resultSet.getDate("canceled_at").toLocalDate()));
        } else {
          return Optional.empty();
        }
      };

  private final NamedParameterJdbcTemplate template;

  public JdbcTaskRepository(DataSource dataSource) {
    this.template = new NamedParameterJdbcTemplate(dataSource);
  }

  @Override
  public List<Task> getAll() {
    return template.query(
        "SELECT "
            + "task.id, "
            + "canceled_at, "
            + "created_at, "
            + "deadline, "
            + "description, "
            + "status, "
            + "category_id, "
            + "name AS category_name "
            + "FROM task JOIN category ON category.id = task.category_id",
        MAPPER);
  }

  @Override
  public void update(Task task) {
    template.update(
        "UPDATE task SET "
            + "canceled_at = :canceled_at, "
            + "created_at = :created_at, "
            + "deadline = :deadline, "
            + "description = :description, "
            + "status = :status, "
            + "category_id = :category_id "
            + "WHERE id = :id",
        new MapSqlParameterSource(
                "canceled_at",
                Optional.ofNullable(task.getCanceledAt()).map(Date::valueOf).orElse(null))
            .addValue("created_at", Date.valueOf(task.getCreatedAt()))
            .addValue("deadline", Date.valueOf(task.getDeadline()))
            .addValue("description", task.getDescription())
            .addValue("status", task.getStatus().toString())
            .addValue("category_id", task.getCategory().getId())
            .addValue("id", task.getId()));
  }

  @Override
  public void save(Task task) {
    template.update(
        "INSERT INTO task("
            + "canceled_at,"
            + "created_at,"
            + "deadline,"
            + "description,"
            + "status,"
            + "category_id"
            + ") VALUES("
            + ":canceled_at, "
            + ":created_at, "
            + ":deadline, "
            + ":description, "
            + ":status, "
            + ":category_id"
            + ")",
        new MapSqlParameterSource(
                "canceled_at",
                task.getCanceledAt() != null ? Date.valueOf(task.getCanceledAt()) : null)
            .addValue("created_at", Date.valueOf(task.getCreatedAt()))
            .addValue("deadline", Date.valueOf(task.getDeadline()))
            .addValue("description", task.getDescription())
            .addValue("status", task.getStatus().toString())
            .addValue("category_id", task.getCategory().getId()));
  }

  @Override
  public void delete(Long id) {
    template.update("DELETE FROM task WHERE id = :id", new MapSqlParameterSource("id", id));
  }

  @Override
  public Optional<Task> get(Long id) {
    return template.query(
        "SELECT "
            + "task.id, "
            + "canceled_at, "
            + "created_at, "
            + "deadline, "
            + "description, "
            + "status, "
            + "category_id, "
            + "name AS category_name "
            + "FROM task JOIN category ON category.id = task.category_id "
            + "WHERE id = :id",
        new MapSqlParameterSource("id", id),
        EXTRACTOR);
  }

  @Override
  public List<Task> getPlannedTasks() {
    return template.query(
        "SELECT "
            + "task.id, "
            + "canceled_at, "
            + "created_at, "
            + "deadline, "
            + "description, "
            + "status, "
            + "category_id, "
            + "name AS category_name "
            + "FROM task JOIN category ON category.id = task.category_id "
            + "WHERE status = 'PLANNED'",
        MAPPER);
  }

  @Override
  public List<Task> getInProgressTasks() {
    return template.query(
        "SELECT "
            + "task.id, "
            + "canceled_at, "
            + "created_at, "
            + "deadline, "
            + "description, "
            + "status, "
            + "category_id, "
            + "name AS category_name "
            + "FROM task JOIN category ON category.id = task.category_id "
            + "WHERE status = 'IN_PROGRESS'",
        MAPPER);
  }

  @Override
  public List<Task> getByCategory(Category category) {
    return template.query(
        "SELECT "
            + "task.id, "
            + "canceled_at, "
            + "created_at, "
            + "deadline, "
            + "description, "
            + "status, "
            + "category_id, "
            + "name AS category_name "
            + "FROM task JOIN category ON category.id = task.category_id "
            + "WHERE category_id = :id",
        new MapSqlParameterSource("id", category.getId()),
        MAPPER);
  }
}
