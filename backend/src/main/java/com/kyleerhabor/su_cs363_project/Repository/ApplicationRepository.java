package com.kyleerhabor.su_cs363_project.Repository;

import com.kyleerhabor.su_cs363_project.Controller.Model.CreateTitleRequestControllerModel;
import com.kyleerhabor.su_cs363_project.Controller.Model.CreateUserRequestControllerModel;
import com.kyleerhabor.su_cs363_project.Controller.Model.CreateUserTitleRequestControllerModel;
import com.kyleerhabor.su_cs363_project.Repository.Model.FindTitleByUUIDRepositoryModel;
import com.kyleerhabor.su_cs363_project.Repository.Model.FindTitlesRepositoryModel;
import com.kyleerhabor.su_cs363_project.Repository.Model.FindUserByUUIDRepositoryModel;
import com.kyleerhabor.su_cs363_project.Repository.Model.FindUsersRepositoryModel;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class ApplicationRepository {
  @Autowired private NamedParameterJdbcTemplate jdbc;

  private <T> @Nullable T queryForObject(final Supplier<T> supplier) {
    try {
      return supplier.get();
    } catch (final EmptyResultDataAccessException e) {
      return null;
    }
  }

  private Number insert(final String sql, final SqlParameterSource parameters) {
    final var keyHolder = new GeneratedKeyHolder();
    jdbc.update(sql, parameters, keyHolder);

    return keyHolder.getKey();
  }

  public Number createTitle(final UUID uuid, final CreateTitleRequestControllerModel title) {
    final var sql = "INSERT INTO titles (uuid, name, description) VALUES (:uuid, :name, :description)";
    final var parameters = new MapSqlParameterSource();
    parameters.addValue("uuid", uuid.toString());
    parameters.addValue("name", title.name());
    parameters.addValue("description", title.description());

    return insert(sql, parameters);
  }

  public @Nullable Number findTitleIDByUUID(final UUID uuid) {
    final var sql = "SELECT id FROM titles WHERE uuid = :uuid";
    final var parameters = new MapSqlParameterSource();
    parameters.addValue("uuid", uuid.toString());

    return queryForObject(() -> jdbc.queryForObject(sql, parameters, Number.class));
  }

  public @Nullable List<FindTitlesRepositoryModel> findTitles() {
    final var sql = """
      SELECT
        uuid AS title_uuid,
        name AS title_name,
        description AS title_description
      FROM titles
      """;

    final var parameters = new MapSqlParameterSource();
    final var items = jdbc.query(sql, parameters, (rs, _) -> new FindTitlesRepositoryModel(
      rs.getString("title_uuid"),
      rs.getString("title_name"),
      rs.getString("title_description")
    ));

    return items;
  }

  public @Nullable FindTitleByUUIDRepositoryModel findTitleByUUID(final UUID uuid) {
    final var sql = """
      SELECT
        name AS title_name,
        description AS title_description
      FROM titles
      WHERE uuid = :uuid
      """;

    final var parameters = new MapSqlParameterSource();
    parameters.addValue("uuid", uuid.toString());

    return queryForObject(() -> jdbc.queryForObject(sql, parameters, (rs, _) -> new FindTitleByUUIDRepositoryModel(
      rs.getString("title_name"),
      rs.getString("title_description"))));
  }

  public void deleteTitle(final Number id) {
    final var sql = "DELETE FROM titles WHERE id = :id";
    final var parameters = new MapSqlParameterSource();
    parameters.addValue("id", id);
    jdbc.update(sql, parameters);
  }

  public Number createUser(final UUID uuid, final CreateUserRequestControllerModel user) {
    final var sql = "INSERT INTO users (uuid, username, name) VALUES (:uuid, :username, :name)";
    final var parameters = new MapSqlParameterSource();
    parameters.addValue("uuid", uuid.toString());
    parameters.addValue("username", user.username());
    parameters.addValue("name", user.name());

    return insert(sql, parameters);
  }

  public @Nullable Number findUserIDByUUID(final UUID id) {
    final var sql = "SELECT id FROM users WHERE uuid = :id";
    final var parameters = new MapSqlParameterSource();
    parameters.addValue("id", id.toString());

    return queryForObject(() -> jdbc.queryForObject(sql, parameters, Number.class));
  }

  public @Nullable Number findUserIDByUsername(final String username) {
    final var sql = "SELECT id FROM users WHERE username = :username";
    final var parameters = new MapSqlParameterSource();
    parameters.addValue("username", username);

    return queryForObject(() -> jdbc.queryForObject(sql, parameters, Number.class));
  }

  public List<FindUsersRepositoryModel> findUsers() {
    final var sql = """
      SELECT
        users.uuid AS user_uuid,
        users.username AS user_username,
        users.name AS user_name,
        user_titles.uuid AS user_title_uuid,
        user_titles.is_favorite as user_title_is_favorite,
        titles.uuid AS user_title_title_uuid,
        titles.name AS user_title_title_name,
        titles.description AS user_title_title_description
      FROM users
      LEFT JOIN title_users ON title_users.user = users.id
      LEFT JOIN user_titles ON user_titles.id = title_users.title
      LEFT JOIN titles ON titles.id = user_titles.title
      """;

    final var parameters = new MapSqlParameterSource();
    final var items = jdbc.query(sql, parameters, (rs, _) -> new FindUsersRepositoryModel(
      rs.getString("user_uuid"),
      rs.getString("user_username"),
      rs.getString("user_name"),
      rs.getString("user_title_uuid"),
      rs.getBoolean("user_title_is_favorite"),
      rs.getString("user_title_title_uuid"),
      rs.getString("user_title_title_name"),
      rs.getString("user_title_title_description")
    ));

    return items;
  }

  public List<FindUserByUUIDRepositoryModel> findUserByUUID(final UUID uuid) {
    final var sql = """
      SELECT
        users.username AS user_username,
        users.name AS user_name,
        user_titles.uuid AS user_title_uuid,
        user_titles.is_favorite as user_title_is_favorite,
        titles.uuid AS user_title_title_uuid,
        titles.name AS user_title_title_name,
        titles.description AS user_title_title_description
      FROM users
      LEFT JOIN title_users ON title_users.user = users.id
      LEFT JOIN user_titles ON user_titles.id = title_users.title
      LEFT JOIN titles ON titles.id = user_titles.title
      WHERE users.uuid = :uuid
      """;

    final var parameters = new MapSqlParameterSource();
    parameters.addValue("uuid", uuid.toString());

    final var items = jdbc.query(sql, parameters, (rs, _) -> new FindUserByUUIDRepositoryModel(
      rs.getString("user_username"),
      rs.getString("user_name"),
      rs.getString("user_title_uuid"),
      rs.getBoolean("user_title_is_favorite"),
      rs.getString("user_title_title_uuid"),
      rs.getString("user_title_title_name"),
      rs.getString("user_title_title_description")
    ));

    return items;
  }

  public void deleteUser(final Number id) {
    final var sql = "DELETE FROM users WHERE id = :id";
    final var parameters = new MapSqlParameterSource();
    parameters.addValue("id", id);
    jdbc.update(sql, parameters);
  }

  public Number createUserTitle(final UUID uuid, final Number title, final CreateUserTitleRequestControllerModel userTitle) {
    final var sql = "INSERT INTO user_titles (uuid, title, is_favorite) VALUES (:uuid, :title, :isFavorite)";
    final var parameters = new MapSqlParameterSource();
    parameters.addValue("uuid", uuid.toString());
    parameters.addValue("title", title);
    parameters.addValue("isFavorite", userTitle.isFavorite());

    return insert(sql, parameters);
  }

  public @Nullable Number findUserTitleIDByUUID(final UUID uuid) {
    final var sql = "SELECT id FROM user_titles WHERE uuid = :uuid";
    final var parameters = new MapSqlParameterSource();
    parameters.addValue("uuid", uuid.toString());

    return queryForObject(() -> jdbc.queryForObject(sql, parameters, Number.class));
  }

  public void deleteUserTitle(final Number id) {
    final var sql = "DELETE FROM user_titles WHERE id = :id";
    final var parameters = new MapSqlParameterSource();
    parameters.addValue("id", id);
    jdbc.update(sql, parameters);
  }

  public Number createTitleUser(final Number user, final Number title) {
    final var sql = "INSERT INTO title_users (user, title) VALUES (:user, :title)";
    final var parameters = new MapSqlParameterSource();
    parameters.addValue("user", user);
    parameters.addValue("title", title);

    return insert(sql, parameters);
  }
}
