package io.tasks.persistence;

import io.tasks.model.Category;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public class JdbcCategoryRepository implements CategoryRepository {

  private static final RowMapper<Category> MAPPER =
      (resultSet, i) -> new Category(resultSet.getLong("id"), resultSet.getString("name"));

  private static final ResultSetExtractor<Optional<Category>> EXTRACTOR =
      resultSet -> {
        if (resultSet.next()) {
          return Optional.of(new Category(resultSet.getLong("id"), resultSet.getString("name")));
        } else {
          return Optional.empty();
        }
      };

  private final NamedParameterJdbcTemplate template;

  public JdbcCategoryRepository(DataSource dataSource) {
    this.template = new NamedParameterJdbcTemplate(dataSource);
  }

  @Override
  public List<Category> getAll() {
    return template.query("SELECT id, name FROM category", MAPPER);
  }

  @Override
  public void update(Category category) {
    template.update(
        "UPDATE category SETname = :name, WHERE id = :id",
        new MapSqlParameterSource("name", category.getName()).addValue("id", category.getId()));
  }

  @Override
  public void save(Category category) {
    System.out.println(category.getName());
    template.update(
        "INSERT INTO category(name) VALUES(:name)",
        new MapSqlParameterSource("name", category.getName()));
  }

  @Override
  public void delete(Long id) {
    template.update("DELETE FROM category WHERE id=:id", new MapSqlParameterSource("id", id));
  }

  @Override
  public Optional<Category> get(Long id) {
    return template.query(
        "SELECT id, name FROM category WHERE id = :id",
        new MapSqlParameterSource("id", id),
        EXTRACTOR);
  }

  @Override
  public Optional<Category> getByName(String name) {
    return template.query(
        "SELECT id, name FROM category WHERE name = :name",
        new MapSqlParameterSource("name", name),
        EXTRACTOR);
  }
}
