package io.tasks;

import com.mysql.cj.jdbc.MysqlDataSource;
import io.tasks.persistence.CategoryRepository;
import io.tasks.persistence.JdbcCategoryRepository;
import io.tasks.persistence.JdbcTaskRepository;
import io.tasks.persistence.TaskRepository;
import javax.sql.DataSource;

public class EntryPoint {

  public static DataSource createDataSource() {

    MysqlDataSource dataSource = new MysqlDataSource();

    dataSource.setUser("tasks_user");
    dataSource.setPassword("pass");
    dataSource.setUrl("jdbc:mysql://localhost:3306/tasks");
    return dataSource;
  }

  public static void main(String[] args) {

    TaskRepository taskRepository = new JdbcTaskRepository(createDataSource());
    CategoryRepository categoryRepository = new JdbcCategoryRepository(createDataSource());

    Application application = new Application(taskRepository, categoryRepository);

    application.start();
  }
}
